package io.github.stelitop.battledudes.game.moves.script.interpreter

import io.github.stelitop.battledudes.game.battles._
import io.github.stelitop.battledudes.game.moves.script.ScriptSpecs
import io.github.stelitop.battledudes.game.moves.script.desugarer._

import java.util.concurrent.ThreadLocalRandom
import scala.annotation.tailrec;

object Interpreter {

  private type Binding = (String, Value)
  case class MoveData(
      move: BattleMove,
      battle: Battle,
      player: Player,
      opponent: Player,
      user: DudeRefV,
      target: Value,
      battleActions: BattleActions,
  )

  def interpNew(expr: ExprC, moveData: MoveData): (Value, List[Binding]) =
    interp(expr, List(("target", moveData.target), ("user", moveData.user)), moveData)
  def interp(expr: ExprC, bindings: List[Binding], moveData: MoveData): (Value, List[Binding]) = expr match {
    // Constant Values //
    case NullC() => (NullV(), bindings)
    case StringC(s) => (StringV(s), bindings)
    case NumberC(n) => (NumberV(n), bindings)
    case BoolC(b) => (BoolV(b), bindings)
    // TODO: Yes this is technically incorrect because it should chain bindings but cba to do it right now.
    case TupleC(exprs) => (TupleV(exprs.map(e => interp(e, bindings, moveData)._1)), bindings)
    case ElementalTypeC(t) => (ElementalTypeV(t), bindings)
    case MoveStyleC(st) => (MoveStyleV(st), bindings)

    // Arithmetic Operators //
    case AddC(left, right) => binOpHelper(left, right, bindings, moveData) match {
      case (NumberV(l), NumberV(r), newBindings) => (NumberV(l + r), newBindings)
      case _ => throw new RuntimeException("Tried adding two non-numbers!")
    }
    case MultC(left, right) => binOpHelper(left, right, bindings, moveData) match {
      case (NumberV(l), NumberV(r), newBindings) => (NumberV(l * r), newBindings)
      case _ => throw new RuntimeException("Tried multiplying two non-numbers!")
    }
    case DivC(left, right) => binOpHelper(left, right, bindings, moveData) match {
      case (NumberV(l), NumberV(r), newBindings) => (NumberV(l / r), newBindings)
      case _ => throw new RuntimeException("Tried dividing two non-numbers!")
    }
    case LtC(left, right) => binOpHelper(left, right, bindings, moveData) match {
      case (NumberV(l), NumberV(r), newBindings) => (BoolV(l < r), newBindings)
      case _ => throw new RuntimeException("Tried comparing two non-numbers!")
    }
    case LtEC(left, right) => binOpHelper(left, right, bindings, moveData) match {
      case (NumberV(l), NumberV(r), newBindings) => (BoolV(l <= r), newBindings)
      case _ => throw new RuntimeException("Tried comparing two non-numbers!")
    }
    case EqC(left, right) => binOpHelper(left, right, bindings, moveData) match {
      case (NumberV(l), NumberV(r), newBindings) => (BoolV(l == r), newBindings)
      case (BoolV(l), BoolV(r), newBindings) => (BoolV(l == r), newBindings)
      case (StringV(l), StringV(r), newBindings) => (BoolV(l == r), newBindings)
      case (ElementalTypeV(l), ElementalTypeV(r), newBindings) => (BoolV(l == r), newBindings)
      case (MoveStyleV(l), MoveStyleV(r), newBindings) => (BoolV(l == r), newBindings)
      case _ => throw new RuntimeException("Tried checking if two values of different types are equal!")
    }
    case AndC(left, right) => binOpHelper(left, right, bindings, moveData) match {
      case (BoolV(l), BoolV(r), newBindings) => (BoolV(l && r), newBindings)
      case _ => throw new RuntimeException("Used binary operation on non-binary values!")
    }
    case NotC(b) => {
      val (v, newBindings) = interp(b, bindings, moveData)
      v match {
        case BoolV(bool) => (BoolV(!bool), newBindings)
        case _ => throw new RuntimeException("Used binary operation on a non-binary value!")
      }
    }

    // Other Stuff //
    case PutC(name, value) =>
      val (v, binding2) = interp(value, bindings,moveData)
      (v, (name, v) :: binding2)
    case GetC(name) => (getBinding(name, bindings), bindings)
    case SeqC(exprs) => exprs.foldLeft((NullV(), bindings): (Value, List[Binding]))((cum, x) => interp(x, cum._2, moveData))
    case ClosureC(e) => (interp(e, bindings, moveData)._1, bindings)
    case IfC(b, t, f) =>
      val (bv, bindings2) = interp(b, bindings, moveData)
      bv match {
        case BoolV(true) => interp(t, bindings2, moveData)
        case BoolV(false) => interp(f, bindings2, moveData)
        case _ => throw new RuntimeException("Non-boolean value found in if statement!")
      }
    case RepeatC(minAmount, maxAmount, expr) => {
      val times = ThreadLocalRandom.current().nextInt(minAmount, maxAmount + 1)
      (NullV(), List.fill(times)(expr).foldLeft(bindings)((cum, x) => interp(x, cum, moveData)._2))
    }

    // Interactive Elements //
    case m@MetaC(_, _) => interpMeta(m, bindings, moveData)
    case a@ActionC(_, _) => interpAction(a, bindings, moveData)
    case TriggerC(name, value) if ScriptSpecs.moveTriggers.contains(name) =>
      moveData.move.addTrigger(ScriptSpecs.moveTriggers(name), value)
      (NullV(), bindings)
    case RandomC(chance, expr) =>
      if (ThreadLocalRandom.current().nextInt(100) < chance) {
        val (_, newBindings) = interp(expr, bindings, moveData)
        (BoolV(true), newBindings)
      } else {
        (BoolV(false), bindings)
      }

    case _ => throw new RuntimeException(s"Could not interpret the following: ${expr}")
  }

  private def interpAction(a: ActionC, bindings: List[Binding], moveData: MoveData): (Value, List[Binding]) = {
    val (v, bindings2) = interp(a.expr, bindings, moveData)
    (a.name, v) match {
      case ("damage", TupleV(DudeRefV(target) :: NumberV(x) :: Nil)) =>
        val realDmg = moveData.battleActions.dealDamage(moveData.battle, moveData.user.dudeRef, target, x, moveData.move.getElementalType)
        (NumberV(realDmg), bindings2)
      case ("applyStatus", TupleV(DudeRefV(target) :: StringV(status) :: NumberV(amount) :: Nil)) if ScriptSpecs.statusEffects.contains(status.toLowerCase) =>
        val newStatus = moveData.battleActions.applyStatus(moveData.battle, moveData.user.dudeRef, target, ScriptSpecs.statusEffects(status.toLowerCase), amount)
        (NumberV(newStatus), bindings2)
      case ("applyStatus", TupleV(DudeRefV(target) :: StringV(status) :: Nil)) if ScriptSpecs.statusEffects.contains(status.toLowerCase) =>
        val newStatus = moveData.battleActions.applyStatus(moveData.battle, moveData.user.dudeRef, target, ScriptSpecs.statusEffects(status.toLowerCase), 1000)
        (NumberV(newStatus), bindings2)
      case ("changeOffense", TupleV(DudeRefV(target) :: NumberV(amount) :: Nil))  =>
        val newAmount = moveData.battleActions.changeOffense(moveData.battle, moveData.user.dudeRef, target, amount)
        (NumberV(newAmount), bindings2)
      case ("changeDefense", TupleV(DudeRefV(target) :: NumberV(amount) :: Nil)) =>
        val newAmount = moveData.battleActions.changeDefense(moveData.battle, moveData.user.dudeRef, target, amount)
        (NumberV(newAmount), bindings2)
      case ("changeSpeed", TupleV(DudeRefV(target) :: NumberV(amount) :: Nil)) =>
        val newAmount = moveData.battleActions.changeSpeed(moveData.battle, moveData.user.dudeRef, target, amount)
        (NumberV(newAmount), bindings2)
      case _ => throw new RuntimeException(s"Incorrect value type for action \"${a.name}\"")
    }
  }

  private def interpMeta(m: MetaC, bindings: List[Binding], moveData: MoveData): (Value, List[Binding]) = {
    val (v, bindings2) = interp(m.expr, bindings, moveData)
    (m.name, v) match {
      case ("name", StringV(s)) =>
        moveData.move.setName(s)
        (NullV(), bindings2)
      case ("text", StringV(s)) =>
        moveData.move.setText(s)
        (NullV(), bindings2)
      case ("charges", NumberV(n)) =>
        moveData.move.setCharges(n)
        (NullV(), bindings2)
      case ("accuracy", NumberV(n)) =>
        moveData.move.setAccuracy(n)
        (NullV(), bindings2)
      case ("element", ElementalTypeV(t)) =>
        moveData.move.setElementalType(t)
        (NullV(), bindings2)
      case ("style", MoveStyleV(st)) =>
        moveData.move.setMoveStyle(st)
        (NullV(), bindings2)
      case ("target", StringV(t)) if ScriptSpecs.targetTypes.contains(t) =>
        moveData.move.setTargetType(ScriptSpecs.targetTypes(t));
        (NullV(), bindings2)
      case _ => throw new RuntimeException(s"Incorrect value type for meta \"${m.name}\"")
    }
  }

  @tailrec
  private def getBinding(name: String, bindings: List[Binding]): Value = bindings match {
    case Nil => throw new RuntimeException(s"Unbinded variable \"${name}\"!")
    case (n, v) :: _ if (n == name) => v
    case _ :: rest => getBinding(name, rest)
  }

  private def binOpHelper(left: ExprC, right: ExprC, bindings: List[Binding], moveData: MoveData): (Value, Value, List[Binding]) = {
    val (l, b2) = interp(left, bindings, moveData)
    val (r, b3) = interp(right, b2, moveData)
    (l, r, b3)
  }
}

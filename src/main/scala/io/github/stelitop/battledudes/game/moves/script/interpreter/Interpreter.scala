package io.github.stelitop.battledudes.game.moves.script.interpreter

import io.github.stelitop.battledudes.game.fights._
import io.github.stelitop.battledudes.game.moves.script.ScriptSpecs
import io.github.stelitop.battledudes.game.moves.script.desugarer._;

object Interpreter {

  private type Binding = (String, Value)
  case class MoveData(
      move: BattleMove,
      battle: Battle,
      player: Player,
      opponent: Player,
      dude: BattleDude,
      target: BattleDude,
      battleActions: BattleActions,
  )

  def interpNew(expr: ExprC, moveData: MoveData) = interp(expr, Nil, moveData)
  def interp(expr: ExprC, bindings: List[Binding], moveData: MoveData): (Value, List[Binding]) = expr match {
    case NullC() => (NullV(), bindings)
    case StringC(s) => (StringV(s), bindings)
    case NumberC(n) => (NumberV(n), bindings)
    case BoolC(b) => (BoolV(b), bindings)
    case ElementalTypeC(t) => (ElementalTypeV(t), bindings)
    case MoveStyleC(st) => (MoveStyleV(st), bindings)
    case PutC(name, value) =>
      val (v, binding2) = interp(value, bindings,moveData)
      (v, (name, v) :: binding2)
    case GetC(name) => (getBinding(name, bindings), bindings)
    case BinOpC(op, left, right) => ???
    case SeqC(exprs) => exprs.foldLeft((NullV(), bindings): (Value, List[Binding]))((cum, x) => interp(x, cum._2, moveData))
    case ClosureC(e) => (interp(e, bindings, moveData)._1, bindings)
    case m@MetaC(_, _) => interpMeta(m, bindings, moveData)
    case a@ActionC(_, _) => interpAction(a, bindings, moveData)
    case TriggerC(name, value) if ScriptSpecs.moveTriggers.contains(name) =>
      moveData.move.addTrigger(ScriptSpecs.moveTriggers(name), value)
      (NullV(), bindings)
  }

  private def getBinding(name: String, bindings: List[Binding]): Value = bindings match {
    case Nil => throw new RuntimeException(s"Unbinded variable \"${name}\"!")
    case (n, v) :: _ if (n == name) => v
    case _ :: rest => getBinding(name, rest)
  }

  //private final val existingActions = List("damage")
  private def interpAction(a: ActionC, bindings: List[Binding], moveData: MoveData): (Value, List[Binding]) = {
    //if (!existingActions.contains(a.name)) throw new RuntimeException(s"Illegal action name \"${a.name}\" found!")
    val (v, bindings2) = interp(a.value, bindings, moveData)
    (a.name, v) match {
      case ("damage", NumberV(x)) =>
        val realDmg = moveData.battleActions.dealDamage(moveData.battle, moveData.dude, moveData.target, x, moveData.move.getElementalType)
        (NumberV(realDmg), bindings2)
      case _ => throw new RuntimeException(s"Incorrect value type for action \"${a.name}\"")
    }
  }

  //private final val existingMetaVals = List("name")
  private def interpMeta(m: MetaC, bindings: List[Binding], moveData: MoveData): (Value, List[Binding]) = {
    //if (!existingMetaVals.contains(m.name)) throw new RuntimeException(s"Illegal meta name \"${m.name}\" found!")
    val (v, bindings2) = interp(m.value, bindings, moveData)
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
      case _ => throw new RuntimeException(s"Incorrect value type for meta \"${m.name}\"")
    }
  }
}

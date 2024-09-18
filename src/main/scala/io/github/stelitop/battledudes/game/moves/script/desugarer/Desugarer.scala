package io.github.stelitop.battledudes.game.moves.script.desugarer

import io.github.stelitop.battledudes.game.moves.script.parser._;

object Desugarer {

  def desugar(expr: ExprP): ExprC = expr match {
    case NullP() => NullC()
    case StringP(s) => StringC(s)
    case NumberP(n) => NumberC(n)
    case BoolP(b) => BoolC(b)
    case ElementalTypeP(t) => ElementalTypeC(t)
    case MoveStyleP(st) => MoveStyleC(st)
    case PutP(name, value) => PutC(name, desugar(value))
    case GetP(name) => GetC(name)
    case b@BinOpP(_, _, _) => desugarBinOp(b)

    case SeqP(exprs) => SeqC(exprs.map(desugar))
    case TupleP(exprs) => TupleC(exprs.map(desugar))
    case ClosureP(e) => ClosureC(desugar(e))
    case IfP(b, t, f) => IfC(desugar(b), desugar(t), desugar(f))
    case RepeatP(minAmount, maxAmount, expr) => RepeatC(minAmount, maxAmount, desugar(expr))

    case MetaP(name, expr) => MetaC(name, desugar(expr))
    case ActionP(name, expr) => ActionC(name, desugar(expr))
    case TriggerP(name, expr) => TriggerC(name, desugar(expr))
    case RandomP(chance, expr) => RandomC(chance, desugar(expr))
  }

  def desugarBinOp(expr: BinOpP): ExprC = expr match {
    case BinOpP("+", left, right) => AddC(desugar(left), desugar(right))
    case BinOpP("*", left, right) => MultC(desugar(left), desugar(right))
    case BinOpP("-", left, right) => AddC(desugar(left), MultC(NumberC(-1), desugar(right)))
    case BinOpP("/", left, right) => DivC(desugar(left), desugar(right))
    case BinOpP("<", left, right) => LtC(desugar(left), desugar(right))
    case BinOpP(">", left, right) => LtC(MultC(NumberC(-1), desugar(left)), MultC(NumberC(-1), desugar(right)))
    case BinOpP("<=", left, right) => LtEC(desugar(left), desugar(right))
    case BinOpP(">=", left, right) => LtEC(MultC(NumberC(-1), desugar(left)), MultC(NumberC(-1), desugar(right)))
    case BinOpP("==", left, right) => EqC(desugar(left), desugar(right))
    case BinOpP("!=", left, right) => NotC(EqC(desugar(left), desugar(right)))
    case BinOpP("&&", left, right) => AndC(desugar(left), desugar(right))
    case BinOpP("||", left, right) => NotC(AndC(NotC(desugar(left)), NotC(desugar(right))))
    case BinOpP(op, _, _) => throw new RuntimeException(s"Could not desugar operator \"$op\"!")
  }
}

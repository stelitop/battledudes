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
    case BinOpP(op, left, right) => BinOpC(op, desugar(left), desugar(right))
    case SeqP(exprs) => SeqC(exprs.map(desugar))
    case ClosureP(e) => ClosureC(desugar(e))
    case MetaP(name, value) => MetaC(name, desugar(value))
    case ActionP(name, value) => ActionC(name, desugar(value))
    case TriggerP(name, value) => TriggerC(name, desugar(value))
  }
}

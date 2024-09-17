package io.github.stelitop.battledudes.game.moves.script.desugarer

import io.github.stelitop.battledudes.game.enums.{ElementalType, MoveStyle}

sealed abstract class ExprC

case class NullC() extends ExprC
case class StringC(s: String) extends ExprC
case class NumberC(n: Int) extends ExprC
case class BoolC(b: Boolean) extends ExprC
case class ElementalTypeC(t: ElementalType) extends ExprC
case class MoveStyleC(st: MoveStyle) extends ExprC

case class AddC(left: ExprC, right: ExprC) extends ExprC
case class MultC(left: ExprC, right: ExprC) extends ExprC
case class DivC(left: ExprC, right: ExprC) extends ExprC
case class LtC(left: ExprC, right: ExprC) extends ExprC
case class LtEC(left: ExprC, right: ExprC) extends ExprC
case class EqC(left: ExprC, right: ExprC) extends ExprC
case class AndC(left: ExprC, right: ExprC) extends ExprC
case class NotC(b: ExprC) extends ExprC

case class PutC(name: String, value: ExprC) extends ExprC // sets a variable to a value
case class GetC(name: String) extends ExprC // gets the value of a variable
case class SeqC(exprs: List[ExprC]) extends ExprC
case class TupleC(exprs: List[ExprC]) extends ExprC
case class ClosureC(e: ExprC) extends ExprC

case class MetaC(name: String, expr: ExprC) extends ExprC
case class ActionC(name: String, expr: ExprC) extends ExprC
case class TriggerC(name: String, expr: ExprC) extends ExprC
case class RandomC(chance: Int, expr: ExprC) extends ExprC
package io.github.stelitop.battledudes.game.moves.script.desugarer

import io.github.stelitop.battledudes.game.enums.{ElementalType, MoveStyle}

sealed abstract class ExprC

case class NullC() extends ExprC
case class StringC(s: String) extends ExprC
case class NumberC(n: Int) extends ExprC
case class BoolC(b: Boolean) extends ExprC
case class ElementalTypeC(t: ElementalType) extends ExprC
case class MoveStyleC(st: MoveStyle) extends ExprC


case class PutC(name: String, value: ExprC) extends ExprC // sets a variable to a value
case class GetC(name: String) extends ExprC // gets the value of a variable
case class BinOpC(op: String, left: ExprC, right: ExprC) extends ExprC
case class SeqC(exprs: List[ExprC]) extends ExprC
case class ClosureC(e: ExprC) extends ExprC

case class MetaC(name: String, value: ExprC) extends ExprC
case class ActionC(name: String, value: ExprC) extends ExprC
case class TriggerC(name: String, value: ExprC) extends ExprC
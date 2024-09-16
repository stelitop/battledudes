package io.github.stelitop.battledudes.game.moves.script.parser

import io.github.stelitop.battledudes.game.enums.{ElementalType, MoveStyle}

sealed abstract class ExprP

case class NullP() extends ExprP
case class StringP(s: String) extends ExprP
case class NumberP(n: Int) extends ExprP
case class BoolP(b: Boolean) extends ExprP
case class ElementalTypeP(t: ElementalType) extends ExprP
case class MoveStyleP(st: MoveStyle) extends ExprP

case class PutP(name: String, value: ExprP) extends ExprP // sets a variable to a value
case class GetP(name: String) extends ExprP // gets the value of a variable
case class BinOpP(op: String, left: ExprP, right: ExprP) extends ExprP
case class SeqP(exprs: List[ExprP]) extends ExprP
case class ClosureP(e: ExprP) extends ExprP

case class MetaP(name: String, value: ExprP) extends ExprP
case class ActionP(name: String, value: ExprP) extends ExprP
case class TriggerP(name: String, value: ExprP) extends ExprP
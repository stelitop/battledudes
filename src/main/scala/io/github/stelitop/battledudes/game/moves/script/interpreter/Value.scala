package io.github.stelitop.battledudes.game.moves.script.interpreter

import io.github.stelitop.battledudes.game.enums.{ElementalType, MoveStyle}

sealed abstract class Value()

case class NullV() extends Value
case class NumberV(n: Int) extends Value
case class StringV(s: String) extends Value
case class BoolV(b: Boolean) extends Value
case class TupleV(vals: List[Value]) extends Value
case class ElementalTypeV(t: ElementalType) extends Value
case class MoveStyleV(st: MoveStyle) extends Value

// CLOSURE
// OTHER STUFF

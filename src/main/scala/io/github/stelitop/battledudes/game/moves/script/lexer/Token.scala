package io.github.stelitop.battledudes.game.moves.script.lexer

sealed abstract class Token

case class NumberT(n: Int) extends Token
case class StringT(s: String) extends Token
case class NameT(name: String) extends Token
case class OperatorT(op: String) extends Token
case class SpecialCharT(c: Char) extends Token
case class KeywordT(kw: String) extends Token

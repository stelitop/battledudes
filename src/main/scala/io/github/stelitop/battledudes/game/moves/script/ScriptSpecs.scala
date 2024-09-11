package io.github.stelitop.battledudes.game.moves.script

package object ScriptSpecs {
  val arithOps: Set[String] = Set("+", "-", "*", "/")
  val boolOps: Set[String] = Set("<", "<=", ">=", ">", "==")
  val keywords: Set[String] = Set("var", "true", "false", "if", "else", "then", "end", "action", "trigger", "meta")
  var nameChars: Set[Char] = Set('_', 'A', 'a', 'B', 'b', 'C', 'c', 'D', 'd', 'E', 'e', 'F', 'f', 'G', 'g', 'H', 'h', 'I', 'i', 'J', 'j', 'K', 'k', 'L', 'l', 'M', 'm', 'N', 'n', 'O', 'o', 'P', 'p', 'Q', 'q', 'R', 'r', 'S', 's', 'T', 't', 'U', 'u', 'V', 'v', 'W', 'w', 'X', 'x', 'Y', 'y', 'Z', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
  val operatorChars: Set[Char] = Set('+', '-', '*', '/', '=', '>', '<')
  val digitChars: Set[Char] = Set('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
  val specialChars: Set[Char] = Set('(', ')', '.', ',', ';', '{', '}')
}







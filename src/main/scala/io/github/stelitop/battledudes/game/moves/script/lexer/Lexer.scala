package io.github.stelitop.battledudes.game.moves.script.lexer

import io.github.stelitop.battledudes.game.moves.script.ScriptSpecs

object Lexer {
  def tokenize(text: List[Char]): List[Token] = text match {
    case Nil => List()
    case c :: _ if ScriptSpecs.digitChars.contains(c) =>
      val (x, left) = getMaxSequence(text, ScriptSpecs.digitChars)
      NumberT(x.mkString.toInt) :: tokenize(left)
    case c :: cs if ScriptSpecs.specialChars.contains(c) =>
      SpecialCharT(c) :: tokenize(cs)
    case c :: _ if ScriptSpecs.nameChars.contains(c) =>
      val (x, left) = getMaxSequence(text, ScriptSpecs.nameChars)
      val s = x.mkString
      if (ScriptSpecs.keywords.contains(s)) KeywordT(s) :: tokenize(left) else NameT(x.mkString) :: tokenize(left)
    case c :: _ if ScriptSpecs.operatorChars.contains(c) =>
      val (x, left) = getMaxSequence(text, ScriptSpecs.operatorChars)
      OperatorT(x.mkString) :: tokenize(left)
    case '"' :: cs =>
      val (x, left) = getUntilQuotation(cs)
      StringT(x.mkString) :: tokenize(left)
    case _ :: cs => tokenize(cs)
  }

  def getMaxSequence(line: List[Char], charSet: Set[Char]): (List[Char], List[Char]) = line match {
    case Nil => (Nil, Nil)
    case c :: cs => if (!charSet.contains(c)) (Nil, line) else {
      val (s, left) = getMaxSequence(cs, charSet)
      (c :: s, left)
    }
  }

  def getUntilQuotation(line: List[Char]): (List[Char], List[Char]) = line match {
    case Nil => throw new RuntimeException();
    case '"' :: left => (Nil, left)
    case c :: cs =>
      val (s, left) = getUntilQuotation(cs)
      (c :: s, left)

  }
}
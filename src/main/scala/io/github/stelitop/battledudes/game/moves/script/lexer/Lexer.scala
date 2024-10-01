package io.github.stelitop.battledudes.game.moves.script.lexer

import io.github.stelitop.battledudes.game.moves.script.ScriptSpecs

object Lexer {

  def tokenize(text: String): List[Token] = tokenizePure(text.split("\n")
    .toList.map(s => removeComment(s.toList)).map(_ + "\n").mkString.toList)

    //tokenize(text.toCharArray.toList)
  //def tokenize(text: List[Char]): List[Token] = text.tokenizePure(text)

  private def tokenizePure(text: List[Char]): List[Token] = text match {
    case Nil => List()
    case c :: _ if ScriptSpecs.digitChars.contains(c) =>
      val (x, left) = getMaxSequence(text, ScriptSpecs.digitChars)
      NumberT(x.mkString.toInt) :: tokenizePure(left)
    case c :: cs if ScriptSpecs.specialChars.contains(c) =>
      SpecialCharT(c) :: tokenizePure(cs)
    case c :: _ if ScriptSpecs.nameChars.contains(c) =>
      val (x, left) = getMaxSequence(text, ScriptSpecs.nameChars)
      val s = x.mkString
      if (ScriptSpecs.keywords.contains(s)) KeywordT(s) :: tokenizePure(left) else NameT(x.mkString) :: tokenizePure(left)
    case c :: _ if ScriptSpecs.operatorChars.contains(c) =>
      val (x, left) = getMaxSequence(text, ScriptSpecs.operatorChars)
      OperatorT(x.mkString) :: tokenizePure(left)
    case '"' :: cs =>
      val (x, left) = getUntilQuotation(cs)
      StringT(x.mkString) :: tokenizePure(left)
    case _ :: cs => tokenizePure(cs)
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

  def removeComment(s: List[Char], isString: Boolean = false): String = s match {
    case Nil => ""
    case '#' :: rest if !isString => ""
    case '"' :: rest => '"' + removeComment(rest, !isString)
    case c :: rest => c + removeComment(rest, isString)
  }
}
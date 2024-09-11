package io.github.stelitop.battledudes.game.moves.script.parser

import io.github.stelitop.battledudes.game.moves.script.lexer.{KeywordT, NameT, NumberT, OperatorT, SpecialCharT, StringT, Token}
import io.github.stelitop.battledudes.game.moves.script.ScriptSpecs
import io.github.stelitop.battledudes.game.moves.script.ScriptSpecs.{arithOps, boolOps}

object Parser {
  type Leftover = List[Token]
//  def parse(tokens: List[Token]): (ExprP, Leftover) = {
//    val splitIndexes = tokens.zipWithIndex.filter(_._1 == SpecialCharT(';')).map(_._2)
//    if (splitIndexes.nonEmpty) {
//      val ranges = (0 :: splitIndexes.map(_ + 1)).zip(splitIndexes :+ tokens.size)
//        .filter(x => x._1 < x._2)
//      return ranges
//        .map(r => tokens.slice(r._1, r._2))
//        .map(parse)
//        .foldRight(NullP(): ExprP)((a, cum) => SeqP(a, cum))
//    } else {
//      println(tokens)
//    }
//    null
//  }
//  def parse(tokens: List[Token]): ExprP = {
//    val (expr, left) = getNextExpr(tokens)
//    left match {
//      case Nil => expr
//      case SpecialCharT(';') :: rest => SeqP(expr, parse(rest))
//      case _ => throw new RuntimeException("Leftover tokens could not be parsed! Leftover: \"" + left.toString() + "\"")
//    }
//  }

  def parse(tokens: List[Token]): ExprP = {
    val idxs = tokens.zipWithIndex.foldLeft((List(): List[Int], 0)){
      case ((l, 0), (SpecialCharT(';'), idx)) => (l :+ idx, 0)
      case ((l, br), (SpecialCharT('{'), _)) => (l, br+1)
      case ((l, br), (SpecialCharT('}'), _)) => (l, br-1)
      case (cum, _) => cum
    }._1
    val parts = (0 :: idxs.map(_+1)).zip(idxs :+ tokens.size)
      .map(p => tokens.slice(p._1, p._2))
      .map(parseSingleExpression)
      .reverse// might be different function

    parts.tail.foldLeft(SeqP(parts.head :: Nil): SeqP)((cum, l) => SeqP(l :: cum.exprs))
  }

  //TODO: Do arithmetic ordering correctly
  def parseSingleExpression(tokens: List[Token]): ExprP = {
    val (part, leftover) = getNextPart(tokens)
    leftover match {
      case Nil => part
      case SpecialCharT(',') :: rest => BinOpP(",", part, parseSingleExpression(rest))
      case OperatorT(op) :: rest if arithOps.contains(op) || boolOps.contains(op) => BinOpP(op, part, parseSingleExpression(rest))
    }
  }

  def getNextPart(tokens: List[Token]): (ExprP, Leftover) = tokens match {
    case Nil => (NullP(), Nil)
//    case _ if tokens.contains(SpecialCharT(';')) =>
//      val idx = tokens.indexOf(SpecialCharT(';'))
//      val left = parse(tokens.slice(0, idx))
//      val right = parse(tokens.slice(idx + 1, tokens.size))
//      (SeqP(left, right), Nil)
    case SpecialCharT('{') :: rest =>
      val matchIdx = rest.zipWithIndex.foldLeft((0, 1))((cum, x) => if (cum._2 == 0) cum else if (x._1 == SpecialCharT('{')) (x._2, cum._2 + 1) else if (x._1 == SpecialCharT('}')) (x._2, cum._2 - 1) else (x._2, cum._2))
      if (matchIdx._2 != 0) throw new RuntimeException("Unclosed { bracket!")
      val inside = parse(rest.slice(0, matchIdx._1))
      (ClosureP(inside), rest.drop(matchIdx._1 + 1))
    case SpecialCharT('(') :: rest =>
      val matchIdx = rest.zipWithIndex.foldLeft((0, 1))((cum, x) => if (cum._2 == 0) cum else if (x._1 == SpecialCharT('(')) (x._2, cum._2 + 1) else if (x._1 == SpecialCharT(')')) (x._2, cum._2 - 1) else (x._2, cum._2))
      if (matchIdx._2 != 0) throw new RuntimeException("Unclosed ( bracket!")
      val inside = parse(rest.slice(0, matchIdx._1))
      (inside, rest.drop(matchIdx._1 + 1))
    case StringT(s) :: rest => (StringP(s), rest)
    case NumberT(s) :: rest => (NumberP(s), rest)
    case KeywordT("true") :: rest => (BoolP(true), rest)
    case KeywordT("false") :: rest => (BoolP(false), rest)
    case KeywordT("meta") :: NameT(name) :: OperatorT("=") :: rest => (MetaP(name, parseSingleExpression(rest)), Nil)
    case KeywordT("action") :: NameT(name) :: rest => (ActionP(name, parseSingleExpression(rest)), Nil)
    case KeywordT("trigger") :: NameT(name) :: OperatorT("+=") :: rest => (TriggerP(name, parseSingleExpression(rest)), Nil)
    case NameT(s) :: OperatorT("=") :: rest => (PutP(s, parseSingleExpression(rest)), Nil)
    case NameT(s) :: rest => (GetP(s), rest)
  }
}

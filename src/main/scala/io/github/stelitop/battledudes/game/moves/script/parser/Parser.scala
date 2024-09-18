package io.github.stelitop.battledudes.game.moves.script.parser

import io.github.stelitop.battledudes.game.enums.ElementalType
import io.github.stelitop.battledudes.game.moves.script.lexer.{KeywordT, NameT, NumberT, OperatorT, SpecialCharT, StringT, Token}
import io.github.stelitop.battledudes.game.moves.script.ScriptSpecs
import io.github.stelitop.battledudes.game.moves.script.ScriptSpecs.{arithOps, boolOps}

object Parser {
  private type Leftover = List[Token]


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
      case SpecialCharT(',') :: rest => parseSingleExpression(rest) match {
        case TupleP(exprs) => TupleP(part :: exprs)
        case x => TupleP(part :: x :: Nil)
      }
      case OperatorT(op) :: rest if arithOps.contains(op) || boolOps.contains(op) => BinOpP(op, part, parseSingleExpression(rest))
      case _ => throw new RuntimeException(s"Leftover found unparsed! \"${leftover}\"")
    }
  }

  def getNextPart(tokens: List[Token]): (ExprP, Leftover) = tokens match {
    case Nil => (NullP(), Nil)
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
    case KeywordT(el) :: rest if ScriptSpecs.elementalTypes.keySet.contains(el) => (ElementalTypeP(ScriptSpecs.elementalTypes(el)), rest)
    case KeywordT(st) :: rest if ScriptSpecs.moveStyles.keySet.contains(st) => (MoveStyleP(ScriptSpecs.moveStyles(st)), rest)
    case KeywordT("true") :: rest => (BoolP(true), rest)
    case KeywordT("false") :: rest => (BoolP(false), rest)
    case KeywordT("meta") :: NameT(name) :: OperatorT("=") :: rest => (MetaP(name, parseSingleExpression(rest)), Nil)
    case KeywordT("action") :: NameT(name) :: rest => (ActionP(name, parseSingleExpression(rest)), Nil)
    case KeywordT("trigger") :: NameT(name) :: OperatorT("+=") :: rest => (TriggerP(name, parseSingleExpression(rest)), Nil)
    case KeywordT("random") :: NumberT(chance) :: rest => (RandomP(chance, parseSingleExpression(rest)), Nil)
    case KeywordT("if") :: rest =>
      val (b, rest1) = getNextPart(rest)
      val (t, rest2) = getNextPart(rest1)
      val (f, rest3) = getNextPart(rest2)
      (IfP(b, t, f), rest3)
    case KeywordT("repeat") :: NumberT(mn) :: NumberT(mx) :: rest => (RepeatP(mn, mx, parseSingleExpression(rest)), Nil)
    case KeywordT("repeat") :: NumberT(amount) :: rest => (RepeatP(amount, amount, parseSingleExpression(rest)), Nil)
    case NameT(s) :: OperatorT("=") :: rest => (PutP(s, parseSingleExpression(rest)), Nil)
    case NameT(s) :: rest => (GetP(s), rest)
    case _ => throw new RuntimeException(s"Could not parse the following: ${tokens}")
  }
}

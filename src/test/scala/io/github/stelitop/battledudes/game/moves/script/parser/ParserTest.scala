package io.github.stelitop.battledudes.game.moves.script.parser

import io.github.stelitop.battledudes.game.moves.script.lexer.Lexer
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ParserTest {
  @Test
  def testOneIsPositive(): Unit = {
    val code = "{a = 5; true}"
    println(code)
    val tokenized = Lexer.tokenize(code.toCharArray.toList)
    println(tokenized)
    val result = Parser.parse(tokenized)
    println(result)
  }

  @Test
  def parseWhack(): Unit = {
    val code =
      """
      meta name = "Whack";
      meta element = neutral;
      meta text = "Deal 40 damage.";
      meta style = ranged;
      meta charges = 20;
      meta accuracy = 100;

      trigger use += {
        action damage 40;
      };
      """
    println(code)
    val tokenized = Lexer.tokenize(code.toCharArray.toList)
    println(tokenized)
    val result = Parser.parse(tokenized)
    println(result)
  }

  @Test
  def parseMath(): Unit = {
    val code =
      """
      a = 15;
      b = 10 + 40 + 25;
      c = b - a;
      """
    println(code)
    val tokenized = Lexer.tokenize(code.toCharArray.toList)
    println(tokenized)
    val result = Parser.parse(tokenized)
    println(result)
  }

  @Test
  def parseCommaSeparatedArgsInAction(): Unit = {
    val code =
      """
      action params 1, 2, 3, 100;
      """
    println(code)
    val tokenized = Lexer.tokenize(code.toCharArray.toList)
    println(tokenized)
    val result = Parser.parse(tokenized)
    println(result)
  }
}

package io.github.stelitop.battledudes.game.moves.script.interpreter;

import io.github.stelitop.battledudes.game.battles.{BattleActions, EmptyBattleActions}
import io.github.stelitop.battledudes.game.moves.script.desugarer.Desugarer
import io.github.stelitop.battledudes.game.moves.script.interpreter.Interpreter.MoveData
import io.github.stelitop.battledudes.game.moves.script.lexer.Lexer
import io.github.stelitop.battledudes.game.moves.script.parser.Parser
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test;


class InterpreterTest {

    def interpCode(code: String): Value =
      Interpreter.interp(
        Desugarer.desugar(
          Parser.parse(
            Lexer.tokenize(code.toCharArray.toList))), List(), new MoveData(null, null, null, null, null, null, new EmptyBattleActions()))._1

    @Test
    def interpSimpleVar(): Unit = {
        val code =
        """
        a = 5;
        a
        """
        assertThat(interpCode(code)).isEqualTo(NumberV(5))
    }

    @Test
    def interpIfStatement(): Unit = {
      val codeT =
        """
        a = 3;
        if (4 < 8) (a = 10) (a = 35);
        a
        """
      assertThat(interpCode(codeT)).isEqualTo(NumberV(10))

      val codeF =
        """
        a = 3;
        if (4 > 8) (a = 10) (a = 35);
        a
        """
      assertThat(interpCode(codeF)).isEqualTo(NumberV(35))
    }

  @Test
  def interpOrderOfOperation(): Unit = {
    val code =
      """
      a = (1 + 3) * ((4 - 2) + (9 / (1 + 2)));
      a
      """
    assertThat(interpCode(code)).isEqualTo(NumberV(20))
  }

  @Test
  def interpRepeat(): Unit = {
    val code =
      """
      a = 5;
      repeat 6 (a = a + 10);
      a
      """
    assertThat(interpCode(code)).isEqualTo(NumberV(65))
  }
}
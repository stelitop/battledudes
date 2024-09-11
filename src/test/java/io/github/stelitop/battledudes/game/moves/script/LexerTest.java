package io.github.stelitop.battledudes.game.moves.script;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

//class LexerTest {
//
//    @Test
//    void splitTest() {
//        var lexer = new Lexer();
//        var result = lexer.split("123 += \"string test\",<(thing);");
//        assertThat(result).hasSize(9);
//        assertThat(result.get(0)).isEqualTo(Pair.of("123", Lexer.TokenType.Number));
//        assertThat(result.get(1)).isEqualTo(Pair.of("+=", Lexer.TokenType.Operator));
//        assertThat(result.get(2)).isEqualTo(Pair.of("string test", Lexer.TokenType.String));
//        assertThat(result.get(3)).isEqualTo(Pair.of(",", Lexer.TokenType.SpecialChar));
//        assertThat(result.get(4)).isEqualTo(Pair.of("<", Lexer.TokenType.Operator));
//        assertThat(result.get(5)).isEqualTo(Pair.of("(", Lexer.TokenType.SpecialChar));
//        assertThat(result.get(6)).isEqualTo(Pair.of("thing", Lexer.TokenType.Name));
//        assertThat(result.get(7)).isEqualTo(Pair.of(")", Lexer.TokenType.SpecialChar));
//        assertThat(result.get(8)).isEqualTo(Pair.of(";", Lexer.TokenType.SpecialChar));
//    }
//
//    @Test
//    void splitNegativeNumber() {
//        var lexer = new Lexer();
//        var result = lexer.split("-54321");
//        assertThat(result).hasSize(1);
//        assertThat(result.get(0)).isEqualTo(Pair.of("-54321", Lexer.TokenType.Number));
//    }
//
//    @Test
//    void splitNameWithNumbers() {
//        var lexer = new Lexer();
//        var result = lexer.split("var name123");
//        assertThat(result).hasSize(2);
//        assertThat(result.get(0)).isEqualTo(Pair.of("var", Lexer.TokenType.Name));
//        assertThat(result.get(1)).isEqualTo(Pair.of("name123", Lexer.TokenType.Name));
//    }
//}
package io.github.stelitop.battledudes.game.battles;

import io.github.stelitop.battledudes.game.moves.script.desugarer.Desugarer;
import io.github.stelitop.battledudes.game.moves.script.interpreter.Interpreter;
import io.github.stelitop.battledudes.game.moves.script.lexer.Lexer;
import io.github.stelitop.battledudes.game.moves.script.parser.Parser;
import org.springframework.stereotype.Service;

@Service
public class MoveScriptService {

    public BattleMove createMove(String code) {
        BattleMove newMove = new BattleMove();
        Interpreter.MoveData moveData = new Interpreter.MoveData(
                newMove,
                null,
                null,
                null,
                null,
                null,
                null
        );
        Interpreter.interpNew(Desugarer.desugar(Parser.parse(Lexer.tokenize(code))), moveData);
        return newMove;
    }
}

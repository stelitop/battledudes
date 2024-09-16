package io.github.stelitop.battledudes.game.fights;

import io.github.stelitop.battledudes.game.enums.ElementalType;
import io.github.stelitop.battledudes.game.enums.MoveStyle;
import io.github.stelitop.battledudes.game.moves.script.desugarer.NullC;
import io.github.stelitop.battledudes.game.moves.script.interpreter.Interpreter;
import io.github.stelitop.battledudes.game.moves.script.desugarer.ExprC;

import java.util.HashMap;
import java.util.Map;

public class BattleMove {

    private ExprC script = new NullC();
    private Map<MoveTrigger, ExprC> triggers = new HashMap<>();
    private String name = "Missing name.";
    private String text = "Missing text.";
    private ElementalType elementalType = ElementalType.Neutral;
    private MoveStyle moveStyle = MoveStyle.Melee;
    private int charges = 100;
    private int accuracy = 100;

    public BattleMove() {

    }

    public void use(Battle battle, Player user, Player opponent, BattleActions ba) {
        var code = getTrigger(MoveTrigger.OnUse);
        Interpreter.MoveData moveData = new Interpreter.MoveData(
                this,
                battle,
                user,
                opponent,
                user.getSelectedDude(),
                opponent.getSelectedDude(),
                ba
        );
        Interpreter.interpNew(code, moveData);
    }

    public ExprC getScript() {
        return script;
    }

    public void setScript(ExprC script) {
        this.script = script;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ElementalType getElementalType() {
        return elementalType;
    }

    public void setElementalType(ElementalType elementalType) {
        this.elementalType = elementalType;
    }

    public MoveStyle getMoveStyle() {
        return moveStyle;
    }

    public void setMoveStyle(MoveStyle moveStyle) {
        this.moveStyle = moveStyle;
    }

    public int getCharges() {
        return charges;
    }

    public void setCharges(int charges) {
        this.charges = charges;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public void addTrigger(MoveTrigger trigger, ExprC expr) {
        triggers.put(trigger, expr);
//        if (!triggers.containsKey(trigger)) {
//            triggers.put(trigger, expr);
//        } else {
//            if (triggers.get(trigger) instanceof SeqC s) {
//                triggers.put(trigger, new SeqC(s.exprs().appended(expr)));
//            } else {
//                triggers.put(trigger, new SeqC(Nil$.MODULE$.$colon$colon(expr).$colon$colon(triggers.get(trigger))));
//            }
//        }
    }

    public ExprC getTrigger(MoveTrigger trigger) {
        if (triggers.containsKey(trigger)) return triggers.get(trigger);
        else return new NullC();
    }
}

package io.github.stelitop.battledudes.game.battles;

import io.github.stelitop.battledudes.game.enums.ElementalType;
import io.github.stelitop.battledudes.game.enums.MoveStyle;
import io.github.stelitop.battledudes.game.enums.TargetType;
import io.github.stelitop.battledudes.game.moves.script.desugarer.NullC;
import io.github.stelitop.battledudes.game.moves.script.interpreter.*;
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
    private TargetType targetType = TargetType.None;

    public BattleMove() {

    }

    public void use(Battle battle, Player user, Player opponent, BattleActions ba, Object target) {
        var code = getTrigger(MoveTrigger.OnUse);
        Interpreter.MoveData moveData = new Interpreter.MoveData(
                this,
                battle,
                user,
                opponent,
                new DudeRefV(user.getSelectedDude()),
                transformObjectToValue(target),
                ba
        );
        Interpreter.interpNew(code, moveData);
    }

    // TODO: Move this to a more general place, e.g. some utils class
    public Value transformObjectToValue(Object o) {
        if (o == null) return new NullV();
        if (o instanceof Integer n) return new NumberV(n);
        if (o instanceof String s) return new StringV(s);
        if (o instanceof Boolean b) return new BoolV(b);
        if (o instanceof ElementalType et) return new ElementalTypeV(et);
        if (o instanceof MoveStyle st) return new MoveStyleV(st);
        if (o instanceof BattleDude bd) return new DudeRefV(bd);
        throw new RuntimeException("Could not transform object " + o + " into a DudeScript value!");
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

    public TargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(TargetType targetType) {
        this.targetType = targetType;
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

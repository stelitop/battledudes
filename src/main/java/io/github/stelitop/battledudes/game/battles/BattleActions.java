package io.github.stelitop.battledudes.game.battles;

import io.github.stelitop.battledudes.game.enums.ElementalType;
import io.github.stelitop.battledudes.game.enums.StatusEffect;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class BattleActions {

    public int dealDamage(Battle battle, BattleDude user, BattleDude target, int damage, ElementalType type) {
        target.setHealth(target.getHealth() - damage);
        return damage;
    }
    public int applyStatus(Battle battle, BattleDude user, BattleDude target, StatusEffect effect, int turns) {
        return target.applyStatus(effect, turns);
    }

    public int changeOffense(Battle battle, BattleDude user, BattleDude target, int amount) {
        target.setOffense(target.getOffense() + amount);
        if (target.getOffense() > 6) target.setOffense(6);
        if (target.getOffense() < -6) target.setOffense(-6);
        return target.getOffense();
    }

    public int changeDefense(Battle battle, BattleDude user, BattleDude target, int amount) {
        target.setDefense(target.getOffense() + amount);
        if (target.getDefense() > 6) target.setDefense(6);
        if (target.getDefense() < -6) target.setDefense(-6);
        return target.getDefense();
    }
    public int changeSpeed(Battle battle, BattleDude user, BattleDude target, int amount) {
        target.setSpeed(target.getSpeed() + amount);
        if (target.getSpeed() > 6) target.setSpeed(6);
        if (target.getSpeed() < -6) target.setSpeed(-6);
        return target.getSpeed();
    }
}

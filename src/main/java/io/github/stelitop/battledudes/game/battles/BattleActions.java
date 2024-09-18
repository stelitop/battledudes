package io.github.stelitop.battledudes.game.battles;

import io.github.stelitop.battledudes.game.enums.ElementalType;
import io.github.stelitop.battledudes.game.enums.StatusEffect;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class BattleActions {

    public int dealDamage(Battle battle, BattleDude self, BattleDude opponent, int damage, ElementalType type) {
        opponent.setHealth(opponent.getHealth() - damage);
        return damage;
    }
    public int applyStatusSelf(Battle battle, BattleDude self, BattleDude opponent, StatusEffect effect, int turns) {
        return self.applyStatus(effect, turns);
    }
    public int applyStatusOpponent(Battle battle, BattleDude self, BattleDude opponent, StatusEffect effect, int turns) {
        return opponent.applyStatus(effect, turns);
    }
    public int changeOffenseSelf(Battle battle, BattleDude self, BattleDude opponent, int amount) {
        self.setOffense(self.getOffense() + amount);
        if (self.getOffense() > 6) self.setOffense(6);
        if (self.getOffense() < -6) self.setOffense(-6);
        return self.getOffense();
    }
    public int changeOffenseOpponent(Battle battle, BattleDude self, BattleDude opponent, int amount) {
        opponent.setOffense(opponent.getOffense() + amount);
        if (opponent.getOffense() > 6) opponent.setOffense(6);
        if (opponent.getOffense() < -6) opponent.setOffense(-6);
        return opponent.getOffense();
    }
    public int changeDefenseSelf(Battle battle, BattleDude self, BattleDude opponent, int amount) {
        self.setDefense(self.getOffense() + amount);
        if (self.getDefense() > 6) self.setDefense(6);
        if (self.getDefense() < -6) self.setDefense(-6);
        return self.getDefense();
    }
    public int changeDefenseOpponent(Battle battle, BattleDude self, BattleDude opponent, int amount) {
        opponent.setDefense(opponent.getOffense() + amount);
        if (opponent.getDefense() > 6) opponent.setDefense(6);
        if (opponent.getDefense() < -6) opponent.setDefense(-6);
        return opponent.getDefense();
    }
    public int changeSpeedSelf(Battle battle, BattleDude self, BattleDude opponent, int amount) {
        self.setSpeed(self.getSpeed() + amount);
        if (self.getSpeed() > 6) self.setSpeed(6);
        if (self.getSpeed() < -6) self.setSpeed(-6);
        return self.getSpeed();
    }
    public int changeSpeedOpponent(Battle battle, BattleDude self, BattleDude opponent, int amount) {
        opponent.setSpeed(opponent.getSpeed() + amount);
        if (opponent.getSpeed() > 6) opponent.setSpeed(6);
        if (opponent.getSpeed() < -6) opponent.setSpeed(-6);
        return opponent.getSpeed();
    }
}

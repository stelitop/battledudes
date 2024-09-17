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

    public int applyStatusOpponent(Battle battle, BattleDude self, BattleDude opponent, StatusEffect effect, int turns) {
        return opponent.applyStatus(effect, turns);
    }
}

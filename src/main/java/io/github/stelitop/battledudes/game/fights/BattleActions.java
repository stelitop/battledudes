package io.github.stelitop.battledudes.game.fights;

import io.github.stelitop.battledudes.game.enums.ElementalType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class BattleActions {

    public int dealDamage(Battle battle, BattleDude attacker, BattleDude target, int damage, ElementalType type) {
        target.setHealth(target.getHealth() - damage);
        return damage;
    }
}

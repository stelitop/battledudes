package io.github.stelitop.battledudes.game.battles;

import io.github.stelitop.battledudes.game.enums.ElementalType;
import io.github.stelitop.battledudes.game.enums.StatusEffect;

public class EmptyBattleActions extends BattleActions {
    @Override
    public int dealDamage(Battle battle, BattleDude user, BattleDude target, int damage, ElementalType type) {
        return 0;
    }

    @Override
    public int applyStatus(Battle battle, BattleDude user, BattleDude target, StatusEffect effect, int turns) {
        return 0;
    }

    @Override
    public int changeOffense(Battle battle, BattleDude user, BattleDude target, int amount) {
        return 0;
    }

    @Override
    public int changeDefense(Battle battle, BattleDude user, BattleDude target, int amount) {
        return 0;
    }

    @Override
    public int changeSpeed(Battle battle, BattleDude user, BattleDude target, int amount) {
        return 0;
    }
}

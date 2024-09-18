package io.github.stelitop.battledudes.game.battles;

import io.github.stelitop.battledudes.game.enums.ElementalType;
import io.github.stelitop.battledudes.game.enums.StatusEffect;

public class EmptyBattleActions extends BattleActions {
    @Override
    public int dealDamage(Battle battle, BattleDude self, BattleDude opponent, int damage, ElementalType type) {
        return 0;
    }

    @Override
    public int applyStatusSelf(Battle battle, BattleDude self, BattleDude opponent, StatusEffect effect, int turns) {
        return 0;
    }

    @Override
    public int applyStatusOpponent(Battle battle, BattleDude self, BattleDude opponent, StatusEffect effect, int turns) {
        return 0;
    }

    @Override
    public int changeOffenseSelf(Battle battle, BattleDude self, BattleDude opponent, int amount) {
        return 0;
    }

    @Override
    public int changeOffenseOpponent(Battle battle, BattleDude self, BattleDude opponent, int amount) {
        return 0;
    }

    @Override
    public int changeDefenseSelf(Battle battle, BattleDude self, BattleDude opponent, int amount) {
        return 0;
    }

    @Override
    public int changeDefenseOpponent(Battle battle, BattleDude self, BattleDude opponent, int amount) {
        return 0;
    }

    @Override
    public int changeSpeedSelf(Battle battle, BattleDude self, BattleDude opponent, int amount) {
        return 0;
    }

    @Override
    public int changeSpeedOpponent(Battle battle, BattleDude self, BattleDude opponent, int amount) {
        return 0;
    }
}

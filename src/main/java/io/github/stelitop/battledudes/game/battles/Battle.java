package io.github.stelitop.battledudes.game.battles;

import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import java.util.concurrent.ThreadLocalRandom;

public class Battle {
    @Getter

    private Player player1, player2;
    private BattleActions ba;

    public Battle(Player player1, Player player2, BattleActions ba) {
        this.player1 = player1;
        this.player2 = player2;
        this.ba = ba;
    }

    public void executeMoves(Object targetP1, Object targetP2) {
        if (player1.getSelectedMove() == null || player2.getSelectedDude() == null) return;
        Player fst, snd;
        Object targetFst, targetSnd;
        if (!swapPlayers()) {
            fst = player1;
            snd = player2;
            targetFst = targetP1;
            targetSnd = targetP2;
        } else {
            fst = player2;
            snd = player1;
            targetFst = targetP2;
            targetSnd = targetP1;
        }
        fst.getSelectedMove().use(this, fst, snd, ba, targetFst);
        if (!fst.getSelectedDude().isAlive() || !snd.getSelectedDude().isAlive()) return;
        snd.getSelectedMove().use(this, snd, fst, ba, targetSnd);
    }

    /**
     *
     * @return True if player2 should go first, false if player1 should go first.
     */
    private boolean swapPlayers() {
        if (player1.getSelectedDude().getSpeed() == player2.getSelectedDude().getSpeed()) {
            if (ThreadLocalRandom.current().nextBoolean()) {
                return false;
            } else {
                return true;
            }
        } else {
            if (player1.getSelectedDude().getSpeed() > player2.getSelectedDude().getSpeed()) {
                return false;
            } else {
                return true;
            }
        }
    }
    private Pair<Player, Player> getPlayerOrder() {
        if (player1.getSelectedDude().getSpeed() == player2.getSelectedDude().getSpeed()) {
            if (ThreadLocalRandom.current().nextBoolean()) {
                return Pair.of(player1, player2);
            } else {
                return Pair.of(player2, player1);
            }
        } else {
            if (player1.getSelectedDude().getSpeed() > player2.getSelectedDude().getSpeed()) {
                return Pair.of(player1, player2);
            } else {
                return Pair.of(player2, player1);
            }
        }
    }
}

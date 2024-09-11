package io.github.stelitop.battledudes.game.fights;

import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Battle {

    private Player player1, player2;

    public Battle(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public void executeMoves() {
        if (player1.getSelectedMove() == null || player2.getSelectedDude() == null) return;
        var orderedPlayers = getPlayerOrder();
        Player fst = orderedPlayers.getLeft(), snd = orderedPlayers.getRight();
        fst.getSelectedMove().use(this, fst, snd);
        if (!fst.getSelectedDude().isAlive() || !snd.getSelectedDude().isAlive()) return;
        snd.getSelectedMove().use(this, snd, fst);

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

package io.github.stelitop.battledudes.game.fights;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
public class Player {

    private long discordUserId;
    /**
     * The team of dudes.
     */
    private List<BattleDude> dudes;
    /**
     * The dude currently fighting on the field.
     */
    private BattleDude selectedDude;
    /**
     * The move selected to be used in the next round.
     */
    @Setter(value = AccessLevel.NONE)
    private BattleMove selectedMove;

    /**
     * Placeholder, should be created from a Team database object that currently doesn't exist.
     */
    public Player() {

    }

    /**
     * Selects a move for the dude to use in the next round. This action might be unsuccessful
     * if no dude is currently on the field or that the index is out of bounds.
     *
     * @param idx Index of the move.
     * @return True if a move was successfully selected and false otherwise.
     */
    public boolean selectMove(int idx) {
        if (selectedDude == null) return false;
        if (idx < 0 || selectedDude.getMoves().size() <= idx) return false;
        selectedMove = selectedDude.getMoves().get(idx);
        return true;
    }
}

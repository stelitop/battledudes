package io.github.stelitop.battledudes.game.fights;

import io.github.stelitop.battledudes.game.entities.Dude;
import io.github.stelitop.battledudes.game.enums.ElementalType;
import lombok.Data;
import lombok.Singular;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Data
public class BattleDude {

    /**
     * The name of the dude.
     */
    private String name;

    /**
     * The stage of the dude.
     */
    private int stage;

    /**
     * The types of the dude.
     */
    private List<ElementalType> types;

    /**
     * The max health of the dude.
     */
    private int maxHealth;

    /**
     * The current health of the dude.
     */
    private int health;

    /**
     * The speed of the dude.
     */
    private int speed;

    /**
     * The offense of the dude.
     */
    private int offense;

    /**
     * The defense of the dude.
     */
    private int defense;

    /**
     * A url link to the artwork of the dude.
     */
    private String artLink;

    /**
     * The item the dude is holding for the fight.
     */
    private @Nullable BattleItem item;

    /**
     * The moves of the dude.
     */
    @Singular
    private List<BattleMove> moves;


    /**
     * Creates a fight dude instance from the data of a dude from the database.
     *
     * @param dbDude The dude object from the database.
     */
    public BattleDude(Dude dbDude) {
        this.name = dbDude.getName();
        this.maxHealth = this.health = dbDude.getHealth();
        this.offense = dbDude.getOffense();
        this.defense = dbDude.getDefense();
        this.speed = dbDude.getSpeed();
        this.stage = dbDude.getStage();
        this.artLink = dbDude.getArtLink();
        this.types = new ArrayList<>(dbDude.getTypes());
        this.item = null;
        //this.moves = new ArrayList<>(dbDude.getMoves().stream().map(BattleMove::new).toList());
        this.moves = new ArrayList<>();
    }

    public boolean isAlive() {
        return health > 0;
    }
}

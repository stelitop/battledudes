package io.github.stelitop.battledudes.game.battles;

import io.github.stelitop.battledudes.game.entities.Dude;
import io.github.stelitop.battledudes.game.enums.ElementalType;
import io.github.stelitop.battledudes.game.enums.StatusEffect;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BattleTest {


    /*
     *this.name = dbDude.getName();
        this.maxHealth = this.health = dbDude.getHealth();
        this.offense = dbDude.getOffense();
        this.defense = dbDude.getDefense();
        this.speed = dbDude.getSpeed();
        this.stage = dbDude.getStage();
        this.artLink = dbDude.getArtLink();
        this.types = new ArrayList<>(dbDude.getTypes());
        this.item = null;
        this.moves = new ArrayList<>(dbDude.getMoves().stream().map(BattleMove::new).toList());
     */

    @Test
    void testBasicDamageMoves() {
        MoveScriptService mss = new MoveScriptService();
        BattleMove moveP1 = mss.createMove("meta target = \"EnemyBattlefieldDude\"; trigger onUse += {action damage (target, 25);};");
        BattleMove moveP2 = mss.createMove("meta target = \"EnemyBattlefieldDude\"; trigger onUse += {action damage (target, 40);};");

        BattleDude testDudeP1 = new BattleDude(Dude.builder()
                .name("Test Dude P1")
                .health(100)
                .offense(0)
                .defense(0)
                .speed(0)
                .stage(1)
                .artLink("")
                .type(ElementalType.Neutral)
                .build());
        testDudeP1.getMoves().add(moveP1);

        BattleDude testDudeP2 = new BattleDude(Dude.builder()
                .name("Test Dude P2")
                .health(100)
                .offense(0)
                .defense(0)
                .speed(-1)
                .stage(1)
                .artLink("")
                .type(ElementalType.Neutral)
                .build());
        testDudeP2.getMoves().add(moveP2);

        Player p1 = new Player(), p2 = new Player();
        p1.getDudes().add(testDudeP1);
        p2.getDudes().add(testDudeP2);
        p1.selectDude(0);
        p1.selectMove(0);
        p2.selectDude(0);
        p2.selectMove(0);
        Battle battle = new Battle(p1, p2, new BattleActions());
        battle.executeMoves(testDudeP2, testDudeP1);
        assertThat(p1.getSelectedDude().getHealth()).isEqualTo(60);
        assertThat(p2.getSelectedDude().getHealth()).isEqualTo(75);
    }

    @Test
    void testStatusMove() {
        MoveScriptService mss = new MoveScriptService();
        BattleMove moveP1 = mss.createMove("meta target = \"EnemyBattlefieldDude\"; trigger onUse += {action applyStatus (target, \"trapped\", 4);};");
        BattleMove moveP2 = mss.createMove("trigger onUse += {};");

        BattleDude testDudeP1 = new BattleDude(Dude.builder()
                .name("Test Dude P1")
                .health(100)
                .offense(0)
                .defense(0)
                .speed(0)
                .stage(1)
                .artLink("")
                .type(ElementalType.Neutral)
                .build());
        testDudeP1.getMoves().add(moveP1);

        BattleDude testDudeP2 = new BattleDude(Dude.builder()
                .name("Test Dude P2")
                .health(100)
                .offense(0)
                .defense(0)
                .speed(-1)
                .stage(1)
                .artLink("")
                .type(ElementalType.Neutral)
                .build());
        testDudeP2.getMoves().add(moveP2);

        Player p1 = new Player(), p2 = new Player();
        p1.getDudes().add(testDudeP1);
        p2.getDudes().add(testDudeP2);
        p1.selectDude(0);
        p1.selectMove(0);
        p2.selectDude(0);
        p2.selectMove(0);
        Battle battle = new Battle(p1, p2, new BattleActions());
        battle.executeMoves(testDudeP2, null);
        assertThat(p2.getSelectedDude().getStatusEffects()).containsEntry(StatusEffect.Trapped, 4);
    }

    @Test
    void testHealMove() {
        MoveScriptService mss = new MoveScriptService();
        BattleMove moveP1 = mss.createMove("trigger onUse += {action heal (user, 25);};");
        BattleMove moveP2 = mss.createMove("trigger onUse += {};");

        BattleDude testDudeP1 = new BattleDude(Dude.builder()
                .name("Test Dude P1")
                .health(100)
                .offense(0)
                .defense(0)
                .speed(0)
                .stage(1)
                .artLink("")
                .type(ElementalType.Neutral)
                .build());
        testDudeP1.getMoves().add(moveP1);

        BattleDude testDudeP2 = new BattleDude(Dude.builder()
                .name("Test Dude P2")
                .health(100)
                .offense(0)
                .defense(0)
                .speed(-1)
                .stage(1)
                .artLink("")
                .type(ElementalType.Neutral)
                .build());
        testDudeP2.getMoves().add(moveP2);
        testDudeP1.setHealth(40);

        Player p1 = new Player(), p2 = new Player();
        p1.getDudes().add(testDudeP1);
        p2.getDudes().add(testDudeP2);
        p1.selectDude(0);
        p1.selectMove(0);
        p2.selectDude(0);
        p2.selectMove(0);
        Battle battle = new Battle(p1, p2, new BattleActions());
        battle.executeMoves(null, null);
        assertThat(p1.getSelectedDude().getHealth()).isEqualTo(65);
    }
}
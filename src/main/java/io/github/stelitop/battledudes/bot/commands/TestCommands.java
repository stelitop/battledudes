package io.github.stelitop.battledudes.bot.commands;

import discord4j.core.spec.EmbedCreateFields;
import discord4j.core.spec.EmbedCreateSpec;
import io.github.stelitop.battledudes.bot.ui.BattleUI;
import io.github.stelitop.battledudes.game.battles.*;
import io.github.stelitop.battledudes.game.entities.Dude;
import io.github.stelitop.battledudes.game.enums.ElementalType;
import io.github.stelitop.mad4j.DiscordEventsComponent;
import io.github.stelitop.mad4j.commands.SlashCommand;
import org.springframework.beans.factory.annotation.Autowired;

@DiscordEventsComponent
public class TestCommands {

    @Autowired
    private MoveScriptService mss;
    @Autowired
    private BattleActions battleActions;
    @Autowired
    private BattleUI battleUI;

    @SlashCommand(name = "test battleuiexample", description = "Battle UI Example")
    public EmbedCreateSpec battleUiExample() {
        BattleMove moveP1 = mss.createMove("");
        BattleMove moveP2 = mss.createMove("");

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
        p1.setName("Player 1");
        p2.setName("Player 2");
        p1.getDudes().add(testDudeP1);
        p2.getDudes().add(testDudeP2);
        p1.selectDude(0);
        p1.selectMove(0);
        p2.selectDude(0);
        p2.selectMove(0);

        Battle battle = new Battle(p1, p2, battleActions);
        return battleUI.create(battle);
    }
}

package io.github.stelitop.battledudes.bot.ui;

import discord4j.core.object.Embed;
import discord4j.core.spec.EmbedCreateFields;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import io.github.stelitop.battledudes.game.battles.Battle;
import io.github.stelitop.battledudes.game.battles.BattleDude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class BattleUI {
    @Autowired
    private UIUtils uiUtils;
    public EmbedCreateSpec create(Battle battle) {
        return EmbedCreateSpec.builder()
                .title(battle.getPlayer1().getName() + " vs " + battle.getPlayer2().getName())
                .addField(battle.getPlayer1().getName(), "\u200B", true)
                .addField("\u200B", "\u200B", true)
                .addField(battle.getPlayer2().getName(), "\u200B", true)
                .addField(getDudeField(battle.getPlayer1().getSelectedDude()))
                .addField("Weather", "(no weather)", true)
                .addField(getDudeField(battle.getPlayer2().getSelectedDude()))
                .addField(battle.getPlayer1().getName() + "'s Half", "Half Info", true)
                .addField("Terrain", "Terrain Info", true)
                .addField(battle.getPlayer2().getName() + "'s Half", "Half Info", true)
                .color(Color.of(52, 222, 235))
                .build();
    }

    private EmbedCreateFields.Field getDudeField(BattleDude dude) {
        String description = "";
        description += "Types: " + dude.getTypes().stream().map(uiUtils::elementalTypeToString).collect(Collectors.joining());
        description += "\n:heart: - " + dude.getHealth() + "/" + dude.getMaxHealth();
        description += "\n:alarm_clock: - " + uiUtils.speedToString(dude.getSpeed());
        description += "\n:crossed_swords: - " + uiUtils.offenseToString(dude.getOffense());
        description += "\n:shield: - " + uiUtils.defenseToString(dude.getDefense());
        description += "\nResistance: xxx";
        description += "\nWeakness: xxx";
        description += "\nStatuses: xxx";
        return EmbedCreateFields.Field.of(dude.getName(), description, true);
    }
}

package io.github.stelitop.battledudes.bot.commands;

import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import io.github.stelitop.battledudes.database.services.DudeService;
import io.github.stelitop.battledudes.game.entities.Dude;
import io.github.stelitop.battledudes.game.enums.DudeStat;
import io.github.stelitop.mad4j.DiscordEventsComponent;
import io.github.stelitop.mad4j.commands.CommandParam;
import io.github.stelitop.mad4j.commands.SlashCommand;
import io.github.stelitop.mad4j.interactions.EventResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.stream.Collectors;

@DiscordEventsComponent
public class InfoCommands {

    @Autowired
    DudeService dudeService;

    @SlashCommand(
            name = "dude info",
            description = "Displays information about a dude."
    )
    public EventResponse dudeInfo(
            @CommandParam(name = "name", description = "The name of the dude")
            String dudeName
    ) {
        Optional<Dude> dudeOpt = dudeService.getDude(dudeName);
        if (dudeOpt.isEmpty()) return EventResponse.createPlaintext("There is no such dude!").ephemeral();
        Dude dude = dudeOpt.get();

        var embed = EmbedCreateSpec.builder()
                .title(dude.getFormattedId() + " - " + dude.getName())
                .thumbnail(dude.getArtLink())
                .color(Color.BLUE)
                .addField("Collection Info",
                        "Rarity: " + dude.getRarity() +
                                "\nTotal Collected: " + dude.getUsersThatOwn().size(),
                        false)
                .addField("Evolution Info",
                        "Stage: " + dude.getStage() +
                                "\nEvolves From: " + (dude.getPreviousEvolutions() != null && !dude.getPreviousEvolutions().isEmpty() ?
                                String.join(", ", dude.getPreviousEvolutions()) : "N/A") +
                                "\nEvolves Into: " + (dude.getNextEvolutions() != null && !dude.getNextEvolutions().isEmpty() ?
                                String.join(", ", dude.getNextEvolutions()) : "N/A"),
                        false)
                .addField("Type Info",
                        dude.getTypes().stream()
                                .map(type -> type.toString().toUpperCase())
                                .collect(Collectors.joining(" - ")),
                        true)
                .addField("Statistics",
                        " Health: " + dude.getHealth() +
                                "\n" + " Speed: " + dude.getSpeed() +
                                "\n" + " Offense: " + dude.getOffense() +
                                "\n" + " Defense: " + dude.getDefense(),
                        true)
                .footer("Art by " + dude.getArtistName(), null);

        return EventResponse.createEmbed(embed.build());
    }
}

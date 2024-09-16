package io.github.stelitop.battledudes.game.fights;

import io.github.stelitop.battledudes.game.enums.ElementalType;
import io.github.stelitop.battledudes.game.enums.MoveStyle;
import io.github.stelitop.battledudes.game.moves.script.desugarer.MoveStyleC;
import io.github.stelitop.battledudes.game.moves.script.desugarer.NullC;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MoveScriptServiceTest {

    MoveScriptService mss;

    @BeforeEach
    void setUp() {
        mss = new MoveScriptService();
    }

    @Test
    void createMoveBasicWhack() {
        var move = mss.createMove("""
        meta name = "Whack";
        meta text = "Deal 40 damage.";
        meta element = neutral;
        meta style = melee;
        meta charges = 20;
        meta accuracy = 100;
        
        trigger onUse += {
            action damage 40
        }
        """);

        assertThat(move.getName()).isEqualTo("Whack");
        assertThat(move.getText()).isEqualTo("Deal 40 damage.");
        assertThat(move.getElementalType()).isEqualTo(ElementalType.Neutral);
        assertThat(move.getMoveStyle()).isEqualTo(MoveStyle.Melee);
        assertThat(move.getCharges()).isEqualTo(20);
        assertThat(move.getAccuracy()).isEqualTo(100);
        assertThat(move.getTrigger(MoveTrigger.OnUse)).isNotOfAnyClassIn(NullC.class);
    }

    @Test
    void verifyAllDudeScriptFilesCanBeLoaded() throws URISyntaxException, FileNotFoundException {
        List<String> wrongMsgs = new ArrayList<>();
        for (String el : List.of("neutral", "fire", "water", "earth", "air", "magic", "decay", "nature", "tech")) {
            File[] files = new File(this.getClass().getClassLoader().getResource("./game/moves/" + el).toURI()).listFiles();
            String incorrectDudeScripts = Arrays.stream(files)
                    .filter(x -> x.getName().endsWith(".ds"))
                    .map(x -> {
                        try {
                            String code = FileUtils.readFileToString(x, "utf-8");
                            mss.createMove(code);
                            return "";
                        } catch (Exception e) {
                            return "- " + x.getName() + ": " + e.getMessage();
                        }
                    }).filter(x -> !x.isBlank())
                    .collect(Collectors.joining("\n"));
            if (incorrectDudeScripts.isBlank()) continue;
            wrongMsgs.add(incorrectDudeScripts);
        }

        if (wrongMsgs.isEmpty()) return;
        fail("The following files could not be interpreted correctly:\n" + String.join("\n", wrongMsgs));
    }
}
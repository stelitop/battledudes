package io.github.stelitop.battledudes.bot.ui;

import io.github.stelitop.battledudes.game.enums.ElementalType;
import org.springframework.stereotype.Service;

@Service
public class UIUtils {

    public String elementalTypeToString(ElementalType elType) {
        return switch (elType) {
            case Neutral -> ":+1:";
            case Earth -> ":+1:";
            case Water -> ":+1:";
            case Air -> ":+1:";
            case Fire -> ":+1:";
            case Nature -> ":+1:";
            case Decay -> ":+1:";
            case Magic -> ":+1:";
            case Tech -> ":+1:";
            case All -> ":+1:";
        };
    }

    private String getStatPrefix(int statSize, String positive, String negative) {
        if (statSize == 0) return "Standard";
        return switch (Math.abs(statSize)) {
            case 1 -> "";
            case 2 -> "Very";
            case 3 -> "Super";
            case 4 -> "Hyper";
            case 5 -> "Ultra";
            default -> "MAX";
        } + (statSize > 0 ? positive : negative);
    }
    public String speedToString(int speed) {
        return getStatPrefix(speed, "Fast", "Slow");
    }

    public String offenseToString(int offense) {
        return getStatPrefix(offense, "Strong", "Weak");
    }

    public String defenseToString(int defense) {
        return getStatPrefix(defense, "Resistant", "Vulnerable");
    }
}

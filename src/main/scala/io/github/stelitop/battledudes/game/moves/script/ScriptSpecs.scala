package io.github.stelitop.battledudes.game.moves.script

import io.github.stelitop.battledudes.game.enums.{ElementalType, MoveStyle, StatusEffect}
import io.github.stelitop.battledudes.game.battles.MoveTrigger

package object ScriptSpecs {
  final var nameChars: Set[Char] = Set('_', 'A', 'a', 'B', 'b', 'C', 'c', 'D', 'd', 'E', 'e', 'F', 'f', 'G', 'g', 'H', 'h', 'I', 'i', 'J', 'j', 'K', 'k', 'L', 'l', 'M', 'm', 'N', 'n', 'O', 'o', 'P', 'p', 'Q', 'q', 'R', 'r', 'S', 's', 'T', 't', 'U', 'u', 'V', 'v', 'W', 'w', 'X', 'x', 'Y', 'y', 'Z', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
  final val operatorChars: Set[Char] = Set('+', '-', '*', '/', '=', '>', '<', '!', '&', '|')
  final val digitChars: Set[Char] = Set('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
  final val specialChars: Set[Char] = Set('(', ')', '.', ',', ';', '{', '}')
  final val arithOps: Set[String] = Set("+", "-", "*", "/")
  final val boolOps: Set[String] = Set("<", "<=", ">=", ">", "==", "!=", "&&", "||")
  final val elementalTypes: Map[String, ElementalType] = Map(
    ("neutral", ElementalType.Neutral),
    ("fire", ElementalType.Fire),
    ("water", ElementalType.Water),
    ("earth", ElementalType.Earth),
    ("air", ElementalType.Air),
    ("magic", ElementalType.Magic),
    ("nature", ElementalType.Nature),
    ("tech", ElementalType.Tech),
    ("decay", ElementalType.Decay),
  )
  final val moveTriggers: Map[String, MoveTrigger] = Map(
    ("onUse", MoveTrigger.OnUse),
  )
  final val moveStyles: Map[String, MoveStyle] = Map(
    ("melee", MoveStyle.Melee),
    ("ranged", MoveStyle.Ranged),
    ("special", MoveStyle.Special),
  )
  final val keywords: Set[String] = Set("var", "true", "false", "if", "else", "then", "end", "action", "trigger", "meta", "random")
    .concat(elementalTypes.keys)
    .concat(moveStyles.keys)
    //.concat(moveTriggers.keys)

  final val statusEffects: Map[String, StatusEffect] = Map(
    ("burning", StatusEffect.Burning),
    ("perishing", StatusEffect.Perishing),
    ("trapped", StatusEffect.Trapped),
    ("exhausted", StatusEffect.Exhausted),
    ("poisoned", StatusEffect.Poisoned),
    ("sheltered", StatusEffect.Sheltered),
    ("marked", StatusEffect.Marked),
    ("blinded", StatusEffect.Blinded),
    ("accurate", StatusEffect.Accurate),
    ("evasive", StatusEffect.Evasive),
  )
}







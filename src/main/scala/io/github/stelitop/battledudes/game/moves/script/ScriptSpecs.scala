package io.github.stelitop.battledudes.game.moves.script

import io.github.stelitop.battledudes.game.enums.{ElementalType, MoveStyle, StatusEffect, TargetType}
import io.github.stelitop.battledudes.game.battles.MoveTrigger

package object ScriptSpecs {
  final var nameChars: Set[Char] = Set('_', 'A', 'a', 'B', 'b', 'C', 'c', 'D', 'd', 'E', 'e', 'F', 'f', 'G', 'g', 'H', 'h', 'I', 'i', 'J', 'j', 'K', 'k', 'L', 'l', 'M', 'm', 'N', 'n', 'O', 'o', 'P', 'p', 'Q', 'q', 'R', 'r', 'S', 's', 'T', 't', 'U', 'u', 'V', 'v', 'W', 'w', 'X', 'x', 'Y', 'y', 'Z', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
  final val operatorChars: Set[Char] = Set('+', '-', '*', '/', '=', '>', '<', '!', '&', '|')
  final val digitChars: Set[Char] = Set('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
  final val specialChars: Set[Char] = Set('(', ')', '.', ',', ';', '{', '}')
  final val arithOps: Set[String] = Set("+", "-", "*", "/")
  final val boolOps: Set[String] = Set("<", "<=", ">=", ">", "==", "!=", "&&", "||")
  final val elementalTypes: Map[String, ElementalType] = ElementalType.values().toList.map(x => (x.toString.toLowerCase, x)).toMap
  final val moveTriggers: Map[String, MoveTrigger] = Map(
    ("onUse", MoveTrigger.OnUse),
  )
  final val moveStyles: Map[String, MoveStyle] = MoveStyle.values().toList.map(x => (x.toString.toLowerCase, x)).toMap
  final val keywords: Set[String] = Set("var", "true", "false", "if", "else", "then", "end", "action", "trigger", "meta", "random", "repeat")
    .concat(elementalTypes.keys)
    .concat(moveStyles.keys)
    //.concat(moveTriggers.keys)

  final val statusEffects: Map[String, StatusEffect] = StatusEffect.values().map(x => (x.toString.toLowerCase, x)).toMap
  final val targetTypes: Map[String, TargetType] = TargetType.values().map(x => (x.toString, x)).toMap
}







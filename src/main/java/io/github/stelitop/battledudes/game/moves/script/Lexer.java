package io.github.stelitop.battledudes.game.moves.script;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.util.*;

//@Component
//public class Lexer {
//
//    private Set<String> keywords = Set.of("var");
//    private Set<Character> nameChars;
//    private Set<Character> operatorChars = Set.of('+', '-', '*', '/', '=', '>', '<');
//    private Set<Character> digitChars = Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
//    private Set<Character> specialChars = Set.of('(', ')', '.', ',', ';');
//    public Lexer() {
//        initializeCharsets();
//    }
//
//    public enum TokenType {
//        Number,
//        Operator,
//        String, // text surrounded by quotation marks
//        Name, // text not surrounded by quotation marks
//        SpecialChar,
//        Keyword,
//    }
//
//    private void initializeCharsets() {
//        nameChars = new HashSet<>();
//        for (char c = 'a'; c <= 'z'; c++) {
//            nameChars.add(c);
//        }
//        for (char c = 'A'; c <= 'Z'; c++) {
//            nameChars.add(c);
//        }
//        nameChars.add('_');
//        nameChars.addAll(List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));
//    }
//
//    public LinkedList<Pair<String, TokenType>> split(String line) throws ScriptFormatException {
//        LinkedList<Pair<String, TokenType>> parts = new LinkedList<>();
//        for (int i = 0; i < line.length(); i++) {
//            char c = line.charAt(i);
//            StringBuilder s = new StringBuilder();
//            if (specialChars.contains(c)) {
//                parts.add(Pair.of(String.valueOf(c), TokenType.SpecialChar));
//            } else if (c == '-' && i != line.length() - 1 && digitChars.contains(line.charAt(i+1))) {
//                s.append('-');
//                i++;
//                while (i < line.length() && digitChars.contains(line.charAt(i))) {
//                    s.append(line.charAt(i));
//                    i++;
//                }
//                i--;
//                parts.add(Pair.of(s.toString(), TokenType.Number));
//            } else if (digitChars.contains(c)) {
//                while (i < line.length() && digitChars.contains(line.charAt(i))) {
//                    s.append(line.charAt(i));
//                    i++;
//                }
//                i--;
//                parts.add(Pair.of(s.toString(), TokenType.Number));
//            } else if (operatorChars.contains(c)) {
//                while (i < line.length() && operatorChars.contains(line.charAt(i))) {
//                    s.append(line.charAt(i));
//                    i++;
//                }
//                i--;
//                parts.add(Pair.of(s.toString(), TokenType.Operator));
//            } else if (nameChars.contains(c)) {
//                while (i < line.length() && nameChars.contains(line.charAt(i))) {
//                    s.append(line.charAt(i));
//                    i++;
//                }
//                i--;
//                if (keywords.contains(s.toString())) parts.add(Pair.of(s.toString(), TokenType.Keyword));
//                else parts.add(Pair.of(s.toString(), TokenType.Name));
//            } else if (c == '"') {
//                i++;
//                while (i < line.length() && line.charAt(i) != '"') {
//                    s.append(line.charAt(i));
//                    i++;
//                }
//                if (i == line.length()) throw new ScriptFormatException();
//                parts.add(Pair.of(s.toString(), TokenType.String));
//            }
//        }
//        return parts;
//    }
//}

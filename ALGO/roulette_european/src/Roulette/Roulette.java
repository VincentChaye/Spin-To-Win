package Roulette;

import java.util.Arrays;
import java.util.List;

public class Roulette {
    private static final int MAX_NUMBER = 36;

    public static final List<String> NUMBERS = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36");

    public static final List<String> COLORS = Arrays.asList("red", "black");

    public static final List<String> PARITIES = Arrays.asList("even", "odd");

    public static final List<String> RANGES = Arrays.asList("low", "high");

    public static final List<String> TIERS = Arrays.asList("tiers1", "tiers2", "tiers3");

    public static final List<String> LIGNES = Arrays.asList("ligne1", "ligne2", "ligne3");

    public static final List<String> SIMPLES = Arrays.asList("even", "odd", "red", "black", "low", "high");

    public static final List<Integer> RED_NUMBERS = Arrays.asList(1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36);

    public static final List<Integer> BLACK_NUMBERS = Arrays.asList(2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35);

    public int spin() {
        return (int) (Math.random() * (MAX_NUMBER + 1));
    }
}
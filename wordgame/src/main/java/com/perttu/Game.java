package com.perttu;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;


/*
 * Class for the single wordgame. 
 * 
 */

public class Game {

    HashMap<Integer, ArrayList<Cell>> rows = new HashMap<>();
    String word_to_guess;
    int word_index;
    int row;
    boolean isWonasd = false;

    public Game() throws IOException {
        word_to_guess = Files.readAllLines(Paths.get("words.txt")).get(word_index);
    }

    public Game(String word, int word_index) {
        this.word_index = word_index;
        this.word_to_guess = word;
        this.row = 0;
    }

    public int getsize() {
        return word_to_guess.length();
    }

    public void setCh(String ch, int row, int col) {
        rows.get(row).get(col).setCh(ch);
    }

    public int getrow() {
        return row;
    }

    /*
     * returns false if there are empty cols in the row.
     * Also checks if the characters in the guessed word are in the right place or in the 
     * word at all. 
     */
    public boolean enterPressed() {

        for (var c : rows.get(row)) {
            if (c.getCh().equals("")) {
                return false;
            }
        }

        int index = 0;
        for (var cell : rows.get(row)) {
            String ch = cell.getCh();

            if (word_to_guess.toUpperCase().contains(ch)) {

                rows.get(row).get(index).setInTheWord(true);
            }
            if (String.valueOf(word_to_guess.toUpperCase().charAt(index)).equals(cell.getCh())) {

                rows.get(row).get(index).setInRightPlace(true);
            }
            index++;
        }
        row += 1;
        return true;
    }

    public int getEmptyCol() {
        int i = 0;
        for (var c : rows.get(this.getrow())) {
            if (c.getCh().equals("")) {
                return i;
            }
            i += 1;
        }
        return -1;
    }

    public boolean isWon() {
        StringBuilder sb = new StringBuilder();

        for (var a : rows.get(this.getrow() - 1)) {
            sb.append(a.getCh());
        }
        if (sb.toString().toUpperCase().equals(word_to_guess.toUpperCase())) {
            return true;
        }
        return false;
    }

    /*
     * resets the game. Next game is started with a next word from the words.txt -list.
     */
    public void reset() throws IOException {
        word_index += 1;
        this.isWonasd = false;
        this.row = 0;
        this.word_to_guess = Files.readAllLines(Paths.get("words.txt")).get(word_index);
        rows.clear();
    }
}

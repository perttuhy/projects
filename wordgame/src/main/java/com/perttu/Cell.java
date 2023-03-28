package com.perttu;

/*
 * Class that represents single cell in the wordgame.
 * Each cell has its row and column coordinates and an characther that is placed
 * into it. There are also two boolean operators that determine if the character
 * is in the word to be guessed at all. Or that it the character in the right place.
 */
public class Cell {
    int row;
    int col;
    String ch;
    Boolean inTheWord;
    Boolean inRightPlace;

    public Cell(int i, int y, String ch) {
        this.row = i;
        this.col = y;
        this.ch = ch;
        this.inRightPlace = false;
        this.inTheWord = false;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    public Boolean getInTheWord() {
        return inTheWord;
    }

    public void setInTheWord(Boolean inTheWord) {
        this.inTheWord = inTheWord;
    }

    public Boolean getInRightPlace() {
        return inRightPlace;
    }

    public void setInRightPlace(Boolean inRightPlace) {
        this.inRightPlace = inRightPlace;
    }

}
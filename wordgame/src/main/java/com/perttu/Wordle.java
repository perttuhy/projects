package com.perttu;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

/*
 * Implements the graphical interface of the game
 */
public class Wordle extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Game wordlegame = new Game();
        var group = new GridPane();
        Scene scene = new Scene(group);
        stage.setScene(scene);

        HBox hBox = new HBox(10);

        Button startBtn = new Button("Start new game");
        startBtn.setId("newGameBtn");
        hBox.getChildren().add(startBtn);

        Label infoBox = new Label();
        infoBox.setId("infoBox");
        hBox.getChildren().add(infoBox);

        group.add(hBox, 0, 0);

        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);

        for (int i = 0; i < 6; i++) {
            ArrayList<Cell> cels = new ArrayList<>();

            for (int y = 0; y < wordlegame.getsize(); y++) {
                Button wordbox = new Button("");
                wordbox.setPrefSize(50, 50);
                wordbox.setId(String.format("%s_%s", i, y));

                wordbox.setStyle("-fx-background-color:WHITE");
                cels.add(new Cell(i, y, ""));
                grid.add(wordbox, y, i);
            }
            wordlegame.rows.put(i, cels);
        }

        for (var a : wordlegame.rows.values()) {
            for (var b : a) {
                System.out.print(b.ch);
            }
        }
        group.add(grid, 0, 1);

        EventHandler<KeyEvent> myEvent = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    
                    if (wordlegame.enterPressed()) {
                        if (wordlegame.getrow() > 5) {
                            infoBox.setText("Game over, you lost!");

                        }
                        for (var i : grid.getChildren()) {
                            int row = GridPane.getRowIndex(i);
                            int col = GridPane.getColumnIndex(i);
                            Button b = (Button) i;
                            if (wordlegame.rows.get(row).get(col).inTheWord) {
                                b.setStyle("-fx-background-color:ORANGE");
                            }
                            if (wordlegame.rows.get(row).get(col).inRightPlace) {
                                b.setStyle("-fx-background-color:GREEN");
                            }
                            if (wordlegame.rows.get(row).get(col).inTheWord == false
                                    && row == wordlegame.getrow() - 1) {
                                b.setStyle("-fx-background-color:GREY");
                            }
                        }
                        if (wordlegame.isWon()) {
                            infoBox.setText("Congratulations, you won!");
                            wordlegame.isWonasd = true;

                        }
                    } else {
                        infoBox.setText("Give a complete word before pressing Enter!");
                    }
                }

                else if (event.getCode() == KeyCode.BACK_SPACE) {
                    for (var i : grid.getChildren()) {
                        Button b = (Button) i;
                        int col = wordlegame.getEmptyCol() - 1;
                        if (col == -2) {
                            col = wordlegame.word_to_guess.length() - 1;
                        }
                        int row = wordlegame.getrow();
                        if (GridPane.getColumnIndex(i) == col
                                && GridPane.getRowIndex(i) == row) {
                            String empty = "";
                            b.setText(empty);
                            wordlegame.setCh(empty, row, col);
                            break;
                        }
                    }
                }
            }
        };

        group.addEventFilter(KeyEvent.KEY_PRESSED, myEvent);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {

                if (event.getCode() != KeyCode.ENTER) {
                    infoBox.setText("");
                }

                for (var i : grid.getChildren()) {

                    Button b = (Button) i;
                    int col = wordlegame.getEmptyCol();
                    int row = wordlegame.getrow();

                    if (GridPane.getColumnIndex(i) == col && GridPane.getRowIndex(i) == row
                            && col != -1 && event.getCode() != KeyCode.BACK_SPACE && !wordlegame.isWonasd) {
                        b.setText(event.getCode().toString());

                        wordlegame.setCh(event.getCode().toString(), row, col);
                        break;
                    }
                }
            }
        });

        startBtn.setOnMousePressed(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                System.out.println("alusta");
                try {

                    wordlegame.reset();

                    grid.getChildren().clear();
                    for (int i = 0; i < 6; i++) {
                        ArrayList<Cell> cels = new ArrayList<>();

                        for (int y = 0; y < wordlegame.getsize(); y++) {
                            Button wordbox = new Button("");
                            wordbox.setPrefSize(50, 50);
                            wordbox.setId(String.format("%s_%s", i, y));

                            wordbox.setStyle("-fx-background-color:WHITE");
                            cels.add(new Cell(i, y, ""));
                            grid.add(wordbox, y, i);
                        }
                        wordlegame.rows.put(i, cels);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
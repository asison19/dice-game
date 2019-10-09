/*
Created By: Andrew Sison
Professor: Jonathan Johannsen, and Tony Diaz.
Subject: CS-2450 Programming GUIs.
Dice Game similar to poker for all your gambling needs.
 */

package dicegame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import java.util.Random;

public class DiceGame extends Application {

    final int DICE_AMOUNT = 5;
    BorderPane borderPane;
    HBox diceLayout;
    ImageView[] dice; // array of the dice's images
    boolean[] isHeld; // array of the dice's being held
    final int ROLL_MAX = 3;
    static int rollCount = 0; // roll count is at max, ROLL_MAX
    Button button;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        borderPane = new BorderPane();
        diceLayout = new HBox();
        dice = new ImageView[DICE_AMOUNT];
        button = new Button();

        // add dice to the HBox, diceLayout.
        for(int i = 0;i < DICE_AMOUNT; i++) {
            diceLayout.getChildren().add(dice[i] = new ImageView());

            // set the logic for the "Holding" of dice.
            int captureI = i;
            dice[i].setOnMouseClicked(mouseEvent-> {
                if(rollCount <= ROLL_MAX) {
                    //TODO: set image to opposite/held version dice[i].setImage();
                    isHeld[captureI] = true;
                }
            });
        }

        borderPane.setCenter(diceLayout);
        borderPane.setBottom(button);

        primaryStage.setScene((new Scene(borderPane)));
        primaryStage.show();
    }
}

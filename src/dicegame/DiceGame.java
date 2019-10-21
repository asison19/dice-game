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
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

import java.io.FileNotFoundException;
import java.util.Random;

public class DiceGame extends Application {

    final int DICE_AMOUNT = 5;
    BorderPane borderPane;
    HBox diceLayout; // this will be on the center of the border pane

    // this contains  the button and two labels, score and rollsRemaining
    VBox bottom; // this will be on the bottom of the border pane
    Label overallScoreLabel; // this will be on the top side of the border pane, also contains overall score
    int overallScore = 0;
    Label roundScoreLabel; // contains the score of the current round
    int roundScore = 0;
    Label rollsRemaining;
    //ImageView[] diceViews; // array of the dice's images
    Image[] diceImages;
    Image[] heldImages;
    Die[] dice;
    //boolean[] isHeld; // array of the dice's being held
    final int ROLL_MAX = 3;
    static int rollCount = 0; // roll count is at most, ROLL_MAX
    Button button;

    // image settings
    double imageHeight = 100;
    double imageWidth = 100;
    boolean preserveRatio = false;
    boolean smooth = true;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        borderPane = new BorderPane();
        diceLayout = new HBox();
        overallScoreLabel = new Label("Overall Score: ");
        roundScoreLabel = new Label("Round Score: ");
        rollsRemaining = new Label("Rolls Remaining: ");
        // diceViews = new ImageView[DICE_AMOUNT];
        diceImages = new Image[6];
        heldImages = new Image[6];
        dice = new Die[DICE_AMOUNT];

        button = new Button();

        // set the images for the non held dice
        // index 0 is for the dice value of 1, index 1: value 2, index 2: value 3, etc.
        diceImages[0] = new Image("file:../../DiceImages/Dice1.png", imageHeight, imageWidth, preserveRatio, smooth);
        diceImages[1] = new Image("file:../../DiceImages/Dice2.png", imageHeight, imageWidth, preserveRatio, smooth);
        diceImages[2] = new Image("file:../../DiceImages/Dice3.png", imageHeight, imageWidth, preserveRatio, smooth);
        diceImages[3] = new Image("file:../../DiceImages/Dice4.png", imageHeight, imageWidth, preserveRatio, smooth);
        diceImages[4] = new Image("file:../../DiceImages/Dice5.png", imageHeight, imageWidth, preserveRatio, smooth);
        diceImages[5] = new Image("file:../../DiceImages/Dice6.png", imageHeight, imageWidth, preserveRatio, smooth);

        //set the images for the held dice
        heldImages[0] = new Image("file:../../DiceImages/Dice1Held.png", imageHeight, imageWidth, preserveRatio, smooth);
        heldImages[1] = new Image("file:../../DiceImages/Dice2Held.png", imageHeight, imageWidth, preserveRatio, smooth);
        heldImages[2] = new Image("file:../../DiceImages/Dice3Held.png", imageHeight, imageWidth, preserveRatio, smooth);
        heldImages[3] = new Image("file:../../DiceImages/Dice4Held.png", imageHeight, imageWidth, preserveRatio, smooth);
        heldImages[4] = new Image("file:../../DiceImages/Dice5Held.png", imageHeight, imageWidth, preserveRatio, smooth);
        heldImages[5] = new Image("file:../../DiceImages/Dice6Held.png", imageHeight, imageWidth, preserveRatio, smooth);

        // add dice to the HBox, diceLayout.
        for (int i = 0; i < DICE_AMOUNT; i++) {
            dice[i] = new Die(diceImages,heldImages);
            diceLayout.getChildren().add(dice[i].getView());

            // set the logic for the "Holding" of dice.
            int captureI = i;
            dice[i].getView().setOnMouseClicked(mouseEvent -> {
                if (rollCount <= ROLL_MAX) {
                    dice[captureI].setHeld(!dice[captureI].getIsHeld());
                    //isHeld[captureI] = true;
                }
            });
        }

        borderPane.setCenter(diceLayout);
        borderPane.setBottom(button);

        primaryStage.setScene((new Scene(borderPane)));
        primaryStage.show();
    }


}

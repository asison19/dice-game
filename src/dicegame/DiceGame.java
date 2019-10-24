/*
Created By: Andrew Sison
Professor: Jonathan Johannsen, and Tony Diaz.
Subject: CS-2450 Programming GUIs.
Dice Game similar to poker for all your gambling needs.
 */

package dicegame;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.HashSet;
import java.util.Set;

public class DiceGame extends Application {

    private final int DICE_AMOUNT = 5;
    private BorderPane borderPane;

    //center, top and bottom of border pane
    private HBox diceLayout; // this will be on the center of the border pane
    // this contains  the button and two labels, score and rollsRemaining
    private VBox top;
    private VBox bottom; // this will be on the bottom of the border pane

    private Label overallScoreLabel; // this will be on the top side of the border pane, also contains overall score
    private String overallScoreText;
    private int overallScore = 0;
    private Label roundScoreLabel; // contains the score of the current round
    private String roundScoreText;
    private int roundScore = 0;
    private Label rollsRemainingLabel;
    private String rollsRemainingText;
    private int rollsRemaining;
    //ImageView[] diceViews; // array of the dice's images
    private Image[] diceImages;
    private Image[] heldImages;
    private Die[] dice;
    //boolean[] isHeld; // array of the dice's being held
    private final int ROLL_MAX = 3;
    private static int rollCount = 0; // roll count is at most, ROLL_MAX
    private Button button;
    private String buttonText;

    // Misc. settings
    private double imageHeight = 100;
    private double imageWidth = 100;
    private boolean preserveRatio = false;
    private boolean smooth = true;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


        /* Declaring Objects and Variables */

        AtomicBoolean firstRound = new AtomicBoolean(true);

        // node holders
        borderPane = new BorderPane();
        diceLayout = new HBox();
        top = new VBox();
        bottom = new VBox();

        overallScoreText = "Overall Score: ";
        overallScoreLabel = new Label(overallScoreText);

        roundScoreText = "Round Score: ";
        roundScoreLabel = new Label("Round Score: ");

        // this should also then show the type of hand the player gets at the end of all the rolls
        rollsRemaining = 0;
        rollsRemainingText = "Rolls Remaining: ";
        rollsRemainingLabel = new Label(rollsRemainingText + rollsRemaining);

        diceImages = new Image[6];
        heldImages = new Image[6];
        dice = new Die[DICE_AMOUNT];

        buttonText = "Roll Dice";
        button = new Button("Start Game");

        /* Add nodes to node holders */
        top.getChildren().add(overallScoreLabel);

        bottom.getChildren().add(button);
        bottom.getChildren().add(roundScoreLabel);
        bottom.getChildren().add(rollsRemainingLabel);

        borderPane.setTop(top);
        borderPane.setCenter(diceLayout);
        borderPane.setBottom(bottom);

        // formatting
        borderPane.setPrefSize(600,300);
        borderPane.setMargin(bottom, new Insets(0,0,30,0));
        borderPane.setMargin(top, new Insets(30,0,0,0));

        diceLayout.setSpacing(10);
        diceLayout.setAlignment(Pos.CENTER);

        top.setAlignment(Pos.CENTER);

        bottom.setAlignment(Pos.CENTER);
        bottom.setSpacing(15);

        // overallScoreLabel.setFont(Font.font("Arial", 25)); TODO use CSS for this

        // set the images that will be used for the dice
        try {
            setImages();
        } catch (FileNotFoundException e) {
            System.out.println("Dice images not found " + e);
        }

        // add dice to the HBox, diceLayout.
        for (int i = 0; i < DICE_AMOUNT; i++) {
            dice[i] = new Die(diceImages,heldImages);
            diceLayout.getChildren().add(dice[i].getView());

            // set the logic for the "Holding" of dice.
            int captureI = i;
            dice[i].getView().setOnMouseClicked(mouseEvent -> {
                if (rollCount <= ROLL_MAX) {
                    dice[captureI].setHeld(!dice[captureI].isHeld());
                }
            });
        }

        button.setOnAction(event -> {
            if(rollsRemaining > 0) {
                setAllReady(true);
                rollsRemaining--;
                rollsRemainingLabel.setText(rollsRemainingText + rollsRemaining);

                for (int i = 0; i < DICE_AMOUNT; i++) {
                    if(!dice[i].isHeld())
                        dice[i].roll();
                }

                if(rollsRemaining == 0) {
                    // TODO calculate round score

                    // turn button to reset game
                    button.setText("Play Again");

                }
            } else { // no more rolls remaining

                if(!firstRound.get())
                    roundScore = calculateRoundScore();
                else
                    firstRound.set(false);
                overallScore += roundScore;
                overallScoreLabel.setText(overallScoreText + overallScore);

                // TODO stop the board and reset should get rid of what's held
                resetGame();

                button.setText(buttonText);
                rollsRemaining = ROLL_MAX;
                rollsRemainingLabel.setText(rollsRemainingText + rollsRemaining);
            }

        });

        setAllReady(false);

        primaryStage.setScene((new Scene(borderPane)));
        primaryStage.show();
    }

    private void setAllReady(boolean bool) {
        for(int i =0 ; i < DICE_AMOUNT; i++)
            dice[i].setReady(bool);
    }

    private void resetGame() {
        for(int i = 0; i < DICE_AMOUNT; i++) {
            dice[i].reset();
        }
    }

    // not scalable. only works with 5 dice
    private int calculateRoundScore(){

        Set<Integer> values= new HashSet<>(); // this is used to find pairs
        int[] arr = new int[DICE_AMOUNT]; // this is used to find straight
        for(int i = 0; i < DICE_AMOUNT; i++) {
            values.add(dice[i].getValue());
            arr[i] = dice[i].getValue();
        }
        Arrays.sort(arr);

        /* check for the best hand from highest to lowest */

        // 5 of a kind
        if(values.size() == 1) // if only 1 distinct elements
            return 10;

        // straight
        if(arr[0] == (arr[1] - 1) && arr[1] == (arr[2] - 1) &&
           arr[3] == (arr[4] - 1) && arr[4] == (arr[5] - 1))
            return 8;

        // 4 of a kind, or full house.
        if (values.size() == 2) {
            // find 4 of a kind
            if(arr[2] == arr[0] || arr[2] == arr[4])
                return 7;
            else
                return 6; // if not a 4 of a kind, it must be a full house

        }

        // 3 of a kind, or 2 pair
        if(values.size() == 3) {
            if((arr[0] == arr[1] && arr[1] == arr[2]) ||
               (arr[1] == arr[2] && arr[2] == arr[3]) ||
               (arr[2] == arr[3] && arr[3] == arr[4]))
                return 5;
            else
                return 4;
        }

        // 2 of a kind
        if(values.size() == 4)
            return 1;

        return 0;

    }

    private void setImages() throws FileNotFoundException {
        // set the images for the non held dice
        // index 0 is for the dice value of 1, index 1: value 2, index 2: value 3, etc.
        diceImages[0] = new Image("file:../../DiceImages/Dice1.png",
                imageHeight, imageWidth, preserveRatio, smooth);
        diceImages[1] = new Image("file:../../DiceImages/Dice2.png",
                imageHeight, imageWidth, preserveRatio, smooth);
        diceImages[2] = new Image("file:../../DiceImages/Dice3.png",
                imageHeight, imageWidth, preserveRatio, smooth);
        diceImages[3] = new Image("file:../../DiceImages/Dice4.png",
                imageHeight, imageWidth, preserveRatio, smooth);
        diceImages[4] = new Image("file:../../DiceImages/Dice5.png",
                imageHeight, imageWidth, preserveRatio, smooth);
        diceImages[5] = new Image("file:../../DiceImages/Dice6.png",
                imageHeight, imageWidth, preserveRatio, smooth);

        //set the images for the held dice
        heldImages[0] = new Image("file:../../DiceImages/Dice1Held.png",
                imageHeight, imageWidth, preserveRatio, smooth);
        heldImages[1] = new Image("file:../../DiceImages/Dice2Held.png",
                imageHeight, imageWidth, preserveRatio, smooth);
        heldImages[2] = new Image("file:../../DiceImages/Dice3Held.png",
                imageHeight, imageWidth, preserveRatio, smooth);
        heldImages[3] = new Image("file:../../DiceImages/Dice4Held.png",
                imageHeight, imageWidth, preserveRatio, smooth);
        heldImages[4] = new Image("file:../../DiceImages/Dice5Held.png",
                imageHeight, imageWidth, preserveRatio, smooth);
        heldImages[5] = new Image("file:../../DiceImages/Dice6Held.png",
                imageHeight, imageWidth, preserveRatio, smooth);
    }

}

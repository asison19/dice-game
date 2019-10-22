package dicegame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class Die {
    private boolean isHeld;
    private int value;

    Image[] diceImages;
    Image[] heldImages;
    private ImageView view;
    private Image image;

    public Die(){ }

    public Die(Image[] diceImages, Image[] heldImages) {
        this.diceImages = diceImages;
        this.heldImages = heldImages;

        isHeld = false;
        value = 1;
        image = diceImages[0]; // TODO add default image?
        view = new ImageView(image);
    }

    public void setHeld(boolean held) {
        isHeld = held;
        // boolean for whether the held is true or false,
        // which equates to true or false for either true or false respectively.
        if(!held ? true : false) {
            switch (value) {
                case 1: setImage(diceImages[0]); break;
                case 2: setImage(diceImages[1]); break;
                case 3: setImage(diceImages[2]); break;
                case 4: setImage(diceImages[3]); break;
                case 5: setImage(diceImages[4]); break;
                case 6: setImage(diceImages[5]); break;
                default: System.out.println("Error: at method setHeld of class die, switch 1"); break;
            }
        } else {
            switch (value) {
                case 1: setImage(heldImages[0]); break;
                case 2: setImage(heldImages[1]); break;
                case 3: setImage(heldImages[2]); break;
                case 4: setImage(heldImages[3]); break;
                case 5: setImage(heldImages[4]); break;
                case 6: setImage(heldImages[5]); break;
                default: System.out.println("Error: at method setHeld of class die, switch 2"); break;
            }
        }
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int roll() {
        Random rand = new Random();
        value = rand.nextInt(5) + 1;
        rollImage();
        return value;
    }

    public ImageView getView(){
        return view;
    }

    public boolean getIsHeld(){
        return isHeld;
    }

    private void setImage(Image image) {
        this.image = image;
        view.setImage(image);
    }

    private void rollImage(){
        switch (value) {
            case 1: setImage(diceImages[0]); break;
            case 2: setImage(diceImages[1]); break;
            case 3: setImage(diceImages[2]); break;
            case 4: setImage(diceImages[3]); break;
            case 5: setImage(diceImages[4]); break;
            case 6: setImage(diceImages[5]); break;
            default: System.out.println("Error: at method rollImage of class die, switch"); break;
        }
    }
}

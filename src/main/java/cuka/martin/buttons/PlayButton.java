package cuka.martin.buttons;

import cuka.martin.core.ITicking;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Tlacitko PLAY v Menu Clickable nastavi jeho poziciu a aj zistuje ci je
 * kliknute....
 *
 * @author Martin Čuka
 */
public final class PlayButton extends Clickable implements ITicking {

    private static final int FRAMES = 11;
    private static final int DELAY = 5;
    private static final int WIDTH = 220;
    private static final int HEIGHT = 98;

    private int count = 0;
    private int frame = 0;

    private final Image imgChar;
    private final Image imgPlay;

    /**
     * Vytvori play button na danom mieste o danych rozmeroch
     */
    public PlayButton() {
        super(100, 298, WIDTH, HEIGHT);
        imgChar = new ImageIcon(this.getClass().
                getResource("/mainCharacter.png")).getImage();
        imgPlay = new ImageIcon(this.getClass().
                getResource("/play_button.png")).getImage();
    }

    /**
     * Vykresli playButton na danej pozicii
     *
     * @param g - grafika
     */
    public void paint(Graphics g) {
        int sizeOfImage = 100;
        int dx = frame * sizeOfImage; // nacitavam 0ty riadok obrazku, stlpce sa menia...
        //int dy = 0 * sizeOfImage; nacitava 0ty riadok obrazku... riadok je stale 0....

        g.drawImage(imgChar, getX(), getY(),
                getX() + sizeOfImage, getY() + sizeOfImage,
                dx, 0, dx + sizeOfImage, sizeOfImage, null);

        g.drawImage(imgPlay, getX() + sizeOfImage, getY(),
                sizeOfImage, sizeOfImage, null);

        count++;
        frame = tick(count, frame, DELAY, FRAMES);
    }

    /**
     * Prehadzuje framy na vykreslenie
     *
     * @param count - pocitadlo refreshnuti
     * @param frame - ktory frame aktualne vykresluje
     * @param delay - delay na prehadzovanie framov
     * @param MAX - index maximalneho framu
     * @return vrati frame ktory sa ma vykreslit
     */
    @Override
    public int tick(int count, int frame, int delay, int MAX) {
        int newFrame = frame;
        if (count % delay == 0) {
            newFrame = frame + 1;
            if (frame == MAX - 1) {
                newFrame = 1;
            }
        }
        return newFrame;
    }
}

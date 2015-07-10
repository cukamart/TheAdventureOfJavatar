package cuka.martin.buttons;

import cuka.martin.spell.MeteorRain;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Tlacitko youTube, presmeruje uzivatela na moj YouTube channel kde najde video
 * navod na dany level.
 *
 * @author Martin Čuka
 */
public class YouTubeButton extends Clickable {

    private static final Image imgYouTube;
    private static final int WIDTH = 250;
    private static final int HEIGHT = 120;

    static {
        imgYouTube = new ImageIcon(MeteorRain.class.
                getResource("/youtube.png")).getImage();
    }

    /**
     * Vytvori tlacidlo na zadanej pozicii o danych rozmeroch
     *
     * @param x - pozicia X
     * @param y - pozicia Y
     */
    public YouTubeButton(int x, int y) {
        super(x, y, WIDTH, HEIGHT);
    }

    /**
     * Vykresli tlacidlo
     *
     * @param g - grafika
     */
    public void paint(Graphics g) {
        g.drawImage(imgYouTube, getX(), getY(), null);
    }

}

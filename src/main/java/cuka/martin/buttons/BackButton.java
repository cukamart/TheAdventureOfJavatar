package cuka.martin.buttons;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Tlacitko spat, vrati uzivatela do predchodzieho stavu.
 *
 * @author Martin Čuka
 */
public final class BackButton extends Clickable {

    private final Image imgMenu;
    private static final int WIDTH = 185;
    private static final int HEIGHT = 80;

    /**
     * Vytvori tlacidlo na urcitej pozicii a velkosti. super(poziciaX, poziciaY,
     * sirka, vyska)
     */
    public BackButton() {
        super(130, 500, WIDTH, HEIGHT);
        imgMenu = new ImageIcon(this.getClass().
                getResource("/backbutton.png")).getImage();
    }

    /**
     * Vykresli tlacidlo
     *
     * @param g - grafika
     */
    public void paint(Graphics g) {
        g.drawImage(imgMenu, getX(), getY(), null);
    }

}

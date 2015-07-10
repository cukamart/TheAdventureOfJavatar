package cuka.martin.buttons;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Trieda reprezentujuca klikatelny button "dalej"
 *
 * @author Martin Čuka
 */
public final class NextButton extends Clickable {

    private final Image imgMenu;
    private static final int WIDTH = 185;
    private static final int HEIGHT = 80;

    /**
     * Inicializuje button na danych suradniciach o danych rozmeroch a obrazku
     * super(poziciaX, poziciaY, sirka, vyska)
     */
    public NextButton() {
        super(480, 500, WIDTH, HEIGHT);
        imgMenu = new ImageIcon(this.getClass().
                getResource("/nextbutton.png")).getImage();
    }

    /**
     * Vykresli button na danej pozicii
     *
     * @param g - grafika
     */
    public void paint(Graphics g) {
        g.drawImage(imgMenu, getX(), getY(), null);
    }

}

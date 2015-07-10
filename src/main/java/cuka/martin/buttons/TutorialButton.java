package cuka.martin.buttons;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Trieda reprezentujuca klikatelne tlacitko "how to play", po stlaceni uvedie
 * hraca so zakladnymi mechanikami hry
 *
 * @author Martin Čuka
 */
public final class TutorialButton extends Clickable {

    private static final int WIDTH = 293;
    private static final int HEIGHT = 98;
    
    private final Image imgMenu;

    /**
     * Inicializuje button na danych suradniciach o danych rozmeroch a obrazku
     * super(poziciaX, poziciaY, sirka, vyska)
     */
    public TutorialButton() {
        super(260, 470, WIDTH, HEIGHT);
        imgMenu = new ImageIcon(this.getClass().
                getResource("/tutorial2.png")).getImage();
    }

    /**
     * Vykresli tlacidlo na danej pozicii
     *
     * @param g - grafika
     */
    public void paint(Graphics g) {
        g.drawImage(imgMenu, getX(), getY(), null);
    }

}

package cuka.martin.buttons;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Tlacitko SelectLevelButton v Menu Clickable nastavi jeho poziciu a aj zistuje
 * ci je kliknute....
 *
 * @author Martin Čuka
 */
public final class SelectLevelButton extends Clickable {

    private static final int WIDTH = 330;
    private static final int HEIGHT = 90;
    
    private final Image imgMenu;

    /**
     * Vytvori selectLevel button na danom miesto o danych rozmeroch
     */
    public SelectLevelButton() {
        super(260, 144, WIDTH, HEIGHT);
        imgMenu = new ImageIcon(this.getClass().
                getResource("/selectlevel_button.png")).getImage();
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

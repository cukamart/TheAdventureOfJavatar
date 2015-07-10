package cuka.martin.buttons;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Tlacitko na vyber levelu BOSS
 * @author Martin Čuka
 */
public final class BossOneButton extends Clickable {

    private static final int WIDTH = 375;
    private static final int HEIGHT = 275;
    
    private final Image imgMenu;
    
    /**
     * Vytvori tlacidlo na urcitej pozicii a velkosti.
     * super(poziciaX, poziciaY, sirka, vyska)
     */
    public BossOneButton() {
        super(385, 315, WIDTH, HEIGHT);
        imgMenu = new ImageIcon(this.getClass().
                getResource("/boss1.jpg")).getImage();
    }
    
    /**
     * Vykresli tlacidlo
     * @param g - grafika
     */
    public void paint(Graphics g) {
        g.drawImage(imgMenu, getX(), getY(), null);
    }
}

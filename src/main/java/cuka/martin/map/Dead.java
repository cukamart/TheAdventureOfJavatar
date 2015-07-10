package cuka.martin.map;

import cuka.martin.welcome.StartingPoint;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Vykresli mrtvu dusu Zombie
 *
 * @author Martin Čuka
 */
public final class Dead {

    private static final Image imgSoul;

    static {
        imgSoul = new ImageIcon(Dead.class.getResource("/dead.png")).getImage();
    }

    private int fade = 70; // ako dlho bude zobrazena dusa...
    private int fly = 0;

    public Dead() {

    }

    /**
     * Getter, zistuje ako dlho bude zobrazeny duch...
     *
     * @return fade - v podstate pocitadlo dlzky zobrazenia ducha
     */
    public int getFade() {
        return fade;
    }

    /**
     * Vykresli dusu na danej pozicii
     *
     * @param g - Grafika
     */
    public void show(Graphics g) {
        g.drawImage(imgSoul, StartingPoint.getCENTERX(),
                StartingPoint.getCENTERY() - fly, null);
        fade--;
        fly += 4;
    }

}

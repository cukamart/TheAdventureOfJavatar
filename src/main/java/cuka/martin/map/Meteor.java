package cuka.martin.map;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Trieda reprezentujuca objekt Meteor, vizualny prvok hry.
 *
 * @author Martin Čuka
 */
public final class Meteor {

    private int positionX;
    private int positionY;

    private final Image imgMenu;

    /**
     * Vytvori meteor na danej pozicii
     */
    public Meteor() {
        this.positionX = 450;
        this.positionY = 0;
        imgMenu = new ImageIcon(this.getClass().
                getResource("/meteor.png")).getImage();
    }

    /**
     * Zobrazi meteor na danej pozicii
     *
     * @param g - Grafika
     */
    public void show(Graphics g) {
        g.drawImage(imgMenu, positionX, positionY, null);
    }

    /**
     * Getter pre X-ksovu poziciu Meteoru
     *
     * @return int hodnota predstavujuca X-ksovu poziciu Objektu
     */
    public int getPositionX() {
        return positionX;
    }

    /**
     * Setter pre X-ksovu poziciu Meteoru
     *
     * @param positionX - udava novu X-ksovu poziciu, kde sa bude nachadzat
     */
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    /**
     * Getter pre Y-novu poziciu Meteoru
     *
     * @return int hodnota predstavujuca Y-novu poziciu Objektu
     */
    public int getPositionY() {
        return positionY;
    }

    /**
     * Setter pre Y-novu poziciu Meteoru
     *
     * @param positionY - udava novu Y-novu poziciu, kde sa bude nachadzat
     */
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

}

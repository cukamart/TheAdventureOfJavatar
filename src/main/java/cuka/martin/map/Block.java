package cuka.martin.map;

import cuka.martin.welcome.StartingPoint;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

/**
 * Reprezentuje 1 blok v hre o rozmeroch 50x50 pixelov.
 *
 * @author Martin Čuka
 */
public abstract class Block implements ICamera {

    private static final int SIZE = 50;
    private final Image imgBlock;
    private final int positionX;
    private int positionY;

    /**
     * Vytvori block na danej pozicii a ulozi si jeho obrazok
     *
     * @param x - pozicia X
     * @param y - pozicia Y
     * @param imgBlock - obrazok Bloku
     */
    Block(int x, int y, Image imgBlock) {
        this.positionX = x;
        this.positionY = y;
        this.imgBlock = imgBlock;
    }

    /**
     * Metoda zobrazuje stenu/zem podla typu na obrazovku hodnoty pozHrdiny X/Y
     * sluzia na urcenie zobrazenia podla jeho polohy
     *
     * @param g - grafika
     * @param posJavatarX - javatarova pozicia X
     * @param posJavatarY - javatarova pozicia Y
     */
    public void show(Graphics g, int posJavatarX, int posJavatarY) {
        if (isInRange(positionX - posJavatarX, positionY - posJavatarY)) {
            g.drawImage(imgBlock, StartingPoint.getCENTERX() + (positionX - posJavatarX),
                    StartingPoint.getCENTERY() + (positionY - posJavatarY), null);
        }
    }

    /**
     * Zobrazenie skutocnej pozicie (kamera sa neybe)
     *
     * @param g - Grafika
     */
    public void showBossFight(Graphics g) {
        g.drawImage(imgBlock, positionX, positionY, null);
    }

    /**
     * Getter pre blok vrati cely blok ako Rectangel pripraveny rovno na
     * porovnanie kolizii
     *
     * @return vrati obdlznik predstavujuci blok hry
     */
    public Rectangle getBlock() {
        return new Rectangle(positionX, positionY, SIZE, SIZE);
    }

    /**
     * Getter pre velkost jedneho bloku hry
     *
     * @return int hodnota reprezentujuca velkost bloku
     */
    public static int getSIZE() {
        return SIZE;
    }

    /**
     * Obycajne steny nedavaju ziaden dmg...
     *
     * @return - damage stien
     */
    public int getDamage() {
        return 0;
    }

    /**
     * Getter pre X-ksovu poziciu bloku
     *
     * @return int hodnota predstavujuca X-ksovu poziciu bloku
     */
    public int getPositionX() {
        return positionX;
    }

    /**
     * Getter pre Y-novu poziciu bloku
     *
     * @return int hodnota predstavujuca Y-novu poziciu bloku
     */
    public int getPositionY() {
        return positionY;
    }

    /**
     * Setter pre Y-novu poziciu bloku
     *
     * @param positionY - nova pozicia bloku
     */
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

}

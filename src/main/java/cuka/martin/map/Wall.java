package cuka.martin.map;

import java.awt.Image;

/**
 * Zobrazuje 1 najzakladnejsi blok steny
 * 
 * @author Martin Čuka
 */
public class Wall extends Block {

    /**
     * Vytvori blok steny na danej pozicii
     *
     * @param x - X-ksova pozicia steny
     * @param y - Y-nova pozicia steny
     * @param imgWall - Obrazok steny
     */
    public Wall(int x, int y, Image imgWall) {
        super(x, y, imgWall);
    }
}

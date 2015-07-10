package cuka.martin.map;

import java.awt.Image;

/**
 * Blok hry reprezentujuci travu
 *
 * @author Martin Čuka
 */
public class Grass extends Block {

    /**
     * Vytvori blok travy na danej pozicii
     *
     * @param x - X-ksova pozicia travy
     * @param y - Y-nova pozicia travy
     * @param img - Obrazok travy
     */
    public Grass(int x, int y, Image img) {
        super(x, y, img);
    }
}

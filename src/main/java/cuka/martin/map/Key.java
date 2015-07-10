package cuka.martin.map;

import java.awt.Image;

/**
 * Specialny blok mapy predstavujuci kluc (item), pri kontakte zmizne a postava
 * ho bude vlastnit
 *
 * @author Martin Čuka
 */
public class Key extends Item {

    /**
     * Vytvori kluc na danej pozicii
     *
     * @param x - X-ksova pozicia pichliacov
     * @param y - Y-nova pozicia pichliacov
     * @param imgKey - Obrazok pichliacov
     */
    public Key(int x, int y, Image imgKey) {
        super(x, y, imgKey);
    }
}

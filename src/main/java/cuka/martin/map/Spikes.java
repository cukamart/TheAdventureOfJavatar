package cuka.martin.map;

import java.awt.Image;

/**
 * Zobrazi 1 blok pichliacov, specialny blok hry, ktory dava poskodenie...
 *
 * @author Martin Čuka
 */
public class Spikes extends Block {

    private static final int DAMAGE = 9999;

    /**
     * Vytvori blok steny na danej pozicii
     *
     * @param x - X-ksova pozicia pichliacov
     * @param y - Y-nova pozicia pichliacov
     * @param imgSpikes - Obrazok pichliacov
     */
    public Spikes(int x, int y, Image imgSpikes) {
        super(x, y, imgSpikes);
    }

    /**
     * Getter vracajuci hodnotu poskodenia pri kontakte
     *
     * @return DAMAGE - int hodnota udavajuca poskodenie pri kontakte s objektom
     */
    @Override
    public int getDamage() {
        return DAMAGE;
    }
}

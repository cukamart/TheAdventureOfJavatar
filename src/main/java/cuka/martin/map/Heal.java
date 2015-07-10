package cuka.martin.map;

import java.awt.Image;

/**
 * Specialny blok mapy predstavujuci lektvar (item), pri kontakte zmizne a
 * postava sa obnovia zivoty
 *
 * @author Martin Čuka
 */
public class Heal extends Item {

    /**
     * Vytvori uzdravujuci lektvar na danej pozicii
     *
     * @param x - X-ksova pozicia lektvaru
     * @param y - Y-nova pozicia lektvaru
     * @param imgHeal - Obrazok lektvaru
     */
    public Heal(int x, int y, Image imgHeal) {
        super(x, y, imgHeal);
    }
}

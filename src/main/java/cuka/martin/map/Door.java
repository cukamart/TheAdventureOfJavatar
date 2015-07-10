package cuka.martin.map;

import java.awt.Image;

/**
 * Predstavuje specialny blok hry, ktory zmizne v pripade ze do nich Javatar
 * narazi a ma kluc
 *
 * @author Martin Čuka
 */
public class Door extends Block {

    /**
     * Inicializuje specialny blok "dvere" na danej pozicii danym obrazkom
     *
     * @param x - X-sova pozicia dveri
     * @param y - Y-nova pozicia dveri
     * @param imgDoor - obrazok dveri
     */
    public Door(int x, int y, Image imgDoor) {
        super(x, y, imgDoor);
    }

}

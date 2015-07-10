package cuka.martin.map;

import java.awt.Image;
import java.awt.Rectangle;

/**
 * Specialny blok mapy, ktory moze zmiznut a nieco spravit pri kontakte s
 * Javatarom
 *
 * @author Martin Čuka
 */
public abstract class Item extends Block {

    /**
     * Vytvori blok itemu na danej pozicii
     *
     * @param x - X-ksova pozicia itemu
     * @param y - Y-nova pozicia itemu
     * @param imgBlock - Obrazok itemu
     */
    Item(int x, int y, Image imgBlock) {
        super(x, y, imgBlock);
    }

    /**
     * Vrati poziciu itemu ako Rectangel pripraveny na porovnanie kolizii
     *
     * @return Obdlznik reprezentujuci skutocnu poziciu objektu
     */
    public Rectangle getItem() {
        return new Rectangle(getPositionX(), getPositionY(),
                getSIZE(), getSIZE());
    }

}

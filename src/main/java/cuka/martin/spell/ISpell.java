package cuka.martin.spell;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Interface ISpell definuje rozhranie kazdeho spellu
 *
 * @author Martin Čuka
 */
public interface ISpell {

    /**
     * Zobrazuje kuzlo v hre
     *
     * @param g - Grafika
     */
    void show(Graphics g);

    /**
     * Vypocita a vrati poziciu objektu kde sa BUDE nachadzat v horizontalnom
     * smere
     *
     * @return Rectangel predstavujuci skutocnu buducu poziciu spellu
     */
    Rectangle getSpellX();

    /**
     * Vypocita a vrati poziciu objektu kde sa BUDE nachadzat vo vertikalnom
     * smere
     *
     * @return Rectangel predstavujuci skutocnu buducu poziciu spellu
     */
    Rectangle getSpellY();

    /**
     * Ake poskodenie da kuzlo hracovi pri kontakte
     * @return int hodnotu predstavujucu poskodenie
     */
    int getDamage();

    /**
     * Zisti ci objekt narazil do niecoho v horizontalnom smere
     * true narazil, false nenarazil
     */
    void setHitX();

    /**
     * Zisti ci objekt narazil do niecoho vo vertikalnom smere
     * true narazil, false nenarazil
     */
    void setHitY();
}

package cuka.martin.characters;

import cuka.martin.core.GameState;
import java.awt.Graphics;

/**
 * Trieda rozsiruje funkcionalitu obycajneho nadradeneho Objektu. Na vykreslenie
 * javatara/bossa treba specialnu metodu show s 2 parametrami.
 *
 * @author Martin Čuka
 */
public abstract class GameCharacter extends MyCharacter {

    /**
     *
     * @param positionX - X-sova suradnica postavy
     * @param positionY - Y-nova suradnica postavy
     * @param sizeX - sirka postavy
     * @param sizeY - vyska postavy
     * @param speed - rychlost postavy
     */
    GameCharacter(int positionX, int positionY, int sizeX,
            int sizeY, int speed) {
        super(positionX, positionY, sizeX, sizeY, speed);
    }

    /**
     * Zobrazi character na mape
     *
     * @param g - grafika
     * @param gs - GameState zisti ci ma hybat kamerou alebo nie
     */
    public abstract void show(Graphics g, GameState gs);

}

package cuka.martin.characters;

/**
 * Enum na pohyb....
 *
 * @author Martin Čuka
 */
public enum Movement {

    LEFT(-1),
    RIGHT(1),
    UP(-1),
    DOWN(1),
    STAND(0),
    LEFTUP(-2),
    RIGHTUP(2);

    private final int movement;

    /**
     * Nastavi hodnotu pohybu
     *
     * @param movement - hodnota pohybu
     */
    Movement(int movement) {
        this.movement = movement;
    }

    /**
     * Zisti hodnotu pohybu, podla toho sa da potom pekne vyriesit opacny pohyb
     *
     * @return ID pohybu
     */
    public int getMovement() {
        return this.movement;
    }
}

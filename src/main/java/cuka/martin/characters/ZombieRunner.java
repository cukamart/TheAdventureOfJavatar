package cuka.martin.characters;

import cuka.martin.map.Block;
import java.awt.Image;
import java.util.Random;

/**
 * Predstavuje druhy Zombika Ruuner - meni sa mu rychlost pohybu za chodu hry
 *
 * @author Martin Čuka
 */
public class ZombieRunner extends ZombieCharacter {

    private static final int DAMAGE = 2;
    private boolean firstTime = true;
    private int count = 0;
    private final Random rnd;

    /**
     * Vytvori zombie na danej pozicii
     *
     * @param positionX - X-ksova pozicia Zombie
     * @param positionY - Y-nova pozicia zombie
     * @param imgLeft - obrazok pre pohyb dolava
     * @param imgRight - obrazok pre pohyb doprava
     */
    public ZombieRunner(int positionX, int positionY, Image imgLeft,
            Image imgRight) {

        super(positionX, positionY, Block.getSIZE(), 1, imgLeft, imgRight);
        this.rnd = new Random();
    }

    /**
     * Getter pre damage zombie
     *
     * @return vrati aktualny damage zombie
     */
    @Override
    public int getDamage() {
        return DAMAGE;
    }

    /**
     * Logika pohybu zombie
     *
     * @param posJavatarX - X-ksova pozicia zombie
     * @param posJavatarY - Y-nova pozicia zombie
     */
    @Override
    public void characterMovement(int posJavatarX, int posJavatarY) {

        if (isInRange(getPositionX() - posJavatarX, getPositionY() - posJavatarY)) {
            if (firstTime) {
                if (getPositionX() >= posJavatarX) {
                    setMov(Movement.LEFT);
                } else {
                    setMov(Movement.RIGHT);
                }
                this.firstTime = false;
            }

            count++;

            if (count > 50) {
                count = 0;
                setSpeed(rnd.nextInt(7 - 2 + 2) + 2);
            }

            if (isChangeDirection()) {
                if (getMov() == Movement.LEFT) {
                    setMov(Movement.RIGHT);
                } else {
                    setMov(Movement.LEFT);
                }
                setChangeDirection(false);
            }
            setMovementofPositionX(getPositionX() + getSpeed() * getMov().getMovement());
        } else {
            firstTime = true;
        }
    }

}

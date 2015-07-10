package cuka.martin.characters;

import cuka.martin.map.Block;
import java.awt.Image;

/**
 * Trieda predstavuje Zombie so sofistikovanejsim pohybom
 *
 * @author Martin Čuka
 */
public class ZombieHighIQ extends ZombieCharacter {

    private static final int DAMAGE = 3;

    /**
     * Vytvori Zombie na danej pozicii
     *
     * @param positionX - X-ksova pozicia Zombie
     * @param positionY - Y-nova pozicia Zombie
     * @param imgLeft - Obrazok pre pohyb dolava
     * @param imgRight - Obrazok pre pohyb doprava
     */
    public ZombieHighIQ(int positionX, int positionY, Image imgLeft,
            Image imgRight) {
        super(positionX, positionY, Block.getSIZE(), 1, imgLeft, imgRight);
    }

    /**
     * Getter pre damage zombie
     *
     * @return damage zombie
     */
    @Override
    public int getDamage() {
        return DAMAGE;
    }

    /**
     * Logika pohybu zombie, hybe sa podla X-ksovej pozicie Javatara pokial je v
     * rangi
     *
     * @param posJavatarX - X-ksova pozicia javatara
     * @param posJavatarY - Y-nova pozicia Javatara
     */
    @Override
    public void characterMovement(int posJavatarX, int posJavatarY) {
        setLastMove(getMov());

        if ((getPositionX() - posJavatarX) < 4
                && (getPositionX() - posJavatarX) > -4) {
            setChangeDirection(false);
            setFrame_count();
            return;
        }

        if (isInRange(getPositionX() - posJavatarX, getPositionY() - posJavatarY)) {
            if (getPositionX() >= posJavatarX) {
                setMov(Movement.LEFT);
            } else {
                setMov(Movement.RIGHT);
            }

            if (isChangeDirection()) {
                setFrame_count();
                setChangeDirection(false);
            } else {
                setMovementofPositionX(getPositionX() + getSpeed()
                        * getMov().getMovement());
            }

            if (getLastMove() != getMov()) {
                setMovementofPositionX(getPositionX() + getSpeed()
                        * getMov().getMovement());
            }

        }
    }

}

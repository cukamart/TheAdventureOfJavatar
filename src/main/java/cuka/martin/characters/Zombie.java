package cuka.martin.characters;

import cuka.martin.core.IPlayer;
import cuka.martin.map.Block;
import java.awt.Image;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Trieda predstavuje najzakladnejsi typ Zombie
 *
 * @author Martin Čuka
 */
public class Zombie extends ZombieCharacter implements IPlayer {

    private static final int DAMAGE = 1;
    private boolean firstTime = true;
    private final Random rnd;

    /**
     * Vytvori Zombika na danej pozicii
     *
     * @param positionX - X-ksova pozicia Zombie
     * @param positionY - Y-nova pozicia Zombie
     * @param imgLeft - Obrazok zombie ide dolava
     * @param imgRight - Obrazok zombie ide doprava
     */
    public Zombie(int positionX, int positionY, Image imgLeft, Image imgRight) {
        super(positionX, positionY, Block.getSIZE(), 3,
                imgLeft, imgRight);
        rnd = new Random();
    }

    /**
     * Vrati damage zombika
     *
     * @return - damage zombika
     */
    @Override
    public int getDamage() {
        return DAMAGE;
    }

    /**
     * Pohyb zombika.
     *
     * @param posJavatarX - X-ksova pozicia Javatara
     * @param posJavatarY - Y-nova pozicia Javatara
     */
    @Override
    public void characterMovement(int posJavatarX, int posJavatarY) {

        if (isInRange(getPositionX() - posJavatarX, getPositionY() - posJavatarY)) {
            if (firstTime) {
                int random = rnd.nextInt(5 - 1 + 1) + 1;
                if (random == 1) {
                    playSound("/brain.wav");
                }
                if (getPositionX() >= posJavatarX) {
                    setMov(Movement.LEFT);
                } else {
                    setMov(Movement.RIGHT);
                }
                firstTime = false;
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

    /**
     * Prehravac zvukov.
     *
     * @param myClip - odkaz na zvuk
     */
    @Override
    public void playSound(String myClip) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                    Zombie.class.getResourceAsStream(myClip));
            clip.open(inputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
            System.err.println("CANT LOAD SOUND: " + ex.getMessage());
            Logger.getLogger(Javatar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

package cuka.martin.characters;

import cuka.martin.welcome.StartingPoint;
import java.awt.Graphics;
import java.awt.Image;

/**
 * Trieda rozsiruje funkcionalitu obycajneho nadradeneho Objektu. Na vykreslenie
 * zombika treba specialnu metodu show s 3 parametrami.
 *
 * @author Martin Čuka
 */
public abstract class ZombieCharacter extends MyCharacter {

    private boolean changeDirection = false;
    private boolean falling = false;
    private final Image imgZombieLeft;
    private final Image imgZombieRight;

    private int frameCount = 0;
    private int num = 0;

    private Movement lastMove;

    /**
     * Vytvori zombie na danej pozicii
     *
     * @param positionX - X-ksova pozicia Zombie
     * @param positionY - Y-nova pozicia Zombie
     * @param sizeX - sirka Zombie
     * @param speed - vyska Zombie
     * @param imgZombieLeft - obrazok pre pohyb dolava
     * @param imgZombieRight - obrazok pre pohyb doprava
     */
    ZombieCharacter(int positionX, int positionY, int sizeX,
            int speed, Image imgZombieLeft, Image imgZombieRight) {
        super(positionX, positionY, sizeX, 90, speed);
        this.imgZombieLeft = imgZombieLeft;
        this.imgZombieRight = imgZombieRight;
    }

    /**
     * Zobrazuje Zombieka podla toho ktorym smerom ide, pre dalsiu mobku staci
     * prekryt tuto metodu...
     *
     * @param g - grafika
     * @param posJavatarX - javatarova pozicia X
     * @param posJavatarY - javatarova pozicia Y
     */
    public void show(Graphics g, int posJavatarX, int posJavatarY) {
        if (isInRange(getPositionX() - posJavatarX, getPositionY() - posJavatarY)) {

            int sizeOfImage = 100;

            int dx = num * sizeOfImage; // nacitavam 0ty riadok obrazku, stlpce sa menia...
            int dy = 0; //nacitava 0ty riadok obrazku... riadok je stale 0....

            if (getMov() == Movement.LEFT) {
                showZombie(imgZombieLeft, g, posJavatarX, posJavatarY,
                        sizeOfImage, dx, dy);
            } else {
                showZombie(imgZombieRight, g, posJavatarX, posJavatarY,
                        sizeOfImage, dx, dy);
            }

            frameCount++;
            if (frameCount % StartingPoint.getDELAY() == 0) {
                num++;
                if (num == 4) { // mam 11 obrazkov pohybu doprava...
                    num = 0; // nulty obrazok je ako stoji, preto od 1dnotky
                }
            }
        }
    }

    private void showZombie(Image showZombie, Graphics g, int posJavatarX,
            int posJavatarY, int sizeOfImage, int dx, int dy) {
        g.drawImage(showZombie,
                StartingPoint.getCENTERX() + (getPositionX() - posJavatarX),
                StartingPoint.getCENTERY() + (getPositionY() - posJavatarY),
                StartingPoint.getCENTERX() + (getPositionX()
                - posJavatarX) + sizeOfImage,
                StartingPoint.getCENTERY() + (getPositionY()
                - posJavatarY) + sizeOfImage,
                dx, dy, dx + sizeOfImage, dy + sizeOfImage, null);
    }

    /**
     * Getter ci ma zombie zmenit svoj smer pohybu
     *
     * @return true - ano ma zmenit false - nie nema zmenit
     */
    boolean isChangeDirection() {
        return this.changeDirection;
    }

    /**
     * Setter pre zmenu pohybu
     *
     * @param changeDirection true - zmen pohyb false - nie uz nemen pohyb
     */
    public void setChangeDirection(boolean changeDirection) {
        this.changeDirection = changeDirection;
    }

    /**
     * Zisti zi zombie pada
     *
     * @return true pada, false nepada
     */
    public boolean isFalling() {
        return this.falling;
    }

    /**
     * Setter pre pad zombie
     *
     * @param falling true zombie pada, false zombie nepada
     */
    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    /**
     * Setter pre pocitadlo framov, vzdy nastavuje na 0 (resetuje pocitadlo)
     */
    void setFrame_count() {
        this.frameCount = 0;
    }

    /**
     * Getter pre naposledy urobeny pohyb zombie
     *
     * @return vrati akym smerom naposledy zombie isiel
     */
    Movement getLastMove() {
        return this.lastMove;
    }

    /**
     * Setter pre posledny pohyb zombie
     *
     * @param lastMove - nastavy posledny pohyb zombie na danu hodnotu
     */
    void setLastMove(Movement lastMove) {
        this.lastMove = lastMove;
    }
}

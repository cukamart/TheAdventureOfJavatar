package cuka.martin.characters;

import cuka.martin.map.ICamera;

/**
 * Trieda reprezentuje postavu v hre ktora je definovana poziciou a zivotami -
 * hrac a pripadni bossovia...
 *
 * @author Martin Čuka
 */
public abstract class MyCharacter implements ICamera {

    private final int SIZE_X;
    private final int SIZE_Y;

    private int speed;

    private int positionX;
    private int positionY;

    private Movement mov;

    /**
     * Vytvori postavu na danej pozicii o danych rozmeroch.
     *
     * @param positionX - X-ksova pozicia postavy
     * @param positionY - Y-nova pozicia postavy
     * @param sizeX - sirka postavy
     * @param sizeY - vyska postavy
     * @param speed - rychlost postavy ktorou sa moze pohybovat po mape
     */
    MyCharacter(int positionX, int positionY, int sizeX, int sizeY, int speed) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.SIZE_X = sizeX;
        this.SIZE_Y = sizeY;
        this.speed = speed;
    }

    /**
     * Najprimitivnejsi pohyb mobky, pre sofistikovanejsi pohyb pretazit.
     *
     * @param posJavatarX - javatarova pozicia X
     * @param posJavatarY - javatarova pozicia Y
     */
    public void characterMovement(int posJavatarX, int posJavatarY) {
        if (isInRange(positionX - posJavatarX, positionY - posJavatarY)) {
            positionX += speed;
        }
    }

    /**
     * Getter pre X-ksovu poziciu postavy
     *
     * @return X-ksova pozicia postavy
     */
    public int getPositionX() {
        return this.positionX;
    }

    /**
     * Getter pre Y-novu poziciu postavy
     *
     * @return Y-nova pozicia postavy
     */
    public int getPositionY() {
        return this.positionY;
    }

    /**
     * Getter pre sirku postavy
     *
     * @return sirka postavy
     */
    public int getSizeX() {
        return this.SIZE_X;
    }

    /**
     * Getter pre vysku postavy
     *
     * @return vyska postavy
     */
    public int getSizeY() {
        return this.SIZE_Y;
    }

    /**
     * Setter pre X-ksovu poziciu postavy
     *
     * @param movement ktorym smerom sa ma hybat
     */
    public void setPositionX(int movement) {
        this.positionX += speed * movement;
    }

    /**
     * Setter pre Y-novu poziciu
     *
     * @param movement ktorym smerom sa ma hybat
     */
    public void setPositionY(int movement) {
        this.positionY += speed * movement;
    }

    /**
     * Getter pre rychlost pohybu postavy
     *
     * @return aktualna rychlost postavy
     */
    public int getSpeed() {
        return this.speed;
    }

    /**
     * Getter pre aktualny pohyb postavy
     *
     * @return aktualny pohyb postavy
     */
    Movement getMov() {
        return this.mov;
    }

    /**
     * Vrati damage postavy, pokial sa nepretazi nedava ziadny damage.
     *
     * @return damage postavy
     */
    public int getDamage() {
        return 0;
    }

    /**
     * Setter pre pohyb, zmeni pohyb postavy
     *
     * @param mov - novy smer pohybu
     */
    void setMov(Movement mov) {
        this.mov = mov;
    }

    /**
     * Meni poziciu nezavisle na rychlosti postavy
     *
     * @param move smer pohybu
     */
    void setMovementofPositionX(int move) {
        this.positionX = move;
    }

    /**
     * Setter pre rychlost postavy
     *
     * @param speed - nova rychlost postavy
     */
    void setSpeed(int speed) {
        this.speed = speed;
    }
}

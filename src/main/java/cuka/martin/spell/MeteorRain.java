package cuka.martin.spell;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 * Trieda je sucastou vacsieho celku, sama o sebe reprezentuje 1 cast kuzla
 * bossa na konci hry. Kuzlo sa sklada z niekolkych tychto objektov vytvara
 * efekt padajuceho ohniveho dazda. Trieda samotna vytvori len jeden padajuci
 * objekt, ktory sa riadi najelementarnejsimi fyzikalnymi zakonmi napr.
 * (gravitacne zrychlenie) pri kontakte s hracom sposobuje poskodenie
 *
 *
 * @author Martin Čuka
 */
public class MeteorRain implements ISpell {

    private static final Image imgMenu;

    static {
        imgMenu = new ImageIcon(MeteorRain.class.
                getResource("/fireball0.png")).getImage();
    }

    private static final int DAMAGE = 1;
    private static final double gravity = 15;
    private static final double energyloss = 0.65; // spomalenie ked dopadne
    private static final double xFriction = 0.9; // spomalenie ked narazi do steny
    private static final double dtime = 0.2; // zmena casu
    private static final int radius = 20;
    private static final int speed = 2;
    private int posX = 0;
    private int posY = 0;
    private double dPosX = 20;
    private double dPosY = 0;
    private int count = 0;
    private boolean isHitX = false;
    private boolean isHitY = false;

    /**
     * Vytvori objekt na danej pozicii
     *
     * @param x - X-ksova pozicia objektu
     * @param y - Y-nova pozicia objektu
     */
    public MeteorRain(int x, int y) {
        super();
        this.posX = x;
        this.posY = y;
    }

    /**
     * Updatne poziciu objektu
     */
    private void update() {
        updateXposition();
        updateYPosition();
    }

    /**
     * Updatne Y-novu poziciu objektu na zaklade fyzikalnych vzorcov
     */
    private void updateYPosition() {
        if (isHitY) {
            if (dPosY > 0) {
                posY -= radius;
            }
            if (dPosY < 0) {
                posY += radius;
            }
            dPosX *= xFriction;
            if (Math.abs(dPosX) < 0.8) {
                dPosX = 0;
            }
            dPosY *= energyloss; // ked dopadne na zem strati trosku energie
            dPosY = -dPosY; // zmeni smer...
        } else {
            // fyzikalny vzorec na zrychlenie
            dPosY += gravity * dtime; // gravitacne zrychlenie
            // dalsi fyzikalny vzorec.....
            posY += dPosY * dtime + 0.5 * gravity * dtime * dtime;
        }
        isHitY = false;
    }

    /**
     * Updatne X-ksovu poziciu, ide jednym smerom az kym do nieco nenarazi
     */
    private void updateXposition() {
        if (isHitX) {
            dPosX = -dPosX; // zmeni smer ked narazi
        } else if (posX + dPosX < radius) {
            posX = radius;
            dPosX = -dPosX;
        } else {
            posX += dPosX;
        }
        isHitX = false;
    }

    /**
     * Zobrazi objekt v hre
     *
     * @param g - Grafika
     */
    @Override
    public void show(Graphics g) {
        count++;
        if (count >= speed) {
            update();
            count = 0;
        }
        g.drawImage(imgMenu, posX, posY, null);
    }

    /**
     * Kde sa bude nachadzat objekt na horizontalnej osy....
     *
     * @return Rectangle predstavujuci kde sa objekt BUDE nachadzat v dalsom
     * tiku
     */
    @Override
    public Rectangle getSpellX() {
        if (dPosX > 0) {
            return new Rectangle(posX + radius, posY, radius * 2, radius * 2);
        } else if (dPosX < 0) {
            return new Rectangle(posX - radius, posY, radius * 2, radius * 2);
        } else {
            return new Rectangle(posX, posY, radius * 2, radius * 2);
        }
    }

    /**
     * Kde sa bude nachadzat objekt na vertikalnej osy....
     *
     * @return Rectangle predstavujuci kde sa objekt BUDE nachadzat v dalsom
     * tiku
     */
    @Override
    public Rectangle getSpellY() {
        double tmpy = posY + dPosY * dtime + 0.5 * gravity * dtime * dtime; // kde sa BUDE nachadzat fireball v dalsom frame...
        if (dPosY > 0) {
            return new Rectangle(posX, (int) tmpy + 1, radius * 2, radius * 2); // ak ide dolava
        } else if (dPosX < 0) {
            return new Rectangle(posX, posY - (int) tmpy + 1, radius * 2, radius * 2); // ak ide doprava
        } else {
            return new Rectangle(posX, posY, radius * 2, radius * 2); // ak stoji
        }
    }

    /**
     * Getter pre poskodenie
     *
     * @return DAMAGE - int hodnota udavajuca poskodenie pri kontakte s hracom
     */
    @Override
    public int getDamage() {
        return DAMAGE;
    }

    /**
     * zisti ci sa ma zmenit X-ksova pozicia objektu (do niecoho narazil) true -
     * narazil, false - nenarazil
     */
    @Override
    public void setHitX() {
        isHitX = true;
    }

    /**
     * zisti ci sa ma zmenit Y-novu poziciu objektu (do niecoho narazil) true -
     * narazil, false - nenarazil
     */
    @Override
    public void setHitY() {
        isHitY = true;
    }
}

package cuka.martin.characters;

import cuka.martin.core.Game;
import cuka.martin.core.GameState;
import cuka.martin.core.IPlayer;
import cuka.martin.listeners.loaders.MyKeyListener;
import cuka.martin.map.Block;
import cuka.martin.welcome.StartingPoint;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

/**
 * Trieda predstavuju javatara, stara sa hlavne o jeho pohyb ci zivoty.
 *
 * @author Martin Čuka
 */
public final class Javatar extends GameCharacter implements IPlayer {

    private static final int GAP_ABOVE_HEAD = 18;
    private static final int LIFEBAR_SIZE = Block.getSIZE() / 4;
    private static final int IMG_LEFT = 200;
    private static final int IMG_RIGHT = 0;
    private static final int IMG_OFFSET_X = 50;
    private static final int IMG_OFFSET_Y = 10;
    private static final int SIZE_OF_IMAGE = 100;
    private static final int MAXIMUM_HP = 100;

    private final MyKeyListener keyListener;

    private final Game game;
    private final int jumpHeight = 3 * Block.getSIZE() + 3;
    private Movement movement;
    private boolean jump = false;
    private boolean falling = false;
    private boolean hasKey = false;
    private int velocity = 0;

    private int frameCount = 0;
    private int num = 0;
    private int healths;

    private final Image imgMenu;

    /**
     * Vytvori Javatara do hry
     *
     * @param x - X-ksova pozicia Javatara
     * @param y - Y-nova pozicia Javatara
     * @param game - hra
     */
    public Javatar(int x, int y, Game game) {
        super(x, y, Block.getSIZE(), 90, 4); // x,y,sirka,vyska,zivoty,rychlost
        this.game = game;
        this.healths = MAXIMUM_HP;
        movement = Movement.STAND;
        keyListener = MyKeyListener.getInstance();
        imgMenu = new ImageIcon(this.getClass().
                getResource("/mainCharacter.png")).getImage();
    }

    /**
     * Nacita aktualny pohyb
     *
     * @param g - Grafika
     * @param gs - GameState podla toho vypocita relativnu poziciu hraca....
     */
    @Override
    public void show(Graphics g, GameState gs) {
        switch (movement) {
            case RIGHT:
                walkRight(g, gs);
                break;
            case LEFT:
                walkLeft(g, gs);
                break;
            case STAND:
                showWalking(g, 0, 0, gs);
                break;
            case LEFTUP:
                showWalking(g, 800, 200, gs); // vyrezanie obrazku: stlpec, riadok
                break;
            case RIGHTUP:
                showWalking(g, 800, 0, gs); // vyrezanie obrazku: stlpec, riadok
                break;
            default:
                System.err.println("CHYBA PRI NACITANI POHYBU !!!!");
        }
    }

    /**
     * Metoda zobrazujuca Javatara iduceho doprava. Na zaklade aktualneho framu
     * vyreze specificky obrazok
     *
     * @param g - Grafika
     * @param gs - aktualny stav hry zisti ci ma hybat s kamerou alebo nie
     */
    private void walkRight(Graphics g, GameState gs) {

        int dx = num * SIZE_OF_IMAGE;

        showWalking(g, dx, IMG_RIGHT, gs);

        frameCount++;
        if (frameCount % StartingPoint.getDELAY() == 0) {
            num++;
            if (num == 11) { // mam 11 obrazkov pohybu doprava...
                num = 1; // nulty obrazok je ako stoji, preto od 1dnotky
            }
        }
        if (frameCount % (StartingPoint.getDELAY() * StartingPoint.getDELAY()) == 0) {
            playSound("/clipStep.wav");
            frameCount = 0;
        }
    }

    /**
     * Metoda zobrazujuca Javatara iduceho dolava. Na zaklade aktualneho framu
     * vyreze specificky obrazok
     *
     * @param g - Grafika
     * @param gs - aktualny stav hry zisti ci ma hybat s kamerou alebo nie
     */
    private void walkLeft(Graphics g, GameState gs) {

        int dx = num * SIZE_OF_IMAGE;

        showWalking(g, dx, IMG_LEFT, gs);

        frameCount++;
        if (frameCount % StartingPoint.getDELAY() == 0) {
            num--;
            if (num <= 0) { // 0ty frame obrazku uz nechcem...
                num = 10; // mam 11 obrazkov pohybu dolava...
            }
        }
        if (frameCount % (StartingPoint.getDELAY() * StartingPoint.getDELAY()) == 0) {
            playSound("/clipStep.wav");
            frameCount = 0;
        }
    }

    /**
     * Vykresli aktualny obrazok podla toho kam sa hrac pohybuje
     *
     * @param g - Grafika
     * @param dx - Zmena vyrezu obrazka
     * @param mov - Aktualny Pohub
     * @param gs - Stav hry podla toho vypocita relativnu poziciu hraca
     */
    private void showWalking(Graphics g, int dx, int mov, GameState gs) {
        if (GameState.INGAME == gs) {
            g.drawImage(imgMenu,
                    StartingPoint.getCENTERX() - IMG_OFFSET_X,
                    StartingPoint.getCENTERY() - IMG_OFFSET_Y,
                    StartingPoint.getCENTERX() + SIZE_OF_IMAGE,
                    StartingPoint.getCENTERY() + SIZE_OF_IMAGE,
                    dx, mov, dx + SIZE_OF_IMAGE, mov + SIZE_OF_IMAGE, null);
            showLifeBar(g, gs, StartingPoint.getCENTERX(), StartingPoint.getCENTERY());
        } else {
            g.drawImage(imgMenu,
                    getPositionX() - IMG_OFFSET_X, getPositionY() - IMG_OFFSET_Y,
                    getPositionX() + SIZE_OF_IMAGE, getPositionY() + SIZE_OF_IMAGE,
                    dx, mov, dx + SIZE_OF_IMAGE, mov + SIZE_OF_IMAGE, null);
            showLifeBar(g, gs, getPositionX(), getPositionY());
        }
        //g.drawRect(StartingPoint.getCENTERX(), StartingPoint.getCENTERY(), getSizeX(), getSizeY());
    }

    /**
     * Logika Javatarovho pohybu
     */
    public void javatarMovement() {

        this.movement = Movement.STAND;

        if (keyListener.isLeft()) {
            walk(Movement.LEFT, Movement.LEFTUP);
        }

        if (keyListener.isRight()) {
            walk(Movement.RIGHT, Movement.RIGHTUP);
        }

        if (!jump) { // ak neskacem
            falling = !game.collisionDetectionVertical(Movement.DOWN);
        }
        // skoc ale iba vtedy ak akurat nepadam alebo ak uz neskacem
        if ((keyListener.isUp() && !falling) || jump) {
            jump = true;
            if (velocity == getSpeed()) {
                playSound("/jump.wav");
            }
            if (game.collisionDetectionVertical(Movement.UP)) {
                jump = false; // padni...
                falling = true; // padni...
                velocity = 0; // padni....
            }
            velocity += getSpeed(); // skoc vyssie...
            if (velocity >= jumpHeight) { // ak som dosiahol maximum vysky skoku
                jump = false; // padni...
                falling = true; // padni...
                velocity = 0; // padni....
            }
        }
    }

    /**
     * Zisti aky pohyb javatar vykonava (ci je v skoku/pade) alebo len tak bezi
     *
     * @param mov - aktualny pohyb javatara
     * @param jumpingMov - ak je vo vzduchu zmeni sa pohyb na tento
     */
    private void walk(Movement mov, Movement jumpingMov) {
        if (!game.collisionDetectionHorizontal(mov)) { // da sa pohnut ?
            if (jump || falling) { // som v skoku ?
                this.movement = jumpingMov; // ak som v skoku dolava/doprava....
            } else {
                this.movement = mov; // ak bezim dolava/doprava....
            }
        }
    }

    /**
     * Zobrazuje LifeBar nad javatarom
     *
     * @param g - Grafika
     * @param gs - GameState
     * @param posX - relativna poziciaX
     * @param posY - relativna poziciaY
     */
    private void showLifeBar(Graphics g, GameState gs, int posX, int posY) {

        // stratene zivoty
        g.setColor(Color.red);
        g.fillRect(posX, posY - GAP_ABOVE_HEAD, Block.getSIZE(), LIFEBAR_SIZE);
        g.setColor(Color.green);

        // aktualne zivoty
        int percentageOfLife = 100 * healths / MAXIMUM_HP;
        // cierny ram okolo lifebaru
        g.fillRect(posX, posY - GAP_ABOVE_HEAD, percentageOfLife / 2, LIFEBAR_SIZE);
        g.setColor(Color.BLACK);

        // rozdelenie po desiatich percentach (zobrazenie aktualneho zivota)
        if (GameState.INGAME == gs) {
            for (int i = 10; i <= percentageOfLife; i += 10) {
                g.drawLine(StartingPoint.getCENTERX() + (i / 2),
                        StartingPoint.getCENTERY() - 8,
                        StartingPoint.getCENTERX() + (i / 2),
                        StartingPoint.getCENTERY() - GAP_ABOVE_HEAD);
            }
        } else {
            for (int i = 10; i <= percentageOfLife; i += 10) {
                g.drawLine(getPositionX() + i / 2, getPositionY() - 20,
                        getPositionX() + i / 2, getPositionY() - 8);
            }
        }

        // ramik okolo zivotov....
        for (int i = 1; i <= 2; i++) {
            g.drawRect(posX - i, posY - GAP_ABOVE_HEAD - i,
                    Block.getSIZE() + i, (Block.getSIZE() + 1) / 4);
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
                    Javatar.class.getResourceAsStream(myClip));
            clip.open(inputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
            System.err.println("CANT LOAD SOUND: " + ex.getMessage());
            Logger.getLogger(Javatar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Setter na Javatarove zivoty
     *
     * @param healths - nova hodnota zivotov
     */
    public void setHealths(int healths) {
        this.healths = healths;
    }

    /**
     * Getter pre zivoty
     *
     * @return vrati aktualne zivoty Javatara
     */
    public int getHealths() {
        return this.healths;
    }

    /**
     * Getter pre pohyb Javatara
     *
     * @return vrati aktualny pohyb javatara
     */
    public Movement getMovement() {
        return this.movement;
    }

    /**
     * Zisti ci Javatar prave nepada
     *
     * @return true - Javatar pada false Javatar nepada
     */
    public boolean isFalling() {
        return this.falling;
    }

    /**
     * Zisti ci je Javatar v skoku
     *
     * @return true - Javatar je v skoku false - Javatar nieje v skoku
     */
    public boolean isJump() {
        return this.jump;
    }

    /**
     * Maximalne zivoty Javatara
     *
     * @return vrati maximalnu hodnotu zivotou ktore Javatar moze mat
     */
    public int getMAXIMUMHP() {
        return MAXIMUM_HP;
    }

    /**
     * Zisti ci ma Javatar kluc od konca levelu.
     *
     * @return true - Javatar ma kluc false - Javatar nema kluc
     */
    public boolean isHasKey() {
        return this.hasKey;
    }

    /**
     * Setter pre Javatar kluc, nastavuje vzdy hodnotu z false na true...
     */
    public void setHasKey() {
        this.hasKey = true;
    }
}

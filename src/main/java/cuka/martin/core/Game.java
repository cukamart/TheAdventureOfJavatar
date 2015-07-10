package cuka.martin.core;

import cuka.martin.characters.*;
import cuka.martin.map.*;
import cuka.martin.listeners.loaders.MyKeyListener;
import cuka.martin.spell.ISpell;
import cuka.martin.welcome.StartingPoint;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

/**
 * Nadradena trieda vsetkych objektov ktore sa nachadzaju priamo v samotnej hre.
 * Obaluje vsetky in game objekty a posiela im rozlicne spravy podla aktualneho
 * stavu hry. Srdce aplikacie.
 *
 * @author Martin Čuka
 */
public final class Game implements IPlayer {

    private static final int SHRINK = 2; // zmensi javatarovu velkost
    private static final int KILL_SIZE = 4; // zmensi koliziu na 4 pixely
    private static final int GAP = 20;
    private static final int DEAD_TIMER = 150; // ako dlho bude gameOver screen
    private static final int CORNER = 450;
    private static final int FONT_SIZE = 48;

    private final List<Block> blocks;
    private final List<ZombieCharacter> enemies;
    private final List<Dead> deadZombie;
    private final List<ISpell> spells;
    private final List<Item> items;
    private final Random rnd;

    private final Image[] imgs = new Image[20];

    private BossCharacter boss;
    private Javatar javatar;
    private Finish finish;
    private Meteor meteor;
    private GameState inGameGameState;
    private int level;
    private int cityX = 0;
    private int deadCount = 0;
    private boolean stand = true;
    private boolean jump = true;

    /**
     * Konstruktor hry, inicializuje potrebne atributy a nacita obrazky hry.
     */
    public Game() {
        inGameGameState = GameState.INGAME;
        blocks = new ArrayList<>();
        enemies = new ArrayList<>();
        deadZombie = new ArrayList<>();
        spells = new ArrayList<>();
        items = new ArrayList<>();
        rnd = new Random();
        loadImages();
    }

    /**
     * Algoritmy hry, vypocitava napr pozicie mobiek, itemov, blokov ci
     * Javatarovu poziciu. Vsetko co nesuvisi s grafikou.
     */
    public void tick() {

        tickTutorial();

        if (MyKeyListener.getInstance().isEsc()) {
            inGameGameState = GameState.MAINSCREEN;
            reset();
        }

        if (javatar.getHealths() <= 0) {
            inGameGameState = GameState.GAMEOVER;
            deadCount++;
        }

        if (inGameGameState == GameState.VICTORY) {
            deadCount++;
        }

        if (inGameGameState == GameState.INGAME) {
            tickInGame();
            moveCity();
        }

        if (inGameGameState == GameState.BOSSFIGHT) {
            tickInBoss();
        }

        tickEnding();
    }

    /**
     * Nastavi stav hry - vyhral / prehral.
     */
    private void tickEnding() {
        if (inGameGameState == GameState.GAMEOVER && deadCount > DEAD_TIMER) {
            reset();
            inGameGameState = GameState.RESET;
        }

        if (inGameGameState == GameState.VICTORY && deadCount > DEAD_TIMER) {
            reset();
            inGameGameState = GameState.RESET;
        }
    }

    /**
     * Algoritmy hry prebiehajuce pocas bossa (nehybe sa kamera).
     */
    private void tickInBoss() {
        javatar.javatarMovement();
        Object object = boss.magic();
        if (object != null) {
            spells.add((ISpell) object);
        }
        spellsCollisionDetection();
        if (boss.isOutOfMana()) {
            if (spells.size() > 0) {
                playSound("/comet.wav");
                meteor = new Meteor();
            }
            spells.clear();
            for (int i = 0; i < 2; i++) {
                items.add(new Heal(rnd.nextInt(StartingPoint.getPANEL_WIDTH()
                        - 3 * Block.getSIZE()) + Block.getSIZE(),
                        rnd.nextInt(Block.getSIZE() * 2) - Block.getSIZE(),
                        imgs[ImageID.HEAL.ID()]));
            }
            boss.setOutOfMana(false);
        }

        meteorFalling();

        victory();
    }

    /**
     * Ak sa Javatar dostal na koniec levela zmeni stav hry na VICTORY.
     */
    private void victory() {
        if (finish != null) {
            Rectangle finishRect = new Rectangle(finish.getPositionX(),
                    finish.getPositionY(), Block.getSIZE(), Block.getSIZE());
            Rectangle javaRect = new Rectangle(javatar.getPositionX(),
                    javatar.getPositionY(), javatar.getSizeX(), javatar.getSizeY());
            if (finishRect.intersects(javaRect)) {
                inGameGameState = GameState.VICTORY;
            }
        }
    }

    /**
     * Padajuci meteorit na bossa (vedlajsi effekt jeho spellu).
     */
    private void meteorFalling() {
        if (meteor != null) {
            meteor.setPositionY(meteor.getPositionY() + 1);
            meteor.setPositionX(meteor.getPositionX() + 1);
            boss.setStop(true);

            Rectangle meteorRect = new Rectangle(meteor.getPositionX(),
                    meteor.getPositionY(), Block.getSIZE(), Block.getSIZE());
            Rectangle bossRect = new Rectangle(boss.getPositionX(),
                    boss.getPositionY(), boss.getSizeX(), boss.getSizeY());
            if (meteorRect.intersects(bossRect)) {
                meteor = null;
                playSound("/boom.wav");
                boss.setHealths(boss.getHealths() - 1);
                if (boss.getHealths() > 0) {
                    boss.setStop(false);
                } else {
                    finish = new Finish(650, CORNER);
                }
            }
        }
    }

    /**
     * Zisti ci hrac spravil zakladne pohyby, ak ano uz nebude zobrazovat hinty.
     */
    private void tickTutorial() {
        if (javatar.getMovement() != Movement.STAND) {
            stand = false;
        }

        if (javatar.getMovement() == Movement.RIGHTUP && javatar.isJump()) {
            jump = false;
        }
    }

    /**
     * Algoritmy hry v obycajnom levely (kamera sa hybe).
     */
    private void tickInGame() {
        javatar.javatarMovement();

        zombieCollisionDetection();

        enemies.stream().forEach((enemy)
                -> enemy.characterMovement(javatar.getPositionX(),
                        javatar.getPositionY()));

        if (isEndofLevel()) {
            try {
                inGameGameState = GameState.BOSSFIGHT;
                reset();
                loadMap("boss", level);
            } catch (IOException ex) {
                System.err.println("Error loading Boss: " + ex.getMessage());
            }
        }
    }

    /**
     * Zisti ci zombie nenarazilo na jamu alebo na vertikalnu stenu.
     */
    private void zombieCollisionDetection() {
        for (ZombieCharacter enemie : enemies) {
            enemie.setFalling(true);

            Rectangle enemyRect = new Rectangle(StartingPoint.getCENTERX()
                    + (enemie.getPositionX() - javatar.getPositionX()),
                    StartingPoint.getCENTERY() + (enemie.getPositionY()
                    - javatar.getPositionY()), enemie.getSizeX()
                    + 2 * GAP, enemie.getSizeY());

            Rectangle enemyRectV = new Rectangle(StartingPoint.getCENTERX()
                    + (enemie.getPositionX() - javatar.getPositionX())
                    + Block.getSIZE(), StartingPoint.getCENTERY()
                    + (enemie.getPositionY() - javatar.getPositionY()
                    + SHRINK * Block.getSIZE() + SHRINK),
                    2 * SHRINK, 2 * SHRINK);

            blocks.stream().map(Block::getBlock).map((blockRect)
                    -> new Rectangle(blockRect.x
                            - (javatar.getPositionX() - StartingPoint.getCENTERX()),
                            blockRect.y - (javatar.getPositionY()
                            - StartingPoint.getCENTERY()), blockRect.width,
                            blockRect.height)).map((trueRect) -> {
                        if (enemyRectV.intersects(trueRect)) {
                            enemie.setFalling(false);
                        }
                        return trueRect;
                    }).filter((trueRect) -> (trueRect.intersects(enemyRect)))
                    .forEach((_item) -> enemie.setChangeDirection(true));
            if (enemie.isFalling()) {
                enemie.setChangeDirection(true);
            }
        }
    }

    /**
     * Vykreslenie grafiky hry.
     *
     * @param g - Grafika
     */
    public void show(Graphics g) {

        if (inGameGameState == GameState.INGAME) {
            showInLevel(g);
        }

        if (inGameGameState == GameState.BOSSFIGHT) {
            showInBoss(g);
        }

        if (inGameGameState == GameState.GAMEOVER) {
            showInGameOver(g);
        }

        if (inGameGameState == GameState.VICTORY) {
            showInVictory(g);
        }
    }

    /**
     * Vykresli GameOver screen.
     *
     * @param g - Grafika
     */
    private void showInGameOver(Graphics g) {
        g.drawImage(imgs[ImageID.GAMEOVER.ID()], 0, 0, null);
        showMessage(g, "YOU DIED !!!");
    }

    /**
     * Vitazny screen.
     *
     * @param g - Grafika
     */
    private void showInVictory(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, StartingPoint.getPANEL_WIDTH(),
                StartingPoint.getPANEL_HEIGHT());
        g.setColor(Color.RED);
        showMessage(g, "VICTORY !!!");
        g.drawImage(imgs[ImageID.VICTORY.ID()], StartingPoint.getCENTERX(),
                StartingPoint.getCENTERY(), null);
    }

    /**
     * Vypise spravu na obrazovku.
     *
     * @param g - Grafika
     * @param message - sprava ktoru ma vypisat
     */
    private void showMessage(Graphics g, String message) {
        g.setColor(Color.RED);
        g.setFont(new Font("TimesRoman", Font.BOLD, FONT_SIZE));
        g.drawString(message, 250, 9 * Block.getSIZE());
        int time = 150 - deadCount;
        g.drawString("continue in: " + time, 250, 10 * Block.getSIZE());
    }

    /**
     * Hra v stave ked sa bojuje proti bossovi -> nehybe sa kamera....
     *
     * @param g - Grafika
     */
    private void showInBoss(Graphics g) {
        g.drawImage(imgs[ImageID.BACKGROUND.ID()], 0, 0, null);

        blocks.stream().forEach((block) -> block.showBossFight(g));

        for (int i = 1; i < 4; i++) {
            if (i <= boss.getHealths()) {
                g.drawImage(imgs[ImageID.HP.ID()], CORNER + 75 * i,
                        GAP / 2, null);
            } else {
                g.drawImage(imgs[ImageID.LOSTHP.ID()], CORNER + 75 * i,
                        GAP / 2, null);
            }
        }

        if (meteor != null) {
            meteor.show(g);
        }

        if (finish != null) {
            finish.showBossFight(g);
        }

        items.stream().forEach((item) -> item.showBossFight(g));

        boss.show(g, inGameGameState);
        javatar.show(g, inGameGameState);

        spells.stream().forEach((spell) -> spell.show(g));
    }

    /**
     * Zobrazuje prvky hry v klasickom kole, kamera sa hybe s javatarom.
     *
     * @param g - Grafika
     */
    private void showInLevel(Graphics g) {
        showCity(g);

        blocks.stream().forEach((block)
                -> block.show(g, javatar.getPositionX(), javatar.getPositionY()));

        enemies.stream().forEach((enemy)
                -> enemy.show(g, javatar.getPositionX(), javatar.getPositionY()));

        items.stream().forEach((item)
                -> item.show(g, javatar.getPositionX(), javatar.getPositionY()));

        for (int i = 0; i < deadZombie.size(); i++) {
            deadZombie.get(i).show(g);
            if (deadZombie.get(i).getFade() <= 0) {
                deadZombie.remove(i);
            }
        }

        showTutorial(g);

        Rectangle finishRect = new Rectangle(finish.getBlock());

        if (finishRect.x - javatar.getPositionX() <= StartingPoint.getCENTERX()
                && !javatar.isHasKey()) {
            g.setColor(Color.RED);
            g.setFont(new Font("TimesRoman", Font.BOLD, FONT_SIZE));
            g.drawString("You don't have the key !!!", 115, Block.getSIZE());
        }

        javatar.show(g, inGameGameState);
        finish.show(g, javatar.getPositionX(), javatar.getPositionY());
    }

    /**
     * Ukazuje hinty ako hrat hru....
     *
     * @param g - Grafika
     */
    private void showTutorial(Graphics g) {
        if (stand) {
            g.drawImage(imgs[ImageID.MOVE.ID()], 0, 0, null);
        }

        if (jump) {
            if (stand) {
                g.drawImage(imgs[ImageID.JUMP.ID()], 0, 200, null);
            } else {
                g.drawImage(imgs[ImageID.JUMP.ID()], 0, 0, null);
            }
        }

        if (javatar.isHasKey()) {
            g.drawImage(imgs[ImageID.HASKEY.ID()], GAP, CORNER, null);
        } else {
            g.drawImage(imgs[ImageID.NOKEY.ID()], GAP, CORNER, null);
        }
    }

    /**
     * Zobrazuje pozadie mesta ako sa hybe (v klasickom kole).
     *
     * @param g - Grafika
     */
    private void showCity(Graphics g) {
        g.drawImage(imgs[ImageID.BACKGROUND.ID()], cityX, 0, null);
        if (cityX < 0) {
            g.drawImage(imgs[ImageID.BACKGROUND.ID()],
                    cityX + StartingPoint.getPANEL_WIDTH(), 0, null);
        } else {
            g.drawImage(imgs[ImageID.BACKGROUND.ID()],
                    cityX - StartingPoint.getPANEL_WIDTH(), 0, null);
        }
    }

    /**
     * Vypocita ako ma pohnut s pozadim (v klasickom kole kde sa hybe kamera).
     */
    private void moveCity() {
        if (cityX > StartingPoint.getPANEL_WIDTH() * -1) {
            if (javatar.getMovement() == Movement.LEFT
                    || javatar.getMovement() == Movement.LEFTUP) {
                cityX += javatar.getSpeed();
            } else if (javatar.getMovement() == Movement.RIGHT
                    || javatar.getMovement() == Movement.RIGHTUP) {
                cityX -= javatar.getSpeed();
            }
        } else {
            cityX = 0;
        }
        if (cityX > StartingPoint.getPANEL_WIDTH()) {
            cityX = 0;
        }
    }

    /**
     * Nacita level.
     *
     * @param name - nazov levelu (klasicky alebo boss)
     * @param level - cislo level
     * @throws FileNotFoundException - nepodarilo sa nacitat level
     * @throws IOException - nepodarilo sa nacitat level
     */
    public void loadMap(String name, int level) throws IOException {

        // pokial sa skoncila predosla hra a nacitava sa nova
        if (inGameGameState == GameState.RESET) {
            inGameGameState = GameState.INGAME;
        }
        this.level = level;
        String path = "/" + name + level + ".txt";
        String line;
        BufferedReader br;
        int posY = 0;
        InputStream is = Game.class.getResourceAsStream(path);
        InputStreamReader isr = new InputStreamReader(is, Charset.defaultCharset());

        br = new BufferedReader(isr);
        try {

            for (line = br.readLine(); line != null; line = br.readLine()) {
                for (int i = 0; i < line.length(); i++) {
                    switch (MapProperties.values()[line.charAt(i) - 'A']) {
                        //========================BLOCK======================
                        case BLOCK:
                            break;
                        //========================WALL======================   
                        case WALL:
                            blocks.add(new Wall(i * Block.getSIZE(),
                                    posY * Block.getSIZE(),
                                    imgs[ImageID.WALL.ID()]));
                            break;
                        //========================GRASS======================   
                        case GRASS:
                            int random = rnd.nextInt(4);
                            Image imgGrass = new ImageIcon(this.getClass().
                                    getResource("/grass" + random + ".png")).
                                    getImage();
                            blocks.add(new Grass(i * Block.getSIZE(),
                                    posY * Block.getSIZE(), imgGrass));
                            break;
                        //========================DOOR======================   
                        case DOOR:
                            blocks.add(new Door(i * Block.getSIZE(),
                                    posY * Block.getSIZE(),
                                    imgs[ImageID.DOOR.ID()]));
                            break;
                        //========================START======================   
                        case START:
                            javatar = new Javatar(i * Block.getSIZE(),
                                    posY * Block.getSIZE(), this);
                            break;
                        //========================FINISH======================     
                        case FINISH:
                            finish = new Finish(i * Block.getSIZE(),
                                    posY * Block.getSIZE());
                            break;
                        //========================WIZZARD======================     
                        case WIZZARD:
                            boss = new Wizzard(i * Block.getSIZE(),
                                    posY * Block.getSIZE());
                            break;
                        //========================SPIKE======================     
                        case SPIKE:
                            blocks.add(new Spikes(i * Block.getSIZE(),
                                    posY * Block.getSIZE(),
                                    imgs[ImageID.SPIKES.ID()]));
                            break;
                        //========================ZOMBIE======================     
                        case ZOMBIE:
                            enemies.add(new Zombie(i * Block.getSIZE(),
                                    posY * Block.getSIZE(),
                                    imgs[ImageID.ZOMBIEL.ID()],
                                    imgs[ImageID.ZOMBIER.ID()]));
                            break;
                        //========================HEAL======================     
                        case HEAL:
                            items.add(new Heal(i * Block.getSIZE(),
                                    posY * Block.getSIZE(),
                                    imgs[ImageID.HEAL.ID()]));
                            break;
                        //========================KEY======================     
                        case KEY:
                            items.add(new Key(i * Block.getSIZE(),
                                    posY * Block.getSIZE(),
                                    imgs[ImageID.KEY.ID()]));
                            break;
                        //========================ZOMBIEIQ======================     
                        case ZOMBIEIQ:
                            enemies.add(new ZombieHighIQ(i * Block.getSIZE(),
                                    posY * Block.getSIZE(),
                                    imgs[ImageID.ZOMBIEIQL.ID()],
                                    imgs[ImageID.ZOMBIEIQR.ID()]));
                            break;
                        //========================RUNNER======================    
                        case RUNNER:
                            enemies.add(new ZombieRunner(i * Block.getSIZE(),
                                    posY * Block.getSIZE(),
                                    imgs[ImageID.RUNNERL.ID()],
                                    imgs[ImageID.RUNNERR.ID()]));
                            break;
                    }
                }
                posY++;
            }
        } catch (IOException ex) {
            System.out.println("Error loading map: " + ex.getMessage());
        } finally {
            br.close();
        }
    }

    /**
     * Javatarove kolizie pri behu doprava/dolava.
     *
     * @param mov - aktualny pohyb javatara
     * @return True - narazil, False - moze pokracovat dalej
     */
    public boolean collisionDetectionHorizontal(Movement mov) {
        javatar.setPositionX(mov.getMovement());

        Rectangle javatarRec = new Rectangle(javatar.getPositionX(), javatar.getPositionY(),
                javatar.getSizeX() - SHRINK, javatar.getSizeY() - SHRINK);

        enemies.stream().forEach((enemy) -> {
            Rectangle enemyRect = new Rectangle(enemy.getPositionX() + GAP,
                    enemy.getPositionY() + GAP, Block.getSIZE(), Block.getSIZE());
            if (javatarRec.intersects(enemyRect)) {
                javatar.setHealths(javatar.getHealths() - enemy.getDamage());
            }
        });

        takeItem(javatarRec);

        for (int i = 0; i < blocks.size(); i++) {
            Rectangle blockRect = blocks.get(i).getBlock();
            if (javatarRec.intersects(blockRect)) {
                if (blocks.get(i) instanceof Door) {
                    if (javatar.isHasKey()) {
                        blocks.remove(i);
                    }
                }
                javatar.setPositionX((-1) * mov.getMovement()); // spravi opacny pohyb (to zabezbecuje -1)
                return true;
            }
        }
        return false;
    }

    /**
     * Healne Javatara o 25% z jeho celkovych HP.
     *
     * @param i - odobre block na indexe i (lekarnicku)
     */
    private void healJavatar(int i) {
        items.remove(i);
        if (javatar.getHealths() >= javatar.getMAXIMUMHP() - javatar.getMAXIMUMHP() / 4) {
            javatar.setHealths(javatar.getMAXIMUMHP());
        } else {
            javatar.setHealths(javatar.getHealths() + javatar.getMAXIMUMHP() / 4);
        }
    }

    /**
     * Vertikalne kolizie javatara (kontroluje aj zabitie zombikov).
     *
     * @param mov - aktualny pohyb
     * @return True - narazil, False - moze pokracovat dalej
     */
    public boolean collisionDetectionVertical(Movement mov) {
        javatar.setPositionY(mov.getMovement());

        Rectangle javatarRec = new Rectangle(javatar.getPositionX(), javatar.getPositionY(),
                javatar.getSizeX() - SHRINK, javatar.getSizeY() - SHRINK);

        javatarOnZombie(javatarRec);

        takeItem(javatarRec);

        for (Block block : blocks) {
            Rectangle blockRect = block.getBlock();
            if (javatarRec.intersects(blockRect)) {
                javatar.setPositionY((-1) * mov.getMovement()); // spravi opacny pohyb (to zabezbecuje -1)
                javatar.setHealths(javatar.getHealths() - block.getDamage()); // ak sa dotkne lavy zomrie...
                if (block instanceof Spikes) {
                    playSound("/spikes.wav");
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Javatar ziska item.
     *
     * @param javatarRec javatarova pozicia
     */
    private void takeItem(Rectangle javatarRec) {
        for (int i = 0; i < items.size(); i++) {
            Rectangle itemRect = items.get(i).getItem();
            if (javatarRec.intersects(itemRect)) {
                if (items.get(i) instanceof Heal) {
                    healJavatar(i);
                } else {
                    javatar.setHasKey();
                    items.remove(i);
                }
            }
        }
    }

    /**
     * Kontroluje ci javatar nenarazil do zombie. Najprv vytvori potrebne bloky
     * na kontrolu potom vola javatarHitsZombie.
     *
     * @param javatarRec javatarova pozicia
     */
    private void javatarOnZombie(Rectangle javatarRec) {
        // pokial javatar skoci na mobku tak ju zabije....
        for (int i = 0; i < enemies.size(); i++) {
            Rectangle enemyRect, javRec, eneRec;
            // vytvori obdlznik aktualnej polohy zombika
            enemyRect = new Rectangle(enemies.get(i).getPositionX() + 5,
                    enemies.get(i).getPositionY() + GAP,
                    Block.getSIZE() + GAP + GAP / 2, Block.getSIZE());
            // vytvori malicky obdlznik na presnu zhodu pripadneho zabitia zombika (javatar)
            javRec = new Rectangle(StartingPoint.getCENTERX(), StartingPoint.getCENTERY()
                    + javatar.getSizeY() - SHRINK, javatar.getSizeX() - SHRINK, KILL_SIZE);
            // vytvori malicky obdlznik na presnu zhodu pripadneho zabitia zombika (zombie)
            eneRec = new Rectangle(StartingPoint.getCENTERX() + GAP
                    + (enemies.get(i).getPositionX() - javatar.getPositionX()),
                    StartingPoint.getCENTERY() + (enemies.get(i).getPositionY()
                    - javatar.getPositionY()),
                    enemies.get(i).getSizeX(), KILL_SIZE + SHRINK);

            javatarHitsZombie(javatarRec, enemyRect, javRec, eneRec, i);
        }
    }

    /**
     * Pomocna metoda k javatarOnZombie, kontroluje ci javatar skocil na zombie.
     *
     * @param javatarRec - aktualna pozicia javatara
     * @param enemyRect - aktualna pozicia zombie
     * @param javRec - presna zhoda na 4 pixely na zabitie zombie (javatar)
     * @param eneRec - presna zhoda na 4 pixely na zabitie zombie (zombie)
     * @param i - index kontajnera
     */
    private void javatarHitsZombie(Rectangle javatarRec, Rectangle enemyRect,
            Rectangle javRec, Rectangle eneRec, int i) {
        if (javatar.isFalling() && javRec.intersects(eneRec)) {
            if (!(enemies.get(i) instanceof ZombieHighIQ)) {
                deadZombie.add(new Dead());
                enemies.remove(i);
                playSound("/dead.wav");
            }

        }
        if (javatarRec.intersects(enemyRect)) {
            javatar.setHealths(javatar.getHealths() - enemies.get(i).getDamage());
        }
    }

    /**
     * Kontroluje ci spell narazil do steny.
     *
     */
    private void spellsCollisionDetection() {
        Rectangle javatarRec = new Rectangle(javatar.getPositionX(),
                javatar.getPositionY(),
                javatar.getSizeX() - SHRINK, javatar.getSizeY() - SHRINK);

        items.stream().forEach((item) -> {
            boolean intersect = false;
            Rectangle itemRect = item.getItem();
            for (Block block : blocks) {
                Rectangle blockRect = block.getBlock();
                if (itemRect.intersects(blockRect)) {
                    intersect = true;
                }
            }
            if (!intersect) {
                item.setPositionY(item.getPositionY() + 2);
            }
        });

        spells.stream().forEach((spell) -> {
            Rectangle spellRect = spell.getSpellX();
            Rectangle spellRectY = spell.getSpellY();
            if (spellRect.intersects(javatarRec)) {
                javatar.setHealths(javatar.getHealths() - spell.getDamage());
            }
            blocks.stream().map(Block::getBlock).map((blockRect) -> {
                if (spellRect.intersects(blockRect)) {
                    spell.setHitX();
                }
                return blockRect;
            }).filter((blockRect) -> (spellRectY.intersects(blockRect)))
                    .forEach((_item) -> spell.setHitY());
        });
    }

    /**
     * Skontroluje ci je javatar v cieli.
     *
     * @return True - javatar je v cieli False - nieje
     */
    private boolean isEndofLevel() {
        Rectangle javatarRect = new Rectangle(javatar.getPositionX(),
                javatar.getPositionY(),
                javatar.getSizeX() - SHRINK, javatar.getSizeY() - SHRINK);
        Rectangle finishRect = new Rectangle(finish.getBlock());

        return javatarRect.intersects(finishRect);
    }

    /**
     * Vymaze to co ostalo v kontajneroch.
     */
    private void reset() {
        if (inGameGameState != GameState.MAINSCREEN) {
            deadCount = 0;
        }
        blocks.clear();
        enemies.clear();
        deadZombie.clear();
        spells.clear();
        items.clear();
        this.stand = true;
        this.jump = true;
        finish = null;
        MyKeyListener.getInstance().reset();
    }

    /**
     * Nacita obrazky objektov vyskytujucich sa v hre.
     */
    private void loadImages() {
        imgs[ImageID.MOVE.ID()] = new ImageIcon(this.getClass().
                getResource("/move.png")).getImage();
        imgs[ImageID.JUMP.ID()] = new ImageIcon(this.getClass().
                getResource("/jump.png")).getImage();
        imgs[ImageID.HASKEY.ID()] = new ImageIcon(this.getClass().
                getResource("/hasKey.png")).getImage();
        imgs[ImageID.NOKEY.ID()] = new ImageIcon(this.getClass().
                getResource("/noKey.png")).getImage();
        imgs[ImageID.BACKGROUND.ID()] = new ImageIcon(this.getClass().
                getResource("/background1.png")).getImage();
        imgs[ImageID.HP.ID()] = new ImageIcon(this.getClass().
                getResource("/hp.png")).getImage();
        imgs[ImageID.LOSTHP.ID()] = new ImageIcon(this.getClass().
                getResource("/lostHP.png")).getImage();
        imgs[ImageID.GAMEOVER.ID()] = new ImageIcon(this.getClass().
                getResource("/GameOver.jpg")).getImage();
        imgs[ImageID.HEAL.ID()] = new ImageIcon(this.getClass().
                getResource("/medicine.png")).getImage();
        imgs[ImageID.WALL.ID()] = new ImageIcon(this.getClass().
                getResource("/wall.png")).getImage();
        imgs[ImageID.SPIKES.ID()] = new ImageIcon(this.getClass().
                getResource("/spike.png")).getImage();
        imgs[ImageID.DOOR.ID()] = new ImageIcon(this.getClass().
                getResource("/door.png")).getImage();
        imgs[ImageID.ZOMBIER.ID()] = new ImageIcon(this.getClass().
                getResource("/ZombieRight.png")).getImage();
        imgs[ImageID.KEY.ID()] = new ImageIcon(this.getClass().
                getResource("/key.png")).getImage();
        imgs[ImageID.ZOMBIEL.ID()] = new ImageIcon(this.getClass().
                getResource("/ZombieLeft.png")).getImage();
        imgs[ImageID.ZOMBIEIQL.ID()] = new ImageIcon(this.getClass().
                getResource("/ZombieAuntLeft.png")).getImage();
        imgs[ImageID.ZOMBIEIQR.ID()] = new ImageIcon(this.getClass().
                getResource("/ZombieAuntRight.png")).getImage();
        imgs[ImageID.RUNNERL.ID()] = new ImageIcon(this.getClass().
                getResource("/ZombieRunnerLeft.png")).getImage();
        imgs[ImageID.RUNNERR.ID()] = new ImageIcon(this.getClass().
                getResource("/ZombieRunnerRight.png")).getImage();
        imgs[ImageID.VICTORY.ID()] = new ImageIcon(this.getClass().
                getResource("/Victory.png")).getImage();
    }

    /**
     * Getter na aktualny stav hry.
     *
     * @return GameState - aktualny stav hry
     */
    public GameState getInGameGameState() {
        return inGameGameState;
    }

    /**
     * Setter na aktualny stav hry.
     *
     * @param inGameGameState - aky bude novy stav hry
     */
    public void setInGameGameState(GameState inGameGameState) {
        this.inGameGameState = inGameGameState;
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
                    Game.class.getResourceAsStream(myClip));
            clip.open(inputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
            System.err.println("CANT LOAD SOUND: " + ex.getMessage());
            Logger.getLogger(Javatar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

package cuka.martin.characters;

import cuka.martin.core.GameState;
import cuka.martin.core.IPlayer;
import cuka.martin.map.Block;
import cuka.martin.spell.ISpell;
import cuka.martin.spell.MeteorRain;
import cuka.martin.welcome.StartingPoint;
import java.awt.Graphics;
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
import javax.swing.ImageIcon;

/**
 * Trieda predstavajucu konkretneho Bossa - Wizzarda
 *
 * @author Martin Čuka
 */
public final class Wizzard extends BossCharacter implements IPlayer {

    private static final int IMAGE_OFFSET = 8;
    private static final int CAST_TIME = 550;
    private static final int SPELL_DURATION = 600;

    private final Random random;

    private int castSpell = 0;
    private int duration = 0;
    private boolean castingSpell = false;
    private boolean shoot = false;
    private int num = 0;
    private int frameCount = -1;

    private final Image imgWizz;
    private final Image imgDead;

    /**
     * Vytvori Wizzarda da danej pozicii
     *
     * @param positionX - X-ksova pozicia bossa
     * @param positionY - Y-nova pozicia bossa
     */
    public Wizzard(int positionX, int positionY) {
        super(positionX, positionY);
        random = new Random();
        imgWizz = new ImageIcon(this.getClass().
                getResource("/BOSS1.png")).getImage();
        imgDead = new ImageIcon(this.getClass().
                getResource("/deadWizz.png")).getImage();
    }

    /**
     * Vykresli bossa
     *
     * @param g - Grafika
     * @param gs - aktualny stav hry
     */
    @Override
    public void show(Graphics g, GameState gs) {
        int sizeOfImage = 49;
        int dx = num * sizeOfImage; // nacitavam 0ty riadok obrazku, stlpce sa menia...
        //int dy = 0 * sizeOfImage; nacitava 0ty riadok obrazku... riadok je stale 0....

        if (getHealths() < 1) {
            g.drawImage(imgDead,
                    getPositionX() - Block.getSIZE(),
                    getPositionY() - Block.getSIZE(), null);
        } else {
            g.drawImage(imgWizz,
                    getPositionX() - Block.getSIZE() + 2 * IMAGE_OFFSET,
                    getPositionY() - Block.getSIZE() + IMAGE_OFFSET,
                    getPositionX() - Block.getSIZE() + sizeOfImage * 2 + 2 * IMAGE_OFFSET,
                    getPositionY() - Block.getSIZE() + sizeOfImage * 2 + IMAGE_OFFSET,
                    dx, Block.getSIZE(), dx + sizeOfImage,
                    Block.getSIZE() + sizeOfImage, null);

            if (frameCount % StartingPoint.getDELAY() == 0) {
                num++;
                if (num == 5) { // mam 5 obrazkov wizzarda...
                    num = 0; // nulty obrazok je ako stoji, preto od 1dnotky
                }
            }
        }

        tick();
    }

    /**
     * Vytvori spell na mape
     *
     * @return Objekt reprezentujuci bossove kuzlo ak vrati null nic nevycaroval
     */
    @Override
    public ISpell magic() {
        if (shoot) {
            shoot = false;
            return new MeteorRain(
                    random.nextInt(StartingPoint.getPANEL_WIDTH()
                            - 3 * Block.getSIZE()) + Block.getSIZE(),
                    random.nextInt(Block.getSIZE() * 2) - Block.getSIZE());
        }
        return null;
    }

    /**
     * Algoritmy bossa, co ma kedy spravit...
     */
    private void tick() {

        if (!isStop()) {
            if (!castingSpell) {
                castSpell++;
            }

            if (castSpell >= CAST_TIME) {
                castSpell = 0;
                setOutOfMana(true);
                castingSpell = true;
            }

            if (castingSpell) {
                frameCount++;
                duration++;
                if (duration % 60 == 0) {
                    playSound("/fireball.wav");
                    shoot = true;
                }
                if (duration >= SPELL_DURATION) {
                    duration = 0;
                    castingSpell = false;
                }
            }
        } else {
            duration = 0;
            num = 0;
            frameCount = -1;
        }

    }

    /**
     * Prehra zvuk (fireball)
     *
     * @param myClip Aky zvuk ma prehrat
     */
    @Override
    public void playSound(String myClip) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                    Wizzard.class.getResourceAsStream(myClip));
            clip.open(inputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
            System.err.println("CANT LOAD SOUND: " + ex.getMessage());
            Logger.getLogger(Javatar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

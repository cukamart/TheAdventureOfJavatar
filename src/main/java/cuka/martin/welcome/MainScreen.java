package cuka.martin.welcome;

import cuka.martin.buttons.Clickable;
import cuka.martin.characters.Javatar;
import cuka.martin.core.IPlayer;
import cuka.martin.core.ITicking;
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
 * Vykresli uvodnu obrazovku, uvitanie do hry...
 *
 * @author Martin Čuka
 */
public final class MainScreen extends Clickable implements ITicking, IPlayer {

    private static final int DELAY = 5;
    private static final int FRAMES = 11;

    private int count = 0;
    private int frame = 0;

    private final Image imgMenu;
    private final Image imgChar;

    /**
     * Inicializuje obrazky, ktore budu potrebne na vykreslenie uvodnej
     * obrazovky
     */
    public MainScreen() {
        super(0, 0, StartingPoint.getPANEL_WIDTH(), StartingPoint.getPANEL_HEIGHT());
        imgMenu = new ImageIcon(this.getClass().
                getResource("/mainScreen.png")).getImage();
        imgChar = new ImageIcon(this.getClass().
                getResource("/mainCharacter.png")).getImage();
    }

    /**
     * Vykresluje uvodnu obrazovku
     *
     * @param g - grafika
     */
    public void paint(Graphics g) {
        int sizeOfImage = 100;
        int x = 340;
        int y = 380;
        int dx = frame * sizeOfImage; // nacitavam 0ty riadok obrazku, stlpce sa menia...
        //int dy = 0 * sizeOfImage; nacitava 0ty riadok obrazku... riadok je stale 0....

        g.drawImage(imgMenu, getX(), getY(), null);

        g.drawImage(imgChar, x, y, x + sizeOfImage, y + sizeOfImage,
                dx, 0, dx + sizeOfImage, sizeOfImage, null);

        count++;
        frame = tick(count, frame, DELAY, FRAMES);

        if (count % (DELAY * DELAY) == 0) {
            playSound("/clipStep.wav");
            count = 0;
        }
    }

    /**
     * Prehadzovac framov
     *
     * @param count - pocitadlo refreshnuti
     * @param frame - ktory frame aktualne vykresluje
     * @param delay - delay na prehadzovanie framov
     * @param MAX - index maximalneho framu
     * @return vrati frame ktory sa ma vykreslit
     */
    @Override
    public int tick(int count, int frame, int delay, int MAX) {
        int newFrame = frame;
        if (count % delay == 0) {
            newFrame = frame + 1;
            if (frame == MAX - 1) {
                newFrame = 1;
            }
        }
        return newFrame;
    }

    /**
     * Prehra zvuk
     *
     * @param myClip - aky zvuk ma prehrat (v tomto pripade chodza)
     */
    @Override
    public void playSound(String myClip) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                    MainScreen.class.getResourceAsStream(myClip));
            clip.open(inputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
            System.err.println("CANT LOAD SOUND: " + ex.getMessage());
            Logger.getLogger(Javatar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

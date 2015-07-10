package cuka.martin.welcome;

import cuka.martin.buttons.BackButton;
import cuka.martin.buttons.NextButton;
import cuka.martin.buttons.Clickable;
import cuka.martin.core.GameState;
import cuka.martin.core.ITicking;
import cuka.martin.listeners.loaders.MyKeyListener;
import cuka.martin.listeners.loaders.MyMouseListener;
import cuka.martin.listeners.loaders.MyMouseMotionListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Trieda zobrazi kratke sekvencie obarzkov v ktorych vysvetluje zakladne
 * mechaniky hry ako aj dolezite klavesove skratky na ovladanie behu aplikacie.
 *
 * @author Martin Čuka
 */
class Tutorial implements ITicking {

    private static final int DELAY = 3;
    private static final int TEXT = 50;
    private static final int GAP = 40;
    private static final int HINTX = 85;
    private static final int HINTY = 100;
    private static final int FONT_SIZE = 32;
    private static final int END = 5;

    private int count = 0;
    private int frame = 0;
    private int page = 0;

    private final NextButton nextButton;
    private final BackButton backButton;

    private final Image[] imgPage0 = new Image[72];
    private final Image[] imgPage1 = new Image[57];
    private final Image[] imgPage2 = new Image[77];
    private final Image[] imgPage3 = new Image[72];
    private final Image[] imgPage4 = new Image[95];

    /**
     * Inicializuje stlacitelne tlacitka
     */
    public Tutorial() {
        nextButton = new NextButton();
        backButton = new BackButton();

        loadImages();

    }

    /**
     * Zobrazi stranku tutorialu a vsetky widgety v nom
     *
     * @param g - grafika
     * @return GameState, rozhodne co bolo stlacene
     */
    public GameState paint(Graphics g) {

        showTutorial(g);

        if (page == -1 || MyKeyListener.getInstance().isEsc() || page > END) {
            return GameState.MAINSCREEN;
        }

        paintFrame(g);

        g.setColor(Color.RED);
        g.setFont(new Font("TimesRoman", Font.BOLD, FONT_SIZE));

        if (page == 0) {
            g.drawString("You can press ESC to return to", TEXT, TEXT);
            g.drawString("the main screen at any time !", TEXT, TEXT + GAP);
            showPage(g, imgPage0);
        }

        if (page == 1) {
            g.drawString("Avoid touching zombies. They will hurt you !", TEXT, TEXT);
            showPage(g, imgPage1);
        }

        if (page == 2) {
            g.drawString("You can KILL zombies by jumping on them", TEXT, TEXT);
            showPage(g, imgPage2);
        }

        if (page == 3) {
            g.drawString("These zombies are EXTREMLY dangerous", TEXT, TEXT);
            g.drawString("You won't be able to kill them be careful !", TEXT, TEXT + GAP);
            showPage(g, imgPage3);
        }

        if (page == 4) {
            g.drawString("You need to find a key", TEXT, TEXT);
            g.drawString("in order to finish the game!", TEXT, TEXT + GAP);
            showPage(g, imgPage4);
        }

        if (page == 5) {
            g.drawString("- when facing boss", TEXT, TEXT);
            g.drawString("try survive as long as possible", TEXT, TEXT + GAP);
            g.drawString("- you can close application immediately",
                    TEXT, 2 * GAP + TEXT);
            g.drawString("by pressing ALT+Q simultaneously", TEXT, TEXT + 3 * GAP);
            g.setColor(Color.GREEN);
            g.drawString("Good Luck !", TEXT, TEXT + 5 * GAP);
            g.setColor(Color.YELLOW);
            g.drawString("Game developed by Martin Čuka", TEXT, TEXT + 7 * GAP);
            g.drawString("Fakulta Riadenia a Informatiky", TEXT, TEXT + 8 * GAP);
            g.drawString("Žilinská Univerzita", TEXT, TEXT + 9 * GAP);
        }

        return GameState.TUTORIAL;
    }

    /**
     * Zobrazi aktualnu stranku tutorialu
     *
     * @param g - Grafika
     * @param hint - aktualna sekvencia obrazkov na zobrazenie
     */
    private void showPage(Graphics g, Image[] hint) {
        g.drawImage(hint[frame], HINTX, HINTY, null);
        count++;
        frame = tick(count, frame, DELAY, hint.length);
    }

    /**
     * Zobrazi stranku tutorialu a vsetky widgety v nom....
     *
     * @param g - grafika
     */
    private void showTutorial(Graphics g) {

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, StartingPoint.getPANEL_WIDTH(),
                StartingPoint.getPANEL_HEIGHT());
        nextButton.paint(g);
        backButton.paint(g);

        isClicked(nextButton);
        isClicked(backButton);
    }

    /**
     * Zisti ci uzivatel klikol na tlacidlo ak ano vykona danu operaciu
     *
     * @param myClickable - tlacitko
     */
    private void isClicked(Clickable myClickable) {
        if (myClickable.isHit(MyMouseListener.getInstance().getX(),
                MyMouseListener.getInstance().getY())) {
            if (myClickable instanceof BackButton) {
                page--;
            } else {
                page++;
            }

            frame = 0;
            count = 0;
            MyMouseListener.getInstance().reset();
        }
    }

    /**
     * Vykresli ram okolo tlacidiel
     *
     * @param g - Grafika
     */
    private void paintFrame(Graphics g) {
        isPointing(g, nextButton);
        isPointing(g, backButton);
    }

    /**
     * Vykresli ram okolo tlacitka v pripade ze nanom je myska
     *
     * @param g - Grafika
     * @param myClickable - klikaci button
     */
    private void isPointing(Graphics g, Clickable myClickable) {
        if (myClickable.isHit(MyMouseMotionListener.getInstance().getX(),
                MyMouseMotionListener.getInstance().getY())) {
            g.setColor(Color.RED);
            for (int i = 0; i < 5; i++) {
                g.drawRect(myClickable.getX() - i, myClickable.getY() - i,
                        myClickable.getWidth() + 2 * i, myClickable.getHeight() + 2 * i);
            }
        }
    }

    /**
     * Nacita obrazky potrebne na zobrazenie tutorialu
     */
    private void loadImages() {
        for (int i = 10; i < imgPage0.length + 10; i++) {
            imgPage0[i - 10] = new ImageIcon(this.getClass().
                    getResource("/o_f401a6abddff6efa-" + i + ".jpg")).getImage();
        }

        for (int i = 10; i < imgPage1.length + 10; i++) {
            imgPage1[i - 10] = new ImageIcon(this.getClass().
                    getResource("/o_92f4e26d723f1690-" + i + ".jpg")).getImage();
        }

        for (int i = 0; i < imgPage2.length; i++) {
            imgPage2[i] = new ImageIcon(this.getClass().
                    getResource("/o_1eb0b6996ae9abb7-" + i + ".jpg")).getImage();
        }
        for (int i = 0; i < imgPage3.length; i++) {
            imgPage3[i] = new ImageIcon(this.getClass().
                    getResource("/o_acece83ec24c1fc3-" + i + ".jpg")).getImage();
        }

        for (int i = 0; i < imgPage4.length; i++) {
            imgPage4[i] = new ImageIcon(this.getClass().
                    getResource("/o_f3d853a1fab4a851-" + i + ".jpg")).getImage();
        }
    }

    /**
     * Setter pre aktualnu "stranu" tutorialu, po opusteni tutorialu nastavi na
     * 0 - uvodna strana
     */
    public void setTutorial() {
        this.page = 0;
    }
}

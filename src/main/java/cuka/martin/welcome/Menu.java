package cuka.martin.welcome;

import cuka.martin.buttons.SelectLevelButton;
import cuka.martin.buttons.PlayButton;
import cuka.martin.buttons.Clickable;
import cuka.martin.buttons.TutorialButton;
import cuka.martin.core.GameState;
import cuka.martin.listeners.loaders.MyMouseListener;
import cuka.martin.listeners.loaders.MyMouseMotionListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Vykresli menu hry, komunikuje s platnom a na zaklade volby uzivatela zisti
 * kam dalej
 *
 * @author Martin Čuka
 */
class Menu {

    private final PlayButton play;
    private final SelectLevelButton selectLevel;
    private final TutorialButton tutorial;

    private final Image imgMenu;

    /**
     * Umiestni obrazky na platno, X,Y,sirka,vyska
     */
    public Menu() {
        play = new PlayButton();
        selectLevel = new SelectLevelButton();
        tutorial = new TutorialButton();
        imgMenu = new ImageIcon(this.getClass().
                getResource("/menuScreen.png")).getImage();
    }

    /**
     * Vykresli menu a vsetky widgety v nom
     *
     * @param g Grafika
     * @return GameState -> podla toho co si hrac vybral
     */
    public GameState paint(Graphics g) {
        g.drawImage(imgMenu, 0, 0, null);
        play.paint(g);
        selectLevel.paint(g);
        tutorial.paint(g);

        paintFrame(g);

        if (selectLevel.isHit(MyMouseListener.getInstance().getX(),
                MyMouseListener.getInstance().getY())) {
            return GameState.SELECTLEVEL;
        }
        if (play.isHit(MyMouseListener.getInstance().getX(),
                MyMouseListener.getInstance().getY())) {
            return GameState.INGAME;
        }

        if (tutorial.isHit(MyMouseListener.getInstance().getX(),
                MyMouseListener.getInstance().getY())) {
            return GameState.TUTORIAL;
        }

        return GameState.MENU;
    }

    /**
     * Vykresli cerveny ramik okolo volby v menu pokial je tam myska....
     *
     * @param g - Grafika
     */
    private void paintFrame(Graphics g) {
        isPointing(g, selectLevel);
        isPointing(g, play);
        isPointing(g, tutorial);
    }

    /**
     * Zisti ci na tlacitko ukazuje myska
     *
     * @param g - grafika
     * @param myClickable - klikaci button
     */
    private void isPointing(Graphics g, Clickable myClickable) {
        if (myClickable.isHit(MyMouseMotionListener.getInstance().getX(),
                MyMouseMotionListener.getInstance().getY())) {
            g.setColor(Color.RED);
            for (int i = 0; i < 5; i++) {
                g.drawRect(myClickable.getX() - i, myClickable.getY() - i,
                        myClickable.getWidth() + 2 * i, myClickable.getHeight()
                        + 2 * i);
            }
        }
    }
}

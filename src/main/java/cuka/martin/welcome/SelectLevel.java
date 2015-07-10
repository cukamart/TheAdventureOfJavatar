package cuka.martin.welcome;

import cuka.martin.buttons.BossOneButton;
import cuka.martin.buttons.Clickable;
import cuka.martin.buttons.LevelOneButton;
import cuka.martin.buttons.YouTubeButton;
import cuka.martin.core.GameState;
import cuka.martin.listeners.loaders.MyMouseListener;
import cuka.martin.listeners.loaders.MyMouseMotionListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.ImageIcon;

/**
 * Trieda je aktivna ked si uzivatel vybera level, komunikuje bezprostredne s
 * "platnom" a na zaklade volby uzivatela sa rozhode kam pojde dalej
 *
 * @author Martin Čuka
 */
class SelectLevel {

    private static final int FONT_SIZE = 22;

    private final LevelOneButton levelOne;
    private final BossOneButton bossOne;
    private final YouTubeButton[] youTube = new YouTubeButton[2];

    private final Image imgMenu;
    private final UrlBrowser urlBrowser;

    public SelectLevel() {
        urlBrowser = new UrlBrowser();
        levelOne = new LevelOneButton();
        bossOne = new BossOneButton();
        imgMenu = new ImageIcon(this.getClass().
                getResource("/menuScreen.png")).getImage();
        youTube[0] = new YouTubeButton(415, 75);
        youTube[1] = new YouTubeButton(115, 375);

    }

    /**
     * Vykresli aplikaciu v stave ked si uzivatel vybera level, navratova
     * hodnota predstavuje uzivatelovu volbu
     *
     * @param g Grafika
     * @return GameState -> podla toho co si hrac vybral
     */
    public GameState paint(Graphics g) {
        g.drawImage(imgMenu, 0, 0, null);
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.ITALIC, FONT_SIZE));
        g.drawString("Adventure walkthrough", 435, 115);
        g.drawString("Boss walkthrough", 155, 415);
        levelOne.paint(g);
        bossOne.paint(g);
        youTube[0].paint(g);
        youTube[1].paint(g);

        paintFrame(g);

        if (levelOne.isHit(MyMouseListener.getInstance().getX(),
                MyMouseListener.getInstance().getY())) {
            return GameState.INGAME;
        }
        if (bossOne.isHit(MyMouseListener.getInstance().getX(),
                MyMouseListener.getInstance().getY())) {
            return GameState.BOSSFIGHT;
        }

        try {
            openUrl();
        } catch (URISyntaxException ex) {
            System.err.println("Cannot open given URI " + ex.getMessage());
        }

        return GameState.SELECTLEVEL;
    }

    /**
     * Vykresli cerveny ramik okolo volby v menu pokial je tam myska....
     *
     * @param g - Grafika
     */
    private void paintFrame(Graphics g) {
        isPointing(g, levelOne);
        isPointing(g, bossOne);
        isPointing(g, youTube[0]);
        isPointing(g, youTube[1]);
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
        } else {
            g.setColor(Color.BLUE);
        }

        for (int i = 0; i < 5; i++) {
            g.drawRect(myClickable.getX() - i, myClickable.getY() - i,
                    myClickable.getWidth() + 2 * i, myClickable.getHeight() + 2 * i);
        }
    }

    /**
     * V pripade ze uzivatel klikne na tlacidlo youtube, presmeruje ho na
     * youtube kanal s "video navodom" ako prejst hru.
     */
    private void openUrl() throws URISyntaxException {
        if (youTube[0].isHit(MyMouseListener.getInstance().getX(),
                MyMouseListener.getInstance().getY())) {
            urlBrowser.openUrlAddress(new URI("https://www.youtube.com/watch?v=nawJtsiwEPo"));
            MyMouseListener.getInstance().reset();
        }
        if (youTube[1].isHit(MyMouseListener.getInstance().getX(),
                MyMouseListener.getInstance().getY())) {
            urlBrowser.openUrlAddress(new URI("https://www.youtube.com/watch?v=8VsQHPl5Wac"));
            MyMouseListener.getInstance().reset();
        }
    }
}

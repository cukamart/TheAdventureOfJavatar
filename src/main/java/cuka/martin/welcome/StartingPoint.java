package cuka.martin.welcome;

import cuka.martin.core.Game;
import cuka.martin.core.GameState;
import cuka.martin.listeners.loaders.MyKeyListener;
import cuka.martin.listeners.loaders.MyMouseListener;
import cuka.martin.listeners.loaders.MyMouseMotionListener;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

/**
 * Vstupny bod programu, rozdeluje aplikaciu na niekolko funkcnych blokov ako je
 * napr select level / play / how to play. Na zaklade aktualneho stavu aplikacie
 * komunikuje s niektorou z tychto 3 casti a posiela jej spravy. Vyuziva pri tom
 * specialny typ vlakna tzv. SwingWorker
 *
 * @author Martin Čuka
 */
public class StartingPoint extends JPanel {

    private static final int GAP = 12;
    private static final int PANEL_WIDTH = 800;
    private static final int PANEL_HEIGHT = 600;
    private static final int CENTERX = StartingPoint.getPANEL_HEIGHT() / 2 + 50; // stred obrazovky X
    private static final int CENTERY = StartingPoint.getPANEL_HEIGHT() / 2 - 50; // stred obrazovky Y
    private static final int FPS = 17; // 60 fps (1000ms/60) = 17 => 56,6 fps...
    private static final int DELAY = 5;

    private final MainScreen mainScreen;
    private final Menu menu;
    private final Game game;
    private final Tutorial tutorial;
    private final SelectLevel selectLevel;

    private GameState gameState;
    private boolean isRunning = true;
    private boolean firstTime = true;
    private String level;

    /**
     * Vstupny bod programu, vytvori a inicializuje najpodstatnejsie triedy
     * nevyhnutne pre beh programu, nastavi zakladne vlastnosti samotnemu
     * platnu.
     */
    public StartingPoint() {
        setPreferredSize(new Dimension(PANEL_WIDTH - GAP, PANEL_HEIGHT - GAP));
        setFocusable(true);
        this.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        addMouseListener(MyMouseListener.getInstance());
        addKeyListener(MyKeyListener.getInstance());
        addMouseMotionListener(MyMouseMotionListener.getInstance());

        setFocusable(true);

        mainScreen = new MainScreen();
        menu = new Menu();
        selectLevel = new SelectLevel();
        game = new Game();
        tutorial = new Tutorial();
        gameState = GameState.MAINSCREEN;
        level = "level";
    }

    /**
     * Odstartuje specialne vlakno (SwingWorker) riadiace hru. Tymto sposobom je
     * v swingu vsetko bezpecne synchronizovane narozdiel od sposobu ked
     * implementujem vlakno rovno a pretazim metodu run.
     */
    public void start() {
        new WorkerThread().execute();
    }

    /**
     * Metoda paintComponent zo swingu vykresli udalosti na platno. Narozdiel od
     * 'awt paint metody' robi double buffering defaultne....
     *
     * @param g - Grafika
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        switch (gameState) {
            case MAINSCREEN:
                paintMainScreen(g);
                break;
            case MENU:
                paintMenu(g);
                break;
            case INGAME:
                paintGame(g);
                break;
            case SELECTLEVEL:
                paintSelectLevel(g);
                break;
            case TUTORIAL:
                paintTutorial(g);
                break;
            default:
                throw new AssertionError();
        }
    }

    /**
     * Vykresli grafiku pre tutorial hry.
     *
     * @param g - Grafika
     */
    private void paintTutorial(Graphics g) {
        if (firstTime) {
            tutorial.setTutorial();
            firstTime = false;
        }
        gameState = tutorial.paint(g);
    }

    /**
     * Vykresluje hru.
     *
     * @param g - Grafika
     */
    private void paintGame(Graphics g) {
        if (firstTime) {
            try {
                game.loadMap(level, 1);
                firstTime = false;
            } catch (IOException ex) {
                System.err.println("IOXception " + ex.getMessage());
            }
        }

        game.tick();
        game.show(g);

        if (game.getInGameGameState() == GameState.MAINSCREEN) {
            firstTime = true;
            game.setInGameGameState(GameState.RESET);
            gameState = GameState.MAINSCREEN;
        }

        MyMouseListener.getInstance().reset();
        if (game.getInGameGameState() == GameState.RESET) {
            firstTime = true;
            gameState = GameState.MAINSCREEN;
        }
    }

    /**
     * Vykresluje menu.
     *
     * @param g - Grafika
     */
    private void paintMenu(Graphics g) {
        level = "level";
        gameState = menu.paint(g);
        MyMouseListener.getInstance().reset();
        firstTime = true;
    }

    /**
     * Vykresluje uvodnu obrazovku.
     *
     * @param g- Grafika
     */
    private void paintMainScreen(Graphics g) {
        level = "level";
        mainScreen.paint(g);
        if (mainScreen.isHit(MyMouseListener.getInstance().getX(),
                MyMouseListener.getInstance().getY())
                || MyKeyListener.getInstance().isKey()) {
            gameState = GameState.MENU;
            MyMouseListener.getInstance().reset();
        }
        firstTime = true;
    }

    /**
     * Vykresluje vyber levelov.
     *
     * @param g - Grafika
     */
    private void paintSelectLevel(Graphics g) {
        if (MyKeyListener.getInstance().isEsc()) {
            gameState = GameState.MAINSCREEN;
        }
        GameState gs = selectLevel.paint(g);
        if (gs == GameState.INGAME) {
            gameState = GameState.INGAME;
        } else if (gs == GameState.BOSSFIGHT) {
            game.setInGameGameState(GameState.BOSSFIGHT);
            gameState = GameState.INGAME;
            level = "boss";
        }
    }

    /**
     * Getter pre sirku platna.
     *
     * @return vrati sirku platna
     */
    public static int getPANEL_WIDTH() {
        return PANEL_WIDTH;
    }

    /**
     * Getter pre vysku platna.
     *
     * @return vrati vysku platna
     */
    public static int getPANEL_HEIGHT() {
        return PANEL_HEIGHT;
    }

    /**
     * Getter pre bod reprezentujuci stred platna na X-ksovej osy.
     *
     * @return stred platna na X-ksovej osy
     */
    public static int getCENTERX() {
        return CENTERX;
    }

    /**
     * Getter pre bod reprezentujuci stred platna na Y-novej osy.
     *
     * @return stred platna na Y-novej osy
     */
    public static int getCENTERY() {
        return CENTERY;
    }

    /**
     * Getter pre konstantu udavajuci delay (napr na prehadzovanie framov).
     *
     * @return vrati int hodnotu udavajucu rychlost prehadzovania framov
     */
    public static int getDELAY() {
        return DELAY;
    }

    /**
     * Privatna trieda, ktora odstartuje vlakno riadiace hru, touto technikou
     * dostavam v swingu stabilnejsie a hlavne thread safe program ako keby som
     * rovno implementoval obycajne vlakno....
     */
    private class WorkerThread extends SwingWorker<String, Object> {

        /**
         * Metoda spusta hlavne vlakno hry a refreshuje ju v ramci moznosti na
         * 60 FPS.
         */
        @Override
        protected String doInBackground() throws Exception {
            while (isRunning) {
                if (MyKeyListener.getInstance().isExit()) {
                    isRunning = false;
                }
                repaint(); // call update, clear screen and call paint method
                try {
                    Thread.sleep(FPS); // ....nepotrebujem presne 60 fps....
                } catch (InterruptedException ex) {
                    System.err.println("InterruptedException: " + ex.getMessage());
                }
            }
            return "Thanks for playing";
        }

        /**
         * Pri vypnuti skratkou ALT+Q vypise spravu na terminal :-).
         */
        @Override
        protected void done() {
            try {
                System.out.println(get());
            } catch (InterruptedException | ExecutionException ignore) {
                System.err.println(ignore.getMessage());
            } finally {
                Runtime.getRuntime().halt(0); // spravne nenasilne vypnutie
            }
        }
    }
}

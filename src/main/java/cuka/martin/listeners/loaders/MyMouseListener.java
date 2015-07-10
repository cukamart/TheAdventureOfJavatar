package cuka.martin.listeners.loaders;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Pocuva na kliknutia mysov Singleton
 *
 * @author Martin Čuka
 */
public class MyMouseListener implements MouseListener {

    private static MyMouseListener instance = null;
    private static final int DONT_CLICK = -1; // kliknutie mysky mimo obrazovky

    private int x;
    private int y;

    /**
     * Vrati instanciu triedy ktora zistuje kde uzivatel klikol
     *
     * @return instancia triedy pocuvajuca kliknutia mysi
     */
    public static MyMouseListener getInstance() {
        synchronized (MyMouseListener.class) {
            if (instance == null) {
                instance = new MyMouseListener();
            }
        }
        return instance;
    }

    /**
     * Vytvori pocuvac kliknuti mysi, nazaciatku nastavy na nezmyselne hodnoty,
     * uzivatel neklikol
     */
    private MyMouseListener() {
        x = DONT_CLICK;
        y = DONT_CLICK;
    }

    @Override
    public void mouseClicked(MouseEvent me) {

    }

    /**
     * Zisti kde uzivatel klikol tato metoda je lepsia ako mouseClicked pretoze
     * zareaguje aj na klik v pohybe...
     *
     * @param me suradnice kliknutia
     */
    @Override
    public void mousePressed(MouseEvent me) {
        x = me.getX();
        y = me.getY();
    }

    @Override
    public void mouseReleased(MouseEvent me) {

    }

    @Override
    public void mouseEntered(MouseEvent me) {

    }

    @Override
    public void mouseExited(MouseEvent me) {

    }

    /**
     * Getter pre X-ksovu suradnicu kliku mysi
     *
     * @return vrati X-ksovu suradnicu kliku mysi
     */
    public int getX() {
        return x;
    }

    /**
     * Getter pre Y-novu suradnicu kliku mysi
     *
     * @return vrati Y-novu suradnicu kliku mysi
     */
    public int getY() {
        return y;
    }

    /**
     * Nastavi pocuvac kliknuti mysi na hodnoty ako keby este uzivatel nikdy
     * neklikol
     */
    public void reset() {
        x = DONT_CLICK;
        y = DONT_CLICK;
    }
}

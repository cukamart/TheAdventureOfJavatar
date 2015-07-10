package cuka.martin.listeners.loaders;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Pocuva na pohyb mysi - Singleton
 *
 * @author Martin Čuka
 */
public class MyMouseMotionListener implements MouseMotionListener {

    private static MyMouseMotionListener instance = null;

    private int x;
    private int y;

    /**
     * Vrati instanciu triedu pocuvajucej na pohyb mysi
     *
     * @return instancia triedy pocuvajuca na pohyb mysi
     */
    public static MyMouseMotionListener getInstance() {
        synchronized (MyMouseListener.class) {
            if (instance == null) {
                instance = new MyMouseMotionListener();
            }
        }
        return instance;
    }

    private MyMouseMotionListener() {

    }

    /**
     * Aktualna X-ksova pozicia mysi
     *
     * @return X-ksovu poziciu mysi
     */
    public int getX() {
        return x;
    }

    /**
     * Aktualna Y-nova pozicia mysi
     *
     * @return Y-novu poziciu mysi
     */
    public int getY() {
        return y;
    }

    @Override
    public void mouseDragged(MouseEvent me) {

    }

    /**
     * Aktivuje sa pri pohybe mysi a aktualizuje suradnice
     *
     * @param me nove suradnice aktualnej polohy
     */
    @Override
    public void mouseMoved(MouseEvent me) {
        this.x = me.getX();
        this.y = me.getY();
    }

}

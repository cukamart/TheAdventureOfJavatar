package cuka.martin.listeners.loaders;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Pocuvac klavesnice
 *
 * @author Martin Čuka
 */
public class MyKeyListener implements KeyListener {

    private static MyKeyListener instance = null;

    private boolean left;
    private boolean right;
    private boolean up;
    private boolean esc;
    private boolean key;
    private boolean alt;
    private boolean qButton;

    /**
     * Vrati instanciu triedu ktora zistuje aku klavesu uzivatel klikol
     *
     * @return instancia triedy pocuvaju na kliknutia klaves
     */
    public static MyKeyListener getInstance() {
        synchronized (MyKeyListener.class) {
            if (instance == null) {
                instance = new MyKeyListener();
            }
        }
        return instance;
    }

    private MyKeyListener() {

    }

    /**
     * Ak uzivatel pustil klavesu nastavi na false
     *
     * @param ke hodnota stlacenej klavesy
     */
    @Override
    public void keyReleased(KeyEvent ke) {
        checkMovement(ke, false);
        if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
            esc = false;
        }
        if (ke.getKeyCode() == KeyEvent.VK_ALT ) {
            alt = false;
        }
        if (ke.getKeyCode() == KeyEvent.VK_Q){
            qButton = false;
        }
    }

    /**
     * Ak uzivatel stlacil klavesu (drzi) nastavi hodnotu danej klavesy na true
     *
     * @param ke hodnota stlacenej klavesy
     */
    @Override
    public void keyPressed(KeyEvent ke) {
        key = true;
        checkMovement(ke, true);
        if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
            esc = true;
        }
        if (ke.getKeyCode() == KeyEvent.VK_ALT ) {
            alt = true;
        }
        if (ke.getKeyCode() == KeyEvent.VK_Q){
            qButton = true;
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    /**
     * Zisti ci sa pohybuje Javatar niektorym smerom
     *
     * @param ke - hodnota klavesy pre pripadny pohyb
     * @param isPressed true - je stlacena klavesa, false - nieje stlacena
     */
    private void checkMovement(KeyEvent ke, boolean isPressed) {
        if (ke.getKeyCode() == KeyEvent.VK_A) {
            left = isPressed;
        }
        if (ke.getKeyCode() == KeyEvent.VK_D) {
            right = isPressed;
        }
        if (ke.getKeyCode() == KeyEvent.VK_W) {
            up = isPressed;
        }
    }

    /**
     * Getter ci je stlacena klavesa pre pohyb dolava
     *
     * @return true, je stlacena, false nieje stlacena
     */
    public boolean isLeft() {
        return left;
    }

    /**
     * Getter ci je stlacena klavesa pre pohyb doprava
     *
     * @return true, je stlacena, false nieje stlacena
     */
    public boolean isRight() {
        return right;
    }

    /**
     * Getter ci je stlacena klavesa pre pohyb hore
     *
     * @return true, je stlacena, false nieje stlacena
     */
    public boolean isUp() {
        return up;
    }

    /**
     * Getter ci je stlacena klavesa pre navrat do menu
     *
     * @return true, je stlacena, false nieje stlacena
     */
    public boolean isEsc() {
        return esc;
    }

    /**
     * Getter ci je stlacena hocijaka klavesa
     *
     * @return true, je stlacena, false nieje stlacena
     */
    public boolean isKey() {
        return key;
    }

    /**
     * Resetuje vsetky klavesy akokeby este nikdy neboli stlacene
     */
    public void reset() {
        left = false;
        right = false;
        up = false;
        esc = false;
        key = false;
    }
    
    /**
     * Ak uzivatel stlaci alt+Q aplikaciu ukonci
     * @return true, vypne sa aplikacia, false aplikacia bezi
     */
    public boolean isExit(){
        return alt && qButton;
    }

}

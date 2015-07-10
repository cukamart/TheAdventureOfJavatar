package cuka.martin.buttons;

/**
 * Abstraktna trieda Clickable - dedi od nej kazdy klikatelny objekt, ak
 * uzivatel klikne tato trieda urci ci klikol nanho alebo nie...
 *
 * @author Martin Čuka
 */
public abstract class Clickable {

    private final int x; //[X,]
    private final int y; //[,Y]
    private final int width; //sirka
    private final int height; //vyska

    /**
     * Nastavi poziciu objektu.
     *
     * @param x poziciaX
     * @param y poziciaY
     * @param width sirka
     * @param height vyska
     */
    protected Clickable(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Zisti ci je objekt kliknuty.
     *
     * @param x x-ksova suradnica kliku mysi
     * @param y y-pnova suradnica kliku mysi
     * @return true - ak je kliknuty objekt inak false
     */
    public boolean isHit(int x, int y) {
        return x >= getX() && x <= getX() + getWidth()
                && y >= getY() && y <= getY() + getHeight();
    }

    /**
     * Getter pre X-ksovu suradnicu tlacidla.
     *
     * @return x-ksova suradnica tlacidla
     */
    public int getX() {
        return x;
    }

    /**
     * Getter pre Y-novu suradnicu tlacidla.
     *
     * @return Y-nova suradnica tlacidla
     */
    public int getY() {
        return y;
    }

    /**
     * Getter pre sirku tlacidla.
     *
     * @return sirka tlacidla
     */
    public int getWidth() {
        return width;
    }

    /**
     * Getter pre vysku tlacidla.
     *
     * @return vyska tlacidla
     */
    public int getHeight() {
        return height;
    }
}

package cuka.martin.map;

import cuka.martin.welcome.StartingPoint;

/**
 * Objekty ktore sa zobrazuju na zaklade pozicie hraca implementuju toto
 * rozhranie, objekty zistia relativnu vzdialenost od pozicie hraca a na zaklade
 * toho sa vykreslia
 *
 * @author Martin Čuka
 */
public interface ICamera {

    /**
     * Skontroluje ci ma dany objekt vykreslit na zaklade od pozicie hraca... Ak
     * hracova pozicia od objektu je vo vzdialenosti velkosti obrazovky, tak ho
     * vykresli...
     *
     * @param positionX vypocita sa ako ObjektX - hracX
     * @param positionY vypocita sa ako ObjektY - hracY
     * @return true - ak je v dosahu na vykreslenie, false - nevykresli
     */
    default boolean isInRange(int positionX, int positionY) {
        return positionX >= -StartingPoint.getCENTERX() - Block.getSIZE()
                && positionX <= StartingPoint.getCENTERX() + Block.getSIZE() * 2
                && positionY >= -StartingPoint.getCENTERY() - Block.getSIZE()
                && positionY <= StartingPoint.getCENTERY() + Block.getSIZE() * 2;
    }
}

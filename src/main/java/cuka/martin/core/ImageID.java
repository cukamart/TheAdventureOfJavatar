package cuka.martin.core;

/**
 * ID-cka obrazkov. Namiesto 20tich atributov mozem teraz prehladne nacitat
 * obrazky do pola... imgs[ImageID.SPIKES.ID()] = new File("....."); Vyhodou je
 * ze nepotrebujem vediet ze ake cislo ma dany obrazok... Jednoducho staci
 * napisat imgs[ImageID.SPIKES.ID()] = new ..; a viem ze nacitavam obrazok pre
 * pichliace :-)
 *
 * @author Martin Čuka
 */
public enum ImageID {

    MOVE(0),
    JUMP(1),
    HASKEY(2),
    NOKEY(3),
    BACKGROUND(4),
    HP(5),
    LOSTHP(6),
    GAMEOVER(7),
    HEAL(8),
    WALL(9),
    SPIKES(10),
    DOOR(11),
    ZOMBIER(12),
    ZOMBIEL(13),
    KEY(14),
    ZOMBIEIQL(15),
    ZOMBIEIQR(16),
    RUNNERL(17),
    RUNNERR(18),
    VICTORY(19);

    private final int ID;

    /**
     * Priradi cislo obrazku
     *
     * @param ID cislo obrazku
     */
    ImageID(int ID) {
        this.ID = ID;
    }

    /**
     * Getter pre ID obrazku
     *
     * @return ID hodnota obrazku
     */
    public int ID() {
        return ID;
    }
}

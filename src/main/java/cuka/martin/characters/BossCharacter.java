package cuka.martin.characters;

import cuka.martin.spell.ISpell;

/**
 * Abstraktna trieda rozsirujuca rozhranie triedy GAMECharacter pre potreby
 * bossov.
 *
 * @author Martin Čuka
 */
public abstract class BossCharacter extends GameCharacter {

    private boolean outOfMana;
    private boolean stop;
    private int healths;

    /**
     * Vytvori bossa na danom mieste
     *
     * @param positionX - X-ksova pozicia
     * @param positionY - Y-nova pozicia
     */
    BossCharacter(int positionX, int positionY) {
        super(positionX, positionY, 50, 50, 0);
        this.healths = 3;
    }

    /**
     * Kuzlo bossa.
     *
     * @return spell bossa, musi implementovat interface ISpell
     */
    public abstract ISpell magic();

    /**
     * Getter bossovej many true - uz nema, false - este ma.
     *
     * @return - outOfMana zisti ci moze este boss carovat.
     */
    public boolean isOutOfMana() {
        return this.outOfMana;
    }

    /**
     * Setter na bossovu manu
     *
     * @param outOfMana - mana (true/false)
     */
    public void setOutOfMana(boolean outOfMana) {
        this.outOfMana = outOfMana;
    }

    /**
     * Getter pre atribut ktory rozhoduje o chodu bossa
     *
     * @return false - boss nic nerobi true - boss je pri zivote
     */
    boolean isStop() {
        return this.stop;
    }

    /**
     * Setter pre atribut ktory rozhoduje o chodu bossa
     *
     * @param stop - true - boss nic nerobi, false - je pri zivote
     */
    public void setStop(boolean stop) {
        this.stop = stop;
    }

    /**
     * Getter na zivoty bossa
     *
     * @return healths - zivoty bossa
     */
    public int getHealths() {
        return this.healths;
    }

    /**
     * Setter na zivoty bossa
     *
     * @param healths - int hodnota udavajuca novu hodnotu zivotov bossa
     */
    public void setHealths(int healths) {
        this.healths = healths;
    }
}

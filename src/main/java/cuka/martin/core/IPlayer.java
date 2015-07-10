package cuka.martin.core;

/**
 * Prehravac zvukov, kazda trieda ktora prehrava zvuky implementuje pre zvysenu
 * prehladnost kodu toto rozhranie
 *
 * @author Martin Čuka
 */
public interface IPlayer {

    /**
     * Prehra urcity zvuk.
     * @param myClip - aky zvuk ma prehrat
     */
    void playSound(String myClip);
}

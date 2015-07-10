package cuka.martin.core;

/**
 * Casovac, meni frame pohyblivych obrazkov
 *
 * @author Martin Čuka
 */
public interface ITicking {

    /**
     * Casuje prepinanie obrazkov / zvukov
     *
     * @param count - pocitadlo
     * @param frame - frame obrazku / zvuku
     * @param delay - delay
     * @param MAX - maximalny pocet framov
     * @return aktualny frame
     */
    default int tick(int count, int frame, int delay, int MAX) {
        int newFrame = frame;
        if (count % delay == 0) {
            newFrame = frame + 1;
            if (frame == MAX - 1) {
                newFrame = 0;
            }
        }
        return newFrame;
    }
}

package cuka.martin.map;

import cuka.martin.core.ITicking;
import cuka.martin.spell.MeteorRain;
import cuka.martin.welcome.StartingPoint;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Reprezentuje ciel aktualneho levelu, ak sa k nemu Javatar dostane dany level
 * konci
 *
 * @author Martin Čuka
 */
public class Finish extends Block implements ITicking {

    private static final Image[] imgFlag = new Image[6];

    static {
        for (int i = 0; i < imgFlag.length; i++) {
            imgFlag[i] = new ImageIcon(MeteorRain.class.
                    getResource("/" + i + "flag.png")).getImage();
        }

    }

    private static final int DELAY = 5;
    private static final int FRAMES = 6;
    private static final int IMAGEOFFSET = -50;

    private int count = 0;
    private int frame = 0;

    /**
     * Nastavi poziciu ciela
     *
     * @param x - X-ksova pozicia ciela
     * @param y - Y-nova pozicia ciela
     */
    public Finish(int x, int y) {
        super(x, y, imgFlag[0]);
    }

    /**
     * Vykresli ciela v obycajnom leveli na zaklade javatarovej pozicie (s nim
     * sa hybe kamera)
     *
     * @param g - Grafika
     * @param posJavatarX - Javatarova pozicia X
     * @param posJavatarY - Javatarova pozicia Y
     */
    @Override
    public void show(Graphics g, int posJavatarX, int posJavatarY) {
        if (isInRange(getPositionX() - posJavatarX, getPositionY() - posJavatarY)) {
            g.drawImage(imgFlag[frame],
                    StartingPoint.getCENTERX() + (getPositionX() - posJavatarX),
                    StartingPoint.getCENTERY() + IMAGEOFFSET + (getPositionY() - posJavatarY),
                    null);

            count++;
            frame = tick(count, frame, StartingPoint.getDELAY(), FRAMES);
        }
    }

    /**
     * V pripade bossa sa vykresli ciela na svojej skutocnej pozicii (kamera sa
     * nehybe)
     *
     * @param g - Grafika
     */
    @Override
    public void showBossFight(Graphics g) {
        g.drawImage(imgFlag[frame], getPositionX(), getPositionY(), null);

        count++;
        frame = tick(count, frame, DELAY, FRAMES);
    }
}

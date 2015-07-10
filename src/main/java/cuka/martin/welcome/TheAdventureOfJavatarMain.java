package cuka.martin.welcome;

import cuka.martin.netbeans.gui.ExitDialog;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 * Hlavna trieda, vytvori okno, spusti hru.
 *
 * @author Martin Čuka
 */
public class TheAdventureOfJavatarMain extends JFrame {

    /**
     * Nastavi zakladne vlastnosti "platna" na ktore sa bude hra vykreslovat
     */
    private TheAdventureOfJavatarMain() {
        String title = "The Adventure of Javatar Martin Cuka 5ZI021";
        setTitle(title);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        StartingPoint sp = new StartingPoint();
        add(sp);

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        sp.start();
    }

    /**
     * Spusti aplikaciu.
     *
     * @param args parametre prikazoveho riadku
     */
    public static void main(String[] args) {
        TheAdventureOfJavatarMain javatar = new TheAdventureOfJavatarMain();
        SwingUtilities.invokeLater(() -> {
            javatar.setVisible(true);
            windowAdapter(javatar);
        });

    }

    /**
     * Metoda prida windowsListener do hlavneho okna, odteraz po kliknuti na
     * krizik sa nezatvori aplikacia hned ale vyskoci dalsie okno kde bude
     * uzivatel vyzvany na potvrdenie svojej volby o zatvorenie aplikacie
     *
     * @param javatar instancia hlavneho platna
     */
    private static void windowAdapter(TheAdventureOfJavatarMain javatar) {
        javatar.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ExitDialog exitFrame = new ExitDialog();
                exitFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                exitFrame.setLocationRelativeTo(null);
                exitFrame.setResizable(false);
                exitFrame.setVisible(true);
            }
        });
    }

}

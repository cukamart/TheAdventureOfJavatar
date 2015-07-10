package cuka.martin.welcome;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

/**
 * Trieda otvara webove stranky na zaklade URL adresy
 *
 * @author Martin Čuka
 */
class UrlBrowser {

    private final static Desktop desktop = Desktop.getDesktop();

    /**
     * Otvori konkretnu webovu stranku zadanu ako parameter defaultnym webovym
     * prehliadacom
     *
     * @param address - odkaz na stranku ktoru chceme otvorit
     */
    public void openUrlAddress(URI address) {
        try {
            desktop.browse(address);
        } catch (IOException ex) {
            System.err.println("Cannot display the webpage: " + address
                    + " " + ex.getMessage());
        }
    }

}

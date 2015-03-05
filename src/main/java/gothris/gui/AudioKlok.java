
package gothris.gui;

import java.io.BufferedInputStream;
import java.io.IOException;
import javax.sound.sampled.*;
import java.io.InputStream;

public class AudioKlok {
    private Clip klok;
    
    public AudioKlok(String tiedostonNimi) {
        try {
            InputStream is = this.getClass().getResourceAsStream(tiedostonNimi);
            InputStream bufferedIs = new BufferedInputStream(is);
            AudioInputStream aaniraita = AudioSystem.getAudioInputStream(bufferedIs);
            this.klok = AudioSystem.getClip();
            this.klok.open(aaniraita);
        } catch ( UnsupportedAudioFileException | IOException | LineUnavailableException e ) {
            System.out.println("Couldn't load audio " + e.getMessage());
        }
    }
    
    public void play() {
        this.klok.setFramePosition(0);
        this.klok.start();
    }
    
    public void stop() {
        this.klok.stop();
    }
}

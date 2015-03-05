
package gothris.gui;

import javax.sound.sampled.*;
import java.io.*;

public class AudioKlik {
    private Clip klik;
    
    public AudioKlik(String tiedostonNimi) {
        try {
            InputStream is = this.getClass().getResourceAsStream(tiedostonNimi);
            InputStream bufferedIs = new BufferedInputStream(is);
            AudioInputStream aaniraita = AudioSystem.getAudioInputStream(bufferedIs);
            this.klik = AudioSystem.getClip();
            this.klik.open(aaniraita);
        } catch ( UnsupportedAudioFileException | IOException | LineUnavailableException e ) {
            System.out.println("No can do! " + e.getMessage());
        }
    }
    
    public void play() {
        this.klik.setFramePosition(0);
        this.klik.start();
    }
    
    public void stop() {
        this.klik.stop();
    }
}


package gothris.gui;

import java.io.*;
import javax.sound.sampled.*;

public class TaustaMusiikki {
    private Clip patka;
    private boolean pyorii;
    private FloatControl vahvistus;
    private int framePosition;
    
    public TaustaMusiikki(String tiedostonNimi) throws Exception {
        try {
            InputStream is = this.getClass().getResourceAsStream(tiedostonNimi);
            InputStream bufferedIs = new BufferedInputStream(is);
            AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIs);
            this.patka = AudioSystem.getClip();
            this.patka.open(ais);
        } catch ( UnsupportedAudioFileException | IOException | LineUnavailableException anythingCouldHappen ) {
            System.out.println("No can do! " + anythingCouldHappen.getMessage());
        }
        
        this.pyorii = false;
        this.vahvistus = (FloatControl) this.patka.getControl(FloatControl.Type.MASTER_GAIN);
        System.out.println(vahvistus.getValue());
    }
    
    public void play() {
        this.patka.setFramePosition(0);
        this.pyorii = true;
        this.patka.start();
        
    }
    
    public void loop() {
        this.patka.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public void stop() {
        this.patka.stop();
        this.framePosition = this.patka.getFramePosition();
        this.pyorii = false;
    }

    public boolean pyorii() {
        return this.pyorii;
    }
    
    public void jatka() {
        this.patka.setFramePosition(this.framePosition);
        loop();
    }
}

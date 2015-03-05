
package gothris.gui;

import gothris.domain.palat.Kompositio;
import gothris.domain.palat.VanhaPala;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import gothris.pelilogiikka.Peli;

public class Pelialusta extends JPanel {
    private Peli peli;
    private Kompositio palanen;
    private Image tausta;
    private Image pause;
    
    public Pelialusta(Peli peli) {
        this.peli = peli;
        
        try {
            this.tausta = ImageIO.read(this.getClass().getResourceAsStream(this.peli.getTaustat().get(1)));
        } catch ( Exception e ) {
            System.out.println("Couldn't load the image: " + e.getMessage());
        }
        
        try {
            this.pause = ImageIO.read(this.getClass().getResourceAsStream("/graphics/pause.png"));
        } catch ( Exception e ) {
            System.out.println("Couldn't load the image: " + e.getMessage());
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
                
        super.paintComponent(g2);
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawImage(this.tausta, 0, 0, null);
        
        if ( !this.peli.isRunning() ) {
            g2.drawImage(this.pause, 
                    getWidth() / 2 - this.pause.getWidth(null) / 2, 
                    getHeight() / 2 - this.pause.getHeight(null) / 2, null);
        } else {
            if ( !(this.palanen == null) ) {
                this.palanen.piirra(g2);
            }

            if ( !this.peli.getPelinPalat().isEmpty() ) {
                for ( VanhaPala vanhaPala : this.peli.getPelinPalat() ) {
                    vanhaPala.piirra(g2);
                }
            }
        }
    }

    public void setPalanen(Kompositio palanen) {
        this.palanen = palanen;
    }

    public void paivita() {
        super.repaint();
    }
    
    public void setTausta(String tiedostonNimi) {
        try {
            this.tausta = ImageIO.read(this.getClass().getResourceAsStream(tiedostonNimi));
        } catch ( Exception e ) {
            System.out.println("Couldn't load the image: " + e.getMessage());
        }
    }
}

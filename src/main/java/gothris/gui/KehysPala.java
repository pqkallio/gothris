
package gothris.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class KehysPala extends JPanel {
    private Image kuva;

    public KehysPala(String tiedostonNimi) {
        try {
            InputStream is = this.getClass().getResourceAsStream(tiedostonNimi);
            this.kuva = ImageIO.read(is);
        } catch ( Exception e ) {
            System.out.println("Kuvaa ei voitu ladata: " + e.getMessage());
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.kuva, 0, 0, null);
    }
}

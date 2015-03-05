
package gothris.gui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import gothris.pelilogiikka.Peli;

public class TasoKyltti extends JPanel {
    private Image plaque;
    private Peli peli;
    
    public TasoKyltti(String plaque, Peli peli) {
        try {
            this.plaque = ImageIO.read(this.getClass().getResourceAsStream(plaque));
            
        } catch ( Exception e ) {
            System.out.println("Unable to load " + plaque + ": " + e.getMessage());
        }
        
        this.peli = peli;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        super.setOpaque(false);
        super.paintComponent(g2);
        
        this.setOpaque(false);
        
        Font fontti = new Font(Font.SERIF, Font.ITALIC|Font.BOLD, 30);
        g2.setFont(fontti);
        
        g2.drawImage(this.plaque, 0, 0, null);
        
        String tasoNyt = "Taso " + this.peli.getTaso();
        
        int tasotNytPituus = (int)g2.getFontMetrics().getStringBounds(tasoNyt, g2).getWidth();
        int tasotNytKorkeus = (int)g2.getFontMetrics().getStringBounds(tasoNyt, g2).getHeight();
        
        g2.drawString(tasoNyt, this.plaque.getWidth(null) / 2 - tasotNytPituus / 2, 
                this.plaque.getHeight(null) / 2 + tasotNytKorkeus / 3);
        
    }
    
    public void paivita() {
        super.repaint();
    }
}

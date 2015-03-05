
package gothris.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import gothris.pelilogiikka.Peli;

public class Alapalkki extends JPanel {
    private Peli peli;
    
    public Alapalkki(Peli peli) {
        this.peli = peli;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.setOpaque(false);
        super.paintComponent(g);
        
        this.setOpaque(false);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        
        String pisteet = "" + this.peli.getPisteet();
        
        Font fontti = new Font(Font.SERIF, Font.PLAIN, 24);
        g2.setFont(fontti);
        g2.setColor(Color.BLACK);
        
        int w = (int)g2.getFontMetrics(fontti).getStringBounds(pisteet, g2).getWidth();
        int h = (int)g2.getFontMetrics(fontti).getStringBounds(pisteet, g2).getHeight();
        
        int x = w * 3 + 4;
        int y = h * 2 + 28;
        
        g2.drawString(pisteet, x, y);
    }
    
    public void paivita() {
        super.repaint();
    }
    
}

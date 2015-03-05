
package gothris.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class Valikkokuuntelija implements ActionListener {
    private JButton aloitaPeli;
    private JButton parhaatPisteet;
    private JButton asetukset;
    private JButton palaaAlkuun;
    private Gui gui;

    public Valikkokuuntelija(JButton aloitaPeli, JButton parhaatPisteet, 
            JButton asetukset, JButton palaaAlkuun, Gui gui) {
        this.aloitaPeli = aloitaPeli;
        this.parhaatPisteet = parhaatPisteet;
        this.asetukset = asetukset;
        this.palaaAlkuun = palaaAlkuun;
        this.gui = gui;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if ( e.getSource() == this.aloitaPeli ) {
            this.gui.aloitaUusiPeli();
        } else if ( e.getSource() == this.parhaatPisteet ) {
            this.gui.naytaParhaatPisteet();
        } else if ( e.getSource() == this.asetukset ) {
            System.out.println("Asetukset");
        } else if ( e.getSource() == this.palaaAlkuun ) {
            this.gui.palaaAlkuun();
        }
    }
    
}

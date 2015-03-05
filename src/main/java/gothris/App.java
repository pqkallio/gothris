package gothris;

import gothris.gui.Gui;
import gothris.pelilogiikka.Peli;
import javax.swing.SwingUtilities;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Peli peli = new Peli(20, 15, 23);
        Gui gui = new Gui(peli);
        peli.setGui(gui);
        
        SwingUtilities.invokeLater(gui);
        
        while ( gui.getPelikentta() == null ) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                System.out.println("Piirtoalustaa ei ole vielä luotu.");
            }
        }
        peli.setPelialusta(gui.getPelikentta());
        peli.start();
    }
}


package gothris.gui;

import gothris.domain.Suunta;
import gothris.domain.palat.Kompositio;
import gothris.domain.palat.Pala;
import gothris.domain.palat.VanhaPala;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Pelikomentojenkuuntelija implements KeyListener {
    private Kompositio palanen;
    private Gui gui;
    private Pelialusta piirtoalusta;
        
    public Pelikomentojenkuuntelija(Kompositio palanen, Gui gui, Pelialusta piirtoalusta) {
        this.palanen = palanen;
        this.piirtoalusta = piirtoalusta;
        this.gui = gui;
        }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent ae) {
        if ( this.gui.getPeli().saaSiirtaa() ) {
            if ( ae.getKeyCode() == KeyEvent.VK_LEFT ) {
                if ( (this.palanen.getVasenX() - 1 >= 0) 
                        && (!tormaaToiseenPalaanSiirtyessaan(Suunta.VASEN)) ) {
                    this.palanen.siirra(Suunta.VASEN);
                }
            } else if ( ae.getKeyCode() == KeyEvent.VK_RIGHT ) {
                if ( (this.palanen.getOikeaX() + 1 <= this.gui.getPeli().getKentanLeveys() - 1 )
                        && (!tormaaToiseenPalaanSiirtyessaan(Suunta.OIKEA))) {
                    this.palanen.siirra(Suunta.OIKEA);
                }
            } else if ( ae.getKeyCode() == KeyEvent.VK_DOWN ) {
                OUTER:
                while ( this.palanen.getAlinY() < this.gui.getPeli().getKentanKorkeus() - 1 ) {
                    this.gui.poistaKuuntelijat();
                    this.palanen.siirra(Suunta.ALAS);
                    for ( VanhaPala vanhaPala : this.gui.getPeli().getPelinPalat() ) {
                        for ( Pala pala : vanhaPala.getPalat() ) {
                            if ( this.palanen.tormaa(pala) ) {
                                this.palanen.siirra(Suunta.YLOS);
                                this.gui.getPeli().setPalaTippuu(false);
                                break OUTER;
                            }
                        }
                    }
                }
            } else if ( ae.getKeyCode() == KeyEvent.VK_UP ) {
                this.palanen.kaanna();

                for ( VanhaPala vanhaPala : this.gui.getPeli().getPelinPalat() ) {
                    for ( Pala pala : vanhaPala.getPalat() ) {
                        if ( this.palanen.tormaa(pala) ) {
                            if ( vanhaPala.getOikeaX() > this.palanen.getOikeaX() ) {
                                this.palanen.siirra(Suunta.VASEN);
                            } else {
                                this.palanen.siirra(Suunta.OIKEA);
                            }
                        }
                    }
                }

                while ( this.palanen.getOikeaX() > this.gui.getPeli().getKentanLeveys() - 1) {
                    this.palanen.siirra(Suunta.VASEN);
                }

                while ( this.palanen.getVasenX() < 0 ) {
                    this.palanen.siirra(Suunta.OIKEA);
                }
            } else if ( ae.getKeyCode() == KeyEvent.VK_SPACE ) {
                if ( this.gui.getPeli().isRunning() ) {
                    this.gui.getPeli().stop();
                    this.gui.getPeli().getTaustaMusa().stop();
                    this.gui.getPelikentta().paivita();
                } else {
                    this.gui.getPeli().start();
                    this.gui.getPeli().getTaustaMusa().jatka();
                    this.gui.getPelikentta().paivita();
                }
            }
        }
        
        this.piirtoalusta.paivita();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    private boolean tormaaToiseenPalaanSiirtyessaan(Suunta suunta) {
        int siirtymaX = 0;
        int siirtymaY = 0;
        
        if ( suunta == Suunta.OIKEA ) {
            siirtymaX++;
        } else if ( suunta == Suunta.VASEN ) {
            siirtymaX--;
        } else if ( suunta == Suunta.ALAS ) {
            siirtymaY++;
        }
        
        for ( VanhaPala vanhaPala : this.gui.getPeli().getPelinPalat() ) {
            for ( Pala vierasPala : vanhaPala.getPalat() ) {
                if ( this.palanen.tormaa(siirtymaX, siirtymaY, vierasPala) ) {
                    return true;
                }
            }
        }
        
        return false;
    }
}


package gothris.pelilogiikka;

import gothris.domain.Suunta;
import gothris.domain.palat.*;
import gothris.gui.AudioKlik;
import gothris.gui.AudioKlok;
import gothris.gui.Gui;
import gothris.gui.Pelialusta;
import gothris.gui.TaustaMusiikki;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import javax.swing.Timer;

public class Peli extends Timer implements ActionListener {
    private int viive;
    private int pisteet;
    private int taso;
    private int viiveenTiputus;
    private Kompositio tippuvaPala;
    private int kentanLeveys;
    private int kentanKorkeus;
    private int palanKoko;
    private Gui gui;
    private boolean peliJatkuu;
    private boolean palaTippuu;
    private List<VanhaPala> vanhatPalat;
    private Map<Integer, Integer> pisterajat;
    private Map<Integer, String> taustat;
    private Pelialusta pelialusta;
    private TaustaMusiikki taustaMusa;
    private AudioKlik klik;
    private AudioKlok klok;
    private boolean saaSiirtaa;
    private File pisteHistoria;
    private List<Tulos> parhaatPisteet;
    private Scanner lukija;
    
    public Peli(int palanKoko, int kentanLeveys, int kentanKorkeus) {
        super(1000, null);
        this.taso = 1;
        this.viive = 1000;
        this.palanKoko = palanKoko;
        this.kentanKorkeus = kentanKorkeus;
        this.kentanLeveys = kentanLeveys;
        this.peliJatkuu = true;
        this.palaTippuu = true;
        this.viiveenTiputus = 40;
        this.pisteet = 0;
        this.vanhatPalat = new ArrayList<>();
        this.pisterajat = new HashMap<>();
        this.taustat = new HashMap<>();
        this.saaSiirtaa = true;
        this.parhaatPisteet = new ArrayList<>();
        
        try {
            this.taustaMusa = new TaustaMusiikki("/audio/goth.wav");
        } catch ( Exception e ) {
            System.out.println("Couldn't load music: " +  e.getMessage());
        }
        
        try {
            this.klik = new AudioKlik("/audio/klik.wav");
        } catch ( Exception e ) {
            System.out.println("Couldn't load music: " +  e.getMessage());
        }
        
        try {
            this.klok = new AudioKlok("/audio/klok.wav");
        } catch ( Exception e ) {
            System.out.println("Couldn't load music: " +  e.getMessage());
        }
        
        this.taustat.put(1, "/graphics/building01.png");
        this.taustat.put(2, "/graphics/building05.png");
        this.taustat.put(3, "/graphics/building03.png");
        this.taustat.put(4, "/graphics/building04.png");
        this.taustat.put(5, "/graphics/building02.png");
        this.taustat.put(6, "/graphics/building06.png");
        this.taustat.put(7, "/graphics/building07.png");
        this.taustat.put(8, "/graphics/building08.png");
        this.taustat.put(9, "/graphics/building09.png");
        
        for (int i = 1; i < 16; i++) {
            this.pisterajat.put(i, 5 * i);
        }
        
        addActionListener(this);
        setInitialDelay(0);
        setDelay(500);
        
        try {
            InputStream is = this.getClass().getResourceAsStream("/scores/parhaattulokset.txt");
            this.lukija = new Scanner(is);

            while ( this.lukija.hasNextLine() ) {
                String rivi = this.lukija.nextLine();
                String[] tulos = rivi.split(":");
                this.parhaatPisteet.add(new Tulos(tulos[0], Integer.parseInt(tulos[1])));
            }
                
            this.lukija.close();
        } catch ( Exception e ) {
            System.out.println("Jotain outoa tapahtui 2: " + e.getLocalizedMessage());
        }
        
        Collections.sort(this.parhaatPisteet);
    }

    public List<Tulos> getParhaatPisteet() {
        return parhaatPisteet;
    }

    public void setPalaTippuu(boolean palaTippuu) {
        this.palaTippuu = palaTippuu;
    }

    public void setPelialusta(Pelialusta pelialusta) {
        this.pelialusta = pelialusta;
    }
    
    public void setGui(Gui gui) {
        this.gui = gui;
    }

    public int getKentanKorkeus() {
        return this.kentanKorkeus;
    }

    public int getKentanLeveys() {
        return kentanLeveys;
    }

    public int getPisteet() {
        return pisteet;
    }

    public boolean saaSiirtaa() {
        return this.saaSiirtaa;
    }
    
    public int getPalanKoko() {
        return palanKoko;
    }
    
    public Kompositio arvoUusiPala() {
        Random arpa = new Random();
        int luku = arpa.nextInt(7);
        
        if ( luku == 0 ) {
            this.tippuvaPala = new Alla(this.kentanLeveys / 2, 0, this.palanKoko);
        } else if ( luku == 1 ) {
            this.tippuvaPala = new Assa(this.kentanLeveys / 2, 0, this.palanKoko);
        } else if ( luku == 2 ) {
            this.tippuvaPala = new Jii(this.kentanLeveys / 2, 0, this.palanKoko);
        } else if ( luku == 3 ) {
            this.tippuvaPala = new Zeta(this.kentanLeveys / 2, 0, this.palanKoko);
        } else if ( luku == 4 ) {
            this.tippuvaPala = new Nelio(this.kentanLeveys / 2, 0, this.palanKoko);
        } else if ( luku == 5 ) {
            this.tippuvaPala = new Tee(this.kentanLeveys / 2, 0, this.palanKoko);
        } else {
            this.tippuvaPala = new Viiva(this.kentanLeveys / 2, 0, this.palanKoko);
        }
        
        return this.tippuvaPala;
    }

    public void uusiPala() {
        this.tippuvaPala = arvoUusiPala();
        this.gui.getPelikentta().setPalanen(this.tippuvaPala);
        this.gui.setPalanen(this.tippuvaPala);
        this.palaTippuu = true;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if ( !this.taustaMusa.pyorii() ) {
            this.taustaMusa.loop();
        }
        
        if ( !this.palaTippuu ) {
            if ( this.tippuvaPala != null ) {
                this.saaSiirtaa = false;
                if ( this.tippuvaPala.getYlinY() <= 0 ) {
                    this.peliJatkuu = false;
                }
                
                this.klik.play();
                
                List<Pala> kopioTippuvastaPalasta = new ArrayList<>();
                for ( Pala pala : this.tippuvaPala.getPalat() ) {
                    kopioTippuvastaPalasta.add(new Pala(pala.getX(), pala.getY()));
                }

                this.vanhatPalat.add(new VanhaPala(this.tippuvaPala.getClass(), 0, 0, 
                        this.palanKoko, this.tippuvaPala.getColor(), 
                        kopioTippuvastaPalasta));

                boolean onPala = false;
                List<Integer> poistettavatRivit = new ArrayList<>();

                for (int i = 0; i <= this.kentanKorkeus * this.palanKoko; i += this.palanKoko) {
                    INNER:
                    for (int j = 0; j < this.kentanLeveys * this.palanKoko; j += this.palanKoko) {
                        onPala = false;
                        for ( VanhaPala vanhaPala : this.vanhatPalat ) {
                            for ( Pala pala : vanhaPala.getPalat() ) {
                                if ( pala.getX() * this.palanKoko == j 
                                        && pala.getY() * this.palanKoko == i ) {
                                    onPala = true;
                                }
                            }

                        }

                        if ( !onPala ) {
                            break INNER;
                        }
                    }

                    if ( onPala ) {
                        poistettavatRivit.add(i);
                    }
                }

                if ( !poistettavatRivit.isEmpty() ) {
                    this.pisteet += poistettavatRivit.size() * poistettavatRivit.size();
                    this.gui.getAlapalkki().paivita();
                    for (int i = 0; i < poistettavatRivit.size(); i++) {
                        for ( VanhaPala oldie : this.vanhatPalat ) {
                            for ( Iterator<Pala> it = oldie.getPalat().iterator(); it.hasNext();) {
                                Pala vanhaPala = it.next();
                                if ( vanhaPala.getY() * this.palanKoko 
                                        == poistettavatRivit.get(i) ) {
                                    it.remove();
                                }
                            }
                        }
                        
                        this.gui.getPisteet().setText("" + this.pisteet);
                        for ( VanhaPala oldie : this.vanhatPalat ) {
                            for ( Pala vanhaPala : oldie.getPalat() ) {
                                if ( vanhaPala.getY() * this.palanKoko 
                                        < poistettavatRivit.get(i) ) {
                                    vanhaPala.siirraAlas();
                                }
                            }
                        }
                    }
                    
                    this.klok.play();
                    
                    poistettavatRivit.clear();

                    if ( this.pisterajat.get(this.taso) <= this.pisteet ) {
                        this.taso++;
                        this.gui.getYlapalkki().paivita();
                        this.pelialusta.setTausta(this.taustat.get(this.taso));
                        setDelay(getDelay() - this.viiveenTiputus);
                    
                    }
                } 
            }
        }
        this.pelialusta.paivita();
        
        if ( !this.peliJatkuu ) {
            this.taustaMusa.stop();
            this.stop();
            if ( this.pisteet > parhaatPisteet.get(9).getTulos() ) {
                String pelaajanNimi = this.gui.lisaaTulosMuistiin(this.pisteet);
                this.parhaatPisteet.add(new Tulos(pelaajanNimi, this.pisteet));
                tallennaPisteetTiedostoon();
            }
            
            Collections.sort(this.parhaatPisteet);
            this.gui.naytaParhaatPisteet();
        }
        
        if ( !this.palaTippuu ) {
            uusiPala();
            this.palaTippuu = true;
        }
        
        if ( this.palaTippuu ) {
            this.saaSiirtaa = true;
            this.tippuvaPala.siirra(Suunta.ALAS);

            if ( this.tippuvaPala.getAlinY() > this.kentanKorkeus - 1 ) {
                this.palaTippuu = false;
                this.tippuvaPala.siirra(Suunta.YLOS);
            }

            for (VanhaPala vanhaPala : this.vanhatPalat) {
                for (Pala pala : vanhaPala.getPalat()) {
                    if ( this.tippuvaPala.tormaa(pala) ) {
                        this.palaTippuu = false;
                        this.tippuvaPala.siirra(Suunta.YLOS);
                        this.saaSiirtaa = false;
                    }
                }
            }
        }
    }

    public Map<Integer, String> getTaustat() {
        return taustat;
    }

    public int getTaso() {
        return taso;
    }

    public List<VanhaPala> getPelinPalat() {
        return this.vanhatPalat;
    }

    private void tallennaPisteetTiedostoon() {
        try {
            try (FileWriter kirjoittaja = new FileWriter(this.pisteHistoria)) {
                for (Tulos tulos : this.parhaatPisteet) {
                    kirjoittaja.write(tulos.getNimi() + ":" + tulos.getTulos() + "\n");
                }
            }
        } catch ( Exception e ) {
            System.out.println(e.getMessage());
        }
    }

    public TaustaMusiikki getTaustaMusa() {
        return this.taustaMusa;
    }
}

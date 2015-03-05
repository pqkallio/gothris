
package gothris.gui;

import gothris.domain.palat.Kompositio;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import gothris.pelilogiikka.Peli;
import gothris.pelilogiikka.Tulos;
import java.awt.Image;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class Gui implements Runnable {
    private JFrame frame;
    private JLabel pisteet;
    private JLabel taso;
    private JButton aloitaPeli;
    private JButton parhaatPisteet;
    private JButton asetukset;
    private JButton palaaAlkuun;
    private Valikkokuuntelija vKuuntelija;
    private Peli peli;
    private Pelialusta pelikentta;
    private Kompositio palanen;
    private Pelikomentojenkuuntelija kuuntelija;
    private TasoKyltti tasoKyltti;
    private Alapalkki alapalkki;
    private int ikkuna;
    private ImageIcon kyltti;
    
    public Gui(Peli peli) {
        this.peli = peli;
        Font fontti = new Font(Font.SERIF, Font.PLAIN, 24);
        this.pisteet = new JLabel("" + this.peli.getPisteet());
        this.pisteet.setFont(fontti);
        this.taso = new JLabel("Taso " + this.peli.getTaso());
        this.ikkuna = 0;
        this.aloitaPeli = new JButton("Aloita peli");
        this.parhaatPisteet = new JButton("Parhaat pisteet");
        this.asetukset = new JButton("Asetukset");
        this.palaaAlkuun = new JButton("Palaa");
        this.vKuuntelija = new Valikkokuuntelija(aloitaPeli, parhaatPisteet, asetukset, palaaAlkuun, this);
        aloitaPeli.addActionListener(vKuuntelija);
        parhaatPisteet.addActionListener(vKuuntelija);
        asetukset.addActionListener(vKuuntelija);
        palaaAlkuun.addActionListener(vKuuntelija);
        try {
            InputStream is = this.getClass().getResourceAsStream("/graphics/plaque.png");
            Image image = ImageIO.read(is);
            this.kyltti = new ImageIcon(image);
        } catch (Exception e) {
            System.out.println("Unable to load plaque.png: " + e.getMessage());
        }
    }

    public Pelialusta getPelikentta() {
        return this.pelikentta;
    }
    
    @Override
    public void run() {
        if ( this.frame != null ) {
            this.frame.dispose();
        }
        
        this.frame = new JFrame("Gothris");
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu();
        menuBar.add(fileMenu);
        
        JMenuItem open = new JMenuItem("Open");
        fileMenu.add(open);
        
        JMenuItem closeTab = new JMenuItem("Close Counter-tab");
        fileMenu.add(closeTab);
        
        JMenuItem deleteCounter = new JMenuItem("Delete Counter");
        fileMenu.add(deleteCounter);
        
        fileMenu.addSeparator();
        
        JMenuItem exit = new JMenuItem("Exit");
        fileMenu.add(exit);
        this.frame.setJMenuBar(menuBar);
        luoSisalto(this.frame.getContentPane());
        
        lisaaKuuntelijat();
        this.frame.setResizable(false);
        
        this.frame.pack();
        this.frame.setVisible(true);
    }
    
    private void luoSisalto(Container container) {
        switch(this.ikkuna) {
            case 0:
                container.add(luoValikkoalusta());
                break;
            case 1: 
                container.add(luoPelialusta());
                break;
            case 2:
                container.add(parhaatPisteetLayout());
                break;
            default:
                System.out.println("Something unexpected happened while "
                        + "creating a layout!");
        }
    }

    private JLabel luoPelialusta() {
        ImageIcon taustakuva = null;
        
        try {
            InputStream is = this.getClass().getResourceAsStream("/graphics/pelintausta 490x700.png");
            Image image = ImageIO.read(is);
            taustakuva = new ImageIcon(image);
        } catch (Exception e) {
            System.out.println("Unable to load pelintausta 490x700.png: " + e.getMessage());
        }
        
        JLabel paneeli = new JLabel(taustakuva);
        
        GridBagLayout leiska = new GridBagLayout();
        GridBagConstraints leiskanAsetukset = new GridBagConstraints();
        paneeli.setLayout(leiska);
        
        paneeli.setPreferredSize(new Dimension(taustakuva.getIconWidth(), taustakuva.getIconHeight()));
        
        int[] leveydet = {95, 90, 120, 90, 95};
        int[] korkeudet = {90, 100, 100, 100, 100, 70, 140};
        leiska.columnWidths = leveydet;
        leiska.rowHeights = korkeudet;
        
        this.pelikentta = new Pelialusta(this.peli);
        this.pelikentta.setPreferredSize(new Dimension(this.peli.getKentanLeveys() * this.peli.getPalanKoko(), 
                this.peli.getKentanKorkeus() * this.peli.getPalanKoko()));
        palanen = this.peli.arvoUusiPala();
        this.pelikentta.setPalanen(palanen);
        
        leiskanAsetukset.gridx = 1;
        leiskanAsetukset.gridy = 1;
        leiskanAsetukset.gridwidth = 3;
        leiskanAsetukset.gridheight = 5;
        leiska.setConstraints(this.pelikentta, leiskanAsetukset);
        paneeli.add(this.pelikentta);
        
        this.tasoKyltti = new TasoKyltti("/graphics/plaque.png", this.peli);
        this.alapalkki = new Alapalkki(this.peli);
        
        JLabel tasotNyt = new JLabel("Taso: " + this.peli.getTaso(), this.kyltti, JLabel.CENTER);
        tasotNyt.setPreferredSize(new Dimension(this.kyltti.getIconWidth(), this.kyltti.getIconHeight()));
        leiskanAsetukset.gridx = 2;
        leiskanAsetukset.gridy = 0;
        leiskanAsetukset.gridwidth = 1;
        leiskanAsetukset.gridheight = 1;
        leiskanAsetukset.anchor = GridBagConstraints.CENTER;
        leiskanAsetukset.fill = GridBagConstraints.BOTH;
        leiska.setConstraints(this.tasoKyltti, leiskanAsetukset);
        paneeli.add(this.tasoKyltti);
        
        GridBagLayout pisteLeiska = new GridBagLayout();
        GridBagConstraints pisteGbc = new GridBagConstraints();
        
        int[] pisteLeiskanCW = {92, 28};
        int[] pisteLeiskanRH = {10, 130};
        pisteLeiska.columnWidths = pisteLeiskanCW;
        pisteLeiska.rowHeights = pisteLeiskanRH;
        
        JPanel pistePaneeli = new JPanel();
        pistePaneeli.setPreferredSize(new Dimension(120, 140));
        pistePaneeli.setLayout(pisteLeiska);
        pistePaneeli.setOpaque(false);
        
        pisteGbc.gridx = 0;
        pisteGbc.gridy = 1;
        pisteGbc.anchor = GridBagConstraints.CENTER;
        pisteLeiska.setConstraints(this.pisteet, pisteGbc);
        pistePaneeli.add(this.pisteet);
        
        leiskanAsetukset.gridx = 2;
        leiskanAsetukset.gridy = 6;
        leiskanAsetukset.gridwidth = 1;
        leiskanAsetukset.gridheight = 1;
        leiska.setConstraints(pistePaneeli, leiskanAsetukset);
        paneeli.add(pistePaneeli);
        
        return paneeli;
    }

    private void lisaaKuuntelijat() {
        this.kuuntelija = new Pelikomentojenkuuntelija(this.palanen, this, this.pelikentta);
        this.frame.addKeyListener(this.kuuntelija);
    }

    public void setPalanen(Kompositio palanen) {
        this.palanen = palanen;
        lisaaKuuntelijat();
    }

    public Peli getPeli() {
        return peli;
    }

    public JLabel getPisteet() {
        return this.pisteet;
    }

    public JLabel getTaso() {
        return taso;
    }

    public TasoKyltti getYlapalkki() {
        return tasoKyltti;
    }

    public Alapalkki getAlapalkki() {
        return alapalkki;
    }
    
    public JFrame getFrame() {
        return this.frame;
    }

    void poistaKuuntelijat() {
        this.frame.removeKeyListener(this.kuuntelija);
    }

    private JLabel luoValikkoalusta() {
        ImageIcon taustakuva = null;
        try {
            InputStream is = this.getClass().getResourceAsStream("/graphics/valikontausta.png");
            Image image = ImageIO.read(is);
            taustakuva = new ImageIcon(image);
        } catch (Exception e) {
            System.out.println("Unable to load valikontausta.png: " + e.getMessage());
        }
        
        JLabel paneeli = new JLabel(taustakuva);
        paneeli.setPreferredSize(new Dimension(taustakuva.getIconWidth(), taustakuva.getIconHeight()));
        GridBagLayout leiska = new GridBagLayout();
        GridBagConstraints ehdot = new GridBagConstraints();
        
        paneeli.setLayout(leiska);
        
        int[] leveydet = {80, 60, 60, 60, 60, 60, 80};
        int[] korkeudet = {90, 100, 100, 50, 50, 50, 50, 70, 140};
        leiska.columnWidths = leveydet;
        leiska.rowHeights = korkeudet;
        
        JLabel otsikko = new JLabel("Gothris");
        otsikko.setFont(new Font(Font.SERIF, Font.BOLD, 50));
        
        aloitaPeli.setFont(new Font(Font.SERIF, Font.BOLD, 24));
        parhaatPisteet.setFont(new Font(Font.SERIF, Font.BOLD, 24));
        asetukset.setFont(new Font(Font.SERIF, Font.BOLD, 24));
        
        ehdot.gridx = 3;
        ehdot.gridy = 1;
        ehdot.anchor = GridBagConstraints.CENTER;
        leiska.setConstraints(otsikko, ehdot);
        paneeli.add(otsikko);
        
        ehdot.gridx = 3;
        ehdot.gridy = 3;
        ehdot.anchor = GridBagConstraints.CENTER;
        ehdot.fill = GridBagConstraints.HORIZONTAL;
        leiska.setConstraints(aloitaPeli, ehdot);
        paneeli.add(aloitaPeli);
        
        ehdot.gridx = 3;
        ehdot.gridy = 4;
        ehdot.anchor = GridBagConstraints.CENTER;
        ehdot.fill = GridBagConstraints.HORIZONTAL;
        leiska.setConstraints(parhaatPisteet, ehdot);
        paneeli.add(parhaatPisteet);
        
        ehdot.gridx = 3;
        ehdot.gridy = 5;
        ehdot.anchor = GridBagConstraints.CENTER;
        ehdot.fill = GridBagConstraints.HORIZONTAL;
        leiska.setConstraints(asetukset, ehdot);
        paneeli.add(asetukset);
        
        return paneeli;
    }
    
    public void aloitaUusiPeli() {
        this.ikkuna = 1;
        this.frame.getContentPane().removeAll();
        run();
    }

    public JLabel parhaatPisteetLayout() {
        ImageIcon taustakuva = null;
        try {
            InputStream is = this.getClass().getResourceAsStream("/graphics/valikontausta.png");
            Image image = ImageIO.read(is);
            taustakuva = new ImageIcon(image);
        } catch (Exception e) {
            System.out.println("Unable to load valikontausta.png: " + e.getMessage());
        }
        JLabel paneeli = new JLabel(taustakuva);
        paneeli.setPreferredSize(new Dimension(taustakuva.getIconWidth(), taustakuva.getIconHeight()));
        GridBagLayout leiska = new GridBagLayout();
        GridBagConstraints ehdot =  new GridBagConstraints();
        paneeli.setLayout(leiska);
        
        int[] leveydet = {180, 20, 5, 50, 5, 20, 180};
        int[] korkeudet = {250, 30, 40, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 60, 200};
        
        leiska.columnWidths = leveydet;
        leiska.rowHeights = korkeudet;
        
        JLabel otsikko = new JLabel("Parhaat pisteet");
        
        ehdot.gridx = 3;
        ehdot.gridy = 1;
        ehdot.anchor = GridBagConstraints.CENTER;
        leiska.setConstraints(otsikko, ehdot);
        paneeli.add(otsikko);
        
        for (int i = 1; i < 11; i++) {
            JLabel sijoitus = new JLabel("" + i);
            ehdot.gridx = 1;
            ehdot.gridy = i + 2;
            ehdot.anchor = GridBagConstraints.EAST;
            leiska.setConstraints(sijoitus, ehdot);
            paneeli.add(sijoitus);
        }
        
        List<Tulos> parhaatPisteet = this.peli.getParhaatPisteet();
        
        for (int i = 1; i < 11; i++) {
            JLabel pelaajanNimi = new JLabel("-");
            
            if ( parhaatPisteet.size() >= i - 1) {
                String nimi = parhaatPisteet.get(i-1).getNimi();
                
                if ( nimi.length() > 20 ) {
                    nimi = nimi.substring(0, 20);
                }
                
                pelaajanNimi.setText(nimi);
            }
            
            ehdot.gridx = 3;
            ehdot.gridy = i + 2;
            ehdot.anchor = GridBagConstraints.WEST;
            leiska.setConstraints(pelaajanNimi, ehdot);
            paneeli.add(pelaajanNimi);
        }
        
        for (int i = 1; i < 11; i++) {
            JLabel pelaajanPisteet = new JLabel("-");
            
            if ( parhaatPisteet.size() >= i - 1) {
                if ( parhaatPisteet.get(i-1).getTulos() > 0 ) {
                    pelaajanPisteet.setText("" + parhaatPisteet.get(i-1).getTulos());
                }
            }
            
            ehdot.gridx = 5;
            ehdot.gridy = i + 2;
            ehdot.anchor = GridBagConstraints.EAST;
            leiska.setConstraints(pelaajanPisteet, ehdot);
            paneeli.add(pelaajanPisteet);
        }
        
        ehdot.gridx = 3;
        ehdot.gridy = 14;
        ehdot.anchor = GridBagConstraints.CENTER;
        leiska.setConstraints(this.palaaAlkuun, ehdot);
        paneeli.add(this.palaaAlkuun);
        
        return paneeli;
    }

    public void naytaParhaatPisteet() {
        this.ikkuna = 2;
        this.frame.getContentPane().removeAll();
        run();
    }

    public void palaaAlkuun() {
        this.ikkuna = 0;
        this.frame.getContentPane().removeAll();
        run();
    }

    public String lisaaTulosMuistiin(int pisteet) {
        String nimi = (String)JOptionPane.showInputDialog(this.frame, "Pääsit 10 parhaan joukkoon!\nAnna nimesi:", "Onnittelut!", JOptionPane.DEFAULT_OPTION, null, null, null);
        
        if ( nimi == null || nimi.length() < 1 ) {
            nimi = "Pelaaja X";
        }
        
        return nimi.trim();
    }
}

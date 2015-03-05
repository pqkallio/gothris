
package gothris.domain.palat;

import gothris.domain.Suunta;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

public abstract class Kompositio {
    protected int palanKoko;
    protected int x;
    protected int y;
    protected int rotaatio;
    protected List<Pala> osat;
    protected Color palanVari;
    protected Color reunanVari;
    protected Color reunanVari2;
    protected Class oldClass;
    
    public abstract void kaanna();
        
    public Kompositio(int x, int y, int palanKoko, Color color) {
        this.palanKoko = palanKoko;
        this.palanVari = color;
        this.rotaatio = 1;
//        setReunanVari(color);
        this.reunanVari = new Color(25, 25, 25);
        this.reunanVari2 = new Color(0, 0, 0);
    }

    public void setOsat(List<Pala> osat) {
        this.osat = osat;
    }
    
    public void siirra(Suunta suunta) {
        if ( suunta == Suunta.ALAS ) {
            for (Pala pala : osat) {
                pala.setY(pala.getY() + 1);
            }
        } else if ( suunta == Suunta.VASEN ) {
            for (Pala pala : osat) {
                pala.setX(pala.getX() - 1);
            }
        } else if ( suunta == Suunta.OIKEA ) {
            for (Pala pala : osat) {
                pala.setX(pala.getX() + 1);
            }
        } else if ( suunta == Suunta.YLOS ) {
            for (Pala pala : osat) {
                pala.setY(pala.getY() - 1);
            }
        }
    }

    public boolean tormaa(Pala pala) {
        for (Pala kompositionOsa : this.osat) {
            kompositionOsa.setPalanKoko(this.palanKoko);
            if ( kompositionOsa.tormaa(pala) ) {
                return true;
            }
        }
        
        return false;
    }
    
    public boolean tormaa(int siirtymaX, int siirtymaY, Pala vierasPala) {
        for (Pala kompositionOsa : this.osat) {
            kompositionOsa.setPalanKoko(this.palanKoko);
            if ( kompositionOsa.tormaa(siirtymaX, siirtymaY, vierasPala)) {
                return true;
            }
        }
        
        return false;
    }

    public int getAlinY() {
        int y = Integer.MIN_VALUE;
        for (Pala pala : this.osat) {
            if ( pala.getY() > y ) {
                y = pala.getY();
            }
        }
        
        return y;
    }
    
    public int getYlinY() {
        int y = Integer.MAX_VALUE;
        for (Pala pala : this.osat) {
            if ( pala.getY() < y ) {
                y = pala.getY();
            }
        }
        
        return y;
    }

    public List<Pala> getPalat() {
        return this.osat;
    }

    public int getVasenX() {
        int vasenX = Integer.MAX_VALUE;
        for (Pala pala : this.osat) {
            if ( pala.getX() < vasenX ) {
                vasenX = pala.getX();
            }
        }
        
        return vasenX;
    }

    public int getOikeaX() {
        int oikeaX = Integer.MIN_VALUE;
        for (Pala pala : this.osat) {
            if ( pala.getX() > oikeaX ) {
                oikeaX = pala.getX();
            }
        }
        
        return oikeaX;    
    }
    
    public void piirra(Graphics graphics) {
        for (Pala pala : this.osat) {
            graphics.setColor(palanVari);
            graphics.fillRect(pala.getX() * this.palanKoko, 
                    pala.getY() * this.palanKoko, this.palanKoko, 
                    this.palanKoko);
            boolean oikealla = false;
            boolean vasemmalla = false;
            boolean ylhaalla = false; 
            boolean alhaalla = false;
            for (Pala toinenPala : this.osat) {
                if ( toinenPala.getX() == pala.getX() ) {
                    if ( toinenPala.getY() == pala.getY() + 1 ) {
                        alhaalla = true;
                    } else if ( toinenPala.getY() == pala.getY() - 1 ) {
                        ylhaalla = true;
                    }
                } else if ( toinenPala.getY() == pala.getY() ){
                    if ( toinenPala.getX() == pala.getX() + 1 ) {
                        oikealla = true;
                    } else if ( toinenPala.getX() == pala.getX() - 1 ) {
                        vasemmalla = true;
                    }
                }
            }
                
            graphics.setColor(this.reunanVari);
            
            if ( !ylhaalla ) {
                graphics.fillRect(pala.getX() * this.palanKoko, 
                        pala.getY() * this.palanKoko, this.palanKoko, 
                        this.palanKoko / 5);
            }
            
            if ( !alhaalla ) {
                graphics.fillRect(pala.getX() * this.palanKoko, 
                        pala.getY() * this.palanKoko + this.palanKoko - this.palanKoko / 5, 
                        this.palanKoko, this.palanKoko / 5);
            }
            
            graphics.setColor(this.reunanVari);
            
            if ( !vasemmalla ) {
                graphics.fillRect(pala.getX() * this.palanKoko, 
                        pala.getY() * this.palanKoko, this.palanKoko / 5, this.palanKoko);
            }
            
            if ( !oikealla ) {
                graphics.fillRect(pala.getX() * this.palanKoko + this.palanKoko - this.palanKoko / 5, 
                        pala.getY() * this.palanKoko, this.palanKoko / 5, this.palanKoko);
                
                graphics.setColor(this.reunanVari2);
                
                graphics.fillRect(pala.getX() * this.palanKoko + this.palanKoko - 2, 
                        pala.getY() * this.palanKoko, 2, this.palanKoko);
            }
            
            graphics.setColor(this.reunanVari);
            
            if ( (this.getClass() != Nelio.class) && (this.oldClass != Nelio.class) ) {
                if ( ylhaalla && oikealla ) {
                    graphics.fillRect(pala.getX() * this.palanKoko + this.palanKoko - this.palanKoko / 5, 
                            pala.getY() * this.palanKoko, this.palanKoko / 5, this.palanKoko / 5);
                }

                if ( ylhaalla && vasemmalla ) {
                    graphics.fillRect(pala.getX() * this.palanKoko, 
                            pala.getY() * this.palanKoko, this.palanKoko / 5, this.palanKoko / 5);
                }

                if ( alhaalla && oikealla ) {
                    graphics.fillRect(pala.getX() * this.palanKoko + this.palanKoko - this.palanKoko / 5, 
                            pala.getY() * this.palanKoko + this.palanKoko - this.palanKoko / 5, 
                            this.palanKoko, this.palanKoko / 5);
                }

                if ( alhaalla && vasemmalla ) {
                    graphics.fillRect(pala.getX() * this.palanKoko, 
                            pala.getY() * this.palanKoko + this.palanKoko - this.palanKoko / 5, 
                            this.palanKoko / 5, this.palanKoko / 5);
                }
            }
            
            graphics.setColor(this.reunanVari2);
            
            if ( !alhaalla ) {
                graphics.fillRect(pala.getX() * this.palanKoko, 
                        pala.getY() * this.palanKoko + this.palanKoko - 2, 
                        this.palanKoko, 2);
            }
            
            if ( !oikealla ) {
                graphics.fillRect(pala.getX() * this.palanKoko + this.palanKoko - 2, 
                        pala.getY() * this.palanKoko, 2, this.palanKoko);
            }
            
            if ( (this.getClass() != Nelio.class) && (this.oldClass != Nelio.class) ) {
                if ( alhaalla && oikealla ) {
                    graphics.fillRect(pala.getX() * this.palanKoko + this.palanKoko - 2, 
                            pala.getY() * this.palanKoko + this.palanKoko - 2, 
                            this.palanKoko, 2);
                }
            }
        }
    }
    
    public Color getColor() {
        return this.palanVari;
    }

    protected void setReunanVari(Color color) {
        int red = 0, green = 0, blue = 0;
        
        if ( color.getRed() < 205 ) {
            red = color.getRed() + 50;
        } else {
            red = color.getRed();
        }
        
        if ( color.getGreen() < 205 ) {
            green = color.getGreen() + 50;
        } else {
            green = color.getGreen();
        }
        
        if ( color.getBlue() < 205 ) {
            blue = color.getBlue() + 50;
        } else {
            blue = color.getBlue();
        }
        
        this.reunanVari = new Color(red, green, blue);
    }
    
    public Class getOldClass() {
        return this.oldClass;
    }
}

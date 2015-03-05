
package gothris.domain.palat;

import java.awt.Graphics;

public class Pala {
    private int x;
    private int y;
    private int palanKoko;

    public Pala(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setPalanKoko(int koko) {
        this.palanKoko = koko;
    }
    
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public boolean tormaa(Pala pala) {
        if ( this.x == pala.getX() && this.y == pala.getY()) {
            return true;
        }
        
        return false;
    }
    
    public boolean tormaa(int siirtymaX, int siirtymaY, Pala vierasPala) {
        if ( getX() + siirtymaX == vierasPala.getX() && getY() + siirtymaY == vierasPala.getY() ) {
            return true;
        }
        
        return false;
    }
    
    public void piirra(Graphics graphics) {
        graphics.fillRect(this.x, this.y, this.palanKoko, this.palanKoko);
    }
    
    public void siirraAlas() {
        this.y++;
    }
}


package gothris.domain.palat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

public class Jii extends Kompositio {
    
    public Jii(int x, int y, int palanKoko) {
        super(x, y , palanKoko, new Color(51, 0, 0));
        super.osat = new ArrayList<Pala>();
        Collections.addAll(super.osat, new Pala(x, y), new Pala(x, y+1),
                new Pala(x, y+2), new Pala(x-1, y+2));
    }
    
    @Override
    public void kaanna() {
        if ( this.rotaatio == 1 ) {
            this.osat.get(0).setX(this.osat.get(0).getX()-1);
            this.osat.get(0).setY(this.osat.get(0).getY()+1);
            
            this.osat.get(2).setX(this.osat.get(2).getX()+1);
            this.osat.get(2).setY(this.osat.get(2).getY()-1);
            
            this.osat.get(3).setX(this.osat.get(3).getX()+2);
            
            rotaatio++;
        } else if ( this.rotaatio == 2 ) {
            this.osat.get(0).setX(this.osat.get(0).getX()+1);
            this.osat.get(0).setY(this.osat.get(0).getY()+1);
                        
            this.osat.get(2).setX(this.osat.get(2).getX()-1);
            this.osat.get(2).setY(this.osat.get(2).getY()-1);
            
            this.osat.get(3).setY(this.osat.get(3).getY()-2);
            
            rotaatio++;
        } else if ( this.rotaatio == 3 ) {
            this.osat.get(0).setX(this.osat.get(0).getX()+1);
            
            this.osat.get(1).setY(this.osat.get(1).getY()+1);
            
            this.osat.get(2).setX(this.osat.get(2).getX()-1);
            this.osat.get(2).setY(this.osat.get(2).getY()+2);
            
            this.osat.get(3).setX(this.osat.get(3).getX()-2);
            this.osat.get(3).setY(this.osat.get(3).getY()+1);
            
            rotaatio++;
        } else if ( this.rotaatio == 4 ) {
            this.osat.get(0).setX(this.osat.get(0).getX()-1);
            this.osat.get(0).setY(this.osat.get(0).getY()-2);
            
            this.osat.get(1).setY(this.osat.get(1).getY()-1);
            
            this.osat.get(2).setX(this.osat.get(2).getX()+1);
            
            this.osat.get(3).setY(this.osat.get(3).getY()+1);
            
            rotaatio = 1;
        }
    }
}

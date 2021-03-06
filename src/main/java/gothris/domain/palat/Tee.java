
package gothris.domain.palat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tee extends Kompositio {
    
    public Tee(int x, int y, int palanKoko) {
        super(x, y , palanKoko, new Color(102, 0, 0));
        List<Pala> palaset = new ArrayList<Pala>();
        Collections.addAll(palaset, new Pala(x, y), new Pala(x-1, y+1),
                new Pala(x-1, y), new Pala(x-2, y));
        super.setOsat(palaset);
    }
    
    @Override
    public void kaanna() {
        if ( this.rotaatio == 1 ) {
            this.osat.get(0).setX(this.osat.get(0).getX()-1);
            this.osat.get(0).setY(this.osat.get(0).getY()+1);
            
            this.osat.get(1).setX(this.osat.get(1).getX()-1);
            this.osat.get(1).setY(this.osat.get(1).getY()-1);
            
            this.osat.get(3).setX(this.osat.get(3).getX()+1);
            this.osat.get(3).setY(this.osat.get(3).getY()-1);
            
            rotaatio++;
        } else if ( this.rotaatio == 2 ) {
            this.osat.get(0).setX(this.osat.get(0).getX()-1);
            
            this.osat.get(1).setX(this.osat.get(1).getX()+1);
            
            this.osat.get(2).setY(this.osat.get(2).getY()+1);
            
            this.osat.get(3).setX(this.osat.get(3).getX()+1);
            this.osat.get(3).setY(this.osat.get(3).getY()+2);
            
            rotaatio++;
        } else if ( this.rotaatio == 3 ) {
            this.osat.get(0).setX(this.osat.get(0).getX()+1);
            this.osat.get(0).setY(this.osat.get(0).getY()-2);
            
            this.osat.get(1).setX(this.osat.get(1).getX()+1);
            
            this.osat.get(2).setY(this.osat.get(2).getY()-1);
            
            this.osat.get(3).setX(this.osat.get(3).getX()-1);
            
            rotaatio++;
        } else if ( this.rotaatio == 4 ) {
            this.osat.get(0).setX(this.osat.get(0).getX()+1);
            this.osat.get(0).setY(this.osat.get(0).getY()+1);
            
            this.osat.get(1).setX(this.osat.get(1).getX()-1);
            this.osat.get(1).setY(this.osat.get(1).getY()+1);
            
            this.osat.get(3).setX(this.osat.get(3).getX()-1);
            this.osat.get(3).setY(this.osat.get(3).getY()-1);
            
            rotaatio = 1;
        }
    } 
}

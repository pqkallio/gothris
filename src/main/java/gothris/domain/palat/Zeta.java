
package gothris.domain.palat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Zeta extends Kompositio {
    
    public Zeta(int x, int y, int palanKoko) {
        super(x, y, palanKoko, new Color(153, 0, 0));
        List<Pala> palaset = new ArrayList<Pala>();
        Collections.addAll(palaset, new Pala(x, y), new Pala(x, y+1),
                new Pala(x-1, y+1), new Pala(x-1, y+2));
        super.setOsat(palaset);
    }
    
    @Override
    public void kaanna() {
        if ( super.rotaatio == 1 ) {
            super.osat.get(0).setX(super.osat.get(0).getX()+1);
            super.osat.get(0).setY(super.osat.get(0).getY()+2);
            
            super.osat.get(1).setY(super.osat.get(1).getY()+1);
            
            super.osat.get(2).setX(super.osat.get(2).getX()+1);
            
            super.osat.get(3).setY(super.osat.get(3).getY()-1);
            
            super.rotaatio++;
        } else if ( super.rotaatio == 2 ) {
            super.osat.get(0).setX(super.osat.get(0).getX()-1);
            super.osat.get(0).setY(super.osat.get(0).getY()-2);
            
            super.osat.get(1).setY(super.osat.get(1).getY()-1);
            
            super.osat.get(2).setX(super.osat.get(2).getX()-1);
            
            super.osat.get(3).setY(super.osat.get(3).getY()+1);
            
            super.rotaatio = 1;
        }
    }
}

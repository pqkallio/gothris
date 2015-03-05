
package gothris.domain.palat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

public class Nelio extends Kompositio {
    
    public Nelio(int x, int y, int palanKoko) {
        super(x, y, palanKoko, new Color(51, 25, 0));
        super.osat = new ArrayList<Pala>();
        Collections.addAll(super.osat, new Pala(x, y), new Pala(x+1, y),
                new Pala(x, y+1), new Pala(x+1, y+1));
    }

    @Override
    public void kaanna() {
        // Koska neliö, ei käännetä
    }
}

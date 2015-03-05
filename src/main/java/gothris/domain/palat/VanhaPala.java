
package gothris.domain.palat;

import java.awt.Color;
import java.util.List;

public class VanhaPala extends Kompositio {
    
    public VanhaPala(Class oldClass, int x, int y, int palanKoko, Color color, List<Pala> palat) {
        super(x, y, palanKoko, color);
        super.setOsat(palat);
        super.oldClass = oldClass;
    }

    @Override
    public void kaanna() {
        // palaa ei käännellä
    }
}

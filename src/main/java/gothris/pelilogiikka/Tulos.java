
package gothris.pelilogiikka;

public class Tulos implements Comparable<Tulos> {
    private String nimi;
    private int tulos;
    
    public Tulos(String nimi, int tulos) {
        this.nimi = nimi;
        this.tulos = tulos;
    }

    public int getTulos() {
        return tulos;
    }

    public String getNimi() {
        return nimi;
    }

    @Override
    public int compareTo(Tulos verrattava) {
        if ( this.tulos > verrattava.getTulos() ) {
            return -1;
        } else if ( this.tulos < verrattava.getTulos() ) {
            return 1;
        } else {
            return this.nimi.compareTo(verrattava.getNimi());
        }
    }
    
    
}

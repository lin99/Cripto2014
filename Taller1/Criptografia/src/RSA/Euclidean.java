package RSA;

/**
 *
 * @author JuanFelipe
 */
public class Euclidean {
    int d, x, y;

    public Euclidean(int d, int x, int y) {
        this.d = d;
        this.x = x;
        this.y = y;
    }
    
    public static Euclidean EEA( int a, int b ){
        if( b==0 ) 
            return new Euclidean(a, 1, 0);
        
        Euclidean aux, eea = EEA( b, a%b);
        
        aux = new Euclidean(eea.d, eea.x, eea.y);
        eea.x = aux.y;
        eea.y = aux.x-aux.y*(a/b);
        
        return eea;
    }
    
}

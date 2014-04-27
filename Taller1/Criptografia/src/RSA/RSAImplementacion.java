package RSA;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Implementacion del <b>RSA</b> realizando cada una de las funciones
 * necesarias para el funcionamiento.
 * @author JuanFelipe
 */
public class RSAImplementacion {
    
    static ArrayList<Integer> primes = new ArrayList<>();
    
    /**
     * Calcula el inverso de e mod n
     * @param e
     * @param n
     * @return 
     */
    public static int inverso( int e, int n){
        Euclidean eea = Euclidean.EEA( e, n);
        
        if( eea.y > 0 )
            return eea.y;
        else
            return eea.y+n;
        
    }
    
    /**
     * 
     * @param n
     * @return 
     */
    public static int totem( int n ) {
        double x = n;
        
        for ( int i = 0;  n > 1 ; i++) {
            if( n%primes.get(i)==0 ) {
                x *= 1-(1.0/primes.get(i));
            }
            while( n%primes.get(i)==0 ) {
                n /= primes.get(i);
            }
        }
        
        return (int) x;
    }
    
    /**
     * Criba de eratostenes con 
     * @param N 
     */
    public static void criba( int N ) {
        int[] isPrime = new int[ N ];
        isPrime[0] = 1;
        isPrime[1] = 1;
        
        primes.add(2);
        for( int j = 2*2; j*j< N; j+=2 ){
            isPrime[j] = 1;
        }
        
        for( int i=3; i*i<N; i+=2 ){
            if( primes.get(i)==1 )
                continue;
            primes.add(i);
            for( int j = i*i; j*j< N; j+=i ){
                isPrime[j] = 1;
            }
        }
    }
    
    public static int powerMod( int e, int b, int n ){
        //String bin = Integer.toBinaryString( b );
        int z = 1;
        
        for( int i=0 ; (1<<i)<=b ; i++){
            if( ( b&(1<<i) )>0 ){
                z = ( ( (z*z)%n )*e)%n;
            } else {
                z = (z*z)%n;
            }
        }
        return z;
    }
    
    
    public static void main( String args[] ) {
        Scanner in = new Scanner( System.in );
        int e, n, b, c;
        
        criba( 100000 );
        
        for( c = 1; in.hasNext(); ++c){
            e = in.nextInt();
            n = in.nextInt();
            b = in.nextInt();
            
            e = inverso(e, totem(n) );
            
            System.out.println(" Cypher: "+powerMod(b, e, n));
        }
    }
}

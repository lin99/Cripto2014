package RSA;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Implementacion del <b>RSA</b> usando las funciones de bigInteger
 * @author JuanFelipe
 */
public class RSAJava {
    
//    public static keyGeneration(){
//        //Let p, q
//        //Let n = pq
//        //chose e at random
//        
//        
//    }
    
    
    public static void main( String args[] ){
        Scanner in = new Scanner( System.in );
        BigInteger e, n, b ;
        e = new BigInteger( in.next() );
        n = new BigInteger( in.next() );
        b = new BigInteger( in.next() );
        //es totem de n
        e = e.modInverse( n );
        
        System.out.println(" res: "+ b.modPow(e, n) );
    }
    
}


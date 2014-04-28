package RSA;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Implementacion del <b>RSA</b> realizando cada una de las funciones
 * necesarias para el funcionamiento.
 * Pensado para que funcione con elementos de tipo entero.
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
            if( isPrime[i]==1 )
                continue;
            primes.add(i);
            for( int j = i*i; j*j< N; j+=i ){
                isPrime[j] = 1;
            }
        }
    }
    
    public static int powerMod( int a, int b, int n ){
//        System.out.println(" **"+b+"** ");
        String bin = Integer.toBinaryString( b );
        int tam = bin.length();
        int z = 1;
        
        for( int i=tam-1 ; i>=0 ; i--){
//            System.out.println(" -> "+bin.charAt(i));
            if( ( b&(1l<<i) )>0 ){
                z = ( ( (z*z)%n )*a)%n;
            } else {
                z = (z*z)%n;
            }
//            System.out.println(" z: "+z);
        }
        return z;
    }
    
    
    public static void main( String args[] ) {
        Scanner in = new Scanner( System.in );
        int c;
        RSAImplementacion rsa = new RSAImplementacion();
        criba( 100000 );
        
        System.out.println("*************Cifrado RSA************");
        //Menu de opciones
        while( true ) {
            System.out.println("\t*** Menu ***");
            System.out.println("  1) Cifrar mensaje");
            System.out.println("  2) DeCifrar mensaje");
            System.out.println("  *) Otra opcion salir ");
            System.out.print("Ingrese la opcion: ");
            c = in.nextInt();
            
            if( c==1 ){
                rsa.encriptar( in );
            }else if( c==2 ){
                rsa.desencriptar( in );
            }else {
                break;
            }
            
        }
    }

    private void encriptar(Scanner in) {
        int e, n, c, d;
        String aux;
        StringTokenizer st;
        
        System.out.print("Ingrese la llave publica E: ");
        e = in.nextInt();
        System.out.print("Ingrese la llave publica N: ");
        n = in.nextInt();
        d = inverso(e, totem(n) );
        
        System.out.println("Ingrese el mensaje: ");
        in.nextLine();
        aux = in.nextLine();
        st = new StringTokenizer( aux );
        int mensaje[] = new int[ st.countTokens() ];
        int cifrado[] = new int[ st.countTokens() ];
        for(int idx=0 ;st.hasMoreTokens() ; idx++){
            mensaje[idx] = Integer.parseInt( st.nextToken() );
            cifrado[idx] = powerMod(mensaje[idx], e, n);
            System.out.println(" Cypher: "+ cifrado[idx]);
        }
        System.out.println("*** FIN CIFRADO ***");
        
        System.out.println("Decifrando: ");
        for(int idx=0 ;st.hasMoreTokens() ; idx++){
            System.out.println(" Original: "+ powerMod(cifrado[idx], d, n));
        }
        
    }

    private void desencriptar(Scanner in) {
        int e, n, c, d;
        String aux;
        StringTokenizer st;
        
        System.out.print("Ingrese la llave privada D: ");
        d = in.nextInt();
        System.out.print("Ingrese la llave publica N: ");
        n = in.nextInt();
        e = inverso(d, totem(n) );
        
        System.out.println("Ingrese el mensaje: ");
        in.nextLine();
        aux = in.nextLine();
        st = new StringTokenizer( aux );
        int cifrado[] = new int[ st.countTokens() ];
        int mensaje[] = new int[ st.countTokens() ];
        for(int idx=0 ;st.hasMoreTokens() ; idx++){
            cifrado[idx] = Integer.parseInt( st.nextToken() );
            mensaje[idx] = powerMod(cifrado[idx], e, n);
            System.out.println(" Cypher: "+ mensaje[idx]);
        }
        System.out.println("*** FIN CIFRADO ***");
        
        System.out.println("Decifrando: ");
        for(int idx=0 ;st.hasMoreTokens() ; idx++){
            System.out.println(" Original: "+ powerMod(mensaje[idx], d, n));
        }
    }
}

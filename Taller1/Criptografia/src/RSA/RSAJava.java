package RSA;

import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

/**
 * Implementacion del <b>RSA</b> usando las funciones de bigInteger
 * @author JuanFelipe
 */
public class RSAJava {
    
    static BigInteger p, q, n, phi, e, d;
    
    public static void keyGeneration(){
        //elegir un p, q aleatorio
        p = new BigInteger(128, 20, new Random( ) );
        q = new BigInteger(128, 20, new Random( ) );
        //Asegurarnos que no sean guales
        while( p.equals(q) ){
            q = new BigInteger(128, 20, new Random( ) );
        }
        //Let n = pq
        n = p.multiply(q);
        phi = (p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)));
                
        //chose e at random
        int tam = (int) (new Random().nextDouble()*129);
        e = new BigInteger(tam, new Random());
        //1 < e < phi such that GCD(e, phi)=1
        
        while( phi.compareTo(e)>0 && !phi.gcd(e).equals(BigInteger.ONE) ){
            tam = (int) (new Random().nextDouble()*129);
            e = new BigInteger(tam, new Random());
            
        }
        
        //Calcular el inverso multiplicativo
        d = e.modInverse(phi);
    }
    
    public static BigInteger Encryption( BigInteger mNum, BigInteger _p, 
                                         BigInteger _q, BigInteger _e ) {
        //keyGeneration();
        p = _p;
        q = _q;
        e = _e;
        n = p.multiply(q);
        phi = (p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)));
        d = e.modInverse(phi);
        
        return Encryption(mNum, 0);
    }
    
    
    /**
     *
     * @param mns Un BigInteger para asegurar que es una cadena de enteros
     */
    public static BigInteger Encryption( BigInteger mNum , int generate ) {
        if( generate>0 )
            keyGeneration();
        //Partir el mensaje en m_t partes cada una menor a n
        BigInteger m_t[], c_t[];
        int i, j, tam = n.toString().length()-1;
        String mns = mNum.toString();
        m_t = new BigInteger[mns.length()/tam + 5];
        for(i=0, j= 0; i+tam<mns.length(); i+=tam, j++){
            m_t[j] = new BigInteger( mns.substring(i, i+tam) );
        }
        if( i<mns.length() ){
            m_t[j++] = new BigInteger( mns.substring(i) );
        }
        
        //PowerMod de cada mi, con la llave publica y n
        c_t = new BigInteger[mns.length()/tam + 5];
        
        for( i=0; i<j; i++ ){
            c_t[i] = m_t[i].modPow(e, n);
        }
        
        //Construmos una cadena que sera el mensaje cifrado
        StringBuilder sb = new StringBuilder();
        for( i = 0 ; i < j ; i++ ){
            sb.append(c_t[i]);
        }
        return new BigInteger( sb.toString() );
    }
    
    /**
     * 
     */
    public static BigInteger Decryption( BigInteger mNum ) {
        BigInteger m_t[], c_t[], aux, aux2;
        BigInteger ten = BigInteger.valueOf( 10 );
        int i, j, tam = n.toString().length()-1;
        String c = mNum.toString();
        c_t = new BigInteger[c.length()/tam + 5];
        //Partir el mensaje en n menores a n
        for(i=0, j= 0; i<c.length(); ){
            aux2 = aux = BigInteger.ZERO;
            while( i<c.length() && n.compareTo(aux)>0 ){
                aux2 = aux;
                aux = aux.multiply(ten).add( new BigInteger( c.charAt(i)+"" ) );
                i++;
            }
//            System.out.println(" c_j: "+aux2+" -> "+i+" "+c);
            if( i<c.length())
                i--;
            else
                aux2 = aux;
//            System.out.println(" c_j: "+aux2+" -> "+i+" "+c);
            c_t[j++] = aux2;
        }
        
        //PowerMod de cada mi, con la llave publica y n
        m_t = new BigInteger[c.length()/tam + 5];
        
        for( i=0; i<j; i++ ){
            m_t[i] = c_t[i].modPow(d, n);
        }
        
        //Construmos una cadena que sera el mensaje cifrado
        StringBuilder sb = new StringBuilder();
        for( i = 0 ; i < j ; i++ ){
            sb.append(m_t[i]);
        }
        return new BigInteger( sb.toString() );
    }
    
    public void cifrando( Scanner in ){
        BigInteger p, q, e, m, c, mm;
        System.out.println("*************Cifrado RSA************");
        System.out.print(" Ingresar P: ");
        p = in.nextBigInteger();
        if( p.equals(BigInteger.ZERO) )
            return;
        System.out.print(" Ingresar Q: ");
        q = in.nextBigInteger();
        System.out.print(" Ingresar E: ");
        e = in.nextBigInteger();
        System.out.print(" Ingresar el Mensaje: ");
        m = in.nextBigInteger();

        c = Encryption(m, p, q, e);
        System.out.println(" EL mensaje cifrado es: "+c+"\n\n");

        System.out.println("*************Descifrado RSA************");
        mm = Decryption( c );
        System.out.println(" EL mensaje DesCifrado es: "+mm);
        System.out.println(" Es igual al original: "+mm.equals(m)+"\n\n");
    }
    
    public void cifrando( BigInteger m ){
        BigInteger c, mm;
        
        System.out.println("*************Cifrado RSA************");
        System.out.println("El mensaje original es: "+m);
        c = Encryption(m, 1);
        System.out.println("Llave publica: < "+p+" , "+n+" >");
        System.out.println("Llave publica: < "+q+" , "+n+" >");
        System.out.println("E: "+e+" D: "+d);
        System.out.println("El mensaje cifrado es: " + c);
        
        System.out.println("\n\n*************Descifrado RSA************");
        mm = Decryption( c );
        System.out.println(" EL mensaje DesCifrado es: "+mm);
        System.out.println(" Es igual al original: "+mm.equals(m)+"\n\n");
        
    }
    
    public static void main( String args[] ){
        Scanner in = new Scanner( System.in );
        RSAJava rsaj = new RSAJava();
        BigInteger mensaje;
        int opc;
        
        
        //Menu de opciones
        while( true ) {
            System.out.println("\t*** Menu ***");
            System.out.println("  1) Cifrado conociendo p, q, e ");
            System.out.println("  2) Cifrado desde 0");
            System.out.println("  *) Otra opcion salir ");
            System.out.print("Ingrese la opcion: ");
            opc = in.nextInt();
            
            if( opc==1 ){
                rsaj.cifrando(in);
            } else if( opc==2 ){
                System.out.print("Ingrese el mensaje: ");
                mensaje = in.nextBigInteger();
                rsaj.cifrando( mensaje );
            } else {
                break;
            }
        }
    }
    
}


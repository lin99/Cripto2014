
package AES;

/**
 * Clase encarga de coordinar las acciones y realizar la encripcion por AES
 * @version 1.0
 * @author JuanFelipe
 */
public class AESEncryption {
    int N_FIL = 4;
    
    public int[] Encryption( int[]plaintext, int[]key ){
        int n = plaintext.length;
        int nCol = n/N_FIL;
        int[] cyphertext = new int[n];
        int[][] state = new int[N_FIL][nCol];
        int[][] subKey;
        AESActions actions = new AESActions();
        AESKeyGenerator keyGenerator = new AESKeyGenerator( key );
        int Nr = 10; //Por lo pronto con 10 Rouds luego cambiar.
        //Pasar Plaintext a state
        for( int i = 0; i < nCol; i++){
            for (int j = 0; j < N_FIL; j++) {
                state[j][i] = plaintext[ nCol*i+j ];
            }
        }
        print( state, "Inicial" );
        
        //Stage 1:
        //Llave temporal
        subKey = keyGenerator.getSubKey(0);
        state = actions.addRoundKey(state, subKey);
        
        //Stage 2: Nr-1 Rounds
        for( int i=0; i<Nr-1; i++){
            state = actions.subBytes(state, AESActions.subBytes);
            //print( state, "Round"+i+" SB ");
            state = actions.shiftRows(state);
            state = actions.MixColumns(state, AESActions.poli);
            //print( state, "Round"+i);
            
            subKey = keyGenerator.getSubKey(i+1);
            state = actions.addRoundKey(state, subKey);
        }
        //Stage 3:
        state = actions.subBytes(state, AESActions.subBytes);
        state = actions.shiftRows(state);
        subKey = keyGenerator.getSubKey(Nr);
        System.out.println("Key "+  Nr + " -> " + subKey);
        state = actions.addRoundKey(state, subKey);
        //Reescribir el cyphertext
        for( int i = 0; i < nCol; i++){
            for (int j = 0; j < N_FIL; j++) {
                cyphertext[nCol*i+j] = state[j][i];
            }
        }
        
        return cyphertext;
    }

    private void print(int[][] state, String msg) {
        System.out.println(" IMPRIMIENDO ESTADO "+msg+"\n");
        for( int i = 0; i <state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                System.out.print(" "+Integer.toString(state[i][j], 16));
            }
            System.out.println("");
        }
        System.out.println("\n FIN impresion de ESTADO "+msg);
    }
    
    
    public static void main( String args[] ){
        AESEncryption aese = new AESEncryption();
        int[] mensaje = { 0x41, 0x45, 0x53, 0x20, 0x65, 0x73, 0x20, 0x6d,
                          0x75, 0x79, 0x20, 0x66, 0x61, 0x63, 0x69, 0x6c };
		
        int[] llave = {0x2b, 0x7e, 0x15, 0x16, 0x28, 0xae, 0xd2, 0xa6,
                    0xab, 0xf7, 0x15, 0x88, 0x09, 0xcf, 0x4f, 0x3c};
        
        int[] cifrado = aese.Encryption(mensaje, llave);
        
        System.out.println(" TEXTO CIFRADO ");
        for( int i=0; i < cifrado.length; i++)
            System.out.print(" "+Integer.toString(cifrado[i], 16) );
        System.out.println("");
        
    }
}

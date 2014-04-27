/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AES;

/**
 *
 * @author w7
 */
public class AESDecryption {
    int N_FIL = 4;
    
    public int[] Decryption( int[]plaintext, int[]key ){
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
        subKey = keyGenerator.getSubKey(Nr);
        state = actions.addRoundKey(state, subKey);
        state = actions.subBytes(state, AESActions.subBytes);
        state = actions.invShiftRows(state);
        
        //Stage 2: Nr-1 Rounds
        for( int i = Nr - 1 ; i > 0; i--){
            state = actions.addRoundKey(state, subKey);
            state = actions.MixColumns(state, AESActions.poliInv);
            state = actions.subBytes(state, AESActions.subBytes);
            //print( state, "Round"+i+" SB ");
            
            state = actions.invShiftRows(state);
            
//            print( state, "Round"+i);
           
            subKey = keyGenerator.getSubKey(i - 1);
            state = actions.addRoundKey(state, subKey);
        }
        //Stage 3:
        
        subKey = keyGenerator.getSubKey(0);
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
        AESDecryption aese = new AESDecryption();
        int[] mensaje = { 0xe4, 0x48, 0xe7, 0x74, 0xa3, 0x74, 0xd9, 0xc,
                          0xc3, 0x3c, 0x22, 0xaf, 0x9b, 0x8e, 0xab, 0x7f };
		
        int[] llave = {0x2b, 0x7e, 0x15, 0x16, 0x28, 0xae, 0xd2, 0xa6,
                    0xab, 0xf7, 0x15, 0x88, 0x09, 0xcf, 0x4f, 0x3c};
        
        int[] cifrado = aese.Decryption(mensaje, llave);
        
        System.out.println(" TEXTO DESCRIFRADO ");
        for( int i=0; i < cifrado.length; i++)
            System.out.print(" "+Integer.toString(cifrado[i], 16) );
        System.out.println("");
        
    }
    
}

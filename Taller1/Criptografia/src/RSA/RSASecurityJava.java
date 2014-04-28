package RSA;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author JuanFelipe
 */
public class RSASecurityJava {
    KeyPairGenerator kpg;
    Key publicKey, privateKey;
    int HIGH_SECURITY = 2048;
    int LOW_SECURITY = 1024;
    
    public RSASecurityJava() throws NoSuchAlgorithmException {
        kpg = KeyPairGenerator.getInstance( "RSA" );
        kpg.initialize( LOW_SECURITY );
    }

    public RSASecurityJava( int level ) {
        super();
        if( level > 0 )
            kpg.initialize( HIGH_SECURITY );
    }

    public Key getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(Key publicKey) {
        this.publicKey = publicKey;
    }

    public Key getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(Key privateKey) {
        this.privateKey = privateKey;
    }
    
    /**
     * Genera las llaves del metodo RSA
     */
    public void generateKeys(){
        KeyPair kp = kpg.genKeyPair();
        publicKey = kp.getPublic();
        privateKey = kp.getPrivate();
    }
}

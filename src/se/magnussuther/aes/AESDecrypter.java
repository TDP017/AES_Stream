package se.magnussuther.aes;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;



public class AESDecrypter extends AES {
	
	public AESDecrypter(final String passphrase, final String keyGenerationAlgorithm, final String cipherModePadding, final byte[] initializationVector, final byte[] salt, final int hashIterations, final int keyLength) {
		super(passphrase, keyGenerationAlgorithm, cipherModePadding, initializationVector, salt, hashIterations, keyLength);
	}
	
	public CipherInputStream streamDecrypt(InputStream is, final Cipher c) throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IOException {
		IvParameterSpec iv = new IvParameterSpec(getInitializationVector());
		c.init(Cipher.DECRYPT_MODE, getSecretKey(), iv);
		CipherInputStream cis = new CipherInputStream(is, c);
		return cis;
	}
}

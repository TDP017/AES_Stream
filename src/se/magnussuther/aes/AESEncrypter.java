package se.magnussuther.aes;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class AESEncrypter extends AES {
	
	public AESEncrypter(final String passphrase, final String keyGenerationAlgorithm, final String cipherModePadding, final byte[] initializationVector, final byte[] salt, final int hashIterations, final int keyLength) {
		super(passphrase, keyGenerationAlgorithm, cipherModePadding, initializationVector, salt, hashIterations, keyLength);
	}
	
	
	public byte[] encryptData(final byte[] data, Cipher c) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		SecretKey sk = getSecretKey();
		IvParameterSpec iv = new IvParameterSpec(getInitializationVector());
		c.init(Cipher.ENCRYPT_MODE, sk, iv);
		byte[] bytes = c.doFinal(data);
		return bytes;
	}
}

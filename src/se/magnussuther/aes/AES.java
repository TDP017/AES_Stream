package se.magnussuther.aes;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;

public class AES {
	
	private final String mPassphrase;
	private final String mKeyGenerationAlgorithm;
	private final String mCipherModePadding;
	private final byte[] mInitializationVector;
	private final byte[] mSalt;
	private final int mHashIterations;
	private final int mKeyLength;
	
	private SecretKey sk = null;
	
	public AES(final String passphrase, final String keyGenerationAlgorithm, final String cipherModePadding, final byte[] initializationVector, final byte[] salt, final int hashIterations, final int keyLength) {
		mPassphrase = passphrase;
		mKeyGenerationAlgorithm = keyGenerationAlgorithm;
		mCipherModePadding = cipherModePadding;
		mInitializationVector = initializationVector;
		mSalt = salt;
		mHashIterations = hashIterations;
		mKeyLength = keyLength;
	}
	
	
	public String getPassphrase() {
		return mPassphrase;
	}


	public String getKeyGenerationAlgorithm() {
		return mKeyGenerationAlgorithm;
	}


	public String getCipherModePadding() {
		return mCipherModePadding;
	}


	public byte[] getInitializationVector() {
		return mInitializationVector;
	}


	public byte[] getSalt() {
		return mSalt;
	}


	public int getHashIterations() {
		return mHashIterations;
	}


	public int getKeyLength() {
		return mKeyLength;
	}

	
	public SecretKey getSecretKey() throws InvalidKeySpecException, NoSuchAlgorithmException {
		if (sk == null) {
			sk = calculateSecretKey();
		}
		return sk;
	}
	
	private SecretKey calculateSecretKey() throws InvalidKeySpecException, NoSuchAlgorithmException {
		char[] passphrase = getPassphrase().toCharArray();
		PBEKeySpec pbeKeySpec = new PBEKeySpec(passphrase, getSalt(), getHashIterations(), getKeyLength());
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(getKeyGenerationAlgorithm());
		return keyfactory.generateSecret(pbeKeySpec);
	}
	
	public Cipher getCipher() throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		return Cipher.getInstance(getCipherModePadding());
	}
	
	public IvParameterSpec calculateIvParameterSpec() {
		return new IvParameterSpec(getInitializationVector());
	}
	
	public AESEncrypter getEncrypter() {
		return new AESEncrypter(mPassphrase, mKeyGenerationAlgorithm, mCipherModePadding, mInitializationVector, mSalt, mHashIterations, mKeyLength);
	}
	
	public AESDecrypter getDecrypter() {
		return new AESDecrypter(mPassphrase, mKeyGenerationAlgorithm, mCipherModePadding, mInitializationVector, mSalt, mHashIterations, mKeyLength);
	}
}

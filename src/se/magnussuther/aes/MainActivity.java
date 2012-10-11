package se.magnussuther.aes;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;

public class MainActivity extends Activity implements MainViewEvents {
			
	private MainView mMainView = null;	
	private ImageFetcher mImageFetcher = null;
	private AES mAES = null;
	
	private Cipher cipher = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mMainView = (MainView) findViewById(R.id.mainview);
        mMainView.registerEventListener(this);
        
        mImageFetcher = new ImageFetcher();
        
        byte[] iv = {0xA,1,0xB,5,4,0xF,7,9,0x17,3,1,6,8,0xC,0xD,91};
        byte[] salt = {0,1,2,3,4,5,6,7,8,9,0xA,0xB,0xC,0xD,0xE,0xF};
        mAES = new AES("secretdonald", "PBEWITHSHA256AND256BITAES-CBC-BC", "AES/CBC/PKCS7Padding", iv, salt, 10000, 256);
        // PBEWITHSHAAND128BITAES-CBC-BC
        // PBEWITHSHA256AND256BITAES-CBC-BC

        
		try {
			cipher = mAES.getCipher();
		} catch (InvalidKeyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidKeySpecException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchPaddingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidAlgorithmParameterException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	    File orig = new File(Environment.getExternalStorageDirectory() + "/AES/Orig");
	    String[]originals = orig.list();
	    for (String path : originals) {
	        try {
	        	File f = new File(Environment.getExternalStorageDirectory() + "/AES/Orig/" + path);
	            byte[] encrypted = encryptImage(f.getAbsolutePath());
	            File crypt = new File(Environment.getExternalStorageDirectory() + "/AES/Crypt/" + f.getName());
	            crypt.createNewFile();
	        	BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(crypt));
	        	bos.write(encrypted);
	        } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
    }

	private byte[] encryptImage(final String imagePath) {
		try {
			byte[] origImage = mImageFetcher.getImageBytes(imagePath);
			byte[] bytes = mAES.getEncrypter().encryptData(origImage, cipher);
			return bytes;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public void onMainViewDraw(Canvas canvas) throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		File crypt = new File(Environment.getExternalStorageDirectory() + "/AES/Crypt");
		String[] files = crypt.list();
		Arrays.sort(files);
		
		FileInputStream fis;
		try {
			fis = new FileInputStream(Environment.getExternalStorageDirectory() + "/AES/Crypt/i500p.jpg");
			CipherInputStream cis = mAES.getDecrypter().streamDecrypt(fis, cipher);
			Bitmap bm = BitmapFactory.decodeStream(cis);
			canvas.drawBitmap(bm, 0 , 0, null);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}



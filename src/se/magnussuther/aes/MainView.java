package se.magnussuther.aes;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.NoSuchPaddingException;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


interface MainViewEvents {
	public void onMainViewDraw(Canvas canvas) throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException;
}

public class MainView extends View {
	private static final String TAG = "AES MainActivity";
	
	private MainViewEvents mEventListener = null;
	
	public MainView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void registerEventListener(MainViewEvents ev) {
		mEventListener = ev;
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		Log.d(TAG, "Calling onMainViewDraw()");
		try {
			mEventListener.onMainViewDraw(canvas);
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

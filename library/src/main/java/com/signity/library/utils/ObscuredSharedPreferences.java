package com.signity.library.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Base64;

import java.util.Map;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 * Created by signity.
 */


public class ObscuredSharedPreferences implements SharedPreferences  {

	
	// password will be your device id.
		
	protected static final String UTF8 = "utf-8";
    private static final char[] SEKRIT = {1,2,3,4,5}; // INSERT A RANDOM PASSWORD HERE.
                                               // Don't use anything you wouldn't want to
                                           // get out there if someone decompiled
                                               // your app.


    protected SharedPreferences delegate;
    protected Context context;

	
    public ObscuredSharedPreferences(Context context, SharedPreferences delegate) {
        this.delegate = delegate;
        this.context = context;
    }
    
    
    public class Editor implements SharedPreferences.Editor {

    	protected SharedPreferences.Editor delegate;

        public Editor() {
            this.delegate = ObscuredSharedPreferences.this.delegate.edit();                    
        }
    	
		@Override	
		public SharedPreferences.Editor putString(String key,
				String value) {
			
			 delegate.putString(key, encrypt(value));
	            return this;
		}

		@Override
		public SharedPreferences.Editor putStringSet(
				String key, Set<String> values) {
			
			return null;
		}

		@Override
		public SharedPreferences.Editor putInt(String key,
				int value) {
			
			 delegate.putString(key, encrypt(Integer.toString(value)));
	            return this;
		}

		@Override
		public SharedPreferences.Editor putLong(String key,
				long value) {
			
			delegate.putString(key, encrypt(Long.toString(value)));
            return this;
		}

		@Override
		public SharedPreferences.Editor putFloat(String key,
				float value) {
			
			 delegate.putString(key, encrypt(Float.toString(value)));
			return this;
		}

		@Override
		public SharedPreferences.Editor putBoolean(String key,
				boolean value) {
			 delegate.putString(key, encrypt(Boolean.toString(value)));
			return this;
		}

		@Override
		public SharedPreferences.Editor remove(String key) {
			
			delegate.remove(key);
            return this;
		}

		@Override
		public SharedPreferences.Editor clear() {
			
			 delegate.clear();
	            return this;
		}

		@Override
		public boolean commit() {
			
			  return delegate.commit();
		}

		@Override
		public void apply() {
			
			 delegate.apply();
		}
    	
    }
    
   
    
	@Override
	public Map<String, ?> getAll() {
		
		throw new UnsupportedOperationException();
	}

	@Override
	public String getString(String key, String defValue) {
		
		    final String v = delegate.getString(key, null);
	        return v != null ? decrypt(v) : defValue;
	}

	@Override
	public Set<String> getStringSet(String key, Set<String> defValues) {
		
		return null;
	}

	@Override
	public int getInt(String key, int defValue) {
		
		final String v = delegate.getString(key, null);
        return v!=null ? Integer.parseInt(decrypt(v)) : defValue;
	}

	@Override
	public long getLong(String key, long defValue) {
		
		    final String v = delegate.getString(key, null);
	        return v!=null ? Long.parseLong(decrypt(v)) : defValue;
	}

	@Override
	public float getFloat(String key, float defValue) {
		
		final String v = delegate.getString(key, null);
        return v!=null ? Float.parseFloat(decrypt(v)) : defValue;
	}

	@Override
	public boolean getBoolean(String key, boolean defValue) {
		
		final String v = delegate.getString(key, null);
        return v!=null ? Boolean.parseBoolean(decrypt(v)) : defValue;
	}

	@Override
	public boolean contains(String key) {
		
		return delegate.contains(key);
	}

	@Override
	public Editor edit() {
		
		return new Editor();
	}

	@Override
	public void registerOnSharedPreferenceChangeListener(
			OnSharedPreferenceChangeListener listener) {
		
		delegate.registerOnSharedPreferenceChangeListener(listener);    
	}

	@Override
	public void unregisterOnSharedPreferenceChangeListener(
			OnSharedPreferenceChangeListener listener) {
		
		 delegate.unregisterOnSharedPreferenceChangeListener(listener);    
	}
	
	
	
	protected String encrypt( String value ) {

        try {
            final byte[] bytes = value!=null ? value.getBytes(UTF8) : new byte[0];
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
            SecretKey key = keyFactory.generateSecret(new PBEKeySpec(SEKRIT));
            Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
            pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(Settings.Secure.getString(context.getContentResolver(),Settings.System.ANDROID_ID).getBytes(UTF8), 20));
            return new String(Base64.encode(pbeCipher.doFinal(bytes), Base64.NO_WRAP),UTF8);

        } catch( Exception e ) {
            throw new RuntimeException(e);
        }

    }

    protected String decrypt(String value){
        try {
            final byte[] bytes = value!=null ? Base64.decode(value,Base64.DEFAULT) : new byte[0];
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
            SecretKey key =  keyFactory.generateSecret(new PBEKeySpec(SEKRIT));
            Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
            pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(Settings.Secure.getString(context.getContentResolver(),Settings.System.ANDROID_ID).getBytes(UTF8), 20));
            return new String(pbeCipher.doFinal(bytes),UTF8);

        } catch( Exception e) {
            throw new RuntimeException(e);
        }
    }


}

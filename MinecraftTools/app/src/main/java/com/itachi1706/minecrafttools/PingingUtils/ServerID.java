package com.itachi1706.minecrafttools.PingingUtils;

import android.annotation.SuppressLint;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Future class for Authentication with Mojang AuthServers
 * @author itachi1706
 *
 */
@SuppressLint("DefaultLocale")
public class ServerID {
	
final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	
	public static String javaHexDigest(String data)
    {
        MessageDigest digest = null;
        try {
			digest = MessageDigest.getInstance("SHA-1");
			digest.reset();
			digest.update(data.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
		        e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
		        e.printStackTrace();
		}
        byte[] hash = digest.digest();
        boolean negative = (hash[0] & 0x80) == 0x80;
        if (negative)
            hash = twosCompliment(hash);
        String digests = getHexString(hash);
        if (digests.startsWith("0"))
        {
                digests = digests.replaceFirst("0", digests);
        }
        if (negative)
        {
                digests = "-" + digests;
        }
        digests = digests.toLowerCase();
        return digests;
    }
	
	public static String getHexString(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for ( int j = 0; j < bytes.length; j++ ) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
	
	private static byte[] twosCompliment(byte[] p)
    {
        int i;
        boolean carry = true;
        for (i = p.length - 1; i >= 0; i--)
        {
            p[i] = (byte)~p[i];
            if (carry)
            {
                carry = p[i] == 0xFF;
                p[i]++;
            }
        }
        return p;
    }


}

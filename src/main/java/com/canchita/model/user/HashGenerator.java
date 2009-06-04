package com.canchita.model.user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.joda.time.DateTime;

public class HashGenerator {

	private static final char[] HEX = {
		'0', '1', '2', '3', '4', '5', '6', '7',
		'8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
	    };
	
	public static String getHash(String salt, String algorithm) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance(algorithm);
		
		String toEncrypt = new DateTime().getMillis() + salt;
		
		digest.update(toEncrypt.getBytes());
		
		return HashGenerator.toHex(digest.digest());
	}
	
	/**
     * Turns array of bytes into string representing each byte as
     * unsigned hex number.
     *
     * @param hash	array of bytes to convert to hex-string
     * @return	generated hex string
     */
    public static String toHex(byte hash[])
    {
    	StringBuffer buf = new StringBuffer(hash.length * 2);

		for (int idx=0; idx<hash.length; idx++) {
			buf.append(HEX[(hash[idx] >> 4) & 0x0f]).append(HEX[hash[idx] & 0x0f]);
		}

		return buf.toString();
    }

	
}

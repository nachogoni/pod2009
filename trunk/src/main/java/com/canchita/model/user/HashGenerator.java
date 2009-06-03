package com.canchita.model.user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.joda.time.DateTime;

public class HashGenerator {

	public static byte[] getHash(String salt, String algorithm) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance(algorithm);
		
		String toEncrypt = new DateTime().getMillis() + salt;
		
		digest.update(toEncrypt.getBytes());
		
		return digest.digest();
	}
	
}

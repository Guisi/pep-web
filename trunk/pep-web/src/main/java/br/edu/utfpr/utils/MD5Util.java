package br.edu.utfpr.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import br.edu.utfpr.constants.Constantes;

public class MD5Util {

	private MD5Util() {}
	
	public static String stringToMD5(String string) {
		try {
			MessageDigest m = MessageDigest.getInstance(Constantes.PASSWORD_ENCODER);
			m.update(string.getBytes(), 0, string.length());
			return new BigInteger(1, m.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
}

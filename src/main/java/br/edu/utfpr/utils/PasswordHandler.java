package br.edu.utfpr.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;

import br.edu.utfpr.constants.Constantes;

/**
 * Classe utilitaria para geracao da senha com encriptacao e salt
 * 
 * @author douglas.guisi
 */
public class PasswordHandler {

	private static final Random RANDOM = new SecureRandom();
	public static final int PASSWORD_LENGTH = 8;

	/**
	 * Encripta senha com MD5
	 * 
	 * @param passwd
	 * @return String
	 * @throws NoSuchAlgorithmException
	 */
	public static String encryptPassword(String passwd) {
		/*
		 * Concatena a senha com Salt para dificultar a pesquisa da senha com MD5
		 */
		String senha = createSalt(passwd) + passwd;

		try {
			MessageDigest md = MessageDigest.getInstance(Constantes.PASSWORD_ENCODER);
			md.update(senha.getBytes());
			return Base64.encodeBase64String(md.digest());
		} catch (NoSuchAlgorithmException e) {
			return passwd;
		}
	}

	/**
	 * Cria um salt a partir da senha informada e adiciona na senha, com o intuito de dificultar a a descoberta da senha codificada com MD5.
	 * 
	 * @param text
	 * @return String
	 */
	private static String createSalt(String text) {

		String salt;

		/* Verifica o tamanho do texto informado */
		int length = text.length();

		if (length <= 2) {
			salt = text;
			return salt;
		}

		/* Caso contrario retorna String */
		salt = text.substring(1, (length - 1));

		return salt;
	}

	/**
	 * Faz a geracao de uma senha randomica. 
	 * SecureRandom faz um getBytes para geracao do seed. NextDouble gera um numero de 0 (inclusive) a 1 (exclusive).
	 * 
	 * @return Senha gerada
	 */
	public static String generateRandomPassword() {

		StringBuilder senha;
		boolean strenght = false;
		
		String lowerLetras = "abcdefghjkmnpqrstuvwxyz";
		String upperLetras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String specialCarac = "!@#$&%*_=-";
		String numbers = "0123456789";
		
		senha = new StringBuilder();
		
		while(!strenght) {
			for (int i = 0 ; i < PASSWORD_LENGTH ; i++) {
				int index;
				String caracteres;
				
				if (i < 4)
					caracteres = lowerLetras;
				else if (i == 5)
					caracteres = specialCarac;
				else if (i == 6)
					caracteres = upperLetras;
				else
					caracteres = numbers;
				
				index = (int) (RANDOM.nextDouble() * caracteres.length());
				
				senha.append(caracteres.substring(index, index + 1));
			}
			
			strenght = validatePasswordStrength(senha.toString());
		}

		return senha.toString();
	}

	/**
	 * Faz a validacao da forca da senha se esta de acordo com o padrao estabelecido. Padrao: Conter pelo menos maiuscula, caracter especial, numero e entre 6 a 10 caracteres.
	 * 
	 * @param passwd
	 *            Senha passada para validacao
	 * @return boolean
	 * @author johnkutzke 
	 */
	public static boolean validatePasswordStrength(String passwd) {

		StringBuilder regex;

		regex = new StringBuilder();
		regex.append("^");						// Inicio da string
		regex.append("(?=.*[a-z|A-Z])");		// Pelo menos uma letra maiuscula
		regex.append("(?=.*[!@#$&%*_=-])");		// Pelo menos um caracter especial
		regex.append("(?=.*[0-9])");			// Pelo menos um numero de 0 a 9
		regex.append("(?=\\S+$)");				// Nao pode ter espacos			
		regex.append(".{6,8}");					// Deve ter entre 6 a 8 caracteres
		regex.append("$");						// Fim da string

		return passwd.matches(regex.toString());
	}

	public static void main(String[] args) {
		System.out.println(PasswordHandler.encryptPassword("123456"));
	}
}

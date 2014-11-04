package br.edu.utfpr.utils;

/**
 * @author douglas.guisi
 */
public class UserThreadLocal {

	private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

	public static ThreadLocal<String> getThreadLocal() {
		return threadLocal;
	}
}
package br.edu.utfpr.utils;

/**
 * @author douglas.guisi
 */
public class UserThreadLocal {

	private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

	public static ThreadLocal<Long> getThreadLocal() {
		return threadLocal;
	}
}
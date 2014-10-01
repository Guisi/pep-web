package br.edu.utfpr.utils;

import org.apache.commons.lang.StringUtils;

public class FormatUtils {

	public static String getFormattedPhoneNumber(String phoneNumber) {
		if (StringUtils.isNotBlank(phoneNumber) && phoneNumber.length() > 9) {
			int prefixSize = phoneNumber.length() - 6;
			phoneNumber = phoneNumber.replaceFirst("(\\d{2})(\\d{" + prefixSize + "})(\\d+)", "($1) $2-$3");
		}
		return phoneNumber;
	}
	
	public static String getFormattedCpf(String cpf) {
		if (StringUtils.isNotBlank(cpf) && cpf.length() == 11) {
			cpf = cpf.replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
		}
		return cpf;
	}
}

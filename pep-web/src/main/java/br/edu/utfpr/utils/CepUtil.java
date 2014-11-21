package br.edu.utfpr.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.MessageFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class CepUtil {
	
	private static final String URL_BUSCAR_CEP = "http://viavirtual.com.br/webservicecep.php?cep={0}";

	public static EnderecoCorreios buscarEnderecoPorCep(String cep) {
		EnderecoCorreios endereco = new EnderecoCorreios();
		if (StringUtils.isNotEmpty(cep)) {
			cep = FormatUtils.somenteDigitos(cep);
			
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).build();
			CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
			HttpGet httpGet = new HttpGet(MessageFormat.format(URL_BUSCAR_CEP, cep));
			try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
				BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "ISO-8859-1"));
				String linhaEndereco = in.readLine();
				
				if (StringUtils.isNotBlank(linhaEndereco)) {
					String[] campos = linhaEndereco.split("\\|\\|");
					if (campos.length == 5) {
						endereco.setLogradouro(campos[0]);
						endereco.setBairro(campos[1]);
						endereco.setCidade(campos[2]);
						endereco.setCep(campos[3]);
						endereco.setUf(campos[4]);
					}
				}
			} catch (Exception e) {}
		}
		return endereco;
	}
	
	public static void main(String[] args) {
		System.out.println( CepUtil.buscarEnderecoPorCep("80240-060") );
	}
}

package br.edu.utfpr.email;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import br.edu.utfpr.exception.AnexoException;

/**
 * Bean para representar e fazer as operacoes do Anexo de um Email
 * 
 * @author mauriciovigolo
 */
public class Anexo {

	/**
	 * Nome do arquivo anexo ao email
	 */
	private String filename;
	/**
	 * Nome da extensao do arquivo
	 */
	private String fileExtension;
	/**
	 * Conteudo do arquivo anexo como um array de bytes
	 */
	private byte[] content;
	/**
	 * Tipo do arquivo anexado. Deve ser de um tipo MimeType.
	 */
	private String mimeType;

	
	/**
	 * Getter de filename
	 * 
	 * @return o valor de filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * Setter de filename
	 * 
	 * @param filename
	 *            Valor a ser atribuido a filename
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * Getter de fileExtension
	 * 
	 * @return o valor de fileExtension
	 */
	public String getFileExtension() {
		return fileExtension;
	}
	

	/**
	 * Getter de fullFileName
	 * @return o valor de fullFileName
	 */
	public String getFullFilename() {
		return filename + "." + fileExtension;
	}

	/**
	 * Setter de fileExtension
	 * 
	 * @param fileExtension
	 *            Valor a ser atribuido a fileExtension
	 */
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	/**
	 * Getter de content
	 * 
	 * @return o valor de content
	 */
	public byte[] getContent() {
		return content;
	}

	/**
	 * Setter de content
	 * 
	 * @param content
	 *            Valor a ser atribuido a content
	 */
	public void setContent(byte[] content) {
		this.content = content;
	}

	/**
	 * Setter de mimeType
	 * 
	 * @return the mimeType
	 */
	public String getMimeType() {
		return mimeType;
	}

	/**
	 * Getter de mimeType 
	 * 
	 * @param mimeType the mimeType to set
	 */
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	/**
	 * Salva o conteudo do anexo em arquivo no servidor.
	 * 
	 * @param path
	 *            <p>
	 *            Caminho no qual sera gravado o anexo
	 *            </p>
	 * @param fileName
	 *            <p>
	 *            Nome do arquivo com extensao que sera gravado
	 *            </p>
	 * @return boolean
	 *         <p>
	 *         Retorna o resultado da gravacao do arquivo em disco
	 *         </p>
	 * @throws AnexoException
	 */
	public boolean saveContentToDisk(String path, String fileName) throws AnexoException {
		File file;

		// Faz as checagens para gravacao no nome do arquivo
		// 1 - Verifica se os parametros sao nulos
		if (path == null || path.isEmpty() || fileName == null
				|| fileName.isEmpty()) {
			throw new AnexoException("Os parametros de entrada para gravacao do anexo nao foram informados");
		}
		// 2 - Se o caminho nao tem o / no final, acrescenta o file separator \ -> windows ou / -> unix
		if (!(path.substring(path.length())).matches("[\\|/]")) {
			path = path.substring(0, path.length() - 1);
			path = path.concat(System.getProperty("file.separator"));
		}
		// 3 - Se o anexo tem conteudo
		if (content == null || content.length == 0) {
			throw new AnexoException("Anexo nao possui conteudo a ser gravado. O array de bytes esta vazio!");
		}

		// Cria o file para gravacao dos dados
		file = new File(path + System.getProperty("file.separator") + fileName);

		// Grava os dados no arquivo criado
		try {
			FileUtils.writeByteArrayToFile(file, content);
		} catch (IOException e) {
			throw new AnexoException("Nao foi possivel gravar o arquivo no servidor. Detalhes:"+e.getMessage());
		}

		return true;
	}

}

package br.edu.utfpr.constants;

/**
 * Enum com os ContentTypes
 * 
 * @author douglas.guisi
 */
public enum ContentType {

	/**
	 * Plain text: documents; program listings
	 */
	TEXT_PLAIN("text/plain"),
	/**
	 * HTML text data (RFC 1866)
	 */
	TEXT_HTML("text/html"),
	/**
	 * Cascading Stylesheets
	 */
	TEXT_CSS("text/css"),
	/**
	 * Messages with multiple parts
	 */
	MULTIPART_MIXED("multipart/mixed"),
	/**
	 * Messages with multiple, alternative parts
	 */
	MULTIPART_ALTERNATIVE("multipart/alternative"),
	/**
	 * Message with multiple, related parts
	 */
	MULTIPART_RELATED("multipart/related"),
	/**
	 * Multiple parts are digests
	 */
	MULTIPART_DIGEST("multipart/digest"),
	/**
	 * Para arquivos .pdf 
	 */
	APPLICATION_PDF("application/pdf"),
	/**
	 * Arquivos .doc
	 */
	APPLICATION_WORD("application/msword"),
	/**
	 * Arquivos .xls
	 */
	APPLICATION_EXCEL("application/excel"),
	/**
	 * Arquivos .xml
	 */
	APPLICATION_XML("application/xml"),
	/**
	 * Imagem .png
	 */
	APPLICATION_PNG("image/png"),
	/**
	 * Imagem .jpg
	 */
	APPLICATION_JPG("image/jpeg"),
	/**
	 * Arquivo compactado .zip
	 */
	APPLICATION_ZIP("application/x-compressed"),
	/**
	 * Arquivo .bz
	 */
	APPLICATION_BZIP("application/x-bzip"),
	/**
	 * Arquivo .ppt
	 */
	APPLICATION_POWERPOINT("application/mspowerpoint");
	
	/**
	 * Valor do ContentType, exemplo: text/plain,.. 
	 */
	private String value;

	
	/**
	 * Construtor default.
	 * 
	 * @param name
	 */
	private ContentType(String name) {
		this.value = name;
	}

	/**
	 * Getter de value
	 * 
	 * @return o valor de value
	 */
	public String value() {
		return value;
	}

}

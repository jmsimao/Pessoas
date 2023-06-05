package br.com.ifsp.Pessoas.ErrorResponse;

public class FoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String erroInfo;

	public FoundException(String mensagem, String erroInfo) {
		super(mensagem);
		this.erroInfo = erroInfo;
	}
	
	public String getErroInfo() {
		return this.erroInfo;
	}
	
}

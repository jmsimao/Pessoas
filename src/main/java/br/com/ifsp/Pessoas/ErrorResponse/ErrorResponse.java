package br.com.ifsp.Pessoas.ErrorResponse;

public class ErrorResponse {

	private final int httpStatusValue;
	private final String httpStatusInfo;
	private String mensagem;
	private String erroInfo;
	private String nomeClasse;
	
	public ErrorResponse(int httpStatusValue, String httpStatusInfo, String mensagem, String erroInfo,
			String nomeClasse) {
		super();
		this.httpStatusValue = httpStatusValue;
		this.httpStatusInfo = httpStatusInfo;
		this.mensagem = mensagem;
		this.erroInfo = erroInfo;
		this.nomeClasse = nomeClasse;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getErroInfo() {
		return erroInfo;
	}

	public void setErroInfo(String erroInfo) {
		this.erroInfo = erroInfo;
	}

	public String getNomeClasse() {
		return nomeClasse;
	}

	public void setNomeClasse(String nomeClasse) {
		this.nomeClasse = nomeClasse;
	}

	public int getHttpStatusValue() {
		return httpStatusValue;
	}

	public String getHttpStatusInfo() {
		return httpStatusInfo;
	}
	
	
}

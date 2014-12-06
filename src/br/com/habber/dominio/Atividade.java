package br.com.habber.dominio;

public class Atividade {
	private String atvName;
	private String atvSubject;
	private int prazo;
	private int tipoDePrazo;
	private boolean processoEncadeado;
	private String nomeProcessoEncadeado;
	private boolean tipoDePrazoCustomizado;
	private String variavelDePrazo;
	
	public String getAtvSubject() {
		return atvSubject;
	}
	public void setAtvSubject(String atvSubject) {
		this.atvSubject = atvSubject;
	}
	public int getPrazo() {
		return prazo;
	}
	public void setPrazo(int prazo) {
		this.prazo = prazo;
	}
	public String getTipoDePrazo() {
		String descricaoPrazo = "";
		if(this.tipoDePrazo == 0){
			descricaoPrazo = "Minutos";
		}else if(this.tipoDePrazo == 1){
			descricaoPrazo = "Horas";
		}else if(this.tipoDePrazo == 2){
			descricaoPrazo = "Dias";
		}else{
			descricaoPrazo = String.valueOf(this.tipoDePrazo);
		}
		
		return descricaoPrazo;
	}
	public void setTipoDePrazo(int tipoDePrazo) {
		this.tipoDePrazo = tipoDePrazo;
	}
	public boolean isProcessoEncadeado() {
		return processoEncadeado;
	}
	public void setProcessoEncadeado(boolean processoEncadeado) {
		this.processoEncadeado = processoEncadeado;
	}
	public String getNomeProcesso() {
		return nomeProcessoEncadeado;
	}
	public void setNomeProcesso(String nomeProcesso) {
		this.nomeProcessoEncadeado = nomeProcesso;
	}
	public boolean isTipoDePrazoCustomizado() {
		return tipoDePrazoCustomizado;
	}
	public void setTipoDePrazoCustomizado(boolean tipoDePrazoCustomizado) {
		this.tipoDePrazoCustomizado = tipoDePrazoCustomizado;
	}
	public String getVariavelDePrazo() {
		return variavelDePrazo;
	}
	public void setVariavelDePrazo(String variavelDePrazo) {
		this.variavelDePrazo = variavelDePrazo;
	}
	public String getAtvName() {
		return atvName;
	}
	public void setAtvName(String atvName) {
		this.atvName = atvName;
	}
}

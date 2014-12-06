package br.com.habber.dominio;
/*
 * TODO Colocar em um pacote de dominio
 */
import java.util.ArrayList;


public class DadoRelatorio {
	private String bpdId;
	private String bpdName;
	private ArrayList<Atividade> atividades;
	public String getBpdId() {
		return bpdId;
	}
	public void setBpdId(String bpdId) {
		this.bpdId = bpdId;
	}
	public String getBpdName() {
		return bpdName;
	}
	public void setBpdName(String bpdName) {
		this.bpdName = bpdName;
	}
	public ArrayList<Atividade> getAtividades() {
		return atividades;
	}
	public void setAtividades(ArrayList<Atividade> atividades) {
		this.atividades = atividades;
	}
	public void addAtividades(int index, ArrayList<Atividade> atividades) {
		this.atividades.addAll(index,atividades);
	}
	public void addAtividades(ArrayList<Atividade> atividades) {
		this.atividades.addAll(atividades);
	}
}

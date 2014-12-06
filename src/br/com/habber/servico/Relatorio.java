package br.com.habber.servico;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import br.com.habber.dominio.DadoRelatorio;

public class Relatorio {
	public ArrayList<DadoRelatorio> trataDadosRelatorio(String caminhoArquivos) throws ParserConfigurationException, SAXException, IOException {
		ArchiveManager archiveManager = new ArchiveManager();
		File[] arquivos = archiveManager.getArquivos(caminhoArquivos);
		ArrayList<DadoRelatorio> dadosRelatoriosTratados = new ArrayList<DadoRelatorio>();
		ArrayList<String> listaDeEncadeados = new ArrayList<String>();
		ArrayList<Integer> listaDeProcesso = new ArrayList<Integer>();
		ArrayList<DadoRelatorio> listaRemover = new ArrayList<DadoRelatorio>();
		ArrayList<Integer> listaDeAtividades = new ArrayList<Integer>();
		DadoRelatorio aux = new DadoRelatorio();
		DadoRelatorio auxAnt = new DadoRelatorio();
		int auxCount = 0;
		int total = 0;
		int totalAnt = 0;

		for(int i=0;i<arquivos.length;i++){
			DadoRelatorio dadoRelatorio = archiveManager.leArquivo(arquivos[i]);
			for(int j=0;j<dadoRelatorio.getAtividades().size();j++){
				if(dadoRelatorio.getAtividades().get(j).isProcessoEncadeado()){
					listaDeEncadeados.add(dadoRelatorio.getAtividades().get(j).getNomeProcesso());
					listaDeProcesso.add(i);
					listaDeAtividades.add(j);
				}
			}
			dadosRelatoriosTratados.add(dadoRelatorio);
		}

		for(int x=0;x<listaDeEncadeados.size();x++){
			for(int z=0;z<dadosRelatoriosTratados.size();z++){
				if(listaDeEncadeados.get(x).equals(dadosRelatoriosTratados.get(z).getBpdId())){

					aux=dadosRelatoriosTratados.get(listaDeProcesso.get(x));

					if(x!=0){
						if(auxAnt.getBpdName() != null && auxAnt.getBpdName().equals(aux.getBpdName())){
							total+=totalAnt;
						}else{
							total=0;
						}
						auxCount = listaDeAtividades.get(x)+total+1;
					}else{
						auxCount = listaDeAtividades.get(x)+1;
					}

					if(auxCount <= aux.getAtividades().size()){
						aux.addAtividades(auxCount,dadosRelatoriosTratados.get(z).getAtividades());
					}else{
						aux.addAtividades(dadosRelatoriosTratados.get(z).getAtividades());
					}
					listaRemover.add(dadosRelatoriosTratados.get(z));

					auxAnt = aux;
					totalAnt=dadosRelatoriosTratados.get(z).getAtividades().size();
				}else if(dadosRelatoriosTratados.get(z).getBpdId().equals(listaDeEncadeados.get(x).substring(1))){

					aux=dadosRelatoriosTratados.get(listaDeProcesso.get(x));

					if(x!=0){
						if(auxAnt.getBpdName() != null && auxAnt.getBpdName().equals(aux.getBpdName())){
							total+=totalAnt;
						}else{
							total=0;
						}
						auxCount = listaDeAtividades.get(x)+total+1;
					}else{
						auxCount = listaDeAtividades.get(x)+1;
					}

					if(auxCount <= aux.getAtividades().size()){
						aux.addAtividades(auxCount,dadosRelatoriosTratados.get(z).getAtividades());
					}else{
						aux.addAtividades(dadosRelatoriosTratados.get(z).getAtividades());
					}
					listaRemover.add(dadosRelatoriosTratados.get(z));
					auxAnt = aux;
					totalAnt=dadosRelatoriosTratados.get(z).getAtividades().size();
				}
			}
		}

		for(int y=0;y<listaRemover.size();y++){
			dadosRelatoriosTratados.remove(listaRemover.get(y));
		}

		return dadosRelatoriosTratados;
	}
}

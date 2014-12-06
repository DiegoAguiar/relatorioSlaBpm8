package br.com.habber.servico;
/* TODO Colocar em um pacote de serviços
 */



/*
 * Alta coeção(classe especialista) e baixo acoplamento(poucas dependencias)
 */

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import br.com.habber.dominio.Atividade;
import br.com.habber.dominio.DadoRelatorio;

public class ArchiveManager {


	public File[] getArquivos(String diretorioZip){

		Unzip unzip = new Unzip();
		String diretorio = unzip.unZipIt(diretorioZip)+"\\objects";

		File diretorioXml = new File(diretorio);
		File[] xmlFile = new File[50];

		if(diretorioXml.isDirectory() && diretorioXml.canRead()){

			FilenameFilter fileNameFilter = new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					if(name.startsWith("25."))
					{
						return true;
					}
					return false;
				}
			};

			xmlFile = diretorioXml.listFiles(fileNameFilter);

		}else{
			System.out.println("Favor informar um diretório para busca dos arquivos");
		}
		return xmlFile;

	}

	public DadoRelatorio leArquivo(File arquivo) throws ParserConfigurationException, SAXException, IOException {
		DadoRelatorio dadoRelatorio = new DadoRelatorio();
		ArrayList<Atividade> atividades = new ArrayList<Atividade>();


		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(arquivo);
		doc.getDocumentElement().normalize();

		NodeList bpd = doc.getElementsByTagName("bpd");
		Node bpdNode = bpd.item(0);
		Element processo = (Element) bpdNode;
		dadoRelatorio.setBpdName(processo.getAttribute("name"));
		dadoRelatorio.setBpdId(processo.getAttribute("id"));

		NodeList atv = doc.getElementsByTagName("flowObject");
		for (int i = 0; i< atv.getLength(); i++) {
			Atividade atividade = new Atividade();
			Element dadosAtividade = (Element) atv.item(i);

			NodeList nodeComponents = dadosAtividade.getElementsByTagName("component");
			Element nodeComponent = (Element) nodeComponents.item(0);
			NodeList nodeTaskTypes = nodeComponent.getElementsByTagName("bpmnTaskType");
			String isAtividade = dadosAtividade.getAttribute("componentType");
			if(isAtividade.equals("Activity")){
				Node nodeTipoAtividade = nodeTaskTypes.item(0);
				int tipoAtividade = Integer.parseInt(nodeTipoAtividade.getTextContent());
				if(tipoAtividade == 1){ //Atividade Humana
					atividade.setProcessoEncadeado(false);
					NodeList nodeAtividades = dadosAtividade.getElementsByTagName("name");
					Node nodeAtividade = nodeAtividades.item(0);
					atividade.setAtvName(nodeAtividade.getTextContent());
					nodeAtividades = dadosAtividade.getElementsByTagName("implementation");
					Element nodeImplentacao = (Element) nodeAtividades.item(0);
					NodeList nodeDueDates = nodeImplentacao.getElementsByTagName("dueDateTime");

					if(nodeDueDates.item(0).getTextContent() != "" && nodeDueDates.item(0).getTextContent() != null && NumberUtil.isNumeric(nodeDueDates.item(0).getTextContent())){
						atividade.setPrazo(Integer.parseInt(nodeDueDates.item(0).getTextContent()));
					}else if(nodeDueDates.item(0).getTextContent() != "" && nodeDueDates.item(0).getTextContent() != null){
						atividade.setTipoDePrazoCustomizado(true);
						atividade.setVariavelDePrazo(nodeDueDates.item(0).getTextContent());
					}else{
						NodeList nodeDueDateCustom = nodeImplentacao.getElementsByTagName("dueDateCustom");
						atividade.setTipoDePrazoCustomizado(true);
						atividade.setVariavelDePrazo(nodeDueDateCustom.item(0).getTextContent());
					}

					NodeList nodedueDateTimeResolutions = nodeImplentacao.getElementsByTagName("dueDateTimeResolution");
					atividade.setTipoDePrazo(Integer.parseInt(nodedueDateTimeResolutions.item(0).getTextContent()));

					NodeList nodeSubject = nodeImplentacao.getElementsByTagName("subject");
					if(nodeSubject.getLength() > 0 && nodeSubject.item(0).getTextContent().length() > 0){
						atividade.setAtvSubject(nodeSubject.item(0).getTextContent());
					}else{
						atividade.setAtvSubject("step:"+atividade.getAtvName());
					}
					
					atividades.add(atividade);
				}else if(tipoAtividade == 5){ //Processo Vinculado
					atividade.setProcessoEncadeado(true);
					NodeList nodeAtividades = dadosAtividade.getElementsByTagName("name");
					Node nodeAtividade = nodeAtividades.item(0);
					atividade.setAtvName(nodeAtividade.getTextContent());
					nodeAtividades = dadosAtividade.getElementsByTagName("implementation");

					Element nodeImplentacao = (Element) nodeAtividades.item(0);
					NodeList nodeAttachedProcess = nodeImplentacao.getElementsByTagName("attachedProcessId");
					Node nodeAttached = nodeAttachedProcess.item(0);
					atividade.setNomeProcesso(nodeAttached.getTextContent());
					
					atividade.setAtvSubject("NONE");
					
					atividades.add(atividade);
				}else if(tipoAtividade == 6){ //Subprocesso
					atividade.setProcessoEncadeado(true);
					NodeList nodeAtividades = dadosAtividade.getElementsByTagName("name");
					Node nodeAtividade = nodeAtividades.item(0);
					atividade.setAtvName(nodeAtividade.getTextContent());
					nodeAtividades = dadosAtividade.getElementsByTagName("implementation");

					Element nodeImplentacao = (Element) nodeAtividades.item(0);
					NodeList nodeEmbeddedProcess = nodeImplentacao.getElementsByTagName("embeddedProcessId");
					Node nodeEmbedded = nodeEmbeddedProcess.item(0);
					atividade.setNomeProcesso(nodeEmbedded.getTextContent());
					
					atividade.setAtvSubject("NONE");
					
					atividades.add(atividade);
				}else if(tipoAtividade == 7){//Processo de Evento
					atividade.setProcessoEncadeado(true);
					NodeList nodeAtividades = dadosAtividade.getElementsByTagName("name");
					Node nodeAtividade = nodeAtividades.item(0);
					atividade.setAtvName(nodeAtividade.getTextContent());
					nodeAtividades = dadosAtividade.getElementsByTagName("implementation");

					Element nodeImplentacao = (Element) nodeAtividades.item(0);
					NodeList nodeEventEmbedded = nodeImplentacao.getElementsByTagName("eventEmbeddedProcessId");
					Node nodeEvent = nodeEventEmbedded.item(0);
					atividade.setNomeProcesso(nodeEvent.getTextContent());
					
					atividade.setAtvSubject("NONE");
					
					atividades.add(atividade);
				}
			}

		}
		dadoRelatorio.setAtividades(atividades);

		return dadoRelatorio;

	}

	

}



package br.com.habber.servico;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.xml.sax.SAXException;

import br.com.habber.dominio.DadoRelatorio;

public class RelatorioExcel {
	public String getRelatorio(String caminhoArquivos) throws ParserConfigurationException, SAXException, IOException {
		Relatorio relatorio = new Relatorio();
		ArrayList<DadoRelatorio> arquivos = relatorio.trataDadosRelatorio(caminhoArquivos);
		HSSFWorkbook workbook = new HSSFWorkbook();

		FileOutputStream out = null;
		String caminhoRelatorio = "";
		caminhoRelatorio = caminhoArquivos.substring(0, caminhoArquivos.lastIndexOf("\\"))+"\\temp"+"\\relatorio.xls";
		out = new FileOutputStream(new File(caminhoRelatorio));
		for(int j=0;j<arquivos.size();j++){

			DadoRelatorio dadoRelatorio = arquivos.get(j);
			HSSFSheet sheet = null;

			sheet = workbook.createSheet(j+". "+dadoRelatorio.getBpdName());
			HSSFRow row = sheet.createRow(0);

			row.createCell(0).setCellValue("Processo");
			row.createCell(1).setCellValue("Nome Atividade");
			row.createCell(2).setCellValue("Assunto");
			row.createCell(3).setCellValue("Prazo");
			row.createCell(4).setCellValue("Subprocesso?");


			for(int i=0;i<dadoRelatorio.getAtividades().size();i++){
				row = sheet.createRow(i+1);
				row.createCell(0).setCellValue(dadoRelatorio.getBpdName());
				row.createCell(1).setCellValue(dadoRelatorio.getAtividades().get(i).getAtvName());
				row.createCell(2).setCellValue(dadoRelatorio.getAtividades().get(i).getAtvSubject());
				if(dadoRelatorio.getAtividades().get(i).isTipoDePrazoCustomizado()){
					row.createCell(3).setCellValue(dadoRelatorio.getAtividades().get(i).getVariavelDePrazo());
				}else{
					row.createCell(3).setCellValue(dadoRelatorio.getAtividades().get(i).getPrazo()+" "+dadoRelatorio.getAtividades().get(i).getTipoDePrazo());
				}
				if(dadoRelatorio.getAtividades().get(i).isProcessoEncadeado()){
					row.createCell(4).setCellValue(dadoRelatorio.getAtividades().get(i).isProcessoEncadeado());
				}
			}
		}
		workbook.write(out);

		return caminhoRelatorio;

	}
}

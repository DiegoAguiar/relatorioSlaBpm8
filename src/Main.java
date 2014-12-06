import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import br.com.habber.servico.RelatorioExcel;


public class Main {
	public static void main(String[] args) {
		RelatorioExcel manager = new RelatorioExcel();
		
		JFileChooser chooser = new JFileChooser();
		int returnVal = chooser.showOpenDialog(null);
		String caminhoRelatorio = "";
		if(returnVal == 0){
			try {
				caminhoRelatorio = manager.getRelatorio(chooser.getSelectedFile().getPath());
				JOptionPane.showMessageDialog(null,"Relatório disponível em: "+caminhoRelatorio);
			} catch (ParserConfigurationException e) {
				JOptionPane.showMessageDialog(null,"Erro "+ e.toString());
			} catch (SAXException e) {
				JOptionPane.showMessageDialog(null,"Erro "+ e.toString());
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,"Erro "+ e.toString());
			}
		}else{
			JOptionPane.showMessageDialog(null,"Cancelado");
		}
		
	 
	}
}

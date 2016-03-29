import javax.swing.*; // para o design do JFrame principal
import java.awt.*; // para fazer a GUI
import java.awt.event.*; // para lidar com eventos
import java.util.Scanner; // para ler de arquivos
import java.io.*; // para escrever em arquivos

public class Notepad extends JFrame implements ActionListener {
	private TextArea textArea = new TextArea("", 0,0,
			TextArea.SCROLLBARS_VERTICAL_ONLY);
	/*
	 * Aqui é a parte principal do aplicativo. O primeiro argumento do
	 * construtor é uma string vazia, que é o "texto inicial" que
	 * aparece quando se abre o programa. Se fosse passado "CES-22", ia
	 * abrir a tela com "CES-22". O 0,0 é o tamanho da área de texto. Não
	 * importa definir porque vai ficar na tela inteira. A flag final
	 * serve para fazer wrapping de palavras
	 * */
	
	private MenuBar menuBar = new MenuBar(); //barra de menus
	private Menu file = new Menu(); // menu "Arquivo"
	private MenuItem openFile = new MenuItem();  // opção abrir
	private MenuItem saveFile = new MenuItem(); // opção salvar
	private MenuItem close = new MenuItem(); // opção fechar

	@Override
	public void actionPerformed(ActionEvent e) {
		

	}

}

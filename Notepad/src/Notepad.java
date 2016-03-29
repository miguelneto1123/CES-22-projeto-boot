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
	
	private MenuBar menuBar = new MenuBar(); // barra de menus
	private Menu file = new Menu(); // menu "Arquivo"
	private MenuItem openFile = new MenuItem();  // opção abrir
	private MenuItem saveFile = new MenuItem(); // opção salvar
	private MenuItem close = new MenuItem(); // opção fechar
	
	public Notepad(){
		this.setSize(600, 800); // tamanho inicial da tela
		this.setTitle("Projeto Boot CES-22"); // título da tela
		setDefaultCloseOperation(EXIT_ON_CLOSE); // fecha a aplicação
		// quando clica em fechar
		this.textArea.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		// definindo a fonte default pro texto
		this.getContentPane().setLayout(new BorderLayout());
		// pro texto preencher a tela inteira do aplicativo
		this.getContentPane().add(textArea); // adiciona a área de texto
		// ao painel da aplicação
		
		this.setMenuBar(this.menuBar);
		this.menuBar.add(this.file); // criação da barra de menus
		
		this.file.setLabel("Arquivo");
		
		//--------- Botão de abrir arquivo
		this.openFile.setLabel("Abrir");
		this.openFile.addActionListener(this);
		// adiciona um ActionListener pra saber quando foi clicado nele
		this.openFile.setShortcut(new MenuShortcut(KeyEvent.VK_O, false));
		// atalho do teclado
		this.file.add(this.openFile);
		// adiciona à aba Arquivo
		
		//--------- Botão de salvar arquivo
		this.saveFile.setLabel("Salvar");
		this.saveFile.addActionListener(this);
		this.saveFile.setShortcut(new MenuShortcut(KeyEvent.VK_S, false));
		this.file.add(this.saveFile);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		

	}

}

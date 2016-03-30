import javax.swing.*; // para o design do JFrame principal
import java.awt.*; // para fazer a GUI
import java.awt.event.*; // para lidar com eventos
import java.util.Scanner; // para ler de arquivos
import java.io.*; // para escrever em arquivos

public class Notepad extends JFrame implements ActionListener {
	private TextArea textArea = new TextArea("", 0,0,
			TextArea.SCROLLBARS_VERTICAL_ONLY);
	/*
	 * Aqui √© a parte principal do aplicativo. O primeiro argumento do
	 * construtor √© uma string vazia, que √© o "texto inicial" que
	 * aparece quando se abre o programa. Se fosse passado "CES-22", ia
	 * abrir a tela com "CES-22". O 0,0 √© o tamanho da √°rea de texto. N√£o
	 * importa definir porque vai ficar na tela inteira. A flag final
	 * serve para fazer wrapping de palavras
	 * */
	
	private MenuBar menuBar = new MenuBar(); // barra de menus
	private Menu file = new Menu(); // menu "Arquivo"
	private MenuItem openFile = new MenuItem();  // op√ß√£o abrir
	private MenuItem saveFile = new MenuItem(); // op√ß√£o salvar
	private MenuItem close = new MenuItem(); // op√ß√£o fechar
	
	public Notepad(){
		this.setSize(600, 800); // tamanho inicial da tela
		this.setTitle("Projeto Boot CES-22"); // t√≠tulo da tela
		setDefaultCloseOperation(EXIT_ON_CLOSE); // fecha a aplica√ß√£o
		// quando clica em fechar
		this.textArea.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		// definindo a fonte default pro texto
		this.getContentPane().setLayout(new BorderLayout());
		// pro texto preencher a tela inteira do aplicativo
		this.getContentPane().add(textArea); // adiciona a √°rea de texto
		// ao painel da aplica√ß√£o
		
		this.setMenuBar(this.menuBar);
		this.menuBar.add(this.file); // cria√ß√£o da barra de menus
		
		this.file.setLabel("Arquivo");
		
		//--------- Bot√£o de abrir arquivo
		this.openFile.setLabel("Abrir");
		this.openFile.addActionListener(this);
		// adiciona um ActionListener pra saber quando foi clicado nele
		this.openFile.setShortcut(new MenuShortcut(KeyEvent.VK_O, false));
		// atalho do teclado
		this.file.add(this.openFile);
		// adiciona √† aba Arquivo
		
		//--------- Bot√£o de salvar arquivo
		this.saveFile.setLabel("Salvar");
		this.saveFile.addActionListener(this);
		this.saveFile.setShortcut(new MenuShortcut(KeyEvent.VK_S, false));
		this.file.add(this.saveFile);
		
		//--------- Bot√£o de fechar
		this.close.setLabel("Fechar");
		this.close.addActionListener(this);
		this.close.setShortcut(new MenuShortcut(KeyEvent.VK_F4,false));
		this.file.add(this.close);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// se for clicado no bot√£o "Fechar" no menu "Arquivo"...
		if (e.getSource() == this.close)
			this.dispose();	// se livra de tudo e fecha a aplica√ß√£o
		// se for clicado no bot„o "Open" no menu "Arquivo"...
		else if (e.getSource() == this.openFile) {
		    JFileChooser open = new JFileChooser(); // abra um selecionador de arquios
		    int option = open.showOpenDialog(this); // receba a opÁ„o que o usu·rio selecionou (approve ou cancel)
		    // NOTA: pq estamos abrindo um arquivo, nÛs chamamos showOpenDialog
		    // se o usu·rio clicou em OK, nÛs temos "APPROVE_OPTION"
		    // ent„o nÛs queremos abrir o arquivo
		    if (option == JFileChooser.APPROVE_OPTION) {
		    	this.textArea.setText(""); // limpar a ·rea de texto antes de aplicar o conte˙do do arquivo
		    	try {
		    	// criar um scanner para ler o arquivo (getSelectedFile().getPath() ir· pegar o path do arquivo)
		    	Scanner scan = new Scanner(new FileReader(open.getSelectedFile().getPath()));
		    	while (scan.hasNext()) // enquanto h· algo para ler
		    		this.textArea.append(scan.nextLine() + "\n"); // adicionar a linha ao TextArea
		    	} catch (Exception ex) { // pegar qualquer exceÁ„o e...
		    		// ...escrever para o debug console
		    	    System.out.println(ex.getMessage());
		    	}
		    }

		}
	}

}

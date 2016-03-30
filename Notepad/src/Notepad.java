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
		
		//--------- Botão de fechar
		this.close.setLabel("Fechar");
		this.close.addActionListener(this);
		this.close.setShortcut(new MenuShortcut(KeyEvent.VK_F4,false));
		this.file.add(this.close);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// se for clicado no botão "Fechar" no menu "Arquivo"...
		if (e.getSource() == this.close)
			this.dispose();	// se livra de tudo e fecha a aplicação
		// se for clicado no botão "Open" no menu "Arquivo"...
				else if (e.getSource() == this.openFile) {
				    JFileChooser open = new JFileChooser(); // abra um selecionador de arquios
				    int option = open.showOpenDialog(this); // receba a opção que o usuário selecionou (approve ou cancel)
				    // NOTA: pq estamos abrindo um arquivo, nós chamamos showOpenDialog
				    // se o usuário clicou em OK, nós temos "APPROVE_OPTION"
				    // então nós queremos abrir o arquivo
				    if (option == JFileChooser.APPROVE_OPTION) {
				    	this.textArea.setText(""); // limpar a área de texto antes de aplicar o conteúdo do arquivo
				    	try {
				    	// criar um scanner para ler o arquivo (getSelectedFile().getPath() irá pegar o path do arquivo)
				    	Scanner scan = new Scanner(new FileReader(open.getSelectedFile().getPath()));
				    	while (scan.hasNext()) // enquanto há algo para ler
				    		this.textArea.append(scan.nextLine() + "\n"); // adicionar a linha ao TextArea
				    	} catch (Exception ex) { // pegar qualquer exceção e...
				    		// ...escreve no debug console
				    	    System.out.println(ex.getMessage());
				    	}
				    }

				}
				
				// e por último, se a fonte do evento foi a opção "Save"
				else if (e.getSource() == this.saveFile) {
					JFileChooser save = new JFileChooser(); // novamente, abra um file chooser
				    int option = save.showSaveDialog(this); // semelhante ao open file, só que dessa vez chamamos
				    // showSaveDialog
				    // se o usuário clicou em OK
				    if (option == JFileChooser.APPROVE_OPTION) {
				    	try {
				    		// cria um buffered writer para escrever num arquivo
				            BufferedWriter out = new BufferedWriter(new FileWriter(save.getSelectedFile().getPath()));
				            out.write(this.textArea.getText()); // escreve o conteúdo do TextAreawrite no arquivo
				            out.close(); // fecha o file stream
				        } catch (Exception ex) { // novamente, pega qualquer exceção e
				        	// ...e escreve no debug console
				            System.out.println(ex.getMessage());
				        }
				        }
				    }
				
			}
			
			// o método main para criar o notepad e torná-lo visível
			    public static void main(String args[]) {
			        Notepad app = new Notepad();
			        app.setVisible(true);
			    }
	}

}

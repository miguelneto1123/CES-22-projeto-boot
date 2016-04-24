import javax.swing.*; // para o design do JFrame principal
import java.awt.*; // para fazer a GUI
import java.awt.event.*; // para lidar com eventos
import java.util.*; // para ler de arquivos e pegar o timestamp atual
import java.text.*; // para formatar as datas
import java.io.*; // para escrever em arquivos
import java.sql.*;

public class Notepad{
	//private TextArea textArea = new TextArea("", 0,0,
		//	TextArea.SCROLLBARS_VERTICAL_ONLY);
	/*
	 * Aqui é a parte principal do aplicativo. O primeiro argumento do
	 * construtor é uma string vazia, que é o "texto inicial" que
	 * aparece quando se abre o programa. Se fosse passado "CES-22", ia
	 * abrir a tela com "CES-22". O 0,0 é o tamanho da área de texto. Não
	 * importa definir porque vai ficar na tela inteira. A flag final
	 * serve para fazer wrapping de palavras
	 * */
	
	/*private MenuBar menuBar = new MenuBar(); // barra de menus
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
		// se for clicado no bot�o "Open" no menu "Arquivo"...
		else if (e.getSource() == this.openFile) {
		    JFileChooser open = new JFileChooser(); // abra um selecionador de arquios
		    int option = open.showOpenDialog(this); // receba a op��o que o usu�rio selecionou (approve ou cancel)
		    // NOTA: pq estamos abrindo um arquivo, n�s chamamos showOpenDialog
		    // se o usu�rio clicou em OK, n�s temos "APPROVE_OPTION"
		    // ent�o n�s queremos abrir o arquivo
		    if (option == JFileChooser.APPROVE_OPTION) {
		    	this.textArea.setText(""); // limpar a �rea de texto antes de aplicar o conte�do do arquivo
		    	try {
		    	// criar um scanner para ler o arquivo (getSelectedFile().getPath() ir� pegar o path do arquivo)
		    	Scanner scan = new Scanner(new FileReader(open.getSelectedFile().getPath()));
		    	while (scan.hasNext()) // enquanto h� algo para ler
		    		this.textArea.append(scan.nextLine() + "\n"); // adicionar a linha ao TextArea
		    	} catch (Exception ex) { // pegar qualquer exce��o e...
		    		// ...escreve no debug console
		    	    System.out.println(ex.getMessage());
		    	}
		    }

		}
		
		// e por �ltimo, se a fonte do evento foi a op��o "Save"
		else if (e.getSource() == this.saveFile) {
			JFileChooser save = new JFileChooser(); // novamente, abra um file chooser
		    int option = save.showSaveDialog(this); // semelhante ao open file, s� que dessa vez chamamos
		    // showSaveDialog
		    // se o usu�rio clicou em OK
		    if (option == JFileChooser.APPROVE_OPTION) {
		    	try {
		    		// cria um buffered writer para escrever num arquivo
		            BufferedWriter out = new BufferedWriter(new FileWriter(save.getSelectedFile().getPath()));
		            out.write(this.textArea.getText()); // escreve o conte�do do TextAreawrite no arquivo
		            out.close(); // fecha o file stream
		        } catch (Exception ex) { // novamente, pega qualquer exce��o e
		        	// ...e escreve no debug console
		            System.out.println(ex.getMessage());
		        }
		        }
		    }
		
	}
	
	// o m�todo main para criar o notepad e torn�-lo vis�vel
	    public static void main(String args[]) {
	        Notepad app = new Notepad();
	        app.setVisible(true);
	    }*/
	private Connection conn;
	
	public void createTable(String filePath){
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+filePath);
			Statement st = conn.createStatement();
			st.setQueryTimeout(30);
			
			st.executeUpdate("DROP TABLE IF EXISTS base");
			st.executeUpdate("CREATE TABLE base "+
			"(name STRING, text STRING, taglist STRING, modified "
			+ "TIMESTAMP DEFAULT (datetime('now','localtime')), "
			+ "created TIMESTAMP DEFAULT (datetime('now','localtime')))");
		} catch (SQLException s) {
			System.err.println(s.getMessage()+" hahaha");
		}
	}
	
	public void readTable(String filePath) {
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+filePath);
			Statement st = conn.createStatement();
			st.setQueryTimeout(30);
			ResultSet rs = st.executeQuery("SELECT * from base");
			
			while (rs.next()) {
				System.out.println("name = "+rs.getString("name"));
				System.out.println("text = "+rs.getString("text"));
				System.out.println("tags = "+rs.getString("taglist"));
				System.out.println("modified = "+rs.getString(
					"modified"));
				System.out.println("created = "+rs.getString("created"));
				System.out.println("");
			}
		} catch (SQLException s) {
			System.err.println(s.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addNote(String name, String text, String tags) {
		if (conn != null) {
			try {
				Statement st = conn.createStatement();
				st.executeUpdate("INSERT INTO base(name,text,taglist)"
					+ " values('"+name+"', '"
					+text+"', '"+tags+"')");
			} catch (SQLException s) {
				System.err.println(s.getMessage());
			}
		}
	}
	
	public void editNote(String name, String text, String tags,
			String oldName) {
		if (conn != null){
			try {
				Statement st = conn.createStatement();
				
				st.executeUpdate("UPDATE base "
					+ "SET name='"+name+"', text='"+text+"', "
					+ "taglist='"+tags+"', modified="
					+ "datetime('now','localtime') "
					+ "WHERE name='"+oldName+"'");
			} catch (SQLException s) {
				System.err.println(s.getMessage());
			}
		}
	}
	
	public static void main(String args[]){
		Notepad n = new Notepad();
		n.createTable("test.db");
		n.addNote("huehuehue", "soh testando", "teste, nota");
		n.addNote("hahaha", "soh testando", "teste, nota");
		n.addNote("hihihi", "soh testando", "teste, nota");
		n.editNote("hehehe", "mudei aqui", "updated", "hihihi");
		n.readTable("test.db");
	}
}

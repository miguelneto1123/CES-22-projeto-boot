import javax.swing.*; // para o design do JFrame principal
import java.awt.*; // para fazer a GUI
import java.awt.event.*; // para lidar com eventos
import java.util.*; // para ler de arquivos e pegar o timestamp atual
import java.text.*; // para formatar as datas
import java.io.*; // para escrever em arquivos
import java.sql.*;

public class Notepad extends JFrame implements ActionListener,
ItemListener {
		
	private MenuBar menuBar = new MenuBar(); // barra de menus
	private Menu file = new Menu(); // menu "Arquivo"
	private MenuItem newFile = new MenuItem();  // opção novo
	private MenuItem openFile = new MenuItem(); // opção abrir
	private MenuItem close = new MenuItem(); // opção fechar
	private Menu note = new Menu(); // menu "Notas"
	private MenuItem newNote = new MenuItem(); // opção nova nota
	private MenuItem editNote = new MenuItem(); // opção editar nota
	private MenuItem deleteNote = new MenuItem(); // opção deletar nota
	private MenuItem sortNotes = new MenuItem(); // opção ordenar notas
	
	private Connection conn; // responsável pela conexão com a database
	
	private ArrayList<Selector> als = new ArrayList<Selector>();
	private String toBeEdited = "";
	
	private ArrayList<JPanel> alp = new ArrayList<JPanel>();
	
	public Notepad(){
		this.setSize(600, 700); // tamanho inicial da tela
		this.setTitle("Projeto Boot CES-22"); // título da tela
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); // fecha a aplicação
		// quando clica em fechar
		this.getContentPane().setLayout(new BoxLayout(
				this.getContentPane(), BoxLayout.Y_AXIS));
		
		this.setMenuBar(this.menuBar);
		this.menuBar.add(this.file);
		this.menuBar.add(this.note); // criação da barra de menus
		
		this.file.setLabel("Arquivo");
		
		//--------- Botão de abrir arquivo
		this.openFile.setLabel("Abrir");
		this.openFile.addActionListener(this);
		// adiciona um ActionListener pra saber quando foi clicado nele
		this.openFile.setShortcut(new MenuShortcut(KeyEvent.VK_O, false));
		// atalho do teclado
		this.file.add(this.openFile);
		// adiciona à aba Arquivo
		
		//--------- Botão de novo arquivo
		this.newFile.setLabel("Novo");
		this.newFile.addActionListener(this);
		this.newFile.setShortcut(new MenuShortcut(KeyEvent.VK_N, false));
		this.file.add(this.newFile);
		
		//--------- Botão de fechar
		this.close.setLabel("Fechar");
		this.close.addActionListener(this);
		this.close.setShortcut(new MenuShortcut(KeyEvent.VK_F4,false));
		this.file.add(this.close);
		
		this.note.setLabel("Notas");
		
		//--------- Botão de nova nota
		this.newNote.setLabel("Nova nota");
		this.newNote.addActionListener(this);
		this.newNote.setShortcut(new MenuShortcut(KeyEvent.VK_EQUALS,
				false));
		this.note.add(this.newNote);
		
		//--------- Botão de editar nota
		this.editNote.setLabel("Editar nota");
		this.editNote.addActionListener(this);
		if (this.als.size() != 1)
			this.editNote.setEnabled(false);
		else
			this.editNote.setEnabled(true);
		this.editNote.setShortcut(new MenuShortcut(KeyEvent.VK_MINUS,
				false));
		this.note.add(this.editNote);
		
		//--------- Botão de deletar nota
		this.deleteNote.setLabel("Deletar nota");
		this.deleteNote.addActionListener(this);
		this.deleteNote.setShortcut(new MenuShortcut(KeyEvent.VK_0,
				false));
		this.note.add(this.deleteNote);
		
		//--------- Botão de ordenar notas
		this.sortNotes.setLabel("Ordenar");
		this.sortNotes.addActionListener(this);
		this.sortNotes.setShortcut(new MenuShortcut(KeyEvent.VK_9,
				false));
		this.note.add(this.sortNotes);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// se foi clicado no botão "Fechar" no menu "Arquivo"...
		if (e.getSource() == this.close)
			this.dispose();	// se livra de tudo e fecha a aplicação
		
		// se foi clicado no botão "Abrir" no menu "Arquivo"...
		else if (e.getSource() == this.openFile) {
			// abra um selecionador de arquivos
		    JFileChooser open = new JFileChooser();
		    // receba a opção que o usuário selecionou (approve ou cancel)
		    int option = open.showOpenDialog(this);
		    // NOTA: estamos abrindo um arquivo, chamamos showOpenDialog
		    // se o usuário clicou em OK, nás temos "APPROVE_OPTION"
		    // então nós queremos abrir o arquivo
		    if (option == JFileChooser.APPROVE_OPTION) {
		    	readTable(open.getSelectedFile().getPath());
		    	for (int i = 0; i < alp.size(); i++)
		    		this.getContentPane().add(alp.get(i));
		    	alp.clear();
		    	//this.setVisible(false);
		    	//this.setVisible(true);
		    	SwingUtilities.updateComponentTreeUI(this);
		    }
		}
		
		// se foi clicado no botão "Novo" no menu "Arquivo"...
		else if (e.getSource() == this.newFile) {
			// novamente, abra um file chooser
			JFileChooser save = new JFileChooser();
			// semelhante ao open file, só que dessa vez chamamos
		    // showSaveDialog
		    int option = save.showSaveDialog(this);
		    // se o usuário clicou em OK
		    if (option == JFileChooser.APPROVE_OPTION) {
		    	createTable(save.getSelectedFile().getPath());
		    }
		}
		
		// se foi clicado no botão "Nova nota" no menu "Notas"...
		else if (e.getSource() == this.newNote){
			
		}
		
		// se foi clicado no botão "Editar nota" no menu "Notas"...
		else if (e.getSource() == this.editNote){
			
		}
		
		// se foi clicado no botão "Deletar nota" no menu "Notas"...
		else if (e.getSource() == this.deleteNote){
			
		}

		// se foi clicado no botão "Ordenar notas" no menu "Notas"...
		else if (e.getSource() == this.sortNotes){
			
		}
	}
	
	@Override
	public void itemStateChanged(ItemEvent ev){
		Selector s = (Selector) ev.getSource();
		if(s.isSelected()){
			this.als.add(s);
			this.toBeEdited = s.getAssociatedName();
		}
		else {
			this.als.remove(s);
			this.toBeEdited = "";
		}
		if (this.als.size() != 1)
			this.editNote.setEnabled(false);
		else
			this.editNote.setEnabled(true);
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	// o método main para criar o Notepad e torná-lo visível
	    public static void main(String args[]) {
	        Notepad app = new Notepad();
	        app.setVisible(true);
	    }
	
	public void createTable(String filePath){
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+filePath);
			Statement st = conn.createStatement();
			
			st.executeUpdate("DROP TABLE IF EXISTS base");
			st.executeUpdate("CREATE TABLE base "+
			"(name STRING, text STRING, taglist STRING, modified "
			+ "TIMESTAMP DEFAULT (datetime('now','localtime')), "
			+ "created TIMESTAMP DEFAULT (datetime('now','localtime')))");
		} catch (SQLException s) {
			System.err.println(s.getMessage()+" hahaha");
		}
	}
	
	public void readTable(String filePath, boolean sorted,
			String sortedBy) {
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+filePath);
			Statement st = conn.createStatement();
			st.setQueryTimeout(30);
			String sql = "SELECT * from base";
			if (sorted) {
				sql = sql + " ORDER BY " + sortedBy + " ASC";
			}
			ResultSet rs = st.executeQuery(sql);
			
			while (rs.next()) {
				/*System.out.println("name = "+rs.getString("name"));
				System.out.println("text = "+rs.getString("text"));
				System.out.println("tags = "+rs.getString("taglist"));
				System.out.println("modified = "+rs.getString(
					"modified"));
				System.out.println("created = "+rs.getString("created"));
				System.out.println("");*/
				JPanel panel = new JPanel();
				
				JLabel name = new JLabel(rs.getString("name"));
				name.setFont(new Font("serif", Font.BOLD,18));
				String s = "Criada: " + rs.getString("created");
				s = s+", Modificada: "+rs.getString("modified");
				JLabel timestamps = new JLabel(s);
				timestamps.setFont(new Font("lucida", Font.PLAIN,10));
				JTextArea text = new JTextArea(rs.getString("text"),
						4, 70);
				text.setLineWrap(true);
				JScrollPane scroller = new JScrollPane(text);
				scroller.setVerticalScrollBarPolicy(
					ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				scroller.setHorizontalScrollBarPolicy(
					ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				panel.add(name);
				this.getContentPane().add(timestamps);
				panel.add(timestamps);
				panel.add(scroller);
				
				s = "Tags: "+rs.getString("taglist");
				panel.add(new JLabel(s));
				Selector sel = new Selector("Selecionado",
						rs.getString("name"));
				sel.addItemListener(this);
				panel.add(sel);
				
				this.alp.add(panel);
			}
		} catch (SQLException s) {
			System.err.println(s.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void readTable(String filePath) {
		readTable(filePath, false, "");
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
	
	/*public static void main(String args[]){
		Notepad n = new Notepad();
		n.createTable("test.db");
		n.addNote("huehuehue", "só testando", "teste, nota");
		n.addNote("hahaha", "só testando", "teste, nota");
		n.addNote("hihihi", "só testando", "teste, nota");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException i) {
			i.printStackTrace();
		}
		n.editNote("hehehe", "mudei aqui", "updated", "hihihi");
		n.readTable("test.db", true, "name");
		n.readTable("test.db");
	}*/
}

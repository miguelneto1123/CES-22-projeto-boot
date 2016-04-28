import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class PromptDialog extends JDialog implements ActionListener {
	private JTextField nameBox;
	private JTextArea textBox;
	private JTextField tagBox;
	private JButton ok;
	private String content;
	private int delete = 0;
	private String selection;
	
	public PromptDialog(String name, String text, String tags,
			String toDo, int numNotes, JFrame parent) {
		super(parent, true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(new Dimension(parent.getWidth() / 2,
				parent.getHeight() / 2));
		String title = toDo + " nota";
		if (numNotes > 1) {
			title += "s";
		}
		this.setTitle(title);
		
		if (toDo.equals("Deletar")) {
			delete = JOptionPane.showConfirmDialog(parent,
					"Essa ação é irreversível!",
					"Tem certeza?",
					JOptionPane.YES_NO_OPTION);
		}
		
		else if (toDo.equals("Ordenar")) {
			Object[] possibilities = {"name",
				"text",
				"created",
				"modified"
			};
			selection = (String)JOptionPane.showInputDialog(parent,
					"Escolha um dos valores abaixo:",
					"Ordenar por...",
					JOptionPane.PLAIN_MESSAGE,
					null, possibilities, "name");
		}
		else if (toDo.equals("Adicionar") || toDo.equals("Editar")) {
			this.getContentPane().setLayout(
					new BorderLayout());
			
			JPanel panel = new JPanel();
			
			nameBox = new JTextField(name, 20);
			textBox = new JTextArea(text,10, 20);
			textBox.setLineWrap(true);
			tagBox = new JTextField(tags, 20);
			
			JScrollPane scroller = new JScrollPane(textBox);
			
			scroller.setVerticalScrollBarPolicy(
					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scroller.setHorizontalScrollBarPolicy(
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			
			panel.add(new JLabel("Nome"));
			panel.add(nameBox);
			panel.add(new JLabel("Texto"));
			panel.add(scroller);
			panel.add(new JLabel("Tags separadas por ,"));
			panel.add(tagBox);
			
			ok = new JButton("Salvar");
			ok.addActionListener(this);
			
			this.getContentPane().add(panel, BorderLayout.CENTER);
			this.getContentPane().add(ok, BorderLayout.SOUTH);
		}
		if (!toDo.equals("Deletar") && !toDo.equals("Ordenar")){
			setVisible(true);
		}
	}
	
	public boolean getValue() {
		if (delete == 0)
			return true;
		return false;
	}
	
	public String getSelection() {
		return selection;
	}
	
	public String getContent() {
		return content;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		content = nameBox.getText()+"~"
				+textBox.getText()+"~"
				+tagBox.getText();
	}
}

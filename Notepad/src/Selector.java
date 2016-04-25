import javax.swing.JCheckBox;

public class Selector extends JCheckBox {
	private String associatedName;
	
	public Selector (String label, String associatedName) {
		super(label);
		this.associatedName = associatedName;
	}
	
	public String getAssociatedName(){
		return this.associatedName;
	}
}
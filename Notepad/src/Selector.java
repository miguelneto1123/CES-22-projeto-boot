import javax.swing.JCheckBox;

public class Selector extends JCheckBox {
	private String associatedName;
	private String associatedText;
	private String associatedTags;
	
	public Selector (String label, String associatedName,
			String text, String tags) {
		super(label);
		this.associatedName = associatedName;
		associatedText = text;
		associatedTags = tags;
	}
	
	public String getAssociatedName(){
		return this.associatedName;
	}
	
	public String getAssociatedText(){
		return this.associatedText;
	}
	
	public String getAssociatedTags(){
		return this.associatedTags;
	}
}
package aoa;

/**
 * 
 * @author Amit 
 * Hold the fields for a rule
 */
public class Rule {

	private String name;
	private int priority;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public String toString(){
		return "[ "+this.name + " - " + this.priority+" ]";
		
	}

}

package nfl;

import javax.swing.JCheckBox;

public class Team extends JCheckBox {
	
	private String name;

	private Team(String name){
		super(name, false);
		this.name = name;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3655110858695322864L;

	public static Team createTeam(String s) {
		Team t = new Team(s);
		
		return t;
	}

	public String getTeamName() {
		return name;
	}

}

package nfl;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Division extends JPanel {
	
	private List<Team> teams;

	private Division(List<Team> teams){
		this.teams = teams;
		
		setLayout(new GridLayout(5,1));
		
		for (Team t : teams){
			add(t);
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5748514219610120668L;

	public static Division createDivisionCheckbox(List<String> div) {
		
		//d.setLayout(new GridLayout(5,1));
		
		List<Team> teams = new ArrayList<>();
		
		for (String s : div){
			Team t = Team.createTeam(s);
			teams.add(t);
		}
		
		Division d = new Division(teams);
		
		return d;
	}

	public void getCheckedTeam(List<String> list) {
		
		for (Team t : teams){
			if (t.isSelected()){
				list.add(t.getTeamName());
			}
		}
	}

	public void getCheckedTeams(List<String> list) {
		for (Team t : teams){
			if (t.isSelected()){
				list.add(t.getTeamName());
			}
		}
		
	}

	

}

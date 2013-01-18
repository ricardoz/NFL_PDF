package nfl;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Conference extends JPanel {
	
	private List<Division> divisions;

	private Conference(List<Division> divisionList){
		this.divisions = divisionList;
		
		setLayout(new GridLayout(2,2));
		
		for (Division d : divisions){
			add(d);
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1345627747158418955L;

	public static Conference createConferenceComponent(List<String> conf) {
		//Conference c = new Conference();
		//c.setLayout(new GridLayout(2,2));
		
		List<Division> divisionList = new ArrayList<>(4);
		
		// create divisions
		int start = 0;
		int end = 4;
		
		while (start < 16){
			Division d = Division.createDivisionCheckbox(conf.subList(start, end));
			
			divisionList.add(d);
			
			start +=4; end +=4;
		}
		
		Conference c = new Conference(divisionList);
		
		return c;
		
	}

	public void getCheckedTeams(List<String> list) {
		
		for (Division d : divisions){
			d.getCheckedTeams(list);
		}
	}

}

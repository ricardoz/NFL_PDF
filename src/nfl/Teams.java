package nfl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Teams {
	private static final String[] nflTeamsAsArray = {
			"Bills","Dolphins","Patriots","Jets",
			"Ravens","Bengals","Browns","Steelers",
			"Texans","Colts","Jaguars","Titans",
			"Broncos","Chiefs","Raiders","Chargers",
			"Cowboys","Giants","Eagles","Redskins",
			"Bears","Lions","Packers","Vikings",
			"Falcons","Panthers","Saints","Buccaneers"
			,"Cardinals","Rams","49ers","Seahawks"};
	
	private Teams(){}
	
	public static List<String> getTeams(){
		List<String> teams = Arrays.asList(nflTeamsAsArray);
		
		return Collections.unmodifiableList(teams);
	}
}

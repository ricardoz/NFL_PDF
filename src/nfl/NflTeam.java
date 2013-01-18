package nfl;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum NflTeam {
	
	Buffalo_Bills("Bills"), Miami_Dolphins("Dolphins"), New_England_Patriots("Patriots"), New_York_Jets("Jets"),

	Baltimore_Ravens("Ravens"), Cincinnati_Bengals("Bengals"), Cleveland_Browns("Browns"), Pittsburgh_Steelers("Steelers"),

	Houston_Texans("Texans"), Indianapolis_Colts("Colts"), Jacksonville_Jaguars("Jaguars"), Tennessee_Titans("Titans"),

	Denver_Broncos("Broncos"), Kansas_City_Chiefs("Chiefs"), Oakland_Raiders("Raiders"), San_Diego_Chargers("Chargers"),

	

	Dallas_Cowboys("Cowboys"), New_York_Giants("Giants"), Philadelphia_Eagles("Eagles") , Washington_Redskins("Redskins"),

	Chicago_Bears("Bears"), Detroit_Lions("Lions"), Green_Bay_Packers("Packers") , Minnesota_Vikings("Vikings"),

	Atlanta_Falcons("Falcons"), Carolina_Panthers("Panthers"), New_Orleans_Saints("Saints") , Tampa_Bay_Buccaneers("Buccaneers"),

	Arizona_Cardinals("Cardinals"), St_Louis_Rams("Rams"), San_Francisco_49ers("49ers"), Seattle_Seahawks("Seahawks");
	
	private String nickname;
	
	NflTeam(String name){
		
		this.nickname = name;
	}
	
	public String getNickname(){
		return nickname;
	}
	
	private static final Map<String, NflTeam> lookup 
    = new HashMap<>();
	
	static {
        for(NflTeam s : EnumSet.allOf(NflTeam.class))
             lookup.put(s.getNickname().toLowerCase(), s);
   }
	
	public static NflTeam getTeam(String nick) { 
        return lookup.get(nick); 
   }
}

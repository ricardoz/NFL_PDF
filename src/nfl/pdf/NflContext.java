package nfl.pdf;

import nfl.MainNflPdf;
import nfl.NflTeam;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public enum NflContext {
	
	

	NFL{
		@Override
		public String getSchedulePageAddress(NflTeam team) {
			
			return null;
		}
		
		@Override
		public MatchReportLinksStrategy getMatchReportLinksStrategy(HtmlPage page, NflTeam team){
			return MatchReportLinkStrategyForNfl.createStrategy(page, team);
		}
	}, SI{
		@Override
		public String getSchedulePageAddress(NflTeam team) {
			
			return "http://sportsillustrated.cnn.com/football/nfl/schedules/" + MainNflPdf.year +
					"/" + team.name().toLowerCase().replaceAll("_", "-") +"/index.html";
			
		}
		
		@Override
		public MatchReportLinksStrategy getMatchReportLinksStrategy(HtmlPage page, NflTeam team){
			return MatchReportLinkStrategyForSi.createStrategy(page, team);
		}
	};

	
	
	public abstract String getSchedulePageAddress(NflTeam team);

	public  abstract MatchReportLinksStrategy getMatchReportLinksStrategy(HtmlPage page, NflTeam team);
}

package nfl.pdf;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import nfl.NflTeam;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TeamArticleData {

	public static TeamArticleData createArticleData(HtmlPage p, WebClient webClient) throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException {
		
		

		//System.out.println(p.asXml());
		//System.out.println(p.asText());
		
		/*
		 * this will get us the links for each new page
		 * pass to match report factory
		 */
		DomNodeList<DomNode> foo = p.querySelectorAll("div.list-matchup-row-gc-link");
		
		List<MatchReportImplNfl> matchReportList = new ArrayList<>(17);

		for (DomNode dn : foo){
			//System.out.println(dn.asXml());
			matchReportList.add(MatchReportImplNfl.createMatchReport(dn, webClient));
		}


		/*
		 * 14 items for away / 13 for home
		 * check (4) if away then remove from list
		 * 0-week
		 * 2/3-date
		 * 5/6 HT & score 7/8 AT & score
		 * 
		 */
		DomNode foo2 = p.querySelector("ul.schedules-table");
		String[] text = foo2.asText().split("\\s+");
		System.out.println(":--" + text.length +"--");

		for (int i = 0; i < text.length; i++) {
			if (text[i].equalsIgnoreCase("at")) {
				System.out.println(i + ":--" + text[i] + "--");
			}
		}



		return null;
		
		
	}

	public static TeamArticleData createArticleData(HtmlPage page,
			NflTeam team, NflPdfImplString nflPdfImplString) {
		// TAD will want a team 
		// MR will need context/strategy and webclient
		
		/*
		 * Summary
		 */
		return null;
	}

}

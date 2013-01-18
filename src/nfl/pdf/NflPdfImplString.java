package nfl.pdf;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import nfl.NflTeam;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.RefreshHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.itextpdf.text.DocumentException;

public class NflPdfImplString implements NflPdf{

	private List<NflTeam> teams;
	private WebClient webClient;
	private NflContext context;

	public NflPdfImplString(WebClient webClient, List<NflTeam> teams, NflContext context) {
		this.webClient = webClient;
		this.teams = teams;
		this.context = context;
	}
	
	private HtmlPage getTeamPage(NflTeam team) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		HtmlPage page = (HtmlPage) webClient.getPage(context.getSchedulePageAddress(team));

		return page;
	}

	/**
	 * @param args
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws FailingHttpStatusCodeException 
	 * @throws InterruptedException 
	 * @throws DocumentException 
	 */
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException, DocumentException {
		List<String> teamNames = new ArrayList<>();
		List<NflTeam> teams = new ArrayList<>();
		NflTeam bills = NflTeam.Buffalo_Bills;
		
		teams.add(bills);
		
		teamNames.add("Bills");
		
		NflContext si = NflContext.SI;
		
		NflPdfImplString pdf = createPdfFromList(teams, si);
		
		
		List<MatchReportLinksStrategy> articles = pdf.getTeamArticles();
		
		StringBuilder builder = new StringBuilder("nfl 2011 results");
		
		for (NflTeam team : teams){
			builder.append(" " + team.getNickname());
		}
		
		builder.append(".pdf");
		
		String filename = builder.toString();
        
        NflPdfFactory.createPdf(articles, filename).run();
		
		System.out.print(articles);
		
		// pass this to factory
		
		
		//find results
	

	}

	

	public List<MatchReportLinksStrategy> getTeamArticles() throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException {
		List<MatchReportLinksStrategy> list = new ArrayList<>(teams.size());
		for (NflTeam team : teams){
			HtmlPage page = getTeamPage(team);
			
			// get match report links
			MatchReportLinksStrategy matchReportLinksStrategy = context.getMatchReportLinksStrategy(page, team);
			list.add(matchReportLinksStrategy);
		}
		return list;
	}

	public static NflPdfImplString createPdfFromList(List<NflTeam> teams, NflContext si) {
		
		// get pages from nfl site
		BrowserVersion bv = BrowserVersion.FIREFOX_3_6;
		bv.getPlugins().clear() ;
		
		WebClient webClient = new WebClient(bv);
		webClient.setThrowExceptionOnScriptError(false);
		webClient.setJavaScriptEnabled(false);
		webClient.setJavaScriptErrorListener(null);
		
		webClient.setRefreshHandler(new RefreshHandler() {
			public void handleRefresh(Page page, URL url, int arg) throws IOException {
				System.out.println("page " + page + "\nurl " + url + " " + arg);
			}

		});
		
		NflPdfImplString nflPdfImplString = new NflPdfImplString(webClient, teams, si);
		
		return nflPdfImplString;
		
	}

	public static NflPdfImplString createPdfFromList(List<NflTeam> teams) {
		return createPdfFromList(teams, NflContext.SI);
		
	}

}

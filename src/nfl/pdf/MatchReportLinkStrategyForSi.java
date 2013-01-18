package nfl.pdf;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nfl.NflTeam;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

public class MatchReportLinkStrategyForSi extends MatchReportLinksStrategy {

	private List<HtmlTable> table;
	private List<DomNode> recapDomNodes;
	protected NflTeam team;

	private MatchReportLinkStrategyForSi(List<HtmlTable> table, List<DomNode> recapDomNodes, NflTeam team) {
		this.table = table;
		this.recapDomNodes = recapDomNodes;
		this.team = team;
	}

	

	public static MatchReportLinksStrategy createStrategy(HtmlPage page, NflTeam team){
		
		// search for tables
	    DomNodeList<DomNode> allTables = page.querySelectorAll("table");
	    
	    boolean found = Boolean.FALSE;
	    
	    //Iterator<DomNode> iter = allTables.iterator();
	    
	    HtmlTable table = null;
	    
	    List<HtmlTable> tablesList = new ArrayList<>();
	    
	    //while (iter.hasNext() && notFound){
	    	
	    	for (DomNode dn : allTables){
	    		table = (HtmlTable) dn;
	    		System.out.println("table: " + table.getRowCount());
	    		if (table.getRowCount() > 10) found = Boolean.TRUE;
	    		
	    		if (found){
	    			tablesList.add(table);
	    		}
	    	}
	    //}
	    
	    List<DomNode> recapDomNodes = new ArrayList<DomNode>(20);
	    
	    for (HtmlTable t : tablesList) {
			// for each row build summary
			for (HtmlTableRow row : t.getRows()) {
				try {
					HtmlAnchor link = (HtmlAnchor) row.getCell(8)
							.getChildNodes().get(0);
					HtmlPage newPage = page.getWebClient().getPage(
							"http://sportsillustrated.cnn.com"
									+ link.getHrefAttribute());
					System.out.println("url: " + link.getHrefAttribute());
					//System.out.println("recap: " + newPage.querySelector("div#story-recap").asText());
					recapDomNodes.add(newPage.querySelector("div#story-recap"));

				} catch (IndexOutOfBoundsException
						| FailingHttpStatusCodeException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
			return new MatchReportLinkStrategyForSi(tablesList, recapDomNodes, team);
		
	}

	

	@Override
	public PdfStrategy getPdfStrategy() {
		// TODO Auto-generated method stub
		return PdfStrategyForSi.createPdfStrategyForSi(this);
	}

	/**
	 * @return the table
	 */
	public List<HtmlTable> getTable() {
		return table;
	}

	/**
	 * @return the recapDomNodes
	 */
	public List<DomNode> getRecapDomNodes() {
		return recapDomNodes;
	}



	public NflTeam getTeam() {
		// TODO Auto-generated method stub
		return team;
	}

	

	

}

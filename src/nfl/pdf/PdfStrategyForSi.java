package nfl.pdf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nfl.NflTeam;

import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;

public class PdfStrategyForSi implements PdfStrategy {

	
	
	/** A font that will be used in our PDF. */
    protected static final Font BOLD_UNDERLINED =
        new Font(FontFamily.TIMES_ROMAN, 8, Font.BOLD | Font.UNDERLINE);
    /** A font that will be used in our PDF. */
    protected static final Font NORMAL =
        new Font(FontFamily.TIMES_ROMAN, 8);
    /** A font that will be used in our PDF. */
    protected static final Font ANCHOR  =
        new Font(FontFamily.TIMES_ROMAN, 8, Font.BOLD, BaseColor.BLUE);
    
	private NflTeam team;
	private PdfPTable summary;
	private Collection<Element> elements;

	private PdfStrategyForSi(PdfPTable summary, Collection<Element> elements, NflTeam team2) {
		this.summary = summary;
		this.elements = elements;
		this.team = team2;
	}
	
	public static PdfStrategy createPdfStrategyForSi( MatchReportLinkStrategyForSi mrls ){
		// create match reports
		Collection<AnchoredElement> mr = createAllMatchReports(mrls);
		Collection<Element> elements = new ArrayList<>(mr.size());
		
		for (AnchoredElement ae : mr){
			elements.add(ae.getElement());
		}
		
		PdfPTable summary = createSeasonSummary(mrls);
		
		NflTeam team = mrls.getTeam();
		
		return new PdfStrategyForSi(summary, elements, team);
		
	}

	
	private static PdfPTable createSeasonSummary( MatchReportLinkStrategyForSi mrls ) {
		
		
		List<HtmlTableRow> rows = new ArrayList<>(24);
		
		HtmlTableRow firstRow = null;
		
		for (HtmlTable t : mrls.getTable()){
			ArrayList<HtmlTableRow> newTableRows = new ArrayList<>(t.getRows());
			
			if (firstRow == null){
				firstRow = newTableRows.remove(0);
			} else {
				newTableRows.remove(0);
			}
			
			for (HtmlTableRow r : newTableRows){
				rows.add(r);
			}
			
		}
				
				
		
		//System.out.println("season all summarry: ");
		//System.out.println("season all summarry2: ");
		//System.out.println("season all summarry3: ");
		//System.out.println("season all summarry4: ");
		//System.out.println("season all summarry5: ");
		
		PdfPTable pdfTable = new PdfPTable(firstRow.getCells().size()+1);
		
		
		
		
		
		
		List<HtmlTableCell> cells = firstRow.getCells();
		
		List<String> headings = new ArrayList<>(cells.size()+1);
		for (HtmlTableCell cell : firstRow.getCells()){
			headings.add(cell.asText());
		}
		headings.add(4,"");
		
		for (String s : headings){
			pdfTable.addCell(s);
			//System.out.println("cell: " + cell.asText());
		}
		
		int linkNumber = 0;
		
		for (HtmlTableRow row : rows){
			
			linkNumber++;
			
			List<HtmlTableCell> rowOfCells = row.getCells();
			
			int i = 0;
			
			for (; i < rowOfCells.size() - 1; i++){
				HtmlTableCell cell = rowOfCells.get(i);
				String text = cell.getTextContent();
				System.out.println("text:--" + rowOfCells.size());
				if(rowOfCells.size() < 4){
					pdfTable.addCell(new Phrase(text, NORMAL));
					
					pdfTable.addCell("");
					pdfTable.addCell("");
					pdfTable.addCell("");
					pdfTable.addCell("");
					pdfTable.addCell("");
					pdfTable.addCell("");
					pdfTable.addCell("");
					
					linkNumber --;
					
					
				} else {
					pdfTable.addCell(new Phrase(text, NORMAL));
				}
				
			}
			
			HtmlTableCell cell = rowOfCells.get(i);
			String text = cell.asText();
			Anchor anchor = new Anchor(new Phrase(text, ANCHOR));
			anchor.setReference("#" + mrls.team.getNickname() + linkNumber);
			
			pdfTable.addCell(anchor);
			
			
			
		}
		return pdfTable;
	}

	
	private static Collection<AnchoredElement> createAllMatchReports( MatchReportLinkStrategyForSi mrls ) {
		// match recaps as DomNode
		// recaps are made of many paragraphs
		List<DomNode> nodes = mrls.getRecapDomNodes();
		
		//to be returned
		List<AnchoredElement> elements = new ArrayList<>();
		
		int linkNumber = 0;
		
		
		for (DomNode dn : nodes){
			//increment linkNumber
			linkNumber++;
			
			// cycle through the paragraphs
			Iterable<DomNode> children = dn.getChildren();
			Iterator<DomNode> iter = children.iterator();
			
			Font currentFont = BOLD_UNDERLINED;
			
			// get rid of first blank paragraph
			 iter.next();
			
			// build headline and link
			
			
			AnchoredElement mainParagraph = null;
			while (iter.hasNext()){
				DomNode thing = iter.next();
				if (currentFont.equals(BOLD_UNDERLINED)) {
					//System.out.println("link:--" + mrls.team.getNickname() + linkNumber);
					//System.out.println("text: " + thing.getTextContent());
					
					String content = buildString(thing);
					
					Anchor anchor = new Anchor(new Phrase(content, ANCHOR));
					anchor.setName(mrls.team.getNickname() + linkNumber);
					anchor.setReference("#" + mrls.team.getNickname() + "Top");
					System.out.println("link:--" + "#" + mrls.team.getNickname() + "Top");
					
					Paragraph p = new Paragraph();
					p.add(anchor);
					
					mainParagraph = new AnchoredParagraph(p, anchor);
					
				} else {
					String content = buildString(thing);
					
					mainParagraph.add(new Phrase(content, currentFont));
				}
				
				if (!thing.asXml().equals("") ) {
					currentFont = NORMAL;
				}
			}
			elements.add(mainParagraph);
		}
		return elements;
	}

	private static String buildString(DomNode thing) {
		String content = thing.getTextContent();
		//String[] words = content.split("\\s+");
		
		return content.replaceAll("\\s+", " ");
	}

	@Override
	public String getTeamName() {
		
		return team.name().replaceAll("_", " ");
	}

	@Override
	public PdfPTable getSeasonSummary() {
		
		return summary;
	}

	@Override
	public Collection<Element> getAllMatchReports() {
		
		return elements;
	}

	@Override
	public String getNickname() {
		// TODO Auto-generated method stub
		return team.getNickname();
	}

}

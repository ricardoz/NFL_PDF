package nfl.pdf;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;

public class NflPdfFactory implements Runnable {

	private List<MatchReportLinksStrategy> articles;
	private Document doc;
	private String filename;

	public NflPdfFactory(Document document,
			List<MatchReportLinksStrategy> articles, String filename) {
		this.articles = articles;
		this.doc = document;
		this.filename = filename;
	}

	public static NflPdfFactory createPdf(List<MatchReportLinksStrategy> articles, String filename) throws FileNotFoundException, DocumentException {
		

		

		// step 1
		Document document = new Document();
		// step 2
		PdfWriter.getInstance(document, new FileOutputStream(filename));
		// step 3
		document.open();

		return new NflPdfFactory(document, articles, filename);
		        
		        
		
	}

	@Override
	public void run() {
		int chapterNumber = 1;
		for (MatchReportLinksStrategy mr : articles){
			PdfStrategy strategy = mr.getPdfStrategy();
			try {
				Anchor anchor = new Anchor(new Phrase(strategy.getTeamName()));
				anchor.setName(strategy.getNickname() + "Top");
				//System.out.println("setting anchor " + strategy.getNickname() + "Top" );
				
				
				Paragraph p = new Paragraph();
				p.add(anchor);
				Chapter c = new Chapter(p , chapterNumber);
				c.add(Chunk.NEWLINE);
				c.add(strategy.getSeasonSummary());
				for (Element e : strategy.getAllMatchReports()){
					c.add(e);
					c.add(Chunk.NEWLINE);
				}
				
				c.add(Chunk.NEWLINE);
				
				doc.add(c);
				
				chapterNumber++;
				
				
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
		doc.close();
		
		if (Desktop.isDesktopSupported()) {
			Desktop d = Desktop.getDesktop();
			try {
				d.open(new File(filename));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}

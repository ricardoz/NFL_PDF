package nfl;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import nfl.pdf.MatchReportLinksStrategy;
import nfl.pdf.NflPdfFactory;
import nfl.pdf.NflPdfImplString;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.itextpdf.text.DocumentException;

public class NflPdfFrame extends JFrame {
	
	Conference afc;
	Conference nfc;
	
	
	private NflPdfFrame(Conference afc, Conference nfc){
		super("Nfl schedule");
		
		this.afc = afc;
		this.nfc = nfc;
		
		// set size
		setSize(500, 300);
		
		// add exit listener
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		getContentPane().add(NflTopPanel.createTopPanel(this), BorderLayout.NORTH);
		getContentPane().add(afc, BorderLayout.WEST);
		getContentPane().add(nfc, BorderLayout.EAST);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 268147398449508982L;

	public static NflPdfFrame getNflPdfFrame(){
		
		// get teams
		List<String> list = Teams.getTeams();
		
		List<String> afcList = list.subList(0, 16);
		List<String> nfcList = list.subList(16, 32);
		
		Conference afc = Conference.createConferenceComponent(afcList);
		Conference nfc = Conference.createConferenceComponent(nfcList);
		
		
		
		
		
		/*for (String s : afc){
			System.out.println(s);
		}*/
		
		
		
		return new NflPdfFrame(afc, nfc);
		
	}

	public void createPdf() {
		List<String> checkedTeams = new ArrayList<>();
		
		afc.getCheckedTeams(checkedTeams);
		nfc.getCheckedTeams(checkedTeams);
		
		for (String s : checkedTeams){
			System.out.println("selected:--" + s);
		}
		
		if (checkedTeams.size() > 0){
			List<NflTeam> converted = new ArrayList<>(checkedTeams.size());
			System.out.println("team:--" + NflTeam.getTeam(checkedTeams.get(0).toLowerCase()));
			
			for (String s : checkedTeams){
				converted.add(NflTeam.getTeam(s.toLowerCase()));
			}
			
			for(NflTeam t : converted){
				System.out.println("conv:--" + t.name());
			}
			NflPdfImplString pdf = NflPdfImplString.createPdfFromList(converted);
			
			try {
				List<MatchReportLinksStrategy> articles = pdf.getTeamArticles();
				
				StringBuilder builder = new StringBuilder("nfl 2011 results");
				
				for (NflTeam team : converted){
					builder.append(" " + team.getNickname());
				}
				
				builder.append(".pdf");
				
				String filename = builder.toString();
				
				NflPdfFactory.createPdf(articles, filename).run();
			} catch (FailingHttpStatusCodeException | IOException
					| InterruptedException | DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	

}

package nfl.pdf;

import java.util.Collection;

import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPTable;


/**
 * Converts Match Report Link Strategy To Pdf
 * @author Miriam Martin
 *
 */
public interface PdfStrategy {
	public PdfPTable getSeasonSummary();
	public Collection<Element> getAllMatchReports();
	public String getTeamName();
	public String getNickname();
}

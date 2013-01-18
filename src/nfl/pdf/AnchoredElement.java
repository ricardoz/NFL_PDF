package nfl.pdf;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Element;
import com.itextpdf.text.TextElementArray;

public interface AnchoredElement extends TextElementArray {
	public Anchor getAnchor();

	public Element getElement();
}

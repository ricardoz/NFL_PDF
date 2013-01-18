package nfl.pdf;

import java.util.List;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.ElementListener;
import com.itextpdf.text.TextElementArray;

public class AnchoredParagraph implements AnchoredElement {

	private Anchor a;
	private TextElementArray textElementArray;
	

	

	/**
	 * @param element
	 * @return
	 * @see com.itextpdf.text.TextElementArray#add(com.itextpdf.text.Element)
	 */
	public boolean add(Element element) {
		return textElementArray.add(element);
	}

	/**
	 * @param listener
	 * @return
	 * @see com.itextpdf.text.Element#process(com.itextpdf.text.ElementListener)
	 */
	public boolean process(ElementListener listener) {
		return textElementArray.process(listener);
	}

	/**
	 * @return
	 * @see com.itextpdf.text.Element#type()
	 */
	public int type() {
		return textElementArray.type();
	}

	/**
	 * @return
	 * @see com.itextpdf.text.Element#isContent()
	 */
	public boolean isContent() {
		return textElementArray.isContent();
	}

	/**
	 * @return
	 * @see com.itextpdf.text.Element#isNestable()
	 */
	public boolean isNestable() {
		return textElementArray.isNestable();
	}

	/**
	 * @return
	 * @see com.itextpdf.text.Element#getChunks()
	 */
	public List<Chunk> getChunks() {
		return textElementArray.getChunks();
	}

	/**
	 * @return
	 * @see com.itextpdf.text.Element#toString()
	 */
	public String toString() {
		return textElementArray.toString();
	}

	public AnchoredParagraph(TextElementArray teArray, Anchor a){
		this.textElementArray = teArray;
		this.a = a;
	}

	@Override
	public Anchor getAnchor() {
		// TODO Auto-generated method stub
		return a;
	}

	@Override
	public Element getElement() {
		// TODO Auto-generated method stub
		return textElementArray;
	}

	

}

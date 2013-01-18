package nfl;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NflTopPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6187150338998293291L;
	private NflPdfFrame frame;
	
	private NflTopPanel(){
		
	}
	
	public ActionListener getActionListener() {

		return new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				//Execute when button is pressed
				System.out.println("You clicked the button " + frame);
				frame.createPdf();
			}
		};
	}

	public static Component createTopPanel(NflPdfFrame frame) {
		NflTopPanel top = new NflTopPanel();
		top.setFrame(frame);
		
		top.setLayout(new GridLayout(1,3));
		
		// jlabel dimensions
		Dimension jlabelDimension = new Dimension(140,20);
		
		// creat labels
		JLabel afc = new JLabel("AFC", JLabel.CENTER);
		afc.setSize(jlabelDimension);
		
		JLabel nfc = new JLabel("NFC", JLabel.CENTER);
		nfc.setSize(jlabelDimension);
		nfc.setHorizontalTextPosition(JLabel.CENTER);
		
		// create button and action listener
		JButton create = new JButton("Create Pdf");
		create.addActionListener(top.getActionListener());
		
		top.add(afc);
		
		top.add(create);
		
		top.add(nfc);
		
		top.setBorder(BorderFactory.createLineBorder(Color.black));
		
		return top;
	}

	private void setFrame(NflPdfFrame frame) {
		this.frame = frame;
		
	}

}

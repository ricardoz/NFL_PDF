package nfl;


public class MainNflPdf {
	
	public static int year = 2011;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int y = 0; 
		
		try {
			y = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (year > 2008 && y < 2013) year = y;
		
		
		NflPdfFrame f = NflPdfFrame.getNflPdfFrame();
		
		f.setVisible(true);

	}

}

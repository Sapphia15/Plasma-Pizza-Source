package levelEditor;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Frame{
	static String  coords;
	static Panel p;
	public static void main(String[] args){
		JFrame f=new JFrame();
		f.setPreferredSize(new Dimension(929,929));
		f.setTitle("Level Editor");
		f.setResizable(false);
		f.pack();
		p=new Panel();
		f.add(p);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		String[] options={"Load","New"};
		int Load=JOptionPane.showOptionDialog(f, "Would you like to load a file?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,options, options[1]);
		coords=JOptionPane.showInputDialog("Sector Coordinates (noted as: (x)y(y))", "0y0");
		if (Load==0){
			
		} else {
			
		}
	}
}

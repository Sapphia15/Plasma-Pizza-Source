package levelEditor;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class EditorFrame{
	static String  coords;
	static int x;
	static int y;
	static EditorPanel p;
	public static void main(String[] args){
		JFrame f=new JFrame();
		f.setPreferredSize(new Dimension(929,929));
		f.setTitle("Level Editor");
		f.setResizable(false);
		f.pack();
		p=new EditorPanel();
		f.add(p);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		String[] options={"Load","New"};
		int Load=JOptionPane.showOptionDialog(f, "Would you like to load a file?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,options, options[1]);
		x=Integer.parseInt(JOptionPane.showInputDialog("Sector X Coordinate", "0"));
		y=Integer.parseInt(JOptionPane.showInputDialog("Sector Y Coordinate", "0"));
		//coords=JOptionPane.showInputDialog("Sector Coordinates (noted as: (x)y(y))", "0y0");
		coords=x+"y"+y;
		if (Load==0){
			
		} else {
			
		}
	}
}

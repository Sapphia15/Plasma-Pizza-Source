package topDownGameAPI.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

public class TextWriter {
	Hashtable<String, BufferedWriter> writers;
	public TextWriter(){
		writers=new Hashtable<String, BufferedWriter>();
	}
	public void writeLine(String path, String msg){
		if (!writers.containsKey(path)){
			try {
				File f=new File(path);
				writers.put(path, new BufferedWriter(new FileWriter(f,true)));
				System.out.println(f.getCanonicalPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		BufferedWriter writer=writers.get(path);
		try {
			writer.write(msg);
			writer.newLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void close(String path){
		if (writers.containsKey(path)){
			try {
				writers.get(path).close();
				writers.remove(writers.get(path));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.err.println("Writer has not been opened");
		}
	}
}

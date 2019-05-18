package topDownGameAPI.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
public class Sounds {
	static Hashtable<String, Clip> BufferedClips;
	static Hashtable<String, AudioInputStream> BufferedAIS;
	static String src; //source path for sounds
	public Sounds(){
	}
	public static void setSourcePath(String src){
		Sounds.src=System.getProperty("user.dir")+src;
		System.out.println(Sounds.src);
	}
	public void initialize(ArrayList<String> soundPaths){
		BufferedClips=new Hashtable<String, Clip>();
		BufferedAIS=new Hashtable<String, AudioInputStream>();
		for (String path:soundPaths){
			loadSound(path);
		}
	}
	public void loadSound(String Name){
		String FilePath=Sounds.src+Name;
		
		AudioInputStream audioInputStream;
        Clip clip;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File(FilePath).getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			BufferedClips.put(Name, clip);
			BufferedAIS.put(Name, audioInputStream);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}
	public void playSound(String Name, int repeat) {
		String FilePath=(Sounds.src+Name);
		AudioInputStream audioInputStream;
        Clip clip;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File(FilePath).getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.loop(repeat);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		
    }public void playSoundOnLoop(String Name,int vol) {
		String FilePath=Sounds.src+Name;
		AudioInputStream audioInputStream;
        Clip clip;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File(FilePath).getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		
    }
}

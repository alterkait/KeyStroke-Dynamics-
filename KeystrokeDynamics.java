package typingdynamics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class KeystrokeDynamics implements KeyListener{
	
	private LinkedList<Long> keyTimes;
	private LinkedList<Boolean> keyPressOrRelease;
	
	private String targetPhrase;
	private String typedPhrase;
	
	public KeystrokeDynamics(String targetPhrase) {
		keyTimes = new LinkedList<Long>();
		keyPressOrRelease = new LinkedList<Boolean>();
		
		this.targetPhrase = targetPhrase;
		
		typedPhrase = "";
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keyTimes.add(e.getWhen());
		keyPressOrRelease.add(true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyTimes.add(e.getWhen());
		keyPressOrRelease.add(false);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		typedPhrase += e.getKeyChar();
		if(typedPhrase.equals(targetPhrase)) {
			System.out.println("Target Phrase Typed");
			System.out.println("Key times: " + keyTimes);
			
			//keystroke duration time
			LinkedList<Long> keystrokeDurations = new LinkedList<Long>();
			for(int i=0; i<keyPressOrRelease.size(); i++) {
				if(keyPressOrRelease.get(i)) {
					int j = i+1;
					while(j < keyPressOrRelease.size() && keyPressOrRelease.get(j)) {
						j++;
					}
					if(j < keyPressOrRelease.size()) {
						keystrokeDurations.add(keyTimes.get(j) - keyTimes.get(i));
					}
				}
			}
			System.out.println("Keystroke durations: " + keystrokeDurations);

			//flight times
			LinkedList<Long> flightTimes = new LinkedList<Long>();
            
			for(int i=0; i<keyPressOrRelease.size(); i++) {
				if(!keyPressOrRelease.get(i)) {
					int j = i+1;
					while (j < keyPressOrRelease.size() && !keyPressOrRelease.get(j)) {
						j++;
					}
					if(j < keyPressOrRelease.size()) {
						flightTimes.add(keyTimes.get(j) - keyTimes.get(i));
					}
				}
			}
			System.out.println("Flight times: " + flightTimes);
			
			long totalFlightTime = 0;
	        for (long flightTime : flightTimes) {
	            totalFlightTime += flightTime;
	        }
	        long averageFlightTime = totalFlightTime / flightTimes.size();
	        System.out.println("Average flight time: " + averageFlightTime + " ms");
		}
		
	}

	
	public static void main(String [] args) {
        
        JTextField textField = new JTextField();
        KeystrokeDynamics recorder = new KeystrokeDynamics("The quick brown fox jumped over the lazy dog");
        textField.addKeyListener(recorder);
        JFrame frame = new JFrame();
        frame.add(textField);
        frame.setSize(400, 200);
        frame.setVisible(true);
        
        
	}

}

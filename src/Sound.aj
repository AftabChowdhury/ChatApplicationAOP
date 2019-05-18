import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public aspect Sound {
	 void around(String notifySound) : execution(void ChatClient3.notifySound(String)) && args(notifySound) {
		// Test
					SimpleAudioPlayer player = new SimpleAudioPlayer();
					try {
						//player.playSound(msgSound);
						player.playSound(notifySound);
					} catch (IOException | InterruptedException | LineUnavailableException | UnsupportedAudioFileException e) {
						System.out.println("Do whatever you want to do with all these exceptions.");
						e.printStackTrace();
					}
	}
 }


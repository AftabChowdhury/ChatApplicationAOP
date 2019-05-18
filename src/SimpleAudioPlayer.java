
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineEvent.Type;

public class SimpleAudioPlayer {

	/*public static void main(String[] args) {

		String msgSound = "/src/client/Message.au";
		String joinSound = "/src/client/Join.au";

		// Test
		SimpleAudioPlayer player = new SimpleAudioPlayer();
		try {
			player.playSound(msgSound);
			player.playSound(joinSound);
		} catch (IOException | InterruptedException | LineUnavailableException | UnsupportedAudioFileException e) {
			System.out.println("Do whatever you want to do with all these exceptions.");
			e.printStackTrace();
		}
	}*/

	public void playSound(String pathToAu)
			throws IOException, InterruptedException, LineUnavailableException, UnsupportedAudioFileException {

		class AudioListener implements LineListener {
			private boolean done = false;

			@Override
			public synchronized void update(LineEvent event) {
				Type eventType = event.getType();
				if (eventType == Type.STOP || eventType == Type.CLOSE) {
					done = true;
					notifyAll();
				}
			}

			public synchronized void waitUntilDone() throws InterruptedException {
				while (!done) {
					wait();
				}
			}
		}

		String cwd = null;
		try {
			cwd = new java.io.File(".").getCanonicalPath();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		InputStream audioSrc = new FileInputStream(cwd + pathToAu);
		InputStream bufferedIn = new BufferedInputStream(audioSrc);

		AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
		audioStream = convertToPCM(audioStream);

		AudioListener listener = new AudioListener();
		try {
			Clip clip = AudioSystem.getClip();

			clip.addLineListener(listener);
			clip.open(audioStream);
			try {
				clip.start();
				listener.waitUntilDone();
			} finally {
				clip.close();
			}
		} finally {
			audioStream.close();
		}
	}

	private static AudioInputStream convertToPCM(AudioInputStream audioInputStream) {
		AudioFormat m_format = audioInputStream.getFormat();

		if ((m_format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED)
				&& (m_format.getEncoding() != AudioFormat.Encoding.PCM_UNSIGNED)) {
			AudioFormat targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, m_format.getSampleRate(), 16,
					m_format.getChannels(), m_format.getChannels() * 2, m_format.getSampleRate(),
					m_format.isBigEndian());
			audioInputStream = AudioSystem.getAudioInputStream(targetFormat, audioInputStream);
		}

		return audioInputStream;
	}
}

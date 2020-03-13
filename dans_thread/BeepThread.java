package dans_thread;

import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;

public class BeepThread extends Thread {
	private int oneSecond = 1000;
	
	public BeepThread() {}
	
	public void run() {
		while (true) {
			Sound.twoBeeps();
			try {
				Thread.sleep(oneSecond);
			} catch (InterruptedException e) {
				LCD.drawString("Beep Sleep Error", 0, 6);
			}
		}
	}
}

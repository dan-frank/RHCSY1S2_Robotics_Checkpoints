package dans_thread;

import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class HelloWorldThread extends Thread {
	private int i = 0;
	private int oneSecond = 1000;
	
	public HelloWorldThread() {}

	public void run() {
		while (true) {
			LCD.drawString("Hello World:", 0, 0);
			LCD.drawInt(i, 0, 1);
			i++;
			
			Delay.msDelay(oneSecond);
			LCD.clear(1);
		}
	}

}

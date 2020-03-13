package checkpoint_1;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class HelloWorld {

	public static void main(String[] args) {
		Button.LEDPattern(4);
		Sound.beepSequenceUp();
		LCD.clear();
		LCD.drawString(" HOLYy", 2 , 2);
		
		LCD.drawString(" SMOKE ", 2 ,3);
		Delay.msDelay(1000);
	}

}

package dans_thread;

import lejos.hardware.Button;

public class Driver {
	private static HelloWorldThread hwt;
//	private static BeepThread bt;
	private static TunePlayerThread tpt;
	private static MoveThread mt;
	
	public static void main(String[] args){
		hwt = new HelloWorldThread();
//		bt = new BeepThread();
		tpt = new TunePlayerThread();
		mt = new MoveThread();

		hwt.start();
		tpt.start();
//		bt.start();
		mt.start();
		
		while(!Button.ENTER.isDown()) {}
		
		System.exit(0);
	}

}

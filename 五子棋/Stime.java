package ������;

import java.awt.Graphics;

public class Stime implements Runnable {
	
	private final int limit= 30;
	private int time=30;

	
	public void run() {


		while(time>0) {			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			

			time--;	
		}

    }			

	public int gettime() {
		return time;
	}
	
	public void reset() {
		time=limit;
	}
	

}

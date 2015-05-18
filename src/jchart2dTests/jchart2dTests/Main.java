package jchart2dTests;

import java.awt.EventQueue;
public class Main {
	public static void main(String []args){
			EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                new simpleFrame().setVisible(true);
	            }
	        });
			new PortReader();	
	}
}

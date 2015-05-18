package jchart2dTests;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PlotPanel extends JPanel{
	public int[] y = new int[10];
	private boolean isLocked=false;
	int xstart;
	public void drawChart(int []values,int xstart){
		y = values;
		this.xstart = xstart;
		this.repaint();
	}
	public boolean isLocked(){
		return isLocked;
	}
	public void lock(){
		isLocked = !isLocked;
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//rysuje t³o
		g.setColor(Color.white);
		g.fillRect(0, 0,this.getSize().width,this.getSize().height);
		//rysuje wykres
		g.setColor(Color.black);
		g.drawRect(0, 0	, this.getSize().width, this.getSize().height);

		for(int i = 0;i<y.length-1;i++){
		g.drawLine(xstart+i, y[i], xstart+i+1,y[i+1]);
		}
	}
}

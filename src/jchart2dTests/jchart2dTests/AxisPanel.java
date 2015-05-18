package jchart2dTests;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class AxisPanel extends JPanel{
	int tickSize = 10;
	int gaptop = 10;
	int gapbottom = 30;
	int gapleft = 60;
	float miny = -1;
	float maxy = 1;
	float startpoint = 0;
	
	public void setStartPoint(float point){
		startpoint = point;
		this.repaint();
	}
	//chartPanel size = 240-gaptop-gapbottom (200)
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, this.getSize().width, this.getSize().height);
		
		//---rysowanie osi y
		g.setColor(Color.BLACK);
		int height = this.getSize().height-gapbottom;
		for(int i = 0;i<21;i++){
			
		 if(i % 2==0)
		 {
			 g.drawLine(gapleft-20, height-i*10, gapleft, height-i*10);
			 g.drawString(String.format("%.2g%n",miny+(maxy-miny)*(float)i/20), gapleft-50,  height-i*10+5);
			 }else g.drawLine(gapleft-15, height-i*10, gapleft, height-i*10); 
		 
		}
		//---koniec rysowania osi y
		
		//---rysowanie osi x
		for(int i = 0;i<this.getSize().width;i+=60){
			g.drawLine(gapleft+ i, height, gapleft+i, height+5);
			int shift = g.getFontMetrics().stringWidth(""+(startpoint+(float)i))/2;
			g.drawString(""+(startpoint+i), gapleft+ i-shift, height+20);
			
		}
		
		
		//---koniec rysowania osi x
	}//koniec paintComponent
}//koniec klasy

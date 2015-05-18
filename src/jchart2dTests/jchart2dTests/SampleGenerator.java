package jchart2dTests;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JScrollBar;

public class SampleGenerator implements Runnable{
	AxisPanel aPan;
	PlotPanel pan;
	JScrollBar bar;
	List<Integer> samples;
	boolean barShifter;
	int a;
	int xstart;
	int panelStart;
	public SampleGenerator(AxisPanel aPan,PlotPanel pan,JScrollBar bar,boolean barShifter,int start){
		this.aPan = aPan;
		this.pan = pan;
		this.bar = bar;
		this.barShifter = barShifter;
		this.panelStart = start;
		samples = new ArrayList<Integer>();		
	}
	
	@Override
	public void run() {
		Random rand = new Random();
		int start = 0;
		int end = 0;

		if(samples.size() % 10 == 0) a = rand.nextInt(200);  
		samples.add(a);
		if(samples.size()>bar.getMaximum()) bar.setMaximum(samples.size());
		if(pan.isLocked())bar.setValue(bar.getMaximum()-1);

//		if(pan.getWidth() > samples.size()){
//			start = 0;
//			end = samples.size()-start;
//		} else{
//			end = pan.getWidth();
//			bar.setMinimum(pan.getWidth());
//			//System.out.println("ahoj2");
//			if(pan.isLocked())bar.setValue(bar.getMaximum()-1);
//			//}
//			start =(bar.getValue()>sample.size() 
//					? 
//					: bar.getValue()-end;
//			aPan.setStartPoint(start);
//
			
		if(bar.getValue()<=pan.getWidth()){
			xstart = panelStart;
			if(panelStart <= pan.getWidth() && panelStart+samples.size()<=pan.getWidth()){
				start = 0;
				end = samples.size();
			} else
			if(panelStart<=pan.getWidth() && panelStart+samples.size()>=pan.getWidth()){
				start = 0;
				end = pan.getWidth()-panelStart;
			} else
			if(panelStart>pan.getWidth()){
				start = 0;
				end = 0;
			}
		} else{
			if(panelStart+samples.size()<=bar.getValue()-pan.getWidth() || panelStart > bar.getValue()){
				xstart = 0;
				start = 0;
				end = 0;
			} else
			if(panelStart+samples.size()>=bar.getValue()-pan.getWidth() && panelStart < bar.getValue() - pan.getWidth()){
				xstart = 0;
				start = bar.getValue()-pan.getWidth()-panelStart;
				if(panelStart+samples.size()>bar.getValue()){
					end = pan.getWidth();
				} else{
					end = samples.size()-start;
				}
			} else
			if(panelStart>bar.getValue()-pan.getWidth()){
				xstart = panelStart -bar.getValue()+pan.getWidth(); 
				start = 0;
				if(panelStart<bar.getValue() - pan.getWidth()){
				end = bar.getValue() - panelStart;
				} else{
					end = samples.size();
				}
			}
			
		}
		//System.out.println(pan.getName()+":"+xstart);
		
		
		
		//System.out.println("bar min:"+bar.getMinimum()+" bar max:"+ bar.getMaximum());
	//	System.out.println("Sample.size: "+samples.size() +" bar.getValue():"+bar.getValue()+" Start: "+start+" End: "+end);

		///end = panel.getWidth() > samples.size()-bar.getValue() ? samples.size()-bar.getValue() : panel.getWidth();
		if(end>1){
			int[] values = new int[end];
			if(samples.size() % 1 == 0){
				for(int i = start;i<start+end;i++){
					values[i-start] = samples.get(i);
						}

				pan.drawChart(values,xstart);
					}
				}
		

			}//koniec run

		}

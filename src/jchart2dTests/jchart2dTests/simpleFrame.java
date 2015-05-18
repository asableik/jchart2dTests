package jchart2dTests;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class simpleFrame extends JFrame{
	double tic;
	boolean isLocked = true;
	
	PlotPanel panel;
	AxisPanel aPanel;
	JPanel container;
	JScrollPane sPane;
	JScrollBar bar;
	JMenuBar menuBar;
	JMenu mfile,medit,mplot,macquisition;
	JMenu mfile_mnew; //Jmenu nale¿¹ce do mfile;
	JMenu mplot_mnew;
	JMenuItem mfile_miuser,mfile_misubject,mistartacq,mipauseacq,mistopacq,minewplot,miraw,mirectified,mirms,mipowerspectrum;
	
	Timer timer = new Timer(true);
    ArrayList<Integer> samples;
    ArrayList<PlotPanel> panels;
   
	final ScheduledExecutorService scheduler =
		     Executors.newScheduledThreadPool(4);
	
	public simpleFrame(){
		samples = new ArrayList<Integer>();
		panels = new ArrayList<PlotPanel>();
		// konfiguracja ramki 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500,300);
		setTitle("frame");
		setFocusable(true);
		requestFocusInWindow();
		this.setLayout(new MigLayout("insets 0"));
		
		// Pasek menu w postaci JMenuBar
		menuBar = new JMenuBar();
		this.add(menuBar, "height 20::, growx,pushx,wrap");
		
		// menu File
		mfile = new JMenu("File");
		menuBar.add(mfile);
		
		mfile_mnew = new JMenu("New");
		mfile.add(mfile_mnew);
		
		mfile_miuser = new JMenuItem("User");
		mfile_miuser.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new PersonFrame(1);
			}
			
		});
		mfile_mnew.add(mfile_miuser);
		
		mfile_misubject = new JMenuItem("Subject");
		mfile_mnew.add(mfile_misubject);
		mfile_misubject.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new PersonFrame(2); 
			}
			
		});

		// menu Edit
		medit = new JMenu("Edit");
		menuBar.add(medit);
		
		// menu plots
		mplot = new JMenu("Plot");
		menuBar.add(mplot);
		minewplot = new JMenuItem("New plot wizard...");
		mplot.add(minewplot);
		
		mplot_mnew = new JMenu("New plot");
		mplot.add(mplot_mnew);
		
		miraw = new JMenuItem("raw EMG");
		mplot_mnew.add(miraw);
		
		mirectified = new JMenuItem("rectified EMG");
		mplot_mnew.add(mirectified);
		
		mirms = new JMenuItem("RMS EMG");
		mplot_mnew.add(mirms);
		
		//menu acquisition
		macquisition = new JMenu("Acquisiton");
		menuBar.add(macquisition);
		
		mistartacq = new JMenuItem("Start Acquisition");
		macquisition.add(mistartacq);
		
		mipauseacq = new JMenuItem("Pause Acquisition");
		macquisition.add(mipauseacq);
		
		mistopacq = new JMenuItem("Stop Acquisition");
		macquisition.add(mistopacq);
		
		// Scroll Panel G³ówny zawieraj¹cy wykresy i jego konfiguracja
		sPane = new JScrollPane();
		sPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		sPane.getHorizontalScrollBar().setSize(400, 100);
		this.add(sPane,"grow, push, wrap");
	
		
		
		// ScrollBar do przesuwania wykresów w osi x
		bar = new JScrollBar();
		bar.setOrientation(JScrollBar.HORIZONTAL);
		bar.setPreferredSize(new Dimension(300,40));
		bar.setMaximum(0);
		this.add(bar,"height ::20,growx,pushx");
		
		// Panel container - kontener z Panelami wykresami
		container = new JPanel();
		container.setLayout(new MigLayout());
		sPane.setViewportView(container);
		// Suwak do przewijania danych
		// Dodawanie paneli z wykresami do paneli container
		aPanel = new AxisPanel();
		aPanel.setLayout(new MigLayout("insets 0"));
		panels.add(new PlotPanel());	
		aPanel.add(panels.get(0),"gapleft 60,gapbottom 30,gaptop 10, push,grow");
		container.add(aPanel, "height 240::, pushx, growx, wrap");
		
		AxisPanel aPanel2 = new AxisPanel();
		aPanel2.setLayout(new MigLayout("insets 0"));
		panels.add(new PlotPanel());	
		aPanel2.add(panels.get(1),"gapleft 60,gapbottom 30,gaptop 10, push,grow");
		container.add(aPanel2, "height 240::, pushx, growx, wrap");
		
		JButton button = new JButton("hehs");
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(PlotPanel p:panels){
					p.lock();
					System.out.println(p.isLocked());
				}
				
			}
			
		});
		
		
		
		container.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "D pressed");
		container.getActionMap().put("D pressed", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent ae) {
	        	for(PlotPanel p:panels){
					p.lock();
					System.out.println(p.isLocked());
				}
	        }
	    });
		
		
		container.add(button,"wrap");
		panels.get(0).setName("1");
		panels.get(1).setName("2");
		genSamples(aPanel,panels.get(0),true,1000,0);
		genSamples(aPanel2,panels.get(1),false,11000,500);
	}//koniec konstruktora
	
	void genSamples(final AxisPanel aPan, final PlotPanel pan,boolean isBarShifter,int shift,int start){
		@SuppressWarnings("unused")
		 ScheduledFuture<?> generatorHandle = scheduler.scheduleAtFixedRate(new SampleGenerator(aPan,pan,bar,isBarShifter,start), shift, 20, TimeUnit.MILLISECONDS);	
	}//koniec genSamples
	
	}//koniec klasy

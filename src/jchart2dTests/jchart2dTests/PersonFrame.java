package jchart2dTests;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class PersonFrame extends JDialog {
	public PersonFrame(int type){
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setSize(400,400);
		setLayout(new MigLayout());
		if(type == 1){
			setTitle("New User");
		} else if(type == 2){
			setTitle("New Subject");
		}
		
		JLabel lfirstName = new JLabel("First Name:");
		JTextField tffirstName = new JTextField(30);
		
		JLabel llastName = new JLabel("Last Name:");
		JTextField tflastName = new JTextField(30);

		JLabel lheight = new JLabel("Height:");
		JTextField tfheight = new JTextField(10);
		String[] sheight = {"cm","in"};
		JComboBox<?> cbheight = new JComboBox<Object>(sheight);
		
		JLabel lweight = new JLabel("Weight:");
		JTextField tfweight = new JTextField(10);
		JComboBox<?> cbweight = new JComboBox<Object>(new String[]{"kg","lbs"});
		
		JLabel lgender = new JLabel("Gender:");
		ButtonGroup bggender = new ButtonGroup();
		JRadioButton rbmale = new JRadioButton("Male");
		JRadioButton rbfemale = new JRadioButton("Female");	
		bggender.add(rbmale);
		bggender.add(rbfemale);
		
		JLabel ladress = new JLabel("Adress:");
		JTextField tfadress = new JTextField(30);
		
		JLabel lcomments = new JLabel("Comments:");
		JTextArea tacomments = new JTextArea(5,30);
		tacomments.setWrapStyleWord(true);
		tacomments.setLineWrap(true);
		JScrollPane spcomments = new JScrollPane(tacomments);

		JButton bcancel= new JButton("Cancel");
		bcancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
			
		});
		
		JButton bok = new JButton("Ok");
		bok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
			
		});
		
		Border border = tflastName.getBorder();
		tacomments.setBorder(border);
		
		this.add(lfirstName);
		this.add(tffirstName,"wrap");
		this.add(llastName);
		this.add(tflastName,"wrap");
		this.add(lgender);
		this.add(rbmale,"split 2");
		this.add(rbfemale,"wrap");
		this.add(lheight);
		this.add(tfheight,"split 2");
		this.add(cbheight,"wrap");
		this.add(lweight);
		this.add(tfweight,"split 2");
		this.add(cbweight,"wrap");
		this.add(ladress);
		this.add(tfadress,"wrap");
		this.add(lcomments);
		this.add(spcomments,"wrap");
		this.add(bcancel);
		this.add(bok,"gapleft push");
		
		this.pack();
		setResizable(false);
		setModal(true);
		setVisible(true);
	}

}

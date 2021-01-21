package com.camp.e4.tiktok.go;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;


public class TimerWidget extends JFrame{
	private JLabel label;
	 
	private void drawTimer(PaintEvent e) {
		JPanel upper_panel = new JPanel();
		
		//text
		  label = new JLabel("Start");
		  label.setFont(new Font("serif", Font.BOLD, 100));
		  upper_panel.add(label);
		  
		 
		  JPanel down_panel = new JPanel();
		  down_panel.setLayout(new GridLayout(2,2));
		  
		  //button_start
		  JButton start_button = new JButton("Start");
		  down_panel.add(start_button);
		  
		  
		  //button_stop
		  JButton stop_button = new JButton("Stop");
		  down_panel.add(stop_button);
		  
		  
		  add(upper_panel);
		  add(down_panel);
		  
		  start_button.addActionListener(event-> {
			  (new MyThread()).start();  
		  });
	}
	
	class MyThread extends Thread {
	  public void run() {
		  int min = 15, sec = 59;
		  for(min = 14; min >= 0; min--) {
			  for (sec = 59; sec >= 0; sec--) {
				    try {
				     Thread.sleep(1000);
				    } catch (InterruptedException e) {
				     e.printStackTrace();
				    }
				    label.setText(min +":"+ sec + "");
			  }
		  }
	  
	  }
	 }
	 
	

	 public TimerWidget(Composite parent, int style) {
		
	  
	  
	  setVisible(true);
	  
	 }
	 

}


package com.camp.e4.tiktok.go;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.time.LocalTime;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class ClockWidget extends Canvas{
	//clock
	@SuppressWarnings("unused")
	private void drawClock(PaintEvent e) {
		
		//circle
		e.gc.drawArc((e.x)+20, (e.y)+20, (e.width)-20, (e.height)-20, 0, 360);
		
		//second
		int seconds = LocalTime.now().getSecond();
        int sec_arc = (15 - seconds) * 6 % 360;
        Color blue = e.display.getSystemColor(SWT.COLOR_BLUE);
        e.gc.setBackground(blue);
        e.gc.fillArc(e.x+20, e.y+20, (e.width)-20, (e.height)-20, sec_arc-1, 2);
        
        //min
        int min = LocalTime.now().getMinute();
        int min_arc = (15 - min) * 6 % 360;
        e.gc.fillArc(e.x+20, e.y+20, (e.width)-20, (e.height)-20, min_arc-1, 2);
        
        //hour
        int hour = LocalTime.now().getHour();
        int hour_arc;
        if(hour> 12) {
        	hour_arc = (3 - (hour-12)) * 30 % 360;
        }
        else {
        	hour_arc = (3 - hour) * 30 % 360;
        }
        e.gc.fillArc(e.x+20, e.y+20, (e.width)-20, (e.height)-20, hour_arc-1, 2);
        Font font = new Font("Serif",Font.BOLD, 50);
        //number
        Color white = e.display.getSystemColor(SWT.COLOR_WHITE);
        e.gc.setBackground(white);
        e.gc.drawString("12", e.width/2, e.y + 30);
        e.gc.drawString("3", (e.width)-30, e.height/2);
        e.gc.drawString("6", e.width/2, e.height-50);
        e.gc.drawString("9", (e.x)+30, e.height/2);
        
	}
	

	public ClockWidget(Composite parent, int style) {
		super(parent, style);

		addPaintListener(this::drawClock); 
		
		Runnable redraw = () -> {
			while (!this.isDisposed()) {
				this.getDisplay().asyncExec(() -> this.redraw());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					return;
				}
			}
		};
		new Thread(redraw, "TickTock").start();

	}
	
}


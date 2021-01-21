package com.camp.e4.tiktok.yang;

import java.time.LocalTime;
import java.util.Random;

import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;

public class YangClockWidget extends Canvas {
	int seconds;
	int hour;
	int min;
	
	public YangClockWidget(Composite parent, int style) {
		super(parent, style);
		
		
		addPaintListener(this::setHour);
		addPaintListener(this::setSecond);
		addPaintListener(this::setMin);
		addPaintListener(this::drawSecond);
        
        Runnable secRedraw = () -> {
			while (!this.isDisposed()) {
				this.getDisplay().asyncExec(() -> this.redraw());
				try {
					Thread.sleep(500);
				} catch (InterruptedException w) {
					return;
				}
			}
		};
		new Thread(secRedraw, "TickTock").start();
		
		
//		addPaintListener(this::drawSecond); 
		
//		Runnable redraw = () -> {
//			while (!this.isDisposed()) {
//				this.getDisplay().asyncExec(() -> this.redraw());
//				try {
//					Thread.sleep(500);
//				} catch (InterruptedException w) {
//					return;
//				}
//			}
//		};
//		new Thread(redraw, "TickTock").start();
	}
	
	private void setSecond(PaintEvent e) {
		seconds = LocalTime.now().getSecond();
	}
	
	private void setHour(PaintEvent e) {
		hour = LocalTime.now().getHour();
	}
	
	private void setMin(PaintEvent e) {
		min = LocalTime.now().getMinute();
	}
	
	private void drawSecond(PaintEvent e) {
		Random random = new Random();
		
		YangClock.x = random.nextInt(e.width-50);
		YangClock.y = random.nextInt(e.height-50);
		
//		e.gc.drawArc(x, y, 50, 50, 0, 360);
		Color black = e.display.getSystemColor(SWT.COLOR_BLACK);
        
        e.gc.setBackground(black);
		e.gc.fillOval(YangClock.x, YangClock.y, 50, 50);
        
        int arc = (15 - seconds) * 6 % 360;
        Color blue = e.display.getSystemColor(SWT.COLOR_WHITE);
        
        e.gc.setBackground(blue);
        e.gc.fillArc(YangClock.x, YangClock.y, 50, 50, arc-1, 2);

        arc = (3 - hour) * 30 % 360;
        e.gc.fillArc(YangClock.x, YangClock.y, 50, 50, arc-5, 10);
        
        arc = (15 - min) * 6 % 360;
        e.gc.fillArc(YangClock.x, YangClock.y, 50, 50, arc-3, 6);
        
        e.gc.drawText(hour+" : " + min +" : " + seconds, YangClock.x, YangClock.y + 60, true);
	}
	
	public Point computeSize(int w, int h, boolean changed) { // overriding method
		int size;
		if (w == SWT.DEFAULT) {
			size = h;
		} else if (h == SWT.DEFAULT) {
			size = w;
		} else {
			size = Math.min(w, h);
		}
		if (size == SWT.DEFAULT) {
			size = 50;
		}
		return new Point(size, size);
	}

}

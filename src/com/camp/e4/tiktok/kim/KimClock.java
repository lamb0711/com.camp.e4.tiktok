package com.camp.e4.tiktok.kim;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class KimClock extends Canvas {

	private Color color;
	private ZoneId zone = ZoneId.systemDefault();
	

	public KimClock(Composite parent, int style, RGB rgb) {
		super(parent, style);
		this.color = new Color(parent.getDisplay(), rgb);
		this.addPaintListener(this::drawClock);
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

	private void drawClock(PaintEvent e) {
		int size = 0;
		if (e.width < e.height) {
			size = e.width;
		} else {
			size = e.height;
		}

		int x = e.x +15, y = e.y + 15;
		size -= 30;

		// 뼈대
		e.gc.drawArc(x, y, size, size, 0, 360);

		// 배경색
		this.setBackground(color);

		// second
		e.gc.setBackground(e.display.getSystemColor(SWT.COLOR_BLACK));
		ZonedDateTime now = ZonedDateTime.now(zone);
		int second = now.getSecond();
		int arc = (15 - second) * 6 % 360;
		e.gc.fillArc(x, y, size, size, arc - 1, 2);
		
		//minute
		e.gc.setBackground(e.display.getSystemColor(SWT.COLOR_DARK_GRAY));
		int min = now.getMinute();
		arc = (15 - min) * 6 % 360;
		e.gc.fillArc(x, y, size, size, arc - 2, 4);
		
		// hour
		e.gc.setBackground(e.display.getSystemColor(SWT.COLOR_GRAY));
		int hours = now.getHour();
		arc = (3 - hours) * 30 % 360;
		e.gc.fillArc(x, y, size, size, arc - 5, 10);

	}

	@Override
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

	public void setZone(ZoneId zone) {
		this.zone = zone;
	}
	
	
	public void setColor(Color color) {
		this.color = color;
	}
	

}
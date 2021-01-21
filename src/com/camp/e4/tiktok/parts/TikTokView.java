package com.camp.e4.tiktok.parts;

import java.time.ZoneId;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.camp.e4.tiktok.go.ClockWidget;
import com.camp.e4.tiktok.go.GoClock;
import com.camp.e4.tiktok.kim.KimClock;
import com.camp.e4.tiktok.yang.YangClock;
import org.eclipse.swt.events.SelectionListener;

public class TikTokView {
	private Label myLabelInView;
	private Combo timeZones;
	private Combo colors;

	@PostConstruct
	public void createPartControl(Composite parent) throws Exception {
		System.out.println("Enter in SampleE4View postConstruct");

		myLabelInView = new Label(parent, SWT.BORDER);
		
		YangClock yangClock = new YangClock();
		yangClock.createPartControl(parent);
		
		GoClock goClock = new GoClock();
		goClock.createPartControl(parent);
		
		final KimClock kimClock = new KimClock(parent, SWT.NONE, new RGB(144,238,144));

		timeZones = new Combo(parent, SWT.DROP_DOWN);
		timeZones.setVisibleItemCount(5);
		for (String zone : ZoneId.getAvailableZoneIds()) {
			timeZones.add(zone);
		}

		timeZones.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				String id = timeZones.getText();
				kimClock.setZone(ZoneId.of(id));
				kimClock.redraw();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				kimClock.setZone(ZoneId.systemDefault());
				kimClock.redraw();
			}
		});

		colors = new Combo(parent, SWT.DROP_DOWN);
		colors.setVisibleItemCount(5);
		colors.add("Black");
		colors.add("White");
		colors.add("Red");
		colors.add("Green");
		colors.add("Blue");
		colors.add("Yellow");
		colors.add("Lime");
		colors.add("Cyan");
		colors.add("Light Green");

		colors.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				Color temp = findColor(colors.getText());
				kimClock.setColor(temp);
				kimClock.redraw();
			}

			private Color findColor(String text) {
				if(text.equals("Black")) {
					return new Color(null,0, 0, 0);
				}
				else if(text.equals("White")) {
					return new Color(null,255, 255, 255);
				}
				else if(text.equals("Red")) return new Color(null,255, 0, 0);
				else if(text.equals("Green")) return new Color(null,0, 128, 0);
				else if(text.equals("Blue")) return new Color(null,0, 0, 255);
				else if(text.equals("Yellow")) return new Color(null,255, 255, 0);
				else if(text.equals("Lime")) return new Color(null,0, 255, 0);
				else if(text.equals("Cyan")) return new Color(null,0, 255, 255);
				else if(text.equals("Light Green")) return new Color(null,144,238,144);
				else return new Color(null,255, 255, 255);

			}

			public void widgetDefaultSelected(SelectionEvent e) {
				kimClock.setColor(new Color(null,255, 255, 255));;
				kimClock.redraw();
			}
		});
		
	}

	@Focus
	public void setFocus() {
		myLabelInView.setFocus();
		timeZones.setFocus();
	}

	/**
	 * This method is kept for E3 compatiblity. You can remove it if you do not
	 * mix E3 and E4 code. <br/>
	 * With E4 code you will set directly the selection in ESelectionService and
	 * you do not receive a ISelection
	 * 
	 * @param s
	 *            the selection received from JFace (E3 mode)
	 */
	@Inject
	@Optional
	public void setSelection(@Named(IServiceConstants.ACTIVE_SELECTION) ISelection s) {
		if (s==null || s.isEmpty())
			return;

		if (s instanceof IStructuredSelection) {
			IStructuredSelection iss = (IStructuredSelection) s;
			if (iss.size() == 1)
				setSelection(iss.getFirstElement());
			else
				setSelection(iss.toArray());
		}
	}

	/**
	 * This method manages the selection of your current object. In this example
	 * we listen to a single Object (even the ISelection already captured in E3
	 * mode). <br/>
	 * You should change the parameter type of your received Object to manage
	 * your specific selection
	 * 
	 * @param o
	 *            : the current object received
	 */
	@Inject
	@Optional
	public void setSelection(@Named(IServiceConstants.ACTIVE_SELECTION) Object o) {

		// Remove the 2 following lines in pure E4 mode, keep them in mixed mode
		if (o instanceof ISelection) // Already captured
			return;

		// Test if label exists (inject methods are called before PostConstruct)
		if (myLabelInView != null)
			myLabelInView.setText("Current single selection class is : " + o.getClass());
	}

	/**
	 * This method manages the multiple selection of your current objects. <br/>
	 * You should change the parameter type of your array of Objects to manage
	 * your specific selection
	 * 
	 * @param o
	 *            : the current array of objects received in case of multiple selection
	 */
	@Inject
	@Optional
	public void setSelection(@Named(IServiceConstants.ACTIVE_SELECTION) Object[] selectedObjects) {

		// Test if label exists (inject methods are called before PostConstruct)
		if (myLabelInView != null)
			myLabelInView.setText("This is a multiple selection of " + selectedObjects.length + " objects");
	}
}

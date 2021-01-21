package com.camp.e4.tiktok.go;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;

public class GoClock {
	
public void createPartControl(Composite parent) throws Exception {
		
		RowLayout layout = new RowLayout(SWT.HORIZONTAL);
		parent.setLayout(layout);
		
		final ClockWidget clock = new ClockWidget(parent, SWT.NONE);
}


}

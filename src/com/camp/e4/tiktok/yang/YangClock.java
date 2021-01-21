package com.camp.e4.tiktok.yang;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class YangClock{
	static int x;
	static int y;
	static Button button;
	private static Shell popup;
	
	public void createPartControl(Composite parent) throws Exception {
		
		RowLayout layout = new RowLayout(SWT.HORIZONTAL);
		parent.setLayout(layout);
		
		final YangClockWidget clock1 = new YangClockWidget(parent, SWT.NONE);
		
		clock1.setLayoutData(new RowData(500, 500));

	}

}

package com.slide;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JSplitPane;

public class ResizableSplitPane extends JSplitPane {

	//
	// instance variables
	//

	private boolean painted;

	private double defaultDividerLocation;

	private double dividerProportionalLocation;

	private int currentDividerLocation;

	private Component first;

	private Component second;

	private boolean dividerPositionCaptured = false;

	//
	// constructors
	//

	public ResizableSplitPane(int splitType, Component first, Component second, Component parent) {
		this(splitType, first, second, parent, 0.1);
	} 

	public ResizableSplitPane(int splitType, Component first, Component second, Component parent,
			double defaultDividerLocation) {
		super(splitType, first, second);
		this.defaultDividerLocation = defaultDividerLocation;
		this.dividerProportionalLocation = defaultDividerLocation;
		this.setResizeWeight(defaultDividerLocation);
		this.first = first;
		this.second = second;
		parent.addComponentListener(new DividerLocator());
		second.addComponentListener(new DividerMovedByUserComponentAdapter());
	}

	//
	// trivial getters and setters
	//

	public double getDefaultDividerLocation() {
		return defaultDividerLocation;
	}

	public void setDefaultDividerLocation(double defaultDividerLocation) {
		this.defaultDividerLocation = defaultDividerLocation;
	}

	//
	// implementation
	//

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (painted == false) {
			painted = true;
			this.setDividerLocation(dividerProportionalLocation);
			this.currentDividerLocation = this.getDividerLocation();
		}
	}

	private class DividerLocator extends ComponentAdapter {
		@Override
		public void componentResized(ComponentEvent e) {
			setDividerLocation(dividerProportionalLocation);
			currentDividerLocation = getDividerLocation();
		}
	}

	private class DividerMovedByUserComponentAdapter extends ComponentAdapter {
		@Override
		public void componentResized(ComponentEvent e) {
			// System.out.println("RESIZED: " + dividerPositionCaptured);
			int newDividerLocation = getDividerLocation();
			boolean dividerWasMovedByUser = newDividerLocation != currentDividerLocation;
			// System.out.println(
			// currentDividerLocation + "\t" + newDividerLocation + "\t" +
			// dividerProportionalLocation);
			if (dividerPositionCaptured == false || dividerWasMovedByUser == true) {
				dividerPositionCaptured = true;
				painted = false;
				if (getOrientation() == JSplitPane.HORIZONTAL_SPLIT) {
					dividerProportionalLocation = (double) first.getWidth()
							/ (double) (first.getWidth() + second.getWidth());
				} else {
					dividerProportionalLocation = (double) first.getHeight()
							/ (double) (first.getHeight() + second.getHeight());

				}
				// System.out.println(dividerProportionalLocation);
			}
		}
	}

}

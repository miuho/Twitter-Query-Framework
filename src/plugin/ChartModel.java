package edu.cmu.cs.cs214.hw5.plugin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/*
 * Example code for drawing pie chart from
 * http://www.ijava2.com/public-class-chartmodel-bar-graph-pie-chart-java-class/
 * 
 */

public class ChartModel {
	private int[] data;
	private String[] dataName;
	private transient Vector actionListeners;

	
	public ChartModel(int[] data, String[] dataName) {
		this.data = data;
	    this.dataName = dataName;
	}
	
	public int[] getData() {
		return this.data;
	}

	public synchronized void removeActionListener(ActionListener l) {
		if ((this.actionListeners != null)
				&& (this.actionListeners.contains(l))) {
			Vector v = (Vector) this.actionListeners.clone();
			v.removeElement(l);
			this.actionListeners = v;
		}
	}

	public synchronized void addActionListener(ActionListener l) {
		Vector v = this.actionListeners == null ? new Vector(2)
				: (Vector) this.actionListeners.clone();
		if (!v.contains(l)) {
			v.addElement(l);
			this.actionListeners = v;
		}
	}

	protected void fireActionPerformed(ActionEvent e) {
		if (this.actionListeners != null) {
			Vector listeners = this.actionListeners;
			int count = listeners.size();
			for (int i = 0; i < count; i++)
				((ActionListener) listeners.elementAt(i)).actionPerformed(e);
		}
	}

	public void setChartData(String[] newDataName, int[] newData) {
		this.dataName = newDataName;
		this.data = newData;

		fireActionPerformed(new ActionEvent(this, 1001, null));
	}

	public String[] getDataName() {
		return this.dataName;
	}
}
package edu.cmu.cs.cs214.hw5.plugin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

/*
 * Example code for drawing pie chart from
 * http://www.ijava2.com/public-class-piechart-extends-jpanel-bar-graph-application/
 * 
 */

public class PieChart extends JPanel implements ActionListener {
	BorderLayout borderLayout1 = new BorderLayout();
	private ChartModel model;
	Color[] colors = { Color.red, Color.yellow, Color.green, Color.blue,
			Color.cyan, Color.magenta, Color.orange, Color.pink, Color.darkGray };

	public PieChart() {
		setLayout(this.borderLayout1);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (this.model == null)
			return;

		int radius = (int) (Math.min(getWidth(), getHeight()) * 0.5D * 0.9D);
		int x = getWidth() / 2 - radius;
		int y = getHeight() / 2 - radius;

		String[] dataName = this.model.getDataName();
		int[] data = this.model.getData();

		double total = 0.0D;
		for (int i = 0; i < data.length; i++) {
			total += data[i];
		}
		int angle1 = 0;
		int angle2 = 0;
		for (int i = 0; i < data.length; i++) {
			angle1 += angle2;
			angle2 = (int) Math.ceil(360.0D * data[i] / total);
			g.setColor(this.colors[(i % this.colors.length)]);
			g.fillArc(x, y, 2 * radius, 2 * radius, angle1, angle2);
			g.setColor(Color.black);
			g.drawString(
					dataName[i],
					(int) (getWidth() / 2 + radius
							* Math.cos((angle1 + angle2 / 2) * 2 * 3.141592653589793D / 360.0D)),
					(int) (getHeight() / 2 - radius
							* Math.sin((angle1 + angle2 / 2) * 2 * 3.141592653589793D / 360.0D)));
		}
	}

	public void setModel(ChartModel newModel) {
		this.model = newModel;
		this.model.addActionListener(this);
	}

	public ChartModel getModel() {
		return this.model;
	}

	public void actionPerformed(ActionEvent e) {
		repaint();
	}
}
package cs355.view;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import cs355.PointOps;
import cs355.ViewSpaceTransform;
import cs355.controller.HandleHitTester;
import cs355.model.drawing.Line;

public class DrawableLine extends DrawableShape {
	
	
	private Line line;
	
	public DrawableLine(Line l) {
		super(l);
		line = l;
	}
	
	
	public void draw(Graphics2D g2d, boolean outline) {
		super.draw(g2d, outline);
		g2d.setStroke(DrawableShape.borderStroke);
		Point2D.Double off = PointOps.subtract(line.getEnd(), line.getCenter());
		if (outline) {
			double scale = ViewSpaceTransform.inst().getScale();
			g2d.setColor(borderColor);
			double s = HandleHitTester.HANDLE_SIZE / scale + scale/4;
			g2d.drawOval(-(int)(s/2), -(int)(s/2), (int)s, (int)s);
			g2d.drawOval((int)(off.x-s/2), (int)(off.y-s/2), (int)s, (int)s);
		}
		else {
			g2d.setColor(line.getColor());
			g2d.drawLine(0, 0, (int)off.x, (int)off.y);
		}
	}

}

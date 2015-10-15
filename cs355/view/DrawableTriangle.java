package cs355.view;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import cs355.PointOps;
import cs355.ViewSpaceTransform;
import cs355.controller.HandleHitTester;
import cs355.model.drawing.Triangle;

public class DrawableTriangle extends DrawableShape {
	
	Triangle triangle;
	
	public DrawableTriangle(Triangle t) {
		super(t);
		triangle = t;
	}

	@Override
	public void draw(Graphics2D g2d, boolean outline) {
		super.draw(g2d, outline);
		int[] xpoints = {(int)(triangle.getA().x), (int)(triangle.getB().x), (int)(triangle.getC().x)};
		int[] ypoints = {(int)(triangle.getA().y), (int)(triangle.getB().y), (int)(triangle.getC().y)};
		if (outline) {
			double scale = ViewSpaceTransform.inst().getScale();
			g2d.setColor(DrawableShape.borderColor);
			g2d.setStroke(DrawableShape.borderStroke);
			g2d.drawPolygon(xpoints, ypoints, 3);
			Point2D.Double n = PointOps.add(triangle.getA(), PointOps.scale(PointOps.normalize(triangle.getA()), HandleHitTester.HANDLE_DISPLACEMENT/scale));
			double s = HandleHitTester.HANDLE_SIZE / scale + scale / 4;
			g2d.drawOval((int)(n.x - s/2), (int)(n.y-s/2), (int)s, (int)s);
		}
		else {
			g2d.setColor(triangle.getColor());
			g2d.fillPolygon(xpoints, ypoints, 3);
		}
	}

}

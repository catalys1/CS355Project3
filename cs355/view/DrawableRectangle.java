package cs355.view;

import java.awt.Graphics2D;

import cs355.ViewSpaceTransform;
import cs355.controller.HandleHitTester;
import cs355.model.drawing.Rectangle;

public class DrawableRectangle extends DrawableShape {

	Rectangle rectangle;
	
	public DrawableRectangle(Rectangle r) {
		super(r);
		rectangle = r;
	}
	
	@Override
	public void draw(Graphics2D g2d, boolean outline) {
		super.draw(g2d, outline);
		double w = rectangle.getWidth();
		double h = rectangle.getHeight();
		if (outline) {
			double scale = ViewSpaceTransform.inst().getScale();
			g2d.setColor(DrawableShape.borderColor);
			g2d.setStroke(DrawableShape.borderStroke);
			g2d.drawRect((int)(-w/2), (int)(-h/2), (int)w, (int)h);
			double s = HandleHitTester.HANDLE_SIZE / scale + scale / 4;
			double dis = HandleHitTester.HANDLE_DISPLACEMENT / scale;
			g2d.drawOval(-(int)(s/2), -(int)(h/2+dis+s), (int)s, (int)s);
		}
		else {
			g2d.setColor(rectangle.getColor());
			g2d.fillRect((int)(-w/2), (int)(-h/2), (int)w, (int)h);
		}
	}

}

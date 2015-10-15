package cs355.view;

import java.awt.Graphics2D;

import cs355.ViewSpaceTransform;
import cs355.controller.HandleHitTester;
import cs355.model.drawing.Ellipse;

public class DrawableEllipse extends DrawableShape {
	
	Ellipse ellipse;
	
	public DrawableEllipse(Ellipse e) {
		super(e);
		ellipse = e;
	}

	@Override
	public void draw(Graphics2D g2d, boolean outline) {
		super.draw(g2d, outline);
		int width = (int)ellipse.getWidth();
		int height = (int)ellipse.getHeight();
		if (outline) {
			double scale = ViewSpaceTransform.inst().getScale();
			g2d.setColor(DrawableShape.borderColor);
			g2d.setStroke(DrawableShape.borderStroke);
			g2d.drawRect(-width/2, -height/2, width, height);
			double hs = HandleHitTester.HANDLE_SIZE / scale + scale / 4;
			double dis = HandleHitTester.HANDLE_DISPLACEMENT / scale;
			g2d.drawOval(-(int)(hs/2), -(int)(height/2+dis+hs), (int)hs, (int)hs);
		}
		else {
			g2d.setColor(ellipse.getColor());
			g2d.fillOval(-width/2, -height/2, width, height);
		}
	}

}

package cs355.view;

import java.awt.Graphics2D;

import cs355.ViewSpaceTransform;
import cs355.controller.HandleHitTester;
import cs355.model.drawing.Square;


public class DrawableSquare extends DrawableShape {
	
	private Square square;
	
	public DrawableSquare(Square s) {
		super(s);
		square = s;
	}

	@Override
	public void draw(Graphics2D g2d, boolean outline) {
		super.draw(g2d, outline);
		int size = (int)square.getSize();
		int off = size / 2;
		if (outline) {
			double scale = ViewSpaceTransform.inst().getScale();
			g2d.setColor(DrawableShape.borderColor);
			g2d.setStroke(DrawableShape.borderStroke);
			g2d.drawRect(-off, -off, size, size);
			double hs = (HandleHitTester.HANDLE_SIZE / scale + scale / 4);
			double dis = (HandleHitTester.HANDLE_DISPLACEMENT / scale);
			g2d.drawOval(-(int)hs/2, -(int)(off+dis+hs), (int)hs, (int)hs);
		}
		else {
			g2d.setColor(square.getColor());
			g2d.fillRect(-off, -off, size, size);
		}
	}

}

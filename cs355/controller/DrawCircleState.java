package cs355.controller;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import cs355.model.drawing.Circle;
import cs355.model.drawing.Model;
import cs355.view.ViewRefresher;

public class DrawCircleState extends ControllerState {
	
	private boolean keepShape;

	public DrawCircleState(Model m, ViewRefresher v, Color selCol) {
		super(m, v, selCol);
		keepShape = false;
	}

	@Override
	public void mousePressed(Double point) {
		clickPoint = point;
		activeShape = new Circle(selectedColor, clickPoint, 0);
		shapeIndex = model.addShape(activeShape);
	}

	@Override
	public void mouseDragged(Double point) {
		keepShape = true;
		Circle cir = (Circle)activeShape;
		double xdif = Math.abs(point.x - clickPoint.x);
		double ydif = Math.abs(point.y - clickPoint.y);
		double rad = xdif < ydif ? xdif / 2 : ydif / 2;
		cir.setRadius(rad);
		if (point.x < clickPoint.x) {
			if (point.y < clickPoint.y)
				cir.setCenter(new Point2D.Double(clickPoint.x-rad, clickPoint.y-rad));
			else 
				cir.setCenter(new Point2D.Double(clickPoint.x-rad, clickPoint.y+rad));
		}
		else if (point.y < clickPoint.y) {
			cir.setCenter(new Point2D.Double(clickPoint.x+rad, clickPoint.y-rad));
		}
		else {
			cir.setCenter(new Point2D.Double(clickPoint.x+rad, clickPoint.y+rad));
		}
		activeShape = cir;
		model.updateShape(activeShape);
	}

	@Override
	public void mouseReleased() {
		clickPoint = null;
		if (!keepShape) {
			model.deleteShape(shapeIndex);
		}
		keepShape = false;
	}
}

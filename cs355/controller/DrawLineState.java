package cs355.controller;

import java.awt.Color;
import java.awt.geom.Point2D.Double;

import cs355.model.drawing.Line;
import cs355.model.drawing.Model;
import cs355.view.ViewRefresher;

public class DrawLineState extends ControllerState {
	
	private boolean keepShape;

	public DrawLineState(Model m, ViewRefresher v, Color selCol) {
		super(m, v, selCol);
		keepShape = false;
	}

	@Override
	public void mousePressed(Double point) {
		clickPoint = point;
		activeShape = new Line(selectedColor, clickPoint, clickPoint);
		shapeIndex = model.addShape(activeShape);
	}

	@Override
	public void mouseDragged(Double point) {
		keepShape = true;
		((Line)activeShape).setEnd(point);
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

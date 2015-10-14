package cs355.controller;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import cs355.GUIFunctions;
import cs355.PointOps;
import cs355.model.drawing.Line;
import cs355.model.drawing.Model;
import cs355.model.drawing.Triangle;
import cs355.view.ViewRefresher;

public class SelectState extends ControllerState {
	
	private int handle;
	
	public SelectState(Model m, ViewRefresher v, Color selCol) {
		super(m,v,selCol);
		handle = 0;
	}

	@Override
	public void mousePressed(Double point) {
		clickPoint = point;
		System.out.println(clickPoint);
		
		// First do a hit test for the selected shapes handle
		if (activeShape != null) {
			if (activeShape instanceof Line) {
				handle = HandleHitTester.lineHandleHitTest((Line)activeShape, clickPoint, CLICK_TOLERANCE/2);
			}
			else {
				boolean hand = HandleHitTester.handleHitTest(activeShape, clickPoint, CLICK_TOLERANCE/2);
				if (hand) handle = 1;
			}
		}
		// If no handle was activated, do a hit test on each shape.
		// If a shape was hit, get the shape information from the model. If no shape was hit,
		// make sure everything is reset
		if (handle == 0) {
			int index = model.hitTest(clickPoint, CLICK_TOLERANCE);
			if (index >= 0) {
				shapeIndex = index;
				activeShape = model.getShape(shapeIndex);
				GUIFunctions.changeSelectedColor(activeShape.getColor());
				view.update(null, activeShape);
			}
			else {
				activeShape = null;
				shapeIndex = -1;
				GUIFunctions.changeSelectedColor(selectedColor);
				view.update(null, new Object());
			}
		}		
	}

	@Override
	public void mouseDragged(Point2D.Double point) {
		if (activeShape != null) {
			Point2D.Double cpoint = point;
			if (handle > 0) {
				moveHandle(cpoint);
			}
			else {
				Point2D.Double delta = PointOps.subtract(cpoint, clickPoint);
				clickPoint = cpoint;
				activeShape.setCenter(PointOps.add(activeShape.getCenter(), delta));
				if (activeShape instanceof Line) {
					Line l = (Line)activeShape;
					l.setEnd(PointOps.add(l.getEnd(), delta));
				}
			}
			model.update();
			view.update(null, activeShape);
		}
	}

	@Override
	public void mouseReleased() {
		handle = 0;
		clickPoint = null;
	}
	
	private void moveHandle(Point2D.Double p) {
		if (activeShape instanceof Line) {
			Line l = (Line)activeShape;
			Point2D.Double delta = PointOps.subtract(p, clickPoint);
			clickPoint = p;
			if (handle == 1) {
				l.setCenter(PointOps.add(l.getCenter(), delta));
			}
			else if (handle == 2) {
				l.setEnd(PointOps.add(l.getEnd(), delta));
			}
		}
		else {
			Point2D.Double obj = new Point2D.Double();
			AffineTransform t = new AffineTransform();
			t.rotate(-activeShape.getRotation());
			t.translate(-activeShape.getCenter().x, -activeShape.getCenter().y);
			t.transform(p, obj);
			double theta = Math.atan2(obj.x, -obj.y);
			double rotation = 0;
			if (activeShape instanceof Triangle) {
				Triangle triangle = (Triangle)activeShape;
				Point2D.Double n = PointOps.add(triangle.getA(), PointOps.scale(PointOps.normalize(triangle.getA()), 20));
				double y = n.y;
				double x = n.x;
				double phi = Math.atan2(x, -y);
				rotation = activeShape.getRotation() + theta - phi;
			}
			else {
				rotation = activeShape.getRotation() + theta;
			}
			if (rotation > TWO_PI) rotation -= TWO_PI;
			else if (rotation < TWO_PI) rotation += TWO_PI;
			activeShape.setRotation(rotation);
		}
	}
	
	public void changeColor(Color c) {
		super.changeColor(c);
		if (activeShape != null) {
			activeShape.setColor(selectedColor);
			model.update();
		}
	}

	@Override
	public void moveForward() {
		if (shapeIndex > -1 && activeShape != null) {
			model.moveForward(shapeIndex);
			shapeIndex += shapeIndex < model.getNumShapes()-1 ? 1 :0;
		}
	}

	@Override
	public void moveBackward() {
		if (shapeIndex > -1 && activeShape != null) {
			model.moveBackward(shapeIndex);
			shapeIndex -= shapeIndex > 0 ? 1 :0;
		}
	}

	@Override
	public void moveToFront() {
		if (shapeIndex > -1 && activeShape != null) {
			model.moveToFront(shapeIndex);
			shapeIndex = model.getNumShapes()-1;shapeIndex += shapeIndex < model.getNumShapes()-1 ? 1 :0;
		}
	}

	@Override
	public void moveToBack() {
		if (shapeIndex > -1 && activeShape != null) {
			model.movetoBack(shapeIndex);
			shapeIndex = 0;
		}
	}
	
	@Override
	public boolean deleteShape() {
		if (shapeIndex > -1 && activeShape != null) {
			model.deleteShape(shapeIndex);
			shapeIndex = -1;
			activeShape = null;
			return true;
		}
		return false;
	}

}

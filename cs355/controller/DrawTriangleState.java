package cs355.controller;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import cs355.model.drawing.Model;
import cs355.model.drawing.Triangle;
import cs355.view.ViewRefresher;

public class DrawTriangleState extends ControllerState {
	
	private enum TriPoint {
		A, B, C
	}

	private TriPoint pointNum;
	private Point2D.Double b;
	private Point2D.Double c;
	
	public DrawTriangleState(Model m, ViewRefresher v, Color selCol) {
		super(m, v, selCol);
		pointNum = TriPoint.A;
		b = null;
		c = null;
	}

	@Override
	public void mousePressed(Double point) {
		switch (pointNum) {
		case A:
			clickPoint = point; 
			pointNum = TriPoint.B;
			break;
		case B:
			b = point; 
			pointNum = TriPoint.C;
			break;
		case C:
			c = point;
			activeShape = new Triangle(selectedColor, clickPoint, clickPoint, b, c);
			completeTriangle();
			model.addShape(activeShape);
			pointNum = TriPoint.A;
			break;
		}
	}
	
	private void completeTriangle() {
		Triangle tri = (Triangle)activeShape;
		Point2D.Double a = tri.getA();
		Point2D.Double b = tri.getB();
		Point2D.Double c = tri.getC();
		double cx = (a.x + b.x + c.x) / 3;
		double cy = (a.y + b.y + c.y) / 3; 
		Point2D.Double center = new Point2D.Double(cx, cy);
		a = new Point2D.Double(a.x-cx, a.y-cy);
		b = new Point2D.Double(b.x-cx, b.y-cy);
		c = new Point2D.Double(c.x-cx, c.y-cy);
		tri.setCenter(center);
		tri.setA(a);
		tri.setB(b);
		tri.setC(c);
		activeShape = tri;
	}

	@Override
	public void mouseDragged(Double point) {
	}

	@Override
	public void mouseReleased() {
	}
}

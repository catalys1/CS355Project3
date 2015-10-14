package cs355.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
//import cs355.model.drawing.Shape;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import cs355.ViewSpaceTransform;
import cs355.model.drawing.Shape;

public class DrawableShape {
	
	public static Color borderColor = Color.red;
	public static Stroke borderStroke = new BasicStroke(2);
	
	private Shape shape;
	
	public DrawableShape(Shape s) {
		shape = s;
	}

	public void draw(Graphics2D g2d, boolean outline) {
		Point2D.Double c = shape.getCenter();
		double t = shape.getRotation();
		double cos = Math.cos(t);
		double sin = Math.sin(t);
		
//		AffineTransform objRot = new AffineTransform(cos, sin, -sin, cos, 0, 0);
//		AffineTransform objToWorld = new AffineTransform(1,0,0,1,c.x,c.y);
//		objToWorld.concatenate(objRot);
		
//		AffineTransform objToWorld = new AffineTransform(cos, sin, -sin, cos, c.x, c.y);
		AffineTransform objToWorld = new AffineTransform();
		
		objToWorld.translate(shape.getCenter().x, shape.getCenter().y);
		objToWorld.rotate(shape.getRotation());
		AffineTransform objToView = ViewSpaceTransform.inst().transformToViewSpace(objToWorld);
//		g2d.setTransform(objToWorld);
		g2d.setTransform(objToView);
	}
	
	
}

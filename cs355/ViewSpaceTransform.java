package cs355;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;


public class ViewSpaceTransform {
	
	private static ViewSpaceTransform instance;
	
	private int viewOrigX;
	private int viewOrigY;
	private double scale;
	
	
	public static ViewSpaceTransform inst() {
		if (instance == null) {
			return null;
		}
		return instance;
	}
	
	public static void createViewSpaceTransform() {
		instance = new ViewSpaceTransform();
	}

	private ViewSpaceTransform() {
		viewOrigX = 0;
		viewOrigY = 0;
		scale = 1;
	}
	
	public void setOrigin(int x, int y) {
		viewOrigX = x;
		viewOrigY = y;
	}
	
	public void setScale(double s) {
		scale = s;
	}
	
	public int getX() {
		return viewOrigX;
	}
	
	public int getY() {
		return viewOrigY;
	}
	
	public double getScale() {
		return scale;
	}
	
	public AffineTransform transformToViewSpace(AffineTransform objToWorld) {
		AffineTransform worldToView = new AffineTransform(scale,0,0,scale,-scale*viewOrigX,-scale*viewOrigY);
		worldToView.concatenate(objToWorld);
		return worldToView;
		
//		OLD CODE		
//		AffineTransform worldToView = new AffineTransform();
//		worldToView.scale(scale, scale);
//		worldToView.translate(-viewOrigX, -viewOrigY);
	}
	
	public Point2D.Double fromViewToWorld(Point2D.Double viewPt) {
		double s = 1/scale;
		AffineTransform viewToWorld = new AffineTransform(s,0,0,s,viewOrigX,viewOrigY);
		Point2D.Double worldPt = new Point2D.Double();
		viewToWorld.transform(viewPt, worldPt);
		return worldPt;
		
//		OLD CODE		
//		AffineTransform viewToWorld = new AffineTransform();
//		viewToWorld.translate(viewOrigX, viewOrigY);
//		viewToWorld.scale(s, s);
	}

}

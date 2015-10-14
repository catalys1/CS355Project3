package cs355;

import java.awt.geom.AffineTransform;


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
		scale = 0.25*4;
	}
	
	public void setOrigin(int x, int y) {
		viewOrigX = x;
		viewOrigY = y;
	}
	
	public void setScale(double s) {
		scale = s*4;
	}
	
	public AffineTransform transformToViewSpace(AffineTransform objToWorld) {
//		AffineTransform trans = new AffineTransform(1,0,0,1,-(int)viewOrigX,-(int)viewOrigY);
//		AffineTransform worldToView = new AffineTransform(scale*4,0,0,scale*4,0,0);
//		worldToView.concatenate(trans);
		
//		AffineTransform worldToView = new AffineTransform(scale,0,0,scale,-scale*viewOrigX,-scale*viewOrigY);
		AffineTransform worldToView = new AffineTransform();
		worldToView.scale(scale, scale);
		worldToView.translate(-viewOrigX, -viewOrigY);
		worldToView.concatenate(objToWorld);
		return worldToView;
	}

}

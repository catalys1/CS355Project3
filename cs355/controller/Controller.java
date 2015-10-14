package cs355.controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.Iterator;

import cs355.GUIFunctions;
import cs355.PointOps;
import cs355.ViewSpaceTransform;
import cs355.model.drawing.*;
import cs355.view.View;
import cs355.view.ViewRefresher;


/**
 * Controller class. Handles interfacing with the user. Stores relevant GUI values
 * 
 */
public class Controller implements CS355Controller {
	
	public static final double MAX_ZOOM_IN = 4;
	public static final double MAX_ZOOM_OUT= 0.25;
	public static final int VIEW_SIZE= 2048;
	
	// Data members
	private Model model;				// Reference to the model
	private Color selectedColor;		// The current drawing color
	private ControllerState state;		// Current state of the controller
	private ViewRefresher view;			// A reference to the view
	private double zoomFactor;			// Zoom factor of the view space
	
	private int viewOrigX;
	private int viewOrigY;
	private boolean manualUpdate;
	
	
	public Controller(Model m, View v) {
		model = m;
		manualUpdate = false;
		selectedColor = Color.white;
		state = null;
		view = v;
		viewOrigX = 0;
		viewOrigY = 0;
		zoomFactor = 1;
		
		ViewSpaceTransform.createViewSpaceTransform();
	}
	
	public void init() {
		int knob = (int)(VIEW_SIZE/(zoomFactor*4));
		int pos = VIEW_SIZE/2 - knob/2;
		viewOrigX = pos;
		viewOrigY = pos;
		GUIFunctions.setHScrollBarKnob(knob);
		GUIFunctions.setVScrollBarKnob(knob);
		GUIFunctions.setHScrollBarPosit(pos);
		GUIFunctions.setVScrollBarPosit(pos);
		ViewSpaceTransform.inst().setScale(zoomFactor);
		ViewSpaceTransform.inst().setOrigin(viewOrigX, viewOrigY);
	}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		if (state != null) {
			Point2D.Double p = ViewSpaceTransform.inst().fromViewToWorld(PointOps.toDouble(arg0.getPoint()));
			state.mousePressed(p);
		}
		else
			test();
	}
	
	private void test() {
		model.addShape(new Square(selectedColor, new Point2D.Double(1024, 1024),100));
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		if (state != null) {
			Point2D.Double p = ViewSpaceTransform.inst().fromViewToWorld(PointOps.toDouble(arg0.getPoint()));
			state.mouseDragged(p);
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (state != null) {
			state.mouseReleased();
		}
	}
	
	
	@Override
	public void colorButtonHit(Color c) {
		selectedColor = c;
		GUIFunctions.changeSelectedColor(c);
		state.changeColor(selectedColor);
	}

	@Override
	public void lineButtonHit() {
		state = new DrawLineState(model, view, selectedColor);
		view.update(null, new Object());
	}

	@Override
	public void squareButtonHit() {
		state = new DrawSquareState(model, view, selectedColor);
		view.update(null, new Object());
	}

	@Override
	public void rectangleButtonHit() {
		state = new DrawRectangleState(model, view, selectedColor);
		view.update(null, new Object());
	}

	@Override
	public void circleButtonHit() {
		state = new DrawCircleState(model, view, selectedColor);
		view.update(null, new Object());
	}

	@Override
	public void ellipseButtonHit() {
		state = new DrawEllipseState(model, view, selectedColor);
		view.update(null, new Object());
	}

	@Override
	public void triangleButtonHit() {
		state = new DrawTriangleState(model, view, selectedColor);
		view.update(null, new Object());
	}

	@Override
	public void selectButtonHit() {
		state = new SelectState(model, view, selectedColor);
	}

	@Override
	public void zoomInButtonHit() {
		if (zoomFactor < MAX_ZOOM_IN) {
			zoomFactor *= 2;
			int knob = (int)(VIEW_SIZE/(zoomFactor*4));
			viewOrigX += knob/2;
			viewOrigY += knob/2;
			System.out.println(String.format("Ox: %d, Oy: %d", viewOrigX, viewOrigY));
			updateScrollBars(knob);
			ViewSpaceTransform.inst().setScale(zoomFactor);
			ViewSpaceTransform.inst().setOrigin(viewOrigX, viewOrigY);
			view.update(null, null);
		}
		else {
			zoomFactor = MAX_ZOOM_IN;
		}
	}

	@Override
	public void zoomOutButtonHit() {
		if (zoomFactor > MAX_ZOOM_OUT) {
			zoomFactor /= 2;
			int knob = (int)(VIEW_SIZE/(zoomFactor*4));
			viewOrigX -= knob/4;
			viewOrigY -= knob/4;
			System.out.println(String.format("Ox: %d, Oy: %d", viewOrigX, viewOrigY));
			if (viewOrigX < 0) viewOrigX = 0;
			else if (viewOrigX + knob > VIEW_SIZE) viewOrigX = VIEW_SIZE - knob;
			if (viewOrigY < 0) viewOrigY = 0;
			else if (viewOrigY + knob > VIEW_SIZE) viewOrigY = VIEW_SIZE - knob;
			updateScrollBars(knob);
			ViewSpaceTransform.inst().setScale(zoomFactor);
			ViewSpaceTransform.inst().setOrigin(viewOrigX, viewOrigY);
			view.update(null, null);
		}
		else {
			zoomFactor = MAX_ZOOM_OUT;
		}
	}
	
	private void updateScrollBars(int knob) {
		manualUpdate = true;
		GUIFunctions.setVScrollBarPosit(0);
		GUIFunctions.setHScrollBarPosit(0);
		GUIFunctions.setVScrollBarKnob(knob);
		GUIFunctions.setHScrollBarKnob(knob);
		GUIFunctions.setVScrollBarPosit(viewOrigY);
		GUIFunctions.setHScrollBarPosit(viewOrigX);
		manualUpdate = false;
	}
	
	@Override
	public void hScrollbarChanged(int value) {
		if (!manualUpdate) {
			viewOrigX = value;
			ViewSpaceTransform.inst().setOrigin(viewOrigX, viewOrigY);
			view.update(null, null);
		}
		
//		System.out.println("x: " + value);
	}

	@Override
	public void vScrollbarChanged(int value) {
		if (!manualUpdate) {
			viewOrigY = value;
			ViewSpaceTransform.inst().setOrigin(viewOrigX, viewOrigY);
			view.update(null, null);
		}
//		System.out.println("y: " + value);
	}

	@Override
	public void openScene(File file) {

	}

	@Override
	public void toggle3DModelDisplay() {

	}

	@Override
	public void keyPressed(Iterator<Integer> iterator) {

	}

	@Override
	public void openImage(File file) {

	}

	@Override
	public void saveImage(File file) {

	}

	@Override
	public void toggleBackgroundDisplay() {

	}

	@Override
	public void saveDrawing(File file) {
		boolean saved = model.save(file);
		if (!saved) {
			System.out.println("Failed to save drawing");
		}
	}

	@Override
	public void openDrawing(File file) {
		boolean opened = model.open(file);
		if (!opened) {
			System.out.println("Failed to open drawing");
		}
	}

	@Override
	public void doDeleteShape() {
		if (state.deleteShape())
			view.update(null, new Object());
	}

	@Override
	public void doEdgeDetection() {

	}

	@Override
	public void doSharpen() {

	}

	@Override
	public void doMedianBlur() {

	}

	@Override
	public void doUniformBlur() {

	}

	@Override
	public void doGrayscale() {

	}

	@Override
	public void doChangeContrast(int contrastAmountNum) {

	}

	@Override
	public void doChangeBrightness(int brightnessAmountNum) {

	}

	@Override
	public void doMoveForward() {
		state.moveForward();
	}

	@Override
	public void doMoveBackward() {
		state.moveBackward();
	}

	@Override
	public void doSendToFront() {
		state.moveToFront();
	}

	@Override
	public void doSendtoBack() {
		state.moveToBack();
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {}
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mouseMoved(MouseEvent arg0) {}


}

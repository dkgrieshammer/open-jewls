

import java.awt.event.MouseEvent;

import controlP5.ControlEvent;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PMatrix2D;
import wblut.core.processing.WB_Render;
import wblut.geom.core.WB_Transform;
import wblut.hemesh.core.HE_Mesh;
import wblut.hemesh.core.HE_Vertex;
import wblut.hemesh.creators.HEC_Geodesic;
import wblut.hemesh.creators.HEC_Grid;
import wblut.hemesh.modifiers.HEM_Extrude;
import wblut.hemesh.modifiers.HEM_Lattice;
import wblut.hemesh.modifiers.HEM_Wireframe;
import wblut.hemesh.tools.HET_Export;
import wblut.hemesh.tools.HET_Selector;


public class Jewls extends PApplet {

	WB_Render myRender; //Renderengine
	PFont myFont;
	PImage myLogo;
	
	Gui myGui; //controlP5
	
	Ring2 myRing; //object
	
	String myMaterial;
	//polytest
//	HEC_HexaTube myHex;//lets see
//	HE_Mesh myHexMesh;
	
	HEM_Extrude myExtrude; //modifier
	
	double rotAngle = 0.01;
	
	double myTransform = 0;
	
	boolean exportSTL = false;
	
	//view rotation values
	float rotX = radians(135);
	float rotY = 0;
	//view relative rotation management
	float tempObjRotX = 0; //Object rotation on drag start
	float tempObjRotY = 0;
	float tempMouseRotX = 0; //absolute rotation if we would not use relative rot.
	float tempMouseRotY = 0;
	float tempRotX = 0; //live rotation to calculate relative rotation change (delta)
	float tempRotY = 0;
	
	
	
	public void setup() {
		size(1024, 768,OPENGL);
		fill(120);
		myLogo = loadImage("assets/Logo1.png");
		
//		myFont = root.createFont("assets/miso-regular.ttf",16);
//		root.textFont(myFont);
		
		
		myRing = new Ring2(this);
		
		myGui = new Gui(this);
		
//		myHex = new HEC_HexaTube(); //someday i will do hex
//		myHexMesh = new HE_Mesh(myHex);
		
		//activate material
		myGui.materialButtons.activate(5);
		
		myRender = new WB_Render(this);
	}

	public void draw() {
		background(100);
		image(myLogo, 10, 20);
		//no pop and pull matrix available since the mesh-selector uses the whole stack!

		myGui.drawGui();
		
//		if(exportSTL == true) {
////			noLoop();
//			int sec = second();
//			int min = minute();
//			int hor = hour();
//			int day = day();
//			int month = month();
//			int year = year();
//			save("ring-" + myMaterial + "_" + day + "_" + hor + "_" + min + "_" + sec +".jpg");
//			HET_Export.saveToSTL(myRing.myMesh, sketchPath("ring-" + myMaterial + "_" + day + "_" + hor + "_" + min + "_" + sec +".stl"), 0.5);
//			println("file saved sucessfully");
//			
//			this.exportSTL = false;
////			loop();
//		}
		
//		lights();
		if(myMaterial == "silver" || myMaterial == "gold") {
			directionalLight(255, 255, 255, 1, 1, -1);
			directionalLight(255, 255, 255, -1, -1, 1);
		}else{
			lights();
			lightSpecular(500, 20, -1);
		}
		translate(width/2, height/2, 500);
		rotateView();
		
		myRing.draw();
		
		
		
		if(exportSTL == true) {
//			noLoop();
			int sec = second();
			int min = minute();
			int hor = hour();
			int day = day();
			int month = month();
			int year = year();
			save("ring-" + myMaterial + "_" + day + "_" + hor + "_" + min + "_" + sec +".png");
			HET_Export.saveToSTL(myRing.myMesh, sketchPath("ring-" + myMaterial + "_" + day + "_" + hor + "_" + min + "_" + sec +".stl"), 0.5);
			println("file saved sucessfully");
			
			this.exportSTL = false;
//			loop();
		}
//		rotAngle += 0.001;
//		stroke(240,100,230);
//		fill(240,100,230);
//		myRender.drawEdges(myHexMesh);
//		myRender.drawFaces(myHexMesh);
		
	}
	
	public void changeSize(double sizer) {
		myTransform = sizer;

	}
	
	public void rotateView() {
		//check if mouse is over controls
//		if(myGui.controlP5.controlWindow.isMouseOver()) {
//			//do nothing
//		}else{
//			if(mousePressed == true && myRing.mySelector.lastKey() == null) {
//				//view Rotate
//				rotY = (mouseX*1.0f/width*TWO_PI);
//				rotX = (mouseY*1.0f/height*TWO_PI);
//			}
//		}
		//remain with current rotation
		rotateX(rotX);
		rotateY(rotY);
	}
	
/*
 * MOUSE EVENTS
 */
	
	public void mouseClicked() {

	}	
	public void mousePressed() {
		//save values for relativeRotations
		tempObjRotX = rotX;
		tempObjRotY = rotY;
		tempMouseRotY = (mouseX * 1.0f/width * TWO_PI);
		tempMouseRotX = (mouseY * 1.0f/height * TWO_PI);
		
		if(myRing.mySelector.lastKey() != null) {

			myRing.splitFace(myRing.mySelector.lastKey()); //
		}
	}
	public void mouseDragged() {
		
		if(myGui.controlP5.controlWindow.isMouseOver()) {
			//do nothing
		}else{
			if(myRing.mySelector.lastKey() == null) {
				//view Rotate - deprecated original
//				rotY = ((mouseX) * 1.0f/width * TWO_PI);
//				rotX = ((mouseY) * 1.0f/height * TWO_PI);
				
				//finally relative rotation
				
				tempRotY = (mouseX * 1.0f/width * TWO_PI); 
				tempRotX = (mouseY * 1.0f/height * TWO_PI);
				rotY = tempObjRotY + (tempRotY - tempMouseRotY);
				rotX = tempObjRotX + (tempRotX - tempMouseRotX);
				
			}
		}
	}
	
	public void mouseReleased() {

		if(myGui.controlP5.controlWindow.isMouseOver()) {
			//do nothing
		}else{
			if(myRing.mySelector.lastKey() == null) {
				
			}
		}
	}
	
	public void controlEvent (ControlEvent theEvent) {
		myGui.controlEvent(theEvent);
	}
	
	
//save files
	public void saveStls() {
//		noLoop();

		HET_Export.saveToSTL(myRing.myMesh, sketchPath("ring.stl"), 1);
		println("file saved sucessfully");
//		loop();
	}
	
//applet method
	public static void main(String args[]) {
//		    PApplet.main(new String[] { "--present", "Jewls" });
	    PApplet.main(new String[] { "Jewls" });
	    
	}
	
}

/**
 * 
 */


import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.tools.ant.taskdefs.Sync.MyCopy;

import com.sun.tools.doclets.internal.toolkit.resources.doclets;

import wblut.core.processing.WB_Render;
import wblut.geom.core.WB_Line;
import wblut.geom.core.WB_Plane;
import wblut.geom.core.WB_Point2d;
import wblut.geom.core.WB_Point3d;
import wblut.geom.core.WB_Voronoi2D;
import wblut.geom.nurbs.WB_BSpline;
import wblut.geom.nurbs.WB_BSplineSurface;
import wblut.geom.nurbs.WB_RBSpline;
import wblut.geom.nurbs.WB_RBSplineSurface;
import wblut.geom.nurbs.WB_SwungSurface;
import wblut.hemesh.composite.HE_DynamicMesh;
import wblut.hemesh.core.HE_Face;
import wblut.hemesh.core.HE_Mesh;
import wblut.hemesh.core.HE_Selection;
import wblut.hemesh.creators.HEC_FromFrame;
import wblut.hemesh.creators.HEC_FromSurface;
import wblut.hemesh.creators.HEC_FromVoronoiCells;
import wblut.hemesh.creators.HEC_Grid;
import wblut.hemesh.creators.HEC_Tube;
import wblut.hemesh.creators.HEC_VoronoiCell;
import wblut.hemesh.creators.HEMC_VoronoiCells;
import wblut.hemesh.creators.HEMC_VoronoiSpheres;
import wblut.hemesh.modifiers.HEM_Bend;
import wblut.hemesh.modifiers.HEM_Extrude;
import wblut.hemesh.modifiers.HEM_Lattice;
import wblut.hemesh.modifiers.HEM_SliceSurface;
import wblut.hemesh.modifiers.HEM_Smooth;
import wblut.hemesh.modifiers.HEM_SmoothInset;
import wblut.hemesh.modifiers.HEM_Wireframe;
import wblut.hemesh.subdividors.HES_CatmullClark;
import wblut.hemesh.subdividors.HES_Planar;
import wblut.hemesh.subdividors.HES_Smooth;
import wblut.hemesh.subdividors.HES_Subdividor;
import wblut.hemesh.tools.HET_Selector;

/**
 * @author david
 *
 */
public class Ring2 {

	//ROOt app
	Jewls root;
	
	int fillColor;
	int strokeColor;
	
	//the baseform
//	HEC_Tube myTube;
	HEC_ShiftTube myTube;
	
	//the mesh class
	public HE_Mesh myMesh;
	public HE_Mesh voronoiMesh;
	public HEMC_VoronoiCells myVoronoi;
//	public HE_DynamicMesh myDynMesh; out of use so far
	
	public HES_CatmullClark myCatmull;
//	public HEM_Wireframe myWireframe;
	public HET_Selector mySelector;
	
//	public HEM_Lattice myLattice; //testweise
//	public HE_Selection mySelection; //testweise
//	public int[] saveSelected; //Lattice Meshes testweise
//	public ArrayList<Integer> selectionList; //testweise
	
	public HEM_Bend myBender;
	public WB_Line myBendLine;
	public WB_Plane myBendPlane;
	
	//SIZE SETTING VARS !
	public float innerRMax = 35;
	public float innerRMin = 6;
//	public float outerRMax = innerRMax + 15;
//	public float outerRMin = innerRMin + 2;
	public float outerRMax = 15;
	public float outerRMin = 1;
	public float heightMax = 30;
	public float heightMin = 3; //w�re 3mm?
	//INIT SIZES 
	public float innerR = 24;
	public float outerR = 3;
	public float height = 10;
	
	public int steps = 2;
	public int facets = 20;
	public double shiftX = 1;
	public double shiftY = 1;
	public double bend = 0;
	//old vars
//	public double innerR = 60;
//	public double outerR = 100;
//	public double height = 100;
//	public int steps = 2;
//	public int facets = 10;
//	public double shiftX = 0;
//	public double shiftY = 0;
//	public double bend = 0;
	
	
	public double shear = 0;
	public double shearPI = 1;
	
	public double expandModX = 0;
	public double expandModXPI = 1;
	public double expandModY = 0;
	public double expandModYPI = 1;
	
	//Constructor
	public Ring2(Jewls parent) {
		root = parent;
		
		fillColor = root.color(255,255,10);
		strokeColor = root.color(200, 200, 0);
//		myTube = new HEC_Tube().setInnerRadius(innerR).setOuterRadius(outerR).setFacets(facets).setSteps(steps).shiftX(shiftX).shiftY(shiftY).setHeight(height); org version
		
		myTube = new HEC_ShiftTube().setInnerRadius(innerR).setOuterRadius(outerR * 2 + innerR).setFacets(facets).setSteps(steps).shiftX(shiftX).shiftY(shiftY).setHeight(height);
		myTube.setShear(shear).setShearPi(shearPI).setExpandMod(expandModX, expandModY).setExpandModPi(expandModXPI, expandModXPI); //new modifiers !!!setTwist needs to be called the last!!

		myCatmull = new HES_CatmullClark();
//		myWireframe = new HEM_Wireframe().setStrutRadius(3).setStrutFacets(3).setAngleOffset(0.5).setTaper(false);
		
		mySelector = new HET_Selector(root);
		
		myBendLine = new WB_Line(100, 0, 0, 100, -100, 0);
		myBendPlane = new WB_Plane(0, -100, 0, 0, 1, 0);
		myBender = new HEM_Bend().setBendAxis(myBendLine).setAngleFactor(0.6);
		myBender.setGroundPlane(0, 0, -200, 0, 0, -1);
		
		//first mesh creation
		initMesh();
		
//		selectionList = new ArrayList<Integer>();
//		myLattice = new HEM_Lattice().setWidth(3).setDepth(3).setThresholdAngle(0.5).setFuse(true).setFuseAngle(0.5);//testweise
//		mySelection = new HE_Selection(myMesh); //testweise
//		myMesh.modifySelected(myLattice, mySelection);
		
	}
	
	public void draw() {
		update();
		root.fill(fillColor);
		root.stroke(strokeColor);
		root.myRender.drawEdges(myMesh);
		
//		root.stroke(30, 200, 60);
//		root.myRender.drawBoundaryEdges(myMesh);
//		if(voronoiMesh != null) {
//			root.stroke(30, 200, 60);
//			root.myRender.drawEdges(voronoiMesh.splitEdges());
////			root.myRender.drawBoundaryEdges(voronoiMesh);
//			
//		}
//		root.myRender.draw(myBendLine, 500);
//		root.myRender.draw(myBendPlane, 100);
		
		root.noStroke();
		root.myRender.drawFaces(mySelector, myMesh);
		
//		root.fill(100,100,200);
//		root.myRender.drawFaces(10,myMesh);
		//draw selected face
		if(mySelector.lastKey() != null) {
			root.fill(strokeColor);
			root.myRender.drawFace(mySelector.lastKey(),myMesh);
//			root.myRender.drawFaces(mySelection);
		}
	}
	
	public void update () {
//		root.println(mySelector.lastKey());
	}
	
	public void changeColor(int fill, int stroke) {
		this.fillColor = fill;
		this.strokeColor = stroke;
	}

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//Regular Modifiers

	public void changeHeight(double value) {
		this.height = (float) value;
		myTube.setHeight(value);
		updateMesh();
	}
	
	public void changeInnerR (double value) {
		this.innerR = (float) value;
		myTube.setInnerRadius(value);
		updateOuterR();
		updateMesh();
	}
	public void changeOuterR (double value) {
		this.outerR = (float) value * 2;
		//changed testwise
		myTube.setOuterRadius(this.innerR + value * 2);
//		myTube.setOuterRadius(value);
		updateMesh();
	}
	public void updateOuterR () {
		myTube.setOuterRadius(this.innerR + this.outerR);
	}
	
	public void changeShiftX (double value) {
		myTube.shiftX(value);
		updateMesh();
	}
	
	public void changeShiftY (double value) {
		myTube.shiftY(value);
		updateMesh();
	}

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//Special Modifiers
	
	//new Modifiers
	public void addShear(double value) {
		myTube.setShear(value);
		updateMesh();
	}
	public void changeShearPI (double value) {
		myTube.setShearPi(value);
		updateMesh();
	}
	public void expandFormX(double value) {
		myTube.setExpandModX(value);
		updateMesh();
	}
	public void expandFormXPI(double value) {
		myTube.setExpandModXPi(value);
		updateMesh();
	}
	public void expandFormY(double value) {
		myTube.setExpandModY(value);
		updateMesh();
	}
	public void expandFormYPI(double value) {
		myTube.setExpandModYPi(value);
		updateMesh();
	}
	
	public void setEffectAmount(double value) {
		myTube.setEffectAmount(value);
		updateMesh();
	}
	
	public void setTwist(boolean tSwich) {

		myTube.setTwist(tSwich);

		updateMesh();
	}
	public void setChaos(boolean cSwitch) {
		myTube.setChaos(cSwitch);
		updateMesh();
	}
	public void setChaos_2(boolean cSwitch) {
		myTube.setChaos_2(cSwitch);
		updateMesh();
	}
	
// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//BUTTONS
	
	public void addCatmull() {
		myCatmull.setBlendFactor(1);
		myCatmull.setKeepBoundary(false);
		myCatmull.setKeepEdges(false);
		myCatmull.apply(myMesh);
//		HES_Smooth modifier = new HES_Smooth();
//		modifier.setKeepBoundary(true);
//		modifier.apply(myMesh);
	}
	
	public void addWireframe() {
		HEM_Wireframe modifier = new HEM_Wireframe().setStrutRadius(6).setStrutFacets(5).setAngleOffset(30).setTaper(false).setMaximumStrutOffset(2);
//		modifier.setCap(false);
//		myWireframe = new HEM_Wireframe().setStrutRadius(3).setStrutFacets(3).setAngleOffset(0.5).setTaper(false);
		
		myMesh.modify(modifier);
	}
	
	public void addVoronoi() {
		HE_Mesh[] cells;
		  int numpoints=50;
		  int N = 10;
		  float[][] points=new float[numpoints][3];
		  for(int i=0;i<numpoints;i++) {
		    points[i][0]=root.random(-100,100);
		    points[i][1]=root.random(-100,100);
		    points[i][2]=root.random(-100,100);
		  }
		  boolean[] active = new boolean[N];
		  for (int i = 0; i < N; i++) {
			active[i] = true;
		  }
		  HEMC_VoronoiCells myVoronoi = new HEMC_VoronoiCells();
		  HEMC_VoronoiSpheres myVoronoiSheres = new HEMC_VoronoiSpheres();
		  
		  myVoronoi.setPoints(points);
		  myVoronoi.setN(N);
		  myVoronoi.setContainer(myMesh);
//		  myVoronoi.setMesh(myMesh, false);
		  myVoronoi.setSimpleCap(false);
		  myVoronoi.setSurface(false);
		  myVoronoi.setCreateSkin(false);
		  myVoronoi.setUseDummy(false);
		  myVoronoi.setOffset(0);
		  
		  cells = myVoronoi.create();

		  HEC_FromVoronoiCells creator = new HEC_FromVoronoiCells();
		  creator.setCells(cells);
		  creator.setActive(active);
		  
//		  voronoiMesh = new HE_Mesh(creator);
		  myMesh = new HE_Mesh(creator);
	}
	
	public void addRandom() {
		HES_Planar modifier = new HES_Planar();
		modifier.setRandom(true).setRange(10).setSeed(facets).setKeepTriangles(false).apply(myMesh);
		
	}
	
	public void addStruct() {
		HES_CatmullClark modifier = new HES_CatmullClark();
		modifier.setKeepBoundary(true);
		modifier.setKeepEdges(true);
		modifier.setBlendFactor(2);
		modifier.apply(myMesh);
//		myMesh.modify(modifier);
	}
	
	//not working jet
	public void latticeVoronoi() {
//		voronoiMesh.selectAllBoundaryEdges();
//		root.println(voronoiMesh.selectAllBoundaryEdges());
		HE_Selection mySelect = new HE_Selection(myMesh);
		Iterator<HE_Face> fltr = voronoiMesh.selectAllBoundaryEdges().fItr();
		HE_Face f;
		while (fltr.hasNext()) {
			f = fltr.next();
			mySelect.add(f);
		}
		root.println(mySelect);
		
//		HE_Selection peter = voronoiMesh.selectAllBoundaryEdges();
		HEM_Lattice lPeter = new HEM_Lattice().setWidth(3).setDepth(3).setThresholdAngle(0.5).setFuse(true).setFuseAngle(0.5);
		myMesh.modifySelected(lPeter, mySelect);
	}
	
	public void addLattice() {
//		HEM_Lattice myLattice = new HEM_Lattice().setWidth(3).setDepth(3).setThresholdAngle(0.5).setFuse(true).setFuseAngle(0.5);
//		HEM_Lattice myLattice = new HEM_Lattice().setWidth(3).setDepth(1).setThresholdAngle(200).setFuse(true).setFuseAngle(0.5); //Der ist ganz ok
		HEM_Lattice myLattice = new HEM_Lattice().setWidth(3).setDepth(2).setThresholdAngle(200).setFuse(true).setFuseAngle(0.5);
		myMesh.modify(myLattice);
//		myMesh.modifySelected(myLattice, myMesh.selectAllBoundaryEdges().invertEdges());
	}
	
	public void changeTwist(double value) {
//		bend = value;
		myBender.setAngleFactor(value);
//		myBender.apply(myMesh);
	}

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//Select Modifiers for single faces - maybe later :)
/** CLICKABLE MODIFIERS */
	
	public void splitFace(int id) {
//		
	}
	
	//works but not in use
	public void extrudeFace(int id) {
		HEM_Extrude modifier = new HEM_Extrude();
		HE_Selection mySelection = new HE_Selection(myMesh);
		HE_Face f = myMesh.getFaceByKey(id);
		mySelection.add(f);
		modifier.setDistance(10); //this would be distance, could be connected to mouse eventually
		myMesh.modifySelected(modifier, mySelection);
	}
	
	
/** PRIVATE FUNCTIONS */
	private void initMesh() {
		myMesh = new HE_Mesh(myTube);
		root.println("myTUbe is: " + myTube);
		root.println("mymesh is: " + myMesh);
	}
	
	//YES, thats it; this way the classes will be reused
	public void updateMesh() {
		myMesh.clear();
//		myMesh = new HE_Mesh(myTube);
		myMesh.add(new HE_Mesh(myTube));
		root.println("myTUbe is: " + myTube);
		root.println("mymesh is: " + myMesh);
	}
	
	
	private void createDynMesh() {
//		myDynMesh = new HE_DynamicMesh(myMesh);
	}

}

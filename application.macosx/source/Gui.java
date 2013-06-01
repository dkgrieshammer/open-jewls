/**
 * 
 */


import controlP5.Button;
import controlP5.ControlEvent;
import controlP5.ControlGroup;
import controlP5.ControlP5;
import controlP5.ControllerSprite;
import controlP5.DropdownList;
import controlP5.RadioButton;
import controlP5.Slider;
import controlP5.Toggle;
import processing.core.PApplet;
import processing.core.PImage;
import sun.awt.windows.ThemeReader;

/**
 * @author david
 *
 */
public class Gui {
	
//	PApplet root;
	Jewls root;
	
	public ControlP5 controlP5;
	public ControlGroup controlGroup;
	public ControlGroup specialGroup;
	
//	public Slider mySlider;
//	public Slider innerR;
	
	public Slider innerRadius;
	public Slider outerRadius;
	public Toggle smoothBtn;
	public Toggle wireBtn;
	public Toggle latticeBtn;
	public Toggle voronoiBtn;
	public Toggle randomBtn;
	
	public RadioButton materialButtons;
	public Toggle blackColor;
	public Toggle whiteColor;
	public Toggle redColor;
	public Toggle steel;
	public Toggle silver;
	public Toggle gold;
	
	public Button saveSTL;
	//Feintuning buttons
	public Slider shear;
	public Slider shearPI;
	public Toggle shearBoth; 
	
	public Slider expandX;
	public Slider expandXPI;
	public Slider expandY;
	public Slider expandYPI;
	
	public DropdownList effect;
	public Slider effectAmount;
	
	public Button chaos_1;
	public Button chaos_2;
	public Button twist;
	
	
	public Gui(Jewls parent) {
		root = parent;
		
		controlP5 = new ControlP5(root);
		controlP5.setAutoDraw(false);
		
		
		controlGroup = controlP5.addGroup("*** Open Jewls ***", 20, 100, 200);
//		controlGroup.setHeight(15);
		controlGroup.hideBar();
		
		Slider hgt = controlP5.addSlider("height", root.myRing.heightMin, root.myRing.heightMax, root.myRing.height, 0, 20, 200, 10);
		hgt.setLabel("Breite in mm");
		hgt.setGroup(controlGroup);
		
//		controlP5.addTextlabel("height", "Breite in mm", 40, 20).setGroup(controlGroup);
		innerRadius = controlP5.addSlider("inner radius", root.myRing.innerRMin, root.myRing.innerRMax, root.myRing.innerR, 0, 40, 200, 10);
		innerRadius.setGroup(controlGroup);
		innerRadius.setLabel("Durchmesser");
		
		outerRadius = controlP5.addSlider("outer radius", root.myRing.outerRMin, root.myRing.outerRMax, root.myRing.outerR, 0, 60, 200, 10);
		outerRadius.setGroup(controlGroup);
		outerRadius.setLabel("Ring-Dicke");
		
		

//		controlP5.addSlider("shift x", 0, 80, (float) root.myRing.shiftX, 0, 80, 200, 10).setGroup(controlGroup);
//		controlP5.addSlider("shift y", 0, 80, (float) root.myRing.shiftY, 0, 100, 200, 10).setGroup(controlGroup);
		
//		controlP5.addButton("smooth", 1, 0, 120, 60, 15).setGroup(controlGroup);
		smoothBtn = controlP5.addToggle("smooth", 0, 100, 15, 15);
		smoothBtn.setGroup(controlGroup);
		smoothBtn.setLabel("Smoothing");
		
//		controlP5.addButton("wire", 1, 70, 120, 60, 15).setGroup(controlGroup);
		wireBtn = controlP5.addToggle("wire", 70, 100, 15, 15);
		wireBtn.setGroup(controlGroup);
		wireBtn.setLabel("Drahtgitter");
		
		
//		controlP5.addButton("lattice", 1, 140, 120, 60, 15).setGroup(controlGroup);
		latticeBtn =  controlP5.addToggle("lattice", 140, 100, 15, 15);
		latticeBtn.setGroup(controlGroup);
		latticeBtn.setLabel("Gitterwerk");
		
//		controlP5.addButton("voronoi", 1, 0, 140, 60, 15).setGroup(controlGroup);
		voronoiBtn = controlP5.addToggle("voronoi", 0, 130, 15, 15 );
		voronoiBtn.setGroup(controlGroup);
		voronoiBtn.setLabel("Voronoischnitt");
		
//		controlP5.addButton("random", 1, 70, 140, 60, 15).setGroup(controlGroup);
		randomBtn = controlP5.addToggle("random", 70, 130, 15, 15);
		randomBtn.setGroup(controlGroup);
		randomBtn.setLabel("Zufallsschnitt");
		
//		controlP5.addButton("struct", 1, 140, 140, 60, 15).setGroup(controlGroup);
		
		materialButtons = controlP5.addRadioButton("material", root.width - 150, 120);
		materialButtons.setLabel("Material");
//		materialButtons.setGroup(controlGroup);
		materialButtons.setItemsPerRow(1);
		materialButtons.setSpacingColumn(50);
		materialButtons.setSpacingRow(10);
		blackColor = materialButtons.addItem("blackColor", 1);
		blackColor.setLabel("Kunststoff schwarz");
		blackColor.setColorActive(root.color(0));
		whiteColor = materialButtons.addItem("whiteColor", 2);
		whiteColor.setLabel("Kunststoff weiss");
		whiteColor.setColorActive(root.color(255));
		redColor = materialButtons.addItem("redColor", 3);
		redColor.setLabel("Kunststoff rot");
		redColor.setColorActive(root.color(255,20,20));
		steel = materialButtons.addItem("steel", 4);
		steel.setLabel("Edelstahl");
		steel.setColorActive(root.color(180));
		silver = materialButtons.addItem("silver", 5);
		silver.setLabel("999 Silber");
		silver.setColorActive(root.color(210));
		gold = materialButtons.addItem("gold", 6);
		gold.setLabel("Edelstahl goldfinish");
		gold.setColorActive(root.color(200,200,0));
		
		saveSTL = controlP5.addButton("SAVE FILE", 1, 0, 180, 200, 30);
		saveSTL.setGroup(controlGroup);
		saveSTL.setLabel("Design abspeichern");
		
//		controlP5.addToggle("test", 0, 140, 20, 20).setGroup(controlGroup);
//		controlP5.addSlider("twist", 0, 100, (float) root.myRing.shiftY, 0, 180, 200, 10).setGroup(controlGroup);
		
		//specials
		specialGroup = controlP5.addGroup("Feintuning", 20 , 340, 200);
		specialGroup.close();
		shear = controlP5.addSlider("shear", 0, 30, 0, 20, 200, 10);
		shear.setGroup(specialGroup);
		shear.setLabel("Schraege");
		shearPI = controlP5.addSlider("shearPI", -2, 4, 0, 0, 40, 200, 10);
		shearPI.setGroup(specialGroup);
		shearPI.setLabel("Manipulator");
		shear.disableSprite();
		
		expandX = controlP5.addSlider("expand X", 0, 5, 0, 60, 200, 10);
		expandX.setGroup(specialGroup);
		expandX.setLabel("Vorne woelben");
		expandXPI = controlP5.addSlider("expand X PI", -2, 4, 0, 0, 80, 200, 10);
		expandXPI.setGroup(specialGroup);
		expandXPI.setLabel("Manipulator");

		expandY = controlP5.addSlider("expand Y", 0, 5, 0, 100, 200, 10);
		expandY.setGroup(specialGroup);
		expandY.setLabel("Seitlich woelben");
		expandYPI = controlP5.addSlider("expand Y PI", -2, 4, 0, 0, 120, 200, 10);
		expandYPI.setGroup(specialGroup);
		expandYPI.setLabel("Manipulator");
		
		effect = controlP5.addDropdownList("Effect", 0, 160, 200, 10);
//		customize(effect);
		effect.setGroup(specialGroup);
		effect.setLabel("Effekt");
		
//		effect.addItem("Wirbel", 1);
//		effect.addItem("Chaos_1", 2);
//		effect.addItem("Chaos_2", 3);
		
		twist = controlP5.addButton("twist", 1, 0, 0, 200, 15 );
		twist.setGroup(effect);
		twist.setLabel("Wirbel");
		chaos_1 = controlP5.addButton("chaos_1",1, 0, 16, 200, 15);
		chaos_1.setGroup(effect);
		chaos_1.setLabel("Chaos 1");
		chaos_2 = controlP5.addButton("chaos_2", 1, 0, 32, 200, 15);
		chaos_2.setGroup(effect);
		chaos_2.setLabel("Chaos 2");
		effectAmount = controlP5.addSlider("effectAmount", -1, 1, 0, 0, 53, 200, 10);
		effectAmount.setGroup(effect);
		effectAmount.setLabel("Effektstaerke");
	}
	

	
	public void controlEvent (ControlEvent theEvent) {
		
		if(theEvent.isController()) {
			
			if(theEvent.controller().name() == "height") {
				root.myRing.changeHeight(theEvent.controller().value());
//				root.myRing.createMesh();
//				root.changeSize(theEvent.controller().value());
			}
			
			if(theEvent.controller().name() == "inner radius") {
				root.myRing.changeInnerR(theEvent.controller().value());
			}
			if(theEvent.controller().name() == "outer radius") {
				root.myRing.changeOuterR(theEvent.controller().value());
//				controlP5.controlbroadcaster().broadcast(theEvent);
				
			}
			
			if(theEvent.controller().name() == "shift x") {
				root.myRing.changeShiftX(theEvent.controller().value());
			}
			if(theEvent.controller().name() == "shift y") {
				root.myRing.changeShiftY(theEvent.controller().value());
			}
			
			if(theEvent.controller().name() == "smooth") {
				root.println("catmull fired");	
				root.myRing.addCatmull();
			}
			if(theEvent.controller().name() == "wire") {
				if(theEvent.controller().value() == 1) {
					root.println("wire fired");	
					root.myRing.addWireframe();
				}else{
					root.myRing.updateMesh();
				}
//				root.myRing.latticeVoronoi();
			}
			if(theEvent.controller().name() == "lattice") {
				root.println("lattice fired");	
				root.myRing.addLattice();
			}
			
			if(theEvent.controller().name() == "voronoi") {
				root.println("voronoi fired");	
				root.myRing.addVoronoi();
			}
			if(theEvent.controller().name() == "random") {
				root.println("random fired");	
//				root.myRing.addWireframe();
				root.myRing.addRandom();
			}
			if(theEvent.controller().name() == "struct") {
				root.println("struct fired");	
				root.myRing.addStruct();
			}
			
			
			//SAVE FILE
			if(theEvent.controller().name() == "SAVE FILE") {
				root.println("saving");	
				root.exportSTL = true;
//				root.saveStls();
			}
			

//			if(theEvent.controller().name() == "twist") {
//				root.myRing.changeTwist(theEvent.controller().value());
//			}
			
			//SPECIALS
			if(theEvent.controller().name() == "shear") {
				root.myRing.addShear(theEvent.controller().value());
			}
			if(theEvent.controller().name() == "shearPI") {
				root.myRing.changeShearPI(theEvent.controller().value());
			}
			if(theEvent.controller().name() == "expand X") {
				root.myRing.expandFormX(theEvent.controller().value());
			}
			if(theEvent.controller().name() == "expand X PI") {
				root.myRing.expandFormXPI(theEvent.controller().value());
			}
			if(theEvent.controller().name() == "expand Y") {
				root.myRing.expandFormY(theEvent.controller().value());
			}
			if(theEvent.controller().name() == "expand Y PI") {
				root.myRing.expandFormYPI(theEvent.controller().value());
			}
			
			//EFFECTS
			if(theEvent.controller().name() == "effectAmount") {
				root.myRing.setEffectAmount(theEvent.controller().value());
			}
			if(theEvent.controller().name() == "chaos_1") {
				root.myRing.setChaos(true);
			}
			if(theEvent.controller().name() == "chaos_2") {
				root.myRing.setChaos_2(true);
			}
			if(theEvent.controller().name() == "twist") {
				root.myRing.setTwist(true);
			}
			
		}
		
		if(theEvent.isGroup()) {
			//COLORS & MATERIALS
			if(theEvent.group().name() == "material") {
				switch ((int) theEvent.group().value()) {
				case 1:
					//black
				root.myRing.changeColor(root.color(0), root.color(60));
				root.myMaterial = "black";
					break;
				case 2:
					//white
					root.myRing.changeColor(root.color(255), root.color(220));
					root.myMaterial = "white";
					break;
				case 3:
					//red
					root.myRing.changeColor(root.color(255,20,20), root.color(220,0,0));
					root.myMaterial = "red";
					break;
				case 4:
					//steel
					root.myRing.changeColor(root.color(180), root.color(120));
					root.myMaterial = "steel";
					break;
				case 5:	
					//silver
					root.myRing.changeColor(root.color(240), root.color(180));
					root.myMaterial = "silver";
					break;
				case 6:
					//gold
					root.myRing.changeColor(root.color(255,255,10), root.color(200,200,10));
					root.myMaterial = "gold";
					break;
				default:
					break;
				}
//				root.println(theEvent.group().value());
			}
		}
	}
	
	public void drawGui() {
		controlP5.draw();
	}

}
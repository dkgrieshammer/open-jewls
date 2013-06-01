/*
 * Copyright (c) 2010, Frederik Vanhoutte This library is free software; you can
 * redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation; either version
 * 2.1 of the License, or (at your option) any later version.
 * http://creativecommons.org/licenses/LGPL/2.1/ This library is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See
 * the GNU Lesser General Public License for more details. You should have
 * received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 51 Franklin St,
 * Fifth Floor, Boston, MA 02110-1301 USA
 */



import java.util.Random;

import wblut.geom.core.WB_Vector3d;
import wblut.hemesh.core.HE_Mesh;
import wblut.hemesh.creators.HEC_Creator;
import wblut.hemesh.creators.HEC_FromFacelist;

/**
 * Tube.
 * 
 * @author Jan Vantomme
 * 
 */

public class HEC_ShiftTube extends HEC_Creator {
	/** Outer Radius */
	private double	outerRadius;

	/** Inner Radius */
	private double	innerRadius;

	/** Height */
	private double	H;

	/** Facets */
	private int		facets;

	/** Height steps */
	private int		steps;

	/** Shift distance in X direction */
	private double	shiftX;

	/** Shift distance in Y direction */
	private double	shiftY;

	//new Modifier
	
	/** shears the form in z direction */
	private double shearModZ;
	/** controls the amount of pi calculated to shear */
	private double shearModZPi;
	
	/** expands the side of the form */
	private double expandModX;
	private double expandModY;
	/** controls the amount of pi calculated to expand */
	private double expandModXPi;
	private double expandModYPi;
	
	/** control the amount of effect affection in percent */
	private double effectAmount;
	//twists
	private boolean twistSwitch;
	//chaos
	private double chaosX[];
	private double chaosY[];
	private double chaosZ[];
	private boolean chaosSwitch;
	
	
	/**
	 * Instantiates a new HEC_ShiftTube.
	 *
	 */
	public HEC_ShiftTube() {
		super();

		innerRadius = 0;
		outerRadius = 0;

		H = 0;

		facets = 6;
		steps = 1;

		shiftX = 0;
		shiftY = 0;
		
		//new Modifiers
		shearModZ = 0;
		shearModZPi = 1;
		
		expandModX = 0;
		expandModXPi = 1;
		expandModY = 0;
		expandModYPi = 1;
		
		twistSwitch = false;
		
		chaosSwitch = false; //no chaos at start
		
		Z = WB_Vector3d.Y();

	}

	/**
	 * Set the outer radius.
	 *
	 * @param R Outer radius
	 * @return self
	 */
	public HEC_ShiftTube setOuterRadius(final double R) {
		outerRadius = R;
		return this;
	}
	public double getOuterRadius() {
		return outerRadius;
	}
	/**
	 * Set the inner radius.
	 *
	 * @param R Inner radius
	 * @return self
	 */
	public HEC_ShiftTube setInnerRadius(final double R) {
		innerRadius = R;
		return this;
	}
	public double getInnerRadius() {
		return innerRadius;
	}
	/**
	 * Set the height
	 *
	 * @param H Height
	 * @return self
	 */
	public HEC_ShiftTube setHeight(final double H) {
		this.H = H;
		return this;
	}

	/**
	 * Set vertical divisions
	 *
	 * @param steps Vertical divisions
	 * @return self
	 */
	public HEC_ShiftTube setSteps(final int steps) {
		this.steps = steps;
		return this;
	}

	/**
	 * Set number of sides
	 *
	 * @param facets number of sides
	 * @return self
	 */
	public HEC_ShiftTube setFacets(final int facets) {
		this.facets = facets;
		return this;
	}

	/**
	 * Set the shift distance for the center of the inner radius in X direction
	 *
	 * @param shiftX shift distance in X direction
	 * @return self
	 */
	public HEC_ShiftTube shiftX(final double shiftX) {
		this.shiftX = shiftX;
		return this;
	}

	/**
	 * Set the shift distance for the center of the inner radius in Y direction
	 *
	 * @param shiftY shift distance in Y direction
	 * @return self
	 */
	public HEC_ShiftTube shiftY(final double shiftY) {
		this.shiftY = shiftY;
		return this;
	}

	/**
	 * Set the shift distance for the center of the inner radius in X and Y direction
	 *
	 * @param shift shift distance in X and Y direction
	 * @return self
	 */
	public HEC_ShiftTube shift(final double shift) {
		shiftX = shift;
		shiftY = shift;
		return this;
	}

	/**
	 * Set the shift distance for the center of the inner radius in X and Y direction
	 *
	 * @param shiftX shift distance in X direction
	 * @param shiftY shift distance in Y direction
	 * @return self
	 */
	public HEC_ShiftTube shift(final double shiftX, final double shiftY) {
		this.shiftX = shiftX;
		this.shiftY = shiftY;
		return this;
	}
	
	
	
	//NEW MODIFIERS +++++++++++++++++++
	/**
	 * @name shear
	 * @desc shears the tube, meaning the z-values of the vertices are expandet on a side
	 * @param shearMod amount, could be between 0 and maybe 100?
	 * @return
	 */
	public HEC_ShiftTube setShear(final double shearMod) {
		this.shearModZ = shearMod;
		return this;
	}
	
	/**
	 * @name setShearPi
	 * @desc this is the pi amount that affects the shear - should change form from nautilus to up-down; values should be between 1 and 2 !
	 * @param shearModPi - best if 1 to 2
	 * @return
	 */
	public HEC_ShiftTube setShearPi(final double shearModPi) {
		this.shearModZPi = shearModPi;
		return this;
	}
	
	public HEC_ShiftTube setExpandMod(double expandX, double expandY) {
		this.expandModX = expandX;
		this.expandModY = expandY;
		return this;
	}
	
	public HEC_ShiftTube setExpandModY(double expandModY) {
		this.expandModY = expandModY;
		return this;
	}
	
	public HEC_ShiftTube setExpandModX(double expandModX) {
		this.expandModX = expandModX;
		return this;
	}
	
	public HEC_ShiftTube setExpandModPi (double piX, double piY) {
		this.expandModXPi = piX;
		this.expandModYPi = piY;
		return this;
	}
	public HEC_ShiftTube setExpandModXPi (double pi) {
		this.expandModXPi = pi;
		return this;
	}
	public HEC_ShiftTube setExpandModYPi (double pi) {
		this.expandModYPi = pi;
		return this;
	}
	//Effects
	public HEC_ShiftTube setEffectAmount (double amount) {
		this.effectAmount = amount;
		return this;
	}
	public HEC_ShiftTube setTwist (boolean tSwitch) {
		
		if(tSwitch == true) {
			setChaos(false);
		}
		this.twistSwitch = tSwitch;
		return this;
	}
	
	public HEC_ShiftTube setChaos(boolean cSwitch) {
		
		this.chaosSwitch = cSwitch;
		chaosX = new double[facets];
		chaosY = new double[facets];
		chaosZ = new double[facets];
		if(cSwitch == true) {
			setTwist(false);
			double num;
			for (int i = 0; i < facets; i++) {
				num = Math.random();
				chaosX[i] = num * (0.5 - (-0.5)) - 0.5;
				num = Math.random();
				chaosY[i] = num * (0.5 - (-0.5)) - 0.5;
				num = Math.random();
				chaosZ[i] = num * (0.5 - (-0.5)) - 0.5;
			}
		}
		return this;
	}
	public HEC_ShiftTube setChaos_2(boolean cSwitch) {
			
			this.chaosSwitch = cSwitch;
			chaosX = new double[facets];
			chaosY = new double[facets];
			chaosZ = new double[facets];
			if(cSwitch == true) {
				setTwist(false);
				double num;
				for (int i = 0; i < facets; i++) {
					chaosX[i] = 0;
					chaosY[i] = 0;
					num = Math.random();
					chaosZ[i] = num * (0.5 - (-0.5)) - 0.5;
				}
			}
			return this;
		}
	

	/*
	 * (non-Javadoc)
	 * @see wblut.hemesh.HE_Creator#create()
	 */
	@Override
	protected HE_Mesh createBase() {
		final double[][] vertices = new double[((steps + 1) * facets) * 2][3];
		// outer vertices
		//normal
		for (int i = 0; i <= steps; i++) {
			final double Hj = i * H / steps;
			for (int j = 0; j < facets; j++) {
				vertices[j + i * facets][0] = outerRadius * Math.cos(2 * Math.PI / facets * j);
				vertices[j + i * facets][1] = outerRadius * Math.sin(2 * Math.PI / facets * j);
				vertices[j + i * facets][2] = Hj;
			}
		}
		
		if(twistSwitch == true) {
			//twist only middle vertices
//			int myIt = 0;
//			while (myIt < steps) {
//				final double Hj = myIt * H / steps;
//				for (int j = 0; j < facets; j++) {
//					vertices[j + myIt * facets][0] =  outerRadius * Math.cos(2 * Math.PI / facets * (j + (myIt * effectAmount)));
//					vertices[j + myIt * facets][1] =  outerRadius * Math.sin(2 * Math.PI / facets * (j + (myIt * effectAmount)));
//					vertices[j + myIt * facets][2] = Hj; //5 ist der amount;
////					vertices[j + myIt * facets][2] = Hj + (myIt * 2 * effectAmount); //5 ist der amount;
//				}
//				myIt ++;
//			}
			for (int i = 0; i <= steps; i++) {
				final double Hj = i * H / steps;
				for (int j = 0; j < facets; j++) {
					vertices[j + i * facets][0] = outerRadius * Math.cos(2 * Math.PI / facets * (j + (i * effectAmount)));
					vertices[j + i * facets][1] = outerRadius * Math.sin(2 * Math.PI / facets * (j + (i * effectAmount)));
					vertices[j + i * facets][2] = Hj;
				}
			}
			
		}
		if (chaosSwitch == true) {
			//twist & random could be like this
			int myIt = 1;
			while (myIt <= steps) {
				final double Hj = myIt * H / steps;
				for (int j = 0; j < facets; j++) {
					vertices[j + myIt * facets][0] =  outerRadius * Math.cos(2 * Math.PI / facets * (j + (myIt * chaosY[j] * effectAmount)));
					vertices[j + myIt * facets][1] =  outerRadius * Math.sin(2 * Math.PI / facets * (j + (myIt * chaosY[j] * effectAmount)));
					vertices[j + myIt * facets][2] = Hj + (myIt * chaosZ[j] * (effectAmount * 10)); //5 ist der amount;
//					vertices[j + i * facets][2] += (Math.sin(shearModZPi * Math.PI / 10 * j) * (modiZ * -1));
				}
				myIt ++;
			}
		}
		
		
		for (int i = 0; i <= steps; i ++) {
			//jetz den modifikator, also 20 mit sin verändern
			double modiX = Math.sin(Math.PI / steps * i) * expandModX;
			double modiY = Math.sin(Math.PI / steps * i) * expandModY;
			double modiZ = Math.cos(Math.PI / steps * i) * shearModZ;
			
			for (int j = 0; j < facets; j++) {
				vertices[j + i * facets][0] += (Math.sin(expandModXPi * Math.PI / 10 * j) * modiX) * Math.cos(2 * Math.PI / facets * j);
				vertices[j + i * facets][1] += (Math.sin(expandModYPi * Math.PI / 10 * j) * modiY) * Math.sin(2 * Math.PI / facets * j);
//				vertices[j + i * facets][2] += (Math.cos(1* Math.PI / 10 * j) * (modiz * -1)); //hoch runter
				vertices[j + i * facets][2] += (Math.sin(shearModZPi * Math.PI / 10 * j) * (modiZ * -1));
			}
		}
		

		// inner vertices
		for (int i = steps; i >= 0; i--) {
			final double Hj = i * H / steps;
			for (int j = 0; j < facets; j++) {
				vertices[j + i * facets + ((steps + 1) * facets)][0] = shiftX
						+ (innerRadius * Math.cos(2 * Math.PI / facets * j));
				vertices[j + i * facets + ((steps + 1) * facets)][1] = shiftY
						+ innerRadius * Math.sin(2 * Math.PI / facets * j);
				vertices[j + i * facets + ((steps + 1) * facets)][2] = Hj;
			}
		}
		
		//inner twister
		if(twistSwitch == true) {
			//twist
			for (int i = steps; i >= 0; i--) {
				final double Hj = i * H / steps;
				for (int j = 0; j < facets; j++) {
					vertices[j + i * facets + ((steps + 1) * facets)][0] = shiftX
							+ (innerRadius * Math.cos(2 * Math.PI / facets * (j + (i * effectAmount))));
					vertices[j + i * facets + ((steps + 1) * facets)][1] = shiftY
							+ innerRadius * Math.sin(2 * Math.PI / facets * (j + (i * effectAmount)));
					vertices[j + i * facets + ((steps + 1) * facets)][2] = Hj;
				}
			}
		}
		
		
//		// modify inner vertices to lift z equaly to outer verts
//		for (int i = steps; i >= 0; i --) {
//			//jetz den modifikator, also 20 mit sin verändern
////			double modiX = Math.sin(Math.PI / steps * i) * expandModX;
////			double modiY = Math.sin(Math.PI / steps * i) * expandModY;
//			double modiZ = Math.cos(Math.PI / steps * i) * shearModZ;
//			
//			for (int j = 0; j < facets; j++) {
////				vertices[j + i * facets][0] += (Math.sin(expandModXPi * Math.PI / 10 * j) * modiX) * Math.cos(2 * Math.PI / facets * j);
////				vertices[j + i * facets][1] += (Math.sin(expandModYPi * Math.PI / 10 * j) * modiY) * Math.sin(2 * Math.PI / facets * j);
////				vertices[j + i * facets][2] += (Math.cos(1* Math.PI / 10 * j) * (modiz * -1)); //hoch runter
//				vertices[j + i * facets + ((steps + 1) * facets)][2] += (Math.sin(shearModZPi * Math.PI / 10 * j) * (modiZ * -1));
//			}
//		}
//		
		

		final int[][] faces = new int[(steps * facets * 2) + (facets * 2)][];

		for (int j = 0; j < facets; j++) {
			// outer faces
			for (int i = 0; i < steps; i++) {
				faces[j + i * facets] = new int[4];
				faces[j + i * facets][0] = (j + 1) % facets + i * facets;
				faces[j + i * facets][1] = ((j + 1) % facets) + facets + i
						* facets;
				faces[j + i * facets][2] = j + i * facets + facets;
				faces[j + i * facets][3] = j + i * facets;
			}

			// top faces
			if (j != facets - 1) {
				faces[j + (steps * facets)] = new int[4];
				faces[j + (steps * facets)][0] = j + (steps * facets) + facets;
				faces[j + (steps * facets)][1] = j + 1 + (steps * facets)
						+ facets;
				faces[j + (steps * facets)][2] = j + 1;
				faces[j + (steps * facets)][3] = j;
			} else {
				faces[j + (steps * facets)] = new int[4];
				faces[j + (steps * facets)][0] = j + (steps * facets) + facets;
				faces[j + (steps * facets)][1] = (steps * facets) + facets;
				faces[j + (steps * facets)][2] = 0;
				faces[j + (steps * facets)][3] = j;
			}

			// inner faces
			for (int i = 0; i < steps; i++) {
				faces[(j + i * facets) + (steps * facets) + facets] = new int[4];
				faces[(j + i * facets) + (steps * facets) + facets][0] = (j + i
						* facets)
						+ (steps * facets) + facets;
				faces[(j + i * facets) + (steps * facets) + facets][1] = (j + i
						* facets + facets)
						+ (steps * facets) + facets;
				faces[(j + i * facets) + (steps * facets) + facets][2] = (((j + 1) % facets)
						+ facets + i * facets)
						+ (steps * facets) + facets;
				faces[(j + i * facets) + (steps * facets) + facets][3] = ((j + 1)
						% facets + i * facets)
						+ (steps * facets) + facets;

			}

			// bottom faces
			if (j != facets - 1) {
				faces[j + 2 * (steps * facets) + facets] = new int[4];
				faces[j + 2 * (steps * facets) + facets][0] = j
						+ (steps * facets);
				faces[j + 2 * (steps * facets) + facets][1] = j + 1
						+ (steps * facets);
				faces[j + 2 * (steps * facets) + facets][2] = j + 1
						+ (steps * facets) + facets + (steps * facets);
				faces[j + 2 * (steps * facets) + facets][3] = j
						+ (steps * facets) + facets + (steps * facets);
			} else {
				faces[j + 2 * (steps * facets) + facets] = new int[4];
				faces[j + 2 * (steps * facets) + facets][0] = j
						+ (steps * facets);
				faces[j + 2 * (steps * facets) + facets][1] = (facets * steps);
				faces[j + 2 * (steps * facets) + facets][2] = vertices.length
						- 1 - j;
				faces[j + 2 * (steps * facets) + facets][3] = vertices.length - 1;
			}
		}
		
		final HEC_FromFacelist fl = new HEC_FromFacelist();
		fl.setVertices(vertices).setFaces(faces).setDuplicate(false);
		return fl.create();
	}


}
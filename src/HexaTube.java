import wblut.geom.core.WB_Point3d;
import wblut.hemesh.core.HE_Mesh;
import wblut.hemesh.creators.HEC_Box;
import wblut.hemesh.creators.HEC_Creator;
import wblut.hemesh.creators.HEC_FromFacelist;
import wblut.hemesh.creators.HEMC_VoronoiCells;

/**
 * 
 */

/**
 * @author david
 *
 */
public class HexaTube extends HEC_Creator {

	int numPoints = 40;
	int numRows = 4;
	int numCols = 10;
	HE_Mesh myMesh;
	WB_Point3d[] myPoints;
	
	
	public HexaTube() {
		super();
//		HEC_Box myBox = new HEC_Box();
//		myBox.setWidth(200).setHeight(100).setDepth(100);
//		
//		myMesh = new HE_Mesh(myBox);
//		double[] limits = myMesh.limits();
//		
//		myPoints = new WB_Point3d[numPoints];
////		for (int i = 0; i < numRows; i++) {
////			for (int j = 0; j < numCols; j++) {
////			}
////		}
//		for (int i = 0; i < numPoints; i++) {
//			myPoints[i] = new WB_Point3d(myRandom((float)limits[0],(float)limits[3]),myRandom((float)limits[1],(float)limits[4]),myRandom((float)limits[2],(float)limits[5]));
//		}
//				
//		HEMC_VoronoiCells vc = new HEMC_VoronoiCells().setContainer(myMesh).setPoints(myPoints).setCreateSkin(true);
//		HE_Mesh[] tempMesh = vc.create(); //voronoi muss scheinbar Ÿber create() erstellt werden
//		myMesh = tempMesh[tempMesh.length - 1];
		
	}

	/* (non-Javadoc)
	 * @see wblut.hemesh.creators.HEC_Creator#createBase()
	 */
	@Override
	protected HE_Mesh createBase() {

		
		HEC_Box myBox = new HEC_Box();
		myBox.setWidth(200).setHeight(100).setDepth(100);
		
		myMesh = new HE_Mesh(myBox);
		double[] limits = myMesh.limits();
		
		myPoints = new WB_Point3d[numPoints];
//		for (int i = 0; i < numRows; i++) {
//			for (int j = 0; j < numCols; j++) {
//			}
//		}
		for (int i = 0; i < numPoints; i++) {
			myPoints[i] = new WB_Point3d(myRandom((float)limits[0],(float)limits[3]),myRandom((float)limits[1],(float)limits[4]),myRandom((float)limits[2],(float)limits[5]));
		}
				
		HEMC_VoronoiCells vc = new HEMC_VoronoiCells().setContainer(myMesh).setPoints(myPoints).setCreateSkin(true);
		HE_Mesh[] tempMesh = vc.create(); //voronoi muss scheinbar Ÿber create() erstellt werden
		myMesh = tempMesh[tempMesh.length - 1];
		
		return myMesh;
		
//		final HEC_FromFacelist fl = new HEC_FromFacelist();
//		fl.setVertices(vertices).setFaces(faces).setDuplicate(false);
//		return fl.create();
//		return null;
	}
	
	public double myRandom(double low, double high) {
		return Math.random() * (high - low) + low;
	}


}

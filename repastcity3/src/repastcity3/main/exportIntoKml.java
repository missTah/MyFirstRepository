package repastcity3.main;


import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.geometry.jts.WKTReader2;
import org.geotools.kml.v22.KML;
import org.geotools.kml.v22.KMLConfiguration;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.xml.Encoder;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;


public class exportIntoKml {
	
	private ArrayList<List<Coordinate>> pathschedule=new ArrayList<List<Coordinate>>();
	List<Coordinate> current;
	 
	public exportIntoKml(ArrayList<List<Coordinate>> pth ){
		this.pathschedule=pth;
		
	}

	 public void featureCollectionToKML() throws Exception {
	        SimpleFeatureCollection features = createSampleFeatures();
	        FileOutputStream lFileOutputStream = new FileOutputStream(new File("Hello.kml"));
	        Encoder encoder = new Encoder(new KMLConfiguration());
	        encoder.setIndenting(true);
	        encoder.encode(features, KML.kml, lFileOutputStream );
	        lFileOutputStream.close();
	    }


	 private SimpleFeatureCollection createSampleFeatures() throws ParseException {
	        SimpleFeatureTypeBuilder typeBuilder = new SimpleFeatureTypeBuilder();
	        typeBuilder.setName("Agents");
	        typeBuilder.add("geometry", Point.class, DefaultGeographicCRS.WGS84);
	        typeBuilder.add("name", String.class);
	       
	      
	        SimpleFeatureType TYPE = typeBuilder.buildFeatureType();

	      
	        
	        GeometryFactory gf = new GeometryFactory();
	        
	        
	      
	        DefaultFeatureCollection features = new DefaultFeatureCollection();
	        
	       
	        for(int i = 0; i < this.pathschedule.size(); i++) {
	        	current=this.pathschedule.get(i);
	        	
	        	for(int j = 0; j < current.size(); j++){
	        		features.add( SimpleFeatureBuilder.build( TYPE, new Object[]{gf.createPoint(current.get(j)),"name"+j}, null) );
	        		
	        		//System.out.println(this.toString()+" Voici le contenu de la route, trajet numero "+i+" coordonnées "+current.get(j));
	        	}
	        }
	        return features;
	    }
}

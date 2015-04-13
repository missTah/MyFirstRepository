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


public class export {
	
	private static ArrayList<List<Coordinate>> pathschedule=new ArrayList<List<Coordinate>>();
	List<Coordinate> current;
	 
	public export(ArrayList<List<Coordinate>> pth ){
		pathschedule=pth;
		
	}

	 public void featureCollectionToKML() throws Exception {
	        SimpleFeatureCollection features = createSampleFeatures();
	        FileOutputStream lFileOutputStream = new FileOutputStream(new File("Hello.kml"));
	        Encoder encoder = new Encoder(new KMLConfiguration());
	        encoder.setIndenting(true);
	        encoder.encode(features, KML.kml, lFileOutputStream );
	        lFileOutputStream.close();
	        System.out.println("==========DONE=========");
	    }


	 private SimpleFeatureCollection createSampleFeatures() throws ParseException {
	        SimpleFeatureTypeBuilder typeBuilder = new SimpleFeatureTypeBuilder();
	        typeBuilder.setName("Agents");
	        typeBuilder.add("geometry", Point.class, DefaultGeographicCRS.WGS84);
	        typeBuilder.add("name", String.class);
	        
	             
	        SimpleFeatureType TYPE = typeBuilder.buildFeatureType();

	              
	        GeometryFactory gf = new GeometryFactory();
	        
	        	      
	        DefaultFeatureCollection features = new DefaultFeatureCollection();
	        
	       
	       for(int i = 0; i < pathschedule.size(); i++) {
	        	current=pathschedule.get(i);
	        	
	        	for(int j = 0; j < current.size(); j++){
	        		Coordinate c=new Coordinate(current.get(j).x,current.get(j).y);
	        		features.add( SimpleFeatureBuilder.build( TYPE, new Object[]{gf.createPoint(c),"name"+i}, null) );
	        		
	        		System.out.println(this.toString()+" Voici le contenu de la route, trajet numero "+i+" coordonnées "+current.get(j));
	        	}
	        }
	      //  features.add(SimpleFeatureBuilder.build(TYPE,new Object[]{gf.createPoint(new Coordinate(-1.5179779647864824, 53.8343867523139)), "name1"},null));
	       // features.add( SimpleFeatureBuilder.build( TYPE, new Object[]{gf.createPoint(new Coordinate(-0.126236,51.500152)), "name1"}, null) );
	        
	        return features;
	    }
}

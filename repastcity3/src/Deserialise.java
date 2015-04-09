
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipOutputStream;

import org.eclipse.swt.widgets.DateTime;
import org.geotools.data.DataUtilities;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.factory.Hints;
import org.geotools.feature.AttributeTypeBuilder;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.filter.AttributeExpression;
import org.geotools.filter.FilterFactoryFinder;
import org.geotools.gce.imagemosaic.catalog.index.ObjectFactory;
import org.geotools.kml.v22.KML;
import org.geotools.kml.v22.KMLConfiguration;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.styling.FeatureTypeConstraint;
import org.geotools.styling.Mark;
import org.geotools.styling.Style;
import org.geotools.styling.StyleBuilder;
import org.geotools.styling.builder.FeatureTypeConstraintBuilder;
import org.geotools.util.TemporalConverterFactory;
import org.geotools.xml.Encoder;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.feature.type.AttributeType;
import org.opengis.feature.type.FeatureType;
import org.opengis.filter.FilterFactory;
import org.opengis.filter.FilterFactory2;
import org.opengis.style.Graphic;

import com.ibm.icu.text.SimpleDateFormat;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import de.micromata.opengis.kml.v_2_2_0.TimeSpan;
import de.micromata.opengis.kml.v_2_2_0.TimeStamp;
import repastcity3.agent.Student;





public class Deserialise {
	
	static int compteur=10;



		
	public static void main(String[] args) throws Exception {
		Student s = null;
		//int year, month, day, hour, minute, second;
		Calendar javaCalendar = Calendar.getInstance();
		/*year = javaCalendar.get(Calendar.YEAR);
		month = javaCalendar.get(Calendar.MONTH);
		day = javaCalendar.get(Calendar.DAY_OF_MONTH);
		hour=javaCalendar.get(Calendar.HOUR_OF_DAY);
		minute=javaCalendar.get(Calendar.MINUTE);
		second=javaCalendar.get(Calendar.SECOND);*/
	
		
		//Create the builder
		SimpleFeatureTypeBuilder typeBuilder = new SimpleFeatureTypeBuilder();
		AttributeTypeBuilder attributeBuilder = new AttributeTypeBuilder();
		SimpleDateFormat timestp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		Date dt=javaCalendar.getTime();
		

	
		 //set global state
		
	     typeBuilder.setName("Agent");
	     typeBuilder.add("geometry", Point.class, DefaultGeographicCRS.WGS84);
	     typeBuilder.add("name", String.class);
	     typeBuilder.add("TimeStamp", String.class);
	   //  typeBuilder.add("KML.TimeStamp", TimeStamp.class);
	     //typeBuilder.add(attributeBuilder.binding(Date.class).nillable(false).buildDescriptor("TimeStamp"));
	     
	     //build the type    
		 SimpleFeatureType TYPE = typeBuilder.buildFeatureType();
	           	     
		
	     //Create a new featureTypes
	     //final SimpleFeatureType TIMESTAMP = DataUtilities.createType("TimeStamp", "name:String");
	     final SimpleFeatureType WHEN = DataUtilities.createType("when", "name:String");
	     
	   	    

	     /*AttributeTypeBuilder buildAtt=new AttributeTypeBuilder();
	     buildAtt.setName("TimeStamp");*/
	    // buildAtt.setBinding(Timestamp.class);
	    
	     
	      // FeatureTypeFactoryImpl myType=new FeatureTypeFactoryImpl();
	     //myType.createAttributeType("TimeStamp", Timestamp.class, true, false, new ArrayList<String>(), String,"e");
	 
	    
	     
	     GeometryFactory gf = new GeometryFactory();
	     ObjectFactory obj=new ObjectFactory();
	    org.geotools.gce.imagemosaic.catalog.index.AttributeType ob= obj.createAttributeType();
	    ob.setAttribute("TimeStamp");
	    
	    
	        
	     DefaultFeatureCollection features = new DefaultFeatureCollection();
	
		
		for(int l=0;l<compteur;l++)
		{
	      try
	      {
	         FileInputStream fileIn = new FileInputStream("Agent "+l+".ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         s = (Student) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("Student class not found");
	         c.printStackTrace();
	         return;
	      }
	      

	      System.out.println("Deserialized Student...");
	    //  System.out.println("Serial number: " + s.uniqueID);
	      System.out.println("Name: " + s.toString());
	     List<Coordinate> current;
	          
	     for(int i = 0; i < s.getpathSchedule().size(); i++) {
	    	 current=s.getpathSchedule().get(i);
	    		//int a=0;
				for(int j = 0; j < current.size(); j++){
					//System.out.println(s.toString()+" Voici le contenu de la route, trajet numero "+i+" coordonnées "+current.get(j));		
					Coordinate c=new Coordinate(current.get(j).x,current.get(j).y);
					System.out.println(s.toString()+" Voici le contenu de la route, trajet numero "+l+"."+i+"."+j+" coordonnées "+gf.createPoint(c));
					/*TimeStamp tm=new TimeStamp();
				    tm.setWhen( getCurrentdate(a,year, month,day, hour, minute, second));*/
					features.add( SimpleFeatureBuilder.build( TYPE, new Object[]{gf.createPoint(c),"name"+l+"."+i+"."+j,timestp.format(dt) }, null) );
					

					//features.add( SimpleFeatureBuilder.build( TYPE, new Object[]{gf.createPoint(c),"name"+l+"."+i+"."+j, timeout.format(dt)}, null) );
					//features.add(SimpleFeatureBuilder.build(myType, new Object[]{gf.createPoint(new Coordinate(01,02))}, null));
	        		//a++;	
					
					//SimpleFeatureBuilder whenBuilder = new SimpleFeatureBuilder(WHEN);
					//whenBuilder.add("test");
					/*SimpleFeatureBuilder timeBuilder = new SimpleFeatureBuilder(TIMESTAMP);
					timeBuilder.add(whenBuilder.buildFeature(null));
					features.add(timeBuilder.buildFeature(null));*/
	     }
				   
				
	     }
	    
		}
		featureCollectionToKML(features);
		
		
	     
				
	     }
	
	 public static void featureCollectionToKML(SimpleFeatureCollection features) throws Exception {
	        FileOutputStream lFileOutputStream = new FileOutputStream(new File("Hello.kml"));
	        Encoder encoder = new Encoder(new KMLConfiguration());
	        encoder.setIndenting(true);
	        encoder.encode(features, KML.kml, lFileOutputStream );
	        lFileOutputStream.close();
	        System.out.println("==========DONE=========");
	    }
	 
	/* private static String getCurrentdate(int a, int year, int month,int day, int hour,int minute,int second){
		 
		 String currentDate = "";
		 
		 /*Timestamp t=new Timestamp();
		 t.getDateTime();
		 //Date dt = t.getDate();
		// return dt;*/
		 
		/*return currentDate = (year+a) + "-" + month + "-" + day +"T"+ hour+":"+minute+":"+ second;
		// return t;
	 }*/
	 
	 	 
}



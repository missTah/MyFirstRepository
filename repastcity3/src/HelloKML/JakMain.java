package HelloKML;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import repastcity3.agent.Student;

import com.ibm.icu.text.SimpleDateFormat;

import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.KmlFactory;
import de.micromata.opengis.kml.v_2_2_0.Placemark;


public class JakMain {

	static int compteur=10;
	
	public static void main(String[] args) throws Exception {
		Student s = null;
		Kml kml = KmlFactory.createKml();
		
		Calendar javaCalendar = Calendar.getInstance();
		Date dt=javaCalendar.getTime();
		 Document document = kml.createAndSetDocument().withName("MyMarkers");
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
	      System.out.println("Serial number: " + s.uniqueID);
	      System.out.println("Name: " + s.toString());
	     List<com.vividsolutions.jts.geom.Coordinate> current;
	    
	     for(int i = 0; i < s.getpathSchedule().size(); i++) {
	    	 current=s.getpathSchedule().get(i);
	    		int a=0;
				for(int j = 0; j < current.size(); j++){
					
					
					Placemark placemark = KmlFactory.createPlacemark();
					placemark.setName(s.toString());
					//placemark.setVisibility(true);
					//placemark.setOpen(false);
					//placemark.setDescription("Un placemarque");
					//placemark.setStyleUrl("styles.kml#jugh_style");
					
					
					// Create <Point> and set values.
					de.micromata.opengis.kml.v_2_2_0.Point point = KmlFactory.createPoint();
					//point.setExtrude(false);
					//point.setAltitudeMode(AltitudeMode.CLAMP_TO_GROUND);
					
					// Add <coordinates>9.444652669565212,51.30473589438118,0<coordinates>.
					point.getCoordinates().add(new Coordinate(current.get(j).x,current.get(j).y));
					placemark.setGeometry(point);
					SimpleDateFormat timestp = new SimpleDateFormat("yyyy"+j+"-MM-dd'T'HH:mm:ss.SSS'Z'");
					placemark.createAndSetTimeStamp().setWhen(timestp.format(dt));
					
					//kml.setFeature(placemark);	
					document.addToFeature(placemark);
					
					//kml.addToKmlSimpleExtension(placemark);
					a++;
	        		
	     }
						   
				
	     }
	    
	}
		 kml.marshal(new File("HelloTahina.kml"));
			
		

	
	}
	

}

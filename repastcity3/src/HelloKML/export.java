package HelloKML;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;





import com.vividsolutions.jts.geom.Coordinate;

import de.micromata.opengis.kml.v_2_2_0.Kml;
import repastcity3.agent.Student;

public class export {
	static int compteur=10;
	
	public export() throws Exception {
		
	
}
	
	public void go() throws FileNotFoundException{
		Student s = null;
		final Kml kml = new Kml();
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
	     List<Coordinate> current;
	          
	     for(int i = 0; i < s.getpathSchedule().size(); i++) {
	    	 current=s.getpathSchedule().get(i);
	    		int a=0;
				for(int j = 0; j < current.size(); j++){
					//System.out.println(s.toString()+" Voici le contenu de la route, trajet numero "+i+" coordonnées "+current.get(j));		
					//Coordinate c=new Coordinate(current.get(j).x,current.get(j).y);
					//System.out.println(s.toString()+" Voici le contenu de la route, trajet numero "+l+"."+i+"."+j+" coordonnées "+gf.createPoint(c));
	        		//features.add( SimpleFeatureBuilder.build( TYPE, new Object[]{gf.createPoint(c),"name"+l+"."+i+"."+j}, null) );
					kml.createAndSetPlacemark()
					   .withName(s.toString()).withOpen(Boolean.TRUE)
							.createAndSetPoint().addToCoordinates(current.get(j).x,current.get(j).y);
					
	        		a++;
	        		
	     }
						   
				
	     }
	   //marshals to console
			kml.marshal();
			//marshals into file
			kml.marshal(new File("Hellotahina.kml"));
	}
			
	}
	}

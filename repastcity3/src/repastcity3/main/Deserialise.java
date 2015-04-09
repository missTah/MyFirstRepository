package repastcity3.main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;

import repastcity3.agent.AgentFactory;
import repastcity3.agent.Student;
import repastcity3.main.exportIntoKml;



public class Deserialise {
	private int compteur;
	
	public Deserialise(int a){
		this.compteur=a;
		
	}

	public void GoDeserialise() throws Exception {
		Student s = null;
		
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
				for(int j = 0; j < current.size(); j++){
					System.out.println(s.toString()+" Voici le contenu de la route, trajet numero "+i+" coordonnées "+current.get(j));		
					
	     }
				
	     }
		}
		exportIntoKml exp=new exportIntoKml(s.getpathSchedule());
	     exp.featureCollectionToKML();
	     
				
	     }		
			
	}



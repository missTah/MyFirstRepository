package repastcity3.main;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.Instant;
import java.util.List;

import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Icon;
import de.micromata.opengis.kml.v_2_2_0.IconStyle;
import de.micromata.opengis.kml.v_2_2_0.KmlFactory;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Units;
import de.micromata.opengis.kml.v_2_2_0.Vec2;
import repastcity3.agent.AgentFactory;
import repastcity3.agent.Student;


public class intoKml {
	static int compteur;

	public intoKml(){

	}

	public void go() throws FileNotFoundException{

		Student s = null;
		AgentFactory f=null;

		de.micromata.opengis.kml.v_2_2_0.Kml kml = de.micromata.opengis.kml.v_2_2_0.KmlFactory.createKml();

		java.time.Instant dt;
		de.micromata.opengis.kml.v_2_2_0.Document document = kml.createAndSetDocument().withName("MyMarkers");
		compteur=AgentFactory.CompteurStudent;

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
			List<Instant> currentListTmstp;
			String couleur=generateColor();

			for(int i = 0; i < s.getpathSchedule().size(); i++) {
				current=s.getpathSchedule().get(i);
				currentListTmstp=s.getAllTimeStamp().get(i);
				int a=0;

				for(int j = 0; j < current.size(); j++){


					Placemark placemark = KmlFactory.createPlacemark();
					//placemark.setName(s.toString());
					//placemark.setVisibility(true);
					//placemark.setOpen(false);
					//placemark.setDescription("Un placemarque");
					//placemark.setStyleUrl("styles.kml#jugh_style");


					// Create <Point> and set values.
					de.micromata.opengis.kml.v_2_2_0.Point point = KmlFactory.createPoint();
					//point.setExtrude(false);
					//point.setAltitudeMode(AltitudeMode.CLAMP_TO_GROUND);


					point.getCoordinates().add(new Coordinate(current.get(j).x,current.get(j).y));
					placemark.setGeometry(point);

					//TimeStamp format
					//SimpleDateFormat timestp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS"+a+"'Z'");

					dt=currentListTmstp.get(j);
					placemark.createAndSetTimeStamp().setWhen(dt.toString());

					// IconStyle
					IconStyle ic= placemark.createAndAddStyle().createAndSetIconStyle();
					ic.setHeading(1);
					ic.setScale(0.5);
					ic.setColor(couleur);
					Vec2 vc=new Vec2();
					vc.setX(0);
					vc.setY(0.5);
					vc.setXunits(Units.FRACTION);
					vc.setYunits(Units.FRACTION);
					ic.setHotSpot(vc);

					//Icon
					Icon icone=ic.createAndSetIcon();
					icone.setRefreshInterval(0.5);
					icone.setViewRefreshTime(0.5);
					icone.setHref("http://maps.google.com/mapfiles/ms/icons/cabs.png");

					document.addToFeature(placemark);


					a++;

				}


			}

		}
		kml.marshal(new File("HelloTahina.kml"));




	}


	public static String generateColor(){
		int red = (int) (( Math.random()*255)+1);
		int green = (int) (( Math.random()*255)+1);
		int blue = (int) (( Math.random()*255)+1);
		Color RandomC = new Color(red,green,blue);
		int RandomRGB = (RandomC.getRGB());
		String RandomRGB2Hex = Integer.toHexString(RandomRGB);
		return RandomRGB2Hex;
	}


}

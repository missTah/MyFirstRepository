package repastcity3.agent;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.kml.v22.KML;
import org.geotools.kml.v22.KMLConfiguration;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.xml.Encoder;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ISchedule;
import repast.simphony.engine.schedule.ScheduleParameters;
import repastcity3.environment.Building;
import repastcity3.environment.Route;
import repastcity3.exceptions.NoIdentifierException;
import repastcity3.main.ContextManager;


public class Student implements IAgent,java.io.Serializable{

	private static Logger LOGGER = Logger.getLogger(DefaultAgent.class.getName());

	private Building home; // Where the agent lives
	private Route route; // An object to move the agent around the world

	private boolean goingHome = false; // Whether the agent is going to or from their home

	public static int uniqueID = 0;
	private int id;

	//list that content the coordinate of the current position of the agent
	private List<Coordinate> currentCoord;
	private List<Instant> currentTimeStamp;


	//ArrayList that content the trajectory of the agent
	private ArrayList<List<Coordinate>> pathschedule=new ArrayList<List<Coordinate>>();

	//ArrayList of all timestamp
	private ArrayList<List<Instant>> allTimeStamps=new ArrayList<List<Instant>>();


	public Student(){

		this.id = uniqueID++;



	}

	@Override
	public void step() throws Exception {


		Building b= ContextManager.buildingContext.getRandomObject();


		// TODO Auto-generated method stub
		LOGGER.log(Level.FINE, "Agent " + this.id + " is stepping.");
		if (this.route == null) {
			this.goingHome = false; // Must be leaving home
			// Choose a new building to go to
			b = randomBuilding(1);


			this.route = new Route(this, b.getCoords(), b);


			System.out.println(this.toString() + " created new route to " + b.toString());
			LOGGER.log(Level.FINE, this.toString() + " created new route to " + b.toString());
			System.out.println("created new route to " + b.toString());
		}

		if (!this.route.atDestination()) {
			this.route.travel();

			//Pick up the trajectory of the agent from his current place to his destination
			setCurrentRoute();
			setCurrentTimeStamp();


			LOGGER.log(Level.FINE, this.toString() + " travelling to " + this.route.getDestinationBuilding().toString());
		} else {
			
			//Store all the trajectory of the agent inside an arrayList
			setpathSchedule();
			setAllTimeStamp(currentTimeStamp);
			




			// Have reached destination, now either go home or onto another building
			if (this.goingHome) {
				this.goingHome = false;
				/*Random randomGenerator = new Random();
				int choice = randomGenerator.nextInt(3);
				b = randomBuilding(choice);*/
				b= randomBuilding(1);



				this.route = new Route(this, b.getCoords(), b);
				System.out.println("created new route to " + b.toString());



				System.out.println(this.toString() + " reached home, now going to " + b.toString());
				LOGGER.log(Level.FINE, this.toString() + " reached home, now going to " + b.toString());
			} else {
				System.out.println(this.toString() + " reached " + this.route.getDestinationBuilding().toString()
						+ ", now going home");
				LOGGER.log(Level.FINE, this.toString() + " reached " + this.route.getDestinationBuilding().toString()
						+ ", now going home");
				this.goingHome = true;
				this.route = new Route(this, this.home.getCoords(), this.home);
				System.out.println("created new route to " + b.toString());
			}


		}
		serialiseMe();


	}

	//Method for the choice of the destination building
	public Building randomBuilding(int a) throws NumberFormatException, NoIdentifierException{
		Building b;
		/*Random randomGenerator = new Random();
		int choice = randomGenerator.nextInt(4);*/
		do{
			b = ContextManager.buildingContext.getRandomObject();
			//System.out.println("Ooooooooooo: "+ b.getCommune());
		} while(b.getCommune()!=a);
		//System.out.println("OLAH OLAH OLAH: "+ b.getCommune());
		return b;	

	}

	//Pick up the trajectory of the agent from his current place to his destination
	public void setCurrentRoute() {

		this.currentCoord=this.route.getListRoute();

	}

	public void setCurrentTimeStamp(){
		this.currentTimeStamp=this.route.getTimeStamp();
	}

	public void setAllTimeStamp(List<Instant> currentTimeStamp2){
		this.allTimeStamps.add(currentTimeStamp2);
	}

	public ArrayList<List<Instant>> getAllTimeStamp(){
		return this.allTimeStamps;
	}

	//Store all the trajectory of the agent inside an arrayList
	public void setpathSchedule() throws FileNotFoundException{
		//List<Coordinate> current;
		this.pathschedule.add(currentCoord);
		/*for(int i = 0; i < this.pathschedule.size(); i++) {
			current=this.pathschedule.get(i);
			for(int j = 0; j < current.size(); j++){
				System.out.println(this.toString()+" Voici le contenu de la route, trajet numero "+i+" coordonnées "+current.get(j));		

			}
			System.out.println("PAUSE");
	};*/





	}

	//Return the arrayList of all the agent trajectories
	public ArrayList<List<Coordinate>> getpathSchedule(){
		return this.pathschedule;

	}



	@Override
	public boolean isThreadable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setHome(Building home) {
		// TODO Auto-generated method stub
		this.home=home;

	}

	@Override
	public Building getHome() {
		// TODO Auto-generated method stub
		return this.home;
	}

	@Override
	public <T> void addToMemory(List<T> objects, Class<T> clazz) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<String> getTransportAvailable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String toString() {
		return "Agent " + this.id;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Student))
			return false;
		Student b = (Student) obj;
		return this.id == b.id;
	}

	@Override
	public int hashCode() {
		return this.id;
	}

	public void serialiseMe(){ 
		try
		{
			FileOutputStream fileOut = new FileOutputStream(this.toString()+".ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
			System.out.println("Serialized data is saved for Student "+this.toString());
		}catch(IOException i)
		{
			i.printStackTrace();
			System.out.println("Serialisation failed for Student"+this.toString());
		}
	}

	@Override
	public void getCurrentRoute() {
		// TODO Auto-generated method stub

	}


	//=====================================KML export========================================





	/*private SimpleFeatureCollection createSampleFeatures() throws ParseException {
        SimpleFeatureTypeBuilder typeBuilder = new SimpleFeatureTypeBuilder();
        typeBuilder.setName("cities");
        typeBuilder.add("geometry", Point.class, DefaultGeographicCRS.WGS84);
        typeBuilder.add("name", String.class);


        SimpleFeatureType TYPE = typeBuilder.buildFeatureType();    
        GeometryFactory gf = new GeometryFactory();     
        DefaultFeatureCollection features = new DefaultFeatureCollection();

        List<Coordinate> current;
        for(int i = 0; i < this.pathschedule.size(); i++) {
        	current=this.pathschedule.get(i);

        	for(int j = 0; j < current.size(); j++){
        		features.add( SimpleFeatureBuilder.build( TYPE, new Object[]{gf.createPoint(current.get(j))}, null) );

        		//System.out.println(this.toString()+" Voici le contenu de la route, trajet numero "+i+" coordonnées "+current.get(j));
        	}
        }
        //  WKTReader2 wkt = new WKTReader2();

        //features.add( SimpleFeatureBuilder.build( TYPE, new Object[]{gf.createPoint(new Coordinate(-0.126236,51.500152)), "name1"}, null) );
        //features.add( SimpleFeatureBuilder.build( TYPE, new Object[]{ wkt.read("POINT(4 4)"), "name2"}, null) );

        return features;
    }

	private void featureCollectionToKML() throws Exception {
        SimpleFeatureCollection features = createSampleFeatures();
        FileOutputStream lFileOutputStream = new FileOutputStream(new File("Hello.kml"));
        Encoder encoder = new Encoder(new KMLConfiguration());
        encoder.setIndenting(true);
        encoder.encode(features, KML.kml, lFileOutputStream );
        lFileOutputStream.close();
    }

	 */



	/*private Building randomHome(int a) throws NumberFormatException, NoIdentifierException{
		Building b;
		do{
			b = ContextManager.buildingContext.getRandomObject();
		} while(b.getCommune()!=a);
		System.out.println("OLAH OLAH OLAH: GO WORK "+ b.getCommune());
			return b;	

	}*/


}

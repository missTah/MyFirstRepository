package repastcity3.agent;




import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.vividsolutions.jts.geom.Coordinate;

import repastcity3.environment.Building;
import repastcity3.environment.Route;
import repastcity3.exceptions.NoIdentifierException;
import repastcity3.main.ContextManager;
import repastcity3.main.intoKml;


public class Student extends AgentClass{

	private static Logger LOGGER = Logger.getLogger(DefaultAgent.class.getName());

	private Building home; // Where the agent lives
	private Route route; // An object to move the agent around the world

	private boolean goingHome = false; // Whether the agent is going to or from their home

	public static int uniqueID = 0;
	private int id;
	private static int compteur=0;

	//list that content the coordinates of the current position of the agent
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


			
			LOGGER.log(Level.FINE, this.toString() + " created new route to " + b.toString());
			
		}

		if (!this.route.atDestination()) {
			this.route.travel();
			intoKml exportSimulation=new intoKml(20);
			exportSimulation.go();
			//Pick up the trajectory of the agent from his current place to his destination
			setCurrentRoute();
			setCurrentTimeStamp();

			setpathSchedule(compteur);
			setAllTimeStamp(compteur,currentTimeStamp);


			LOGGER.log(Level.FINE, this.toString() + " travelling to " + this.route.getDestinationBuilding().toString());
		} else {

			//Store all the trajectory of the agent inside an arrayList
			compteur++;






			// Have reached destination, now either go home or onto another building
			if (this.goingHome) {
				this.goingHome = false;
				/*Random randomGenerator = new Random();
				int choice = randomGenerator.nextInt(3);
				b = randomBuilding(choice);*/
				b= randomBuilding(1);



				this.route = new Route(this, b.getCoords(), b);
				

				

				System.out.println(this.toString() + " reached home, now going to " + b.toString());
				LOGGER.log(Level.FINE, this.toString() + " reached home, now going to " + b.toString());
			} else {
				System.out.println(this.toString() + " reached " + this.route.getDestinationBuilding().toString()
						+ ", now going home");
				LOGGER.log(Level.FINE, this.toString() + " reached " + this.route.getDestinationBuilding().toString()
						+ ", now going home");
				this.goingHome = true;
				this.route = new Route(this, this.home.getCoords(), this.home);
				
				
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
			
		} while(b.getCommune()!=a);
		
		return b;	

	}

	//Pick up the trajectory of the agent from his current place to his destination
	public void setCurrentRoute() {

		this.currentCoord=this.route.getListRoute();

	}

	public void setCurrentTimeStamp(){
		this.currentTimeStamp=this.route.getTimeStamp();
	}

	public void setAllTimeStamp(int compt, List<Instant> currentTimeStamp2){
		if (this.allTimeStamps.size()==compt || this.allTimeStamps.size()==0){
			this.allTimeStamps.add(currentTimeStamp2);
		}
		else
		{
				this.allTimeStamps.set(this.allTimeStamps.size()-1,currentTimeStamp2);	
			
		}

	}

	public ArrayList<List<Instant>> getAllTimeStamp(){
		return this.allTimeStamps;
	}

	//Store all the trajectory of the agent inside an arrayList
	public void setpathSchedule(int compt) throws FileNotFoundException{
		//List<Coordinate> current;
		if (this.pathschedule.size()==compt || this.pathschedule.size()==0){
			this.pathschedule.add(currentCoord);

		}
		else{
			
				this.pathschedule.set(this.pathschedule.size()-1,currentCoord);
			
			}
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
			FileOutputStream fileOut = new FileOutputStream("Student"+this.toString()+".ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
			
		}catch(IOException i)
		{
			i.printStackTrace();
			
		}
	}

	@Override
	public void getCurrentRoute() {
		// TODO Auto-generated method stub

	}

}

import org.sql2o.Connection;

import java.sql.Timestamp;
import java.util.List;

public class Sightings extends Animal{

    public static boolean ENDANGERED =  false;
    private String location;
    private String rangerName;
    private Timestamp timestamp;



//constrructor to initiate a new sighting.
    public Sightings(int id, String name,String health,int age,boolean ENDANGERED, String location, String rangerName, Timestamp timestamp) {
        if (rangerName.equals("")) {
            throw new IllegalArgumentException("Please enter Ranger name.");
        }
        this.id = id;
        this.name = name;
        this.location = location;
        this.rangerName = rangerName;
        this.timestamp = timestamp;
        this.health = health;
        this.ENDANGERED = ENDANGERED;
        this.age = age;
        this.save();
    }


    //Getters


    public static boolean isENDANGERED() {
        return ENDANGERED;
    }

    public String getLocation() {
        return location;
    }

    public String getRangerName() {
        return rangerName;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    //Saving the sighting of an animal
    public void save(){
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO sightings (name, health, age, endangered, location, rangername, timestamp) VALUES (:name, :health, :age, :endangered, :location, :rangerName, :timestamp)";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("name", this.name)
                    .addParameter("health",this.health)
                    .addParameter("age",this.age)
                    .addParameter("endangered",this.ENDANGERED)
                    .addParameter("health",this.location)
                    .addParameter("age",this.rangerName)
                    .addParameter("age",this.timestamp)
                    .executeUpdate()
                    .getKey();

        }
    }


    //return all sighted animals
    public static List<Sightings> all() {
        String sql = "SELECT * FROM sightings ORDER BY timestamp DESC";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Sightings.class);
        }
    }

    //Overriding sighting
    public boolean equals(Object otherSighting){
        if(!(otherSighting instanceof Sightings)){
            return false;
        }else{
            Sightings newSighting = (Sightings) otherSighting;
            return this.getId()==newSighting.getId() && this.getRangerName().equals(newSighting.getRangerName());
        }
    }


    //find a specific sighted animal
    public static Sightings find(){
        try (Connection con = DB.sql2o.open()){
            String sql = "SELECT * FROM sightings WHERE id =:id";
            Sightings sightings = con.createQuery(sql)
                    .addParameter("id",id)
                    .executeAndFetchFirst(Sightings.class);
            return sightings;
        }
    }

    //implement method delete() from Database management class
    public void delete(){
        try(Connection con = DB.sql2o.open()) {
            String sql = "DELETE FROM sightings WHERE id=:id;";
            con.createQuery(sql)
                    .addParameter("id",id)
                    .executeUpdate();
        }
    }

    //update the Sightings table && throwing an exception incase the id is not mapped
    public void update() {
        String sql = "UPDATE sightings SET name = :name, health = :health, age = :age, endangered = :ENDANGERED location = :location, rangername = :rangerName WHERE id = :id";

        try(Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("name",name)
                    .addParameter("health", health)
                    .addParameter("age", age)
                    .addParameter("health", health)
                    .addParameter("ENDANGERED", ENDANGERED)
                    .addParameter("location", location)
                    .addParameter("rangername", rangerName)
                    .throwOnMappingFailure(false)
                    .executeUpdate();
        }
    }



}

//ALTER TABLE Customers
//ADD Email varchar(255);
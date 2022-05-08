import org.sql2o.Connection;

import java.sql.Timestamp;
import java.util.List;

public class Sightings extends Animal{

    public static final boolean ENDANGERED =  false;
    private String location;
    private String ranger_name;
    private Timestamp timestamp;

    //Saving the sighting
    public void save(){
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO sightings (name, health, age, endangered) VALUES (:name, :health, :age, :endangered)";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("name", this.name)
                    .addParameter("health",this.health)
                    .addParameter("age",this.age)
                    .addParameter("endangered",this.ENDANGERED)
                    .executeUpdate()
                    .getKey();

        }
    }


    //return all sighted animals
    public static List<EndangeredAnimal> all() {
        String sql = "SELECT * FROM sightings WHERE endangered = false";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(EndangeredAnimal.class);
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
}

//ALTER TABLE Customers
//ADD Email varchar(255);
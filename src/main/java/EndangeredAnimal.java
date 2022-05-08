import org.sql2o.*;

import java.util.List;

public class EndangeredAnimal extends Animal{

    public final boolean ENDANGERED =  true;
    public static int id;

    public EndangeredAnimal() {
    }

    //Saving the endangered animal
    public void save(){
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO animals (name, health, age, endangered) VALUES (:name, :health, :age, :endangered)";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("name", this.name)
                    .addParameter("health",this.health)
                    .addParameter("age",this.age)
                    .addParameter("endangered",this.ENDANGERED)
                    .executeUpdate()
                    .getKey();

        }
    }


    //return all endangered animals
    public static List<EndangeredAnimal> all() {
        String sql = "SELECT * FROM animals WHERE endangered = true";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(EndangeredAnimal.class);
        }
    }

    //find a specific endengered animal
    public static EndangeredAnimal find(){
        try (Connection con = DB.sql2o.open()){
            String sql = "SELECT * FROM animals WHERE id =:id";
            EndangeredAnimal endangeredAnimal = con.createQuery(sql)
                    .addParameter("id",id)
                    .executeAndFetchFirst(EndangeredAnimal.class);
            return endangeredAnimal;

        }
    }
}

import org.sql2o.*;

import java.util.List;

public class EndangeredAnimal extends Animal{

    public static final boolean ENDANGERED =  true;
    public boolean type;
    public static int id;

    private final Sql2o sql2o;

    public EndangeredAnimal(String name, Sql2o sql2o) {
        this.sql2o = sql2o;
        if (name.equals("")){
            //throw exception if no name is entered
            throw new IllegalArgumentException("Please enter an animal name.");
        }
        this.name = name;

        type = true;
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
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(EndangeredAnimal.class);
        }
    }

    //find a specific endengered animal
    public static EndangeredAnimal find(){
        String sql = "SELECT * FROM animals WHERE id =:id";
        try (Connection con = DB.sql2o.open()){
            EndangeredAnimal endangeredAnimal = con.createQuery(sql)
                    .addParameter("id",id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(EndangeredAnimal.class);
            return endangeredAnimal;

        }
    }

    //updating an endangered animal using its Id
    public void update(){
        String sql =  "UPDATE animals SET name = :name WHERE ID = :id";
        try(Connection con = DB.sql2o.open()){
            con.createQuery(sql)
                    .addParameter("name",name)
                    .addParameter("id",id)
                    .addParameter("health",health)
                    .addParameter("age",age)
                    .addParameter("endangered",ENDANGERED)
                    .throwOnMappingFailure(false)
                    .executeUpdate();
        }
    }

    //Deleting an animal that is endanged
    public void delete(){
        String sql = "DELETE FROM animals WHERE id = :id";
        try(Connection con = DB.sql2o.open()){
            con.createQuery(sql)
                    .addParameter("name",name)
                    .addParameter("id",id)
                    .addParameter("health",health)
                    .addParameter("age",age)
                    .addParameter("endangered",ENDANGERED)
                    .throwOnMappingFailure(false)
                    .executeUpdate();
        }
    }

}

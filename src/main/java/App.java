import org.sql2o.Sql2o;

import static spark.Spark.staticFileLocation;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        String connectionString = "jdbc:postgresql://localhost:4567/wildlife_tracker";
        Sql2o sql2o = new Sql2o(connectionString, "ian", "Neno$iri1");
    }
}

import org.junit.rules.ExternalResource;
import org.sql2o.*;
public class DatabaseRule extends ExternalResource{
    @Override
    public void before() {
        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker", null, null);
    }

    @Override
    protected void after() {
        try(Connection con = DB.sql2o.open()) {
            String deletePersonsQuery = "DELETE FROM persons *;";
            con.createQuery(deletePersonsQuery).executeUpdate();
        }
    }
}

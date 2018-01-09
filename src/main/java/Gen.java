import java.util.List;
import org.sql2o.*;

public class Gen{
  //Checking Seller loin
  public static int SellerChecker(String name, String password) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT COUNT(*) FROM sellers where name=:name and password=:password";
      int count = con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("password", password)
        .executeScalar(Integer.class);
      return count;
    }
  }
}

import java.util.List;
import org.sql2o.*;

public class Seller{
  private int id;
  private String name;
  private String password;
  private String email;
  private String phone;


  public Seller(String name, String password, String email, String phone){
    this.name = name.toUpperCase();
    this.password = password;
    this.email = email;
    this.phone = phone;

  }
  public int getId(){
    return id;
  }
  public String getName(){
    return name;
  }
  public String getPassword(){
    return password;
  }
  public String getEmail(){
    return email;
  }
  public String getPhone(){
    return phone;
  }
  public static List<Seller> all() {
      String sql = "SELECT id, name, password, email, phone FROM sellers ORDER BY name";
      try(Connection con = DB.sql2o.open()) {
        return con.createQuery(sql).executeAndFetch(Seller.class);
      }
  }
  public void save() {
   try(Connection con = DB.sql2o.open()) {
     String sql = "INSERT INTO sellers(name, password, email, phone) VALUES (:name, :password, :email, :phone)";
     this.id = (int) con.createQuery(sql, true)
       .addParameter("name", this.name)
       .addParameter("password", this.password)
       .addParameter("email", this.email)
       .addParameter("phone", this.phone)
       .executeUpdate()
       .getKey();
    }
  }
  public static Seller find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM sellers where id=:id";
      Seller seller = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Seller.class);
      return seller;
    }
  }
  public void update(String name, String password, String email, String phone) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE sellers SET name = :name, password = :password, email = :email , phone = :phone WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("password", password)
        .addParameter("email", email)
        .addParameter("phone", phone)
        .addParameter("id", id)
        .executeUpdate();
    }
  }
  public void delete() {
    try(Connection con = DB.sql2o.open()) {
    String sql = "DELETE FROM sellers WHERE id = :id;";
    con.createQuery(sql).addParameter("id", id).executeUpdate();
    }
    //Assigning client_id to 0 for Clients allocated to the deleted sTYLIST
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM items WHERE sellerid = :id;";
      con.createQuery(sql).addParameter("id", id).executeUpdate();
    }
  }
  public List<Item> getItems() {
      try(Connection con = DB.sql2o.open()) {
        String sql = "SELECT * FROM items where sellerid=:id ORDER BY name";
        return con.createQuery(sql)
          .addParameter("id", this.id)
          .executeAndFetch(Item.class);
      }
    }

    //login Seller
    public static Seller login(String name, String password) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "SELECT * FROM sellers where name=:name and password=:password";
        Seller seller = con.createQuery(sql)
          .addParameter("name", name)
          .addParameter("password", password)
          .executeAndFetchFirst(Seller.class);
        return seller;
      }
    }

}

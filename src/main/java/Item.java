import java.util.List;
import org.sql2o.*;

public class Item{
  private int id;
  private String name;
  private String location;
  private String cost;
  private String img;
  private int sellerid;

  public Item(String name, String location, String cost, String img, int sellerid){
    this.name = name.toUpperCase();
    this.location = location;
    this.cost = cost;
    this.img = img;
    this.sellerid = sellerid;

  }
  public int getId(){
    return id;
  }
  public String getName(){
    return name;
  }
  public String getLocation(){
    return location;
  }
  public String getCost(){
    return cost;
  }

  public String getImg(){
    return img;
  }

  public Seller getSeller() {
    int id = sellerid;
    Seller seller;
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM sellers where id=:id";
       seller = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Seller.class);
       }
    return seller;
  }

  public static List<Item> all() {
      String sql = "SELECT id, name, location, cost, img, sellerid FROM items ORDER BY name";
      try(Connection con = DB.sql2o.open()) {
        return con.createQuery(sql).executeAndFetch(Item.class);
      }
  }
  public static List<String> allLocations() {
      String sql = "SELECT DISTINCT(location) AS locations FROM items ORDER BY locations";
      try(Connection con = DB.sql2o.open()) {
         return con.createQuery(sql).executeScalarList(String.class);
      }
  }
  public static List<String> allItems() {
      String sql = "SELECT DISTINCT(name) AS items FROM items ORDER BY items";
      try(Connection con = DB.sql2o.open()) {
        return con.createQuery(sql).executeScalarList(String.class);
      }
  }
  public void save() {
   try(Connection con = DB.sql2o.open()) {
     String sql = "INSERT INTO items(name, location, cost, img, sellerid) VALUES (:name, :location, :cost, :img, :sellerid)";
     this.id = (int) con.createQuery(sql, true)
       .addParameter("name", this.name)
       .addParameter("location", this.location)
       .addParameter("cost", this.cost)
       .addParameter("img", this.img)
       .addParameter("sellerid", this.sellerid)
       .executeUpdate()
       .getKey();
    }
  }
  public static Item find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM items where id=:id";
      Item item = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Item.class);
      return item;
    }
  }
  public void update(String name, String location, String cost, String img) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE items SET name = :name, location = :location, cost = :cost, img = :img WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name",name)
        .addParameter("location", location)
        .addParameter("cost", cost)
        .addParameter("img", img)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
    String sql = "DELETE FROM items WHERE id = :id;";
    con.createQuery(sql).addParameter("id", id).executeUpdate();
    }
  }

}

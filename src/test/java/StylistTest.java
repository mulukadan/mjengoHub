import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class StylistTest {

  @Rule
 public DatabaseRule database = new DatabaseRule();

 @Test
 public void savingStylist_true() {
   Stylist stylist = new Stylist("Mose","M","999");
   stylist.save();
   assertEquals("Mose", stylist.getName());
 }
 @Test
 public void findStylist_true() {
   Stylist stylist = new Stylist("Mose","M","999");
   stylist.save();
   assertEquals("Mose", Stylist.find(stylist.getId()).getName());
 }
 @Test
 public void updatingStylist_true() {
   Stylist stylist = new Stylist("Mose","M","999");
   stylist.save();
   stylist.update("Sara", "F", "111");
   assertEquals("Sara", Stylist.find(stylist.getId()).getName());
   assertEquals("F", Stylist.find(stylist.getId()).getGender());
   assertEquals("111", Stylist.find(stylist.getId()).getContact());
 }

 @Test
public void delete_deletesStylist_true() {
      Stylist stylist = new Stylist("Mose","M","999");
      stylist.save();
      int stylistId = stylist.getId();
      stylist.delete();
      assertEquals(null, Stylist.find(stylistId));
   }

}

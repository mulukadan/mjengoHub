import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ClientTest {

  @Rule
 public DatabaseRule database = new DatabaseRule();

 @Test
 public void savingClient_true() {
   Client client = new Client("Victor","M","234", 2);
   client.save();
   assertEquals("Victor", client.getName());
 }
 @Test
 public void findCLient_true() {
   Client client = new Client("Victor","M","234", 2);
   client.save();
   assertEquals("Victor", Client.find(client.getId()).getName());
 }
 @Test
 public void updatingClient_true() {
   Client client = new Client("Victor","M","234", 2);
   client.save();
   client.update("Sara", "F", "111", 5);
   assertEquals("Sara", Client.find(client.getId()).getName());
   assertEquals("F", Client.find(client.getId()).getGender());
   assertEquals("111", Client.find(client.getId()).getContact());
   assertEquals(5, Client.find(client.getId()).getStylist_Id());
 }

 @Test
public void delete_deletesClient_true() {
      Client client = new Client("Victor","M","234", 2);
      client.save();
      int clientId = client.getId();
      client.delete();
      assertEquals(null, Client.find(clientId));
   }

}

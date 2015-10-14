package DBLayer;

import static org.junit.Assert.*;

import org.junit.Test;

public class DbConnectionTest {

	@Test
	public void test() {
		DbConnection dbCon = DbConnection.getInstance();
		if(dbCon != null)
		{
			System.out.println("Conecction to DB is ok");
		}
		else{
		    fail("Can not connect to the DB");
		}
	}

}

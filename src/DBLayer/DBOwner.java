package DBLayer;
import java.sql.*;
import java.util.ArrayList;

import ModelLayer.Owner;

public class DBOwner implements IFDBOwner{
	private  Connection con;

	public static void main(String[] args) {

		new DBOwner();
	    }
	
	public DBOwner() {
		con = DbConnection.getInstance().getDBcon();
		/*
		ArrayList<Owner> liste = getAllOwners(false);
		
		for(int i = 0; i < liste.size(); i++)
		{
			System.out.println(liste.get(i).getCity());
		}
		*/
		/*
		Owner owner = findOwner(2, false);
		System.out.println(owner.getFname());
		*/
	}
	
	public ArrayList<Owner> getAllOwners(boolean retriveAssociation)
    {
        return miscWhere("", retriveAssociation);
    }
	
	public Owner findOwner(int id, boolean retriveAssociation)
    {   String wClause = "  id = '" + id + "'";
        return singleWhere(wClause, retriveAssociation);
    }
	
	private ArrayList<Owner> miscWhere(String wClause, boolean retrieveAssociation)
	{
            ResultSet results;
	    ArrayList<Owner> list = new ArrayList<Owner>();	
		
	    String query =  buildQuery(wClause);
  
            try{ // read the owner from the database
		Statement stmt = con.createStatement();
	 	stmt.setQueryTimeout(5);
	 	results = stmt.executeQuery(query);
	 	
	
		while( results.next() ){
	     	 Owner ownerObj = new Owner();
		 ownerObj = buildOwner(results);	
                 list.add(ownerObj);	
		}//end while
                 stmt.close();     
                 if(retrieveAssociation)
                 {
                 }//end if   
			
		}//slut try	
	 	catch(Exception e){
	 		System.out.println("Query exception - select: "+e);
			e.printStackTrace();
	 	}
		return list;
	}
	
	private Owner singleWhere(String wClause, boolean retrieveAssociation)
	{
		ResultSet results;
		Owner ownerObj = new Owner();
                
	        String query =  buildQuery(wClause);
                //System.out.println(query);
		try{ // read the employee from the database
	 		Statement stmt = con.createStatement();
	 		stmt.setQueryTimeout(5);
	 		results = stmt.executeQuery(query);
	 		
	 		if( results.next() ){
	 			ownerObj = buildOwner(results);
                            //assocaition is to be build
                            stmt.close();
                            if(retrieveAssociation)
                            {
                            }
			}
                        else{ //no employee was found
                        	ownerObj = null;
                        }
		}//end try	
	 	catch(Exception e){
	 		System.out.println("Query exception: "+e);
	 	}
		return ownerObj;
	}
	
	//method to build the query
		private String buildQuery(String wClause)
		{
		    String query="SELECT fname, lname, address, zipcode, (select city from cities where id = owner.zipcode) as city, email, phone  FROM owner";
			
			if (wClause.length()>0)
				query=query+" WHERE "+ wClause;
				
			return query;
		}
		
		//method to build an owner object
		private Owner buildOwner(ResultSet results)
	      {   Owner ownerObj = new Owner();
	          try{ // the columns from the table emplayee  are used
	        	  ownerObj.setFname(results.getString("fname"));
	        	  ownerObj.setLname(results.getString("lname"));
	        	  ownerObj.setAddress(results.getString("address"));
	        	  ownerObj.setZipcode(results.getInt("zipcode"));
	        	  ownerObj.setCity(results.getString("city"));
	        	  ownerObj.setEmail(results.getString("email"));
	        	  ownerObj.setPhone(results.getInt("phone"));
	          }
	         catch(Exception e)
	         {
	             System.out.println("error in building the owner object");
	         }
	         return ownerObj;
	      }
}

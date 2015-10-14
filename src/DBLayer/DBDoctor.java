package DBLayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import ModelLayer.Doctor;

public class DBDoctor  implements IFDBDoctor{
	private  Connection con;

	public static void main(String[] args) {

		new DBDoctor();
	    }
	
	public DBDoctor() {
		con = DbConnection.getInstance().getDBcon();
		/*
		ArrayList<Doctor> liste = getAllDoctors(false);
		
		for(int i = 0; i < liste.size(); i++)
		{
			System.out.println(liste.get(i).getCity());
		}
		*/
		/*
		Doctor doctor = findDoctor(1, false);
		System.out.println(doctor.getFname());
		*/
	}
	
	public ArrayList<Doctor> getAllDoctors(boolean retriveAssociation)
    {
        return miscWhere("", retriveAssociation);
    }
	
	public Doctor findDoctor(int id, boolean retriveAssociation)
    {   String wClause = "  id = '" + id + "'";
        return singleWhere(wClause, retriveAssociation);
    }
	
	private ArrayList<Doctor> miscWhere(String wClause, boolean retrieveAssociation)
	{
            ResultSet results;
	    ArrayList<Doctor> list = new ArrayList<Doctor>();	
		
	    String query =  buildQuery(wClause);
  
            try{ // read the owner from the database
		Statement stmt = con.createStatement();
	 	stmt.setQueryTimeout(5);
	 	results = stmt.executeQuery(query);
	 	
	
		while( results.next() ){
	     	 Doctor doctorObj = new Doctor();
		 doctorObj = buildDoctor(results);	
                 list.add(doctorObj);	
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
	
	private Doctor singleWhere(String wClause, boolean retrieveAssociation)
	{
		ResultSet results;
		Doctor doctorObj = new Doctor();
                
	        String query =  buildQuery(wClause);
                //System.out.println(query);
		try{ // read the employee from the database
	 		Statement stmt = con.createStatement();
	 		stmt.setQueryTimeout(5);
	 		results = stmt.executeQuery(query);
	 		
	 		if( results.next() ){
	 			doctorObj = buildDoctor(results);
                            //assocaition is to be build
                            stmt.close();
                            if(retrieveAssociation)
                            {
                            }
			}
                        else{ //no employee was found
                        	doctorObj = null;
                        }
		}//end try	
	 	catch(Exception e){
	 		System.out.println("Query exception: "+e);
	 	}
		return doctorObj;
	}
	
	//method to build the query
		private String buildQuery(String wClause)
		{
		    String query="SELECT fname, lname, address, zipcode, (select city from cities where id = doctor.zipcode) as city, email, phone  FROM doctor";
			
			if (wClause.length()>0)
				query=query+" WHERE "+ wClause;
				
			return query;
		}
		
		//method to build an owner object
		private Doctor buildDoctor(ResultSet results)
	      {   Doctor doctorObj = new Doctor();
	          try{ // the columns from the table emplayee  are used
	        	  doctorObj.setFname(results.getString("fname"));
	        	  doctorObj.setLname(results.getString("lname"));
	        	  doctorObj.setAddress(results.getString("address"));
	        	  doctorObj.setZipcode(results.getInt("zipcode"));
	        	  doctorObj.setCity(results.getString("city"));
	        	  doctorObj.setEmail(results.getString("email"));
	        	  doctorObj.setPhone(results.getInt("phone"));
	          }
	         catch(Exception e)
	         {
	             System.out.println("error in building the owner object");
	         }
	         return doctorObj;
	      }
}

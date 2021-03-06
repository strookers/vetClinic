package DBLayer;
import java.sql.*;
import java.util.ArrayList;

import ModelLayer.*;

public class DBAnimal implements IFDBAnimal{
	private Connection con;
	private DBOwner dbOwner = new DBOwner();
	
	public static void main(String[] args) {

		new DBAnimal();
	    }

	public DBAnimal() {
		con = DbConnection.getInstance().getDBcon();
		/*
		ArrayList<Animal> liste = getAllAnimals(false);
		
		for(int i = 0; i < liste.size(); i++)
		{
			System.out.println(liste.get(i).getName());
			System.out.println(liste.get(i).getOwner().getFname());
			System.out.println("");
		}
		*/
		/*
		Animal animal = findAnimal(1, false);
		System.out.println(animal.getName() +" "+ animal.getSpecies());
		*/
	}
	
	public ArrayList<Animal> getAllAnimals(boolean retriveAssociation)
    {
        return miscWhere("", retriveAssociation);
    }
	
	public Animal findAnimal(int id, boolean retriveAssociation)
    {   String wClause = "  id = '" + id + "'";
        return singleWhere(wClause, retriveAssociation);
    }
	
	private ArrayList<Animal> miscWhere(String wClause, boolean retrieveAssociation)
	{
            ResultSet results;
	    ArrayList<Animal> list = new ArrayList<Animal>();	
		
	    String query =  buildQuery(wClause);
  
            try{ // read the owner from the database
		Statement stmt = con.createStatement();
	 	stmt.setQueryTimeout(5);
	 	results = stmt.executeQuery(query);
	 	
	
		while( results.next() ){
	     	 Animal animalObj = new Animal();
		 animalObj = buildAnimal(results);	
                 list.add(animalObj);	
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
	
	private Animal singleWhere(String wClause, boolean retrieveAssociation)
	{
		ResultSet results;
		Animal animalObj = new Animal();
                
	        String query =  buildQuery(wClause);
                //System.out.println(query);
		try{ // read the employee from the database
	 		Statement stmt = con.createStatement();
	 		stmt.setQueryTimeout(5);
	 		results = stmt.executeQuery(query);
	 		
	 		if( results.next() ){
	 			animalObj = buildAnimal(results);
                            //association is to be build
                            stmt.close();
                            if(retrieveAssociation)
                            {
                            }
			}
                        else{ //no employee was found
                        	animalObj = null;
                        }
		}//end try	
	 	catch(Exception e){
	 		System.out.println("Query exception: "+e);
	 	}
		return animalObj;
	}
	
	//method to build the query
		private String buildQuery(String wClause)
		{
		    String query="SELECT ownerid, name, (select species from species where id = animals.speciesid) as species, (select race from races where id = animals.raceid) as race FROM animals";
			
			if (wClause.length()>0)
				query=query+" WHERE "+ wClause;
				
			return query;
		}
		
		//method to build an owner object
		private Animal buildAnimal(ResultSet results)
	      {   Animal animalObj = new Animal();
	          try{ // the columns from the table animal are used
	        	  animalObj.setOwner(dbOwner.findOwner(results.getInt("ownerid"), false));
	        	  animalObj.setName(results.getString("name"));
	        	  animalObj.setSpecies(results.getString("species"));
	        	  animalObj.setRace(results.getString("race"));
	          }
	         catch(Exception e)
	         {
	             System.out.println("error in building the animal object");
	         }
	         return animalObj;
	      }

}

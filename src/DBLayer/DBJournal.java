package DBLayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import ModelLayer.Journal;

public class DBJournal  implements IFDBJournal{
	private  Connection con;
	private DBDoctor dbDoctor = new DBDoctor();
	private DBAnimal dbAnimal = new DBAnimal();

	public static void main(String[] args) {

		new DBJournal();
	    }
	
	public DBJournal() {
		con = DbConnection.getInstance().getDBcon();
		/*
		ArrayList<Journal> liste = getAllJournals(false);
		
		for(int i = 0; i < liste.size(); i++)
		{
			System.out.println("Dyr: " + liste.get(i).getAnimal().getName());
			System.out.println("Årsag: " + liste.get(i).getTitle());
			System.out.println("Læge:" + liste.get(i).getDoctor().getFname());
			System.out.println(" ");
		}
		*/
		/*
		Journal journal = findJournal(2, false);
		System.out.println(journal.getTitle());
		*/
	}
	
	public ArrayList<Journal> getAllJournals(boolean retriveAssociation)
    {
        return miscWhere("", retriveAssociation);
    }
	
	public Journal findJournal(int id, boolean retriveAssociation)
    {   String wClause = "  id = '" + id + "'";
        return singleWhere(wClause, retriveAssociation);
    }
	
	private ArrayList<Journal> miscWhere(String wClause, boolean retrieveAssociation)
	{
            ResultSet results;
	    ArrayList<Journal> list = new ArrayList<Journal>();	
		
	    String query =  buildQuery(wClause);
  
            try{ // read the owner from the database
		Statement stmt = con.createStatement();
	 	stmt.setQueryTimeout(5);
	 	results = stmt.executeQuery(query);
	 	
	
		while( results.next() ){
			Journal journalObj = new Journal();
			journalObj = buildJournal(results);	
                 list.add(journalObj);	
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
	
	private Journal singleWhere(String wClause, boolean retrieveAssociation)
	{
		ResultSet results;
		Journal journalObj = new Journal();
                
	        String query =  buildQuery(wClause);
                //System.out.println(query);
		try{ // read the employee from the database
	 		Statement stmt = con.createStatement();
	 		stmt.setQueryTimeout(5);
	 		results = stmt.executeQuery(query);
	 		
	 		if( results.next() ){
	 			journalObj = buildJournal(results);
                            //association is to be build
                            stmt.close();
                            if(retrieveAssociation)
                            {
                            }
			}
                        else{ //no employee was found
                        	journalObj = null;
                        }
		}//end try	
	 	catch(Exception e){
	 		System.out.println("Query exception: "+e);
	 	}
		return journalObj;
	}
	
	//method to build the query
		private String buildQuery(String wClause)
		{
		    String query="SELECT animalid, title, date, text, doctorid FROM journals";
			
			if (wClause.length()>0)
				query=query+" WHERE "+ wClause;
				
			return query;
		}
		
		//method to build an owner object
		private Journal buildJournal(ResultSet results)
	      {   Journal journalObj = new Journal();
	          try{ // the columns from the table emplayee  are used
	        	  journalObj.setAnimal(dbAnimal.findAnimal(results.getInt("animalid"), false));
	        	  journalObj.setTitle(results.getString("title"));
	        	  journalObj.setText(results.getString("text"));
	        	  journalObj.setDoctor(dbDoctor.findDoctor(results.getInt("doctorid"), false));
	          }
	         catch(Exception e)
	         {
	             System.out.println("error in building the owner object");
	         }
	         return journalObj;
	      }
}

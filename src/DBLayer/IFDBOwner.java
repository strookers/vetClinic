package DBLayer;

import java.util.ArrayList;

import ModelLayer.*;

public interface IFDBOwner {
	// get all employees
    public ArrayList<Owner> getAllOwners(boolean retriveAssociation);
    //get one employee having the ssn
    public Owner findOwner(int id, boolean retriveAssociation);
    //find one employee having the name
    //public Owner searchOwnerFname( String fname, boolean retriveAssociation);
    //public Owner searchOwnerLname( String Lname, boolean retriveAssociation);
    
    //insert a new employee
    //public int insertOwner(Owner emp) throws Exception;
    //update information about an employee
    //public int updateOwner(Owner emp);
	//public Owner searchOwnerId(int id, boolean b);
}

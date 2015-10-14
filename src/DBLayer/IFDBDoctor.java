package DBLayer;

import java.util.ArrayList;

import ModelLayer.*;

public interface IFDBDoctor {
	// get all employees
    public ArrayList<Doctor> getAllDoctors(boolean retriveAssociation);
    //get one doctor having the id
    public Doctor findDoctor(int id, boolean retriveAssociation);
    //find one employee having the name
    //public Owner searchOwnerFname( String fname, boolean retriveAssociation);
    //public Owner searchOwnerLname( String Lname, boolean retriveAssociation);
    
    //insert a new employee
    //public int insertOwner(Owner emp) throws Exception;
    //update information about an employee
    //public int updateOwner(Owner emp);
	//public Owner searchOwnerId(int id, boolean b);
}

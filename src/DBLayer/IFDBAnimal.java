package DBLayer;

import java.util.ArrayList;

import ModelLayer.*;

public interface IFDBAnimal {
	// get all animals
    public ArrayList<Animal> getAllAnimals(boolean retriveAssociation);
    //get one animal having the ssn
    //public Owner findAnimals(String empssn, boolean retriveAssociation);
    //find one animal having the name
    //public Owner searchAnimalFname( String fname, boolean retriveAssociation);
    //public Owner searchOwnerLname( String Lname, boolean retriveAssociation);
    
    //insert a new employee
    //public int insertOwner(Owner emp) throws Exception;
    //update information about an employee
    //public int updateOwner(Owner emp);
	//public Owner searchOwnerId(int id, boolean b);
}

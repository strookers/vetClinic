package ModelLayer;

import java.sql.Date;

public class Journal {
	private Animal animal;
	private String title;
	private Date date;
	private String text;
	private Doctor doctor;

	public Journal() {
		// TODO Auto-generated constructor stub
	}
	
	public Journal(Animal animal, String title, Date date, String text, Doctor doctor) {
		// TODO Auto-generated constructor stub
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

}

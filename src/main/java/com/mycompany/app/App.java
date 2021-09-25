package com.mycompany.app;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.FileReader;




//this class will be used to fill array list 
//form Passenger_list class
class Passenger {
private String name;
private String last_name;
private String type;
private int age;

Passenger (String name, String last_name,
		   String type, int age) {
	this.name = name;
	this.last_name = last_name;
	this.type = type;
	this.age = age;
}

public String get_name() {
	return this.name;
}
public String get_last_name() {
	return this.last_name;
}
public String get_type() {
	return this.type;
}
public int get_age() {
	return age;
}
}

//this class will be created when we taking 
//information from csv file
class Passenger_list {
 private ArrayList<Passenger> passenger_lst;

 Passenger_list () {
	passenger_lst = new ArrayList<>();
 }

 public void show_array_list() {
	for (int i = 0; i < this.passenger_lst.size(); i++) {
			System.out.println("Name: " + passenger_lst.get(i).get_name() + 
								" Last_Name: " + passenger_lst.get(i).get_last_name() +
								" Age: " + Integer.toString(passenger_lst.get(i).get_age()) +
								" Type: " + passenger_lst.get(i).get_type() + "\n\n");
	}
 }

public Passenger take_passenger_data() {
	Scanner input = new Scanner(System.in);
	System.out.println("enter your name");
	String name = input.nextLine();
	System.out.println("enter your last name");
	String last_name = input.nextLine();
	System.out.println("enter your type");
	String type = input.nextLine();
	System.out.println("enter your age");
	int age = input.nextInt();
	input.close();

	Passenger temp_passenger = new Passenger(name,last_name,type,age);
	return temp_passenger;
}

 public void add_passenger(Passenger temp_passenger) {
	this.passenger_lst.add(temp_passenger);
 }

public void delete_passenger(String pass_name) {
	for (int i = 0; i < this.passenger_lst.size(); i++) {
		if (pass_name.equals(passenger_lst.get(i).get_name())){
			passenger_lst.remove(passenger_lst.get(i));
		}
	}
 }

public ArrayList<Passenger> get_pass_array_list (){
	return passenger_lst;
}
}


class DB_worker{
private Passenger_list pl_object;

DB_worker () {
pl_object = new Passenger_list();	

}

public void take_from_db(){
	//taking one line from csv file, parsing it, 
	//adding it to passenger_lst arraylist
	try  {  
		BufferedReader br = new BufferedReader(new FileReader("../projects/base.csv"));  
		String line;
		while ((line = br.readLine()) != null) {
			//spliting line from db into string array
			String[] arr_of_str = line.split(",");  
			//converting age str to age int
			int temp_age;
			try { 
				temp_age = Integer.parseInt(arr_of_str[2]); 
			}catch (NumberFormatException e) {
				   temp_age = 0;
			}
			//adding those strings to array list 
			Passenger temp_passenger = new Passenger(arr_of_str[0], arr_of_str[1],
													 arr_of_str[3], temp_age); 

			this.pl_object.add_passenger(temp_passenger);
		}
	  
	}  
	catch (Exception e)  {  
		e.printStackTrace();  
	} 
}

public void save_to_db(){
	// adding to csv file
	try  (PrintWriter writer = new PrintWriter(new File("../projects/base.csv"))) {  
			StringBuilder sb = new StringBuilder();
			Passenger temp_passenger;

		for (int i = 0; i < pl_object.get_pass_array_list().size(); i++) {
			temp_passenger = pl_object.get_pass_array_list().get(i);
			sb.append(temp_passenger.get_name());
		    sb.append(',');
			sb.append(temp_passenger.get_last_name());
		    sb.append(',');
			sb.append(temp_passenger.get_type());
		    sb.append(',');
			sb.append(Integer.toString(temp_passenger.get_age()));
		    sb.append('\n');
			//writing string builder into a file
		    writer.write(sb.toString());
			//clearing string builder
			sb.setLength(0);
		}
	} catch (Exception e)  {  
		e.printStackTrace();  
	}
}

public Passenger_list get_pass_list(){
	return this.pl_object; //return Passenger_list object, that we will mainly use in a program

}
}

public class App 
{
    public static void main( String[] args )
    {
		DB_worker database = new DB_worker();
		//taking data from db
		database.take_from_db();
		//showing it
		database.get_pass_list().show_array_list();	
		//adding one passenger to array list
		database.get_pass_list().add_passenger(database.get_pass_list().take_passenger_data());
		//saving new array list to db
		database.save_to_db();

    }
}




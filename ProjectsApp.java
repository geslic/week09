/*
 * The exercises in this section build a menu-driven application. This application displays a list of available
 * operations. The user selects which operation to perform. A switch statement then routes the selection to the
 * appropriate method. Along the way you will add in proper exception handling. This is an important step to get
 * right when building any application. The purpose of the menu application is to perform CRUD operations on a
 * relational database that holds information on DIY projects. Throughout the coming weeks you will add to this
 * application to insert project rows, then materials, steps, and categories. You will fetch projects as a list
 * and fetch an individual project with all the details. You will modify rows and delete an entire project with
 * all associated detail (child) rows.
 */
package projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;

//import projects.dao.DbConnection;

public class ProjectsApp {
	/*
	 *  In this step you will use a Scanner to obtain input from a user from the Java console. A Scanner is a
	 *  Java object that can be used to read from a variety of sources. When you create the Scanner, you will set
	 *   its input source to System.in, which is the opposite of System.out. You use System.out to print to the
	 *   console. You will use the Scanner to read from the console. So, the user types in selections and the
	 *   Scanner reads the input and gives it to the application.Add a private instance variable named scanner. 
	 *   It is of type java.util.Scanner. Initialize it to a new Scanner object. Pass System.in to the
	 *   constructor. This will set the scanner so that it accepts user input from the Java console. 
	 *   It should look like this:
	 */
	private Scanner scanner = new Scanner (System.in);
	/*
	 * In this section, you are working with ProjectsApp.java in the projects package.

		In order to display a list of menu options you must store them somewhere. In this step you will 
		write the code that holds the list of operations.

		a.	Add a private instance variable named "operations". The type is List<String>. Initialize it using 
		List.of with the following value: "1) Add a project". To prevent the Eclipse formatter from reformatting
		the list, surround the variable declaration with 
		// @formatter:off and // @formatter:on so that it looks like this:
	 */
	
	// @formatter:off
	private List<String> operations = List.of(
					"1) Add a project"
	);
	// @formatter:on
	
	private ProjectService projectService;
	// Private instance variable of type ProjectService
	public ProjectsApp() {
		projectService = new ProjectService();
	}
	
	
	
	public static void main(String[] args) {
		/*
		 *  In this step you will call the method that processes the menu. In the main() method, create a new
		 *  ProjectsApp object and call the method: processUserSelections() method. The method takes zero
		 *  parameters and returns nothing.
		 */
		new ProjectsApp().processUserSelections();
		//DbConnection.getConnection();
	}
	
	private void processUserSelections() {
		// TODO Auto-generated method stub
		/*
		 * Now you can create the processUserSelections() method as an instance method. This method displays the 
		 * menu selections, gets a selection from the user, and then acts on the selection. Let Eclipse create
		 * the method for you by waving your mouse over the compiler error in the main() method
		 * (over the red squigglies). Eclipse will pop up a menu. Select "Create method processUserSelections()".
		 * In method processUserSelections():

			a.	Add a local variable:
		 */
		boolean done = false;
		
		// b.	Add a while loop below the local variable. Loop until the variable done is true.
		
		while(!done) {
			/*
			 * c.	Inside the while loop, add a try/catch block. The catch block should catch Exception. 
			 * Inside the catch block print the Exception message. Call the toString() method on the Exception 
			 * object provided to the catch block. This is done by simply concatenating the Exception object onto
			 * a String literal. When you do this Java implicitly calls the toString() method behind the scenes.
			 * 	d.	Inside the try block, assign an int variable named selection to the return value from the 
			 * method getUserSelection().
			 */
			try {
				int selection = getUserSelection();
				
				switch(selection) {
				 case -1: done = exitMenu();
				 	break;
				 
				 case 1: createProject();
				 	break;
				 
				 default:
					 System.out.println("\n" + selection + " is not a valid selection. Try again.");
					 break;
			}
		}
		catch(Exception e) {
			System.out.println("\nError: " + e + " Try again.");		
			}
		}
	}
	private void createProject() {
		// TODO Auto-generated method stub

		String projectName = getStringInput("Enter the project name");
		BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
		BigDecimal actualHours = getDecimalInput("Enter the actual hours");
		Integer difficulty = getIntInput("Enter the project difficulty (1-5)");
		String notes = getStringInput("Enter the project notes");
		
		Project project = new Project();
		
		/*
		 * Call the appropriate setters on the Project object to set projectName, estimatedHours, actualHours, 
		 * difficulty and notes. For example, to add the project name on the Project object, 
		 * call setProjectName() and pass it projectName.  
		 */
		project.setProjectName(projectName);
		project.setEstimatedHours(estimatedHours);
		project.setActualHours(actualHours);
		project.setDifficulty(difficulty);
		project.setNotes(notes);
		
		/*
		 * Call the addProject() method on the projectService object. Pass it the Project object. 
		 * This method will be created shortly. This method should return an object of type Project. 
		 * Assign it to variable dbProject. 
		 */
		Project dbProject = projectService.addProject(project);
		
		/*
		 * Print a success message to the console "You have successfully created project: " + dbProject. 
		 * The value returned from projectService.addProject() is different from the Project object passed to 
		 * the method. It contains the project ID that was added by MySQL. 
		 */
		System.out.println("You have successfully created project: " + dbProject);	
	}
	/*
	 * Create the method getDecimalInput(). The easiest way to do this is to create the method body, 
	 * then copy the method contents from getIntInput() and paste it into the method body. 
	 * Fix the following lines: 
	 * a. The line in the try block. Change it to: return new BigDecimal(input).setScale(2);
	 * b. The message in DbException. Change it to: input + " is not a valid decimal number.
	 */

	private BigDecimal getDecimalInput(String prompt) {
		// TODO Auto-generated method stub
		String input = getStringInput(prompt);
		
		if(Objects.isNull(input)) {
			return null;
		}
		try {
			// Create the BigDecimal object and set it to two decimal places (the scale).
			return new BigDecimal(input).setScale(2);
			// This will create a new BigDecimal object and set the number of decimal places (the scale) to 2.
		}
		catch(NumberFormatException e) {
			throw new DbException(input + "is not a valid number.");
		}
	}

	private boolean exitMenu() {
		// TODO Auto-generated method stub
		System.out.println("Exiting the menu.");		
		return true;
	}
	
	/* Create the method getUserSelection(). It takes no parameters and returns an int. This method will print
	 * the operations and then accept user input as an Integer. In the getUserSelection() method:
	 * 
	 * 	a.	Make a method call to the method printOperations(). This method takes no parameters and returns
	 * nothing.
	 * 	b.	Add a method call to getIntInput(). Assign the results of the method call to a variable named input
	 * of type Integer. The method getIntInput(), which you haven't written yet. It will return the user's menu
	 * selection. The value may be null. Pass the String literal "Enter a menu selection" as a parameter to the
	 * method.
	 * 	c.	Add a return statement that checks to see if the value in local variable input is null. If so, 
	 * return -1. (The value -1 will signal the menu processing method to exit the application.) Otherwise,
	 * return the value of input. The method should look like this:
	 */
	private int getUserSelection() {
		// TODO Auto-generated method stub
		printOperations();
		
		Integer input = getIntInput("Enter a menu selection");
		
		return Objects.isNull(input)? -1 : input;
	}
	
	private void printOperations() {
		// TODO Auto-generated method stub
		/*
		 * Create the method printOperations(). It takes no parameters and returns nothing. This method does just what it says, it prints each available selection on a separate line in the console. In the printOperations() method:
		 * a.	Print a line to the console:
		 */
		System.out.println("\nThese are the available selections. Press the Enter key to quit:");
		
		/*b.	Print all the available menu selections, one on each line. Each line should be indented slightly
		 * (2 or 3 spaces). Use any strategy that you choose to print the instructions. If you use a Lambda 
		 * expression as shown in the video, it should look like this:
		 */
		
		/* With Lambda expression */
		operations.forEach(line -> System.out.println(" " + line));
		
		/*
		 * Every List object must implement the forEach() method. forEach() takes a Consumer interface object as
		 * a parameter. Consumer has a single abstract method, accept(). The accept() method takes a single
		 * parameter and returns nothing. The Lambda expression has a single parameter and System.out.println and 
		 * returns nothing. The Lambda expression thus matches the requirements for the accept() method.
		 * If you don't want to use a Lambda expression, you can use an enhanced for loop to print the 
		 * instructions.
		 */
		
		/* With enhanced for loop */
		// for(String line : operations) {
		// System.out.println(" " + line);
		// }
		
		/*
		 * There will be several user input methods that return different types of objects. Due to the way the
		 * java.util.Scanner object was implemented, the safest way to get an input line from the user is to 
		 * input it as a String and then convert it to the appropriate type. With this design, all the input 
		 * methods will ultimately call the String input method, which actually prints the prompt and uses the 
		 * Scanner to get the user's input. In this step, you will write a method that returns an Integer value.

Create the method getIntInput. It takes a single parameter of type String named prompt. This method accepts input
from the user and converts it to an Integer, which may be null. It is called by getUserSelection() and will be
called by other data collection methods that require an Integer. Inside the method body:

a.	Assign a local variable named input of type String to the results of the method call getStringInput(prompt).

b.	Test the value in the variable input. If it is null, return null. Use Objects.isNull() for the null check.

c.	Create a try/catch block to test that the value returned by getStringInput() can be converted to an Integer.
The catch block should accept a parameter of type NumberFormatException.

i.	In the try block, convert the value of input, which is a String, to an Integer and return it. If the 
conversion is not possible, a NumberFormatException is thrown. The message in the NumberFormatException is 
totally obscure so it will get fixed in the catch block. Here's what the contents of the try block should look
like:

return Integer.valueOf(input);

ii.    In the catch block throw a new DbException with the message, input + " is not a valid number. Try again."
		 */
	}
	
	private Integer getIntInput(String prompt) {
		// TODO Auto-generated method stub
		String input = getStringInput(prompt);
		
		if(Objects.isNull(input)) {
			return null;
		}
		try {
			return Integer.valueOf(input);
		}
		catch(NumberFormatException e) {
			throw new DbException(input + "is not a valid number.");
		}
	}
	/*
	 *	Now create the method that really prints the prompt and gets the input from the user. Create the method
	 *getStringInput(). It should have a single parameter of type String named prompt. This is the lowest level
	 *input method. The other input methods call this method and convert the input value to the appropriate type.
	 *This will also be called by methods that need to collect String data from the user. It should return a 
	 *String.
	 *Inside the method:

a.     Print the prompt using System.out.print(prompt + ": ") to keep the cursor on the same line as the prompt. (Note: print and not println!)

b.     Assign a String variable named input to the results of a method call to scanner.nextLine().

c.      Test the value of input. If it is blank return null. Otherwise return the trimmed value.

d.     The method should look like this:
	 */
	private String getStringInput(String prompt) {
		// TODO Auto-generated method stub
		System.out.print(prompt + ": ");
		String input = scanner.nextLine();
		
		return input.isBlank() ? null : input.trim();
	}

}

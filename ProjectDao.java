package projects.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import projects.entity.Project;
import projects.exception.DbException;
import provided.util.DaoBase;

// ProjectDao should extend DaoBase. If it doesn't, do that now or you will run into problems later. 
@SuppressWarnings("unused")
public class ProjectDao extends DaoBase { 
	/*
	 * In this section, you will be adding constants into the ProjectDao class. These are placed at 
	 * the top of the class just inside the class body. Java does not have a "constant" keyword. Instead, 
	 * a constant is specified using static final. Constants can either be public or private. In this file 
	 * all the constants should be private. 
	 */
	private static final String CATEGORY_TABLE = "category";
	private static final String MATERIAL_TABLE = "material";
	private static final String PROJECT_TABLE = "project";
	private static final String PROJECT_CATEGORY_TABLE = "project_category";
	private static final String STEP_TABLE = "step";
	/**
	 * 
	 * @param project The project object to insert.
	 * @return The Project object with the primary key.
	 * @throws DbException Thrown if any error occurs inserting the row.
	 */
	public Project insertProject(Project project) {
		// TODO Auto-generated method stub
		
		/*
		 * In this section, you will be working exclusively in the method insertProject() in ProjectDao.java.
		 * 1.	Write the SQL statement that will insert the values from the Project object passed to the
		 * insertProject() method. Remember to use question marks as placeholder values for the parameters passed
		 * to the PreparedStatement. Add the fields project_name, estimated_hours, actual_hours, difficulty, 
		 * and notes. Make sure to add the correct blank spaces between words or it won't work. 
		 * It should look like this:
		 */
		// @formatter:off
		String sql = ""
			+ "INSERT INTO " + PROJECT_TABLE + " "
			+ "(project_name, estimated_hours, actual_hours, difficulty, notes) "
			+ "VALUES "
			+ "(?, ?, ?, ?, ?)";
		// @formatter:on
		
		/*
		 * 2.	Obtain a connection from DbConnection.getConnection(). Assign it a variable of type Connection 
		 * named conn in a try-with-resource statement. Catch the SQLException in a catch block added to the 
		 * try-with-resource. From within the catch block, throw a new DbException. The DbException constructor 
		 * should take the SQLException object passed into the catch block.
		 * 
		 */
		try(Connection conn = DbConnection.getConnection()) {
			/*
			 * 3.	Start a transaction. Inside the try block, start a transaction by calling startTransaction() 
			 * and passing in the Connection object. startTransaction() is a method in the base class, DaoBase.
			 * 
			 * 4.	Obtain a PreparedStatement object from the Connection object. Inside the try block and below 
			 * startTransaction(), add another try-with-resource statement to obtain a PreparedStatement from the 
			 * Connection object.

				a.	Pass the SQL statement as a parameter to conn.prepareStatement().

				b.     Add a catch block to the inner try block that catches Exception. 
				In the catch block, roll back the transaction and throw a DbException initialized with the 
				Exception object passed into the catch block. This will ensure that the transaction is rolled 
				back when an exception is thrown.

				c.      The method should look like this at this point:
			 */
			startTransaction(conn); // Transaction started
			try(PreparedStatement stmt = conn.prepareStatement(sql)){
				/*
				 * 5.	In this step you will set the project details as parameters in the PreparedStatement
				 * object. Inside the inner try block, set the parameters on the Statement. Use the convenience
				 * method in DaoBase setParameter(). This method handles null values correctly.
				 * (See the JavaDoc comments on that method for details.) 
				 * Add these parameters: projectName, estimatedHours, actualHours, difficulty, and notes. 
				 * When done it should look like this:
				 */
				setParameter(stmt, 1, project.getProjectName(), String.class);
				setParameter(stmt, 2, project.getEstimatedHours(), BigDecimal.class);
				setParameter(stmt, 3, project.getActualHours(), BigDecimal.class);
				setParameter(stmt, 4, project.getDifficulty(), Integer.class);
				setParameter(stmt, 5, project.getNotes(), String.class);
				/*
				 * 6.	Now you can save the project details. Perform the insert by calling executeUpdate() on 
				 * the PreparedStatement object. Do not pass any parameters to executeUpdate() or it will reset 
				 * all the parameters leading to an obscure error.
				 */
				stmt.executeUpdate();
				/*
				 * 7.	Obtain the project ID (primary key) by calling the convenience method in DaoBase, 
				 * getLastInsertId(). (See the JavaDoc documentation on that method for details.) 
				 * Pass the Connection object and the constant PROJECT_TABLE to getLastInsertId(). Assign the 
				 * return value to an Integer variable named projectId.
				 * 
				 * 8.	Commit the transaction by calling the convenience method in DaoBase, commitTransaction(). 
				 * Pass the Connection object to commitTransaction() as a parameter.
				 * 
				 * 9.	Set the projectId on the Project object that was passed into insertProject and return it. 
				 */
				Integer projectId = getLastInsertId(conn, PROJECT_TABLE);
				commitTransaction(conn);
				
				project.setProjectId(projectId);
				return project;
			}
			catch(Exception e) { // Transaction rolled back
				rollbackTransaction(conn);
				throw new DbException(e);
			}
		}
		catch(SQLException e) {
			throw new DbException(e);
			}
		}	
	}

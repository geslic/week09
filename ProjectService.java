package projects.service;

import projects.dao.ProjectDao;
import projects.entity.Project;

public class ProjectService {
	private ProjectDao projectDao = new ProjectDao();
	/*
	 * This method simply calls the DAO class to insert a project row.
	 * 
	 * @param project The {@link Project} object.
	 * @return The Project object with the newly generated primary key value.
	 */
	public Project addProject(Project project) {
		// TODO Auto-generated method stub
		/*
		 * In method addProject(), call the method insertProject() on the projectDao object. 
		 * The method should take a single parameter. Pass it the Project parameter and return 
		 * the value from the method. The addProject() method should look like this: 
		 * public Project addProject(Project project) {   return projectDao.insertProject(project); } 
		 */
		return projectDao.insertProject(project);
	}

}

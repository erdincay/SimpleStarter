package cs320.model;

import java.util.Date;
import java.util.List;

public interface ProjectsI extends List<ProjectD>{

	public static final String fstrProjectsContent = "allprojects";

	public ProjectsI generateProjects(int records_num); //sorry, the para 'records_num' is not used.
	public ProjectD getProjectByID(long id);
	public long saveProject(UserD author, String title, String description,	int target, int duration, Date start_date);
}

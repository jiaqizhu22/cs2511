package unsw.enrolment;
import java.util.ArrayList;
import java.util.List;

public class Student {

    private String zid;
    private ArrayList<Enrolment> enrolments;

	public Student(String zid) {
        this.zid = zid;
        enrolments = new ArrayList<>();
    }

	public String getZID() {
		return zid;
	}

    public ArrayList<Enrolment> getEnrolments() {
        return enrolments;
    }

    public Boolean meetPrereqs(Course course) {
        List<Course> prereqs = course.getPrereqs();
        for (Enrolment e : enrolments) {
            Course currCourse = e.getCourse();
            if (prereqs.contains(currCourse) & e.getGrade().getMark() >= 60) {
                prereqs.remove(currCourse);
            }
        }
        if (prereqs.size() == 0) {
            return true;
        } 
        return false;
    }

}

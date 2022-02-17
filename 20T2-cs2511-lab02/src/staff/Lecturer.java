package staff;

import java.sql.Date;

/**
 * A lecturer
 * @author Jiaqi Zhu
 * 
 */
public class Lecturer extends StaffMember {

    /**
     * this is the school (e.g. CSE) that the lecturer belongs to
     */
    public String school;
    
    /**
     * A for Associate Lecturer
     * B for a Lecturer
     * C for Senior Lecturer
     */
    public String academicStatus;

    /**
     * date as String constructor
     * @param name name of lecturer
     * @param salary salary of lecturer
     * @param hireDate the date when the lecturer is hired
     * @param endDate the date when the lecturer's contract ends
     * @param school the school the lecturer belongs to
     * @param academicStatus the academic status of the lecturer
     */
    public Lecturer(String name, int salary, String hireDate, String endDate, String school, String academicStatus) {
        super(name, salary, hireDate, endDate);
        this.school = school;
        this.academicStatus = academicStatus;
    }

    /**
     * date as Date constructor
     */
    public Lecturer(String name, int salary, Date hireDate, Date endDate, String school, String academicStatus) {
        super(name, salary, hireDate, endDate);
        this.school = school;
        this.academicStatus = academicStatus;
    }

    /**
     * 
     * @return the school the lecturer belongs to
     */
    public String getSchool() {
        return school;
    }

    /**
     * 
     * @param school the school the lecturer belongs to
     */
    public void setSchool(String school) {
        this.school = school;
    }

    /**
     * 
     * @return the academic status of the lecturer
     */
    public String getAcademicStatus() {
        return academicStatus;
    }

    /**
     * 
     * @param academicStatus the academic status of the lecturer
     */
    public void setAcademicStatus(String academicStatus) {
        this.academicStatus = academicStatus;
    }

    @Override
    public String toString() {
        String str = super.toString();
        return str + ", school=" + school + ", academic status=" + academicStatus;
    }

    @Override
    public boolean equals(Object obj) {
        if(super.equals(obj) == false) { return false;}

        Lecturer other = (Lecturer)obj;
        if (other.school.equals(this.school) && other.academicStatus.equals(this.academicStatus)) {
            return true;
        } else {
            return false;
        }
    }
}
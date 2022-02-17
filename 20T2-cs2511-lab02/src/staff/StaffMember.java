package staff;

import java.sql.Date;

/**
 * A staff member
 * @author Jiaqi Zhu
 *
 */
public class StaffMember {

    public String name;
    public int salary;
    public Date hireDate;
    public Date endDate;

    /**
     * 
     * @param name name of the staff member
     * @param salary salary of the staff member
     * @param hireDate the date when the member is hired
     * @param endDate the date when the member's contract ends
     */
    public StaffMember(String name, int salary, String hireDate, String endDate) {
        this.name = name;
        this.salary = salary;
        this.hireDate = Date.valueOf(hireDate);
        this.endDate = Date.valueOf(endDate);
    }
    
    public StaffMember(String name, int salary, Date hireDate, Date endDate) {
        this.name = name;
        this.salary = salary;
        this.hireDate = hireDate;
        this.endDate = endDate;
    }

    /**
     * 
     * @return staff member's name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name staff member's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return staff member's salary
     */
    public int getSalary() {
        return salary;
    }

    /**
     * 
     * @param salary staff member's salary
     */
    public void setSalary(int salary) {
        this.salary = salary;
    }

    /**
     * 
     * @return staff member's hire date
     */
    public Date gethireDate() {
        return hireDate;
    }

    /**
     * 
     * @param newDate a new hire date
     */
    public void sethireDate(Date newDate) {
        this.hireDate = newDate;
    }

    /**
     * 
     * @return staff member's end date
     */
    public Date getendDate() {
        return endDate;
    } 

    /**
     * 
     * @param newDate a new end date
     */
    public void setendDate(Date newDate) {
        this.endDate = newDate;
    }

    @Override
    public String toString() {
        return getClass().toString()+", name="+name+", salary="+salary+", hire date="+hireDate+", end date="+endDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        StaffMember m = (StaffMember) obj;
        return (name.equals(m.name) && salary==m.salary && hireDate.compareTo(m.hireDate)==0 && endDate.compareTo(m.endDate)==0);
    }

}

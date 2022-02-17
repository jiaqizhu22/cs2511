package staff.test;

import java.sql.Date;
import java.time.LocalDate;

import staff.Lecturer;
import staff.StaffMember;

/**
 * tests for StaffMember
 * @author Jiaqi Zhu
 * 
 */
public class StaffTest {
    
    public static void printStaffDetails(StaffMember member) {
        String str = member.toString();
        System.out.println(str);
    }

    public static void main(String[] args) {
        StaffMember hayden = new StaffMember("Hayden Smith", 200000, Date.valueOf(LocalDate.of(2010, 1, 1)), Date.valueOf(LocalDate.of(2060, 12, 31)));

        Lecturer marc = new Lecturer("Marc Chee", 100000, Date.valueOf(LocalDate.of(2010, 1, 1)), Date.valueOf(LocalDate.of(2060, 12, 31)), "CSE", "B");

        //print
        printStaffDetails(hayden);
        printStaffDetails(marc);

        //test lecturer != staffmember
        Lecturer hayden2 = new Lecturer("Hayden Smith", 200000, Date.valueOf(LocalDate.of(2010, 1, 1)), Date.valueOf(LocalDate.of(2060, 12, 31)), "CSE", "C");
        assert(hayden2.equals(hayden) == false);

        //test reflexive
        assert(hayden.equals(hayden) == true);
        assert(marc.equals(marc) == true);
        
        //test symmetric
        StaffMember smith = new StaffMember("Hayden Smith", 200000, Date.valueOf(LocalDate.of(2010, 1, 1)), Date.valueOf(LocalDate.of(2060, 12, 31)));
        assert(smith.equals(hayden) == true);
        assert(hayden.equals(smith) == true);
        
        Lecturer chee = new Lecturer("Marc Chee", 100000, Date.valueOf(LocalDate.of(2010, 1, 1)), Date.valueOf(LocalDate.of(2060, 12, 31)), "CSE", "B");
        assert(chee.equals(marc) == true);
        assert(marc.equals(chee) == true);

        //test transitive
        StaffMember temp = new StaffMember("Hayden Smith", 200000, Date.valueOf(LocalDate.of(2010, 1, 1)), Date.valueOf(LocalDate.of(2060, 12, 31)));
        assert(smith.equals(hayden) == true);
        assert(hayden.equals(temp) == true);
        assert(smith.equals(temp) == true);

        Lecturer copy = new Lecturer("Marc Chee", 100000, Date.valueOf(LocalDate.of(2010, 1, 1)), Date.valueOf(LocalDate.of(2060, 12, 31)), "CSE", "B");
        assert(chee.equals(marc) == true);
        assert(marc.equals(copy) == true);
        assert(chee.equals(copy) == true);

        //test consistent
        StaffMember ashesh = new StaffMember("Ashesh Mahidadia", 50000, Date.valueOf(LocalDate.of(2013, 1, 1)), Date.valueOf(LocalDate.of(2065, 12, 31)));
        assert(smith.equals(hayden) == true);
        assert(smith.equals(hayden) == true);
        assert(smith.equals(hayden) == true);
        assert(ashesh.equals(smith) == false);
        assert(ashesh.equals(smith) == false);
        assert(ashesh.equals(smith) == false);

        Lecturer matthew = new Lecturer("Matthew Perry", 10000, Date.valueOf(LocalDate.of(2012, 1, 1)), Date.valueOf(LocalDate.of(2055, 12, 31)), "CSE", "A");
        assert(marc.equals(chee) == true);
        assert(marc.equals(chee) == true);
        assert(marc.equals(chee) == true);
        assert(matthew.equals(marc) == false);
        assert(matthew.equals(marc) == false);
        assert(matthew.equals(marc) == false);

        //test null
        assert(hayden.equals(null) == false);
        assert(marc.equals(null) == false);
        
    }



}
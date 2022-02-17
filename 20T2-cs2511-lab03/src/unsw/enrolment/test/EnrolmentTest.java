package unsw.enrolment.test;

import unsw.enrolment.Course;
import unsw.enrolment.CourseOffering;
import unsw.enrolment.Lecture;
import unsw.enrolment.Student;
import unsw.enrolment.Enrolment;
import unsw.enrolment.Grade;

import java.util.ArrayList;
import java.util.List;
import java.time.DayOfWeek;
import java.time.LocalTime;

public class EnrolmentTest {

    public static void main(String[] args) {

        // Create courses
        Course comp1511 = new Course("COMP1511", "Programming Fundamentals");
        Course comp1531 = new Course("COMP1531", "Software Engineering Fundamentals");
        comp1531.addPrereq(comp1511);
        Course comp2521 = new Course("COMP2521", "Data Structures and Algorithms");
        comp2521.addPrereq(comp1511);

        CourseOffering comp1511Offering = new CourseOffering(comp1511, "19T1");
        CourseOffering comp1531Offering = new CourseOffering(comp1531, "19T1");
        CourseOffering comp2521Offering = new CourseOffering(comp2521, "19T2");

        // TODO Create some sessions for the courses
        Lecture Lec1 = new Lecture("ElecEng204", DayOfWeek.of(1), LocalTime.of(9,0,0), LocalTime.of(11,0,0), "Marc Chee");
        Lecture Lec2 = new Lecture("Ainsworth101", DayOfWeek.of(3), LocalTime.of(14,0,0), LocalTime.of(16,0,0), "Hayden Smith");
        Lecture Lec3 = new Lecture("Online", DayOfWeek.of(4), LocalTime.of(16,0,0), LocalTime.of(18,0,0), "Ashesh");

        // TODO Create a student
        Student me = new Student("z5261703");

        // TODO Enrol the student in COMP1511 for T1 (this should succeed)
        assert(me.meetPrereqs(comp1511) == true);
        Enrolment enrolCOMP1511 = new Enrolment(comp1511Offering, me);
        

        // TODO Enrol the student in COMP1531 for T1 (this should fail as they
        // have not met the prereq)
        assert(me.meetPrereqs(comp1531) == false);
        

        // TODO Give the student a passing grade for COMP1511
        Grade myCOMP1511Grade = new Grade(86, "HD");
        enrolCOMP1511.setGrade(myCOMP1511Grade);

        // TODO Enrol the student in 2521 (this should succeed as they have met
        // the prereqs)
        if (me.meetPrereqs(comp2521)) {
            Enrolment enrolCOMP2521 = new Enrolment(comp2521Offering, me);
        }
        
    }
}

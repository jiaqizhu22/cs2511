package unsw.enrolment.test;

import java.time.DayOfWeek;
import java.time.LocalTime;

import unsw.enrolment.Course;
import unsw.enrolment.CourseOffering;
import unsw.enrolment.Enrolment;
import unsw.enrolment.Lecture;
import unsw.enrolment.Student;
import unsw.enrolment.Tutorial;

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

        // Create some sessions for the courses
        Lecture lecture1511 = new Lecture(comp1511Offering, "Rex Vowels", DayOfWeek.TUESDAY, LocalTime.of(12, 0),LocalTime.of(14, 0), "Andrew Taylor");
        Lecture lecture1531 = new Lecture(comp1531Offering, "CLB 5", DayOfWeek.MONDAY, LocalTime.of(9, 0),LocalTime.of(11, 0), "Aarthi Natarajan");
        Lecture lecture2521 = new Lecture(comp2521Offering, "Clancy Auditorium", DayOfWeek.FRIDAY, LocalTime.of(15, 0),LocalTime.of(17, 0), "Ashesh Mahidadia");

        Tutorial tute1531 = new Tutorial(comp1531Offering, "Quad 1041", DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(19,0), "Hugh Chan");

        // Create a student
        Student student = new Student("z5555555");

        // Enrol the student in COMP1511 for T1 (this should succeed)
        Enrolment comp1511Enrolment = comp1511Offering.enrol(student, lecture1511);

        if (comp1511Enrolment != null)
            System.out.println("Enrolled in COMP1511");

        // Enrol the student in COMP1531 for T1 (this should fail as they
        // have not met the prereq)
        Enrolment comp1531Enrolment = comp1531Offering.enrol(student, lecture1531, tute1531);

        if (comp1531Enrolment == null)
            System.out.println("Can't enrol in COMP1531");

        // Give the student a passing grade for COMP1511
//        comp1511Enrolment.assignMark(65);

        // Assign marks for comp1511
        // TODO Give the student a passing mark for assignment 1
        comp1511Enrolment.addMark(60, "ass1");

        // TODO Give the student a passing mark for milestone 1 of assignment 2
        comp1511Enrolment.addMark(60, "ass2", "milestone 1");
        // TODO Give the student a passing mark for milestone 2 of assignment 2
        comp1511Enrolment.addMark(64, "ass2", "milestone 2");
        // TODO Give the student an assignment 2 mark which is the average of
        // milestone 1 and 2
        int sum = 0;
        int count = 0;
        for (Mark mark : comp1511Enrolment.getMarks()) {
            if (mark.isOfProject("ass2")) {
                sum += mark.getMark();
                count++;
            }
        }
        int average = sum/count;
        comp1511Enrolment.addMark(average, "ass2");
        // TODO Give the student a prac mark which is the sum of assignment 1
        // and 2
        sum = 0;
        for (Mark mark:comp1511Enrolment.getMarks()) {
            if (mark.ifOfStage("")) {
                sum += mark.getMark();
            }
        }
        comp1511Enrolment.addSubGrade(sum, "prac");
        // TODO Give the student a passing exam mark.
        comp1511Enrolment.addSubGrade(70, "exam");
        // Enrol the student in 2521 (this should succeed as they have met
        // the prereqs)
        comp1511Enrolment.assignMark();
        Enrolment comp2521Enrolment = comp2521Offering.enrol(student, lecture2521);

        if (comp2521Enrolment != null)
            System.out.println("Enrolled in COMP2521");
    }
}

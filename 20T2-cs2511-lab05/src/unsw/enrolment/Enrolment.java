package unsw.enrolment;

import java.util.ArrayList;
import java.util.List;

public class Enrolment {

    private CourseOffering offering;
    private Grade grade;
    private Student student;
    private List<Session> sessions;
    private List<Mark> marks;
    private List<Grade> subGrades;

    public Enrolment(CourseOffering offering, Student student, Session... sessions) {
        this.offering = offering;
        this.student = student;
        this.grade = null; // Student has not completed course yet.
        student.addEnrolment(this);
        offering.addEnrolment(this);
        this.sessions = new ArrayList<>();
        for (Session session : sessions) {
            this.sessions.add(session);
        }
        this.marks = new ArrayList<>();
    }

    public Course getCourse() {
        return offering.getCourse();
    }

    public String getTerm() {
        return offering.getTerm();
    }

    public boolean hasPassed() {
        return grade != null && grade.isPassing();
    }

    public void addMark(int mark, String project) {
        Mark newMark = new Mark(mark, project);
        marks.add(newMark);
    }

    public ArrayList<> getMarks() {
        return marks;
    }

    public void addSubGrade(int mark, String exam) {
        Grade newGrade = new Grade(mark, exam);
        subGrades.add(newGrade);
    }
//    Whole course marks can no longer be assigned this way.
//    public void assignMark(int mark) {
//        grade = new Grade(mark);
//    }
    public void assignMark() {
        int sum = 0;
        int count = 0;
        for (Grade g : subGrades) {
            sum += g.getMark();
            count++;
        }
        int mark = sum/count;
        grade = new Grade(mark);
    }
}

package unsw.enrolment;

public class Grade {
    private int mark;
    private String grade;

    public Grade(int mark, String grade) {
        this.mark = mark;
        this.grade = grade;
    }

    public String getGrade() {
        return grade;
    }

    public int getMark() {
        return mark;
    }

}

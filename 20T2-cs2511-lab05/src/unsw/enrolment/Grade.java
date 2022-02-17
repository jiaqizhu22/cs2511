package unsw.enrolment;

public class Grade {
    private int mark;
    private String grade;
    private String exam;

    public Grade(int mark) {
        this.mark = mark;
        this.exam = "";
        if (mark < 50)
            grade = "FL";
        else if (mark < 65)
            grade = "PS";
        else if (mark < 75)
            grade = "DN";
        else
            grade = "HD";
    }

    public Grade(int mark, String exam) {
        this.mark = mark;
        this.exam = exam;
        if (mark < 50)
            grade = "FL";
        else if (mark < 65)
            grade = "PS";
        else if (mark < 75)
            grade = "DN";
        else
            grade = "HD";
    }

    public boolean isPassing() {
        return mark >= 50;
    }

    public int getMark() {
        return mark;
    }
}

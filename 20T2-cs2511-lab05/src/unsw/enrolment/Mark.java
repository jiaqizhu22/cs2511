package unsw.enrolment;

public class Mark {
    private int mark;
    private String project;
    private String stage;

    public Mark(int mark, String project) {
        this.mark = mark;
        this.project = project;
        this.stage = "";
    }

    public Mark(int mark, String project, String stage) {
        this.mark = mark;
        this.project = project;
        this.stage = stage;
    }

    public int getMark() {
        return mark;
    }

    public String getProject() {
        return project;
    }

    public String getStage() {
        return stage;
    }

    public String isOfStage(String stage) {
        if(getStage().equals(stage)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isOfProject(String project) {
        if(getProject().equals(project)) {
            return true;
        } else {
            return false;
        }
    }
}

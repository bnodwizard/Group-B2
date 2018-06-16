package System;

public class PostTable {
    int id;
    String time;
    String location;
    String problem;
    String title;
    String descreption;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescreption() {
        return descreption;
    }

    public void setDescreption(String descreption) {
        this.descreption = descreption;
    }
    public PostTable(int id, String time, String location, String problem, String title, String descreption) {
        this.id = id;
        this.time = time;
        this.location = location;
        this.problem = problem;
        this.title = title;
        this.descreption = descreption;
    }
}


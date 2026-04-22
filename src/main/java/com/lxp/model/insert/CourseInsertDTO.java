package com.lxp.model.DTO;

public class CourseInsertDTO {
    private String courseTitle;
    private String courseDescription;

    public CourseInsertDTO() {
    }

    public CourseInsertDTO(String courseTitle, String courseDescription) {
        this.courseTitle = courseTitle;
        this.courseDescription = courseDescription;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    @Override
    public String toString() {
        return "CourseInsertDTO{" + "courseTitle='" + courseTitle + '\'' + ", courseDescription='"
                + courseDescription + '\'' + '}';
    }
}

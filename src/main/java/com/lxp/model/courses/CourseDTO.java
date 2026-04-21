package com.lxp.model.courses;

public class CourseDTO {
    private Long courseId;
    private String courseTitle;
    private String courseDescription;
    private boolean isPublic;
    private boolean isDeleted;

    public CourseDTO() {
    }

    public CourseDTO(String courseTitle, String courseDescription) {
        this.courseTitle = courseTitle;
        this.courseDescription = courseDescription;
    }

    public CourseDTO(Long courseId, String courseTitle, String courseDescription, boolean isPublic,
            boolean isDeleted) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.courseDescription = courseDescription;
        this.isPublic = isPublic;
        this.isDeleted = isDeleted;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
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

    public boolean isCoursePublic() {
        return isPublic;
    }

    public boolean isCourseDeleted() {
        return isDeleted;
    }

    @Override
    public String toString() {
        return "CourseDTO{" + "courseTitle='" + courseTitle + '\'' + ", courseDescription='"
                + courseDescription + '\'' + '}';
    }
}


package com.lxp.model.courses;

public class Course {
    private Long courseId;
    private String courseTitle;
    private String courseDescription;
    private boolean isPublic;
    private boolean isDeleted;

    public Course() {
    }

    public Course(Long courseId, String courseTitle, String courseDescription, boolean isPublic,
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

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "Course{" + "courseId=" + courseId + ", courseTitle='" + courseTitle + '\''
                + ", courseDescription='" + courseDescription + '\'' + ", isPublic=" + isPublic
                + ", isDeleted=" + isDeleted + '}';
    }
}

package com.lxp.model.dto;

import com.lxp.model.dao.Course;

public class CourseDTO {
    private String courseTitle;
    private String courseDescription;

    public CourseDTO() {
    }

    public CourseDTO(String courseTitle, String courseDescription) {
        this.courseTitle = courseTitle;
        this.courseDescription = courseDescription;
    }

    public static CourseDTO from(Course course) {
        return new CourseDTO(course.getCourseTitle(), course.getCourseDescription());
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

    public Course toCourse() {
        Course course = new Course();
        course.setCourseTitle(courseTitle);
        course.setCourseDescription(courseDescription);
        return course;
    }

    @Override
    public String toString() {
        return "CourseInsertDTO{" + "courseTitle='" + courseTitle + '\'' + ", courseDescription='"
                + courseDescription + '\'' + '}';
    }
}

package com.lxp.courses.controller;

import com.lxp.courses.services.CourseService;
import com.lxp.model.courses.Course;
import com.lxp.model.courses.CourseDTO;
import java.util.List;


public class CourseController {
    private CourseService courseService = new CourseService();

    public void findAllCourses() {
        try {
            List<Course> courses = courseService.findAllCourses();

            if (courses.isEmpty()) {
                System.out.println("There is no course List");
            } else {
                for (Course course : courses) {
                    System.out.println(course);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void findCourse(Long courseId) {
        if (courseId == null) {
            throw new IllegalArgumentException("There is no course Id");
        }

        try {
            Course course = courseService.findCourseById(courseId);
            System.out.println("Founded this course info: " + course);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void createCourse(CourseDTO courseDto) {
        if (courseDto == null) {
            throw new IllegalArgumentException("There is no course data");
        }
        if (courseDto.getCourseTitle() == null || courseDto.getCourseTitle().equals("")) {
            throw new IllegalArgumentException("course title is empty");
        }

        try {
            Long result = courseService.upsertCourse(courseDto);
            System.out.println("create Course Success" + result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCourse(CourseDTO courseDto) {
        if (courseDto == null) {
            throw new IllegalArgumentException("There is no course data");
        }
        if (courseDto.getCourseId() == null) {
            throw new IllegalArgumentException("course id is null");
        }
        if (courseDto.getCourseTitle() == null || courseDto.getCourseTitle().equals("")) {
            throw new IllegalArgumentException("course title is empty");
        }

        try {
            Long result = courseService.upsertCourse(courseDto);
            System.out.println("update Course Success" + result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCourse(Long courseId) {
        if (courseId == null) {
            throw new IllegalArgumentException("course id is null");
        }

        try {
            Boolean result = courseService.deleteCourseById(courseId);
            System.out.println("delete Course Success" + result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

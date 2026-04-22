package com.lxp.courses.services;

import com.lxp.config.JDBCConnection;
import com.lxp.courses.repository.CourseRepository;
import com.lxp.model.courses.Course;
import com.lxp.model.courses.CourseDTO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CourseService {
    private final CourseRepository courseRepository;
    private final Connection connection;

    public CourseService() {
        try {
            this.connection = JDBCConnection.getConnection();
            this.courseRepository = new CourseRepository(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Course> findAllCourses() throws Exception {
        List<Course> courses = courseRepository.getAllCourses();

        if (courses == null) {
            throw new Exception("Failed to find all courses");
        }
        return courses;
    }

    public Course findCourseById(Long courseId) throws Exception {
        Course course = courseRepository.getCourse(courseId);

        if (course == null) {
            throw new Exception("Failed to find course with courseId: " + courseId);
        }
        return course;
    }

    public Long upsertCourse(CourseDTO courseDto) throws Exception {
        Long result;

        if (courseDto.getCourseId() == null) {
            Course createCourse =
                    new Course(0L, courseDto.getCourseTitle(), courseDto.getCourseDescription(),
                            true, false);
            result = courseRepository.createCourse(createCourse);
        } else {
            Course updateCourse = new Course(courseDto.getCourseId(), courseDto.getCourseTitle(),
                    courseDto.getCourseDescription(), courseDto.isCoursePublic(),
                    courseDto.isCourseDeleted());
            result = courseRepository.updateCourse(updateCourse);
        }

        return result;
    }

    public Boolean deleteCourseById(Long courseId) throws Exception {
        if (courseId == null) {
            throw new Exception("Course id is null");
        }

        Boolean course = courseRepository.deleteCourse(courseId);
        if (!course) {
            throw new Exception("Failed to delete course with courseId: " + courseId);
        }

        return course;
    }

}

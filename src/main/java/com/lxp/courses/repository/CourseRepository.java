package com.lxp.courses.repository;

import com.lxp.model.Course;
import com.lxp.utils.QueryUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository {
    private final Connection connection;

    public CourseRepository(Connection connection) {
        this.connection = connection;
    }

    // 모든 Course 조회
    public List<Course> getAllCourses() {
        String sql = QueryUtils.getQuery("get.allCourses");
        List<Course> courses = new ArrayList<>();

        try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                Course course = new Course(
                        rs.getLong("course_id"),
                        rs.getString("course_title"),
                        rs.getString("course_description"),
                        rs.getBoolean("is_public"),
                        rs.getBoolean("is_deleted")
                );
                courses.add(course);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courses;
    }

    // 단일 조회
    public Course getCourse(Long courseId) {
        String sql = QueryUtils.getQuery("get.course");

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, courseId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Course(
                            rs.getLong("course_id"),
                            rs.getString("course_title"),
                            rs.getString("course_description"),
                            rs.getBoolean("is_public"),
                            rs.getBoolean("is_deleted")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Long createCourse(Course course) {
        String sql = QueryUtils.getQuery("create.course");

        try(PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, course.getCourseTitle());
            pstmt.setString(2, course.getCourseDescription());

            int affectedRows = pstmt.executeUpdate();

            if(affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if(generatedKeys.next()) {
                        return generatedKeys.getLong(1);
                    }
                }
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Long updateCourse(Course course) {
        String sql = QueryUtils.getQuery("update.course");

        try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, course.getCourseTitle());
            pstmt.setString(2, course.getCourseDescription());
            pstmt.setLong(3, course.getCourseId());

            int updatedRow = pstmt.executeUpdate();

            if (updatedRow > 0) {
                return course.getCourseId();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public Boolean deleteCourse(long courseId) {
        String sql = QueryUtils.getQuery("delete.course");

        try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, courseId);

            int deletedRow = pstmt.executeUpdate();
            return deletedRow > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

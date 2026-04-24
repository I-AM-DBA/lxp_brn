package com.lxp.courses.services;

import com.lxp.config.JDBCConnection;
import com.lxp.courses.repository.ContentRepository;
import com.lxp.courses.repository.CourseRepository;
import com.lxp.courses.repository.SectionRepository;
import com.lxp.model.CreateCourseRequest;
import com.lxp.model.dao.Content;
import com.lxp.model.dao.Course;
import com.lxp.model.dao.Section;
import com.lxp.model.dto.ContentDTO;
import com.lxp.model.dto.CourseDTO;
import com.lxp.model.dto.SectionDTO;
import java.sql.Connection;
import java.sql.SQLException;

public class InsertCourseService {
    private final CourseRepository courseRepository;
    private final SectionRepository sectionRepository;
    private final ContentRepository contentRepository;
    private final Connection connection;

    public InsertCourseService() {
        try {
            this.connection = JDBCConnection.getConnection();
            this.courseRepository = new CourseRepository(connection);
            this.sectionRepository = new SectionRepository(connection);
            this.contentRepository = new ContentRepository(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertCourse(CreateCourseRequest request) {
        try {
            CourseDTO courseDto = request.getCourse();
            Course course = courseDto.toCourse();
            Long courseId = courseRepository.createCourse(course);

            if (courseId == null) {
                throw new SQLException("Failed to create Course");
            }

            for (SectionDTO sectionDTO : request.getSections()) {
                sectionDTO.setCourseId(courseId);

                createSection(sectionDTO);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createSection(SectionDTO sectionDTO) throws SQLException {
        Section section = sectionDTO.toSection();

        Long sectionId = sectionRepository.createSection(section);
        if (sectionId == null) {
            throw new SQLException("Failed to create Section");
        }

        for (ContentDTO contentDTO : sectionDTO.getContents()) {
            contentDTO.setSectionId(sectionId);

            createContent(contentDTO);
        }
    }

    private void createContent(ContentDTO contentDto) throws SQLException {
        Content content = contentDto.toContent();

        Long contentId = contentRepository.insertContent(content);
        if (contentId == null) {
            throw new SQLException("Failed to create Content");
        }
    }
}

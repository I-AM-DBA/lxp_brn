package com.lxp.courses.services;

import com.lxp.config.JDBCConnection;
import com.lxp.courses.repository.ContentRepository;
import com.lxp.courses.repository.CourseRepository;
import com.lxp.courses.repository.SectionRepository;
import com.lxp.model.CreateCourseRequest;
import com.lxp.model.dao.Content;
import com.lxp.model.dao.Course;
import com.lxp.model.dao.Section;
import com.lxp.model.dto.ContentInsertDTO;
import com.lxp.model.dto.CourseInsertDTO;
import com.lxp.model.dto.SectionInsertDTO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public CreateCourseRequest insertCourse(CreateCourseRequest request) {
        CreateCourseRequest result;

        try {
            CourseInsertDTO courseDto = request.getCourse();
            Course course = courseDto.toCourse();
            Long courseId = courseRepository.createCourse(course);

            if (courseId == null) {
                throw new SQLException("Failed to create Course");
            }

            List<SectionInsertDTO> sectionList = new ArrayList<>();
            for (SectionInsertDTO sectionDTO : request.getSections()) {
                sectionDTO.setCourseId(courseId);

                SectionInsertDTO sectionObj = createSection(sectionDTO);

                sectionList.add(sectionObj);
            }
            return new CreateCourseRequest(courseDto, sectionList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private SectionInsertDTO createSection(SectionInsertDTO sectionDTO) throws SQLException {
        Section section = sectionDTO.toSection();
        Long sectionId = sectionRepository.createSection(section);

        if (sectionId == null) {
            throw new SQLException("Failed to create Section");
        }

        List<ContentInsertDTO> contentList = new ArrayList<>();
        for (ContentInsertDTO contentDTO : sectionDTO.getContents()) {
            contentDTO.setSectionId(sectionId);

            ContentInsertDTO contentObj = createContent(contentDTO);

            contentList.add(contentObj);
        }

        SectionInsertDTO sectionObj =
                new SectionInsertDTO(sectionId, sectionDTO.getSectionTitle(), contentList);

        return sectionObj;
    }

    private ContentInsertDTO createContent(ContentInsertDTO contentDto) throws SQLException {
        Content content = contentDto.toContent();
        Long contentId = contentRepository.insertContent(content);

        if (contentId == null) {
            throw new SQLException("Failed to create Content");
        }

        return new ContentInsertDTO(contentId, contentDto.getContentTitle(),
                contentDto.getContentUrl(), contentDto.getTime());
    }
}

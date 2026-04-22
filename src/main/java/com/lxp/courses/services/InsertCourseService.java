package com.lxp.courses.services;

import com.lxp.config.JDBCConnection;
import com.lxp.courses.repository.ContentRepository;
import com.lxp.courses.repository.CourseRepository;
import com.lxp.courses.repository.SectionRepository;
import com.lxp.model.CreateCourseRequest;
import com.lxp.model.DTO.ContentInsertDTO;
import com.lxp.model.DTO.CourseInsertDTO;
import com.lxp.model.DTO.SectionInsertDTO;
import com.lxp.model.courses.Course;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, Object> insertCourse(CreateCourseRequest request) {
        Map<String, Object> result = new HashMap<>();

        try {
            CourseInsertDTO courseDTO = request.getCourse();
            Course course = new Course();
            course.setCourseTitle(courseDTO.getCourseTitle());
            course.setCourseDescription(courseDTO.getCourseDescription());

            Long courseId = courseRepository.createCourse(course);

            if (courseId == null) {
                throw new SQLException("Failed to create Course");
            }

            result.put("courseId", courseId);

            List<Map<String, Object>> sectionList = new ArrayList<>();
            for (SectionInsertDTO sectionDTO : request.getSections()) {
                sectionDTO.setCourseId(courseId);
                Long sectionId = sectionRepository.createSection(sectionDTO);

                Map<String, Object> sectionMap = new LinkedHashMap<>();
                sectionMap.put("sectionId", sectionId);
                sectionMap.put("sectionTitle", sectionDTO.getSectionTitle());

                List<Map<String, Object>> contentList = new ArrayList<>();
                for (ContentInsertDTO contentDTO : sectionDTO.getContents()) {
                    contentDTO.setSectionId(sectionId);
                    Long contentId = contentRepository.insertContent(contentDTO);

                    Map<String, Object> contentMap = new LinkedHashMap<>();
                    contentMap.put("contentId", contentId);
                    contentMap.put("contentTitle", contentDTO.getContentTitle());
                    contentMap.put("contentUrl", contentDTO.getContentUrl());
                    contentMap.put("contentPlayTime", contentDTO.getTime());
                    contentList.add(contentMap);
                }
                sectionMap.put("contents", contentList);
                sectionList.add(sectionMap);
            }
            result.put("sections", sectionList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}

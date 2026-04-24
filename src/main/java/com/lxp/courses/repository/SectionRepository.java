package com.lxp.courses.repository;

import com.lxp.model.dao.Section;
import com.lxp.utils.QueryUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SectionRepository {
    private final Connection connection;

    public SectionRepository(Connection connection) {
        this.connection = connection;
    }
    //
    // // 모든 Section 조회
    // public List<Course.Section> getAllSections() {
    //     String sql = QueryUtils.getQuery("get.allSections");
    //     List<Course.Section> sections = new ArrayList<>();
    //
    //     try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
    //         ResultSet rs = pstmt.executeQuery();
    //
    //         while (rs.next()) {
    //             Course.Section section =
    //                     new Course.Section(rs.getLong("section_id"), rs.getLong("course_id"),
    //                             rs.getString("section_title"), rs.getBoolean("is_public"),
    //                             rs.getBoolean("is_deleted"));
    //             sections.add(section);
    //         }
    //     } catch (SQLException e) {
    //         throw new RuntimeException(e);
    //     }
    //     return sections;
    // }
    //
    // // 단일 조회
    // public Course.Section getSection(Long courseId) {
    //     String sql = QueryUtils.getQuery("get.course");
    //
    //     try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
    //         pstmt.setLong(1, courseId);
    //
    //         try (ResultSet rs = pstmt.executeQuery()) {
    //             if (rs.next()) {
    //                 return new Course.Section(rs.getLong("section_id"), rs.getLong("course_id"),
    //                         rs.getString("section_title"), rs.getBoolean("is_public"),
    //                         rs.getBoolean("is_deleted"));
    //             }
    //         }
    //     } catch (SQLException e) {
    //         throw new RuntimeException(e);
    //     }
    //     return null;
    // }

    public Long createSection(Section section) {
        String sql = QueryUtils.getQuery("create.section");

        try (PreparedStatement pstmt = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setLong(1, section.getCourseId());
            pstmt.setString(2, section.getSectionTitle());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getLong(1);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    //
    // public Long updateSection(Course.Section section) {
    //     String sql = QueryUtils.getQuery("update.section");
    //
    //     try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
    //         pstmt.setLong(1, section.getSectionId());
    //         pstmt.setLong(2, section.getCourseId());
    //         pstmt.setString(3, section.getSectionTitle());
    //
    //         int updatedRow = pstmt.executeUpdate();
    //
    //         if (updatedRow > 0) {
    //             return section.getSectionId();
    //         }
    //     } catch (SQLException e) {
    //         throw new RuntimeException(e);
    //     }
    //
    //     return null;
    // }
    //
    // public Boolean deleteSection(long sectionId) {
    //     String sql = QueryUtils.getQuery("delete.section");
    //
    //     try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
    //         pstmt.setLong(1, sectionId);
    //
    //         int deletedRow = pstmt.executeUpdate();
    //         return deletedRow > 0;
    //
    //     } catch (SQLException e) {
    //         throw new RuntimeException(e);
    //     }
    // }
}

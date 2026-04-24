package com.lxp.courses.repository;

import com.lxp.model.dao.Content;
import com.lxp.utils.QueryUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ContentRepository {
    private final Connection connection;

    public ContentRepository(Connection connection) {
        this.connection = connection;
    }

    // public ContentInsertDTO getContentById(Long contentId) {
    //     String sql = QueryUtils.getQuery("get.content");
    //
    //     try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
    //         pstmt.setLong(1, contentId);
    //
    //         try (ResultSet rs = pstmt.executeQuery()) {
    //             if (rs.next()) {
    //                 return new ContentInsertDTO(
    //                         rs.getLong("content_id"),
    //                         rs.getLong("section_id"),
    //                         rs.getString("content_title"),
    //                         rs.getString("content_url"),
    //                         rs.getInt("time"),
    //                         rs.getBoolean("is_public"),
    //                         rs.getBoolean("is_deleted"));
    //             }
    //         }
    //     } catch (SQLException e) {
    //         throw new RuntimeException(e);
    //     }
    //     return null;
    // }

    public Long insertContent(Content content) {
        String sql = QueryUtils.getQuery("create.content");

        try (PreparedStatement pstmt = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setLong(1, content.getSectionId());
            pstmt.setString(2, content.getContentTitle());
            pstmt.setString(3, content.getContentUrl());
            pstmt.setInt(4, content.getTime());

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

    // public Long updateContent(Content content) {
    //     String sql = QueryUtils.getQuery("update.content");
    //
    //     try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
    //         pstmt.setLong(1, content.getContentId());
    //         pstmt.setLong(2, content.getTime());
    //         pstmt.setString(3, content.getContentTitle());
    //         pstmt.setString(4, content.getContentUrl());
    //         pstmt.setInt(5, content.getTime());
    //         pstmt.setBoolean(6, content.isPublic());
    //         pstmt.setBoolean(7, content.isDeleted());
    //
    //         int updatedRow = pstmt.executeUpdate();
    //
    //         if (updatedRow > 0) {
    //             return content.getContentId();
    //         }
    //     } catch (SQLException e) {
    //         throw new RuntimeException(e);
    //     }
    //
    //     return null;
    // }
    //
    // public Boolean deleteContent(long contentId) {
    //     String sql = QueryUtils.getQuery("delete.content");
    //
    //     try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
    //         pstmt.setLong(1, contentId);
    //
    //         int deletedRow = pstmt.executeUpdate();
    //         return deletedRow > 0;
    //
    //     } catch (SQLException e) {
    //         throw new RuntimeException(e);
    //     }
    // }
}

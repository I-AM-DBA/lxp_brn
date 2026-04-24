package com.lxp.model.dao;

import java.time.LocalDateTime;

public class Section {
    private Long sectionId;
    private Long courseId;
    private String sectionTitle;
    private boolean isPublic;
    private boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public Section(Long sectionId, Long courseId, String sectionTitle, boolean isPublic,
            boolean isDeleted, LocalDateTime createdAt, LocalDateTime updatedAt,
            LocalDateTime deletedAt) {
        this.sectionId = sectionId;
        this.courseId = courseId;
        this.sectionTitle = sectionTitle;
        this.isPublic = isPublic;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public Section() {
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Override
    public String toString() {
        return "Section{" + "sectionId=" + sectionId + ", courseId=" + courseId + ", sectionTitle='"
                + sectionTitle + '\'' + ", isPublic=" + isPublic + ", isDeleted=" + isDeleted
                + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", deletedAt="
                + deletedAt + '}';
    }
}

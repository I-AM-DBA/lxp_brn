package com.lxp.model.dao;

import java.time.LocalDateTime;

public class Content {
    private Long contentId;
    private Long sectionId;
    private String contentTitle;
    private String contentUrl;
    private int time;
    private boolean isPublic;
    private boolean isDeleted;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public Content(Long contentId, Long sectionId, String contentTitle, String contentUrl, int time,
            boolean isPublic, boolean isDeleted, LocalDateTime createAt, LocalDateTime updatedAt,
            LocalDateTime deletedAt) {
        this.contentId = contentId;
        this.sectionId = sectionId;
        this.contentTitle = contentTitle;
        this.contentUrl = contentUrl;
        this.time = time;
        this.isPublic = isPublic;
        this.isDeleted = isDeleted;
        this.createAt = createAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public Content() {
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
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

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
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
        return "Content{" + "contentId=" + contentId + ", sectionId=" + sectionId
                + ", contentTitle='" + contentTitle + '\'' + ", contentUrl='" + contentUrl + '\''
                + ", time=" + time + ", isPublic=" + isPublic + ", isDeleted=" + isDeleted
                + ", createAt=" + createAt + ", updatedAt=" + updatedAt + ", deletedAt=" + deletedAt
                + '}';
    }
}

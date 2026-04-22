package com.lxp.model.DTO;


public class ContentInsertDTO {
    private Long sectionId;
    private String contentTitle;
    private String contentUrl;
    private int time;

    public ContentInsertDTO(Long sectionId, String contentTitle, String contentUrl, int time) {
        this.sectionId = sectionId;
        this.contentTitle = contentTitle;
        this.contentUrl = contentUrl;
        this.time = time;
    }

    public ContentInsertDTO() {
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

    @Override
    public String toString() {
        return "ContentInsertDTO{" + "contentTitle='" + contentTitle + '\'' + ", contentUrl='"
                + contentUrl + '\'' + ", time=" + time + '}';
    }
}

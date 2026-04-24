package com.lxp.model.dto;


import com.lxp.model.dao.Content;

public class ContentDTO {
    private Long sectionId;
    private String contentTitle;
    private String contentUrl;
    private int time;

    public ContentDTO(Long sectionId, String contentTitle, String contentUrl, int time) {
        this.sectionId = sectionId;
        this.contentTitle = contentTitle;
        this.contentUrl = contentUrl;
        this.time = time;
    }

    public ContentDTO() {
    }

    public static ContentDTO from(Long sectionId, String contentTitle, String contentUrl,
            int time) {
        return new ContentDTO(sectionId, contentTitle, contentUrl, time);
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

    public Content toContent() {
        Content content = new Content();
        content.setSectionId(sectionId);
        content.setContentTitle(contentTitle);
        content.setContentUrl(contentUrl);
        content.setTime(time);
        return content;
    }

    @Override
    public String toString() {
        return "ContentInsertDTO{" + "contentTitle='" + contentTitle + '\'' + ", contentUrl='"
                + contentUrl + '\'' + ", time=" + time + '}';
    }
}

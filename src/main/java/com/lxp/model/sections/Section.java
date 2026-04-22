package com.lxp.model.sections;

public class Section {
    private Long sectionId;
    private Long CourseId;
    private String sectionTitle;
    private boolean isPublic;
    private boolean isDeleted;

    public Section(Long sectionId, Long courseId, String sectionTitle, boolean isPublic,
            boolean isDeleted) {
        this.sectionId = sectionId;
        CourseId = courseId;
        this.sectionTitle = sectionTitle;
        this.isPublic = isPublic;
        this.isDeleted = isDeleted;
    }

    public Section(Long sectionId, Long courseId, String sectionTitle) {
        this.sectionId = 0L;
        CourseId = courseId;
        this.sectionTitle = sectionTitle;
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
        return CourseId;
    }

    public void setCourseId(Long courseId) {
        CourseId = courseId;
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

    @Override
    public String toString() {
        return "Section{" + "sectionId=" + sectionId + ", CourseId=" + CourseId + ", sectionTitle='"
                + sectionTitle + '\'' + ", isPublic=" + isPublic + ", isDeleted=" + isDeleted + '}';
    }
}

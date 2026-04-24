package com.lxp.model.dto;

import com.lxp.model.dao.Section;
import java.util.List;

public class SectionDTO {
    private Long courseId;
    private String sectionTitle;
    private List<ContentDTO> contents;

    public SectionDTO(Long courseId, String sectionTitle, List<ContentDTO> contents) {
        this.courseId = courseId;
        this.sectionTitle = sectionTitle;
        this.contents = contents;
    }

    public SectionDTO() {
    }

    public static SectionDTO from(Section section) {
        SectionDTO sectionInsertDTO = new SectionDTO();
        sectionInsertDTO.setCourseId(section.getCourseId());
        sectionInsertDTO.setSectionTitle(section.getSectionTitle());
        return sectionInsertDTO;
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

    public List<ContentDTO> getContents() {
        return contents;
    }

    public void setContents(List<ContentDTO> contents) {
        this.contents = contents;
    }

    public void addContent(ContentDTO contentDTO) {
        this.contents.add(contentDTO);
    }

    public Section toSection() {
        Section section = new Section();
        section.setCourseId(courseId);
        section.setSectionTitle(sectionTitle);
        return section;
    }

    @Override
    public String toString() {
        return "SectionInsertDTO{" + "sectionTitle='" + sectionTitle + '\'' + ", contents="
                + contents + '}';
    }
}

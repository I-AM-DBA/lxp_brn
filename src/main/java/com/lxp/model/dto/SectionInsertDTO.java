package com.lxp.model.dto;

import com.lxp.model.dao.Section;
import java.util.List;

public class SectionInsertDTO {
    private Long courseId;
    private String sectionTitle;
    private List<ContentInsertDTO> contents;

    public SectionInsertDTO(Long courseId, String sectionTitle, List<ContentInsertDTO> contents) {
        this.courseId = courseId;
        this.sectionTitle = sectionTitle;
        this.contents = contents;
    }

    public SectionInsertDTO() {
    }

    public static SectionInsertDTO from(Section section) {
        SectionInsertDTO sectionInsertDTO = new SectionInsertDTO();
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

    public List<ContentInsertDTO> getContents() {
        return contents;
    }

    public void setContents(List<ContentInsertDTO> contents) {
        this.contents = contents;
    }

    public void addContent(ContentInsertDTO contentInsertDTO) {
        this.contents.add(contentInsertDTO);
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

package com.lxp.courses.services;

import com.lxp.config.JDBCConnection;
import com.lxp.courses.repository.SectionRepository;
import com.lxp.model.sections.Section;
import com.lxp.utils.CheckNull;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SectionService {
    private final SectionRepository sectionRepository;
    private final Connection connection;

    public SectionService() {
        try {
            this.connection = JDBCConnection.getConnection();
            this.sectionRepository = new SectionRepository(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Section> findAllSections() throws Exception {
        List<Section> sections = sectionRepository.getAllSections();

        CheckNull.checkExists(sections, "Failed to find all sections");
        return sections;
    }

    public Section findSecById(Long sectionId) throws Exception {
        Section section = sectionRepository.getSection(sectionId);

        CheckNull.checkExists(section, "Failed to find all section");

        return section;
    }

    public Long upsertCourse(Section section) throws Exception {
        Long result;

        if (section.getSectionId() == null) {
            Section insertSec =
                    new Section(0L, section.getCourseId(), section.getSectionTitle(), true, false);
            result = sectionRepository.createSection(insertSec);

        } else {
            Section updateSec = new Section(section.getSectionId(), section.getCourseId(),
                    section.getSectionTitle(), section.isPublic(), section.isDeleted());
            result = sectionRepository.updateSection(updateSec);
        }

        return result;
    }

    public boolean deleteSection(Long sectionId) throws Exception {
        CheckNull.checkArgument(sectionId, "sectionId must not be null");

        boolean section = sectionRepository.deleteSection(sectionId);
        CheckNull.checkExists(section, "Failed to delete section");

        return section;
    }
}

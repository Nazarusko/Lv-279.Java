package ua.softserve.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.softserve.persistence.entity.Languages;

public interface LanguagesDAO extends JpaRepository<Languages, Integer> {
}

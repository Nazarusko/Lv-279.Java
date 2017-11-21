package ua.softserve.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.persistence.dao.AcademyDAO;
import ua.softserve.persistence.entity.Academy;
import ua.softserve.persistence.entity.Directions;
import ua.softserve.service.AcademyService;

import java.util.List;

@Service
public class AcademyServiceImpl implements AcademyService {
    @Autowired
    private AcademyDAO academyDAO;

    @Transactional(readOnly = true)
    @Override
    public Academy getById(Integer id) {
        return academyDAO.findOne(id);
    }

    @Transactional
    @Override
    public List<Academy> getAllAcademys() {
        return academyDAO.findAll();
    }

    @Override
    public List<Academy> findAllByName(String name) {
        return academyDAO.findAllByName(name);
    }

    @Override
    public void save(Academy academy) {
        academyDAO.save(academy);
    }
}

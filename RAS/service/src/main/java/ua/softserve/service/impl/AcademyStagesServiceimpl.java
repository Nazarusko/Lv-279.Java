package ua.softserve.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.persistence.dao.AcademyStagesDAO;
import ua.softserve.persistence.entity.AcademyStages;
import ua.softserve.service.AcademyStagesService;

import java.util.List;

@Service
public class AcademyStagesServiceimpl implements AcademyStagesService {
    @Autowired
    private AcademyStagesDAO academyStagesDAO;


    @Transactional(readOnly = true)
    @Override
    public List<AcademyStages> getAllAcademyStagesService() {
        return academyStagesDAO.findAll();
    }
}

package ua.softserve.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.persistence.constants.ConstantsFromDb;
import ua.softserve.persistence.dto.GroupInformationDTO;
import ua.softserve.persistence.entity.*;
import ua.softserve.persistence.repo.GroupInfoRepository;
import ua.softserve.service.*;
import ua.softserve.service.converter.AcademyConverter;
import ua.softserve.service.converter.GroupInfoConverter;
import ua.softserve.service.dto.AcademyDTO;
import ua.softserve.service.dto.AcademyForViewDTO;
import ua.softserve.service.dto.GroupAllInformationDTO;

import java.util.*;

/**
 * Service processes information that returns Repositories.
 */
@Service
public class GroupInfoServiceImpl implements GroupInfoService {
    @Autowired
    private GroupInfoRepository groupInfoRepository;

    @Autowired
    private AcademyConverter academyConverter;

    @Autowired
    private AcademyStagesService academyStagesService;

    @Autowired
    private DirectionService directionService;

    @Autowired
    private TechnologyServiceImpl technologyServiceImpl;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private LanguageTranslationsService languageTranslationsService;

    @Autowired
    private GroupInfoTeachersService groupInfoTeachersService;

    @Autowired
    private TeacherTypeService teacherTypeService;

    @Autowired
    private StudentService studentsService;

    @Autowired
    private StudentsStatusesService studentsStatusesService;

    @Autowired
    private GroupInfoConverter groupInfoConverter;

    @Override
    public void save(GroupInfo groupInfo) {
        groupInfoRepository.save(groupInfo);
    }

    @Override
    public GroupInfo findOne(int id) {
        return groupInfoRepository.findOne(id);
    }

    /**
     * Method transforms information about academies from all tables into one DTO object.
     *
     * @return information about academies from all tables into one DTO object.
     */
    @Override
    public Map<GroupInformationDTO, Integer> getInfoAboutStudents() {
        List<GroupInformationDTO> allInfoAboutGroups = findAllInfoAboutGroups();
        Map<GroupInformationDTO, Integer> infoStudentMap= new HashMap<>();
        for(GroupInformationDTO infoAboutGroup: allInfoAboutGroups){
            if(infoStudentMap.containsKey(infoAboutGroup)){
                infoStudentMap.put(infoAboutGroup, infoStudentMap.get(infoAboutGroup) + 1);
            } else {
                infoStudentMap.put(infoAboutGroup, 0);
            }
        }
        for (Map.Entry<GroupInformationDTO, Integer> entry : infoStudentMap.entrySet())
        {
            if(entry.getValue() > 0){
                infoStudentMap.put(entry.getKey(), entry.getValue() + 1);
            }
        }
        return infoStudentMap;
    }

    @Override
    public Set<GroupAllInformationDTO> getAllInfo(){
        Map<GroupInformationDTO, Integer> infoAboutStudents = getInfoAboutStudents();
        Set<GroupAllInformationDTO> groupAllInformationDTOSet = new HashSet<>();
        for (Map.Entry<GroupInformationDTO, Integer> entry : infoAboutStudents.entrySet())
        {
            GroupAllInformationDTO allInformationDTO = groupInfoConverter.InfoStudentsDTOToInfoGroupDTO(entry.getKey());
            if(groupAllInformationDTOSet.contains(allInformationDTO)){

            } else {
                GroupAllInformationDTO groupAllInformationDTO = groupInfoConverter.InfoStudentsDTOToInfoGroupDTO(entry.getKey());
                groupAllInformationDTOSet.add(groupAllInformationDTO);
            }
        }
        return null;
    }

    /**
     * Method transforms information about academies from all tables into one DTO object.
     *
     * @return information about academies from all tables into one DTO object.
     */
    @Override
    public List<AcademyForViewDTO> getAllAcademies() {
        List<GroupInfo> groupInfoList = findAllWithOrder();
        List<AcademyForViewDTO> academyDTOList = new ArrayList<>();
        Integer countActualStudents = null;
        List<GroupInfoTeachers> getExpertsOfTheGroup = null;
        Set<LanguageTranslations> languageTranslations = languageTranslationsService.getAllLanguageTranslationsName();
        TeacherTypes teacherTypes = teacherTypeService.findOne(ConstantsFromDb.TEACHER_TYPE_EXPERT_ID);
        StudentStatuses studentStatuses = studentsStatusesService.findOne(ConstantsFromDb.STUDENT_STATUS_TRAINEE_ID);
        if (teacherTypes != null) {
            getExpertsOfTheGroup = groupInfoTeachersService.findAllByTeacherType(teacherTypes);
        }
        if (groupInfoList != null) {
            for (GroupInfo groupInfo : groupInfoList) {
                AcademyForViewDTO academyDTO = academyConverter.toDTO(groupInfo);
                Academy academy = groupInfo.getAcademy();
                if (academy != null) {
                    if (academy.getCity() != null) {
                        for (LanguageTranslations languageTranslation : languageTranslations) {
                            if (languageTranslation.getItemId() == academy.getCity().getCityId()) {
                                academyDTO.setCityName(languageTranslation.getTrasnlation());
                                break;
                            }
                        }
                    }
                    if (academy.getAcademyStages() != null) {
                        academyDTO.setStatus(academy.getAcademyStages().getName());
                    }
                    List<String> employeeList = new ArrayList<>();
                    if (getExpertsOfTheGroup != null) {
                        for (GroupInfoTeachers groupInfoTeachers : getExpertsOfTheGroup) {
                            if ((groupInfoTeachers.getAcademy().getAcademyId() == academy.getAcademyId())
                                    && groupInfoTeachers != null) {
                                employeeList.add(groupInfoTeachers.getEmployee().getFirstNameEng() + " "
                                        + groupInfoTeachers.getEmployee().getLastNameEng());
                            }
                        }
                    }
                    if (employeeList.size() != 0) {
                        academyDTO.setExperts(employeeList);
                    }
                    if (studentStatuses != null) {
                        countActualStudents = studentsService.countAllByAcademyAndStudentStatus(academy,
                                studentStatuses);
                    }
                    if (countActualStudents != null) {
                        academyDTO.setStudentsActual(countActualStudents);
                    }
                }
                if (groupInfo.getProfileInfo() != null) {
                    academyDTO.setProfileName(groupInfo.getProfileInfo().getProfileName());
                }
                academyDTOList.add(academyDTO);
            }
        }

        AcademyForViewDTO academyForViewDTO = new AcademyForViewDTO();
        academyForViewDTO.setAcademyStages(academyStagesService.getAllAcademyStagesService());
        academyForViewDTO.setDirection(directionService.findAllDirectionsInIta());
        academyForViewDTO.setTechnologie(technologyServiceImpl.findAllTechonologyInIta());
        academyForViewDTO.setProfile(profileService.findAll());
        academyForViewDTO.setCityNames(languageTranslations);
        academyDTOList.add(academyForViewDTO);
        return academyDTOList;
    }

    /**
     * Method returns data from GroupInfo table in order of adding records.
     *
     * @return data from GroupInfo table in order of adding records.
     */
    @Override
    public List<GroupInfo> findAllWithOrder() {
        return groupInfoRepository.findAllWithOrder();
    }

    @Override
    public List<GroupInformationDTO> findAllInfoAboutGroups() {
        return groupInfoRepository.findAllInfoAboutGroups();
    }

    @Override
    public GroupInfo findOneGroupInfoByAcademyId(int academyId) {
        return groupInfoRepository.findByAcademyAcademyId(academyId);
    }
}

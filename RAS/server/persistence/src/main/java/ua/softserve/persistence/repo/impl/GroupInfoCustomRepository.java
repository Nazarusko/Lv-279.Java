package ua.softserve.persistence.repo.impl;

import org.hibernate.Session;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ua.softserve.persistence.dto.GroupInformationDTO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class GroupInfoCustomRepository {
    @PersistenceContext
    private EntityManager em;

    private static final Logger logger = LoggerFactory.getLogger(GroupInfoCustomRepository.class);
    /**
     * Method returns information about groups and creates new DTOs
     * when there are two or more experts in the group.
     *
     * @return information about groups.
     */
    public List<GroupInformationDTO> getAllInformationAboutGroups() {
        logger.info("Before getAllInformationAboutGroup() in the repository");
        long startTime = System.currentTimeMillis();
            List<GroupInformationDTO> results = ((Session) this.em.getDelegate()).createSQLQuery(
                    "SELECT gI.academy_id as academyId, gI.group_name as groupName, gI.students_planned_to_graduate as studentsPlannedToGraduate, " +
                            "gI.students_planned_to_enrollment as studentsPlannedToEnrollment, pI.profile_name as profileName, " +
                            "ac.start_date as startDate, ac.end_date as endDate, ac.free as paymentStatus, ac.name as nameForSite, aSt.name as status, " +
                            "tech.name as directionName, gIT.teacher_type_id as teacherType, dir.name as commonDirectionName, " +
                            "group_concat(IF(gIT.teacher_type_id = 2, concat(empl.first_name_eng, \" \", empl.last_name_eng), null) separator ', ') as experts, " +
                            "lt.trasnlation as cityName, " +
                            "(SELECT COUNT(stn.academy_id) from students as stn where stn.academy_id = gI.academy_id AND stn.student_status_id = 1 AND stn.removed = FALSE) as studentsActual " +
                            "FROM group_info gI " +
                            "LEFT JOIN group_info_teachers gIT ON gIT.academy_id = gI.academy_id " +
                            "LEFT JOIN profile_info as pI ON gI.profile_id = pI.profile_id " +
                            "LEFT JOIN employee as empl ON gIT.employee_id = empl.employee_id " +
                            "LEFT JOIN academy as ac ON gI.academy_id = ac.academy_id " +
                            "LEFT JOIN technologies as tech ON ac.technology_id = tech.technology_id " +
                            "LEFT JOIN academy_stages as aSt ON aSt.stage_id = ac.stage_id " +
                            "LEFT JOIN directions as dir ON ac.direction_id = dir.direction_id " +
                            "LEFT JOIN city as c ON ac.city_id = c.city_id " +
                            "JOIN  language_translations as lt ON c.city_id = lt.item_id " +
                            "WHERE c.ita = 1 and lt.tag = 'city' and lt.local = 'en' " +
                            "GROUP BY gI.academy_id ORDER BY gI.academy_id DESC, gIT.teacher_type_id DESC"
            )
                .addScalar("academyId", StandardBasicTypes.INTEGER)
                .addScalar("groupName", StandardBasicTypes.STRING)
                .addScalar("studentsPlannedToGraduate", StandardBasicTypes.INTEGER)
                .addScalar("studentsPlannedToEnrollment", StandardBasicTypes.INTEGER)
                .addScalar("startDate", StandardBasicTypes.DATE)
                .addScalar("endDate", StandardBasicTypes.DATE)
                .addScalar("paymentStatus", StandardBasicTypes.INTEGER)
                .addScalar("nameForSite", StandardBasicTypes.STRING)
                .addScalar("profileName", StandardBasicTypes.STRING)
                .addScalar("directionName", StandardBasicTypes.STRING)
                .addScalar("commonDirectionName", StandardBasicTypes.STRING)
                .addScalar("status", StandardBasicTypes.STRING)
                .addScalar("cityName", StandardBasicTypes.STRING)
                .addScalar("studentsActual", StandardBasicTypes.LONG)
                .addScalar("experts", StandardBasicTypes.STRING)
                .addScalar("teacherType", StandardBasicTypes.INTEGER)
                .setResultTransformer(new AliasToBeanResultTransformer(GroupInformationDTO.class))
                .list();
        long endTime = System.currentTimeMillis();
        logger.info("Time for executing getAllInformationAboutGroup() method in the repository : " + (float)(endTime - startTime)/1000);
        return results;
    }
}

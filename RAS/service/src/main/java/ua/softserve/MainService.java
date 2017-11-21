package ua.softserve;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.softserve.config.DataConfig;
import ua.softserve.config.ServiceConf;

import ua.softserve.persistence.entity.Feedback;
import ua.softserve.persistence.entity.History;
import ua.softserve.persistence.entity.Mark;
import ua.softserve.persistence.entity.User;
import ua.softserve.service.*;

import ua.softserve.service.LanguageTranslationsService;


public class MainService {

    public static void main(String[] args) {


        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ServiceConf.class, DataConfig.class);

        LanguageTranslationsService languageTranslationsService = context.getBean(LanguageTranslationsService.class);

        System.out.println(languageTranslationsService.getTranslations());

//        HistoryService historyService = context.getBean(HistoryService.class);
//
//        for (History history:historyService.findByCrmGroup(428)) {
//            System.out.println(history.toString());
//
//        }



    }

}
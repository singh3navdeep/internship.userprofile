package in.dailyhunt.internship.userprofile.services.interfaces;

import in.dailyhunt.internship.userprofile.entities.Language;

import java.util.List;

public interface LanguageService {
    List<Language> getAllLanguages();

    Language saveLanguage(String name);
}

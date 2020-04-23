package in.dailyhunt.internship.userprofile.services.interfaces;

import in.dailyhunt.internship.userprofile.entities.Language;
import in.dailyhunt.internship.userprofile.exceptions.ResourceNotFoundException;

import java.util.List;

public interface LanguageService {
    List<Language> getAllLanguages();

    Language saveLanguage(String name);

    Language findLanguageById(Long id);

    Language findLanguageByName(String name) throws ResourceNotFoundException;
}

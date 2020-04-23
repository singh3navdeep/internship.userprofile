package in.dailyhunt.internship.userprofile.services;

import in.dailyhunt.internship.userprofile.entities.Language;
import in.dailyhunt.internship.userprofile.exceptions.BadRequestException;
import in.dailyhunt.internship.userprofile.exceptions.ResourceNotFoundException;
import in.dailyhunt.internship.userprofile.repositories.LanguageRepository;
import in.dailyhunt.internship.userprofile.services.interfaces.LanguageService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class LanguageServiceImpl implements LanguageService {

    private LanguageRepository languageRepository;

    public LanguageServiceImpl(LanguageRepository languageRepository){
        this.languageRepository = languageRepository;
    }

    @Override
    @Transactional
    public List<Language> getAllLanguages(){
        return languageRepository.findAll();
    }

    @Override
    @Transactional
    public Language findLanguageById(Long id) {
        return languageRepository.findById(id).get();
    }

    @Override
    @Transactional
    public Language findLanguageByName(String name) throws ResourceNotFoundException{
        Optional<Language> language = languageRepository.findByName(name);
        if(!language.isPresent())
            throw new ResourceNotFoundException("language with name "+name+" does not exist");
        return language.get();
    }

    @Override
    @Transactional
    public Language saveLanguage(String name) throws BadRequestException {
        if(languageRepository.existsByName(name))
            throw new BadRequestException("This language is already added");
        Language language = Language.builder()
                    .name(name)
                    .build();
        languageRepository.save(language);
        return language;
    }
}

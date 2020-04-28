package in.dailyhunt.internship.userprofile.endpoints;

import in.dailyhunt.internship.userprofile.exceptions.BadRequestException;
import in.dailyhunt.internship.userprofile.services.interfaces.LanguageService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(LanguageEndpoint.BASE_URL)
public class LanguageEndpoint {
    static final String BASE_URL = "api/v1/language_profile";

    private final LanguageService languageService;

    @Autowired
    public LanguageEndpoint(LanguageService languageService){
        this.languageService = languageService;
    }

    @GetMapping
    public ResponseEntity<?> getAllLanguages(){
        return ResponseEntity.ok().body(languageService.getAllLanguages());
    }

    @PostMapping("/save")
    public ResponseEntity<String> addLanguage(@Valid @RequestBody String name) throws BadRequestException {
        languageService.saveLanguage(name);
        return ResponseEntity.ok().body("language added successfully");
    }
}

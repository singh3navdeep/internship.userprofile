package in.dailyhunt.internship.userprofile.endpoints.cards;

import in.dailyhunt.internship.userprofile.client_model.request.DateFilter;
import in.dailyhunt.internship.userprofile.client_model.response.CardResponse;
import in.dailyhunt.internship.userprofile.services.interfaces.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(CardsEndpoint.BASE_URL)
public class CardsEndpoint {

    static final String BASE_URL = "api/v1/cards";

    private final CardService cardService;

    @Autowired
    public CardsEndpoint(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<CardResponse> getKeywordCards(@PathVariable String keyword) {
        return ResponseEntity.ok().body(cardService.getKeywordCards(keyword));
    }

    //Get cards based on user's following genres.
    @GetMapping("/genre")
    public ResponseEntity<CardResponse> getGenresCards() {
        return ResponseEntity.ok().body(cardService.getGenresCards());
    }

    //Get cards based on one genre
    @GetMapping("/genre/{genreId}")
    public ResponseEntity<CardResponse> getGenreCards(@PathVariable Long genreId) {
        return ResponseEntity.ok().body(cardService.getGenreCards(genreId));
    }

    @GetMapping("/generic")
    public ResponseEntity<CardResponse> getGenericCards() {
        return ResponseEntity.ok().body(cardService.getGenericCards());
    }

    //Get cards based on user's following languages.
    @GetMapping("/language")
    public ResponseEntity<CardResponse> getLanguagesCards() {
        return ResponseEntity.ok().body(cardService.getLanguagesCards());
    }

    //Get cards based on one language.
    @GetMapping("/language/{languageId}")
    public ResponseEntity<CardResponse> getLanguageCards(@PathVariable Long languageId) {
        return ResponseEntity.ok().body(cardService.getLanguageCards(languageId));
    }

    //Get cards based on user's following localities.
    @GetMapping("/locality")
    public ResponseEntity<CardResponse> getLocalitiesCards() {
        return ResponseEntity.ok().body(cardService.getLocalitiesCards());
    }

    //Get cards based on one locality.
    @GetMapping("/locality/{localityId}")
    public ResponseEntity<CardResponse> getLocalityCards(@PathVariable Long localityId) {
        return ResponseEntity.ok().body(cardService.getLocalityCards(localityId));
    }

    //Get cards based on user's following tags.
    @GetMapping("/tag")
    public ResponseEntity<CardResponse> getTagsCards() {
        return ResponseEntity.ok().body(cardService.getTagsCards());
    }

    //Get cards based on one tag.
    @GetMapping("/tag/{tagId}")
    public ResponseEntity<CardResponse> getTagCards(@PathVariable Long tagId) {
        return ResponseEntity.ok().body(cardService.getTagCards(tagId));
    }

    //Get cards based on trending.
    @GetMapping("/trending")
    public ResponseEntity<CardResponse> getTrendingCards() {
        return ResponseEntity.ok().body(cardService.getTrendingCards());
    }

    //Get cards based on date range.
    @PostMapping("/date-range")
    public ResponseEntity<CardResponse> getCardsByDateRange(@Valid @RequestBody DateFilter dateFilter){
        return ResponseEntity.ok().body(cardService.getCardsByDateRange(dateFilter));
    }
}

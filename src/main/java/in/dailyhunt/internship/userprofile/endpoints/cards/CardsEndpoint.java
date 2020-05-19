package in.dailyhunt.internship.userprofile.endpoints.cards;

import in.dailyhunt.internship.userprofile.client_model.request.DateFilter;
import in.dailyhunt.internship.userprofile.client_model.response.DataCardResponse;
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
    public ResponseEntity<DataCardResponse> getKeywordCards(@PathVariable String keyword) {
        return ResponseEntity.ok().body(cardService.getKeywordCards(keyword));
    }

    //Get cards based on user's following genres.
    @GetMapping("/genre")
    public ResponseEntity<DataCardResponse> getGenresCards() {
        return ResponseEntity.ok().body(cardService.getGenresCards());
    }

    //Get cards based on one genre
    @GetMapping("/genre/{genreId}")
    public ResponseEntity<DataCardResponse> getGenreCards(@PathVariable Long genreId) {
        return ResponseEntity.ok().body(cardService.getGenreCards(genreId));
    }

    @GetMapping("/generic")
    public ResponseEntity<DataCardResponse> getGenericCards() {
        return ResponseEntity.ok().body(cardService.getGenericCards());
    }

    //Get cards based on user's following languages.
    @GetMapping("/language")
    public ResponseEntity<DataCardResponse> getLanguagesCards() {
        return ResponseEntity.ok().body(cardService.getLanguagesCards());
    }

    //Get cards based on one language.
    @GetMapping("/language/{languageId}")
    public ResponseEntity<DataCardResponse> getLanguageCards(@PathVariable Long languageId) {
        return ResponseEntity.ok().body(cardService.getLanguageCards(languageId));
    }

    //Get cards based on user's following localities.
    @GetMapping("/locality")
    public ResponseEntity<DataCardResponse> getLocalitiesCards() {
        return ResponseEntity.ok().body(cardService.getLocalitiesCards());
    }

    //Get cards based on one locality.
    @GetMapping("/locality/{localityId}")
    public ResponseEntity<DataCardResponse> getLocalityCards(@PathVariable Long localityId) {
        return ResponseEntity.ok().body(cardService.getLocalityCards(localityId));
    }

    //Get cards based on user's following tags.
    @GetMapping("/tag")
    public ResponseEntity<DataCardResponse> getTagsCards() {
        return ResponseEntity.ok().body(cardService.getTagsCards());
    }

    //Get cards based on one tag.
    @GetMapping("/tag/{tagId}")
    public ResponseEntity<DataCardResponse> getTagCards(@PathVariable Long tagId) {
        return ResponseEntity.ok().body(cardService.getTagCards(tagId));
    }

    //Get cards based on trending.
    @GetMapping("/trending")
    public ResponseEntity<DataCardResponse> getTrendingCards() {
        return ResponseEntity.ok().body(cardService.getTrendingCards());
    }

    //Get cards based on date range.
    @PostMapping("/date-range")
    public ResponseEntity<DataCardResponse> getCardsByDateRange(@Valid @RequestBody DateFilter dateFilter){
        return ResponseEntity.ok().body(cardService.getCardsByDateRange(dateFilter));
    }

    //Based on user's following sources.
    @GetMapping("/sources")
    public ResponseEntity<DataCardResponse> getSourcesCards() {
        return ResponseEntity.ok().body(cardService.getSourcesCards());
    }

    //Get cards based on one source.
    @GetMapping("/source/{genreId}")
    public ResponseEntity<DataCardResponse> getSourceCards(@PathVariable Long sourceId) {
        return ResponseEntity.ok().body(cardService.getSourceCards(sourceId));
    }
}

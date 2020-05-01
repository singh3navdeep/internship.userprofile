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

    @GetMapping("/genre")
    public ResponseEntity<CardResponse> getGenreCards() {
        return ResponseEntity.ok().body(cardService.getGenreCards());
    }

    @GetMapping("/language")
    public ResponseEntity<CardResponse> getLanguageCards() {
        return ResponseEntity.ok().body(cardService.getLanguageCards());
    }

    @GetMapping("/locality")
    public ResponseEntity<CardResponse> getLocalityCards() {
        return ResponseEntity.ok().body(cardService.getLocalityCards());
    }

    @GetMapping("/tag")
    public ResponseEntity<CardResponse> getTagCards() {
        return ResponseEntity.ok().body(cardService.getTagCards());
    }

    @GetMapping("/trending")
    public ResponseEntity<CardResponse> getTrendingCards() {
        return ResponseEntity.ok().body(cardService.getTrendingCards());
    }

    @PostMapping("/date-range")
    public ResponseEntity<CardResponse> getCardsByDateRange(@Valid @RequestBody DateFilter dateFilter){
        return ResponseEntity.ok().body(cardService.getCardsByDateRange(dateFilter));
    }
}

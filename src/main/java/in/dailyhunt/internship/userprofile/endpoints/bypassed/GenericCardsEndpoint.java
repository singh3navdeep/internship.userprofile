package in.dailyhunt.internship.userprofile.endpoints.bypassed;

import in.dailyhunt.internship.userprofile.client_model.response.DataCardResponse;
import in.dailyhunt.internship.userprofile.services.interfaces.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(GenericCardsEndpoint.BASE_URL)
public class GenericCardsEndpoint {

    static final String BASE_URL = "api/v1/injestion/cards";

    private final CardService cardService;

    @Autowired
    public GenericCardsEndpoint(CardService cardService) {
        this.cardService = cardService;
    }

    //Get cards with generic genres.
    @GetMapping("/generic")
    public ResponseEntity<DataCardResponse> getGenericCards() {
        return ResponseEntity.ok().body(cardService.getGenericCardsWithoutLogin());
    }

    @GetMapping("/genre/{genreId}")
    public ResponseEntity<DataCardResponse> getGenreCards(@PathVariable Long genreId) {
        return ResponseEntity.ok().body(cardService.getGenreCardsWithoutLogin(genreId));
    }

    @GetMapping("/trending")
    public ResponseEntity<DataCardResponse> getTrendingCards() {
        return ResponseEntity.ok().body(cardService.getTrendingCards());
    }

    @GetMapping("/source/{sourceId}")
    public ResponseEntity<DataCardResponse> getSourceCards(@PathVariable Long sourceId) {
        return ResponseEntity.ok().body(cardService.getSourceCardsWithoutLogin(sourceId));
    }
}

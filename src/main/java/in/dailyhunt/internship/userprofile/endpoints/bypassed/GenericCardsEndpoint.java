package in.dailyhunt.internship.userprofile.endpoints.bypassed;

import in.dailyhunt.internship.userprofile.client_model.response.CardResponse;
import in.dailyhunt.internship.userprofile.services.interfaces.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<CardResponse> getGenericCards() {
        return ResponseEntity.ok().body(cardService.getGenericCards());
    }
}

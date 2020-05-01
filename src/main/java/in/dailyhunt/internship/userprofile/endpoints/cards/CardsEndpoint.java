package in.dailyhunt.internship.userprofile.endpoints.cards;

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
@RequestMapping(CardsEndpoint.BASE_URL)
public class CardsEndpoint {

    static final String BASE_URL = "api/v1/cards";

    private final CardService cardService;

    @Autowired
    public CardsEndpoint(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/genre")
    public ResponseEntity<CardResponse> getGenreCards() {
        return ResponseEntity.ok().body(cardService.getGenreCards());
    }
}

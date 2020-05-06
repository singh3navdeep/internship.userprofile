package in.dailyhunt.internship.userprofile.services.interfaces;

import in.dailyhunt.internship.userprofile.client_model.request.DateFilter;
import in.dailyhunt.internship.userprofile.client_model.response.CardResponse;

public interface CardService {

    CardResponse getKeywordCards(String keyword);

    CardResponse getGenreCards();

    CardResponse getGenericCards();

    CardResponse getLanguageCards();

    CardResponse getLocalityCards();

    CardResponse getTagCards();

    CardResponse getTrendingCards();

    CardResponse getCardsByDateRange(DateFilter dateFilter);
}

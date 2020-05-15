package in.dailyhunt.internship.userprofile.services.interfaces;

import in.dailyhunt.internship.userprofile.client_model.request.DateFilter;
import in.dailyhunt.internship.userprofile.client_model.response.CardResponse;
import in.dailyhunt.internship.userprofile.client_model.response.DataCardResponse;

public interface CardService {

    CardResponse getKeywordCards(String keyword);

    DataCardResponse getGenresCards();

    CardResponse getGenreCards(Long genreId);

    CardResponse getGenericCards();

    CardResponse getGenericCardsWithoutLogin();

    CardResponse getGenreCardsWithoutLogin(Long genreId);

    CardResponse getLanguagesCards();

    CardResponse getLanguageCards(Long langaugeId);

    CardResponse getLocalitiesCards();

    CardResponse getLocalityCards(Long localityId);

    CardResponse getTagsCards();

    CardResponse getTagCards(Long tagId);

    CardResponse getTrendingCards();

    CardResponse getCardsByDateRange(DateFilter dateFilter);
}

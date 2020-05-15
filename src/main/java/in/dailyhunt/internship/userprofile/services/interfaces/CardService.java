package in.dailyhunt.internship.userprofile.services.interfaces;

import in.dailyhunt.internship.userprofile.client_model.request.DateFilter;
import in.dailyhunt.internship.userprofile.client_model.response.DataCardResponse;

public interface CardService {

    DataCardResponse getKeywordCards(String keyword);

    DataCardResponse getGenresCards();

    DataCardResponse getGenreCards(Long genreId);

    DataCardResponse getGenericCards();

    DataCardResponse getGenericCardsWithoutLogin();

    DataCardResponse getGenreCardsWithoutLogin(Long genreId);

    DataCardResponse getLanguagesCards();

    DataCardResponse getLanguageCards(Long langaugeId);

    DataCardResponse getLocalitiesCards();

    DataCardResponse getLocalityCards(Long localityId);

    DataCardResponse getTagsCards();

    DataCardResponse getTagCards(Long tagId);

    DataCardResponse getTrendingCards();

    DataCardResponse getCardsByDateRange(DateFilter dateFilter);
}

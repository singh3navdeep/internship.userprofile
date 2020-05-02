package in.dailyhunt.internship.userprofile.services.interfaces;

import in.dailyhunt.internship.userprofile.client_model.response.CardNews;

public interface NewsService {
    CardNews getNews(Long id);
}

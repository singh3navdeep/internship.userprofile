package in.dailyhunt.internship.userprofile.services.interfaces;

import in.dailyhunt.internship.userprofile.client_model.response.Article;

public interface NewsService {
    Article getNews(Long id);
}

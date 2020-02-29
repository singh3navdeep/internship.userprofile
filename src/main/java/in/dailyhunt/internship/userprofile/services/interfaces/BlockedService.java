package in.dailyhunt.internship.userprofile.services.interfaces;
import in.dailyhunt.internship.userprofile.client_model.request.TopicOb;
public interface BlockedService {
    void addBlocked(TopicOb topicid);
    void deleteBlocked(TopicOb topicob);
}

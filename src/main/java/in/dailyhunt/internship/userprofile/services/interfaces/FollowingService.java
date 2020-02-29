package in.dailyhunt.internship.userprofile.services.interfaces;

import in.dailyhunt.internship.userprofile.client_model.request.TopicOb;

public interface FollowingService {
    void addFollowing(TopicOb topicid);

    void deleteFollowing(TopicOb topicob);
}

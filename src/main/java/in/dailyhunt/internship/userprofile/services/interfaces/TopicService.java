package in.dailyhunt.internship.userprofile.services.interfaces;

import in.dailyhunt.internship.userprofile.entities.Topic;

import java.util.List;

public interface TopicService {
    List<Topic> getAllTopics();

    Topic saveTopic(String name);
}

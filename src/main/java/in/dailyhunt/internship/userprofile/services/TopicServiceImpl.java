package in.dailyhunt.internship.userprofile.services;

import in.dailyhunt.internship.userprofile.entities.Topic;
import in.dailyhunt.internship.userprofile.exceptions.BadRequestException;
import in.dailyhunt.internship.userprofile.repositories.TopicRepository;
import in.dailyhunt.internship.userprofile.services.interfaces.TopicService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    private TopicRepository topicRepository;

    public TopicServiceImpl(TopicRepository topicRepository){
        this.topicRepository = topicRepository;
    }

    @Override
    @Transactional
    public List<Topic> getAllTopics(){
        return topicRepository.findAll();

    }

    @Override
    @Transactional
    public Topic saveTopic(String name) throws BadRequestException{
        if(topicRepository.existsByName(name))
            throw new BadRequestException(("This topic is already added"));
        Topic topic = Topic.builder()
                .name(name)
                .build();
        topicRepository.save(topic);
        return topic;
    }
}

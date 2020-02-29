package in.dailyhunt.internship.userprofile.services;

import in.dailyhunt.internship.userprofile.client_model.request.TopicOb;
import in.dailyhunt.internship.userprofile.entities.Topic;
import in.dailyhunt.internship.userprofile.entities.User;
import in.dailyhunt.internship.userprofile.exceptions.ResourceNotFoundException;
import in.dailyhunt.internship.userprofile.repositories.TopicRepository;
import in.dailyhunt.internship.userprofile.repositories.UserRepository;
import in.dailyhunt.internship.userprofile.services.interfaces.FollowingService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Service
public class FollowingServiceImpl implements FollowingService {


    private TopicRepository topicRepository;

    private UserRepository userRepository;


    public FollowingServiceImpl(UserRepository userRepository, TopicRepository topicRepository){
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
    }

    @Override
    @Transactional
    public void addFollowing(TopicOb topicob) throws ResourceNotFoundException {
        Optional<User> optional = userRepository.findById(topicob.getUserid());
        if(!optional.isPresent())
            throw new ResourceNotFoundException("invalid user");
        User user = optional.get();
        Set<Topic> following = user.getFollowing();
        following.add(topicRepository.findById(topicob.getTopicid()).get());
        user.setFollowing(following);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteFollowing(TopicOb topicob) throws ResourceNotFoundException {
        Optional<User> optional = userRepository.findById(topicob.getUserid());
        if(!optional.isPresent())
            throw new ResourceNotFoundException("invalid user");
        User user = optional.get();
        Set<Topic> following = user.getFollowing();
        following.remove(topicRepository.findById(topicob.getTopicid()).get());
        user.setFollowing(following);
        userRepository.save(user);
    }
}

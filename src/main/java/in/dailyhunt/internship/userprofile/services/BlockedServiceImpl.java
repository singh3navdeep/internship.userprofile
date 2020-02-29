package in.dailyhunt.internship.userprofile.services;
import in.dailyhunt.internship.userprofile.client_model.request.TopicOb;
import in.dailyhunt.internship.userprofile.entities.Topic;
import in.dailyhunt.internship.userprofile.entities.User;
import in.dailyhunt.internship.userprofile.exceptions.ResourceNotFoundException;
import in.dailyhunt.internship.userprofile.repositories.TopicRepository;
import in.dailyhunt.internship.userprofile.repositories.UserRepository;
import in.dailyhunt.internship.userprofile.services.interfaces.BlockedService;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
@Service
public class BlockedServiceImpl implements BlockedService {
    private TopicRepository topicRepository;
    private UserRepository userRepository;
    public BlockedServiceImpl(UserRepository userRepository, TopicRepository topicRepository){
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
    }
    @Override
    @Transactional
    public void addBlocked(TopicOb topicob) throws ResourceNotFoundException {
        Optional<User> optional = userRepository.findById(topicob.getUserid());
        if(!optional.isPresent())
            throw new ResourceNotFoundException("invalid user");
        User user = optional.get();
        Set<Topic> blocked = user.getBlocked();
        blocked.add(topicRepository.findById(topicob.getTopicid()).get());
        user.setBlocked(blocked);
        userRepository.save(user);
    }
    @Override
    @Transactional
    public void deleteBlocked(TopicOb topicob) throws ResourceNotFoundException {
        Optional<User> optional = userRepository.findById(topicob.getUserid());
        if(!optional.isPresent())
            throw new ResourceNotFoundException("invalid user");
        User user = optional.get();
        Set<Topic> blocked = user.getBlocked();
        blocked.remove(topicRepository.findById(topicob.getTopicid()).get());
        user.setBlocked(blocked);
        userRepository.save(user);
    }
}

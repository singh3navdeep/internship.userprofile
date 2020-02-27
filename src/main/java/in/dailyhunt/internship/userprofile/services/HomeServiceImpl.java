package in.dailyhunt.internship.userprofile.services;

import in.dailyhunt.internship.userprofile.entities.Topic;
import in.dailyhunt.internship.userprofile.entities.User;
import in.dailyhunt.internship.userprofile.exceptions.ResourceNotFoundException;
import in.dailyhunt.internship.userprofile.repositories.UserRepository;
import in.dailyhunt.internship.userprofile.client_model.response.UserDetails;
import in.dailyhunt.internship.userprofile.services.interfaces.HomeService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HomeServiceImpl implements HomeService {

    private UserRepository userRepository;

    public HomeServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails getDetailsById(Long userid) {
        Optional<User> optional = userRepository.findById(userid);
        if(!optional.isPresent()){
            throw new ResourceNotFoundException("This user does not exist");
        }
        User user = optional.get();
        return UserDetails.from(user);

    }

    @Override
    @Transactional
    public List<Topic> getFollowingById(Long userid){
        Optional<User> optional = userRepository.findById(userid);
        if(!optional.isPresent()){
            throw new ResourceNotFoundException("This user does not exist");
        }
        User user = optional.get();
        List<Topic> list = new ArrayList<Topic>(user.getFollowing());
        return list;
    }

    @Override
    @Transactional
    public List<Topic> getBlockedById(Long userid){
        Optional<User> optional = userRepository.findById(userid);
        if(!optional.isPresent()){
            throw new ResourceNotFoundException("This user does not exist");
        }
        User user = optional.get();
        List<Topic> list = new ArrayList<Topic>(user.getBlocked());
        return list;
    }
}

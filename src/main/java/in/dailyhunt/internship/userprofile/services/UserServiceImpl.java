package in.dailyhunt.internship.userprofile.services;

import in.dailyhunt.internship.userprofile.client_model.request.SignUpForm;
import in.dailyhunt.internship.userprofile.entities.Gender;
import in.dailyhunt.internship.userprofile.entities.Language;
import in.dailyhunt.internship.userprofile.entities.Topic;
import in.dailyhunt.internship.userprofile.entities.User;
import in.dailyhunt.internship.userprofile.enums.GenderValue;
import in.dailyhunt.internship.userprofile.exceptions.BadRequestException;
import in.dailyhunt.internship.userprofile.exceptions.ResourceNotFoundException;
import in.dailyhunt.internship.userprofile.repositories.GenderRepository;
import in.dailyhunt.internship.userprofile.repositories.LanguageRepository;
import in.dailyhunt.internship.userprofile.repositories.TopicRepository;
import in.dailyhunt.internship.userprofile.repositories.UserRepository;
import in.dailyhunt.internship.userprofile.services.interfaces.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
  //  private PasswordEncoder passwordEncoder;
    private LanguageRepository languageRepository;
    private TopicRepository topicRepository;
    private GenderRepository genderRepository;

    public UserServiceImpl(UserRepository userRepository, LanguageRepository languageRepository,
                           TopicRepository topicRepository, GenderRepository genderRepository){
        this.userRepository = userRepository;
    //    this.passwordEncoder = passwordEncoder;
        this.languageRepository = languageRepository;
        this.topicRepository = topicRepository;
        this.genderRepository = genderRepository;
    }

    @Override
    @Transactional
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User findUserById(Long userid) throws ResourceNotFoundException {
        Optional<User> optional = userRepository.findById(userid);
        if(!optional.isPresent())
            throw new ResourceNotFoundException("This user does not exist");
        return optional.get();
    }

    @Override
    @Transactional
    public User saveUser(SignUpForm signUpForm) throws BadRequestException {

        if(userRepository.existsByEmail(signUpForm.getEmail()))
            throw new BadRequestException("This email is already taken");

        User user = User.builder()
                .name(signUpForm.getName())
                .password(signUpForm.getPassword())
                .email(signUpForm.getEmail())
                .date_of_birth(signUpForm.getDate_of_birth())
                .username(signUpForm.getUsername())
                .build();
        Optional<Language> language = languageRepository.findByName(signUpForm.getNews_language());
        if(!language.isPresent())
            throw new BadRequestException("This language not available");

        user.setNews_language(language.get());

        Set<String> recieved_following = signUpForm.getFollowing();
        Set<String> recieved_blocked = signUpForm.getBlocked();

        Set<Topic> send_following = new HashSet<Topic>();
        Set<Topic> send_blocked = new HashSet<Topic>();

        for(String topic : recieved_following){
            Topic topic_following = topicRepository.findByName(topic)
                    .orElseThrow(() -> new BadRequestException("Fail! -> Cause: Topic not available."));
            send_following.add(topic_following);
        }

        for(String topic : recieved_blocked){
            Topic topic_blocked = topicRepository.findByName(topic)
                    .orElseThrow(() -> new BadRequestException("Fail! -> Cause: Topic not available."));
            send_blocked.add(topic_blocked);
        }
        user.setFollowing(send_following);
        user.setBlocked(send_blocked);

        String recieved_gender = signUpForm.getGender();
        Gender send_gender;
        if(recieved_gender.equalsIgnoreCase("male")) {
            Gender male_gender = genderRepository.findByValue(GenderValue.GENDER_MALE)
                    .orElseThrow(() -> new BadRequestException("Fail! -> Cause: Gender not available."));
            send_gender = male_gender;
        }
        else if(recieved_gender.equalsIgnoreCase("female")){
            Gender female_gender = genderRepository.findByValue(GenderValue.GENDER_FEMALE)
                    .orElseThrow(() -> new BadRequestException("Fail! -> Cause: Gender not available."));
            send_gender = female_gender;
        }
        else if(recieved_gender.equalsIgnoreCase("other")){
            Gender other_gender = genderRepository.findByValue(GenderValue.GENDER_OTHER)
                    .orElseThrow(() -> new BadRequestException("Fail! -> Cause: Gender not available."));
            send_gender = other_gender;
        }
        else{
            throw new BadRequestException("Please enter gender either male, or female, or other");
        }
        user.setGender(send_gender);
        userRepository.save(user);

        return user;
    }
}

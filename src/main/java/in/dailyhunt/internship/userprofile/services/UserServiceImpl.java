package in.dailyhunt.internship.userprofile.services;

import in.dailyhunt.internship.userprofile.client_model.request.PreferenceRequest;
import in.dailyhunt.internship.userprofile.client_model.request.SignUpForm;
import in.dailyhunt.internship.userprofile.entities.*;
import in.dailyhunt.internship.userprofile.enums.GenderValue;
import in.dailyhunt.internship.userprofile.exceptions.BadRequestException;
import in.dailyhunt.internship.userprofile.exceptions.ResourceNotFoundException;
import in.dailyhunt.internship.userprofile.repositories.GenderRepository;
import in.dailyhunt.internship.userprofile.repositories.UserRepository;
import in.dailyhunt.internship.userprofile.services.interfaces.BlockedService;
import in.dailyhunt.internship.userprofile.services.interfaces.FollowingService;
import in.dailyhunt.internship.userprofile.services.interfaces.LanguageService;
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
    private LanguageService languageService;
    private GenderRepository genderRepository;
    private FollowingService followingService;
    private BlockedService blockedService;

    public UserServiceImpl(UserRepository userRepository, LanguageService languageService,
                           GenderRepository genderRepository, FollowingService followingService,
                           BlockedService blockedService){
        this.userRepository = userRepository;
    //    this.passwordEncoder = passwordEncoder;
        this.languageService = languageService;
        this.genderRepository = genderRepository;
        this.followingService = followingService;
        this.blockedService = blockedService;
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
        if(signUpForm.getNews_language() != null) {
            Language language = languageService.findLanguageById(signUpForm.getNews_language());

            user.setNews_language(language);
        }

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
        followingService.addFollowing(PreferenceRequest.builder()
                        .userId(user.getId())
                        .genreIds(signUpForm.getFollowing_genres())
                        .languageIds(signUpForm.getFollowing_languages())
                        .localityIds(signUpForm.getFollowing_localities())
                        .tagIds(signUpForm.getFollowing_tags())
                        .build());
        blockedService.addBlocked(PreferenceRequest.builder()
                .userId(user.getId())
                .genreIds(signUpForm.getBlocked_genres())
                .languageIds(signUpForm.getBlocked_languages())
                .localityIds(signUpForm.getBlocked_localities())
                .tagIds(signUpForm.getBlocked_tags())
                .build());
        return user;
    }


}

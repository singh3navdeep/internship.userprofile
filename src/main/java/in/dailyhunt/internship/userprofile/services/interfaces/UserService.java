package in.dailyhunt.internship.userprofile.services.interfaces;

import in.dailyhunt.internship.userprofile.client_model.request.SignUpForm;
import in.dailyhunt.internship.userprofile.entities.User;
import in.dailyhunt.internship.userprofile.exceptions.ResourceNotFoundException;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User findUserById(Long userid) throws ResourceNotFoundException;

    User saveUser(SignUpForm signUpForm);

}

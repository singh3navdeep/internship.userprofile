package in.dailyhunt.internship.userprofile.client_model.response;

import in.dailyhunt.internship.userprofile.entities.User;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDetails {
    private String name;
    private String username;
    private Integer following_count;
    private Integer blocked_count;

    public static UserDetails from(User user) {
        return UserDetails.builder()
                .name(user.getName())
                .username(user.getUsername())
                .build();
    }
}

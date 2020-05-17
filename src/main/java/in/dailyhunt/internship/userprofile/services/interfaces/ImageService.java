package in.dailyhunt.internship.userprofile.services.interfaces;

import java.io.IOException;

public interface ImageService {
    String saveImage(String base_64_string) throws IOException;
}

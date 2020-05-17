package in.dailyhunt.internship.userprofile.services;

import in.dailyhunt.internship.userprofile.client_model.request.ImageDTO;
import in.dailyhunt.internship.userprofile.services.interfaces.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

//import org.springframework.cloud.client.loadbalancer.LoadBalanced;

@Service
public class ImageServiceImpl implements ImageService {

//    private final RestTemplate restTemplate;
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public ImageServiceImpl(/*RestTemplate restTemplate,*/ WebClient.Builder webClientBuilder) {
//        this.restTemplate = restTemplate;
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public String saveImage(String base_64_string) throws IOException{


//        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl = "https://dailyhunt-image-server.herokuapp.com/image/profile";
//        String fooResourceUrl = "http://image-service/image/uploadFile";
        ImageDTO imageDTO = ImageDTO.builder().base64(base_64_string).build();

        String result = webClientBuilder.build()
                .post()
                .uri(fooResourceUrl)
                .body(Mono.just(imageDTO), ImageDTO.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();

//        String result = restTemplate.postForObject(fooResourceUrl, imageDTO, String.class);
        return result;
    }

}

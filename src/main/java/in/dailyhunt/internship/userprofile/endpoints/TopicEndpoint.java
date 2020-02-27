package in.dailyhunt.internship.userprofile.endpoints;

import in.dailyhunt.internship.userprofile.exceptions.BadRequestException;
import in.dailyhunt.internship.userprofile.services.interfaces.TopicService;
import lombok.NoArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@NoArgsConstructor
@RestController
@RequestMapping(TopicEndpoint.BASE_URL)
public class TopicEndpoint {
    static final String BASE_URL = "api/v1/topic_profile";

    @Autowired
    private TopicService topicService;

    public TopicEndpoint(TopicService topicService){
        this.topicService = topicService;
    }

    @GetMapping
    public ResponseEntity<?> getAllTopics(){
        return ResponseEntity.ok().body(topicService.getAllTopics());
    }

    @PostMapping("/save")
    public ResponseEntity<String> addTopic(@Valid @RequestBody String name) throws BadRequestException{
        topicService.saveTopic(name);
        return ResponseEntity.ok().body("topic added successfully");
    }
}

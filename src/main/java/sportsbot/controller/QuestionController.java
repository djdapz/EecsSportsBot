package sportsbot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sportsbot.model.QuestionContext;
import sportsbot.model.QuestionResponse;
import sportsbot.service.QuestionProcessor;

/**
 * Created by devondapuzzo on 4/19/17.
 */

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionProcessor questionProcessor;

    @RequestMapping(method = RequestMethod.GET)
    public QuestionResponse addItem(@RequestParam(value = "query", required = false) String query) {


        QuestionContext questionContext = new QuestionContext();
        questionContext.setQuestion(query);

        questionContext = questionProcessor.answer(questionContext);

        QuestionResponse  qr = new QuestionResponse(questionContext);


        return qr;
    }

}

package com.kenzie.appserver.controller;

import com.amazonaws.Response;
import com.kenzie.appserver.controller.model.ArrowCreateRequest;
import com.kenzie.appserver.controller.model.ArrowResponse;
import com.kenzie.appserver.service.ArrowService;
import com.kenzie.appserver.service.model.Arrow;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.UUID.randomUUID;


@RestController
@RequestMapping("/message")
public class ArrowController {

    private ArrowService arrowService;

    ArrowController(ArrowService arrowService) {this.arrowService = arrowService;}

//    @PostMapping
//    public ResponseEntity<ArrowResponse> addNewArrow(@RequestBody ArrowCreateRequest arrowCreateRequest){
//        Arrow arrow = new Arrow(arrowCreateRequest.getUserId(),
//                randomUUID().toString(),
//                arrowCreateRequest.getRecipientName(),
//                arrowCreateRequest.getPhone(),
//                arrowCreateRequest.isStarred(),
//                arrowCreateRequest.getCategory(),
//                arrowCreateRequest.getContent(),
//                arrowCreateRequest.getSendDate());
//
//        //ArrowService.addNewArrow(arrow);
//
//        ArrowResponse response = createArrowResponse(arrow);
//
//        return Respons
//    }

    private ArrowResponse createArrowResponse(Arrow arrow) {
        ArrowResponse arrowResponse = new ArrowResponse();
        arrowResponse.setUserId(arrow.getUserId());
        arrowResponse.setRecipientName(arrow.getRecipientName());
        arrowResponse.setPhone(arrow.getPhone());
        arrowResponse.setStarred(arrow.isStarred());
        arrowResponse.setCategory(arrow.getCategory());
        arrowResponse.setContent(arrow.getContent());
        arrowResponse.setSendDate(arrow.getSendDate());
        return arrowResponse;
    }





}

package com.kenzie.appserver.controller;

import com.amazonaws.Response;
import com.kenzie.appserver.controller.model.ArrowCreateRequest;
import com.kenzie.appserver.controller.model.ArrowResponse;
import com.kenzie.appserver.controller.model.ArrowUpdateRequest;
import com.kenzie.appserver.service.ArrowService;
import com.kenzie.appserver.service.model.Arrow;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.UUID.randomUUID;


@RestController
@RequestMapping("/message")
public class ArrowController {

    private ArrowService arrowService;

    ArrowController(ArrowService arrowService) {this.arrowService = arrowService;}

    @PostMapping
    public ResponseEntity<ArrowResponse> addNewArrow(@RequestBody ArrowCreateRequest arrowCreateRequest){
        String forSentStatus = "pending";
        LocalDate today = LocalDate.now();
        LocalDate send = LocalDate.parse(arrowCreateRequest.getSendDate());
        if(today.isAfter(send)){
            forSentStatus = "sent";
        }

        Arrow arrow = new Arrow(arrowCreateRequest.getUserId(),
                UUID.randomUUID().toString(),
                arrowCreateRequest.getRecipientName(),
                arrowCreateRequest.getPhone(),
                arrowCreateRequest.getStarred(),
                arrowCreateRequest.getCategory(),
                arrowCreateRequest.getContent(),
                arrowCreateRequest.getSendDate(),
                forSentStatus);

        arrowService.addNewArrow(arrow);

        ArrowResponse response = createArrowResponse(arrow);

        return ResponseEntity.created(URI.create("/message/" + response.getMessageId())).body(response);
    }


    @PutMapping
    public ResponseEntity<ArrowResponse> updateArrow(@RequestBody ArrowUpdateRequest arrowUpdateRequest){
        Arrow arrow = new Arrow(arrowUpdateRequest.getUserId(),
                arrowUpdateRequest.getMessageId(),
                arrowUpdateRequest.getRecipientName(),
                arrowUpdateRequest.getPhone(),
                arrowUpdateRequest.getStarred(),
                arrowUpdateRequest.getCategory(),
                arrowUpdateRequest.getContent(),
                arrowUpdateRequest.getSendDate(),
                arrowUpdateRequest.getStatus());

        arrowService.updateArrow(arrow);

        ArrowResponse response = createArrowResponse(arrow);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllMessages")
    public ResponseEntity<List<ArrowResponse>> getArrows() {

        List<Arrow> arrows = arrowService.findAllArrows();

        List<ArrowResponse> response = new ArrayList<>();
        for(Arrow arrow: arrows){
            response.add(this.createArrowResponse(arrow));
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArrowResponse> getArrowById(@PathVariable("messageId") String messageId) {
        Arrow arrow = arrowService.findArrowById(messageId);
        if (arrow == null) {
            return ResponseEntity.notFound().build();
        }

        ArrowResponse response = createArrowResponse(arrow);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getSublist/{option}")
    public ResponseEntity<List<ArrowResponse>> getArrowsByCategory(@PathVariable("option")String option){
        List<Arrow> arrowsByCategory = arrowService.findArrowByCategory(option);
        List<ArrowResponse> response = new ArrayList<>();
        for(Arrow arrow: arrowsByCategory){
            response.add(this.createArrowResponse(arrow));
        }

        return ResponseEntity.ok(response);

    }


    @DeleteMapping("/delete{id}")
    public ResponseEntity deleteArrowById(@PathVariable("messageId") String messageId) {
        arrowService.deleteArrow(messageId);
        return ResponseEntity.noContent().build();
    }


    private ArrowResponse createArrowResponse(Arrow arrow) {
        ArrowResponse arrowResponse = new ArrowResponse();
        arrowResponse.setUserId(arrow.getUserId());
        arrowResponse.setMessageId(arrow.getMessageId());
        arrowResponse.setRecipientName(arrow.getRecipientName());
        arrowResponse.setPhone(arrow.getPhone());
        arrowResponse.setStarred(arrow.getStarred());
        arrowResponse.setCategory(arrow.getCategory());
        arrowResponse.setContent(arrow.getContent());
        arrowResponse.setSendDate(arrow.getSendDate());
        arrowResponse.setStatus(arrow.getStatus());
        return arrowResponse;
    }
}

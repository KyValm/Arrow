package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.ArrowRepository;
import com.kenzie.appserver.repositories.model.ArrowRecord;
import com.kenzie.appserver.service.model.Arrow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArrowService {

    private ArrowRepository arrowRepository;

    @Autowired
    public ArrowService(ArrowRepository arrowRepository) {
        this.arrowRepository = arrowRepository;
    }

    public Arrow addNewArrow(Arrow arrow) {
        ArrowRecord arrowRecord = new ArrowRecord();
        arrowRecord.setUserId(arrow.getUserId());
        arrowRecord.setMessageId(arrow.getMessageId());
        arrowRecord.setRecipientName(arrow.getRecipientName());
        arrowRecord.setPhone(arrow.getPhone());
        arrowRecord.setStarred(arrow.getStarred());
        arrowRecord.setCategory(arrow.getCategory());
        arrowRecord.setContent(arrow.getContent());
        arrowRecord.setSendDate(arrow.getSendDate());
        arrowRecord.setStatus(arrow.getStatus());
        arrowRepository.save(arrowRecord);
        return arrow;
    }

    public void updateArrow(Arrow arrow){
        if (arrowRepository.existsById(arrow.getMessageId())) {
            ArrowRecord arrowRecord = new ArrowRecord();
            arrowRecord.setUserId(arrow.getUserId());
            arrowRecord.setMessageId(arrow.getMessageId());
            arrowRecord.setRecipientName(arrow.getRecipientName());
            arrowRecord.setPhone(arrow.getPhone());
            arrowRecord.setStarred(arrow.getStarred());
            arrowRecord.setCategory(arrow.getCategory());
            arrowRecord.setContent(arrow.getContent());
            arrowRecord.setSendDate(arrow.getSendDate());
            arrowRecord.setStatus(arrow.getStatus());
            arrowRepository.save(arrowRecord);
        }
    }

    public List<Arrow> findAllArrows(){
        List<Arrow> arrows = new ArrayList<>();
        Iterable<ArrowRecord> arrowIterator= arrowRepository.findAll();
        for(ArrowRecord arrow: arrowIterator) {
            arrows.add(new Arrow(arrow.getUserId(),
                    arrow.getMessageId(),
                    arrow.getRecipientName(),
                    arrow.getPhone(),
                    arrow.getStarred(),
                    arrow.getCategory(),
                    arrow.getContent(),
                    arrow.getSendDate(),
                    arrow.getStatus()));
        }
        return arrows;
    }

    public Arrow findArrowById(String messageId){
        return arrowRepository
                .findById(messageId)
                .map(arrow -> new Arrow(arrow.getUserId(),
                        arrow.getMessageId(),
                        arrow.getRecipientName(),
                        arrow.getPhone(),
                        arrow.getStarred(),
                        arrow.getCategory(),
                        arrow.getContent(),
                        arrow.getSendDate(),
                        arrow.getStatus()))
                .orElse(null);
    }


    public List<Arrow> findArrowByCategory(String option){
        List<Arrow> arrowsByCategory = new ArrayList<>();
        Iterable<ArrowRecord> arrowIterator= arrowRepository.findAll();
        for(ArrowRecord arrow: arrowIterator) {
            switch (option) {
                case "friends":
                case "family":
                case "colleagues":
                    if (arrow.getCategory().equals(option)) {
                        arrowsByCategory.add(new Arrow(arrow.getUserId(),
                                arrow.getMessageId(),
                                arrow.getRecipientName(),
                                arrow.getPhone(),
                                arrow.getStarred(),
                                arrow.getCategory(),
                                arrow.getContent(),
                                arrow.getSendDate(),
                                arrow.getStatus()));
                    }
                    break;
                case "sent":
                    if (arrow.getStatus().equals("sent")) {
                        arrowsByCategory.add(new Arrow(arrow.getUserId(),
                                arrow.getMessageId(),
                                arrow.getRecipientName(),
                                arrow.getPhone(),
                                arrow.getStarred(),
                                arrow.getCategory(),
                                arrow.getContent(),
                                arrow.getSendDate(),
                                arrow.getStatus()));
                    }
                    break;
                case "starred":
                    if (arrow.getStarred().equals("starred")) {
                        arrowsByCategory.add(new Arrow(arrow.getUserId(),
                                arrow.getMessageId(),
                                arrow.getRecipientName(),
                                arrow.getPhone(),
                                arrow.getStarred(),
                                arrow.getCategory(),
                                arrow.getContent(),
                                arrow.getSendDate(),
                                arrow.getStatus()));
                    }
                    break;

                default: throw new IllegalArgumentException();
            }

        }
        return arrowsByCategory;
    }


    public void deleteArrow(String messageId){
        arrowRepository.deleteById(messageId);
    }

}

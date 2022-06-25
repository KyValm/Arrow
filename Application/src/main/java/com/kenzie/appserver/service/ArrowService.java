package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.ArrowRepository;
import com.kenzie.appserver.repositories.model.ArrowRecord;
import com.kenzie.appserver.service.model.Arrow;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArrowService {
    private ArrowRepository arrowRepository;

    public ArrowService(ArrowRepository arrowRepository) {
        this.arrowRepository = arrowRepository;
    }

    public Arrow addNewArrow(Arrow arrow){
        ArrowRecord arrowRecord = new ArrowRecord();
        arrowRecord.setUserId(arrow.getUserId());
        arrowRecord.setMessageId(arrow.getMessageId());
        arrowRecord.setRecipientName(arrow.getRecipientName());
        arrowRecord.setPhone(arrow.getPhone());
        arrowRecord.setStarred(arrow.isStarred());
        arrowRecord.setCategory(arrow.getCategory());
        arrowRecord.setContent(arrow.getContent());
        arrowRecord.setSendDate(arrow.getSendDate());
        arrowRecord.setSent(arrow.isSent());
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
            arrowRecord.setStarred(arrow.isStarred());
            arrowRecord.setCategory(arrow.getCategory());
            arrowRecord.setContent(arrow.getContent());
            arrowRecord.setSendDate(arrow.getSendDate());
            arrowRecord.setSent(arrow.isSent());
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
                    arrow.isStarred(),
                    arrow.getCategory(),
                    arrow.getContent(),
                    arrow.getSendDate()));
        }
        return arrows;
    }

    public Arrow findArrowById(String messageId){
        Arrow arrowFromBackendService = arrowRepository
                .findById(messageId)
                .map(arrow -> new Arrow(arrow.getUserId(),
                        arrow.getMessageId(),
                        arrow.getRecipientName(),
                        arrow.getPhone(),
                        arrow.isStarred(),
                        arrow.getCategory(),
                        arrow.getContent(),
                        arrow.getSendDate()))
                .orElse(null);

        return arrowFromBackendService;
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
                                arrow.isStarred(),
                                arrow.getCategory(),
                                arrow.getContent(),
                                arrow.getSendDate()));
                    }
                    break;
                case "isSent":
                    if (arrow.isSent()) {
                        arrowsByCategory.add(new Arrow(arrow.getUserId(),
                                arrow.getMessageId(),
                                arrow.getRecipientName(),
                                arrow.getPhone(),
                                arrow.isStarred(),
                                arrow.getCategory(),
                                arrow.getContent(),
                                arrow.getSendDate()));
                    }
                    break;
                case "isStarred":
                    if (arrow.isStarred()) {
                        arrowsByCategory.add(new Arrow(arrow.getUserId(),
                                arrow.getMessageId(),
                                arrow.getRecipientName(),
                                arrow.getPhone(),
                                arrow.isStarred(),
                                arrow.getCategory(),
                                arrow.getContent(),
                                arrow.getSendDate()));
                    }
                    break;
            }

        }
        return arrowsByCategory;
    }


    public void deleteArrow(String messageId){
        arrowRepository.deleteById(messageId);
    }

}

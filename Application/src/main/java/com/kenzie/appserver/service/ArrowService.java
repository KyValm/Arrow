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

    public void deleteArrow(String messageId){
        arrowRepository.deleteById(messageId);
    }








}

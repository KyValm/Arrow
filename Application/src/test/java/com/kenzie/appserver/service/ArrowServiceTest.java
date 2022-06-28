package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.ArrowRepository;
import com.kenzie.appserver.repositories.model.ArrowRecord;
import com.kenzie.appserver.service.model.Arrow;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.*;

public class ArrowServiceTest {
    private ArrowRepository arrowRepository;
    private ArrowService arrowService;

    @BeforeEach
    void setup() {
        arrowRepository = mock(ArrowRepository.class);
        arrowService = new ArrowService(arrowRepository);
    }


    @Test
    void addNewArrow() {
        //GIVEN
        Arrow arrow = new Arrow(randomUUID().toString(),
                randomUUID().toString(),
                "John Test",
                "909-000-0000",
                "starred",
                "friends",
                "test message",
                "10-14-2022");

        ArgumentCaptor<ArrowRecord> argumentCaptor = ArgumentCaptor.forClass(ArrowRecord.class);

        //WHEN
        Arrow returnedArrow = arrowService.addNewArrow(arrow);

        //THEN
        Assertions.assertNotNull(returnedArrow);

        verify(arrowRepository).save(argumentCaptor.capture());

        ArrowRecord record = argumentCaptor.getValue();

        Assertions.assertNotNull(record, "The arrow record is returned");
        Assertions.assertEquals(arrow.getUserId(), record.getUserId());
        Assertions.assertEquals(arrow.getMessageId(), record.getMessageId());
        Assertions.assertEquals(arrow.getRecipientName(), record.getRecipientName());
        Assertions.assertEquals(arrow.getPhone(), record.getPhone());
        Assertions.assertEquals(arrow.getStarred(), record.getStarred());
        Assertions.assertEquals(arrow.getCategory(), record.getCategory());
        Assertions.assertEquals(arrow.getContent(), record.getContent());
        Assertions.assertEquals(arrow.getSendDate(), record.getSendDate());
        Assertions.assertEquals(arrow.getStatus(), record.getStatus());

    }

    @Test // TODO : Test needs to be fixed, need to throw exception
    void addNewArrow_nullId_throwsException() {
        //GIVEN
        Arrow arrow = new Arrow(null,
                randomUUID().toString(),
                "John Test",
                "909-000-0000",
                "starred",
                "friends",
                "test message",
                "10-14-2022");
        //WHEN
        //THEN
       // Assertions.assertThrows(IllegalArgumentException.class, () -> arrowService.addNewArrow(arrow));
    }

    @Test
    void updateArrow() {
        //GIVEN
        String userId = randomUUID().toString();
        String messageId = randomUUID().toString();
        ArrowRecord arrowRecord = new ArrowRecord();
        arrowRecord.setUserId(userId);
        arrowRecord.setMessageId(messageId);
        arrowRecord.setRecipientName("John Test");
        arrowRecord.setPhone("909-000-0000");
        arrowRecord.setStarred("starred");
        arrowRecord.setCategory("friends");
        arrowRecord.setContent("Message did not update successfully.");
        arrowRecord.setSendDate("10-14-2022");
        arrowRecord.setStatus("pending");

        when(arrowRepository.findById(messageId)).thenReturn(Optional.of(arrowRecord));
        //WHEN
        when(arrowRepository.existsById(messageId)).thenReturn(true);
        Arrow arrow = arrowService.findArrowById(messageId);
        arrowRecord.setContent("The message updated successfully.");
        arrowService.updateArrow(arrow);
        Arrow updatedArrow = arrowService.findArrowById(messageId);
        //THEN
        Assertions.assertNotEquals(arrow.getContent(), updatedArrow.getContent());
        Assertions.assertEquals("The message updated successfully.", updatedArrow.getContent());

    }
    @Test
    void findAllArrows() {
        //GIVEN

        //WHEN

        //THEN

    }

    @Test
    void findArrowById() {
        //GIVEN

        //WHEN

        //THEN

    }

    @Test
    void findArrowByCategory() {
        //GIVEN

        //WHEN

        //THEN

    }

    @Test
    void deleteArrow() {
        //GIVEN
        String messageId = randomUUID().toString();

        //WHEN
        arrowService.deleteArrow(messageId);

        //THEN
        verify(arrowRepository).deleteById(messageId);
    }

}

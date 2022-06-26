package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.ArrowRepository;
import com.kenzie.appserver.repositories.model.ArrowRecord;
import com.kenzie.appserver.service.model.Arrow;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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

    @Test
    void addNewArrow_invalidParameters_throwsException() {
        //GIVEN

        //WHEN

        //THEN

    }

    @Test
    void updateArrow() {
        //GIVEN

        //WHEN

        //THEN

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

        //WHEN

        //THEN

    }

}

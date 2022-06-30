package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.ArrowRepository;
import com.kenzie.appserver.repositories.model.ArrowRecord;
import com.kenzie.appserver.service.model.Arrow;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
                "10-14-2022",
                "pending");

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
                "10-14-2022",
                "pending");
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
        ArrowRecord record1 = new ArrowRecord();
        record1.setUserId(randomUUID().toString());
        record1.setMessageId(randomUUID().toString());
        record1.setRecipientName("Joe Test");
        record1.setPhone("909-000-0000");
        record1.setStarred("starred");
        record1.setCategory("friends");
        record1.setContent("Message did not update successfully.");
        record1.setSendDate("10-14-2022");
        record1.setStatus("pending");

        ArrowRecord record2 = new ArrowRecord();
        record2.setUserId(randomUUID().toString());
        record2.setMessageId(randomUUID().toString());
        record2.setRecipientName("Jane Test");
        record2.setPhone("909-000-0000");
        record2.setStarred("starred");
        record2.setCategory("family");
        record2.setContent("Message did not update successfully.");
        record2.setSendDate("10-14-2022");
        record2.setStatus("sent");

        //WHEN

        List<ArrowRecord> recordList = new ArrayList<>();
        recordList.add(record1);
        recordList.add(record2);
        when(arrowRepository.findAll()).thenReturn(recordList);

        //THEN


    }

    @Test
    void findArrowById() {
        //GIVEN

        //WHEN

        //THEN

    }

    @Test
    void findArrowByCategory_friends() {
        //GIVEN
        ArrowRecord record1 = new ArrowRecord();
        record1.setUserId(randomUUID().toString());
        record1.setMessageId(randomUUID().toString());
        record1.setRecipientName("John Test");
        record1.setPhone("909-000-0000");
        record1.setStarred("starred");
        record1.setCategory("friends");
        record1.setContent("Message did not update successfully.");
        record1.setSendDate("10-14-2022");
        record1.setStatus("pending");

        ArrowRecord record2 = new ArrowRecord();
        record2.setUserId(randomUUID().toString());
        record2.setMessageId(randomUUID().toString());
        record2.setRecipientName("Mary Test");
        record2.setPhone("909-000-0000");
        record2.setStarred("starred");
        record2.setCategory("family");
        record2.setContent("Message did not update successfully.");
        record2.setSendDate("10-14-2022");
        record2.setStatus("sent");

        ArrowRecord record3 = new ArrowRecord();
        record3.setUserId(randomUUID().toString());
        record3.setMessageId(randomUUID().toString());
        record3.setRecipientName("John Test");
        record3.setPhone("909-000-0000");
        record3.setStarred("notStarred");
        record3.setCategory("colleagues");
        record3.setContent("Message did not update successfully.");
        record3.setSendDate("10-14-2022");
        record3.setStatus("pending");

        List<ArrowRecord> recordList = new ArrayList<>();
        recordList.add(record1);
        recordList.add(record2);
        recordList.add(record3);
        when(arrowRepository.findAll()).thenReturn(recordList);

        //WHEN
        List<Arrow> arrows = arrowService.findArrowByCategory("friends");

        //THEN
        Assertions.assertNotNull(arrows, "The arrow list is returned");
        Assertions.assertEquals(1, arrows.size(), "There is one arrow from friend category.");

        for (Arrow arrow : arrows) {
            if (arrow.getMessageId() == record1.getMessageId()) {
                Assertions.assertEquals(record1.getUserId(), arrow.getUserId(), "The user id matches");
                Assertions.assertEquals(record1.getRecipientName(), arrow.getRecipientName(), "The recipient name matches");
                Assertions.assertEquals(record1.getPhone(), arrow.getPhone(), "The phone number matches");
                Assertions.assertEquals(record1.getStarred(), arrow.getStarred(), "The starred status matches");
                Assertions.assertEquals(record1.getCategory(), arrow.getCategory(), "The category matches");
                Assertions.assertEquals(record1.getContent(), arrow.getContent(), "The content matches");
                Assertions.assertEquals(record1.getSendDate(), arrow.getSendDate(), "The send date matches");
                Assertions.assertEquals(record1.getStatus(), arrow.getStatus(), "The status matches");
            } else {
                Assertions.assertTrue(false, "Arrow returned that was not in the records!");
            }
        }

    }

    @Test
    void findArrowByCategory_family() {
        //GIVEN
        ArrowRecord record1 = new ArrowRecord();
        record1.setUserId(randomUUID().toString());
        record1.setMessageId(randomUUID().toString());
        record1.setRecipientName("John Test");
        record1.setPhone("909-000-0000");
        record1.setStarred("starred");
        record1.setCategory("friends");
        record1.setContent("Message did not update successfully.");
        record1.setSendDate("10-14-2022");
        record1.setStatus("pending");

        ArrowRecord record2 = new ArrowRecord();
        record2.setUserId(randomUUID().toString());
        record2.setMessageId(randomUUID().toString());
        record2.setRecipientName("Mary Test");
        record2.setPhone("909-000-0000");
        record2.setStarred("starred");
        record2.setCategory("family");
        record2.setContent("Message did not update successfully.");
        record2.setSendDate("10-14-2022");
        record2.setStatus("pending");

        ArrowRecord record3 = new ArrowRecord();
        record3.setUserId(randomUUID().toString());
        record3.setMessageId(randomUUID().toString());
        record3.setRecipientName("John Test");
        record3.setPhone("909-000-0000");
        record3.setStarred("notStarred");
        record3.setCategory("colleagues");
        record3.setContent("Message did not update successfully.");
        record3.setSendDate("10-14-2022");
        record3.setStatus("pending");

        List<ArrowRecord> recordList = new ArrayList<>();
        recordList.add(record1);
        recordList.add(record2);
        recordList.add(record3);
        when(arrowRepository.findAll()).thenReturn(recordList);

        //WHEN
        List<Arrow> arrows = arrowService.findArrowByCategory("family");

        //THEN
        Assertions.assertNotNull(arrows, "The arrow list is returned");
        Assertions.assertEquals(1, arrows.size(), "There is one arrow from family category.");

        for (Arrow arrow : arrows) {
            if (arrow.getMessageId() == record2.getMessageId()) {
                Assertions.assertEquals(record2.getUserId(), arrow.getUserId(), "The user id matches");
                Assertions.assertEquals(record2.getRecipientName(), arrow.getRecipientName(), "The recipient name matches");
                Assertions.assertEquals(record2.getPhone(), arrow.getPhone(), "The phone number matches");
                Assertions.assertEquals(record2.getStarred(), arrow.getStarred(), "The starred status matches");
                Assertions.assertEquals(record2.getCategory(), arrow.getCategory(), "The category matches");
                Assertions.assertEquals(record2.getContent(), arrow.getContent(), "The content matches");
                Assertions.assertEquals(record2.getSendDate(), arrow.getSendDate(), "The send date matches");
                Assertions.assertEquals(record2.getStatus(), arrow.getStatus(), "The status matches");
            } else {
                Assertions.assertTrue(false, "Arrow returned that was not in the records!");
            }
        }

    }

    @Test
    void findArrowByCategory_colleagues() {
        //GIVEN
        ArrowRecord record1 = new ArrowRecord();
        record1.setUserId(randomUUID().toString());
        record1.setMessageId(randomUUID().toString());
        record1.setRecipientName("John Test");
        record1.setPhone("909-000-0000");
        record1.setStarred("starred");
        record1.setCategory("friends");
        record1.setContent("Message did not update successfully.");
        record1.setSendDate("10-14-2022");
        record1.setStatus("pending");

        ArrowRecord record2 = new ArrowRecord();
        record2.setUserId(randomUUID().toString());
        record2.setMessageId(randomUUID().toString());
        record2.setRecipientName("Mary Test");
        record2.setPhone("909-000-0000");
        record2.setStarred("starred");
        record2.setCategory("family");
        record2.setContent("Message did not update successfully.");
        record2.setSendDate("10-14-2022");
        record2.setStatus("pending");

        ArrowRecord record3 = new ArrowRecord();
        record3.setUserId(randomUUID().toString());
        record3.setMessageId(randomUUID().toString());
        record3.setRecipientName("John Test");
        record3.setPhone("909-000-0000");
        record3.setStarred("notStarred");
        record3.setCategory("colleagues");
        record3.setContent("Message did not update successfully.");
        record3.setSendDate("10-14-2022");
        record3.setStatus("pending");

        List<ArrowRecord> recordList = new ArrayList<>();
        recordList.add(record1);
        recordList.add(record2);
        recordList.add(record3);
        when(arrowRepository.findAll()).thenReturn(recordList);

        //WHEN
        List<Arrow> arrows = arrowService.findArrowByCategory("colleagues");

        //THEN
        Assertions.assertNotNull(arrows, "The arrow list is returned");
        Assertions.assertEquals(1, arrows.size(), "There is one arrow from colleagues category.");

        for (Arrow arrow : arrows) {
            if (arrow.getMessageId() == record3.getMessageId()) {
                Assertions.assertEquals(record3.getUserId(), arrow.getUserId(), "The user id matches");
                Assertions.assertEquals(record3.getRecipientName(), arrow.getRecipientName(), "The recipient name matches");
                Assertions.assertEquals(record3.getPhone(), arrow.getPhone(), "The phone number matches");
                Assertions.assertEquals(record3.getStarred(), arrow.getStarred(), "The starred status matches");
                Assertions.assertEquals(record3.getCategory(), arrow.getCategory(), "The category matches");
                Assertions.assertEquals(record3.getContent(), arrow.getContent(), "The content matches");
                Assertions.assertEquals(record3.getSendDate(), arrow.getSendDate(), "The send date matches");
                Assertions.assertEquals(record3.getStatus(), arrow.getStatus(), "The status matches");
            } else {
                Assertions.assertTrue(false, "Arrow returned that was not in the records!");
            }
        }

    }

    @Test
    void findArrowByCategory_starred() {
        //GIVEN
        ArrowRecord record1 = new ArrowRecord();
        record1.setUserId(randomUUID().toString());
        record1.setMessageId(randomUUID().toString());
        record1.setRecipientName("John Test");
        record1.setPhone("909-000-0000");
        record1.setStarred("starred");
        record1.setCategory("friends");
        record1.setContent("Message did not update successfully.");
        record1.setSendDate("10-14-2022");
        record1.setStatus("pending");

        ArrowRecord record2 = new ArrowRecord();
        record2.setUserId(randomUUID().toString());
        record2.setMessageId(randomUUID().toString());
        record2.setRecipientName("Mary Test");
        record2.setPhone("909-000-0000");
        record2.setStarred("starred");
        record2.setCategory("family");
        record2.setContent("Message did not update successfully.");
        record2.setSendDate("10-14-2022");
        record2.setStatus("pending");

        ArrowRecord record3 = new ArrowRecord();
        record3.setUserId(randomUUID().toString());
        record3.setMessageId(randomUUID().toString());
        record3.setRecipientName("John Test");
        record3.setPhone("909-000-0000");
        record3.setStarred("notStarred");
        record3.setCategory("colleagues");
        record3.setContent("Message did not update successfully.");
        record3.setSendDate("10-14-2022");
        record3.setStatus("pending");

        List<ArrowRecord> recordList = new ArrayList<>();
        recordList.add(record1);
        recordList.add(record2);
        recordList.add(record3);
        when(arrowRepository.findAll()).thenReturn(recordList);

        //WHEN
        List<Arrow> arrows = arrowService.findArrowByCategory("starred");

        //THEN
        Assertions.assertNotNull(arrows, "The arrow list is returned");
        Assertions.assertEquals(2, arrows.size(), "There are two arrows that are starred.");

        for (Arrow arrow : arrows) {
            if (arrow.getMessageId() == record1.getMessageId()) {
                Assertions.assertEquals(record1.getUserId(), arrow.getUserId(), "The user id matches");
                Assertions.assertEquals(record1.getRecipientName(), arrow.getRecipientName(), "The recipient name matches");
                Assertions.assertEquals(record1.getPhone(), arrow.getPhone(), "The phone number matches");
                Assertions.assertEquals(record1.getStarred(), arrow.getStarred(), "The starred status matches");
                Assertions.assertEquals(record1.getCategory(), arrow.getCategory(), "The category matches");
                Assertions.assertEquals(record1.getContent(), arrow.getContent(), "The content matches");
                Assertions.assertEquals(record1.getSendDate(), arrow.getSendDate(), "The send date matches");
                Assertions.assertEquals(record1.getStatus(), arrow.getStatus(), "The status matches");
            } else if (arrow.getMessageId() == record2.getMessageId()){
                Assertions.assertEquals(record2.getUserId(), arrow.getUserId(), "The user id matches");
                Assertions.assertEquals(record2.getRecipientName(), arrow.getRecipientName(), "The recipient name matches");
                Assertions.assertEquals(record2.getPhone(), arrow.getPhone(), "The phone number matches");
                Assertions.assertEquals(record2.getStarred(), arrow.getStarred(), "The starred status matches");
                Assertions.assertEquals(record2.getCategory(), arrow.getCategory(), "The category matches");
                Assertions.assertEquals(record2.getContent(), arrow.getContent(), "The content matches");
                Assertions.assertEquals(record2.getSendDate(), arrow.getSendDate(), "The send date matches");
                Assertions.assertEquals(record2.getStatus(), arrow.getStatus(), "The status matches");

            } else {
                Assertions.assertTrue(false, "Arrow returned that was not in the records!");
            }
        }

    }

    @Test // TODO : Test needs to be fixed, status is always "pending"
    void findArrowByCategory_status() {
        //GIVEN
        ArrowRecord record1 = new ArrowRecord();
        record1.setUserId(randomUUID().toString());
        record1.setMessageId(randomUUID().toString());
        record1.setRecipientName("John Test");
        record1.setPhone("909-000-0000");
        record1.setStarred("starred");
        record1.setCategory("friends");
        record1.setContent("Message did not update successfully.");
        record1.setSendDate("2022-01-14");
        record1.setStatus("sent");

        //WHEN
        List<ArrowRecord> recordList = new ArrayList<>();
        recordList.add(record1);
        when(arrowRepository.findAll()).thenReturn(recordList);
        List<Arrow> arrows = arrowService.findArrowByCategory("sent");

        //THEN
        Assertions.assertNotNull(arrows, "The arrow list is returned");
        Assertions.assertEquals(1, arrows.size(), "There is one arrows that are starred.");

        for (Arrow arrow : arrows) {
            if (arrow.getMessageId() == record1.getMessageId()) {
                Assertions.assertEquals(record1.getUserId(), arrow.getUserId(), "The user id matches");
                Assertions.assertEquals(record1.getRecipientName(), arrow.getRecipientName(), "The recipient name matches");
                Assertions.assertEquals(record1.getPhone(), arrow.getPhone(), "The phone number matches");
                Assertions.assertEquals(record1.getStarred(), arrow.getStarred(), "The starred status matches");
                Assertions.assertEquals(record1.getCategory(), arrow.getCategory(), "The category matches");
                Assertions.assertEquals(record1.getContent(), arrow.getContent(), "The content matches");
                Assertions.assertEquals(record1.getSendDate(), arrow.getSendDate(), "The send date matches");
                Assertions.assertEquals(record1.getStatus(), arrow.getStatus(), "The status matches");
            } else {
                Assertions.assertTrue(false, "Arrow returned that was not in the records!");
            }
        }

    }

    @Test
    void findArrowByCategory_invalidInput_ExceptionThrown() {
        //GIVEN
        ArrowRecord record1 = new ArrowRecord();
        record1.setUserId(randomUUID().toString());
        record1.setMessageId(randomUUID().toString());
        record1.setRecipientName("John Test");
        record1.setPhone("909-000-0000");
        record1.setStarred("starred");
        record1.setCategory("friends");
        record1.setContent("Message did not update successfully.");
        record1.setSendDate("10-14-2022");
        record1.setStatus("pending");

        List<ArrowRecord> recordList = new ArrayList<>();
        recordList.add(record1);
        when(arrowRepository.findAll()).thenReturn(recordList);

        //WHEN+THEN
        assertThrows(IllegalArgumentException.class,
                () -> arrowService.findArrowByCategory("classmates"),
                "Expected exception to be thrown when option does not exist");
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

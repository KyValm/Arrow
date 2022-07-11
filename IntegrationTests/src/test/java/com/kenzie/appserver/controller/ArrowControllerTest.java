package com.kenzie.appserver.controller;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.ArrowCreateRequest;
import com.kenzie.appserver.service.ArrowService;
import com.kenzie.appserver.service.model.Arrow;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.module.SimpleModule;

import java.time.LocalDate;
import java.util.UUID;

import static java.util.UUID.randomUUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
class ArrowControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    ArrowService arrowService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void loadArrow_withValidInputs_ArrowCreated() throws Exception {
        // GIVEN
        Arrow arrow = new Arrow(randomUUID().toString(),
                randomUUID().toString(),
                mockNeat.strings().valStr(),
                "909-000-0000",
                "starred",
                "friends",
                "test message",
                "2022-07-12",
                "pending");

        ArrowCreateRequest arrowCreateRequest = new ArrowCreateRequest();
        arrowCreateRequest.setUserId(arrow.getUserId());
        arrowCreateRequest.setMessageId(arrow.getMessageId());
        arrowCreateRequest.setRecipientName(arrow.getRecipientName());
        arrowCreateRequest.setPhone(arrow.getPhone());
        arrowCreateRequest.setStarred(arrow.getStarred());
        arrowCreateRequest.setCategory(arrow.getCategory());
        arrowCreateRequest.setContent(arrow.getContent());
        arrowCreateRequest.setSendDate(arrow.getSendDate());

        // WHEN
        mvc.perform(post("/message")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(arrowCreateRequest)))
                // THEN
                .andExpect(jsonPath("userId")
                        .exists())
                .andExpect(jsonPath("messageId")
                        .value(is(arrow.getMessageId())))
                .andExpect(jsonPath("recipientName")
                        .value(is(arrow.getRecipientName())))
                .andExpect(jsonPath("phone")
                        .value(is(arrow.getPhone())))
                .andExpect(jsonPath("starred")
                        .value(is(arrow.getStarred())))
                .andExpect(jsonPath("category")
                        .value(is(arrow.getCategory())))
                .andExpect(jsonPath("content")
                        .value(is(arrow.getContent())))
                .andExpect(jsonPath("sendDate")
                        .value(is(arrow.getSendDate())))
                .andExpect(status().isCreated());

    }

    @Test
    public void updateMessage_successful() throws Exception {
        // GIVEN
        Arrow arrow = new Arrow(randomUUID().toString(),
                randomUUID().toString(),
                mockNeat.strings().valStr(),
                "909-000-0000",
                "starred",
                "friends",
                "test message",
                "2022-07-12",
                "pending");

        Arrow arrow1 = arrowService.addNewArrow(arrow);
        String updatedContent = "The message was updated successfully";

        ArrowCreateRequest arrowCreateRequest = new ArrowCreateRequest();
        arrowCreateRequest.setUserId(arrow.getUserId());
        arrowCreateRequest.setMessageId(arrow.getMessageId());
        arrowCreateRequest.setRecipientName(arrow.getRecipientName());
        arrowCreateRequest.setPhone(arrow.getPhone());
        arrowCreateRequest.setStarred(arrow.getStarred());
        arrowCreateRequest.setCategory(arrow.getCategory());
        arrowCreateRequest.setContent(updatedContent);
        arrowCreateRequest.setSendDate(arrow.getSendDate());

        // WHEN
        mvc.perform(put("/message")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(arrowCreateRequest)))
                // THEN
                .andExpect(jsonPath("userId")
                        .exists())
                .andExpect(jsonPath("messageId")
                        .value(is(arrow.getMessageId())))
                .andExpect(jsonPath("recipientName")
                        .value(is(arrow.getRecipientName())))
                .andExpect(jsonPath("phone")
                        .value(is(arrow.getPhone())))
                .andExpect(jsonPath("starred")
                        .value(is(arrow.getStarred())))
                .andExpect(jsonPath("category")
                        .value(is(arrow.getCategory())))
                .andExpect(jsonPath("content")
                        .value(is(updatedContent)))
                .andExpect(jsonPath("sendDate")
                        .value(is(arrow.getSendDate())))
                .andExpect(status().isOk());

    }

    @Test
    public void deleteArrow_DeleteSuccessful() throws Exception {
        // GIVEN
        Arrow arrow = new Arrow(randomUUID().toString(),
                randomUUID().toString(),
                "John Test",
                "909-000-0000",
                "starred",
                "friends",
                "test message",
                "2022-07-12",
                "pending");

        Arrow persistedArrow = arrowService.addNewArrow(arrow);

        // WHEN
        mvc.perform(delete("/message/delete/{id}", persistedArrow.getMessageId())
                        .accept(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isNoContent());
        assertThat(arrowService.findArrowById(persistedArrow.getMessageId())).isNull();
    }

    @Test
    public void getMessageById_Exists() throws Exception {
        String id = UUID.randomUUID().toString();

        Arrow arrow = new Arrow(randomUUID().toString(),
                id,
                "John Test",
                "909-000-0000",
                "starred",
                "friends",
                "test message",
                "2022-07-12",
                "pending");

        Arrow persistedArrow = arrowService.addNewArrow(arrow);

        mvc.perform(get("/message/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void getAll_Contains_Messages() throws Exception {

        String id = UUID.randomUUID().toString();

        Arrow arrow = new Arrow(randomUUID().toString(),
                id,
                "John Test",
                "909-000-0000",
                "starred",
                "friends",
                "test message",
                "2022-07-12",
                "pending");

        Arrow arrow2 = new Arrow(randomUUID().toString(),
                UUID.randomUUID().toString(),
                "Test",
                "909-000-0000",
                "starred",
                "friends",
                "test message",
                "2022-07-12",
                "pending");

        Arrow persistedArrow = arrowService.addNewArrow(arrow);

        Arrow persistedArrow2 = arrowService.addNewArrow(arrow);

        mvc.perform(get("/message/getAllMessages")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

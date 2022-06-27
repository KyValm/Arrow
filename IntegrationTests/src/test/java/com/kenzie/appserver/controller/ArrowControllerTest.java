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

import static java.util.UUID.randomUUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

        Arrow arrow = new Arrow(randomUUID().toString(),
                randomUUID().toString(),
                mockNeat.strings().valStr(),
                "909-000-0000",
                "starred",
                "friends",
                "test message",
                "10-14-2022");

        ArrowCreateRequest arrowCreateRequest = new ArrowCreateRequest();
        arrowCreateRequest.setUserId(arrow.getUserId());
        arrowCreateRequest.setMessageId(arrow.getMessageId());
        arrowCreateRequest.setRecipientName(arrow.getRecipientName());
        arrowCreateRequest.setPhone(arrow.getPhone());
        arrowCreateRequest.setStarred(arrow.getStarred());
        arrowCreateRequest.setCategory(arrow.getCategory());
        arrowCreateRequest.setContent(arrow.getContent());
        arrowCreateRequest.setSendDate(arrow.getSendDate());

       // mapper.registerModule(new SimpleModule());

        // WHEN
        mvc.perform(post("/messsage")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(arrowCreateRequest)))
        // THEN
                /*.andExpect(jsonPath("userId")
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
                        .value(is(arrow.getSendDate())))*/
                .andExpect(status().isCreated());

    }

    //TODO: add more tests below

}

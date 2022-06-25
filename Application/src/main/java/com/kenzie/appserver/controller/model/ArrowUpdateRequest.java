package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ArrowUpdateRequest {
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("messageId")
    private String messageId;
    @JsonProperty("recipientName")
    private String recipientName;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("starred")
    private String starred;
    @JsonProperty("category")
    private String category;
    @JsonProperty("content")
    private String content;
    @JsonProperty("sendDate")
    private String sendDate;


    public String getUserId() { return userId; }

    public String getMessageId() { return messageId; }

    public String getRecipientName() { return recipientName; }

    public String getPhone() { return phone; }

    public String getStarred() { return starred; }

    public String getCategory() { return category; }

    public String getContent() { return content; }

    public String getSendDate() { return sendDate; }

    public void setUserId(String userId) { this.userId = userId; }

    public void setMessageId(String messageId) { this.messageId = messageId; }

    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }

    public void setPhone(String phone) { this.phone = phone; }

    public void setStarred(String starred) { this.starred = starred; }

    public void setCategory(String category) { this.category = category; }

    public void setContent(String content) { this.content = content; }

    public void setSendDate(String sendDate) { this.sendDate = sendDate; }

}

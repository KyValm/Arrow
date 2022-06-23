package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArrowResponse {

    @JsonProperty("userId")
    private String userId;
    @JsonProperty("messageId")
    private String messageId;
    @JsonProperty("recipientName")
    private String recipientName;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("isStarred")
    private boolean isStarred;
    @JsonProperty("category")
    private String category;
    @JsonProperty("content")
    private String content;
    @JsonProperty("sendDate")
    private String sendDate;
    @JsonProperty("isSent")
    private boolean isSent;

    public String getUserId() { return userId; }

    public String getMessageId() {return messageId; }

    public String getRecipientName() { return recipientName; }

    public String getPhone() { return phone; }

    public boolean isStarred() { return isStarred; }

    public String getCategory() { return category; }

    public String getContent() { return content; }

    public String getSendDate() { return sendDate; }

    public void setUserId(String userId) { this.userId = userId; }

    public void setMessageId(String messageId) { this.messageId = messageId; }

    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }

    public void setPhone(String phone) { this.phone = phone; }

    public void setStarred(boolean starred) { isStarred = starred; }

    public void setCategory(String category) { this.category = category; }

    public void setContent(String content) { this.content = content; }

    public void setSendDate(String sendDate) { this.sendDate = sendDate; }

    public void setIsSent(boolean isSent) {this.isSent = isSent; }
}

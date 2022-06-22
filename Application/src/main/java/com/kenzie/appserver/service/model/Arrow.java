package com.kenzie.appserver.service.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Arrow {
    private final String userId;
    private final String messageId;
    private String recipientName;
    private String phone;
    private boolean isStarred;
    private String category;
    private String content;
    private String sendDate;
    private boolean isSent = false; // this is to be updated automatically if send date has passed

    public Arrow(String userId,
                 String messageId,
                 String recipientName,
                 String phone,
                 boolean isStarred,
                 String category,
                 String content,
                 String sendDate
                 ) {
        this.messageId = messageId;
        this.userId = userId;
        this.recipientName = recipientName;
        this.phone = phone;
        this.isStarred = isStarred;
        this.category = category;
        this.content = content;
        this.sendDate = sendDate;
    }

    public String getUserId() { return userId; }

    public String getMessageId() { return messageId; }

    // the following parameters have getters And setters in order to be updatable

    public String getRecipientName() { return recipientName; }

    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public boolean isStarred() { return isStarred; }

    public void setStarred(boolean starred) { isStarred = starred; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public String getSendDate() { return sendDate; }

    public void setSendDate(String sendDate) { this.sendDate = sendDate; }

    // the following parameter is defaulted to false and updated when object called based on current date

    public boolean isSent() {
        LocalDate today = LocalDate.now();
        LocalDate send = LocalDate.parse(sendDate);
        isSent = today.isAfter(send);

        return isSent;
    }

}

//KyBranch pull request Test
// branch test

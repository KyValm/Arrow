package com.kenzie.appserver.service.model;

public class Arrow {
    private String userId;
    private String messageId;
    private String recipientName;
    private boolean isStarred;
    private String category;
    private String content;
    private String sendDate;
    private boolean isSent = false; // this to be updated automatically if send date has passed

    public Arrow(String userId,
                 String messageId,
                 String recipientName,
                 boolean isStarred,
                 String category,
                 String content,
                 String sendDate
                 ) {
        this.messageId = messageId;
        this.userId = userId;
        this.recipientName = recipientName;
        this.isStarred = isStarred;
        this.category = category;
        this.content = content;
        this.sendDate = sendDate;
    }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getMessageId() { return messageId; }

    public void setMessageId(String messageId) { this.messageId = messageId; }

    public String getRecipientName() { return recipientName; }

    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }

    public boolean isStarred() { return isStarred; }

    public void setStarred(boolean starred) { isStarred = starred; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public String getSendDate() { return sendDate; }

    public void setSendDate(String sendDate) { this.sendDate = sendDate; }

    public boolean isSent() { return isSent; }

    public void setSent(boolean sent) { isSent = sent; }
}

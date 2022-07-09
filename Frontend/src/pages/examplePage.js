import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import ArrowClient from "../api/arrowClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class ExamplePage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onCreateArrow', 'getAllMessages', 'onRefresh', 'updateArrow', 'onDeleteArrow'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    mount() {
        document.getElementById('create-arrow-form').addEventListener('submit', this.onCreateArrow);
        document.getElementById('allArrows').addEventListener('click', this.getAllMessages);
        document.getElementById('update-create-arrow').addEventListener('submit', this.updateArrow);
        document.getElementById('delete-arrow').addEventListener('click', this.onDeleteArrow);
        this.client = new ArrowClient();
        this.dataStore.addChangeListener(this.getAllMessages);
        this.fetchMessages();
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async fetchMessages() {
        const messages = await this.client.getArrows(this.errorHandler);

        if(messages && messages.length > 0) {
            for (let message of messages) {
                message.message = await this.fetchMessage(message.messageId);
            }

        }

        this.dataStore.set("messages", messages);
        localStorage.setItem("messages", messages);
    }

    async fetchMessage(messageId) {
        return await this.client.getArrowById(messageId, this.errorHandler);
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    onRefresh() {
        this.fetchMessages();
    }

    async onCreateArrow(event) {

        event.preventDefault();

        let createButton = document.getElementById('create-arrow');

        const messageId = document.getElementById('messageId').value;
        const recipientName = document.getElementById('recipientName').value;
        const phone = document.getElementById('phone').value;
        const category = document.getElementById('category').value;
        const date = document.getElementById('sendDate').value;
        const content = document.getElementById('content').value;
        const starred = document.getElementById('starred').value;

        const arrow = await this.client.addNewArrow(recipientName, phone, starred, category, content, date);

        createButton.innerText = 'Create';
        this.onRefresh();
        closeForm();
    }

    getAllMessages() {

        localStorage.clear();

        let messagesHtml = "";

        const messages = this.dataStore.get("messages");

        if(messages) {
            for (const message of messages) {
                const existingMessage = document.getElementById(message.messageId);
                if(!existingMessage) {
                    messagesHtml += `                                              
                    <tr class="card" id="${message.messageId}">
                        <td hidden>${message.messageId}</td>
                        <td>${message.recipientName}</td>
                        <td>${message.phone}</td>                     
                        <td>${message.category}</td>
                        <td>${message.sendDate}</td>
                        <td>${message.content}</td>
                        <td>${message.starred}</td
                        <td hidden></td>
                        <td><input class="btn" id="update-arrow" type="button" value="Update" onclick="openUpdateMessageForm(this)"></td>
                    </tr>                            
                `;
                }
            }
        }
        document.getElementById("allMessagesToAdd").innerHTML += messagesHtml;
        document.getElementById("create-arrow-form").reset();
    }

    async onDeleteArrow(event) {

        event.preventDefault();

        closeAllMessagesForm();

        const messageIdToDelete = localStorage.getItem('messageToDelete');

        await this.client.deleteArrow(messageIdToDelete, this.errorHandler);

       document.getElementById(messageIdToDelete).remove();

        closeUpdateMessageForm()
        this.onRefresh();
    }

    async updateArrow(event) {

        event.preventDefault();

        const messageId = document.getElementById('updateMessageId').value;
        const recipientName = document.getElementById('updateRecipientName').value;
        const phone = document.getElementById('updatePhone').value;
        const category = document.getElementById('updateCategory').value;
        const date = document.getElementById('updateSendDate').value;
        const content = document.getElementById('updateContent').value;
        const starred = document.getElementById('updateStarred').value;

        const arrow = await this.client.updateArrow(messageId, recipientName, phone, starred, category, content, date);


        document.getElementById("update-arrow-form").reset();
        closeUpdateMessageForm();
        this.onRefresh();
    }
 }
/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const examplePage = new ExamplePage();
    examplePage.mount();
};

window.addEventListener('DOMContentLoaded', main);

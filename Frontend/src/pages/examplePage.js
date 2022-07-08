import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import ArrowClient from "../api/arrowClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class ExamplePage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onCreateArrow', 'getAllMessages'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('create-arrow-form').addEventListener('submit', this.onCreateArrow);
        document.getElementById('allArrows').addEventListener('click', this.getAllMessages);
        /*document.getElementById('delete-arrow').addEventListener('click', this.onDeleteArrow);*/
        this.client = new ArrowClient();
        this.dataStore.addChangeListener(this.getAllMessages());
        await this.fetchMessages();
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async fetchMessages() {
        const messages = await this.client.getArrows(this.errorHandler)

        if(messages && messages.length > 0) {
            for (let message of messages) {
                message = await this.fetchMessage(message.messageId);
            }

        }

        this.dataStore.set("messages", messages);
    }

    async fetchMessage(messageId) {
        return await this.client.getArrowById(messageId, this.errorHandler);
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    async onCreateArrow() {

        event.preventDefault();

        let createButton = document.getElementById('create-arrow');

        const recipientName = document.getElementById('recipientName').value;
        const phone = document.getElementById('phone').value;
        const category = document.getElementById('category').value;
        const date = document.getElementById('sendDate').value;
        const content = document.getElementById('content').value;
        const starred = document.getElementById('starred').value;

        const arrow = await this.client.addNewArrow(recipientName, phone, starred, category, content, date, starred);

        document.getElementById("create-arrow-form").reset();
        createButton.innerText = 'Create';
    }

    async getAllMessages() {
        let messagesHtml = "";
        const messages = this.dataStore.get("messages");

        if(messages) {
            for (const message of messages) {
                const existingMessage = document.getElementById(message.messageId);
                if(!existingMessage) {
                    messagesHtml += `            
                    <tr class="card" id="${message.messageId}">
                        <td>${message.recipientName}</td>
                        <td>${message.phone}</td>                     
                        <td>${message.category}</td>
                        <td>${message.sendDate}</td>
                        <td>${message.starred}</td>
                        <td>${message.content}</td>
                        <td hidden>${message.messageId}</td>
                        <button type="button" id="delete-arrow" onclick="this.client.deleteArrow(${message.messageId})">Delete</button>
                        <button type="button">Update</button>
                    </tr>                            
                `;
                }
            }
        }
        document.getElementById("allMessagesToAdd").innerHTML += messagesHtml;
    }

    async onDeleteArrow(messageId) {

        document.getElementById(messageId).innerHTML = "";

        return await this.client.deleteArrow(messageId, this.errorHandler);
    }

 }
/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const examplePage = new ExamplePage();
    await examplePage.mount();
};

window.addEventListener('DOMContentLoaded', main);

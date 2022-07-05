import BaseClass from "../util/baseClass";
import axios from 'axios';

// Client to Call the Music Service

export default class ArrowClient extends BaseClass {

 constructor(props = {}){
        super();
        const methodsToBind = ['clientLoaded', 'addNewArrow', 'updateArrow'];
        this.bindClassMethods(methodsToBind, this);
        this.props = props;
        this.clientLoaded(axios);
    }

    clientLoaded(client) {
        this.client = client;
        if (this.props.hasOwnProperty("onReady")){
            this.props.onReady();
        }
    }

    async addNewArrow(userId, messageID, recipientName, phone, starred, category, content, sendDate, status, errorCallback) {
        try {
            const response = await this.client.post('message', {
            userId: userId,
            recipientName: recipientName,
            phone: phone,
            starred: starred,
            category: category,
            content: content,
            sendDate: sendDate,
            status: status});
            return response.data;
        } catch (error) {
            this.handleError("message", error, errorCallback);
            }
    }
    async updateArrow(userId, messageID, recipientName, phone, starred, category, content, sendDate, status, errorCallback) {
           try {
               const response = await this.client.put('message', {
               userId: userId,
               recipientName: recipientName,
               phone: phone,
               starred: starred,
               category: category,
               content: content,
               sendDate: sendDate,
               status: status});
               return response.data;
           } catch (error) {
               this.handleError("message", error, errorCallback);
               }
       }

  /*   async createConcert(name, date, ticketBasePrice, errorCallback) {
            try {
                const response = await this.client.post(`concerts`, {
                    name: name,
                    date: date,
                    ticketBasePrice: ticketBasePrice
                });
                return response.data;
            } catch (error) {
                this.handleError("createConcert", error, errorCallback);
            }
        }
*/



}
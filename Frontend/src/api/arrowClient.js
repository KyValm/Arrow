import BaseClass from "../util/baseClass";
import axios from 'axios';

// Client to Call the Music Service

export default class ArrowClient extends BaseClass {

 constructor(props = {}){
        super();
        const methodsToBind = ['clientLoaded', 'addNewArrow', 'updateArrow', 'getArrows', 'getArrowById',
         'getArrowsByCategory', 'deleteArrow'];
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

    async addNewArrow(recipientName, phone, starred, category, content, sendDate, errorCallback) {
        try {
            const response = await this.client.post(`message`, {
            recipientName: recipientName,
            phone: phone,
            starred: starred,
            category: category,
            content: content,
            sendDate: sendDate
            });
            return response.data;
        } catch (error) {
            this.handleError("addNewArrow", error, errorCallback);
        }
    }

    async updateArrow(messageId, recipientName, phone, starred, category, content, sendDate, errorCallback) {
            try {
                const response = await this.client.put(`message`, {
                messageId: messageId,
                recipientName: recipientName,
                phone: phone,
                starred: starred,
                category: category,
                content: content,
                sendDate: sendDate
                });
                return response.data;
            } catch (error) {
                this.handleError("updateArrow", error, errorCallback);
            }
        }

     async getArrows(errorCallback) {
             try {
                 const response = await this.client.get(`/message/getAllMessages`);
                 return response.data;
             } catch(error) {
                 this.handleError("getArrows", error, errorCallback);
             }
         }

     async getArrowById(messageId, errorCallback) {
             try {
                 const response = await this.client.get(`/message/${messageId}`);
                 return response.data;
             } catch (error) {
                 this.handleError("getArrowById", error, errorCallback)
             }
         }

     async getArrowsByCategory(option, errorCallback) {
             try {
                 const response = await this.client.get(`/message/getSublist/${option}`);
                 return response.data;
             } catch (error) {
                 this.handleError("getArrowByCategory", error, errorCallback)
             }
         }

     async deleteArrow(messageId, errorCallback) {
             try {
                 const response = await this.client.delete(`/message/delete/${messageId}`,{
                 messageId: messageId
                 });
                 return response.data;
             } catch (error) {
                 this.handleError("deleteArrow", error, errorCallback)
             }
         }

     handleError(method, error, errorCallback) {
             console.error(method + " failed - " + error);
             if (error.response.data.message !== undefined) {
                 console.error(error.response.data.message);
             }
             if (errorCallback) {
                 errorCallback(method + " failed - " + error);
             }
         }

}

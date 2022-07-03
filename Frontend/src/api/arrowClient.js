import BaseClass from "../util/baseClass";
import axios from 'axios';

// Client to Call the Music Service

export default class ArrowCline extends BaseClass {

 constructor(props = {}){
        super();
        const methodsToBind = [];
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
    // TODO: FIX arrow object class to account for random messageID/UserID
    async addNewArrow(, errorCallback) {
        try {
            const response = await this.client.post
        }
    }

     async createConcert(name, date, ticketBasePrice, errorCallback) {
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




}
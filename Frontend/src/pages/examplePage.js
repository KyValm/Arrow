import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import ArrowClient from "../api/ArrowClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class ExamplePage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onClickNewArrow'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('get-by-id-form').addEventListener('submit', this.onGet);
        document.getElementById('create-form').addEventListener('submit', this.onCreate);

        this.client = new ArrowClient();
        this.dataStore.addChangeListener(this.renderExample)
    }

    // Render Methods --------------------------------------------------------------------------------------------------


    // Event Handlers --------------------------------------------------------------------------------------------------

    function onClickNewArrow() {
         document.getElementById("createArrow").style.display = "block";
    }

    async closeForm() {
        document.getElementById("createArrow").style.display = "none";
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

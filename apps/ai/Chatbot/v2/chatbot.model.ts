
export class Chatbot {
    public response?: string;

    public context?: string;

    constructor(response?: string, context?: string){
        this.response = response ? response : null;
        this.context = context ? context : null;
    }
}

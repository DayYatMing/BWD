import { AfterViewChecked, Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import {ChatbotService} from "./chatbot.service";
import { marked } from 'marked';
import {Chatbot} from "./chatbot.model";
import {PMCustomerModel} from "../pm/pmcustomer.model";

class Message {
    text?: string;
    type: MessageType;
}

enum MessageType {
    Bot = 'bot',
    User = 'user',
    Loading = 'loading'
}

@Component({
    selector: 'jhi-chatbot',
    templateUrl: './chatbot.component.html',
    styleUrls: ['./chatbot.component.css']
})
export class ChatbotComponent implements OnInit, AfterViewChecked {

    @ViewChild('messageContainer') private messageContainer: ElementRef;
    @Input() public display: string;

    public form: FormGroup;
    public messages: Array<Message> = [];
    private canSendMessage = true;

    public botResponse: string = "Hello! How can I help you?";

    public conversations: string[] = [];
    public chatbot: Chatbot = { response: '', context: '' };

    constructor(private formBuilder: FormBuilder, private chatbotService: ChatbotService){}

    ngOnInit(): void {
        this.form = this.formBuilder.group({
            message: ['']
        });

        this.initBotMessage();
    }

    ngAfterViewChecked(): void {
        this.scrollToBottom();
    }

    public onClickSendMessage(): void {
        this.chatbot.response = this.form.get('message').value;

        if (this.chatbot.response && this.canSendMessage) {
            const userMessage: Message = {text: this.chatbot.response, type: MessageType.User};
            this.messages.push(userMessage);

            this.form.get('message').setValue('');
            this.form.updateValueAndValidity();

            this.canSendMessage = false;
            const waitMessage: Message = {type: MessageType.Loading};
            this.messages.push(waitMessage);

            this.addToConversations("Previous Question: " + this.chatbot.response);
            this.getMessage();
        }
    }

    private getMessage() {
        this.convertListToString();
        this.chatbotService.postResponse(this.chatbot).subscribe(
            res => this.onSuccess(res),
            res => this.onError(res.message),
        );
    }

    private convertListToString(){
        this.chatbot.response = encodeURIComponent(this.chatbot.response);
        this.chatbot.context = encodeURIComponent("Context: " + this.conversations.join('\n'));
    }

    private getBotMessage(): void {
        this.canSendMessage = false;
        const waitMessage: Message = {type: MessageType.Loading};
        this.messages.push(waitMessage);

        this.messages.pop();
        const botMessage: Message = {text: this.botResponse, type: MessageType.Bot};
        this.messages.push(botMessage);
        this.canSendMessage = true;
    }

    public onClickEnter(event: KeyboardEvent): void {
        event.preventDefault();
        this.onClickSendMessage();
    }

    private scrollToBottom(): void {
        this.messageContainer.nativeElement.scrollTop = this.messageContainer.nativeElement.scrollHeight;
    }

    private initBotMessage(): void {
        this.canSendMessage = false;
        const waitMessage: Message = {type: MessageType.Loading};
        this.messages.push(waitMessage);

        setTimeout(()=>{
            this.messages.pop();
            const botMessage: Message = {text: this.botResponse, type: MessageType.Bot};
            this.messages.push(botMessage);
            this.canSendMessage = true;
        },2000);
    }
    private onSuccess(res) {

        const marked = require('marked');
        let html = "";

        if(res.reply != null){
            if(res.reply != "The requested information is not available in the retrieved data. Please try another query or topic." &&
                res.reply != "The requested information is not found in the retrieved data. Please try another query or topic.") {
                this.addToConversations("Previous Answer: " + res.reply);
            }
            html = marked(res.reply);

            this.botResponse = html;

            this.messages.pop();
            const botMessage: Message = {text: this.botResponse, type: MessageType.Bot};
            this.messages.push(botMessage);
            this.canSendMessage = true;
        }else {
            this.onError(res.error)
        }
    }
    private onError(error) {
        this.botResponse = "##########ERROR##########<br><br>" + error;

        this.messages.pop();
        const botMessage: Message = {text: this.botResponse, type: MessageType.Bot};
        this.messages.push(botMessage);
        this.canSendMessage = true;

        console.error('Error fetching data:', error);
    }

    private addToConversations(message){
        if(this.conversations.length > 10){
            this.conversations.shift();
        }

        this.conversations.push(message);
    }
}

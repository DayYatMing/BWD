import { AfterViewChecked, Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import {ChatbotService} from "./chatbot.service";

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
        const message = this.form.get('message').value;

        if (message && this.canSendMessage) {
            const userMessage: Message = {text: message, type: MessageType.User};
            this.messages.push(userMessage);

            this.form.get('message').setValue('');
            this.form.updateValueAndValidity();

            this.canSendMessage = false;
            const waitMessage: Message = {type: MessageType.Loading};
            this.messages.push(waitMessage);

            this.getMessage(message);
        }
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

    private getMessage(message: string) {
        this.chatbotService.getResponse(message).subscribe(
            res => this.onSuccess(res),
            res => this.onError(res),
        );
    }
    private onSuccess(res) {
        this.botResponse = res.response;

        this.messages.pop();
        const botMessage: Message = {text: this.botResponse, type: MessageType.Bot};
        this.messages.push(botMessage);
        this.canSendMessage = true;
    }
    private onError(error) {
        this.botResponse = "Sorry, I do not understand that. Could you please rephrase? Thank you.";
        console.error('Error fetching data:', error);
    }
}

import { Component, OnInit, ViewChild } from '@angular/core';
import { ChatbotService } from './chatbot.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ChatUIModule, ChatUIComponent, MessageToolbarSettingsModel } from '@syncfusion/ej2-angular-interactive-chat';
import { UserModel } from '@syncfusion/ej2-angular-interactive-chat';
import SharedModule from '../../shared/shared.module';
import { marked } from 'marked';
import {Chatbot} from "./chatbot.model";

@Component({
  selector: 'jhi-chatbot',
  templateUrl: './chatbot.component.html',
  styleUrl: './chatbot.component.scss',
  imports: [FormsModule, ReactiveFormsModule, ChatUIModule, SharedModule],
  providers: [ChatbotService],
})
export class ChatbotComponent implements OnInit {
  @ViewChild('chatUIComponent')
  public chatUIComponent!: ChatUIComponent;
  public currentUserModel: UserModel = { user: 'User', id: 'user1' };
  public botUserModel: UserModel = {
    user: 'Amy',
    id: 'user2',
    cssClass: 'custom-avatar',
  };

  public conversations: string[] = [];
  public chatbot: Chatbot = { response: '', context: '', method: 'default' };

  public messageToolbarSettings: MessageToolbarSettingsModel = {
    width:'100%',
    items: [
      { type: 'Button', iconCss: 'e-icons e-chat-copy', tooltip: 'Copy' },
      { type: 'Button', iconCss: 'e-icons e-chat-reply', tooltip: 'Reply' },
      // { type: 'Button', iconCss: 'e-icons e-chat-pin', tooltip: 'Pin' },
      { type: 'Button', iconCss: 'e-icons e-chat-trash', tooltip: 'Delete' },
    ],
  };

  public replyMessage = (message: any) => {
    let index = 0;
    let currentText = '';

    const botMsg = {
      author: this.botUserModel,
      text: '',
    };
    this.chatUIComponent.addMessage(botMsg);

    const typingInterval = setInterval(() => {
      currentText += message[index];
      botMsg.text = currentText;

      const lastMsgIndex = this.chatUIComponent.messages.length - 1;
      this.chatUIComponent.messages[lastMsgIndex].text = currentText;
      this.chatUIComponent.scrollToBottom();

      index++;
      if (index >= message.length) {
        clearInterval(typingInterval);
        this.chatUIComponent.typingUsers = [];
      }
    }, 10);

    this.chatUIComponent.typingUsers = [this.botUserModel];
  };

  public onMessageSend = (event: any) => {
    this.chatUIComponent.typingUsers = [this.botUserModel];

    if (event && event.message && event.message.text) {
      let tempMsg: any = event.message.text;
      this.chatbot.response = event.message.text;
      this.chatbot.method = "doc_multiple";
      this.convertListToString();

      this.chatbotService.getResponse(this.chatbot).subscribe(
        (res: HttpResponse<any>) => this.onSuccess(res),
        (res: HttpErrorResponse) => this.onError(res.message),
      );

      this.addToConversations("User Previous Question: " + tempMsg);
    }
  };

  private onSuccess(res: any) {
    if(res.reply != null){
      this.addToConversations("Bot Previous Answer: " + res.reply);
      const parsed: string = marked.parse(res.reply) as string;
      this.replyMessage(parsed.replace(/^<p>/, '').replace(/<\/p>\s*$/, ''));
    }else{
      this.onError(res.error);
    }
  }

  private onError(error: any) {
    this.addToConversations("Bot Previous Answer: " + error);
    const parsed: string = marked.parse("<span style=\"color: red\">########## ERROR ##########</span><br><br>" + error) as string;
    this.replyMessage(parsed.replace(/^<p>/, '').replace(/<\/p>\s*$/, ''));
  }

  private convertListToString(){
    let str: any = this.chatbot.response;
    this.chatbot.response = encodeURIComponent(str);
    this.chatbot.context = encodeURIComponent(this.conversations.join('\n'));
  }

  private addToConversations(message: any){
    if(this.conversations.length > 30){
      this.conversations.shift();
    }

    this.conversations.push(message);
  }

  constructor(private chatbotService: ChatbotService) {}

  ngOnInit(): void {
  }

}

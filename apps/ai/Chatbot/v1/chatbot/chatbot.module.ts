import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import {PanelModule, TabViewModule} from 'primeng/primeng';
import {CommonModule} from "@angular/common";
import {NrmsSharedModule} from "../../shared";
import {HOME_ROUTE} from "./chatbot.route";
import {ChatbotComponent} from "./chatbot.component";
import {ChatbotService} from "./chatbot.service";
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

import {ReactiveFormsModule} from "@angular/forms";
import {MatCardModule} from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonToggleModule} from '@angular/material/button-toggle';

@NgModule({
    imports: [
        CommonModule,
        NrmsSharedModule,
        PanelModule,
        TabViewModule,
        RouterModule.forChild([HOME_ROUTE]),
        BrowserModule,
        BrowserAnimationsModule,

        ReactiveFormsModule,
        MatCardModule,
        MatInputModule,
        MatIconModule,
        MatButtonModule

    ],
    declarations: [
        ChatbotComponent,
    ],
    entryComponents: [],
    providers: [
        ChatbotService
    ],
    exports: [
        ChatbotComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChatbotModule {}

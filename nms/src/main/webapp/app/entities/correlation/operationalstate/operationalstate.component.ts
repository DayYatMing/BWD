import { Component, OnInit, ViewChild, AfterViewInit, ViewEncapsulation } from '@angular/core';
import { ContextMenuItem } from '@syncfusion/ej2-angular-grids';
import { ClickEventArgs,MenuEventArgs, ExpandEventArgs} from '@syncfusion/ej2-navigations'
import { GridComponent, GroupSettingsModel, } from '@syncfusion/ej2-angular-grids';
import { DialogComponent } from "@syncfusion/ej2-angular-popups";
import { OperationalstateService } from "./operationalstate.service";
import { OperationalStateData } from "./operationalstate.model";
import { HttpHeaders, HttpResponse, HttpErrorResponse} from "@angular/common/http";
import { CommonModule } from '@angular/common';
import { AlertService } from 'app/core/util/alert.service';
import { Timeline} from "../../networkconstruction/timeline";
import { Article, Ticket, TicketRequest, TicketResponse} from "./ticket.model";
import { SwitchComponent} from "@syncfusion/ej2-angular-buttons";
import { HtmlEditorService, RichTextEditorComponent,RichTextEditorModule,RichTextEditorAllModule} from '@syncfusion/ej2-angular-richtexteditor';
import { DomSanitizer} from "@angular/platform-browser";
import { AccordionModule } from '@syncfusion/ej2-angular-navigations';
import { FormsModule } from '@angular/forms';
import { DropDownListModule } from '@syncfusion/ej2-angular-dropdowns';
import { ButtonPropsModel} from "@syncfusion/ej2-popups";
import { DialogModule } from '@syncfusion/ej2-angular-popups';
import { AccordionComponent } from '@syncfusion/ej2-angular-navigations';

import {
  Grid,
  Reorder,
  ColumnChooser,
  GridModule,
  ToolbarService,
  ExcelExportService,
  PdfExportService,PageService,
  SortService,FilterService, ResizeService, ColumnMenuService, ContextMenuService, ContextMenuItemModel
} from '@syncfusion/ej2-angular-grids';

Grid.Inject(Reorder, ColumnChooser);

@Component(
  {
    selector: 'jhi-operationalstate',
    styleUrls: ['operationalstate.css'],
    templateUrl: './operationalstate.component.html',
    imports: [GridModule, CommonModule,RichTextEditorAllModule, DropDownListModule, AccordionModule, DialogModule, FormsModule],

    providers: [
      ToolbarService,
      ExcelExportService,PageService,
      PdfExportService,HtmlEditorService,RichTextEditorModule, SortService, FilterService, ResizeService, ResizeService,
      ColumnMenuService, ContextMenuService
    ]
  }
)

export class OperationalstateComponent implements OnInit {
  infoMsg: string = "";
  progressLoader: boolean = true;
  mcsServiceUrl: string = "https://ncs-ausy.local.bw-digital.com/ui/#/transport-services?keyword"
  grafanaUrl = "https://stats-new.local.bw-digital.com/d/rWNnA1bne/customer-service-12x-factors-fast-ref?orgId=1&from=now-3h&to=now&timezone=utc&var-pm_source=$__all&fullscreen=true&kiosk=true&theme=light&var-AvgMax=$__all&refresh=15m";

  toolbar: any;
  filterSettings: any;
  columnMenuItems: Object[] = ['AutoFit', 'AutoFitAll', 'SortAscending', 'SortDescending', 'Group', 'Ungroup', 'Filter'];
  contextMenuItems: ContextMenuItem[] = ['AutoFit', 'AutoFitAll', 'SortAscending', 'SortDescending',
    'Copy', 'ExcelExport', 'CsvExport', 'FirstPage', 'PrevPage', 'LastPage', 'NextPage'];

  sortOptions: object = {
    columns: [{field: 'operationState', direction: 'Ascending'},
      {field: 'downSince', direction: 'Ascending'},
      {field: 'serviceName', direction: 'Ascending'}]
  };
  tools: object = {
    type: 'MultiRow',
    items: ['Bold', 'Italic', 'Underline', 'StrikeThrough',
      'FontName', 'FontSize', 'FontColor', 'BackgroundColor',
      'LowerCase', 'UpperCase', '|',
      'Formats', 'Alignments', 'OrderedList', 'UnorderedList',
      'Outdent', 'Indent', '|',
      'CreateLink', 'Image', '|', 'ClearFormat', 'Print',
      'SourceCode', 'FullScreen', '|', 'Undo', 'Redo']
  };
  data!: OperationalStateData[] | null;
  groupOptions!: GroupSettingsModel;
  selectionSettings !: Object;
  dReady: boolean = false;
  services: OperationalStateData[] = [];
  ticketReponse!:TicketResponse[];
  ticket:Ticket = {};
  selectedServices: OperationalStateData[] = [];
  ticketrequest:TicketRequest = {};
  article:Article = {};
  ticketypemodel: any;
  titlemodel: any;
  ticketType = "";
  dialogHeaderTicket = 'Ticket Status';
  resString = '';

  public confirmWidth: string = '500px';
  public target: string = '.control-section-status';
  public animationSettings1: Object = { effect: 'None' };
  public secondDlgHeader = 'Ticket Details:';
  public secondDialogCloseIcon: Boolean = true;
  public secondDialogWidth = '600px';
  public animationSettings2: Object = { effect: 'None' };
  public enablelinks: Boolean = false;
  public hidden: Boolean = false;
  public alertHeader: string = 'Copy with Header';
  public alertWidth: string = '300px';
  public targetservice: string = '.control-section-status';
  public targetticket: string = '.control-section-status';
  public alertContent: string = 'Atleast one row should be selected to copy with header';
  public showCloseIcon: Boolean = false;
  public alertDlgBtnClick = () => {
    this.alertDialog.hide();
  }
  public fields: Object = { text: 'text', value: 'Value' };

  public resMap = new Map();

  public visible1: Boolean = false;
  public visible2: Boolean = false;
  public pageSettings: Object = { pageCount: 5 };
  public visible3: Boolean = false;
  public isModal: Boolean = true;
  public dialogCloseIcon: Boolean = true;
  public serviceButtonClick = (): void => {
    this.selectedServices = [];
    this.titlemodel = '';
    var indexes : number[] = this.servicegridInstance.getSelectedRowIndexes();
    if(indexes.length === 0){
      alert("Please select any service")
      return;
    }else{
      indexes.forEach((index) => {
          this.selectedServices.push(this.services[index]);
        }
      )
    }
    this.ticketypemodel = null;
    this.defaultRTEInstance.value = '';
    this.titlemodel = '';
    this.serviceDialog.hide();
    this.ticketDialog.show(true);
  }


  public serviceButtonClickClose  = (): void => {
    this.serviceDialog.element.style.display = 'none';
  }

  public selectionOptionsservices: Object = { type: 'Multiple', enableSimpleMultiRowSelection: true  , persistSelection: true};
  public ticketDlgButtons:  ButtonPropsModel[] = [{ click: this.serviceButtonClick.bind(this), buttonModel: { content: 'Next', isPrimary: true } }, { click: this.serviceButtonClickClose.bind(this), buttonModel: { content: 'Cancel', cssClass: 'e-flat' } }];

  public alertDlgButtons: Object[] = [{ click: this.alertDlgBtnClick.bind(this), buttonModel: { content: 'OK', isPrimary: true } }];
  public animationSettings: Object = { effect: 'Zoom' };
  sortComparer = (reference: string, comparer: string) => {
    if (reference === null || comparer === null)
      return 0;

    if (reference < comparer) {
      return -1;
    }
    if (reference > comparer) {
      return 1;
    }
    return 0;
  }


  ticketstatustoolbarbarClick(args: ClickEventArgs): void {
    switch (args.item.text) {
      case 'Excel Export':
        this.ticketreponsegridInstance.excelExport();
        break;

    }
  }
  public confirmHeader: string = 'Confirmation:';
  public showServiceDialog  = (): void => {
    this.ticketDialog.hide();
    this.serviceDialog.show(true);
  }
  ticketypedata: string[] = [
    'One Ticket Per Service', 'Incident' ,  'Hazardous Condition','Maintenance_service impacting' , 'Maintenance_non-service impacting' ,
    'Internal_Only_Incident' , 'Internal_Only_Maintenance'
  ];
  public tickettoolbar: Object[] = ['ExcelExport', 'Search' ];
  public dialogWidth1 = '300px';
  @ViewChild('grid') gridInstance!: GridComponent;
  @ViewChild('alertDialog') alertDialog!: DialogComponent;
  @ViewChild('operationalStateserviceDialog') serviceDialog!: DialogComponent;
  @ViewChild('eventsticketstatusdialog') ticketStatusDialog!: DialogComponent;
  @ViewChild('eventsticketDialog') ticketDialog!: DialogComponent;
  @ViewChild('switch') switch!: SwitchComponent;
  @ViewChild('rte') defaultRTEInstance!: RichTextEditorComponent;
  @ViewChild('servicegrid')servicegridInstance!: GridComponent ;
  @ViewChild('grafanastatusdialog') grafanastatusdialog!: DialogComponent;
  @ViewChild('confirmDialog') confirmDialog!: DialogComponent;
  @ViewChild('ticketreponsegrid') ticketreponsegridInstance! : GridComponent ;
  @ViewChild('accordion')  acrdn!: AccordionComponent;

  constructor(private operationalstateservice: OperationalstateService,
              private  alertService :AlertService,private _sanitizer: DomSanitizer) {
  }

  ngOnInit(): void {
    this.data = [];
    this.filterSettings = { type: "CheckBox" };
    this.groupOptions = { showGroupedColumn: false };
    this.selectionSettings = { persistSelection: true, type: 'Multiple', checkboxOnly: true };

    this.toolbar = [
      'ExcelExport', 'CsvExport', 'Print',
      { text: 'Create Tickets', id: 'createtickets' },
      { text: 'Tickets Status', id: 'ticketsstatus' },
      'Search'
    ];

    this.operationalstateservice.query().subscribe(
      (res: HttpResponse<OperationalStateData[]>) => this.onSuccess(res.body, res.headers),
      (err: HttpErrorResponse) => this.onError(err)
    );
  }



  public ticketClickClose = (): void => {
    this.confirmDialog.hide();
    this.ticketDialog.hide();
  }

  public confirmAlertDlgCloseClick = (): void => {
    this.confirmDialog.hide();
  }



  public confirmAlertDlgBtnClick = (): void => {
    this.resString = '';
    this.resMap = new Map();
    var totalCount = 0;

    for(var data of this.selectedServices) {
      if (data?.serviceName?.includes('::')) {
        const serviceName = data.serviceName;
        const separatorIndex = serviceName.indexOf('::');

        const customerservice = serviceName.substr(separatorIndex + 2);
        const slashIndex = customerservice.indexOf('/');

        if (slashIndex !== -1) {
          data.serviceName = serviceName.substr(0, separatorIndex) + "::" + customerservice.substr(slashIndex + 1);
          data.customerName = customerservice.substr(0, slashIndex);
        }
      }
      var customerKey = data.customerName;

      if( this.ticketType === 'Incident'
        || this.ticketType === 'Maintenance_service impacting' || this.ticketType === 'Internal_Only_Incident'
        || this.ticketType === 'Internal_Only_Maintenance') {
        totalCount++;

        if(! this.resMap.has(customerKey+" INTERNAL") )
          this.resMap.set(customerKey+" INTERNAL", 1);
        else
          this.resMap.set(customerKey+" INTERNAL" , this.resMap.get(customerKey+" INTERNAL") +1 );
      }else if(this.ticketType === 'One Ticket Per Service'){
        totalCount++;
        if(! this.resMap.has(customerKey+" EXTERNAL") )
          this.resMap.set(customerKey+" EXTERNAL", 1);
        else
          this.resMap.set(customerKey+" EXTERNAL" , this.resMap.get(customerKey+" EXTERNAL") +1 );
      }
    }
    if(this.ticketType === 'Incident' || this.ticketType === 'Hazardous Condition'
      || this.ticketType === 'Maintenance_service impacting'   || this.ticketType === 'Maintenance_non-service impacting' )
      totalCount = this.createSingleBulkTicketCount(totalCount);

    this.resMap.forEach((value: number, key: string) => {
      this.resString += "FOR CUSTOMER : " + key + " TICKET COUNT = " + value + ".<br/>";
    });
    this.resString += "<br/><br/>IN TOTAL "+ totalCount + " TICKETS WILL BE CREATED."
    this.confirmDialog.show();
  }

  public secondDlgButtons:  ButtonPropsModel[] = [{ click: this.showServiceDialog.bind(this), buttonModel: { content: 'Back', isPrimary: true } }, { click: this.confirmAlertDlgBtnClick.bind(this), buttonModel: { content: 'Ok',  cssClass: 'e-flat'  } } , { click: this.ticketClickClose.bind(this), buttonModel: { content: 'Cancel', cssClass: 'e-flat' } }];

  public createSingleBulkTicketCount(ticketslen :any){
    for(const key in this.bulkCustomer) {
      this.bulkCustomer[key]
      if( !this.resMap.has(key+" EXTERNAL") )
        this.resMap.set(key+" EXTERNAL", 1);
      else
        this.resMap.set(key+" EXTERNAL" , this.resMap.get(key+" EXTERNAL") +1 );
      ticketslen++
    }

    return ticketslen;
  }

  public ticketClick = (): void => {
    this.bulkCustomer ={};
    this.selectedServices.forEach(
      data => {
        let customerKey: string | undefined = data?.customerName;
        let customershrtName: string | undefined = '';
        if( data?.serviceName?.includes('::')){
          customershrtName = data.serviceName.substr( data.serviceName.indexOf("::")+2,3)
        }else
          customershrtName = data?.serviceName?.substr(0,3);

        if (customerKey) {
          if (!this.bulkCustomer[customerKey]) {
            this.bulkCustomer[customerKey] = [];
          }

          this.bulkCustomer[customerKey].push({
            serviceName: data?.serviceName,
            customershrtName: customershrtName
          });
        }

        if(this.ticketType === 'Incident'
          || this.ticketType === 'Maintenance_service impacting' || this.ticketType === 'Internal_Only_Incident'
          || this.ticketType === 'Internal_Only_Maintenance') {
          this.ticket.Bulk = "BULK";
          this.ticket.Service = data.serviceName;
          if( this.ticketType === 'Maintenance_service impacting'
            || this.ticketType === 'Internal_Only_Maintenance') {
            this.ticket.Type = 'Maintainence';
            this.ticket.Title =  "Internal : " +this.titlemodel;
          }
          else if(this.ticketType === 'Incident' || this.ticketType === 'Internal_Only_Incident'){
            this.ticket.Type = 'Incident';
            this.ticket.Title = "Internal : " + this.titlemodel;
          }
          this.ticket.CustomerID = customershrtName;
          this.article.Body = this.defaultRTEInstance.getHtml();
          if(data.customerName)
            this.article.Body = this.article.Body.replace("&lt;BulkTicket-Customer&gt;",data.customerName)
          else
            this.article.Body = this.article.Body.replace("&lt;BulkTicket-Customer&gt;",' ')
          if (this.article?.Body) {

            const serviceName = data?.serviceName ?? 'Unknown Service';

            this.article.Body = this.article.Body.replace("&lt;BulkTicket-ServiceID&gt;", serviceName);
          }          this.article.Subject = this.titlemodel;
          this.ticketrequest.Ticket = this.ticket;
          this.ticketrequest.Article = this.article;
          this.operationalstateservice.createTicket(this.ticketrequest).subscribe(
            (res: HttpResponse<any>) => this.onTicketSuccess(res.body, res.headers),
            (err: HttpErrorResponse) => this.onError(err)
          );

        }else if(this.ticketType === 'One Ticket Per Service'){
          this.ticket.Bulk = "SINGLE";
          this.ticket.Service = data.serviceName;
          this.ticket.Type = 'Maintainence';
          this.ticket.Title = this.titlemodel;
          this.ticket.CustomerID = customershrtName;
          this.article.Body = this.defaultRTEInstance.getHtml();
          if(data.customerName)
            this.article.Body = this.article.Body.replace("&lt;BulkTicket-Customer&gt;",data.customerName)
          else
            this.article.Body = this.article.Body.replace("&lt;BulkTicket-Customer&gt;",' ')
          if (this.article?.Body) {

            const serviceName = data?.serviceName ?? 'Unknown Service';

            this.article.Body = this.article.Body.replace("&lt;BulkTicket-ServiceID&gt;", serviceName);
          }
          this.article.Subject = this.titlemodel;
          this.ticketrequest.Ticket = this.ticket;
          this.ticketrequest.Article = this.article;
          this.operationalstateservice.createTicket(this.ticketrequest).subscribe(
            (res: HttpResponse<any>) => this.onTicketSuccess(res.body, res.headers),
            (err: HttpErrorResponse) => this.onError(err)
          );
        }
      });
    if(this.ticketType === 'Incident' || this.ticketType === 'Hazardous Condition'
      || this.ticketType === 'Maintenance_service impacting'   || this.ticketType === 'Maintenance_non-service impacting' )
      this.createSingleBulkTicket();

    this.confirmDialog.hide();
    this.ticketDialog.hide();
    alert("Requests sent sucessfully! \n\nClick on Tickets Status Button to view details");

  }

  public confirmDlgButtons: ButtonPropsModel[] =
    [{ click: this.ticketClick.bind(this), buttonModel: { content: 'Confirm', isPrimary: true } },
      { click: this.confirmAlertDlgCloseClick.bind(this), buttonModel: { content: 'Cancel' } }];


  public ticketcloseClick = (): void => {
    this.ticketStatusDialog.hide();
  }

  public ticketReponseDlgButtons:  ButtonPropsModel[] = [{ click: this.ticketcloseClick.bind(this), buttonModel: { content: 'Close', isPrimary: true }  }];
  private initializeData()
  {
    this.operationalstateservice.query(
    ).subscribe(
      (res: HttpResponse<OperationalStateData[]>) => this.onSuccess(res.body, res.headers)
    );
    setTimeout(this.initializeData.bind(this), (1000 * 60) );
  }

  contextMenuClick(args: MenuEventArgs): void {
  }


  toolbarClick(args: ClickEventArgs): void {
    switch (args.item.text) {

      case 'Excel Export':
        this.gridInstance.excelExport();
        break;
      case 'CSV Export':
        this.gridInstance.csvExport();
        break;
      case 'Copy With Header':
        if (this.gridInstance.getSelectedRecords().length > 0) {
          this.gridInstance.copy(true);
        } else {
          this.alertDialog.show();
        }
        break;
      case 'Copy':
        if (this.gridInstance.getSelectedRecords().length > 0) {
          this.gridInstance.copy(false);
        } else {
          this.alertDialog.show();
        }
        break;
    }

  }

  private onSuccess(data: OperationalStateData[] | null, headers: HttpHeaders) {
    this.data = data;
    this.progressLoader = false;
  }
  private onError(error: any) {
    this.infoMsg = error.message;
  }


  changetemplate(args:any) {
    this.ticketType = args.itemData.value
    if (args.itemData.value === 'One Ticket Per Service') {
      this.titlemodel = "Hawaiki Submarine Cable: Maintenance Notification";
      this.defaultRTEInstance.value = "<div style=\"display: block;\"><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\">Dear &lt;BulkTicket-Customer&gt; NOC<span>&nbsp;</span>,</span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\"><br></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\">Hawaiki NOC has identified a<strong> Service Impacting</strong> event on your service which has been notified to the Hawaiki Engineering team and Duty Incident Manager.</span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\">Service ID: &lt;BulkTicket-ServiceID&gt;</span></p><p><br></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><strong>Service Impacting :</strong><br>Date/Time Start: </span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">﻿﻿</span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">YYYY:MM:DD:HH:MM:SS UTC</span><span lang=\"EN-NZ\" style=\"color:black;\"><br>Date/Time End:&nbsp; &nbsp;<span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: left; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\">YYYY:MM:DD:HH:MM:SS UTC</span></span></p><p><br></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\"><br>While this is being investigated, please review, and advise if this event is due to any maintenance works or faults on your side. Please standby for the next update and contact the Hawaiki NOC for further details.</span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\"><br></span></p><p style=\" margin: 0cm; color: rgb(51, 51, 51); font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); font-size: 11pt; font-family: Calibri, sans-serif; line-height: normal;\"><i><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\">-----</span></i><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><b style=\" font-weight: 700;\"><i><span lang=\"EN-NZ\" style=\" color: rgb(52, 152, 219);\">Best Regards,</span></i></b><b style=\" font-weight: 700;\"><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: rgb(52, 152, 219);\"><br></span></b><b style=\" font-weight: 700;\"><i><span lang=\"EN-NZ\" style=\" color: rgb(52, 152, 219);\">HAWAIKI NOC</span></i></b><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br><br></span><span lang=\"EN-NZ\" style=\" color: black;\">In case of emergency, contact us on&nbsp;any of the following:</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- New-Zealand Toll-free : +64 800 002 600</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- Australia Toll-free : +61 1800 319 388</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- United States Toll-free : +1 8888 313 339</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- International Calling Number: +64 9 887 3243</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- Email :&nbsp;</span><span lang=\"EN-NZ\"><a href=\"mailto:support@hawaikicable.co.nz\" target=\"_blank\" style=\" color: rgb(46, 46, 241); text-decoration: none; cursor: pointer; background-color: transparent; font-weight: 700;\"><span style=\" color: blue;\">support@hawaikicable.co.nz</span></a></span></p><p style=\" margin: 0cm 0cm 0px; color: rgb(51, 51, 51); font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); font-size: 11pt; font-family: Calibri, sans-serif; line-height: normal;\"><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Arial, sans-serif; color: rgb(31, 73, 125);\">HAWAIKI CONFIDENTIALITY NOTICE:</span><br><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Arial, sans-serif; color: rgb(31, 73, 125);\">This email and its contents are confidential and may also be privileged.&nbsp;If you have received it in error, you may not read, use, copy or disclose this email or its attachments. If you are the intended recipient,&nbsp;you must not disclose or circulate this email or its content other than in accordance with any confidentiality arrangements between us.</span></p></div>";
    } else if (args.itemData.value === 'Incident') {
      this.titlemodel = "Hawaiki Submarine Cable: Network Incident Notification";
      this.defaultRTEInstance.value = "<div style=\"display: block;\"><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\">Dear &lt;BulkTicket-Customer&gt; NOC<span>&nbsp;</span>,</span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\"><br></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\">Hawaiki NOC has identified a<strong> Service Impacting</strong> event on your service which has been notified to the Hawaiki Engineering team and Duty Incident Manager.</span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\">Service ID: &lt;BulkTicket-ServiceID&gt;</span></p><p><br></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><strong>Service Impacting :</strong><br>Date/Time Start: </span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">﻿﻿</span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">YYYY:MM:DD:HH:MM:SS UTC</span><span lang=\"EN-NZ\" style=\"color:black;\"><br>Date/Time End:&nbsp; &nbsp;<span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: left; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\">YYYY:MM:DD:HH:MM:SS UTC</span></span></p><p><br></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\"><br>While this is being investigated, please review, and advise if this event is due to any maintenance works or faults on your side. Please standby for the next update and contact the Hawaiki NOC for further details.</span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\"><br></span></p><p style=\" margin: 0cm; color: rgb(51, 51, 51); font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); font-size: 11pt; font-family: Calibri, sans-serif; line-height: normal;\"><i><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\">-----</span></i><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><b style=\" font-weight: 700;\"><i><span lang=\"EN-NZ\" style=\" color: rgb(52, 152, 219);\">Best Regards,</span></i></b><b style=\" font-weight: 700;\"><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: rgb(52, 152, 219);\"><br></span></b><b style=\" font-weight: 700;\"><i><span lang=\"EN-NZ\" style=\" color: rgb(52, 152, 219);\">HAWAIKI NOC</span></i></b><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br><br></span><span lang=\"EN-NZ\" style=\" color: black;\">In case of emergency, contact us on&nbsp;any of the following:</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- New-Zealand Toll-free : +64 800 002 600</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- Australia Toll-free : +61 1800 319 388</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- United States Toll-free : +1 8888 313 339</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- International Calling Number: +64 9 887 3243</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- Email :&nbsp;</span><span lang=\"EN-NZ\"><a href=\"mailto:support@hawaikicable.co.nz\" target=\"_blank\" style=\" color: rgb(46, 46, 241); text-decoration: none; cursor: pointer; background-color: transparent; font-weight: 700;\"><span style=\" color: blue;\">support@hawaikicable.co.nz</span></a></span></p><p style=\" margin: 0cm 0cm 0px; color: rgb(51, 51, 51); font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); font-size: 11pt; font-family: Calibri, sans-serif; line-height: normal;\"><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Arial, sans-serif; color: rgb(31, 73, 125);\">HAWAIKI CONFIDENTIALITY NOTICE:</span><br><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Arial, sans-serif; color: rgb(31, 73, 125);\">This email and its contents are confidential and may also be privileged.&nbsp;If you have received it in error, you may not read, use, copy or disclose this email or its attachments. If you are the intended recipient,&nbsp;you must not disclose or circulate this email or its content other than in accordance with any confidentiality arrangements between us.</span></p></div>";
    } else if (args.itemData.value === 'Hazardous Condition') {
      this.titlemodel = "Hawaiki Submarine Cable: Hazardous Condition Notification";
      this.defaultRTEInstance.value = "<div style=\"display: block;\"><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\">Dear &lt;BulkTicket-Customer&gt; NOC<span>&nbsp;</span>,</span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\"><br></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\">Please be advised of maintenance works&nbsp;<span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">(work description goes here</span></span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">﻿﻿</span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">).&nbsp;</span><span style=\"color: black; font-size: 11pt; text-align: inherit;\">This work<strong> is not expected </strong>to impact your service(s) identified below.&nbsp;&nbsp;</span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><strong>Service ID</strong><strong>:</strong> &lt;BulkTicket-ServiceID&gt;</span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><span style=\"font-size:11.0pt;line-height:107%;font-family:&quot;Calibri&quot;,sans-serif;color:black;background:white;\"><strong>Reason for Notification</strong>: </span><span style=\"font-size:11.0pt;line-height:107%;font-family:&quot;Calibri&quot;,sans-serif;color:red;background:white;\">xxxxx</span><span style=\"font-size:11.0pt;line-height:107%;font-family:&quot;Calibri&quot;,sans-serif;color:black;background:white;\"><br><br></span></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><strong><b><span style=\"font-size:11.0pt;line-height:107%;font-family:&quot;Calibri&quot;,sans-serif;color:black;background:white;\">Maintenance Window</span></b>:</strong><br>Date/Time Start: </span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">﻿﻿</span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">YYYY:MM:DD:HH:MM:SS UTC</span><span lang=\"EN-NZ\" style=\"color:black;\"><br>Date/Time End:&nbsp; &nbsp;<span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: left; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\">YYYY:MM:DD:HH:MM:SS UTC</span></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: left; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\"><br></span></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><span style=\"color: rgb(0, 0, 0); text-decoration: inherit;\"><strong>Location of&nbsp;</strong><strong style=\" font-weight: 700; color: rgb(0, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255);\"><b style=\" font-weight: 700;\"><span style=\" font-size: 11pt; line-height: 15.6933px; font-family: Calibri, sans-serif; color: black; background: white;\">Maintenance:&nbsp;<span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\">xxxxx</span></span></b></strong></span></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><span style=\"color: rgb(0, 0, 0); text-decoration: inherit;\"><strong style=\" font-weight: 700; color: rgb(0, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255);\"><b style=\" font-weight: 700;\"><span style=\" font-size: 11pt; line-height: 15.6933px; font-family: Calibri, sans-serif; color: black; background: white;\"><span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\"><br></span></span></b></strong></span></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span style=\"color:black;background:white;\">Hawaiki NOC will monitor the progress of works throughout the maintenance window, keep you informed of developments as they occur and advise when the activity is complete.<br><br>Please contact the HAWAIKI SUBMARINE CABLE Network Operations Centre (NOC) with any questions of concerns.</span></p><p style=\" margin: 0cm; color: rgb(51, 51, 51); font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); font-size: 11pt; font-family: Calibri, sans-serif; line-height: normal;\"><i><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\">-----</span></i><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><b style=\" font-weight: 700;\"><i><span lang=\"EN-NZ\" style=\" color: rgb(52, 152, 219);\">Best Regards,</span></i></b><b style=\" font-weight: 700;\"><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: rgb(52, 152, 219);\"><br></span></b><b style=\" font-weight: 700;\"><i><span lang=\"EN-NZ\" style=\" color: rgb(52, 152, 219);\">HAWAIKI NOC</span></i></b><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br><br></span><span lang=\"EN-NZ\" style=\" color: black;\">In case of emergency, contact us on&nbsp;any of the following:</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- New-Zealand Toll-free : +64 800 002 600</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- Australia Toll-free : +61 1800 319 388</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- United States Toll-free : +1 8888 313 339</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- International Calling Number: +64 9 887 3243</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- Email :&nbsp;</span><span lang=\"EN-NZ\"><a href=\"mailto:support@hawaikicable.co.nz\" target=\"_blank\" style=\" color: rgb(46, 46, 241); text-decoration: none; cursor: pointer; background-color: transparent; font-weight: 700;\"><span style=\" color: blue;\">support@hawaikicable.co.nz</span></a></span></p><p style=\" margin: 0cm 0cm 0px; color: rgb(51, 51, 51); font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); font-size: 11pt; font-family: Calibri, sans-serif; line-height: normal;\"><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Arial, sans-serif; color: rgb(31, 73, 125);\">HAWAIKI CONFIDENTIALITY NOTICE:</span><br><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Arial, sans-serif; color: rgb(31, 73, 125);\">This email and its contents are confidential and may also be privileged.&nbsp;If you have received it in error, you may not read, use, copy or disclose this email or its attachments. If you are the intended recipient,&nbsp;you must not disclose or circulate this email or its content other than in accordance with any confidentiality arrangements between us.</span></p></div>"
    }  else if (args.itemData.value === 'Maintenance_non-service impacting') {
      this.titlemodel = "Hawaiki Submarine Cable: Non-Service impacting Maintenance Notification";
      this.defaultRTEInstance.value = "<div style=\"display: block;\"><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\">Dear &lt;BulkTicket-Customer&gt; NOC<span>&nbsp;</span>,</span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\"><br></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\">Please be advised of maintenance works&nbsp;<span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">(work description goes here</span></span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">﻿﻿</span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">).&nbsp;</span><span style=\"color: black; font-size: 11pt; text-align: inherit;\">This work<strong> is not expected </strong>to impact your service(s) identified below.&nbsp;&nbsp;</span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><strong>Service ID</strong><strong>:</strong> &lt;BulkTicket-ServiceID&gt;</span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><span style=\"font-size:11.0pt;line-height:107%;font-family:&quot;Calibri&quot;,sans-serif;color:black;background:white;\"><strong>Reason for Notification</strong>: </span><span style=\"font-size:11.0pt;line-height:107%;font-family:&quot;Calibri&quot;,sans-serif;color:red;background:white;\">xxxxx</span><span style=\"font-size:11.0pt;line-height:107%;font-family:&quot;Calibri&quot;,sans-serif;color:black;background:white;\"><br><br></span></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><strong><b><span style=\"font-size:11.0pt;line-height:107%;font-family:&quot;Calibri&quot;,sans-serif;color:black;background:white;\">Maintenance Window</span></b>:</strong><br>Date/Time Start: </span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">﻿﻿</span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">YYYY:MM:DD:HH:MM:SS UTC</span><span lang=\"EN-NZ\" style=\"color:black;\"><br>Date/Time End:&nbsp; &nbsp;<span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: left; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\">YYYY:MM:DD:HH:MM:SS UTC</span></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: left; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\"><br></span></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><span style=\"color: rgb(0, 0, 0); text-decoration: inherit;\"><strong>Location of&nbsp;</strong><strong style=\" font-weight: 700; color: rgb(0, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255);\"><b style=\" font-weight: 700;\"><span style=\" font-size: 11pt; line-height: 15.6933px; font-family: Calibri, sans-serif; color: black; background: white;\">Maintenance:&nbsp;<span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\">xxxxx</span></span></b></strong></span></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><br></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span style=\"color:black;background:white;\"><strong>Duration:-<span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\">xxxxx</span></strong></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span style=\"color:black;background:white;\"><strong><span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\"><br></span></strong></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span style=\"color:black;background:white;\">Hawaiki NOC will monitor the progress of works throughout the maintenance window, keep you informed of developments as they occur and advise when the activity is complete.<br><br>Please contact the HAWAIKI SUBMARINE CABLE Network Operations Centre (NOC) with any questions of concerns.</span></p><p style=\" margin: 0cm; color: rgb(51, 51, 51); font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); font-size: 11pt; font-family: Calibri, sans-serif; line-height: normal;\"><i><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\">-----</span></i><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><b style=\" font-weight: 700;\"><i><span lang=\"EN-NZ\" style=\" color: rgb(52, 152, 219);\">Best Regards,</span></i></b><b style=\" font-weight: 700;\"><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: rgb(52, 152, 219);\"><br></span></b><b style=\" font-weight: 700;\"><i><span lang=\"EN-NZ\" style=\" color: rgb(52, 152, 219);\">HAWAIKI NOC</span></i></b><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br><br></span><span lang=\"EN-NZ\" style=\" color: black;\">In case of emergency, contact us on&nbsp;any of the following:</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- New-Zealand Toll-free : +64 800 002 600</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- Australia Toll-free : +61 1800 319 388</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- United States Toll-free : +1 8888 313 339</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- International Calling Number: +64 9 887 3243</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- Email :&nbsp;</span><span lang=\"EN-NZ\"><a href=\"mailto:support@hawaikicable.co.nz\" target=\"_blank\" style=\" color: rgb(46, 46, 241); text-decoration: none; cursor: pointer; background-color: transparent; font-weight: 700;\"><span style=\" color: blue;\">support@hawaikicable.co.nz</span></a></span></p><p style=\" margin: 0cm 0cm 0px; color: rgb(51, 51, 51); font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); font-size: 11pt; font-family: Calibri, sans-serif; line-height: normal;\"><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Arial, sans-serif; color: rgb(31, 73, 125);\">HAWAIKI CONFIDENTIALITY NOTICE:</span><br><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Arial, sans-serif; color: rgb(31, 73, 125);\">This email and its contents are confidential and may also be privileged.&nbsp;If you have received it in error, you may not read, use, copy or disclose this email or its attachments. If you are the intended recipient,&nbsp;you must not disclose or circulate this email or its content other than in accordance with any confidentiality arrangements between us.</span></p></div>"
    } else if (args.itemData.value === 'Maintenance_service impacting') {
      this.titlemodel = "Hawaiki Submarine Cable: Service impacting Maintenance Notification";
      this.defaultRTEInstance.value = "<div style=\"display: block;\"><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\">Dear &lt;BulkTicket-Customer&gt; NOC<span>&nbsp;</span>,</span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\"><br></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\">Please be advised of maintenance works&nbsp;<span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">(work description goes here</span></span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">﻿﻿</span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">).&nbsp;</span><span style=\"color: black; font-size: 11pt; text-align: inherit;\">This work<strong> is expected </strong>to impact your service(s) identified below.&nbsp;&nbsp;</span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><strong>Service ID</strong><strong>:</strong> &lt;BulkTicket-ServiceID&gt;</span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><span style=\"font-size:11.0pt;line-height:107%;font-family:&quot;Calibri&quot;,sans-serif;color:black;background:white;\"><strong>Reason for Notification</strong>: </span><span style=\"font-size:11.0pt;line-height:107%;font-family:&quot;Calibri&quot;,sans-serif;color:red;background:white;\">xxxxx</span><span style=\"font-size:11.0pt;line-height:107%;font-family:&quot;Calibri&quot;,sans-serif;color:black;background:white;\"><br><br></span></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><strong><b><span style=\"font-size:11.0pt;line-height:107%;font-family:&quot;Calibri&quot;,sans-serif;color:black;background:white;\">Maintenance Window</span></b>:</strong><br>Date/Time Start: </span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">﻿﻿</span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">YYYY:MM:DD:HH:MM:SS UTC</span><span lang=\"EN-NZ\" style=\"color:black;\"><br>Date/Time End:&nbsp; &nbsp;<span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: left; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\">YYYY:MM:DD:HH:MM:SS UTC</span></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: left; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\"><br></span></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><span style=\"color: rgb(0, 0, 0); text-decoration: inherit;\"><strong>Location of&nbsp;</strong><strong style=\" font-weight: 700; color: rgb(0, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255);\"><b style=\" font-weight: 700;\"><span style=\" font-size: 11pt; line-height: 15.6933px; font-family: Calibri, sans-serif; color: black; background: white;\">Maintenance:&nbsp;<span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\">xxxxx</span></span></b></strong></span></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><span style=\"color: rgb(0, 0, 0); text-decoration: inherit;\"><strong style=\" font-weight: 700; color: rgb(0, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255);\"><b style=\" font-weight: 700;\"><span style=\" font-size: 11pt; line-height: 15.6933px; font-family: Calibri, sans-serif; color: black; background: white;\"><span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\"><br></span></span></b></strong></span></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><span style=\"color: rgb(0, 0, 0); text-decoration: inherit;\"><strong style=\" font-weight: 700; color: rgb(0, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255);\"><b style=\" font-weight: 700;\"><span style=\" font-size: 11pt; line-height: 15.6933px; font-family: Calibri, sans-serif; color: black; background: white;\"><span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\"><strong style=\" font-weight: 700; color: rgb(0, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; text-align: justify; text-indent: 0px; white-space: normal;\"><span style=\" color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important;\"><span lang=\"EN-NZ\" style=\" font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); color: black;\"><strong style=\" font-weight: 700;\"><b style=\" font-weight: 700;\"><span style=\" font-size: 11pt; line-height: 15.6933px; font-family: Calibri, sans-serif; color: black; background: white;\">Service Impacting</span></b>:</strong><br>Date/Time Start:<span>&nbsp;</span></span><span style=\" font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration: inherit; color: rgb(255, 0, 0);\">﻿﻿</span><span style=\" font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration: inherit; color: rgb(255, 0, 0);\">YYYY:MM:DD:HH:MM:SS UTC</span><span lang=\"EN-NZ\" style=\" font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); color: black;\"><br>Date/Time End:&nbsp; &nbsp;<span style=\" color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: left; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); float: none; display: inline !important;\">YYYY:MM:DD:HH:MM:SS UTC</span></span></span></strong><br></span></span></b></strong></span></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><br></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span style=\"color:black;background:white;\"><strong>Duration:-<span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\">xxxxx</span></strong></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span style=\"color:black;background:white;\"><strong><span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\"><br></span></strong></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span style=\"color:black;background:white;\">Hawaiki NOC will monitor the progress of works throughout the maintenance window, keep you informed of developments as they occur and advise when the activity is complete.<br><br>Please contact the HAWAIKI SUBMARINE CABLE Network Operations Centre (NOC) with any questions of concerns.</span></p><p style=\" margin: 0cm; color: rgb(51, 51, 51); font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); font-size: 11pt; font-family: Calibri, sans-serif; line-height: normal;\"><i><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\">-----</span></i><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><b style=\" font-weight: 700;\"><i><span lang=\"EN-NZ\" style=\" color: rgb(52, 152, 219);\">Best Regards,</span></i></b><b style=\" font-weight: 700;\"><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: rgb(52, 152, 219);\"><br></span></b><b style=\" font-weight: 700;\"><i><span lang=\"EN-NZ\" style=\" color: rgb(52, 152, 219);\">HAWAIKI NOC</span></i></b><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br><br></span><span lang=\"EN-NZ\" style=\" color: black;\">In case of emergency, contact us on&nbsp;any of the following:</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- New-Zealand Toll-free : +64 800 002 600</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- Australia Toll-free : +61 1800 319 388</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- United States Toll-free : +1 8888 313 339</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- International Calling Number: +64 9 887 3243</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- Email :&nbsp;</span><span lang=\"EN-NZ\"><a href=\"mailto:support@hawaikicable.co.nz\" target=\"_blank\" style=\" color: rgb(46, 46, 241); text-decoration: none; cursor: pointer; background-color: transparent; font-weight: 700;\"><span style=\" color: blue;\">support@hawaikicable.co.nz</span></a></span></p><p style=\" margin: 0cm 0cm 0px; color: rgb(51, 51, 51); font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); font-size: 11pt; font-family: Calibri, sans-serif; line-height: normal;\"><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Arial, sans-serif; color: rgb(31, 73, 125);\">HAWAIKI CONFIDENTIALITY NOTICE:</span><br><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Arial, sans-serif; color: rgb(31, 73, 125);\">This email and its contents are confidential and may also be privileged.&nbsp;If you have received it in error, you may not read, use, copy or disclose this email or its attachments. If you are the intended recipient,&nbsp;you must not disclose or circulate this email or its content other than in accordance with any confidentiality arrangements between us.</span></p></div>"
    }else if (args.itemData.value === 'Internal_Only_Incident') {
      this.titlemodel = "Hawaiki Submarine Cable: Network Incident Internal";
      this.defaultRTEInstance.value = "<div style=\"display: block;\"><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\">Dear &lt;BulkTicket-Customer&gt; NOC<span>&nbsp;</span>,</span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\"><br></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\">Hawaiki NOC has identified a<strong> Service Impacting</strong> event on your service which has been notified to the Hawaiki Engineering team and Duty Incident Manager.</span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\">Service ID: &lt;BulkTicket-ServiceID&gt;</span></p><p><br></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><strong>Service Impacting :</strong><br>Date/Time Start: </span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">﻿﻿</span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">YYYY:MM:DD:HH:MM:SS UTC</span><span lang=\"EN-NZ\" style=\"color:black;\"><br>Date/Time End:&nbsp; &nbsp;<span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: left; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\">YYYY:MM:DD:HH:MM:SS UTC</span></span></p><p><br></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\"><br>While this is being investigated, please review, and advise if this event is due to any maintenance works or faults on your side. Please standby for the next update and contact the Hawaiki NOC for further details.</span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\"><br></span></p><p style=\" margin: 0cm; color: rgb(51, 51, 51); font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); font-size: 11pt; font-family: Calibri, sans-serif; line-height: normal;\"><i><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\">-----</span></i><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><b style=\" font-weight: 700;\"><i><span lang=\"EN-NZ\" style=\" color: rgb(52, 152, 219);\">Best Regards,</span></i></b><b style=\" font-weight: 700;\"><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: rgb(52, 152, 219);\"><br></span></b><b style=\" font-weight: 700;\"><i><span lang=\"EN-NZ\" style=\" color: rgb(52, 152, 219);\">HAWAIKI NOC</span></i></b><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br><br></span><span lang=\"EN-NZ\" style=\" color: black;\">In case of emergency, contact us on&nbsp;any of the following:</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- New-Zealand Toll-free : +64 800 002 600</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- Australia Toll-free : +61 1800 319 388</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- United States Toll-free : +1 8888 313 339</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- International Calling Number: +64 9 887 3243</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- Email :&nbsp;</span><span lang=\"EN-NZ\"><a href=\"mailto:support@hawaikicable.co.nz\" target=\"_blank\" style=\" color: rgb(46, 46, 241); text-decoration: none; cursor: pointer; background-color: transparent; font-weight: 700;\"><span style=\" color: blue;\">support@hawaikicable.co.nz</span></a></span></p><p style=\" margin: 0cm 0cm 0px; color: rgb(51, 51, 51); font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); font-size: 11pt; font-family: Calibri, sans-serif; line-height: normal;\"><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Arial, sans-serif; color: rgb(31, 73, 125);\">HAWAIKI CONFIDENTIALITY NOTICE:</span><br><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Arial, sans-serif; color: rgb(31, 73, 125);\">This email and its contents are confidential and may also be privileged.&nbsp;If you have received it in error, you may not read, use, copy or disclose this email or its attachments. If you are the intended recipient,&nbsp;you must not disclose or circulate this email or its content other than in accordance with any confidentiality arrangements between us.</span></p></div>";
    }else if (args.itemData.value === 'Internal_Only_Maintenance') {
      this.titlemodel = "Hawaiki Submarine Cable: Network Internal";
      this.defaultRTEInstance.value = "<div style=\"display: block;\"><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\">Dear &lt;BulkTicket-Customer&gt; NOC<span>&nbsp;</span>,</span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\"><br></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\">Please be advised of maintenance works&nbsp;<span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">(work description goes here</span></span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">﻿﻿</span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">).&nbsp;</span><span style=\"color: black; font-size: 11pt; text-align: inherit;\">This work<strong> is not expected </strong>to impact your service(s) identified below.&nbsp;&nbsp;</span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><strong>Service ID</strong><strong>:</strong> &lt;BulkTicket-ServiceID&gt;</span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><span style=\"font-size:11.0pt;line-height:107%;font-family:&quot;Calibri&quot;,sans-serif;color:black;background:white;\"><strong>Reason for Notification</strong>: </span><span style=\"font-size:11.0pt;line-height:107%;font-family:&quot;Calibri&quot;,sans-serif;color:red;background:white;\">xxxxx</span><span style=\"font-size:11.0pt;line-height:107%;font-family:&quot;Calibri&quot;,sans-serif;color:black;background:white;\"><br><br></span></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><strong><b><span style=\"font-size:11.0pt;line-height:107%;font-family:&quot;Calibri&quot;,sans-serif;color:black;background:white;\">Maintenance Window</span></b>:</strong><br>Date/Time Start: </span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">﻿﻿</span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">YYYY:MM:DD:HH:MM:SS UTC</span><span lang=\"EN-NZ\" style=\"color:black;\"><br>Date/Time End:&nbsp; &nbsp;<span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: left; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\">YYYY:MM:DD:HH:MM:SS UTC</span></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: left; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\"><br></span></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><span style=\"color: rgb(0, 0, 0); text-decoration: inherit;\"><strong>Location of&nbsp;</strong><strong style=\" font-weight: 700; color: rgb(0, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255);\"><b style=\" font-weight: 700;\"><span style=\" font-size: 11pt; line-height: 15.6933px; font-family: Calibri, sans-serif; color: black; background: white;\">Maintenance:&nbsp;<span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\">xxxxx</span></span></b></strong></span></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><br></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span style=\"color:black;background:white;\"><strong>Duration:-<span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\">xxxxx</span></strong></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span style=\"color:black;background:white;\"><strong><span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\"><br></span></strong></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span style=\"color:black;background:white;\">Hawaiki NOC will monitor the progress of works throughout the maintenance window, keep you informed of developments as they occur and advise when the activity is complete.<br><br>Please contact the HAWAIKI SUBMARINE CABLE Network Operations Centre (NOC) with any questions of concerns.</span></p><p style=\" margin: 0cm; color: rgb(51, 51, 51); font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); font-size: 11pt; font-family: Calibri, sans-serif; line-height: normal;\"><i><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\">-----</span></i><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><b style=\" font-weight: 700;\"><i><span lang=\"EN-NZ\" style=\" color: rgb(52, 152, 219);\">Best Regards,</span></i></b><b style=\" font-weight: 700;\"><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: rgb(52, 152, 219);\"><br></span></b><b style=\" font-weight: 700;\"><i><span lang=\"EN-NZ\" style=\" color: rgb(52, 152, 219);\">HAWAIKI NOC</span></i></b><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br><br></span><span lang=\"EN-NZ\" style=\" color: black;\">In case of emergency, contact us on&nbsp;any of the following:</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- New-Zealand Toll-free : +64 800 002 600</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- Australia Toll-free : +61 1800 319 388</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- United States Toll-free : +1 8888 313 339</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- International Calling Number: +64 9 887 3243</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- Email :&nbsp;</span><span lang=\"EN-NZ\"><a href=\"mailto:support@hawaikicable.co.nz\" target=\"_blank\" style=\" color: rgb(46, 46, 241); text-decoration: none; cursor: pointer; background-color: transparent; font-weight: 700;\"><span style=\" color: blue;\">support@hawaikicable.co.nz</span></a></span></p><p style=\" margin: 0cm 0cm 0px; color: rgb(51, 51, 51); font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); font-size: 11pt; font-family: Calibri, sans-serif; line-height: normal;\"><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Arial, sans-serif; color: rgb(31, 73, 125);\">HAWAIKI CONFIDENTIALITY NOTICE:</span><br><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Arial, sans-serif; color: rgb(31, 73, 125);\">This email and its contents are confidential and may also be privileged.&nbsp;If you have received it in error, you may not read, use, copy or disclose this email or its attachments. If you are the intended recipient,&nbsp;you must not disclose or circulate this email or its content other than in accordance with any confidentiality arrangements between us.</span></p></div>"
    }
  }
  clickHandler(args: ClickEventArgs): void {

    if (args.item.id === 'createtickets') {
      this.services = this.gridInstance.getSelectedRecords();
      if( this.services.length > 0) {
        this.services =    this.services.reduce((accumalator, current) => {
          if(!accumalator.some(obj => obj.serviceName === current.serviceName)) {
            accumalator.push(current);
          }
          return accumalator;
        },[] as any[]).sort((a, b) => (a.servicename > b.servicename ? -1 : 1));;

        this.serviceDialog.show(true);
      }else{
        alert("Please select any row")
      }
    }else  if (args.item.id === 'ticketsstatus') {
      this.operationalstateservice.findTickets(
      ).subscribe(
        (res: HttpResponse<OperationalStateData[]>) => this.onTicketStatusSuccess(res.body, res.headers)
      );
    }
  }


  bulkCustomer:any = {};
  public createSingleBulkTicket(){

    for (const key in this.bulkCustomer) {
      let allservices = "";
      let customershrtName = "";
      this.bulkCustomer[key].forEach (
        (service: any) => {
          allservices = allservices + service.serviceName +" , ";
          customershrtName = service.customershrtName;
        }
      );

      if(allservices.length > 3)
        allservices = allservices.slice(0, -3);
      this.ticket.Bulk = "SINGLE";
      this.ticket.Service = customershrtName;
      this.ticket.Title = this.titlemodel;
      if( this.ticketType === 'Maintenance_non-service impacting' || this.ticketType === 'Maintenance_service impacting' ) {
        this.ticket.Type = 'Maintainence';
      }else
        this.ticket.Type = this.ticketypemodel;
      this.ticket.CustomerID = customershrtName;
      this.article.Body = this.defaultRTEInstance.getHtml();
      if(key)
        this.article.Body = this.article.Body.replace("&lt;BulkTicket-Customer&gt;",key)
      else
        this.article.Body = this.article.Body.replace("&lt;BulkTicket-Customer&gt;",' ')
      this.article.Body = this.article.Body.replace("&lt;BulkTicket-ServiceID&gt;",allservices)
      this.article.Subject = this.titlemodel;
      this.ticketrequest.Ticket = this.ticket;
      this.ticketrequest.Article = this.article;
      this.operationalstateservice.createTicket(this.ticketrequest).subscribe(
        (res: HttpResponse<any>) => this.onTicketSuccess(res.body, res.headers),
        (err: HttpErrorResponse) => this.onError(err)
      );
    }
  }

  private onTicketStatusSuccess(data:any, headers:any) {
    this.ticketReponse = data;
    this.ticketStatusDialog.show(true);
  }

  getDuration(downSince: string): string {
    if (!downSince) return '';

    const start = new Date(downSince).getTime();
    const now = new Date().getTime();

    const diffMs = now - start;

    const minutes = Math.floor(diffMs / (1000 * 60));
    const days = Math.floor(minutes / (60 * 24));
    const hours = Math.floor((minutes % (60 * 24)) / 60);
    const mins = minutes % 60;

    return `${days}d ${hours}h ${mins}m`;
  }

  onTicketSuccess(data:any, headers:any) {
    if (data?.Error) {
      this.infoMsg = data.Error.ErrorMessage;
    } else if(data?.status) {
      this.infoMsg = data.status;
    } else{
      this.infoMsg = "Success."
    }
  }


}

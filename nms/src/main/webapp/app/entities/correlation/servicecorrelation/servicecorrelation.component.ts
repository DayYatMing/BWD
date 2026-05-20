import { Component, OnInit, ViewChild, AfterViewInit, ViewEncapsulation } from '@angular/core';
import { ClickEventArgs,MenuEventArgs, ExpandEventArgs} from '@syncfusion/ej2-navigations'
import { GridComponent, GroupSettingsModel, } from '@syncfusion/ej2-angular-grids';
import { DialogComponent } from "@syncfusion/ej2-angular-popups";
import { ServicecorrelationService } from "./servicecorrelation.service";
import { ServicecorrelationModel } from "./servicecorrelation.model";
import { HttpHeaders, HttpResponse, HttpErrorResponse} from "@angular/common/http";
import { CommonModule } from '@angular/common';
import { AlertService } from 'app/core/util/alert.service';
import { Timeline} from "../../networkconstruction/timeline";
import { Article, Ticket, TicketRequest, TicketResponse} from "./ticket.model";
import { DomSanitizer} from "@angular/platform-browser";
import { AccordionModule } from '@syncfusion/ej2-angular-navigations';
import { FormsModule } from '@angular/forms';
import { DropDownListModule } from '@syncfusion/ej2-angular-dropdowns';
import { ButtonPropsModel} from "@syncfusion/ej2-popups";
import { DialogModule } from '@syncfusion/ej2-angular-popups';
import { AccordionComponent } from '@syncfusion/ej2-angular-navigations';
import {ColumnModel, SelectionSettingsModel} from "@syncfusion/ej2-grids";
import { Grid, Group } from '@syncfusion/ej2-angular-grids';
import {ButtonComponent, SwitchComponent} from "@syncfusion/ej2-angular-buttons";
import { DropDownListComponent } from '@syncfusion/ej2-angular-dropdowns';
import { ContextMenuItem } from '@syncfusion/ej2-angular-grids';
import {FormGroup} from "@angular/forms";
import {EmitType} from "@syncfusion/ej2-base";
import {Observable} from "rxjs";
import { ButtonModule } from '@syncfusion/ej2-angular-buttons';
import { ProgressButtonModule } from '@syncfusion/ej2-angular-splitbuttons';
import { HtmlEditorService, RichTextEditorComponent,RichTextEditorModule,RichTextEditorAllModule} from '@syncfusion/ej2-angular-richtexteditor';
import {
  LinkService,
  ImageService,
} from '@syncfusion/ej2-angular-richtexteditor';
import {GridModule,
  FilterService,
  IFilter,
  ToolbarService,
  VirtualScrollService,
  RowDDService,
  SelectionService,
  ResizeService,
  ColumnMenuService,
  PdfExportService,
  SortService,
  ContextMenuService,
  ExcelExportService, QueryCellInfoEventArgs, RowSelectEventArgs, ContextMenuItemModel, EditService, PageService
} from '@syncfusion/ej2-angular-grids';

Grid.Inject(Group);

@Component(
  {
    selector: 'jhi-servicecorrelation',
    styleUrls: ['servicecorrelation.css'],
    templateUrl: './servicecorrelation.component.html',
    imports: [GridModule, ButtonModule, ProgressButtonModule, CommonModule,RichTextEditorAllModule, DropDownListModule, AccordionModule, DialogModule, FormsModule],

    providers: [
      ToolbarService , HtmlEditorService,  FilterService,VirtualScrollService, SelectionService , SortService
      , RowDDService, ExcelExportService, PdfExportService, ContextMenuService
      ,ResizeService , ColumnMenuService, LinkService, ImageService,EditService,PageService
    ]
  }
)

export class ServicecorrelationComponent implements OnInit {
  infoMsg: string = "";
  progressLoader: boolean = true;
  mcsServiceUrl: string = "https://ncs-ausy.local.bw-digital.com/ui/#/transport-services?keyword"
  grafanaUrl = "https://stats-new.local.bw-digital.com/d/rWNnA1bne/customer-service-12x-factors-fast-ref?orgId=1&from=now-3h&to=now&timezone=utc&var-pm_source=$__all&fullscreen=true&kiosk=true&theme=light&var-AvgMax=$__all&refresh=15m";

  constructor(private correlationService: ServicecorrelationService) {}
  public tools: object = {
    type: 'MultiRow',
    items: ['Bold', 'Italic', 'Underline', 'StrikeThrough',
      'FontName', 'FontSize', 'FontColor', 'BackgroundColor',
      'LowerCase', 'UpperCase', '|',
      'Formats', 'Alignments', 'OrderedList', 'UnorderedList',
      'Outdent', 'Indent', '|',
      'CreateLink', 'Image', '|', 'ClearFormat', 'Print',
      'SourceCode', 'FullScreen', '|', 'Undo', 'Redo']
  };
  public ticketType = "";
  public toolbar!: Object[];
  public tickettoolbar: Object[] = ['ExcelExport', 'Search' ];
  public dateFormat:object = { type: 'dateTime', format: 'dd/MM/yyyy hh:mm' };
  public confirmHeader: string = 'Confirmation:';
  @ViewChild('confirmDialog')
  public confirmDialog!: DialogComponent;
  public editSettings!: Object;

  resString = '';
  public resMap = new Map();

  public confirmAlertDlgBtnClick = (): void => {
    this.resString = '';
    this.resMap = new Map();
    var totalCount = 0;
    this.bulkCustomer =  {};

    for(var data of this.selectedServices) {
      var customerKey: string = '';
      if(data.comment !== undefined && data.comment !== null && data.comment.includes('Customer-Customer-Service--')){
        var customerservice = data.comment.substr(data.comment.indexOf('Customer-Customer-Service--')+27,
          data.comment.indexOf('----') - (data.comment.indexOf('Customer-Customer-Service--')+27) );
        customerservice = customerservice.substr(customerservice.indexOf('::')+2);
        data.servicename = customerservice.substr(customerservice.indexOf('/')+1 );
        data.customername =     customerservice.substr(0,customerservice.indexOf('/'));
      }
      if(data.customername) customerKey = data.customername
      if(!this.bulkCustomer[customerKey]) {
        this.bulkCustomer[customerKey] = [];
      }
      if(data.servicename){
        this.bulkCustomer[customerKey].push({
          serviceName: data.servicename,
          customershrtName:  data.servicename.substr(0,3)
        })
      }

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
  public confirmAlertDlgCloseClick = (): void => {
    this.confirmDialog.hide();
  }


  public ticketClick = (): void => {
    this.bulkCustomer ={};
    this.visiblespinny = true;
    this.selectedServices.forEach(
      data => {
        var customerKey = '';
        var customershrtName = '';
        if(data.comment !== undefined && data.comment !== null && data.comment.includes('Customer-Customer-Service--')){
          var customerservice = data.comment.substr(data.comment.indexOf('Customer-Customer-Service--')+27,
            data.comment.indexOf('----') - (data.comment.indexOf('Customer-Customer-Service--')+27) );
          var parentService = customerservice.substr(0, customerservice.indexOf('::'));
          var childService = customerservice.substr(customerservice.indexOf('::')+2);
          data.servicename = parentService.substr(parentService.indexOf('/')+1 )+"::"+childService.substr(childService.indexOf('/')+1 );
          data.customername =     childService.substr(0,childService.indexOf('/'));
          customershrtName = childService.substr(childService.indexOf('/')+1 ).substr(0,3);

        }else{
          if(data.servicename) customershrtName =  data.servicename.substr(0,3)
        }
        if(data.customername) customerKey = data.customername;
        if(!this.bulkCustomer[customerKey]) {
          this.bulkCustomer[customerKey] = [];
        }
        this.bulkCustomer[customerKey].push({
          serviceName: data.servicename,
          customershrtName:  customershrtName
        });

        if( this.ticketType === 'Incident'
          || this.ticketType === 'Maintenance_service impacting' || this.ticketType === 'Internal_Only_Incident'
          || this.ticketType === 'Internal_Only_Maintenance') {
          this.ticket.Bulk = "BULK";
          this.ticket.Service = data.servicename;
          if(this.ticketType === 'Maintenance_service impacting'
            || this.ticketType === 'Internal_Only_Maintenance') {
            this.ticket.Type = 'Maintenance';
            this.ticket.Title = "Internal : " +this.titlemodel;
          }
          else if(this.ticketType === 'Incident' || this.ticketType === 'Internal_Only_Incident'){
            this.ticket.Type = 'Incident';
            this.ticket.Title = "Internal : " + this.titlemodel;
          }
          this.ticket.CustomerID = customershrtName;
          this.article.Body = this.defaultRTEInstance.getHtml();
          if(data.customername)
            this.article.Body = this.article.Body.replace("&lt;BulkTicket-Customer&gt;",data.customername)
          else
            this.article.Body = this.article.Body.replace("&lt;BulkTicket-Customer&gt;",' ')

          if(data.servicename) this.article.Body = this.article.Body.replace("&lt;BulkTicket-ServiceID&gt;",data.servicename)
          this.article.Subject = this.titlemodel;
          this.ticketrequest.Ticket = this.ticket;
          this.ticketrequest.Article = this.article;
          this.correlationService.createTicket(this.ticketrequest).subscribe(
            (res: HttpResponse<any>) => this.onTicketSuccess(res.body, res.headers),
            (err: HttpErrorResponse) => this.onError(err)
          );
        }else if(this.ticketType === 'One Ticket Per Service'){
          this.ticket.Bulk = "SINGLE";
          this.ticket.Service = data.servicename;
          this.ticket.Type = 'Maintenance';
          this.ticket.Title = this.titlemodel;
          this.ticket.CustomerID = customershrtName;
          this.article.Body = this.defaultRTEInstance.getHtml();
          if(data.customername)
            this.article.Body = this.article.Body.replace("&lt;BulkTicket-Customer&gt;",data.customername)
          else
            this.article.Body = this.article.Body.replace("&lt;BulkTicket-Customer&gt;",' ')

          if(data.servicename) this.article.Body = this.article.Body.replace("&lt;BulkTicket-ServiceID&gt;",data.servicename)
          this.article.Subject = this.titlemodel;
          this.ticketrequest.Ticket = this.ticket;
          this.ticketrequest.Article = this.article;
          this.correlationService.createTicket(this.ticketrequest).subscribe(
            (res: HttpResponse<any>) => this.onTicketSuccess(res.body, res.headers),
            (err: HttpErrorResponse) => this.onError(err)
          );
        }
      });
    if(this.ticketType === 'Incident' || this.ticketType === 'Hazardous Condition'
      || this.ticketType === 'Maintenance_service impacting'   || this.ticketType === 'Maintenance_non-service impacting' )
      this.createSingleBulkTicket();
    this.ticketDialog.hide();
    this.confirmDialog.hide();
    this.visiblespinny = false;
    alert("Requests sent successfully! \nClick on Tickets Status Button to view details");

  }

  public confirmDlgButtons: ButtonPropsModel[] =
    [{ click: this.ticketClick.bind(this), buttonModel: { content: 'Confirm', isPrimary: true } },
      { click: this.confirmAlertDlgCloseClick.bind(this), buttonModel: { content: 'Cancel' } }];

  public confirmWidth: string = '500px';

  public groupOptions!: GroupSettingsModel;
  public group!: Group;
  public dReady: boolean = false;
  public detailsview: boolean = false;
  public portdetails!: ServicecorrelationModel;
  readonly imageType: string = 'data:image/PNG;base64,';

  public data!: Object[];
  public datadetail!: ServicecorrelationModel[];
  public filterSettings!: Object;
  public selectionSettings!: Object;

  ticketReponse!:TicketResponse[];

  @ViewChild('switch')
  public slider!: SwitchComponent;

  @ViewChild('serviceDialog')
  public serviceDialog!: DialogComponent;

  @ViewChild('ticketstatusdialog')
  public ticketStatusDialog!: DialogComponent;

  @ViewChild('ticketDialog')
  public ticketDialog!: DialogComponent;

  @ViewChild('confirmButton')
  public dialogBtn!: ButtonComponent;

  @ViewChild('sample')
  public listObj!: DropDownListComponent;
  @ViewChild('overviewgrid')
  public gridInstance! : GridComponent ;

  @ViewChild('servicegrid')
  public servicegridInstance!: GridComponent ;

  @ViewChild('ticketreponsegrid')
  public ticketreponsegridInstance!: GridComponent ;

  @ViewChild('overviewgriddetail')
  public overviewgriddetail!: GridComponent ;

  selectedrecords!: ServicecorrelationModel[];
  services: ServicecorrelationModel[] = [];
  selectedServices: ServicecorrelationModel[] = [];
  ticketrequest: TicketRequest = {};
  ticket:Ticket = {};
  article:Article = {};


  @ViewChild('alertDialog')
  public alertDialog!: DialogComponent;
  @ViewChild('switch')
  public switch!: SwitchComponent;
  public enablelinks: Boolean = false;
  public hidden: Boolean = false;
  public alertHeader: string = 'Copy with Header';
  public alertWidth: string = '300px';
  public target: string = '.control-section3';
  public targetservice: string = '.control-section-service';
  public targetticket: string = '.control-section-ticket';
  public alertContent: string = 'Atleast one row should be selected to copy with header';
  public showCloseIcon: Boolean = false;
  public alertDlgBtnClick = () => {
    this.alertDialog.hide();
  }
  public alertDlgButtons: Object[] = [{ click: this.alertDlgBtnClick.bind(this), buttonModel: { content: 'OK', isPrimary: true } }];
  public animationSettings: Object = { effect: 'Zoom' };


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

  public ticketcloseClick = (): void => {
    this.ticketStatusDialog.hide();
  }


  public serviceButtonClickClose  = (): void => {
    this.serviceDialog.element.style.display = 'none';
  }


  ticketstatustoolbarbarClick(args: ClickEventArgs): void {
    switch (args.item.text) {
      case 'Excel Export':
        this.ticketreponsegridInstance.excelExport();
        break;

    }
  }
  bulkCustomer:any = {};

  public createSingleBulkTicketCount(ticketslen: any){
    for(var key in this.bulkCustomer) {
      this.bulkCustomer[key]
      if( !this.resMap.has(key+" EXTERNAL") )
        this.resMap.set(key+" EXTERNAL", 1);
      else
        this.resMap.set(key+" EXTERNAL" , this.resMap.get(key+" EXTERNAL") +1 );
      ticketslen++
    };

    return ticketslen;
  }


  public createSingleBulkTicket(){

    for (const key in this.bulkCustomer) {
      let allservices = "";
      let customershrtName = "";
      this.bulkCustomer[key].forEach (
        (service: { serviceName: string; customershrtName: string }) => {
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
        this.ticket.Type = 'Maintenance';
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
      this.correlationService.createTicket(this.ticketrequest).subscribe(
        (res: HttpResponse<any>) => this.onTicketSuccess(res.body, res.headers),
        (err: HttpErrorResponse) => this.onError(err)
      );
    }
  }


  onTicketSuccess(data: any, headers: any) {
    if (data?.Error) {
      this.infoMsg = data.Error.ErrorMessage;
    } else if(data?.status) {
      this.infoMsg = data.status;
    } else{
      this.infoMsg = "Success."
    }
  }
  public ticketClickClose = (): void => {
    this.ticketDialog.hide();
    this.confirmDialog.hide();
  }

  public showServiceDialog  = (): void => {
    this.ticketDialog.hide();
    this.serviceDialog.show(true);
  }
  public pageSettings: Object = { pageCount: 5 };
  public ticketReponseDlgButtons:  ButtonPropsModel[] = [{ click: this.ticketcloseClick.bind(this), buttonModel: { content: 'Close', isPrimary: true }  }];

  public ticketDlgButtons:  ButtonPropsModel[] = [{ click: this.serviceButtonClick.bind(this), buttonModel: { content: 'Next', isPrimary: true } }, { click: this.serviceButtonClickClose.bind(this), buttonModel: { content: 'Cancel', cssClass: 'e-flat' } }];
  public secondDlgButtons:  ButtonPropsModel[] = [{ click: this.showServiceDialog.bind(this), buttonModel: { content: 'Back', isPrimary: true } }, { click: this.confirmAlertDlgBtnClick.bind(this), buttonModel: { content: 'Ok',  cssClass: 'e-flat'  } } , { click: this.ticketClickClose.bind(this), buttonModel: { content: 'Cancel', cssClass: 'e-flat' } }];
  public watermark: string = 'Select a Ticket Type';

  public dialogHeader1 = 'Do You want to create tickets for following Services';
  public dialogHeaderTicket = 'Ticket Status';
  public dialogWidth1 = '300px';

  public animationSettings1: Object = { effect: 'None' };
  public secondDlgHeader = 'Ticket Details:';
  public secondDialogCloseIcon: Boolean = true;
  public secondDialogWidth = '600px';
  public animationSettings2: Object = { effect: 'None' };

  public visible1: Boolean = false;
  public visible2: Boolean = false;


  public pageOptions: Object = { pageSizes: true,pageSize: 5 };
  public selectionOptions: Object = { type: 'Multiple',  enableSimpleMultiRowSelection: true , persistSelection: true , checkboxMode: 'ResetOnRowClick'};
  public selectionOptionsservices: Object = { type: 'Multiple', enableSimpleMultiRowSelection: true  , persistSelection: true};

  public customNetworkAttributes: Object =  {class: 'customnetworkcss'};
  public customPortAttributes: Object =  {class: 'customportcss'};
  public odfCustomAttributes: Object =  {class: 'odfcustomcss'};
  public patchCustomAttributes: Object =  {class: 'patchcustomcss'};
  public mmrCustomAttributes: Object =  {class: 'mmrcustomcss'};
  public fields: Object = { text: 'text', value: 'Value' };




  public ticketypedata: string[] = [
    'One Ticket Per Service', 'Incident' ,  'Hazardous Condition','Maintenance_service impacting' , 'Maintenance_non-service impacting' ,
    'Internal_Only_Incident' , 'Internal_Only_Maintenance'
  ];

  public contextMenuItems: ContextMenuItem[] = ['AutoFit', 'AutoFitAll', 'SortAscending', 'SortDescending',
    'Copy',
    'ExcelExport','CsvExport', 'FirstPage', 'PrevPage',
    'LastPage', 'NextPage'];

  public columnMenuItems: Object[] = ['AutoFit', 'AutoFitAll', 'SortAscending', 'SortDescending','Group','Ungroup','Filter'
  ];

  @ViewChild('accordion')
  public acrdn!: AccordionComponent;

  public genericColumns: ColumnModel[] =  [
    {
      field: 'vendor',
      headerText: 'Vendor',
      width: 120,
      minWidth: 100,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },
    {
      field: 'sitename',
      headerText: 'Site',
      width: 120,
      minWidth: 80,
      autoFit: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },{
      field: 'customername',
      headerText: 'Customer',
      width: 160,
      minWidth: 120,
      autoFit: true,
      isPrimaryKey: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    }, {
      field: 'servicename',
      headerText: 'Service Id',
      width: 120,
      minWidth: 120,
      autoFit: true,
      isPrimaryKey: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    }, {
      field: 'customerserviceid',
      headerText:'Comment',
      width: 120,
      minWidth: 120,
      autoFit: true,
      isPrimaryKey: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: true
    }, {
      field: 'route',
      headerText: 'Route',
      width: 120,
      minWidth: 100,
      autoFit: true,
      isPrimaryKey: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },{
      field: 'segment',
      headerText: 'Segment',
      width: 150,
      minWidth: 80,
      autoFit: true,
      isPrimaryKey: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },{
      field: 'dls',
      headerText: 'DLS',
      width: 120,
      minWidth: 60,
      autoFit: true,
      isPrimaryKey: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },{
      field: 'frequency',
      headerText: 'Frequency',
      width: 150,
      minWidth: 120,
      autoFit: true,
      isPrimaryKey: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },{
      field: 'capacity',
      headerText: 'Capacity',
      width: 150,
      minWidth: 100,
      autoFit: true,
      isPrimaryKey: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },{
      field: 'backhaul',
      headerText: 'BackhaulID',
      width: 150,
      minWidth: 100,
      autoFit: true,
      isPrimaryKey: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },{
      field: 'backhaulvendor',
      headerText: 'Backhaul Vendor',
      width: 150,
      minWidth: 100,
      autoFit: true,
      isPrimaryKey: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },{
      field: 'backhauldetails',
      headerText: 'Backhaul Details',
      width: 500,
      minWidth: 100,
      autoFit: true,
      isPrimaryKey: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },{
      field: 'offnetvendor',
      headerText: 'Offnet Vendor',
      width: 200,
      minWidth: 100,
      autoFit: true,
      isPrimaryKey: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },{
      field: 'offnetid',
      headerText: 'Offnet Id',
      width: 300,
      minWidth: 100,
      autoFit: true,
      isPrimaryKey: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },{
      field: 'offnetprotected',
      headerText: 'Offnet Protected',
      width: 200,
      minWidth: 100,
      autoFit: true,
      isPrimaryKey: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },{
      field: 'offnetaenddetails',
      headerText: 'Offnet A-End Details',
      width: 500,
      minWidth: 100,
      autoFit: true,
      isPrimaryKey: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },{
      field: 'offnetbenddetails',
      headerText: 'Offnet B-End Details',
      width: 500,
      minWidth: 100,
      autoFit: true,
      isPrimaryKey: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },{
      field: 'offnetsegment',
      headerText: 'Offnet Segment',
      width: 150,
      minWidth: 100,
      autoFit: true,
      isPrimaryKey: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    }
  ];
  public passthroughportColumns: ColumnModel[] =  [
    {
      field: 'comment',
      headerText: 'Client Port Comment',
      width: 500,
      minWidth: 150,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    }];
  public portColumns: ColumnModel[] =  [
    {
      field: 'roomlocation',
      headerText: 'Client Port Room Location',
      width: 250,
      minWidth: 100,
      autoFit: true,
      visible: this.detailsview,
      isPrimaryKey: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },{
      field: 'cardtype',
      headerText: 'Client Port Card Type',
      width: 250,
      minWidth: 100,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },{
      field: 'shelf',
      headerText: 'Client Port Shelf',
      width: 250,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },{
      field: 'slot',
      headerText: 'Client Port Slot',
      width: 200,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },{
      field: 'position',
      headerText: 'Client Port Position',
      width: 200,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },{
      field: 'portnumber',
      headerText: 'Client Port Port No.',
      width: 250,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },{
      field: 'direction',
      headerText: 'Client Port Direction',
      width: 200,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },{
      field: 'connector',
      headerText: 'Client Port Connector',
      width: 200,
      minWidth: 120,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },
    {
      field: 'portname',
      headerText: 'Port Name (Full)',
      width: 450,
      minWidth: 180,
      autoFit: true,
      isPrimaryKey: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    }
  ];
  public lsiODFColumns: ColumnModel[] =  [
    {
      field: 'lsi_odfcardtype',
      headerText: 'LSI ODF Card Type',
      width: 250,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },{
      field: 'lsi_roomlocation',
      headerText: 'LSI ODF Room Location',
      width: 250,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    }, {
      field: 'lsi_shelf',
      headerText: 'LSI ODF Shelf',
      width: 200,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },  {
      field: 'lsi_position',
      headerText: 'LSI ODF Position',
      width: 200,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    }, {
      field: 'lsi_port',
      headerText: 'LSI ODF Port',
      width: 200,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },{
      field: 'lsi_connector',
      headerText: 'LSI ODF Connector',
      width: 200,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },{
      field: 'lsiodf',
      headerText: 'LSI ODF ( Full  Name )',
      width: 400,
      minWidth: 300,
      isPrimaryKey: true,
      autoFit: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },
  ];
  public patchColumns: ColumnModel[] =  [
    {
      field: 'patch1cardtype',
      headerText: 'Patch ODF Card Type',
      width: 250,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },{
      field: 'patch1roomlocation',
      headerText: 'Patch ODF Room Location',
      width: 250,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    }, {
      field: 'patch1_shelf',
      headerText: 'Patch ODF Shelf',
      width: 200,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },  {
      field: 'patch1_position',
      headerText: 'Patch ODF Position',
      width: 200,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    }, {
      field: 'patch1_port',
      headerText: 'Patch ODF Port',
      width: 200,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },{
      field: 'patch1_connector',
      headerText: 'Patch ODF Connector',
      width: 200,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },{
      field: 'patch1',
      headerText: 'Patch ODF ( Full  Name )',
      width: 400,
      minWidth: 300,
      isPrimaryKey: true,
      autoFit: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },
    {
      field: 'patch1_thirdparty',
      headerText: 'Patch ODF Third Party',
      width: 400,
      minWidth: 300,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },
    {
      field: 'patch1_comment',
      headerText: 'Patch ODF Comment',
      width: 400,
      minWidth: 300,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },
  ];
  public patchColumns1: ColumnModel[] =  [
    {
      field: 'patch2_cardtype',
      headerText: 'MMR ODF Card Type',
      width: 250,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },{
      field: 'patch2_roomlocation',
      headerText: 'MMR ODF Room Location',
      width: 250,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    }, {
      field: 'patch2_shelf',
      headerText: 'MMR ODF Shelf',
      width: 200,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },  {
      field: 'patch2_position',
      headerText: 'MMR ODF Position',
      width: 200,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    }, {
      field: 'patch2_port',
      headerText: 'MMR ODF Port',
      width: 200,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },{
      field: 'patch2_connector',
      headerText: 'MMR ODF Connector',
      width: 200,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },
    {
      field: 'patch2',
      headerText: 'MMR ODF ( Full  Name )',
      width: 400,
      minWidth: 300,
      autoFit: true,
      isPrimaryKey: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },
    {
      field: 'patch2_thirdparty',
      headerText: 'MMR ODF Third Party',
      width: 400,
      minWidth: 300,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },
    {
      field: 'patch2_comment',
      headerText: 'MMR ODF Comment',
      width: 400,
      minWidth: 300,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false
    },
  ];

  @ViewChild('defaultDialog')
  public defaultDialog!: DialogComponent;
  public dialogHeader: string = 'Help';
  public dialogCloseIcon: Boolean = true;
  public dialogWidth: string = '1300px';
  public isModal: Boolean = true;

  public ticketclick: Boolean = false;
  reactForm!: FormGroup;
  public offnetcapacityclick: Boolean = false;
  public tpClick: Boolean = false;
  public spreadsheetclick: Boolean = false;
  public hide: any;
  public visible: Boolean = false;
  public visiblespinny: Boolean = false;

  public isUpdateClicked: boolean = false;
  public togglecolumns: string[] =     ['Vendor' , 'Segment' , 'Backhaul Details' , 'Client Port Room Location' ,'Client Port Card Type' , 'Client Port Shelf',
    'Client Port Slot' ,  'Client Port Position' , 'Client Port Port No.' , 'Client Port Direction' , 'Client Port Connector' ,
    'LSI ODF Card Type' , 'LSI ODF Room Location' , 'LSI ODF Shelf' ,   'LSI ODF Position' ,  'LSI ODF Port' , 'LSI ODF Connector',
    'Patch ODF Card Type' , 'Patch ODF Room Location' , 'Patch ODF Shelf' ,   'Patch ODF Position' ,  'Patch ODF Port' , 'Patch ODF Connector' ,
    'MMR ODF Card Type' , 'MMR ODF Room Location' , 'MMR ODF Shelf' ,   'MMR ODF Position' ,  'MMR ODF Port' , 'MMR ODF Connector'  ,  'MMR ODF Comment' ,  'MMR ODF Third Party'
    ,  'Patch ODF Comment' ,  'Patch ODF Third Party' , 'Client Port Comments/Passthrough/Backhauls' , 'Offnet Vendor', 'Offnet Id' , 'Offnet Protected' , 'Offnet A-End Details' , 'Offnet B-End Details' , 'Offnet Segment'
  ];



  public dlgButtonClick: EmitType<Object> = () => {
    this.defaultDialog.hide();
  }
  public defaultDlgButtons: Object[] = [{ click: this.dlgButtonClick.bind(this), buttonModel: { content: 'Hide', isPrimary: true } }];


  public dialogBtnClick: EmitType<Object> = (fullscreen: any) => {
    if(fullscreen)
      this.defaultDialog.show(fullscreen);
    else
      this.defaultDialog.show();
  }


  actionComplete(args: any) {

    // if (args.requestType === 'save') {
    //   this.data = args.data;
    //   if (args.data.portname && (args.data.portname.includes('_AOC') || args.data.portname.includes('_DAC'))) {
    //     this.subscribeToSaveResponse(this.correlationService.updateCustomerServiceDataDacPort(this.data));
    //   } else {
    //     this.subscribeToSaveResponse(this.correlationService.updateCustomerServiceData(this.data));
    //   }
    //
    // }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<void>>): void {
    result.subscribe(
      response => this.onSaveSuccess(response),
      (res: HttpErrorResponse) => this.onSaveError(res)
    );
  }

  private onSaveSuccess(response: HttpResponse<void>): void {
    if(response.status == 200)
    {
      this.isUpdateClicked = false;
      this.onAuthSuccess();
    }
    else {
      this.isUpdateClicked = false;
      this.onAuthSuccess();
    }
  }
  private onSaveError(error: HttpErrorResponse): void {
    console.error('Save failed', error);
    if(error) {
      this.isUpdateClicked = false;
      this.onAuthSuccess();
    }
  }

  private onAuthSuccess(){
    this.correlationService.query(
    ).subscribe(
      (res: HttpResponse<ServicecorrelationModel[]>) => this.onSuccess(res.body, res.headers)
    );
  }


  slide(): void {
    if(!this.detailsview) {
      this.gridInstance!.showColumns(this.togglecolumns);
      this.detailsview = true;
    } else {
      this.gridInstance!.hideColumns(this.togglecolumns);
      this.detailsview = false;
    }
  }


  ticketypemodel: any;

  @ViewChild('rte') defaultRTEInstance!: RichTextEditorComponent;

  titlemodel: any;

  public onRowSelected(args: any): void {
    this.portdetails = args.data;
    // this.correlationService.queryForNE(this.portdetails
    // ).subscribe(
    //   (res: HttpResponse<ServicecorrelationModel>) => this.onNEReposneSuccess(res.body, res.headers),
    //   (res: HttpErrorResponse) =>  this.onError(res)
    // );
    this.overviewgriddetail.dataSource = this.data.filter((element:ServicecorrelationModel) => element.portname === args.data.portname);
    this.overviewgriddetail.refresh();

  }

  public rowdeselected(args: any): void {
    this.portdetails = new ServicecorrelationModel();

  }

  toolbarClick(args: ClickEventArgs): void {
    switch (args.item.text) {

      case 'Excel Export':
        this.gridInstance!.excelExport();
        break;
      case 'CSV Export':
        this.gridInstance!.csvExport();
        break;
      case 'Copy With Header':
        if(this.gridInstance!.getSelectedRecords().length>0) {
          this.gridInstance!.copy(true);
        } else {
          this.alertDialog.show();
        }
        break;
      case 'Copy':
        if(this.gridInstance!.getSelectedRecords().length>0) {
          this.gridInstance!.copy(false);
        } else {
          this.alertDialog.show();
        }
        break;
    }
  }

  contextMenuClick(args: MenuEventArgs): void {

  }

  public ngOnInit(): void {
    this.loadAll();
  }

  loadAll(){
    this.data = [];
    this.datadetail = [];

    this.correlationService.query().subscribe({
      next: res => {
        console.log('Status:', res.status);
        console.log('Headers:', res.headers);
        if (res.body !== null) {
          this.data = res.body;
          this.progressLoader = false;

          this.filterSettings = { type: "Menu" };
          this.groupOptions = { showGroupedColumn: false  };
          this.toolbar = ['ExcelExport', 'CsvExport' , 'Print' ,
            { text: 'Create Tickets',  id: 'createtickets' } ,
            { text: 'Tickets Status',  id: 'ticketsstatus' } ,
             'Search' ];
          this.selectionSettings = {persistSelection: true, type: "Multiple", checkboxOnly: true };
          this.editSettings = { allowEditing: false };
          if(this.gridInstance){
            this.gridInstance!.on('data-ready', () => {
              this.dReady! = true;
            });
          }
        }
      },
      error: err => {
        this.infoMsg = err.message;
      },
    });
  }

  changetemplate(args: any) {
    if (args.itemData) {
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
      } else if (args.itemData.value === 'Maintenance_non-service impacting') {
        this.titlemodel = "Hawaiki Submarine Cable: Non-Service impacting Maintenance Notification";
        this.defaultRTEInstance.value = "<div style=\"display: block;\"><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\">Dear &lt;BulkTicket-Customer&gt; NOC<span>&nbsp;</span>,</span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\"><br></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\">Please be advised of maintenance works&nbsp;<span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">(work description goes here</span></span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">﻿﻿</span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">).&nbsp;</span><span style=\"color: black; font-size: 11pt; text-align: inherit;\">This work<strong> is not expected </strong>to impact your service(s) identified below.&nbsp;&nbsp;</span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><strong>Service ID</strong><strong>:</strong> &lt;BulkTicket-ServiceID&gt;</span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><span style=\"font-size:11.0pt;line-height:107%;font-family:&quot;Calibri&quot;,sans-serif;color:black;background:white;\"><strong>Reason for Notification</strong>: </span><span style=\"font-size:11.0pt;line-height:107%;font-family:&quot;Calibri&quot;,sans-serif;color:red;background:white;\">xxxxx</span><span style=\"font-size:11.0pt;line-height:107%;font-family:&quot;Calibri&quot;,sans-serif;color:black;background:white;\"><br><br></span></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><strong><b><span style=\"font-size:11.0pt;line-height:107%;font-family:&quot;Calibri&quot;,sans-serif;color:black;background:white;\">Maintenance Window</span></b>:</strong><br>Date/Time Start: </span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">﻿﻿</span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">YYYY:MM:DD:HH:MM:SS UTC</span><span lang=\"EN-NZ\" style=\"color:black;\"><br>Date/Time End:&nbsp; &nbsp;<span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: left; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\">YYYY:MM:DD:HH:MM:SS UTC</span></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: left; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\"><br></span></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><span style=\"color: rgb(0, 0, 0); text-decoration: inherit;\"><strong>Location of&nbsp;</strong><strong style=\" font-weight: 700; color: rgb(0, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255);\"><b style=\" font-weight: 700;\"><span style=\" font-size: 11pt; line-height: 15.6933px; font-family: Calibri, sans-serif; color: black; background: white;\">Maintenance:&nbsp;<span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\">xxxxx</span></span></b></strong></span></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><br></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span style=\"color:black;background:white;\"><strong>Duration:-<span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\">xxxxx</span></strong></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span style=\"color:black;background:white;\"><strong><span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\"><br></span></strong></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span style=\"color:black;background:white;\">Hawaiki NOC will monitor the progress of works throughout the maintenance window, keep you informed of developments as they occur and advise when the activity is complete.<br><br>Please contact the HAWAIKI SUBMARINE CABLE Network Operations Centre (NOC) with any questions of concerns.</span></p><p style=\" margin: 0cm; color: rgb(51, 51, 51); font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); font-size: 11pt; font-family: Calibri, sans-serif; line-height: normal;\"><i><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\">-----</span></i><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><b style=\" font-weight: 700;\"><i><span lang=\"EN-NZ\" style=\" color: rgb(52, 152, 219);\">Best Regards,</span></i></b><b style=\" font-weight: 700;\"><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: rgb(52, 152, 219);\"><br></span></b><b style=\" font-weight: 700;\"><i><span lang=\"EN-NZ\" style=\" color: rgb(52, 152, 219);\">HAWAIKI NOC</span></i></b><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br><br></span><span lang=\"EN-NZ\" style=\" color: black;\">In case of emergency, contact us on&nbsp;any of the following:</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- New-Zealand Toll-free : +64 800 002 600</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- Australia Toll-free : +61 1800 319 388</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- United States Toll-free : +1 8888 313 339</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- International Calling Number: +64 9 887 3243</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- Email :&nbsp;</span><span lang=\"EN-NZ\"><a href=\"mailto:support@hawaikicable.co.nz\" target=\"_blank\" style=\" color: rgb(46, 46, 241); text-decoration: none; cursor: pointer; background-color: transparent; font-weight: 700;\"><span style=\" color: blue;\">support@hawaikicable.co.nz</span></a></span></p><p style=\" margin: 0cm 0cm 0px; color: rgb(51, 51, 51); font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); font-size: 11pt; font-family: Calibri, sans-serif; line-height: normal;\"><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Arial, sans-serif; color: rgb(31, 73, 125);\">HAWAIKI CONFIDENTIALITY NOTICE:</span><br><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Arial, sans-serif; color: rgb(31, 73, 125);\">This email and its contents are confidential and may also be privileged.&nbsp;If you have received it in error, you may not read, use, copy or disclose this email or its attachments. If you are the intended recipient,&nbsp;you must not disclose or circulate this email or its content other than in accordance with any confidentiality arrangements between us.</span></p></div>"
      } else if (args.itemData.value === 'Maintenance_service impacting') {
        this.titlemodel = "Hawaiki Submarine Cable: Service impacting Maintenance Notification";
        this.defaultRTEInstance.value = "<div style=\"display: block;\"><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\">Dear &lt;BulkTicket-Customer&gt; NOC<span>&nbsp;</span>,</span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\"><br></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\">Please be advised of maintenance works&nbsp;<span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">(work description goes here</span></span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">﻿﻿</span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">).&nbsp;</span><span style=\"color: black; font-size: 11pt; text-align: inherit;\">This work<strong> is expected </strong>to impact your service(s) identified below.&nbsp;&nbsp;</span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><strong>Service ID</strong><strong>:</strong> &lt;BulkTicket-ServiceID&gt;</span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><span style=\"font-size:11.0pt;line-height:107%;font-family:&quot;Calibri&quot;,sans-serif;color:black;background:white;\"><strong>Reason for Notification</strong>: </span><span style=\"font-size:11.0pt;line-height:107%;font-family:&quot;Calibri&quot;,sans-serif;color:red;background:white;\">xxxxx</span><span style=\"font-size:11.0pt;line-height:107%;font-family:&quot;Calibri&quot;,sans-serif;color:black;background:white;\"><br><br></span></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><strong><b><span style=\"font-size:11.0pt;line-height:107%;font-family:&quot;Calibri&quot;,sans-serif;color:black;background:white;\">Maintenance Window</span></b>:</strong><br>Date/Time Start: </span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">﻿﻿</span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">YYYY:MM:DD:HH:MM:SS UTC</span><span lang=\"EN-NZ\" style=\"color:black;\"><br>Date/Time End:&nbsp; &nbsp;<span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: left; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\">YYYY:MM:DD:HH:MM:SS UTC</span></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: left; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\"><br></span></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><span style=\"color: rgb(0, 0, 0); text-decoration: inherit;\"><strong>Location of&nbsp;</strong><strong style=\" font-weight: 700; color: rgb(0, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255);\"><b style=\" font-weight: 700;\"><span style=\" font-size: 11pt; line-height: 15.6933px; font-family: Calibri, sans-serif; color: black; background: white;\">Maintenance:&nbsp;<span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\">xxxxx</span></span></b></strong></span></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><span style=\"color: rgb(0, 0, 0); text-decoration: inherit;\"><strong style=\" font-weight: 700; color: rgb(0, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255);\"><b style=\" font-weight: 700;\"><span style=\" font-size: 11pt; line-height: 15.6933px; font-family: Calibri, sans-serif; color: black; background: white;\"><span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\"><br></span></span></b></strong></span></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><span style=\"color: rgb(0, 0, 0); text-decoration: inherit;\"><strong style=\" font-weight: 700; color: rgb(0, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255);\"><b style=\" font-weight: 700;\"><span style=\" font-size: 11pt; line-height: 15.6933px; font-family: Calibri, sans-serif; color: black; background: white;\"><span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\"><strong style=\" font-weight: 700; color: rgb(0, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; text-align: justify; text-indent: 0px; white-space: normal;\"><span style=\" color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important;\"><span lang=\"EN-NZ\" style=\" font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); color: black;\"><strong style=\" font-weight: 700;\"><b style=\" font-weight: 700;\"><span style=\" font-size: 11pt; line-height: 15.6933px; font-family: Calibri, sans-serif; color: black; background: white;\">Service Impacting</span></b>:</strong><br>Date/Time Start:<span>&nbsp;</span></span><span style=\" font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration: inherit; color: rgb(255, 0, 0);\">﻿﻿</span><span style=\" font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration: inherit; color: rgb(255, 0, 0);\">YYYY:MM:DD:HH:MM:SS UTC</span><span lang=\"EN-NZ\" style=\" font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); color: black;\"><br>Date/Time End:&nbsp; &nbsp;<span style=\" color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: left; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); float: none; display: inline !important;\">YYYY:MM:DD:HH:MM:SS UTC</span></span></span></strong><br></span></span></b></strong></span></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><br></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span style=\"color:black;background:white;\"><strong>Duration:-<span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\">xxxxx</span></strong></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span style=\"color:black;background:white;\"><strong><span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\"><br></span></strong></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span style=\"color:black;background:white;\">Hawaiki NOC will monitor the progress of works throughout the maintenance window, keep you informed of developments as they occur and advise when the activity is complete.<br><br>Please contact the HAWAIKI SUBMARINE CABLE Network Operations Centre (NOC) with any questions of concerns.</span></p><p style=\" margin: 0cm; color: rgb(51, 51, 51); font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); font-size: 11pt; font-family: Calibri, sans-serif; line-height: normal;\"><i><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\">-----</span></i><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><b style=\" font-weight: 700;\"><i><span lang=\"EN-NZ\" style=\" color: rgb(52, 152, 219);\">Best Regards,</span></i></b><b style=\" font-weight: 700;\"><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: rgb(52, 152, 219);\"><br></span></b><b style=\" font-weight: 700;\"><i><span lang=\"EN-NZ\" style=\" color: rgb(52, 152, 219);\">HAWAIKI NOC</span></i></b><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br><br></span><span lang=\"EN-NZ\" style=\" color: black;\">In case of emergency, contact us on&nbsp;any of the following:</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- New-Zealand Toll-free : +64 800 002 600</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- Australia Toll-free : +61 1800 319 388</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- United States Toll-free : +1 8888 313 339</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- International Calling Number: +64 9 887 3243</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- Email :&nbsp;</span><span lang=\"EN-NZ\"><a href=\"mailto:support@hawaikicable.co.nz\" target=\"_blank\" style=\" color: rgb(46, 46, 241); text-decoration: none; cursor: pointer; background-color: transparent; font-weight: 700;\"><span style=\" color: blue;\">support@hawaikicable.co.nz</span></a></span></p><p style=\" margin: 0cm 0cm 0px; color: rgb(51, 51, 51); font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); font-size: 11pt; font-family: Calibri, sans-serif; line-height: normal;\"><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Arial, sans-serif; color: rgb(31, 73, 125);\">HAWAIKI CONFIDENTIALITY NOTICE:</span><br><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Arial, sans-serif; color: rgb(31, 73, 125);\">This email and its contents are confidential and may also be privileged.&nbsp;If you have received it in error, you may not read, use, copy or disclose this email or its attachments. If you are the intended recipient,&nbsp;you must not disclose or circulate this email or its content other than in accordance with any confidentiality arrangements between us.</span></p></div>"
      } else if (args.itemData.value === 'Internal_Only_Incident') {
        this.titlemodel = "Hawaiki Submarine Cable:  Network Incident Notification";
        this.defaultRTEInstance.value = "<div style=\"display: block;\"><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\">Dear &lt;BulkTicket-Customer&gt; NOC<span>&nbsp;</span>,</span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\"><br></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\">Hawaiki NOC has identified a<strong> Service Impacting</strong> event on your service which has been notified to the Hawaiki Engineering team and Duty Incident Manager.</span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\">Service ID: &lt;BulkTicket-ServiceID&gt;</span></p><p><br></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><strong>Service Impacting :</strong><br>Date/Time Start: </span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">﻿﻿</span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">YYYY:MM:DD:HH:MM:SS UTC</span><span lang=\"EN-NZ\" style=\"color:black;\"><br>Date/Time End:&nbsp; &nbsp;<span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: left; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\">YYYY:MM:DD:HH:MM:SS UTC</span></span></p><p><br></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\"><br>While this is being investigated, please review, and advise if this event is due to any maintenance works or faults on your side. Please standby for the next update and contact the Hawaiki NOC for further details.</span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\"><br></span></p><p style=\" margin: 0cm; color: rgb(51, 51, 51); font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); font-size: 11pt; font-family: Calibri, sans-serif; line-height: normal;\"><i><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\">-----</span></i><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><b style=\" font-weight: 700;\"><i><span lang=\"EN-NZ\" style=\" color: rgb(52, 152, 219);\">Best Regards,</span></i></b><b style=\" font-weight: 700;\"><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: rgb(52, 152, 219);\"><br></span></b><b style=\" font-weight: 700;\"><i><span lang=\"EN-NZ\" style=\" color: rgb(52, 152, 219);\">HAWAIKI NOC</span></i></b><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br><br></span><span lang=\"EN-NZ\" style=\" color: black;\">In case of emergency, contact us on&nbsp;any of the following:</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- New-Zealand Toll-free : +64 800 002 600</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- Australia Toll-free : +61 1800 319 388</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- United States Toll-free : +1 8888 313 339</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- International Calling Number: +64 9 887 3243</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- Email :&nbsp;</span><span lang=\"EN-NZ\"><a href=\"mailto:support@hawaikicable.co.nz\" target=\"_blank\" style=\" color: rgb(46, 46, 241); text-decoration: none; cursor: pointer; background-color: transparent; font-weight: 700;\"><span style=\" color: blue;\">support@hawaikicable.co.nz</span></a></span></p><p style=\" margin: 0cm 0cm 0px; color: rgb(51, 51, 51); font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); font-size: 11pt; font-family: Calibri, sans-serif; line-height: normal;\"><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Arial, sans-serif; color: rgb(31, 73, 125);\">HAWAIKI CONFIDENTIALITY NOTICE:</span><br><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Arial, sans-serif; color: rgb(31, 73, 125);\">This email and its contents are confidential and may also be privileged.&nbsp;If you have received it in error, you may not read, use, copy or disclose this email or its attachments. If you are the intended recipient,&nbsp;you must not disclose or circulate this email or its content other than in accordance with any confidentiality arrangements between us.</span></p></div>";
      } else if (args.itemData.value === 'Internal_Only_Maintenance') {
        this.titlemodel = "Hawaiki Submarine Cable: Network Internal";
        this.defaultRTEInstance.value = "<div style=\"display: block;\"><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\">Dear &lt;BulkTicket-Customer&gt; NOC<span>&nbsp;</span>,</span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\"><br></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span lang=\"EN-NZ\" style=\"color:black;\">Please be advised of maintenance works&nbsp;<span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">(work description goes here</span></span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">﻿﻿</span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">).&nbsp;</span><span style=\"color: black; font-size: 11pt; text-align: inherit;\">This work<strong> is not expected </strong>to impact your service(s) identified below.&nbsp;&nbsp;</span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><strong>Service ID</strong><strong>:</strong> &lt;BulkTicket-ServiceID&gt;</span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><span style=\"font-size:11.0pt;line-height:107%;font-family:&quot;Calibri&quot;,sans-serif;color:black;background:white;\"><strong>Reason for Notification</strong>: </span><span style=\"font-size:11.0pt;line-height:107%;font-family:&quot;Calibri&quot;,sans-serif;color:red;background:white;\">xxxxx</span><span style=\"font-size:11.0pt;line-height:107%;font-family:&quot;Calibri&quot;,sans-serif;color:black;background:white;\"><br><br></span></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><strong><b><span style=\"font-size:11.0pt;line-height:107%;font-family:&quot;Calibri&quot;,sans-serif;color:black;background:white;\">ID: &lt;OTRS_TICKET_TicketNumber&gt; Maintenance Window</span></b>:</strong><br>Date/Time Start: </span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">﻿﻿</span><span style=\"color: rgb(255, 0, 0); text-decoration: inherit;\">YYYY:MM:DD:HH:MM:SS UTC</span><span lang=\"EN-NZ\" style=\"color:black;\"><br>Date/Time End:&nbsp; &nbsp;<span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: left; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\">YYYY:MM:DD:HH:MM:SS UTC</span></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: left; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\"><br></span></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><span lang=\"EN-NZ\" style=\"color:black;\"><span style=\"color: rgb(0, 0, 0); text-decoration: inherit;\"><strong>Location of&nbsp;</strong><strong style=\" font-weight: 700; color: rgb(0, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255);\"><b style=\" font-weight: 700;\"><span style=\" font-size: 11pt; line-height: 15.6933px; font-family: Calibri, sans-serif; color: black; background: white;\">Maintenance:&nbsp;<span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\">xxxxx</span></span></b></strong></span></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-left:0cm;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;margin-bottom:0cm;line-height:normal;\"><br></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span style=\"color:black;background:white;\"><strong>Duration:-<span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\">xxxxx</span></strong></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span style=\"color:black;background:white;\"><strong><span style=\"color: rgb(255, 0, 0); font-family: Calibri, sans-serif; font-size: 14.6667px; font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); display: inline !important; float: none;\"><br></span></strong></span></p><p style=\"margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;\"><span style=\"color:black;background:white;\">Hawaiki NOC will monitor the progress of works throughout the maintenance window, keep you informed of developments as they occur and advise when the activity is complete.<br><br>Please contact the HAWAIKI SUBMARINE CABLE Network Operations Centre (NOC) with any questions of concerns.</span></p><p style=\" margin: 0cm; color: rgb(51, 51, 51); font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); font-size: 11pt; font-family: Calibri, sans-serif; line-height: normal;\"><i><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\">-----</span></i><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><b style=\" font-weight: 700;\"><i><span lang=\"EN-NZ\" style=\" color: rgb(52, 152, 219);\">Best Regards,</span></i></b><b style=\" font-weight: 700;\"><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: rgb(52, 152, 219);\"><br></span></b><b style=\" font-weight: 700;\"><i><span lang=\"EN-NZ\" style=\" color: rgb(52, 152, 219);\">HAWAIKI NOC</span></i></b><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br><br></span><span lang=\"EN-NZ\" style=\" color: black;\">In case of emergency, contact us on&nbsp;any of the following:</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- New-Zealand Toll-free : +64 800 002 600</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- Australia Toll-free : +61 1800 319 388</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- United States Toll-free : +1 8888 313 339</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- International Calling Number: +64 9 887 3243</span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" color: black;\">- Email :&nbsp;</span><span lang=\"EN-NZ\"><a href=\"mailto:support@hawaikicable.co.nz\" target=\"_blank\" style=\" color: rgb(46, 46, 241); text-decoration: none; cursor: pointer; background-color: transparent; font-weight: 700;\"><span style=\" color: blue;\">support@hawaikicable.co.nz</span></a></span></p><p style=\" margin: 0cm 0cm 0px; color: rgb(51, 51, 51); font-style: normal; font-weight: 400; text-align: justify; text-indent: 0px; white-space: normal; background-color: rgb(255, 255, 255); font-size: 11pt; font-family: Calibri, sans-serif; line-height: normal;\"><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Helvetica, sans-serif; color: black;\"><br></span><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Arial, sans-serif; color: rgb(31, 73, 125);\">HAWAIKI CONFIDENTIALITY NOTICE:</span><br><span lang=\"EN-NZ\" style=\" font-size: 9pt; font-family: Arial, sans-serif; color: rgb(31, 73, 125);\">This email and its contents are confidential and may also be privileged.&nbsp;If you have received it in error, you may not read, use, copy or disclose this email or its attachments. If you are the intended recipient,&nbsp;you must not disclose or circulate this email or its content other than in accordance with any confidentiality arrangements between us.</span></p></div>"
      }
    }
  }

  private onSuccess(data:any, headers:any) {
    this.data = data;
  }

  private onError(error: any) {
    this.infoMsg = error.message;
  }

  onNEReposneSuccess(res:any, headers:any) {
    let obj = [];
    obj.push(res)
    this.overviewgriddetail.dataSource = obj;
    this.overviewgriddetail.refresh();
  }
  public update() {
    this.isUpdateClicked = true;
    this.gridInstance!.endEdit();
  }
  public cancel() {
    this.isUpdateClicked = false;
    this.gridInstance!.closeEdit();
  }

  clickHandler(args: ClickEventArgs): void {
    if (args.item.id === 'cd') {
      if(this.portdetails === null || this.portdetails === undefined) {
        alert("Please select any row")
      }else{
        this.dialogHeader = "Circuit Diagram";
        this.dialogBtnClick(true);
        this.tpClick = false;

      }
    }else  if (args.item.id === 'createtickets') {
      this.services = this.gridInstance!.getSelectedRecords();
      if( this.services.length > 0) {
        this.services =    this.services.reduce((accumalator, current) => {
          if(!accumalator.some(obj => obj.serviceName === current.servicename)) {
            accumalator.push(current);
          }
          return accumalator;
        },[] as any[]).sort((a, b) => (a.servicename > b.servicename ? -1 : 1));

        this.serviceDialog.show(true);
      }else{
        alert("Please select any row")
      }
    }else  if (args.item.id === 'ticketsstatus') {
      this.correlationService.findTickets({
      }).subscribe(
        (res: HttpResponse<Timeline[]>) => this.onTicketStatusSuccess(res.body, res.headers)
      );
    }
  }

  private onTicketStatusSuccess(data:any, headers:any) {
    this.ticketReponse = data;
    this.ticketStatusDialog.show(true);
  }


  dataURItoBlob(dataURI: string): Blob {
    const byteString = atob(dataURI.split(',')[1]);
    const mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];
    const ab = new ArrayBuffer(byteString.length);
    const ia = new Uint8Array(ab);
    for (let i = 0; i < byteString.length; i++) {
      ia[i] = byteString.charCodeAt(i);
    }
    return new Blob([ab], { type: mimeString });
  }

}

import { Component, OnInit, ViewChild } from '@angular/core';
import SharedModule from '../../../shared/shared.module';
import {LsiodfportService} from './lsiodfport.service'
import {
  FilterService,
  GridComponent,
  VirtualScrollService,
  GroupSettingsModel,
  RowDDService,
  SelectionService,
  ResizeService,
  ColumnMenuService,
  PdfExportService,
  SortService,
  ContextMenuService,
  ExcelExportService,
} from '@syncfusion/ej2-angular-grids';
import {ColumnModel, ContextMenuItem, Group, GridModule, PageService, ReorderService, GroupService, ToolbarService, ColumnChooserService } from '@syncfusion/ej2-angular-grids';
import {DialogModule, DialogComponent} from "@syncfusion/ej2-angular-popups";
import {EmitType} from "@syncfusion/ej2-base";
import { DropDownListComponent } from '@syncfusion/ej2-angular-dropdowns';
import { ClickEventArgs } from '@syncfusion/ej2-navigations'
import { ButtonModule } from '@syncfusion/ej2-angular-buttons';
import { ProgressButtonModule } from '@syncfusion/ej2-angular-splitbuttons';


@Component({
  selector: 'jhi-lsiodfport',
  styleUrls: ['lsiodfport.css'],
  templateUrl: './lsiodfport.component.html',
  imports: [SharedModule, DialogModule, ButtonModule, GridModule, ProgressButtonModule],
  providers: [LsiodfportService, FilterService,VirtualScrollService, SelectionService , SortService
    , RowDDService, ExcelExportService, PdfExportService, ContextMenuService
    ,ResizeService , ColumnMenuService, PageService, ReorderService, GroupService, ToolbarService, ColumnChooserService],
})
export class LsiodfportComponent implements OnInit {

  constructor(private correlationService: LsiodfportService) {}

  public infoMsg: string = "";
  progressLoader: boolean = true;

  public toolbar: Object[] | undefined;
  public groupOptions: GroupSettingsModel | undefined;
  public group: Group | undefined;
  public dReady: boolean = false;
  public detailsview:boolean = false;


  public data: Object | undefined;
  public filterSettings: Object | undefined;
  public selectionSettings: Object | undefined;
  @ViewChild('sample')
  public listObj: DropDownListComponent | undefined;
  @ViewChild('overviewgrid2')
  public gridInstance : GridComponent | undefined ;
  @ViewChild('alertDialog')
  public alertDialog: DialogComponent | undefined;
  public hidden: Boolean = false;
  public alertHeader: string = 'Copy with Header';
  public alertWidth: string = '300px';
  public target: string = '.control-section';
  public alertContent: string = 'Atleast one row should be selected to copy with header';
  public showCloseIcon: Boolean = false;
  public alertDlgBtnClick = () => {
    this.alertDialog!.hide();
  }
  public alertDlgButtons: Object[] = [{ click: this.alertDlgBtnClick.bind(this), buttonModel: { content: 'OK', isPrimary: true } }];
  public animationSettings: Object = { effect: 'FlipYRight' };

  public pageOptions: Object = { pageCount: 10 };
  public selectionOptions: Object = { type: 'Multiple' };
  public customNetworkAttributes: Object =  {class: 'customnetworkcss'};

  public customPortAttributes: Object =  {class: 'customportcss'};
  public odfCustomAttributes: Object =  {class: 'odfcustomcss'};
  public patchCustomAttributes: Object =  {class: 'patchcustomcss'};
  public mmrCustomAttributes: Object =  {class: 'mmrcustomcss'};
  public fields: Object = { text: 'text', value: 'value' };
  public contextMenuItems: ContextMenuItem[] = ['AutoFit', 'AutoFitAll', 'SortAscending', 'SortDescending',
    'Copy',
    'ExcelExport', 'CsvExport', 'FirstPage', 'PrevPage',
    'LastPage', 'NextPage'];
  public columnMenuItems: Object[] = ['AutoFit', 'AutoFitAll', 'SortAscending', 'SortDescending','Group','Ungroup','Filter'
  ];

  public sortOptions: object= { columns: [{ field: 'portstatus', direction: 'Descending' },{ field: 'sitename', direction: 'Ascending' },
      { field: 'lsiodf', direction: 'Ascending' }] };


  public genericColumns: ColumnModel[] =  [
    {
      field: 'sitename',
      headerText: 'Site',
      width: 120,
      minWidth: 100,
      autoFit: true,
      clipMode: 'EllipsisWithTooltip'
    },
    {
      field: 'portstatus',
      headerText: 'Status',
      width: 120,
      minWidth: 100,
      autoFit: true,
      clipMode: 'EllipsisWithTooltip'
    } , {
      field: 'servicename',
      headerText: 'ServiceID',
      width: 120,
      minWidth: 100,
      autoFit: true,
      clipMode: 'EllipsisWithTooltip'
    }];

  public lsiodfColumns: ColumnModel[] =  [
    {
      field: 'lsi_odfcardtype',
      headerText: 'LSI ODF Card Type',
      width: 250,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip'
    },{
      field: 'lsi_roomlocation',
      headerText: 'LSI ODF Room Location',
      width: 250,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip'
    }, {
      field: 'lsi_shelf',
      headerText: 'LSI ODF Shelf',
      width: 200,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip'
    },  {
      field: 'lsi_position',
      headerText: 'Patch ODF Position',
      width: 200,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip'
    }, {
      field: 'lsi_port',
      headerText: 'LSI ODF Port',
      width: 200,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip'
    },{
      field: 'lsi_connector',
      headerText: 'LSI ODF Connector',
      width: 200,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip'
    },{
      field: 'lsiodf',
      headerText: 'LSI ODF ( Full  Name )',
      width: 400,
      minWidth: 300,
      isPrimaryKey: true,
      autoFit: true,
      clipMode: 'EllipsisWithTooltip'
    },{
      field: 'comment',
      headerText: 'LSI ODF Comment',
      width: 400,
      minWidth: 300,
      isPrimaryKey: true,
      autoFit: true,
      clipMode: 'EllipsisWithTooltip'
    },
  ];
  public patch1Columns: ColumnModel[] =  [
    {
      field: 'patch1cardtype',
      headerText: 'Line Side Card Type',
      width: 250,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip'
    },{
      field: 'patch1roomlocation',
      headerText: 'Line Side Card Room Location',
      width: 250,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip'
    }, {
      field: 'patch1_shelf',
      headerText: 'Line Side Card Shelf',
      width: 200,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip'
    },  {
      field: 'patch1_position',
      headerText: 'Line Side Card Position',
      width: 200,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip'
    }, {
      field: 'patch1_port',
      headerText: 'Line Side Card Port',
      width: 200,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip'
    },{
      field: 'patch1_connector',
      headerText: 'Line Side Card Connector',
      width: 200,
      minWidth: 60,
      isPrimaryKey: true,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip'
    },{
      field: 'patch1',
      headerText: 'Line Side Port ( Full  Name )',
      width: 400,
      minWidth: 300,
      isPrimaryKey: true,
      autoFit: true,
      clipMode: 'EllipsisWithTooltip'
    },
    {
      field: 'patch1_comment',
      headerText: 'Line Side Card Comment',
      width: 400,
      minWidth: 300,
      isPrimaryKey: true,
      autoFit: true,
      clipMode: 'EllipsisWithTooltip'
    },
  ];


  @ViewChild('defaultDialog')
  public defaultDialog: DialogComponent | undefined;
  public dialogHeader: string = 'Help';
  public dialogCloseIcon: Boolean = true;
  public dialogWidth: string = '1300px';
  public isModal: Boolean = true;
  public hide: any;
  public visible: Boolean = false;
  public togglecolumns: string[] =     ['Vendor' , 'Segment' , 'Client Port Room Location' ,'Client Port Card Type' , 'Client Port Shelf',
    'Client Port Slot' ,  'Client Port Position' , 'Client Port Port No.' , 'Client Port Direction' , 'Client Port Connector' ,
    'LSI ODF Card Type' , 'LSI ODF Room Location' , 'LSI ODF Shelf' ,   'LSI ODF Position' ,  'LSI ODF Port' , 'LSI ODF Connector',
    'Patch ODF Card Type' , 'Patch ODF Room Location' , 'Patch ODF Shelf' ,   'Patch ODF Position' ,  'Patch ODF Port' , 'Patch ODF Connector' ,
    'MMR ODF Card Type' , 'MMR ODF Room Location' , 'MMR ODF Shelf' ,   'MMR ODF Position' ,  'MMR ODF Port' , 'MMR ODF Connector'  ,

  ];

  public dlgButtonClick: EmitType<Object> = () => {
    this.defaultDialog!.hide();
  }
  public defaultDlgButtons: Object[] = [{ click: this.dlgButtonClick.bind(this), buttonModel: { content: 'Hide', isPrimary: true } }];


  public dialogBtnClick: EmitType<Object> = (args: any) => {

    this.defaultDialog!.show();
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
          this.alertDialog!.show();
        }
        break;
      case 'Copy':
        if(this.gridInstance!.getSelectedRecords().length>0) {
          this.gridInstance!.copy(false);
        } else {
          this.alertDialog!.show();
        }
        break;
    }
  }

  clickHandler(args: ClickEventArgs): void {
    if (args.item.id === 'Help') {
      this.dialogBtnClick()
    }
  }

  ngOnInit() {
    this.loadAll();
  }

  loadAll() {
    this.correlationService.findAllData().subscribe({
      next: res => {
        console.log('Status:', res.status);
        console.log('Headers:', res.headers);
        if (res.body !== null) {
          this.data = res.body;
          this.progressLoader = false;

          this.filterSettings = { type: "CheckBox" };
          this.groupOptions = { showGroupedColumn: false  };
          this.toolbar = ['ExcelExport',  'CsvExport' , 'Print' , 'ColumnChooser' , 'Search' ];
          if(this.gridInstance){
            this.gridInstance!.on('data-ready', () => {
              this.dReady! = true;
            })
          }
          this.selectionSettings = {persistSelection: true, type: "Multiple", checkboxOnly: true };

        }
      },
      error: err => {
        this.infoMsg = err.message;
      },
    });
  }

}

import { Component, OnInit, ViewChild } from '@angular/core';
import SharedModule from '../../../shared/shared.module';
import {DaccorrelationService} from './daccorrelation.service'
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
import { EditSettingsModel } from '@syncfusion/ej2-angular-treegrid';
import {EditService, ColumnModel, ContextMenuItem, GridModule, PageService, ReorderService, GroupService, ToolbarService, ColumnChooserService } from '@syncfusion/ej2-angular-grids';
import {DialogModule} from "@syncfusion/ej2-angular-popups";
import { ClickEventArgs } from '@syncfusion/ej2-navigations'
import { ButtonModule } from '@syncfusion/ej2-angular-buttons';
import { ProgressButtonModule } from '@syncfusion/ej2-angular-splitbuttons';
import {DaccorrelationData} from "./daccorrelation.model";

@Component({
  selector: 'jhi-daccorrelation',
  styleUrls: ['daccorrelation.css'],
  templateUrl: './daccorrelation.component.html',
  imports: [SharedModule, DialogModule, ButtonModule, GridModule, ProgressButtonModule],
  providers: [EditService, ToolbarService,DaccorrelationService, FilterService,VirtualScrollService, SelectionService , SortService
    , RowDDService, ExcelExportService, PdfExportService, ContextMenuService
    ,ResizeService , ColumnMenuService, PageService, ReorderService, GroupService, ToolbarService, ColumnChooserService],
})
export class DaccorrelationComponent implements OnInit {

  constructor(private correlationService: DaccorrelationService) {}

  public infoMsg: string = "";
  progressLoader: boolean = true;

  public isUpdateClicked: boolean = false;

  public data: Object | undefined;

  @ViewChild('daggrid')
  public gridInstance : GridComponent | undefined ;

  @ViewChild('daggrid')
  grid: GridComponent | undefined;

  public contextMenuItems: ContextMenuItem[] = ['AutoFit', 'AutoFitAll', 'SortAscending', 'SortDescending',
    'ExcelExport', 'CsvExport',  'FirstPage', 'PrevPage',
    'LastPage', 'NextPage'];
  public columnMenuItems: Object[] = ['AutoFit', 'AutoFitAll', 'SortAscending', 'SortDescending','Group','Ungroup','Filter'
  ];
  public togglecolumns: string[] =     [ 'Device', 'Device ' ,'RR#' , 'RR #' , 'Shelf#' , 'Shelf #' , 'Slot#', 'Slot #' ,
    'Port#' , 'Port #' ,  'Connector' , 'Connector '
  ];

  public fromAttributes: Object =  {class: 'fromcss'};
  public toAttributes: Object =  {class: 'tocss'};
  public detailsview:boolean = false;

  public dacdata:DaccorrelationData = new DaccorrelationData();
  public selectionOptions: Object = { type: 'Multiple' };

  public pageOptions: Object = { pageCount: 10 };

  public groupOptions: GroupSettingsModel = { showGroupedColumn: false  };

  public toolbarOptions: Object[] | undefined;
  public editSettings: EditSettingsModel | undefined;
  public filterSettings: Object | undefined;

  public fromcolumns: ColumnModel[] =  [
    {
      field: 'fromtblid',
      headerText: 'FromTblId',
      width: 120,
      minWidth: 60,
      autoFit: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false,
      visible: false,
      isPrimaryKey: true
    },
    {
      field: 'fromserviceid',
      headerText: 'ServiceId',
      width: 120,
      minWidth: 60,
      autoFit: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: true

    },
    {
      field: 'fromdls',
      headerText: 'DLS',
      width: 120,
      minWidth: 60,
      autoFit: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: true
    },
    {
      field: 'fromfrequency',
      headerText: 'Frequency',
      width: 140,
      minWidth: 100,
      autoFit: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: true
    },
    {
      field: 'fromsite',
      headerText: 'Site',
      width: 100,
      minWidth: 80,
      autoFit: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: true
    },
    {
      field: 'fromdevice',
      headerText: 'Device',
      width: 100,
      minWidth: 80,
      autoFit: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: true
    },
    {
      field: 'fromrr',
      headerText: 'RR#',
      width: 120,
      minWidth: 80,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: true
    },
    {
      field: 'fromshelf',
      headerText: 'Shelf#',
      width: 120,
      minWidth: 40,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: true
    },
    {
      field: 'fromslot',
      headerText: 'Slot#',
      width: 120,
      minWidth: 50,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: true
    },
    {
      field: 'fromport',
      headerText: 'Port#',
      width: 120,
      minWidth: 80,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: true
    },
    {
      field: 'fromconnectortype',
      headerText: 'Connector',
      width: 120,
      minWidth: 80,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: true
    },
    {
      field: 'fromname',
      headerText: 'FullName',
      width: 120,
      minWidth: 80,
      autoFit: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: true
    },
    {
      field: 'fromcomment',
      headerText: 'Comment',
      width: 120,
      minWidth: 60,
      autoFit: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: true
    },
    {
      field: 'fromportstatus',
      headerText: 'Status',
      width: 120,
      minWidth: 60,
      autoFit: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false,
      visible: false,
    }
  ];

  public tocolumns: ColumnModel[] =  [
    {
      field: 'totblid',
      headerText: 'ToTblId',
      width: 120,
      minWidth: 60,
      autoFit: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false,
      visible: false,
      isPrimaryKey: true
    },
    {
      field: 'toserviceid',
      headerText: 'ServiceId',
      width: 120,
      minWidth: 60,
      autoFit: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: true
    },
    {
      field: 'todls',
      headerText: 'DLS',
      width: 120,
      minWidth: 60,
      autoFit: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: true
    },
    {
      field: 'tofrequency',
      headerText: 'Frequency',
      width: 140,
      minWidth: 100,
      autoFit: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: true
    },
    {
      field: 'tosite',
      headerText: 'Site',
      width: 100,
      minWidth: 80,
      autoFit: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: true
    },
    {
      field: 'todevice',
      headerText: 'Device ',
      width: 120,
      minWidth: 80,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: true
    },
    {
      field: 'torr',
      headerText: 'RR #',
      width: 120,
      minWidth: 80,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: true
    },
    {
      field: 'toshelf',
      headerText: 'Shelf #',
      width: 120,
      minWidth: 80,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: true
    },
    {
      field: 'toslot',
      headerText: 'Slot #',
      width: 120,
      minWidth: 80,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: true
    },
    {
      field: 'toport',
      headerText: 'Port #',
      width: 120,
      minWidth: 80,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: true
    },
    {
      field: 'toconnectortype',
      headerText: 'Connector ',
      width: 120,
      minWidth: 80,
      autoFit: true,
      visible: this.detailsview,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: true
    },
    {
      field: 'toname',
      headerText: 'FullName',
      width: 120,
      minWidth: 80,
      autoFit: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: true
    },
    {
      field: 'tocomment',
      headerText: 'Comment',
      width: 120,
      minWidth: 60,
      autoFit: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: true
    },
    {
      field: 'toportstatus',
      headerText: 'Status',
      width: 120,
      minWidth: 60,
      autoFit: true,
      clipMode: 'EllipsisWithTooltip',
      allowEditing: false,
      visible: false,
    }
  ];

  slide(): void {
    if(!this.detailsview) {
      this.gridInstance!.showColumns(this.togglecolumns);
      this.detailsview = true;
    } else {
      this.gridInstance!.hideColumns(this.togglecolumns);
      this.detailsview = false;
    }
  }

  clickHandler(args: ClickEventArgs): void {
    if(args.item.id === 'Grid_update') {
      this.update();

    }
    if(args.item.id === 'Grid_cancel') {
      this.cancel();
    }
    switch (args.item.text) {
      case 'Excel Export':
        this.gridInstance!.excelExport();
        break;
      case 'CSV Export':
        this.gridInstance!.csvExport();
        break;
    }
  }

  public update() {
    this.isUpdateClicked = true;
    this.gridInstance!.endEdit();
  }
  public cancel() {
    this.isUpdateClicked = false;
    this.gridInstance!.closeEdit();
  }

  toolbarClick(args: ClickEventArgs): void {

    switch (args.item.text) {

      case 'Excel Export':
        this.gridInstance!.excelExport();
        break;
      case 'CSV Export':
        this.gridInstance!.csvExport();
        break;
    }
  }

  public  actionComplete(args: any): void {

    const isAdd = args.action === 'add';
    const isEdit = args.action === 'edit';

    if (isEdit && args.data) {
      args.data.toserviceid = args.data.fromserviceid;
      if (args.data.fromserviceid != undefined || args.data.fromserviceid != '') {
        args.data.fromportstatus = 'allocated';
      } else {
        args.data.fromportstatus = 'free';
      }
      if (args.data.toserviceid != undefined || args.data.toserviceid != '') {
        args.data.toportstatus = 'allocated';
      } else {
        args.data.toportstatus = 'free';
      }
      this.dacdata = args.data;

      setTimeout(() => {
        this.updateOrInsertDac("update", this.dacdata);
      }, 0);
    }else if (isAdd && args.rows[0].data) {
      const row = args.rows[0].data;
      row.toserviceid = row.fromserviceid;

      if (row.fromserviceid != undefined && row.fromserviceid !== '') {
        row.fromportstatus = 'allocated';
      } else {
        row.fromportstatus = 'free';
      }

      if (row.toserviceid != undefined && row.toserviceid !== '') {
        row.toportstatus = 'allocated';
      } else {
        row.toportstatus = 'free';
      }

      if (row.fromname) {
        const fromparts = row.fromname.split('/');
        row.fromsite = fromparts[0] || null;
        row.fromrr = fromparts[2] || null;
        row.fromdevice = fromparts[1] || null;

        const shelfRaw = fromparts[3];
        if (shelfRaw) {
          const colonIndex = shelfRaw.indexOf(':');
          row.fromshelf = shelfRaw.substring(2, colonIndex !== -1 ? colonIndex : undefined);
        } else {
          row.fromshelf = null;
        }

        const slotIndex = row.fromname.indexOf('Sl_');
        if (slotIndex !== -1) {
          const after = row.fromname.substring(slotIndex + 3);
          row.fromslot = after.split(':')[0];
        } else {
          row.fromslot = null;
        }

        const portIndex = row.fromname.indexOf('P_');
        if (portIndex !== -1) {
          const after = row.fromname.substring(portIndex + 2);
          row.fromport = after.split(':')[0];
        } else {
          row.fromport = null;
        }

        const cIndex = row.fromname.indexOf('C_');
        if (cIndex !== -1) {
          row.fromconnectortype = row.fromname.substring(cIndex + 2);
        } else {
          row.fromconnectortype = null;
        }
      }

      if (row.toname) {
        const toparts = row.toname.split('/');
        row.tosite = toparts[0] || null;
        row.torr = toparts[2] || null;
        row.todevice = toparts[1] || null;

        const shelfRaw = toparts[3];
        if (shelfRaw) {
          const colonIndex = shelfRaw.indexOf(':');
          row.toshelf = shelfRaw.substring(2, colonIndex !== -1 ? colonIndex : undefined);
        } else {
          row.toshelf = null;
        }

        const slotIndex = row.toname.indexOf('Sl_');
        if (slotIndex !== -1) {
          const after = row.toname.substring(slotIndex + 3);
          row.toslot = after.split(':')[0];
        } else {
          row.toslot = null;
        }

        const portIndex = row.toname.indexOf('P_');
        if (portIndex !== -1) {
          const after = row.toname.substring(portIndex + 2);
          row.toport = after.split(':')[0];
        } else {
          row.toport = null;
        }

        const cIndex = row.toname.indexOf('C_');
        if (cIndex !== -1) {
          row.toconnectortype = row.toname.substring(cIndex + 2);
        } else {
          row.toconnectortype = null;
        }
      }
      this.dacdata = args.rows[0].data;

      setTimeout(() => {
        this.updateOrInsertDac("insert", this.dacdata);
      }, 0);
    }

  }

  private updateOrInsertDac(action: string, data: any): void {

    if(action == "update") {
      this.correlationService.updateData(data).subscribe({
        next: res => {
          console.log('Status:', res.status);
          console.log('Headers:', res.headers);
          this.infoMsg = "Update successfully.";

          this.loadAll();
        },
        error: err => {
          this.infoMsg = err.message;
        },
      });
    }
    if(action == "insert"){
      this.correlationService.insertData(data).subscribe({
        next: res => {
          console.log('Status:', res.status);
          console.log('Headers:', res.headers);
          this.infoMsg = "Add successfully.";

          this.loadAll();
        },
        error: err => {
          this.infoMsg = err.message;
        },
      });
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

          this.toolbarOptions = [ 'ExcelExport', 'CsvExport',  'Print',
            'ColumnChooser','Search', 'Add', 'Edit', 'Update', 'Cancel'

          ];
          this.filterSettings = { type: 'CheckBox', hierarchyMode: 'Both' };
          this.editSettings = { allowEditing: true, allowAdding: true, allowDeleting: true};
        }
      },
      error: err => {
        this.infoMsg = err.message;
      },
    });
  }

}

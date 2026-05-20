import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {CapPlanningServ} from './capacityplanning.service'
import {capplanModel} from "./capacityplanning.model";
import {
  FieldListService,
  IDataSet,
  PivotFieldListAllModule,
  PivotViewAllModule,
  PivotViewComponent,
  BeginDrillThroughEventArgs
} from '@syncfusion/ej2-angular-pivotview'
import {DataSourceSettingsModel} from '@syncfusion/ej2-pivotview/src/model/datasourcesettings-model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'jhi-cp',
  styleUrl:  './capacityplanning.component.scss',
  templateUrl: './capacityplanning.component.html',
  imports: [
    PivotViewAllModule,
    PivotFieldListAllModule, CommonModule],
  providers: [CapPlanningServ, FieldListService],
})
export class CapacityplanningComponent implements OnInit{

  constructor(private capPlanningServ: CapPlanningServ) {}

  @ViewChild('pivotview')
  public pivotGridObj!: PivotViewComponent;

  public infoMsg!: string;
  public dataSourceSettings?: DataSourceSettingsModel;
  public width?: string;
  public capData: capplanModel[] = []

  ngOnInit(){
    this.loadCapPlanning();
  }

  loadCapPlanning(){
    const persistenceKey = 'pivotviewPivotView';
    const savedState = localStorage.getItem(persistenceKey);

    this.capPlanningServ.findCapplanning().subscribe(
      (res: HttpResponse<capplanModel[]>) => this.onSuccess(res.body),
      (res: HttpErrorResponse) => this.onError(res.message),
    );

    setTimeout(() => {
      if (savedState) {
        this.dataSourceSettings = {
          dataSource: this.capData as IDataSet[]
        };
      } else {

        this.dataSourceSettings = {
          dataSource:
            this.capData as IDataSet[],
          expandAll: false,

          columns: [
            {name: 'capacity', caption: 'Capacity'},
            {name: 'portstatus', caption: 'Status Count'}
          ],

          rows: [
            {name: 'segmentnm', caption: 'Segments'},
            {name: 'sitename', caption: 'Site Name'},
            {name: 'capacity', caption: 'Capacity'},
            {name: 'portstatus', caption: 'Status Count'},
            {name: 'cardtype', caption: 'Card'}
          ],

          values: [
            {name: 'TotalPorts', caption: 'Total Ports', type: 'CalculatedField'},
            {name: 'TotalCapacity', caption: 'Total Capacity', type: 'CalculatedField'}
          ],

          calculatedFieldSettings: [
            {name: 'TotalCapacity', formula: '"Sum(capacity)"'},
            {name: 'TotalPorts', formula: '"Sum(portstatus)"'}
          ],

          formatSettings: [
            {name: 'TotalCapacity', format: '0 GB'}
          ],

          filters: [
            {name: 'networktype', caption: 'NetworkType'},
            {name: 'frequency', caption: 'Frequency'},
            {name: 'route', caption: 'Route'}, {name: 'segmentnm', caption: 'Segments'},
            {name: 'sitename', caption: 'Sites'},
            {name: 'capacity', caption: 'Capacity'},
            {name: 'cardtype', caption: 'Cards'},
            {name: 'vendor', caption: 'Vendor'}, {name: 'servicename', caption: 'Service'},
            {name: 'customername', caption: 'Customer'}
          ],

          filterSettings: [
            {name: 'segmentnm', type: 'Exclude', items: ['null']},
            {name: 'capacity', type: 'Exclude', items: ['null', '0']},
            {name: 'networktype', type: 'Exclude', items: ['null']},
            {name: 'servicename', type: 'Exclude', items: ['null']}
          ],

          conditionalFormatSettings: [
            {
              measure: 'TotalPorts', value1: 3, conditions: 'LessThan',

              style: {backgroundColor: '#59b8fd', color: 'black', fontFamily: 'Tahoma', fontSize: '12px'}}

          ]
        };
      }
      this.width = "100%";

    }, 3000);
  }

  aggregateCell(args: any){

    this.removeUnwantedAggregation(args);

    let daccount: number = 0;
    let offnetcount: number = 0;

    const validCellSets = args.cellSets.filter((x: any) => x && x.portname);

    if (args.columnCellType === 'value' && args.fieldName === 'TotalCapacity') {
      daccount = validCellSets
        .filter((x: any) => x.portname.includes('DAC') || x.portname.includes('AOC'))
        .reduce((sum: number, obj: any) => sum + (obj.capacity ?? 0), 0);

      offnetcount = validCellSets
        .filter((x: any) => x.portname.includes('OFF-NET'))
        .reduce((sum: number, obj: any) => sum + (obj.capacity ?? 0), 0);

    } else if ((args.columnCellType === 'grandTotal' || args.columnCellType === 'subTotal')
      && args.fieldName === 'TotalCapacity') {

      daccount = validCellSets
        .filter((x: any) => x.portname.includes('DAC') || x.portname.includes('AOC'))
        .reduce((sum: number, obj: any) => sum + (obj.capacity ?? 0), 0);

      offnetcount = validCellSets
        .filter((x: any) => x.portname.includes('OFF-NET'))
        .reduce((sum: number, obj: any) => sum + (obj.capacity ?? 0), 0);

    } else {
      daccount = validCellSets
        .filter((x: any) => x.portname.includes('DAC') || x.portname.includes('AOC'))
        .length;

      offnetcount = validCellSets
        .filter((x: any) => x.portname.includes('OFF-NET'))
        .length;
    }

    if (args.value != null) {
      const valueSortSegment = args.row?.valueSort?.axis === 'segmentnm';
      const isGrandTotalRow = args.rowCellType === 'grandTotal';
      const isGrandTotalCol = args.columnCellType === 'grandTotal';

      if (valueSortSegment || (isGrandTotalRow && isGrandTotalCol) || isGrandTotalRow) {
        args.value = ((args.value - daccount - offnetcount) / 4) + (daccount / 2) + (offnetcount / 2);
      } else {
        args.value = ((args.value - daccount - offnetcount) / 2) + daccount + offnetcount;
      }
    }

    this.capData = args;
  }

  beginDrillThrough(args: BeginDrillThroughEventArgs) {

    this.removeUnwantedCapacity(args);

    if (args.gridObj) {
      const gridColumns = args.cellInfo.gridColumns as { field: string; visible?: boolean; width?: number }[];
      const portCol = gridColumns.find(obj => obj.field === 'portname');
      if(portCol){
        portCol.visible = true;
        portCol.width = 350;
      }
      const odfCol = gridColumns.find(obj => obj.field === 'lsiodf');
      if(odfCol){
        odfCol.visible = true;
        odfCol.width = 350;
      }

    }
  }

  removeUnwantedAggregation(args: any){
    let columns = args.column.valueSort.levelName.split(".");
    columns.forEach((column: any) => {
      let index: any = args.cellSets.findIndex(
          (myObj: { capacity: { toString: () => any; }; }) => myObj.capacity.toString() === column) -1;
      if(index > -1)
        args.cellSets.splice(index, 1 )

      index = undefined;
      index = args.cellSets.findIndex(
          (myObj: { portstatus: any; }) => myObj.portstatus === column) -1;
      if(index > -1)
        args.cellSets.splice(index, 1 )
    });
  }

  removeUnwantedCapacity(args: BeginDrillThroughEventArgs){
    let index = args.cellInfo.rawData.findIndex(
      myObj => myObj.capacity.toString() === args.cellInfo.columnHeaders) -1;
    if(index > -1)
      args.cellInfo.rawData.splice(index, 1 )
  }

  private onSuccess(body: any) {
    //console.log("=========================================================");
    //console.log(body); //185 list items.
    this.capData = body;
    const keysToClean = ['segmentnm', 'servicename', 'networktype', 'capacity'];
    this.capData = (body as any[]).map(item => {
      const copy = { ...item };
      keysToClean.forEach(key => {
        const value = copy[key];
        if (value === null) {
          delete copy[key];
        }
      });
      return copy;
    });
  }

  private onError(message: any) {
    this.infoMsg = message;
  }


}


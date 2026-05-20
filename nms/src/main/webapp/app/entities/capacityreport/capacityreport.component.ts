import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {ReactiveFormsModule, FormBuilder, FormGroup, FormControl} from '@angular/forms';
import {
  PieSeriesService,
  AccumulationLegendService,
  AccumulationTooltipService,
  AccumulationAnnotationService,
  AccumulationChartModule,
  AccumulationChart,
  AccumulationDataLabelService,
  AccumulationChartComponent
} from '@syncfusion/ej2-angular-charts'
import {
  IDataOptions,
  FieldListService,
  CalculatedFieldService,
  ToolbarService,
  ConditionalFormattingService,
  ToolbarItems,
  DisplayOption,
  PivotViewComponent, DrillThroughService, PivotChartService, BeginDrillThroughEventArgs
} from '@syncfusion/ej2-angular-pivotview'
import {ChartSettings} from '@syncfusion/ej2-pivotview/src/pivotview/model/chartsettings';
import {GridSettings} from "@syncfusion/ej2-pivotview/src/pivotview/model/gridsettings";
import {ClientportService} from "../correlation/clientport/clientport.service";
import {ClientportData} from "../correlation/clientport/clientport.model";
import {Grid, Sort, Group, Filter} from "@syncfusion/ej2-grids";
import {ManagereservedcapService, ManagereservedcapStatus} from "../managereservedcap/managereservedcap.service";
import {CapacityRepLabel} from "./capacityreport.service";
import {CapacityreportService} from "./capacityreport.service";
import {Observable, forkJoin} from "rxjs";
import * as XLSX from 'xlsx';
import {CommonModule} from '@angular/common';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {PivotViewModule} from '@syncfusion/ej2-angular-pivotview';

interface FormattedData {
  segmentOrFiberPair: string | null;
  dlsName: string | null;
  litData: string | null;
  reservedData: number | null;
  litfp: number | null;
  soldpf: number | null;
  avimfp: number | null;
  reservedfp: number | null;
  estfp: number | null;
  avfufp: number | null;
  totalavai: number | null;
  backgColor: string;
}

interface FormattedPieData {
  segmentOrFiberPair: string | null;
  sold: number | null;
  reserved: number | null;
  availableforsale: number | null;
  estimatedCap: number | null;
}

interface NrmsCapacityAllocated {
  segmentnm: string;
  totalallocated: string,
  capacityallocated: string,
  dls: string
}

interface NrmsCapacity {
  segmentnm: string;
  value: string;
  fieldname: string;
  capacity: number;
  dls: string;
}

interface ReservedCapacity {
  dlsName: string;
  total: number;
}

@Component({
  selector: 'jhi-capacityreport',
  styleUrl: './capacityreport.scss',
  templateUrl: './capacityreport.component.html',
  standalone: true,
  imports: [ReactiveFormsModule, AccumulationChartModule, CommonModule, FormsModule, PivotViewModule],
  providers: [PivotChartService, CalculatedFieldService, ToolbarService, ConditionalFormattingService, FieldListService, DrillThroughService,
    PieSeriesService, AccumulationLegendService, AccumulationTooltipService, AccumulationDataLabelService,
    AccumulationAnnotationService],
})

export class CapacityreportComponent implements OnInit {
  updateForm!: FormGroup;
  @ViewChild('pivotview')
  pivotGridObj!: PivotViewComponent;
  public pivotData: any = [];
  insertflag: boolean = true;
  dataSource!: IDataOptions;
  gridSettings!: GridSettings;
  toolbarOptions!: ToolbarItems[];
  chartSettings!: ChartSettings;
  displayOption!: DisplayOption;
  segmentFiberPairTotals: { [key: string]: number } = {};
  segmentFiberPairAllocatedTotals: { [key: string]: number } = {};

  segmentFiberPaiResrevedTotals: Record<string, number> = {};
  private dataToInserts: NrmsCapacity[] = [];
  private dataToInsertsAllocated: NrmsCapacityAllocated[] = [];
  public transformedDataPieChart: any;
  gridLoaded: boolean = false;
  hasAuthority: boolean = false;
  formattedData: FormattedData[] = [];
  reservedCapacityObject: ReservedCapacity[] = [];
  reservedCapacityCounts: ReservedCapacity[] = [];
  nrmsCapacityObject: NrmsCapacity[] = [];
  isModalOpen = false;
  capacityData: any;
  dlsEstimatedData: any;
  loading: boolean = false;
  formattedPieData: FormattedPieData[] = [];
  legendSettings = {
    visible: true,
    position: 'Bottom'
  };
  datalabel = {
    visible: true,
    position: 'Inside',
    template: '<div>${point.y}%</div>'
  };
  colorPalette = ['#2e8bc0', '#ffa384', '#81b622'];
  isVisible: boolean = false;
  isLoading: boolean = true;

  constructor(private http: HttpClient,
              private clientportservice: ClientportService,
              private capacityreportservice: CapacityreportService,
              private managereservedcapService: ManagereservedcapService,
              private fb: FormBuilder
  ) {
  }

  ngOnInit(): void {

    this.displayOption = {view: 'Both'} as DisplayOption;

    this.gridSettings = {
      columnWidth: 20,
      rowHeight: 40,
      allowResizing: true
    } as GridSettings;

    this.chartSettings = {
      title: 'Capacity Analysis',
      chartSeries: {type: 'StackingColumn'},
      tooltip: {template: '<b><span class="wrap">${valueField}: ${value}</span></b>'}
    } as ChartSettings;

    this.toolbarOptions = [
      'Grid', 'Chart', 'Export', 'SubTotal', 'GrandTotal', 'ConditionalFormatting', 'FieldList'] as ToolbarItems[];

    this.dataSource = {
      dataSource: [],
      expandAll: true,
      enableSorting: true,
      showRowGrandTotals: false,
      rows: [{name: 'segmentnm', caption: 'Segments'}, {
        name: 'sitename',
        caption: 'Site Name'
      }, {name: 'capacity', caption: 'Capacity'}, {
        name: 'portstatus',
        caption: 'Status Count'
      }, {name: 'cardtype', caption: 'Card'}],
      columns: [{name: 'capacity', caption: 'Capacity'}, {name: 'portstatus', caption: 'Status Count'}],
      values: [{name: 'TotalPorts', caption: 'Total Ports', type: 'CalculatedField'}, {
        name: 'TotalCapacity',
        caption: 'Total Capacity',
        type: 'CalculatedField'
      }],
      calculatedFieldSettings: [{name: 'TotalCapacity', formula: '"Sum(capacity)"'}, {
        name: 'TotalPorts',
        formula: '"Sum(portstatus)"'
      }],
      formatSettings: [{name: 'TotalCapacity', format: '0 GB'}],
      filters: [{name: 'networktype', caption: 'NetworkType'}, {
        name: 'frequency',
        caption: 'Frequency'
      }, {name: 'route', caption: 'Route'}, {name: 'segmentnm', caption: 'Segments'}, {
        name: 'sitename',
        caption: 'Sites'
      }, {name: 'capacity', caption: 'Capacity'}, {name: 'cardtype', caption: 'Cards'},
        {name: 'vendor', caption: 'Vendor'}, {name: 'servicename', caption: 'Service'}, {
          name: 'customername',
          caption: 'Customer'
        }],
      filterSettings: [{name: 'segmentnm', type: 'Exclude', items: ['null']}, {
        name: 'capacity',
        type: 'Exclude',
        items: ['null', '0']
      },
        {name: 'networktype', type: 'Exclude', items: ['null']},
        {name: 'servicename', type: 'Exclude', items: ['null']}],
      conditionalFormatSettings: [{
        measure: 'TotalPorts', value1: 3, conditions: 'LessThan',
        style: {
          backgroundColor: '#f48fb1',
          color: 'black',
          fontFamily: 'Tahoma',
          fontSize: '12px'
        }
      }
      ],

    };

    this.clientportservice.findAllData().subscribe(
      (res: HttpResponse<ClientportData>) => {
        console.log(res.body);
        this.onSuccess(res.body, res.headers)
      }
    );
    //this.fetchData();
    this.fetchEstimatedCapacityData();

    this.updateForm = new FormGroup({
      id: new FormControl(''),
      au_nz_fp1_dls16: new FormControl(''),
      nz_hi_fp1_dls02: new FormControl(''),
      nz_hi_fp1_dls13: new FormControl(''),
      nz_hi_fp1_dls11: new FormControl(''),
      au_hi_fp1_dls14: new FormControl(''),
      au_hi_fp2_dls54: new FormControl(''),
      hi_uf_fp1_dls27: new FormControl(''),
      hi_uf_fp1_dls67: new FormControl(''),
      hi_uf_fp2_dls57: new FormControl(''),
      hi_uf_fp3_dls: new FormControl(''),
    });
    this.loadGridData();

    this.fetchEstimatedCapacityData();

  }

  openModal() {
    this.isModalOpen = true;
  }

  closeModal() {
    this.isModalOpen = false;
  }

  togglePieChart(): void {
    this.isVisible = !this.isVisible;
  }

  updateAllFP() {
    if (this.updateForm.valid) {
      const formValues = this.updateForm.value;
      Object.keys(formValues).forEach((key) => {
        if (formValues[key] === null || isNaN(formValues[key]) || formValues[key] === '') {
          formValues[key] = 0;
        }
      });
      this.loading = true;
      this.capacityreportservice.updateEstimatedData(formValues).subscribe({
        next: (response) => {
          this.loading = false;
          window.location.reload();
        },
        error: (error) => {
          console.error('Error saving data:', error);
        }
      })
    } else {
      console.log("Form is invalid");
    }
    this.closeModal();
  }

  fetchEstimatedCapacityData(): void {
    this.capacityreportservice.getCapacityEstimatedData().subscribe(
      (res: HttpResponse<any>) => {
        this.capacityData = res.body;
        if (this.capacityData && this.capacityData.length > 0) {
          this.updateForm.patchValue(this.capacityData[0]);
        }
        this.dlsEstimatedData = Object.keys(this.capacityData[0])
          .filter(key => key.includes('dls'))
          .map(key => ({
            segment: key.split('_').slice(0, 2).join('-'),
            fiberpair: key.split('_')[2],
            dls: key.replace(/.*(?=dls)/, '').replace('dls', 'DLS'),
            value: parseInt(this.capacityData[0][key], 10)
          }));

        // console.log(this.dlsEstimatedData);
      },
      (error) => {
        console.error('Error occurred:', error);
      }
    );
  }


  fetchData(): void {
    forkJoin(
      this.managereservedcapService.getGridData(),
      this.capacityreportservice.getCapacityRepLabelData()
    ).subscribe(
      ([gridData, dlsCapacityData]: [
        HttpResponse<ManagereservedcapStatus[]>,
        HttpResponse<CapacityRepLabel[]>
      ]) => {
        const gridDataBody = gridData.body || [];
        const dlsCapacityDataBody = dlsCapacityData.body || [];
        const nrmsCapacityDataBody: NrmsCapacity[] = this.dataToInserts || [];
        const nrmsCapacityAllocatedDataBody: NrmsCapacityAllocated[] = this.dataToInsertsAllocated || [];
        this.processData(gridDataBody, dlsCapacityDataBody, nrmsCapacityDataBody, nrmsCapacityAllocatedDataBody);

      },
      (error) => {
        console.error('Error fetching data:', error);
      }
    );
  }


  loadGridData(): void {
    this.isLoading = true;
    setTimeout(() => {
      this.gridLoaded = true;
      this.fetchData();
      this.gridLoaded = true; // mark grid as ready
      this.isLoading = false; // hide loader
    }, 6000);

    this.hasAuthority = true;
  }

  collapseAll(): void {
    this.pivotGridObj.dataSourceSettings.expandAll = false;
    this.insertflag = false;
  }

  expandAll(): void {
    this.pivotGridObj.dataSourceSettings.expandAll = true;
    this.insertflag = false;
  }

  aggregateCell(args: any) {
    this.removeUnwantedAggregation(args);
    let daccount = 0;
    let offnetcount = 0;
    if (args.columnCellType === 'value' && args.fieldName === 'TotalCapacity') {
      daccount = args.cellSets.filter((x: any) => (x.portname.indexOf('DAC') >= 0 || x.portname.indexOf('AOC') >= 0))
        .reduce((daccount: any, obj: any) => daccount + obj.capacity, 0);
      offnetcount = args.cellSets.filter((x: any) => (x.portname.indexOf('OFF-NET') >= 0))
        .reduce((offnetcount: any, obj: any) => offnetcount + obj.capacity, 0);
    } else if ((args.columnCellType === 'grandTotal' || args.columnCellType === 'subTotal') && args.fieldName === 'TotalCapacity') {
      daccount = args.cellSets.filter((x: any) => (x.portname.indexOf('DAC') >= 0 || x.portname.indexOf('AOC') >= 0))
        .reduce((daccount: any, obj: any) => daccount + obj.capacity, 0);
      offnetcount = args.cellSets.filter((x: any) => (x.portname.indexOf('OFF-NET') >= 0))
        .reduce((offnetcount: any, obj: any) => offnetcount + obj.capacity, 0);
    } else {
      daccount = args.cellSets.filter((x: any) => (x.portname.indexOf('DAC') >= 0 || x.portname.indexOf('AOC') >= 0)).length;
      offnetcount = args.cellSets.filter((x: any) => (x.portname.indexOf('OFF-NET') >= 0)).length;
    }

    if (args.value && args.row.valueSort.axis === 'segmentnm') {
      if (args.value) {
        args.value = ((args.value - daccount - offnetcount) / 4) + (daccount / 2) + (offnetcount / 2);
      }
    } else {
      if (args.value) {
        if ((args.columnCellType === 'grandTotal' && args.rowCellType === 'grandTotal'))
          args.value = ((args.value - daccount - offnetcount) / 4) + (daccount / 2) + (offnetcount / 2);
        else if (args.rowCellType === 'grandTotal')
          args.value = ((args.value - daccount - offnetcount) / 4) + (daccount / 2) + (offnetcount / 2);
        // else
        args.value = ((args.value - daccount - offnetcount) / 2) + (daccount) + (offnetcount);
      }
    }

    this.pivotData = args;
    if (args.value != undefined) {
    }
    if (args.value != undefined && (args.row.level == 0) && (args.column.level == 0 || args.column.level == 1)) {

      if (args.column.level == 0 && args.fieldName == 'TotalCapacity') {
        if (args.fieldName == 'TotalCapacity' && args.columnCellType == 'grandTotal') {
          if (args.row.actualText != null) {
            this.dataToInserts.push({
              // id: this.generateUniqueId(),
              segmentnm: args.row.actualText,
              value: args.value,
              fieldname: args.fieldName,
              capacity: args.cellSets[0].capacity,
              dls: 'DLS' + args.row.actualText.split('-').pop()

            });
          }
        }
      }
      if (args.column.level == 1 && args.column.actualText == 'ALLOCATED' && args.fieldName == 'TotalCapacity') {

        this.dataToInsertsAllocated.push({
          //  id: this.generateAllocatedUniqueId(),
          segmentnm: args.row.actualText,
          totalallocated: args.value,
          capacityallocated: args.cellSets[0].capacity,
          dls: 'DLS' + args.row.actualText.split('-').pop()
        });
      }
    }

  }

  displayReport() {
    this.fetchData();
  }

  aggregateCellreturn(args: any) {
    this.removeUnwantedAggregation(args);

    let daccount = 0;
    let offnetcount = 0;
    if (args.columnCellType === 'value' && args.fieldName === 'TotalCapacity') {
      daccount = args.cellSets.filter((x: any) => (x.portname.indexOf('DAC') >= 0 || x.portname.indexOf('AOC') >= 0))
        .reduce((daccount: any, obj: any) => daccount + obj.capacity, 0);
      offnetcount = args.cellSets.filter((x: any) => (x.portname.indexOf('OFF-NET') >= 0))
        .reduce((offnetcount: any, obj: any) => offnetcount + obj.capacity, 0);
    } else if ((args.columnCellType === 'grandTotal' || args.columnCellType === 'subTotal') && args.fieldName === 'TotalCapacity') {
      daccount = args.cellSets.filter((x: any) => (x.portname.indexOf('DAC') >= 0 || x.portname.indexOf('AOC') >= 0))
        .reduce((daccount: any, obj: any) => daccount + obj.capacity, 0);
      offnetcount = args.cellSets.filter((x: any) => (x.portname.indexOf('OFF-NET') >= 0))
        .reduce((offnetcount: any, obj: any) => offnetcount + obj.capacity, 0);
    } else {
      daccount = args.cellSets.filter((x: any) => (x.portname.indexOf('DAC') >= 0 || x.portname.indexOf('AOC') >= 0)).length;
      offnetcount = args.cellSets.filter((x: any) => (x.portname.indexOf('OFF-NET') >= 0)).length;
    }

    if (args.value && args.row.valueSort.axis === 'segmentnm') {
      if (args.value) {
        args.value = ((args.value - daccount - offnetcount) / 4) + (daccount / 2) + (offnetcount / 2);
      }
    } else {
      if (args.value) {
        if ((args.columnCellType === 'grandTotal' && args.rowCellType === 'grandTotal'))
          args.value = ((args.value - daccount - offnetcount) / 4) + (daccount / 2) + (offnetcount / 2);
        else if (args.rowCellType === 'grandTotal')
          args.value = ((args.value - daccount - offnetcount) / 4) + (daccount / 2) + (offnetcount / 2);
        else
          args.value = ((args.value - daccount - offnetcount) / 2) + (daccount) + (offnetcount);
      }
    }

  }

  beginDrillThrough(args: BeginDrillThroughEventArgs) {

    this.removeUnwantedCapacity(args);
    if (args.gridObj) {

      var portCol = args.cellInfo?.gridColumns?.find(obj => obj.field === 'portname');
      if (portCol) {
        portCol.visible = true;
        portCol.width = 350;
      }
      var odfCol = args.cellInfo?.gridColumns?.find(obj => obj.field === 'lsiodf');
      if (odfCol) {
        odfCol.visible = true;
        odfCol.width = 350;
      }

      Grid.Inject(Sort, Filter, Group);
      let gridObj = args.gridObj;
      // @ts-ignore
      gridObj.columns.splice(2, 0, gridObj.columns.splice(0, 1)[0]);

      gridObj.allowSorting = true;
      gridObj.allowExcelExport = true;
      gridObj.sortSettings = {columns: [{field: 'portname', direction: 'Ascending'}]};


      gridObj.toolbar = ["ExcelExport", 'ColumnChooser', "Search"];
      gridObj.toolbarClick = (event) => {
        if (event.item.text === 'Excel Export')
          gridObj.excelExport();
      }
    }
  }

  removeUnwantedCapacity(args: BeginDrillThroughEventArgs) {
    let index = args.cellInfo.rawData.findIndex(
      myObj => myObj.capacity.toString() === args.cellInfo.columnHeaders) - 1;
    if (index > -1)
      args.cellInfo.rawData.splice(index, 1)
  }

  removeUnwantedAggregation(args: any) {
    let columns = args.column.valueSort.levelName.split(".");
    columns.forEach((column: any) => {
      let index = args.cellSets.findIndex(
        (myObj: any) => myObj.capacity.toString() === column) - 1;
      if (index > -1)
        args.cellSets.splice(index, 1)
      // let index: number;
      index = args.cellSets.findIndex(
        (myObj: any) => myObj.portstatus === column) - 1;
      if (index > -1)
        args.cellSets.splice(index, 1)
    });
  }


  processData(gridData: ManagereservedcapStatus[], data: CapacityRepLabel[], nrmsdata: NrmsCapacity[], nrmsallocated: NrmsCapacityAllocated[]): void {

    const aggregatedValues: { [key: string]: number } = {};
    nrmsdata.forEach(item => {
      const dls = item.dls;
      const value = parseFloat(item.value);
      if (!aggregatedValues[dls]) {
        aggregatedValues[dls] = 0;
      }
      aggregatedValues[dls] += value;
    });


    const combinedData = data.map(item => {
      const dls = item.dlsName;
      const aggregatedValue = aggregatedValues[dls] || 0;
      return {
        ...item,
        totalValue: aggregatedValue
      };
    });


    combinedData.forEach(item => {
      const segmentFiberPairKey = `${item.segmentName}-${item.fiberPairName}`;
      const totalValue = item.totalValue;
      if (!this.segmentFiberPairTotals[segmentFiberPairKey]) {
        this.segmentFiberPairTotals[segmentFiberPairKey] = 0;
      }
      this.segmentFiberPairTotals[segmentFiberPairKey] += totalValue;
    });


    // This code will all the vallues for PER FP SOLD
    const aggregatedAllocatedValues: { [key: string]: number } = {};
    nrmsallocated.forEach(item => {
      const dls = item.dls;
      const value = parseFloat(item.totalallocated);
      if (!aggregatedAllocatedValues[dls]) {
        aggregatedAllocatedValues[dls] = 0;
      }
      aggregatedAllocatedValues[dls] += value;
    });

    const combinedAllocatedData = data.map(item => {
      const dls = item.dlsName;
      const aggregatedAllocatedValue = aggregatedAllocatedValues[dls] || 0;
      return {
        ...item,
        totalValue: aggregatedAllocatedValue
      };
    });

    combinedAllocatedData.forEach(item => {
      const segmentFiberPairKey = `${item.segmentName}-${item.fiberPairName}`;
      const totalValue = item.totalValue;

      if (!this.segmentFiberPairAllocatedTotals[segmentFiberPairKey]) {
        this.segmentFiberPairAllocatedTotals[segmentFiberPairKey] = 0;
      }
      this.segmentFiberPairAllocatedTotals[segmentFiberPairKey] += totalValue;
    });


    this.nrmsCapacityObject = nrmsdata;
    const formattedData: FormattedData[] = [];
    let currentSegmentName: string | null = null;
    let currentFiberPairName: string | null = null;
    const seenFiberPairs: Set<string> = new Set();

    // this is to display the reserved capacity value in the table
    const reservedCapacities = gridData.filter(entry => entry.capacityName === 'Reserved Capacity');
    reservedCapacities.forEach(entry => {
      const dlsName = entry.dlsName;
      const total = Number(entry.total);

      const existingEntry = this.reservedCapacityCounts.find(item => item.dlsName === dlsName);

      if (existingEntry) {
        existingEntry.total += total;
      } else {
        this.reservedCapacityCounts.push({dlsName, total});
      }
    });

    this.reservedCapacityCounts.forEach((entry) => {
      this.reservedCapacityObject.push({
        dlsName: entry.dlsName,
        total: entry.total
      });
    });


    //   this code will get the PER fp Reservered
    const dlsReservedMap = new Map<string, number>(
      this.reservedCapacityCounts.map(item => [item.dlsName, item.total] as [string, number])
    );

    const reservedArray = data.map(item => {
      const allocated = dlsReservedMap.get(item.dlsName) ?? 0;
      return {
        ...item,
        totalallocated: allocated.toString()
      };
    });

    reservedArray.forEach(item => {
      const segmentFiberPairKey = `${item.segmentName}-${item.fiberPairName}`;
      const totalValue = parseFloat(item.totalallocated) || 0;
      if (!this.segmentFiberPaiResrevedTotals[segmentFiberPairKey]) {
        this.segmentFiberPaiResrevedTotals[segmentFiberPairKey] = 0;
      }
      this.segmentFiberPaiResrevedTotals[segmentFiberPairKey] += totalValue;
    });


    data.forEach(item => {
      let litfp = null;
      let reservedfptotal: number | null = 0;
      if (item.segmentName !== currentSegmentName) {
        formattedData.push({
          segmentOrFiberPair: item.segmentName, dlsName: null, litData: null, reservedData: null,
          litfp: null, soldpf: null, avimfp: null, reservedfp: null, estfp: null, avfufp: null, totalavai: null,
          backgColor: 'lightblue'
        });
        currentSegmentName = item.segmentName;
        currentFiberPairName = '';
        seenFiberPairs.clear();
        litfp = null;
        reservedfptotal = null;
      }


      if (item.fiberPairName !== currentFiberPairName) {
        const nrmstotalvalue = this.getValueBySegmentAndFiberPair(
          item.segmentName!,
          Number(item.fiberPairName!)
        );

        const nrmsAllocatedvalue = this.getValueByAllocatedSegmentAndFiberPair(item.segmentName, item.fiberPairName);
        const nrmsReservedvalue = this.getValueByReservedSegmentAndFiberPair(item.segmentName, item.fiberPairName);


        let estvalue = this.getValueByDLS(item.dlsName, item.segmentName, item.fiberPairName);

        const availresult = this.calculateValue(this.getValueByDLSForUpgrade(item.dlsName, item.segmentName, item.fiberPairName), nrmstotalvalue,
          this.getValueByReservedSegmentAndFiberPair(item.segmentName, item.fiberPairName)
        );
        const totalavail = (((nrmstotalvalue ?? 0) - (nrmsAllocatedvalue ?? 0)) + availresult) === 0 ? null : (((nrmstotalvalue ?? 0) - (nrmsAllocatedvalue ?? 0)) + availresult);
        const totalsold = this.totalsold(item.segmentName, item.dlsName, item.fiberPairName, nrmsAllocatedvalue ?? 0, nrmsReservedvalue ?? 0, totalavail ?? 0, estvalue);


        formattedData.push({
          segmentOrFiberPair: item.fiberPairName,
          dlsName: '',
          litData: '',
          reservedData: null,
          litfp: nrmstotalvalue === 0 ? null : nrmstotalvalue,
          soldpf: nrmsAllocatedvalue === 0 ? null : nrmsAllocatedvalue,
          avimfp: ((nrmstotalvalue ?? 0) - (nrmsAllocatedvalue ?? 0)) === 0 ? null : ((nrmstotalvalue ?? 0) - (nrmsAllocatedvalue ?? 0)),
          reservedfp: nrmsReservedvalue === 0 ? null : nrmsReservedvalue,
          estfp: estvalue === 0 ? null : estvalue,
          avfufp: availresult === 0 ? null : availresult,
          totalavai: (((nrmstotalvalue ?? 0) - (nrmsAllocatedvalue ?? 0)) + availresult) === 0 ? null : (((nrmstotalvalue ?? 0) - (nrmsAllocatedvalue ?? 0)) + availresult),
          backgColor: 'lightblue'
        });

        litfp = null;
        currentFiberPairName = item.fiberPairName;
        seenFiberPairs.clear();
        litfp = null;
        reservedfptotal = null;
      }


      if (!seenFiberPairs.has(item.fiberPairName)) {
        const reservedData = this.getReservedCapacityByDlsName(item.dlsName);
        const nrmsvalue = this.getValueByDlsName(item.dlsName);


        formattedData.push({
          segmentOrFiberPair: null,
          dlsName: item.dlsName,
          litData: Number(nrmsvalue) === 0 ? null : String(Number(nrmsvalue)),
          reservedData: (reservedData ? reservedData : null),
          litfp: null,
          soldpf: null,
          avimfp: null,
          reservedfp: null,
          estfp: null,
          avfufp: null,
          totalavai: null,
          backgColor: ''
        });
        seenFiberPairs.add(item.fiberPairName);

        litfp = null;
      } else {
        const reservedData = this.getReservedCapacityByDlsName(item.dlsName);
        const nrmsvalue = this.getValueByDlsName(item.dlsName);

        const estvalue = this.getValueByDLS(item.dlsName, item.segmentName, item.fiberPairName);
        const totalsold = this.totalsold(item.segmentName, item.dlsName, item.fiberPairName, 0, 0, 0, estvalue);


        formattedData.push({
          segmentOrFiberPair: null,
          dlsName: item.dlsName,
          litData: Number(nrmsvalue) === 0 ? null : String(Number(nrmsvalue)),
          reservedData: (reservedData ? reservedData : null),
          litfp: null,
          soldpf: null,
          avimfp: null,
          reservedfp: null,
          estfp: null,
          avfufp: null,
          totalavai: null,
          backgColor: ''
        });
      }
    });

    this.formattedData = formattedData;
    console.log(this.formattedData);
    console.log(this.formattedData);
    this.transformedDataPieChart = this.formattedPieData.map(item => ({
      name: item.segmentOrFiberPair,
      data: [
        {
          x: 'Sold',
          y: item.estimatedCap
            ? Math.round(((item.sold ?? 0) / item.estimatedCap) * 100)
            : 0
        },
        {
          x: 'Reserved',
          y: item.estimatedCap
            ? Math.round(((item.reserved ?? 0) / item.estimatedCap) * 100)
            : 0
        },
        {
          x: 'Available',
          y: item.estimatedCap
            ? Math.round(((item.availableforsale ?? 0) / item.estimatedCap) * 100)
            : 0
        }
      ]
    }));
    console.log('Pie Data:', this.formattedPieData);
    console.log('Chart Data:', this.transformedDataPieChart);
  }

  totalsold(
    segmentName: string,
    dlsName: string,
    fiberPairName: string,
    nrmsAllocatedvalue: number,
    nrmsReservedvalue: number,
    totalavail: number,
    estimatedcap: number | null
  ) {

    const existingEntry = this.formattedPieData.find(entry => entry.segmentOrFiberPair === segmentName);
    if (existingEntry) {
      existingEntry.sold = (existingEntry.sold ?? 0) + nrmsAllocatedvalue;
      existingEntry.reserved = (existingEntry.reserved ?? 0) + nrmsReservedvalue;
      existingEntry.availableforsale = (existingEntry.availableforsale ?? 0) + totalavail;
      existingEntry.estimatedCap =
        (existingEntry.estimatedCap ?? 0) + (estimatedcap ?? 0);
    } else {
      this.formattedPieData.push({
        segmentOrFiberPair: segmentName, sold: nrmsAllocatedvalue,
        reserved: nrmsReservedvalue, availableforsale: totalavail, estimatedCap: estimatedcap
      });
    }
  }

  getReservedCapacityByDlsName(dlsName: string): number {
    const entry = this.reservedCapacityObject.find(item => item.dlsName === dlsName);
    return entry ? entry.total : 0;
  }

  getValueByDlsName(dlsName: any) {
    const entry = this.nrmsCapacityObject.find(item => item.dls === dlsName);
    return entry ? entry.value : null;
  }

  getValueBySegmentAndFiberPair(segmentName: string, fiberPairName: number): number | null {
    const key = `${segmentName}-${fiberPairName}`;
    return this.segmentFiberPairTotals[key] ?? null;
  }

  getValueByAllocatedSegmentAndFiberPair(segmentName: any, fiberPairName: any) {
    const key = `${segmentName}-${fiberPairName}`;
    return this.segmentFiberPairAllocatedTotals[key] !== undefined ? this.segmentFiberPairAllocatedTotals[key] : null;
  }

  getValueByReservedSegmentAndFiberPair(segmentName: any, fiberPairName: any) {
    const key = `${segmentName}-${fiberPairName}`;
    return this.segmentFiberPaiResrevedTotals[key] !== undefined ? this.segmentFiberPaiResrevedTotals[key] : null;
  }

  getValueByDLS = (dls: string, segmentName: string, fiberPairName: string): number => {
    // const item = this.dlsEstimatedData.find(data => data.dls === dls);
    const item = this.dlsEstimatedData.find((data: any) => data.segment.toUpperCase() === segmentName && data.fiberpair.toUpperCase() === fiberPairName);
    return item ? item.value : 0;
  };
  getValueByDLSForUpgrade = (
    dls: string,
    segmentName: string,
    fiberPairName: string
  ): number => {

    const matchSegmentFiberDls = this.dlsEstimatedData.find(
      (item: any) =>
        item.segment.toUpperCase() === segmentName &&
        item.fiberpair.toUpperCase() === fiberPairName
    );

    if (matchSegmentFiberDls && matchSegmentFiberDls.value !== 0) {
      return matchSegmentFiberDls.value;
    }

    const matchSegmentFiber = this.dlsEstimatedData.find(
      (item: any) =>
        item.segment.toUpperCase() === segmentName &&
        item.fiberpair.toUpperCase() === fiberPairName &&
        item.value !== 0
    );

    return matchSegmentFiber?.value ?? 0;
  };

  calculateValue(estvalue: number, lit: number | null, reserved: number | null): number {
    const roundedDownResult = Math.floor(4000 / 102.5); // round down to 0 decimal places
    const multipliedResult = roundedDownResult * 400;
    let finalResult = 0;

    if (estvalue == null || estvalue == 0) {
      finalResult = ((lit ?? 0) + (reserved ?? 0));
    } else {
      finalResult = estvalue - ((lit ?? 0) + (reserved ?? 0));
    }

    return finalResult;
  }

  exportToExcel() {
    const headers = [
      'Segment or Fiber Pair',
      'DLS Name',
      'Lit per DLS',
      'Reserved per DLS',
      'Lit FP',
      'Sold',
      'Available for Immediate sale',
      'Reserved',
      'Estimated Fp Capacity',
      'Available for future upgrades',
      'Total available for sale'
    ];
    const mappedData = this.formattedData.map(item => ({
      'Segment or Fiber Pair': item.segmentOrFiberPair || '',
      'DLS Name': item.dlsName || '',
      'Lit per DLS': item.litData || '',
      'Reserved per DLS': item.reservedData || '',
      'Lit FP': item.litfp || '',
      'Sold': item.soldpf || '',
      'Available for Immediate sale': item.avimfp || '',
      'Reserved': item.reservedfp || '',
      'Estimated Fp Capacity': item.estfp || '',
      'Available for future upgrades': item.avfufp || '',
      'Total available for sale': item.totalavai || '',

    }));

    const ws: XLSX.WorkSheet = XLSX.utils.json_to_sheet(mappedData, {header: headers});
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');

    const wbout: ArrayBuffer = XLSX.write(wb, {bookType: 'xlsx', type: 'array'});
    const blob = new Blob([wbout], {type: 'application/octet-stream'});

    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'capacity_report.xlsx';
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
    window.URL.revokeObjectURL(url);
  }

  getRowColor(segmentOrFiberPair: string, dlsname: string): string {
    if (!segmentOrFiberPair) {
      return '';
    }

    const value = segmentOrFiberPair.toLowerCase();

    if (value.includes('dls')) {
      return '';
    } else if (value.includes('fp')) {
      return '#d4edda';
    } else {
      return '#c1e7f3a8';
    }
  }

  containsFP(segmentOrFiberPair: string): boolean {
    if (segmentOrFiberPair) {
      return segmentOrFiberPair.includes('FP');
    } else if (!segmentOrFiberPair) {
      return true;
    } else {
      return false;
    }
  }

  private onSuccess(data: any, header: any) {
    this.pivotGridObj.dataSourceSettings.dataSource = data;
  }

}


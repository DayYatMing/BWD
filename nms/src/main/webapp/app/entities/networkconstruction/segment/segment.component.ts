import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

import { Segment } from './segment.model';
import { SegmentService } from './segment.service';
import SharedModule from '../../../shared/shared.module';
import { DialogAllModule } from '@syncfusion/ej2-angular-popups';
import { Observable } from 'rxjs';
import {
  GridModule,
  GridComponent,
  SortService,
  PageService,
  ResizeService,
  ToolbarService,
  EditService,
  SelectionSettingsModel,
  ContextMenuItem,
} from '@syncfusion/ej2-angular-grids';

@Component({
  selector: 'jhi-segment',
  styleUrls: ['segment.scss'],
  templateUrl: './segment.component.html',
    imports: [SharedModule, GridModule, DialogAllModule],
  providers: [SegmentService, SortService, PageService, ResizeService, ToolbarService, EditService],
})
export class SegmentComponent implements OnInit {
  segments!: Segment[];
  public infoMsg!: string;
  public pageSettings!: Object;
  public editSettings!: Object;
  public toolbar!: Object[];
  public selectionOptions: SelectionSettingsModel = { type: 'Single' };
  public initialSort!: Object;
  public contextMenuItems!: ContextMenuItem[];
  public orderidrules!: Object;
  public selectedSegment!: Segment;
  public segment!: Segment;
  public selectedArgs: any;

  @ViewChild('segmentgrid')
  public segmentComponent!: GridComponent;

  public position: object = { X: 'Center' };
  public dialogHeader: string = 'Network Details';
  public isModal: Boolean = true;
  public visible: Boolean = false;
  public animationSettings: Object = { effect: 'Zoom' };
  public dialogCloseIcon: Boolean = true;
  public target: string = '.control-section';
  public dialogWidth: string = '1200px';
  public customeridrules!: Object;
  public freightrules!: Object;
  public editparams!: Object;

  progressLoader: boolean = true;

  constructor(private segmentService: SegmentService) {}

  ngOnInit() {
    this.loadAll();
    this.editSettings = { allowEditing: true, allowAdding: true, allowDeleting: true, mode: 'Dialog' };
    this.toolbar = ['Add', 'Update', 'Cancel', 'Search'];
    this.orderidrules = { required: true };
    this.customeridrules = { required: true };
    this.freightrules = { required: true };
    this.editparams = { params: { popupHeight: '300px' } };
    this.pageSettings = { pageSizes: false, pageSize: 15 };
    this.initialSort = {
      columns: [{ field: 'labelname', direction: 'Ascending' }],
    };
    this.contextMenuItems = [
      'AutoFit',
      'AutoFitAll',
      'SortAscending',
      'SortDescending',
      'Copy',
      'Edit',
      'Save',
      'Cancel',
      'PdfExport',
      'ExcelExport',
      'CsvExport',
      'FirstPage',
      'PrevPage',
      'LastPage',
      'NextPage',
    ];
  }

  loadAll() {
    this.segmentService.query({}).subscribe((res: HttpResponse<Segment[]>) => {
      this.onSuccess(res.body, res.headers), (res: HttpErrorResponse) => this.onError(res.message);
    });
  }

  private onSuccess(data: any, headers: any) {
    this.progressLoader = false;
    this.segments = data;
  }
  private onError(error: any) {
    this.infoMsg = error;
  }

  rowSelected(e: any): void {
    if (this.segmentComponent.getSelectedRecords().length > 0) this.selectedSegment = this.segmentComponent.getSelectedRecords()[0] as Segment;
  }

  actionComplete(args: any): void {
    if (args.requestType === 'save') {
      this.segment = args.data;
      console.log(this.segment.id)
      if (this.segment.id === undefined || this.segment.id === null) {
        this.subscribeToSaveResponse(this.segmentService.create(this.segment));
      } else {
        this.subscribeToSaveResponse(this.segmentService.update(this.segment));
      }
    } else if (args.requestType === 'delete') {
      this.selectedArgs = args.data[0];
      this.subscribeToSegmentCountResponse(this.segmentService.querySegmentCount(this.selectedArgs.segmentname));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<Segment>>) {
    result.subscribe(
      (res: HttpResponse<Segment>) => this.onSaveSuccess(),
      (res: HttpErrorResponse) => this.onSaveError(res),
    );
  }

  private subscribeToSegmentCountResponse(result: Observable<HttpResponse<number>>) {
    result.subscribe(
      (res: HttpResponse<number>) => this.onCountSuccess(res),
      (res: HttpErrorResponse) => this.onSaveError(res),
    );
  }

  private onSaveSuccess() {
    this.infoMsg = 'Success.';
    this.loadAll();
  }

  private onSaveError(res: HttpErrorResponse | null) {
    if (res !== null && res.error !== undefined && res.error.title !== undefined) {
      this.infoMsg = 'Failed, ' + res.error.title;
      this.loadAll();
    } else {
      this.infoMsg = 'Failed, Please contact the System Administrator.';
      this.loadAll();
    }
  }

  private onCountSuccess(result: any) {
    if (result.body === 0) this.subscribeToDeleteResponse(this.segmentService.delete(this.selectedArgs.id));
    else {
      alert('Please note that deleting segment may result the subsequent items relations.');
      this.loadAll();
    }
  }
  private subscribeToDeleteResponse(result: Observable<HttpResponse<Segment>>) {
    result.subscribe(
      (res: HttpResponse<Segment>) => this.onSaveDeleteSuccess(res.body),
      (res: HttpErrorResponse) => this.onSaveError(res),
    );
  }
  private onSaveDeleteSuccess(result: Segment | null) {
    this.infoMsg = 'Successfully Deleted!';
    this.loadAll();
  }
}

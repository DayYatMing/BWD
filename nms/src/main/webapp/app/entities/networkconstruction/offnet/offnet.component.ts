import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

import {Offnet} from './offnet.model';
import { OffnetService } from './offnet.service';
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
  selector: 'jhi-offnet',
  styleUrls: ['offnet.scss'],
  templateUrl: './offnet.component.html',
  imports: [SharedModule, GridModule, DialogAllModule],
  providers: [OffnetService, SortService, PageService, ResizeService, ToolbarService, EditService],
})
export class OffnetComponent implements OnInit {
  offnets!: Offnet[];
  public infoMsg!: string;
  public pageSettings!: Object;
  public editSettings!: Object;
  public toolbar!: Object[];
  public selectionOptions: SelectionSettingsModel = { type: 'Single' };
  public initialSort!: Object;
  public contextMenuItems!: ContextMenuItem[];
  public orderidrules!: Object;
  public selectedOffnet!: Offnet;
  public offnet!: Offnet;
  public selectedArgs: any;

  @ViewChild('offnetgrid')
  public offnetComponent!: GridComponent;

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

  constructor(private offnetService: OffnetService) {}

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
    this.offnetService.query({}).subscribe((res: HttpResponse<Offnet[]>) => {
      this.onSuccess(res.body, res.headers), (res: HttpErrorResponse) => this.onError(res.message);
    });
  }

  private onSuccess(data: any, headers: any) {
    this.progressLoader = false;
    this.offnets = data;
  }
  private onError(error: any) {
    this.infoMsg = error;
  }

  rowSelected(e: any): void {
    if (this.offnetComponent.getSelectedRecords().length > 0) this.selectedOffnet = this.offnetComponent.getSelectedRecords()[0] as Offnet;
  }

  actionComplete(args: any): void {
    if (args.requestType === 'save') {
      this.offnet = args.data;
      if (this.offnet.id === undefined || this.offnet.id === null) {
        this.subscribeToSaveResponse(this.offnetService.create(this.offnet));
      } else this.subscribeToSaveResponse(this.offnetService.update(this.offnet));
    } else if (args.requestType === 'delete') {
      this.selectedArgs = args.data[0];
      this.subscribeToOffnetCountResponse(this.offnetService.queryOffnetCount(this.selectedArgs.offnetname));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<Offnet>>) {
    result.subscribe(
      (res: HttpResponse<Offnet>) => this.onSaveSuccess(),
      (res: HttpErrorResponse) => this.onSaveError(res),
    );
  }

  private subscribeToOffnetCountResponse(result: Observable<HttpResponse<number>>) {
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
    if (result.body === 0) this.subscribeToDeleteResponse(this.offnetService.delete(this.selectedArgs.id));
    else {
      alert('Please note that deleting Offnet may result the subsequent items relations.');
      this.loadAll();
    }
  }
  private subscribeToDeleteResponse(result: Observable<HttpResponse<Offnet>>) {
    result.subscribe(
      (res: HttpResponse<Offnet>) => this.onSaveDeleteSuccess(res.body),
      (res: HttpErrorResponse) => this.onSaveError(res),
    );
  }
  private onSaveDeleteSuccess(result: Offnet | null) {
    this.infoMsg = 'Successfully Deleted!';
    this.loadAll();
  }
}

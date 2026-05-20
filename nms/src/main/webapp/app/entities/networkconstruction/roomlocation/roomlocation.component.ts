import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

import { Roomlocation } from './roomlocation.model';
import { RoomlocationService } from './roomlocation.service';
import SharedModule from '../../../shared/shared.module';
import { WorldMapComponentSync } from '../../../shared/worldmap-syncfusion/worldmap.component';
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
  selector: 'jhi-roomlocation',
  styleUrls: ['roomlocation.scss'],
  templateUrl: './roomlocation.component.html',
  imports: [SharedModule, GridModule, DialogAllModule],
  providers: [RoomlocationService, SortService, PageService, ResizeService, ToolbarService, EditService],
})
export class RoomlocationComponent implements OnInit {
  roomlocations!: Roomlocation[];
  public infoMsg!: string;
  public pageSettings!: Object;
  public editSettings!: Object;
  public toolbar!: Object[];
  public selectionOptions: SelectionSettingsModel = { type: 'Single' };
  public initialSort!: Object;
  public contextMenuItems!: ContextMenuItem[];
  public orderidrules!: Object;
  public selectedRoomlocation!: Roomlocation;
  public roomlocation!: Roomlocation;
  public selectedArgs: any;

  @ViewChild('roomlocationgrid')
  public roomlocationComponent!: GridComponent;

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

  constructor(private roomlocationService: RoomlocationService) {}

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
    this.roomlocationService.query({}).subscribe((res: HttpResponse<Roomlocation[]>) => {
      this.onSuccess(res.body, res.headers), (res: HttpErrorResponse) => this.onError(res.message);
    });
  }

  private onSuccess(data: any, headers: any) {
    this.progressLoader = false;
    this.roomlocations = data;
  }
  private onError(error: any) {
    this.infoMsg = error;
  }

  rowSelected(e: any): void {
    if (this.roomlocationComponent.getSelectedRecords().length > 0) this.selectedRoomlocation = this.roomlocationComponent.getSelectedRecords()[0] as Roomlocation;
  }

  actionComplete(args: any): void {
    if (args.requestType === 'save') {
      this.roomlocation = args.data;
      if (this.roomlocation.id === undefined || this.roomlocation.id === null) {
        this.subscribeToSaveResponse(this.roomlocationService.create(this.roomlocation));
      } else {
        this.subscribeToSaveResponse(this.roomlocationService.update(this.roomlocation));
      }
    } else if (args.requestType === 'delete') {
      this.selectedArgs = args.data[0];
      this.subscribeToRoomlocationCountResponse(this.roomlocationService.queryRoomlocationCount(this.selectedArgs.roomlocationname));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<Roomlocation>>) {
    result.subscribe(
      (res: HttpResponse<Roomlocation>) => this.onSaveSuccess(),
      (res: HttpErrorResponse) => this.onSaveError(res),
    );
  }

  private subscribeToRoomlocationCountResponse(result: Observable<HttpResponse<number>>) {
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
    if (result.body === 0) this.subscribeToDeleteResponse(this.roomlocationService.delete(this.selectedArgs.id));
    else {
      alert('Please note that deleting Roomlocation may result the subsequent items relations.');
      this.loadAll();
    }
  }
  private subscribeToDeleteResponse(result: Observable<HttpResponse<Roomlocation>>) {
    result.subscribe(
      (res: HttpResponse<Roomlocation>) => this.onSaveDeleteSuccess(res.body),
      (res: HttpErrorResponse) => this.onSaveError(res),
    );
  }
  private onSaveDeleteSuccess(result: Roomlocation | null) {
    this.infoMsg = 'Successfully Deleted!';
    this.loadAll();
  }
}

import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

import { Backhaul } from './backhaul.model';
import { BackhaulService } from './backhaul.service';
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
  selector: 'jhi-backhaul',
  styleUrls: ['backhaul.scss'],
  templateUrl: './backhaul.component.html',
  imports: [SharedModule, GridModule, DialogAllModule],
  providers: [BackhaulService, SortService, PageService, ResizeService, ToolbarService, EditService],
})
export class BackhaulComponent implements OnInit {
  backhauls!: Backhaul[];
  public infoMsg!: string;
  public pageSettings!: Object;
  public editSettings!: Object;
  public toolbar!: Object[];
  public selectionOptions: SelectionSettingsModel = { type: 'Single' };
  public initialSort!: Object;
  public contextMenuItems!: ContextMenuItem[];
  public orderidrules!: Object;
  public selectedBackhaul!: Backhaul;
  public backhaul!: Backhaul;
  public selectedArgs: any;

  @ViewChild('backhaulgrid')
  public backhaulComponent!: GridComponent;

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

  constructor(private backhaulService: BackhaulService) {}

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
    this.backhaulService.query({}).subscribe((res: HttpResponse<Backhaul[]>) => {
      this.onSuccess(res.body, res.headers), (res: HttpErrorResponse) => this.onError(res.message);
    });
  }

  private onSuccess(data: any, headers: any) {
    this.progressLoader = false;
    this.backhauls = data;
  }
  private onError(error: any) {
    this.infoMsg = error;
  }

  rowSelected(e: any): void {
    if (this.backhaulComponent.getSelectedRecords().length > 0) this.selectedBackhaul = this.backhaulComponent.getSelectedRecords()[0] as Backhaul;
  }

  actionComplete(args: any): void {
    if (args.requestType === 'save') {
      this.backhaul = args.data;
      if (this.backhaul.id === undefined || this.backhaul.id === null) {
        this.subscribeToSaveResponse(this.backhaulService.create(this.backhaul));
      } else this.subscribeToSaveResponse(this.backhaulService.update(this.backhaul));
    } else if (args.requestType === 'delete') {
      this.selectedArgs = args.data[0];
      this.subscribeToBackhaulCountResponse(this.backhaulService.queryBackhaulCount(this.selectedArgs.backhaulname));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<Backhaul>>) {
    result.subscribe(
      (res: HttpResponse<Backhaul>) => this.onSaveSuccess(),
      (res: HttpErrorResponse) => this.onSaveError(res),
    );
  }

  private subscribeToBackhaulCountResponse(result: Observable<HttpResponse<number>>) {
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
    if (result.body === 0) this.subscribeToDeleteResponse(this.backhaulService.delete(this.selectedArgs.id));
    else {
      alert('Please note that deleting Backhaul may result the subsequent items relations.');
      this.loadAll();
    }
  }
  private subscribeToDeleteResponse(result: Observable<HttpResponse<Backhaul>>) {
    result.subscribe(
      (res: HttpResponse<Backhaul>) => this.onSaveDeleteSuccess(res.body),
      (res: HttpErrorResponse) => this.onSaveError(res),
    );
  }
  private onSaveDeleteSuccess(result: Backhaul | null) {
    this.infoMsg = 'Successfully Deleted!';
    this.loadAll();
  }
}

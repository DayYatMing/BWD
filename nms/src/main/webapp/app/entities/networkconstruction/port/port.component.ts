import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

import {Port} from './port.model';
import { PortService } from './port.service';
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
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'jhi-port',
  styleUrls: ['port.scss'],
  templateUrl: './port.component.html',
  imports: [SharedModule, GridModule, DialogAllModule, FormsModule],
  providers: [PortService, SortService, PageService, ResizeService, ToolbarService, EditService],
})
export class PortComponent implements OnInit {
  ports!: Port[];
  public infoMsg!: string;
  public pageSettings!: Object;
  public editSettings!: Object;
  public toolbar!: Object[];
  public selectionOptions: SelectionSettingsModel = { type: 'Single' };
  public initialSort!: Object;
  public contextMenuItems!: ContextMenuItem[];
  public orderidrules!: Object;
  public selectedPort!: Port;
  public port!: Port;
  public selectedArgs: any;

  @ViewChild('portgrid')
  public portComponent!: GridComponent;

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

  selectedFile: File | null = null;
  progressLoader: boolean = true;

  constructor(private portService: PortService) {}

  ngOnInit() {
    this.loadAll();
    this.editSettings = { allowEditing: true, allowAdding: true, allowDeleting: false, mode: 'Dialog' };
    this.toolbar = ['Add', 'Update', 'Cancel', 'Search'];
    this.orderidrules = { required: true };
    this.customeridrules = { required: true };
    this.freightrules = { required: true };
    this.editparams = { params: {popupHeight: '300px' } };
    this.pageSettings = { pageSizes: false, pageSize: 10 };
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
    this.portService.query({}).subscribe((res: HttpResponse<Port[]>) => {
      this.onSuccess(res.body, res.headers), (res: HttpErrorResponse) => this.onError(res.message);
    });
  }

  private onSuccess(data: any, headers: any) {
    this.progressLoader = false;
    this.ports = data;
  }
  private onError(error: any) {
    this.infoMsg = error;
  }

  rowSelected(e: any): void {
    if (this.portComponent.getSelectedRecords().length > 0) this.selectedPort = this.portComponent.getSelectedRecords()[0] as Port;
  }

  actionComplete(args: any): void {
    if (args.requestType === 'save') {
      this.port = args.data;
      if (this.port.id === undefined || this.port.id === null) {
        this.subscribeToSaveResponse(this.portService.create(this.port));
      } else {
        this.subscribeToSaveResponse(this.portService.update(this.port));
      }
    } else if (args.requestType === 'delete') {
      this.selectedArgs = args.data[0];
      this.subscribeToPortCountResponse(this.portService.queryPortCount(this.selectedArgs.portname));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<Port>>) {
    result.subscribe(
      (res: HttpResponse<Port>) => this.onSaveSuccess(),
      (res: HttpErrorResponse) => this.onSaveError(res),
    );
  }

  private subscribeToPortCountResponse(result: Observable<HttpResponse<number>>) {
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
    if (result.body === 0) this.subscribeToDeleteResponse(this.portService.delete(this.selectedArgs.id));
    else {
      alert('Please note that deleting port may result the subsequent items relations.');
      this.loadAll();
    }
  }
  private subscribeToDeleteResponse(result: Observable<HttpResponse<Port>>) {
    result.subscribe(
      (res: HttpResponse<Port>) => this.onSaveDeleteSuccess(res.body),
      (res: HttpErrorResponse) => this.onSaveError(res),
    );
  }
  private onSaveDeleteSuccess(result: Port | null) {
    this.infoMsg = 'Successfully Deleted!';
    this.loadAll();
  }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (!input.files?.length) return;
    this.selectedFile = input.files[0];
  }

  public upload() {
    const formData = new FormData();
    if (this.selectedFile) {
      formData.append('uploadedBulkPortsCSV', this.selectedFile);

      this.portService.createBulk(formData).subscribe({
        next: res => {
          console.log('Status:', res.status);
          console.log('Headers:', res.headers);

          this.infoMsg = "Uploaded successfully, and file will be processed in minutes. Please check later.";
        },
        error: (err: { message: string; }) => {
          this.infoMsg = "Duplicate records may be existed. " + err.message;
        },
      });
    }
    else {
      this.infoMsg = "Please upload CSV with correct format.";
    }
  }
}

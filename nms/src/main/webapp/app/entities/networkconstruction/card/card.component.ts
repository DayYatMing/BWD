import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

import {Card} from './card.model';
import { CardService } from './card.service';
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
  selector: 'jhi-card',
  styleUrls: ['card.scss'],
  templateUrl: './card.component.html',
  imports: [SharedModule, GridModule, DialogAllModule],
  providers: [CardService, SortService, PageService, ResizeService, ToolbarService, EditService],
})
export class CardComponent implements OnInit {
  cards!: Card[];
  public infoMsg!: string;
  public pageSettings!: Object;
  public editSettings!: Object;
  public toolbar!: Object[];
  public selectionOptions: SelectionSettingsModel = { type: 'Single' };
  public initialSort!: Object;
  public contextMenuItems!: ContextMenuItem[];
  public orderidrules!: Object;
  public selectedCard!: Card;
  public card!: Card;
  public selectedArgs: any;

  @ViewChild('cardgrid')
  public cardComponent!: GridComponent;

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

  constructor(private cardService: CardService) {}

  ngOnInit() {
    this.loadAll();
    this.editSettings = { allowEditing: true, allowAdding: true, allowDeleting: false, mode: 'Dialog' };
    this.toolbar = ['Add', 'Update', 'Cancel', 'Search'];
    this.orderidrules = { required: true };
    this.customeridrules = { required: true };
    this.freightrules = { required: true };
    this.editparams = { params: { popupHeight: '300px' } };
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
    this.cardService.query({}).subscribe((res: HttpResponse<Card[]>) => {
      this.onSuccess(res.body, res.headers), (res: HttpErrorResponse) => this.onError(res.message);
    });
  }

  private onSuccess(data: any, headers: any) {
    this.progressLoader = false;
    this.cards = data;
  }
  private onError(error: any) {
    this.infoMsg = error;
  }

  rowSelected(e: any): void {
    if (this.cardComponent.getSelectedRecords().length > 0) this.selectedCard = this.cardComponent.getSelectedRecords()[0] as Card;
  }

  actionComplete(args: any): void {
    if (args.requestType === 'save') {
      this.card = args.data;
      console.log(this.card);
      if (this.card.id === undefined || this.card.id === null) {
        this.subscribeToSaveResponse(this.cardService.create(this.card));
      } else {
        this.subscribeToSaveResponse(this.cardService.update(this.card));
      }
    } else if (args.requestType === 'delete') {
      this.selectedArgs = args.data[0];
      this.subscribeToCardCountResponse(this.cardService.queryCardCount(this.selectedArgs.cardname));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<Card>>) {
    result.subscribe(
      (res: HttpResponse<Card>) => this.onSaveSuccess(),
      (res: HttpErrorResponse) => this.onSaveError(res),
    );
  }

  private subscribeToCardCountResponse(result: Observable<HttpResponse<number>>) {
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
    if (result.body === 0) this.subscribeToDeleteResponse(this.cardService.delete(this.selectedArgs.id));
    else {
      alert('Please note that deleting Card may result the subsequent items relations.');
      this.loadAll();
    }
  }
  private subscribeToDeleteResponse(result: Observable<HttpResponse<Card>>) {
    result.subscribe(
      (res: HttpResponse<Card>) => this.onSaveDeleteSuccess(res.body),
      (res: HttpErrorResponse) => this.onSaveError(res),
    );
  }
  private onSaveDeleteSuccess(result: Card | null) {
    this.infoMsg = 'Successfully Deleted!';
    this.loadAll();
  }
}

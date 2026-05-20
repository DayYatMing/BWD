import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

import { Site } from './site.model';
import { SiteService } from './site.service';
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
  selector: 'jhi-site',
  styleUrls: ['site.scss'],
  templateUrl: './site.component.html',
  imports: [SharedModule, WorldMapComponentSync, GridModule, DialogAllModule],
  providers: [SiteService, SortService, PageService, ResizeService, ToolbarService, EditService],
})
export class SiteComponent implements OnInit {
  sites!: Site[];
  public infoMsg!: string;
  public pageSettings!: Object;
  public editSettings!: Object;
  public toolbar!: Object[];
  public selectionOptions: SelectionSettingsModel = { type: 'Single' };
  public initialSort!: Object;
  public contextMenuItems!: ContextMenuItem[];
  public orderidrules!: Object;
  public selectedSite!: Site;
  public site!: Site;
  public selectedArgs: any;

  @ViewChild('sitegrid')
  public siteComponent!: GridComponent;

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

  constructor(private siteService: SiteService) {}

  @ViewChild(WorldMapComponentSync)
  public worldmapComponent!: WorldMapComponentSync;

  updateMapsSettings(sites: Site[]) {
    if (
      this.worldmapComponent.maps &&
      this.worldmapComponent.maps.layers &&
      this.worldmapComponent.maps.layers[0] &&
      this.worldmapComponent.maps.layers[0].markerSettings
    ) {
      this.worldmapComponent.maps.layers[0].markerSettings[0].dataSource = sites.map(site => ({
        latitude: site.latitude,
        longitude: site.longitude,
        city: site.labelname,
      }));
    }
  }

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
    this.siteService.query({}).subscribe((res: HttpResponse<Site[]>) => {
      this.onSuccess(res.body, res.headers), (res: HttpErrorResponse) => this.onError(res.message);
    });
  }

  private onSuccess(data: any, headers: any) {
    this.sites = data;
    this.progressLoader = false;
    this.updateMapsSettings(this.sites);
  }

  private onError(error: any) {
    this.infoMsg = error;
  }

  rowSelected(e: any): void {
    if (this.siteComponent.getSelectedRecords().length > 0) this.selectedSite = this.siteComponent.getSelectedRecords()[0] as Site;
  }

  actionComplete(args: any): void {
    if (args.requestType === 'save') {
      this.site = args.data;
      if (this.site.id === undefined || this.site.id === null) {
        this.subscribeToSaveResponse(this.siteService.create(this.site));
      } else this.subscribeToSaveResponse(this.siteService.update(this.site));
    } else if (args.requestType === 'delete') {
      this.selectedArgs = args.data[0];
      this.subscribeToSiteCountResponse(this.siteService.querySiteCount(this.selectedArgs.sitename));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<Site>>) {
    result.subscribe(
      (res: HttpResponse<Site>) => this.onSaveSuccess(),
      (res: HttpErrorResponse) => this.onSaveError(res),
    );
  }

  private subscribeToSiteCountResponse(result: Observable<HttpResponse<number>>) {
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
    if (result.body === 0) this.subscribeToDeleteResponse(this.siteService.delete(this.selectedArgs.id));
    else {
      alert('Deleting a site already mapped to a segment may lead to inconsistencies, Please delete those segments first');
      this.loadAll();
    }
  }
  private subscribeToDeleteResponse(result: Observable<HttpResponse<Site>>) {
    result.subscribe(
      (res: HttpResponse<Site>) => this.onSaveDeleteSuccess(res.body),
      (res: HttpErrorResponse) => this.onSaveError(res),
    );
  }
  private onSaveDeleteSuccess(result: Site | null) {
    this.infoMsg = 'Successfully Deleted!';
    this.loadAll();
  }
}

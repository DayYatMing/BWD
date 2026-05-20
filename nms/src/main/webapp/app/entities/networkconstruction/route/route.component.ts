import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

import { Route } from './route.model';
import { RouteService } from './route.service';
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
import {SiteService} from "../site/site.service";
import {SegmentService} from "../segment/segment.service";
import {Site} from "../site/site.model";
import {Segment} from "../segment/segment.model";

@Component({
  selector: 'jhi-route',
  styleUrls: ['route.scss'],
  templateUrl: './route.component.html',
  imports: [SharedModule, GridModule, DialogAllModule],
  providers: [RouteService, SortService, PageService, ResizeService, ToolbarService, EditService],
})
export class RouteComponent implements OnInit {
  routes!: Route[];
  public infoMsg!: string;
  public pageSettings!: Object;
  public editSettings!: Object;
  public toolbar!: Object[];
  public selectionOptions: SelectionSettingsModel = { type: 'Single' };
  public initialSort!: Object;
  public contextMenuItems!: ContextMenuItem[];
  public orderidrules!: Object;
  public selectedRoute!: Route;
  public route!: Route;
  public selectedArgs: any;

  @ViewChild('routegrid')
  public routeComponent!: GridComponent;

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
  sites: Site[] | undefined;
  segments: Segment[] | undefined;

  progressLoader: boolean = true;

  constructor(private routeService: RouteService,
              private siteService: SiteService,
              private segmentService: SegmentService,) {}

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
    this.routeService.query({}).subscribe((res: HttpResponse<Route[]>) => {
      this.onSuccess(res.body, res.headers), (res: HttpErrorResponse) => this.onError(res.message);
    });
  }
  private onSiteSuccess(data: any, headers: any) {
    this.sites = data;
  }

  private onSegmentSuccess(data: any, headers: any) {
    this.segments = data;
  }

  private onSuccess(data: any, headers: any) {
    this.progressLoader = false;
    this.routes = data;
  }
  private onError(error: any) {
    this.infoMsg = error;
  }

  rowSelected(e: any): void {
    if (this.routeComponent.getSelectedRecords().length > 0) this.selectedRoute = this.routeComponent.getSelectedRecords()[0] as Route;
  }

  actionComplete(args: any): void {
    if (args.requestType === 'save') {
      this.route = args.data;
      this.createRouteName(args);
      this.route.segments = args.data.segments.split(",");

      if (this.route.id === undefined || this.route.id === null) {
        this.subscribeToSaveResponse(this.routeService.create(this.route));
      } else {
        this.subscribeToSaveResponse(this.routeService.update(this.route));
      }
    } else if (args.requestType === 'delete') {
      this.selectedArgs = args.data[0];
      this.subscribeToRouteCountResponse(this.routeService.queryRouteCount(this.selectedArgs.routename));
    }
  }

  private createRouteName(args: any){
    let re = /US/gi;
    if(args.data.aend.substring(0,2).search(re)  === -1 && args.data.zend.substring(0,2).search(re) === -1)
      this.route.routename = args.data.aend.substring(0,2)+"-"+args.data.zend.substring(0,2)+"-001";
    else if(args.data.aend.substring(0,2).search(re)  !== -1 && args.data.zend.substring(0,2).search(re) === -1)
      this.route.routename = "U"+args.data.aend.substring(3,2)+"-"+args.data.zend.substring(0,2)+"-001";
    else if(args.data.aend.substring(0,2).search(re)  === -1 && args.data.zend.substring(0,2).search(re) !== -1)
      this.route.routename = args.data.aend.substring(0,2)+"-U"+args.data.zend.substring(3,2)+"-001";
    else
      this.route.routename = "U"+args.data.aend.substring(3,2)+"-U"+args.data.zend.substring(3,2)+"-001";
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<Route>>) {
    result.subscribe(
      (res: HttpResponse<Route>) => this.onSaveSuccess(),
      (res: HttpErrorResponse) => this.onSaveError(res),
    );
  }

  private subscribeToRouteCountResponse(result: Observable<HttpResponse<number>>) {
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
    if (result.body === 0) this.subscribeToDeleteResponse(this.routeService.delete(this.selectedArgs.id));
    else {
      alert('Please note that deleting Route may result the subsequent items relations.');
      this.loadAll();
    }
  }
  private subscribeToDeleteResponse(result: Observable<HttpResponse<Route>>) {
    result.subscribe(
      (res: HttpResponse<Route>) => this.onSaveDeleteSuccess(res.body),
      (res: HttpErrorResponse) => this.onSaveError(res),
    );
  }
  private onSaveDeleteSuccess(result: Route | null) {
    this.infoMsg = 'Successfully Deleted!';
    this.loadAll();
  }
}

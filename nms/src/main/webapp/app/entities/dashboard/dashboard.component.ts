import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse} from '@angular/common/http';
import {DashboardLayoutModule} from '@syncfusion/ej2-angular-layouts';
import {SiteService} from "../networkconstruction/site/site.service";
import {TimelineService, Timeline} from "./timeline";
import {Site} from "../networkconstruction/site/site.model";
import {GridComponent, GridModule} from "@syncfusion/ej2-angular-grids";
import { CommonModule } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import {WorldMapComponentSync} from "../../shared/worldmap-syncfusion/worldmap.component";

@Component({
  selector: 'jhi-dashboard',
  imports: [DashboardLayoutModule, GridModule, CommonModule, FontAwesomeModule, WorldMapComponentSync],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})

export class DashboardComponent {
  site!: Site[];
  timelines!: Timeline[] | null;
  public infoMsg!: string;
  public aspectRatio: any = 100 / 75;
  public cellSpacing: number[] = [5, 5];

  @ViewChild(WorldMapComponentSync)
  public worldmapComponent!: WorldMapComponentSync;

  @ViewChild('eventgrid')
  public eventGrid!: GridComponent;

  constructor(private siteService: SiteService, private timelineService: TimelineService) {
  }

  updateMapsSettings(sites: Site[]) {
    if (
      this.worldmapComponent.maps &&
      this.worldmapComponent.maps.layers &&
      this.worldmapComponent.maps.layers[0] &&
      this.worldmapComponent.maps.layers[0].markerSettings
    ) {
      this.worldmapComponent.maps.layers[0].markerSettings[0].dataSource = sites.map(s => ({
        latitude: s.latitude,
        longitude: s.longitude,
        city: s.labelname,
      }));
    }
  }

  ngOnInit() {
    this.loadAll();
  }

  loadAll() {
    this.siteService.query({}).subscribe(
      (res: HttpResponse<Site[]>) => this.onSuccess(res.body, res.headers),
      (res: HttpErrorResponse) => this.onError(res.message)
    );

    this.timelineService.query({}).subscribe(
      (res: HttpResponse<Timeline[]>) => this.onTimelineSuccess(res.body, res.headers),
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  private onSuccess(data: any, headers: any) {
    this.site = data;
    this.updateMapsSettings(this.site);
  }

  private onError(error: any) {
    this.infoMsg = error;
  }

  toolbarClick(): void {
    this.eventGrid.excelExport(this.getExcelExportProperties());
  }

  private getExcelExportProperties(): any {
    return {
      fileName: "Timeline.xlsx"
    };
  }

  private onTimelineSuccess(data: Timeline[] | null, headers: HttpHeaders) {
    if(data != null){
      this.timelines = data;
      this.timelines.sort((a, b) => {
        const dateA = a.eventdate ? new Date(a.eventdate).getTime() : 0;
        const dateB = b.eventdate ? new Date(b.eventdate).getTime() : 0;
        return dateB - dateA;
      });
    }
  }
}

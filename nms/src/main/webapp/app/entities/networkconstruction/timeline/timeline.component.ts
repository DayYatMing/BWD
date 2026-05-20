import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpErrorResponse, HttpResponse, HttpHeaders } from '@angular/common/http';
import { DatePipe} from "@angular/common";
import { Timeline } from './timeline.model';
import { TimelineService } from './timeline.service';
import  SharedModule from '../../../shared/shared.module';
import { DialogAllModule } from '@syncfusion/ej2-angular-popups';
import {
  GridModule,
  GridComponent,
  SortService,
  PageService,
  ResizeService,
  ToolbarService,
  EditService} from '@syncfusion/ej2-angular-grids';

@Component({
  selector: 'jhi-timeline',
  styleUrls: ['timeline.css'],
  imports: [SharedModule, GridModule, DialogAllModule],
  templateUrl: './timeline.component.html',
  providers: [TimelineService, SortService, PageService, ResizeService, ToolbarService, EditService],

})
export class TimelineComponent implements OnInit {

  timelines!: Timeline[] | null;
  public infoMsg!: string;

  @ViewChild('eventsGrid')
  public eventsGrid!: GridComponent;
  public timeline: Timeline[] = [];

  constructor(
    private timelineService: TimelineService,
    public datePipe: DatePipe
  ) {}

  ngOnInit(): void {
    this.timelineService.query({}).subscribe(
      (res: HttpResponse<Timeline[]>) => this.onTimelineSuccess(res.body, res.headers),
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  toolbarClick(): void {
    this.eventsGrid.excelExport(this.getExcelExportProperties());
  }

  private getExcelExportProperties(): any {
    return {
      fileName: "Timeline.xlsx"
    };
  }

  private onTimelineSuccess(data: Timeline[] | null, headers: HttpHeaders) {
        this.timelines = data;

    }

    private onError(error: string) {
        this.infoMsg = error;
    }
    ngOnDestroy() {
    }
}

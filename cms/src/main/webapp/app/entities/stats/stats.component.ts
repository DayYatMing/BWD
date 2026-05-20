import { Component, inject, OnInit, signal, ViewChild } from '@angular/core';
import {
  CategoryService,
  ChartModule,
  DataLabelService,
  LegendService,
  LineSeriesService,
  TooltipService,
} from '@syncfusion/ej2-angular-charts';
import { CommonModule } from '@angular/common';
import { StatsService } from './stats.service';
import { HttpResponse } from '@angular/common/http';
import { Stats, StatsInput } from './stats.model';
import { FormsModule } from '@angular/forms';
import { DateTimePickerComponent, DateTimePickerModule } from '@syncfusion/ej2-angular-calendars';
import { Account } from '../../core/auth/account.model';
import { AccountService } from '../../core/auth/account.service';
import { lastValueFrom, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { TicketManagementService } from '../ticket-management/ticket-management.service';

@Component({
  selector: 'jhi-stats',
  imports: [CommonModule, ChartModule, DateTimePickerModule, FormsModule],
  templateUrl: './stats.component.html',
  styleUrl: './stats.component.scss',
  providers: [CategoryService, LegendService, TooltipService, DataLabelService, LineSeriesService],
})
export class StatsComponent implements OnInit {
  constructor(
    private statsService: StatsService,
    private ticketManagementService: TicketManagementService,
  ) {
    this.fromDate = new Date(this.today.getTime() - 3 * 60 * 60 * 1000);
  }

  account = signal<Account | null>(null);
  private readonly accountService = inject(AccountService);
  private readonly destroy$ = new Subject<void>();

  infoMsg: string = '';
  progressLoader: boolean = true;
  login: any;

  public stats: Stats[] = [];
  public statsInput: StatsInput = new StatsInput();

  public cusShortName: string = '';
  public serviceId: any;
  public selectedServiceId!: string;
  public source: any;

  public linkFailSecInData?: Object[];
  public linkFailSecOutData?: Object[];
  public physicalErrCntInData?: Object[];
  public physicalErrCntOutData?: Object[];
  public frameChkSeqErrCntInData?: Object[];
  public frameChkSeqErrCntOutData?: Object[];
  public numOfSecInBinTxLineCardData?: Object[];
  public numOfSecInBinRxLineCardData?: Object[];

  public primaryXAxis?: Object;
  public primaryYAxis?: Object;
  public legendSettings?: Object;
  public tooltip?: Object;
  public palette?: string[];

  @ViewChild('dtPickerFr') dtPickerFr!: DateTimePickerComponent;
  @ViewChild('dtPickerTo') dtPickerTo!: DateTimePickerComponent;
  public today: Date = new Date();
  public fromDate: Date = new Date();

  async ngOnInit() {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => this.account.set(account));

    this.login = this.account()?.login;
    await this.loadCusShortName();
    await this.loadServices(this.cusShortName);
    await this.loadSources(this.selectedServiceId);
    await this.loadServerData(this.fromDate.getTime().toString(), this.today.getTime().toString());
  }

  async loadCusShortName(): Promise<void> {
    try {
      const res: HttpResponse<string> = await lastValueFrom(this.ticketManagementService.findCusShortName(this.login));
      if (res.body !== null) {
        this.cusShortName = res.body;
        this.statsInput.customer = '';
      }
      console.log('Request completed - customer name');
    } catch (err: any) {
      this.infoMsg = err.message;
    }
  }

  async loadServices(cusShortName: string): Promise<void> {
    try {
      const res: HttpResponse<string[]> = await lastValueFrom(this.statsService.findServices(cusShortName));
      if (res.body !== null) {
        this.serviceId = res.body;
        this.selectedServiceId = this.serviceId[0];
        this.statsInput.serviceId = this.selectedServiceId;
      }
      console.log('Request completed - service id');
    } catch (err: any) {
      this.infoMsg = err.message;
    }
  }

  async loadSources(serviceId: string): Promise<void> {
    try {
      const res: HttpResponse<string[]> = await lastValueFrom(this.statsService.findSources(serviceId));
      if (res.body !== null) {
        this.source = res.body;
        this.statsInput.pmSource = `{${this.source.join(',')}}`;
      }
      console.log('Request completed - pm source');
    } catch (err: any) {
      this.infoMsg = err.message;
    }
  }

  async loadServerData(from: string, to: string): Promise<void> {
    this.statsInput.dtFr = from;
    this.statsInput.dtTo = to;

    try {
      const res: HttpResponse<Stats[]> = await lastValueFrom(this.statsService.find(this.statsInput));
      if (res.body !== null) {
        this.stats = res.body;
        this.loadStats();
      }
      console.log('Request completed - server data');
    } catch (err: any) {
      this.infoMsg = err.message;
    }
  }

  loadStats() {
    this.tooltip = {
      enable: true,
    };
    this.primaryXAxis = {
      valueType: 'Category',
    };
    this.primaryYAxis = {
      labelFormat: '{value}',
    };
    this.legendSettings = {
      visible: true,
    };
    this.palette = ['#000000', '#0000FF', '#A9A9A9', '#50A747'];

    this.linkFailSecInData = buildDataset(this.stats, 'linkFailSecIn');
    this.linkFailSecOutData = buildDataset(this.stats, 'linkFailSecOut');
    this.physicalErrCntInData = buildDataset(this.stats, 'physicalErrCntIn');
    this.physicalErrCntOutData = buildDataset(this.stats, 'physicalErrCntOut');
    this.frameChkSeqErrCntInData = buildDataset(this.stats, 'frameChkSeqErrCntIn');
    this.frameChkSeqErrCntOutData = buildDataset(this.stats, 'frameChkSeqErrCntOut');
    this.numOfSecInBinTxLineCardData = buildDataset(this.stats, 'numOfSecInBinTxLineCard');
    this.numOfSecInBinRxLineCardData = buildDataset(this.stats, 'numOfSecInBinRxLineCard');

    this.progressLoader = false;
  }

  async btnDt() {
    this.progressLoader = true;

    const from = this.dtPickerFr.value;
    const to = this.dtPickerTo.value;

    if (!from || !to) {
      alert('Please select both From and To dates.');
      return;
    }

    if (from.getTime() >= to.getTime()) {
      alert('Error: "From" datetime must be earlier than "To" datetime!');
      return;
    }

    this.statsInput.customer = '';
    this.statsInput.dtFr = from.getTime().toString();
    this.statsInput.dtTo = to.getTime().toString();
    this.statsInput.serviceId = this.selectedServiceId;

    await this.loadSources(this.selectedServiceId);
    await this.loadServerData(this.statsInput.dtFr, this.statsInput.dtTo);
  }
}

function buildDataset(stats: Stats[], field: keyof Stats): Object[] {
  const times = Array.from(new Set(stats.map(d => d.time?.substring(0, 16)))).filter(Boolean) as string[];

  const metrics = Array.from(new Set(stats.map(d => d.metric))).filter(Boolean) as string[];

  const result: Object[] = [];

  times.forEach(time => {
    const obj: Record<string, number | string> = { time };
    let hasValue = false;

    metrics.forEach(metric => {
      const stat = stats.find(d => d.metric === metric && d.time?.substring(0, 16) === time);

      const value = stat?.[field];

      if (value !== null && value !== undefined) {
        obj[metric] = value;
        hasValue = true;
      }
    });

    if (hasValue) result.push(obj);
  });

  return result;
}

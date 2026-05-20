import { Component, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { PMService } from './pm.service';
import { PMCustomerModel } from './pmcustomer.model';
import { PMServiceModel } from './pmservice.model';
import {
  GridModule,
  GridComponent,
  SortService,
  PageService,
  ResizeService,
  ToolbarService,
  EditService,
} from '@syncfusion/ej2-angular-grids';
import { ClickEventArgs, SelectEventArgs, Tab } from '@syncfusion/ej2-angular-navigations';
import { PMSourceModel } from './pmsource.model';
import { PMConfigurationModel } from './pmconfiguration.model';
import SharedModule from '../../shared/shared.module';

@Component({
  selector: 'jhi-pm',
  templateUrl: './pm.component.html',
  styleUrls: [],
  imports: [SharedModule, GridModule],
  providers: [PMService, SortService, PageService, ResizeService, ToolbarService, EditService],
})
export class PMComponent implements OnInit {
  public infoMsg!: string;
  public tabObj!: Tab;
  public toolbarSettings = ['Add', 'Edit', 'Update', 'Cancel', 'Search'];
  public editSettings = { allowEditing: true, allowAdding: true };
  public pageSettings = { pageSize: 10 };
  public valueRules = { required: true };

  @ViewChild('customerGrid')
  public customerGrid!: GridComponent;
  public customers: PMCustomerModel[] = [];
  public selectedCustomerIndex: number = 0;
  public selectedCustomer!: PMCustomerModel;
  public customerUpdate: number = 0;

  @ViewChild('serviceGrid')
  public serviceGrid!: GridComponent;
  public services: PMServiceModel[] = [];
  public selectedServiceIndex: number = 0;
  public selectedService!: PMServiceModel;
  public serviceUpdate: number = 0;

  @ViewChild('sourceGrid')
  public sourceGrid!: GridComponent;
  public sources: PMSourceModel[] = [];
  public selectedSourceIndex: number = 0;
  public selectedSource!: PMSourceModel;
  public sourceUpdate: number = 0;

  @ViewChild('configurationGrid')
  public configurationGrid!: GridComponent;
  public configurations: PMConfigurationModel[] = [];
  public selectedConfigurationIndex: number = 0;
  public selectedConfiguration!: PMConfigurationModel;
  public configurationUpdate: number = 0;

  constructor(private pmService: PMService) {}

  ngOnInit() {
    this.loadCustomers();
    this.displayTab();
  }

  loadCustomers() {
    this.pmService.findCustomers().subscribe(
      (res: HttpResponse<any>) => (this.customers = res.body),
      (res: HttpErrorResponse) => this.onError(res.message),
    );
  }

  editCustomer(customer: any) {
    this.pmService.updateOrAddCustomer(customer).subscribe(
      (res: HttpResponse<any>) => this.refreshCustomers(res.body, res.headers),
      (res: HttpErrorResponse) => this.onError(res.message),
    );
  }

  editService(service: any, customerId: any) {
    this.pmService.updateOrAddService(service, customerId).subscribe(
      (res: HttpResponse<any>) => this.refreshServices(res.body, res.headers),
      (res: HttpErrorResponse) => this.onError(res.message),
    );
  }

  editSource(source: any, name: any, serviceId: any) {
    this.pmService.updateOrAddSource(source, name, serviceId).subscribe(
      (res: HttpResponse<any>) => this.refreshSources(res.body, res.headers),
      (res: HttpErrorResponse) => this.onError(res.message),
    );
  }

  editConfiguration(configuration: any, serviceId: any) {
    this.pmService.updateOrAddConfiguration(configuration, serviceId).subscribe(
      (res: HttpResponse<any>) => this.refreshConfigurations(res.body, res.headers),
      (res: HttpErrorResponse) => this.onError(res.message),
    );
  }

  private refreshCustomers(body: any, headers: any) {
    this.infoMsg = headers.get('x-nrmsapp-params');
    this.loadCustomers();
  }

  private refreshServices(body: any, headers: any) {
    this.infoMsg = headers.get('x-nrmsapp-params');
    this.loadServices(this.customers[this.selectedCustomerIndex].id);
  }

  private refreshSources(body: any, headers: any) {
    this.infoMsg = headers.get('x-nrmsapp-params');
    this.loadSources(this.services[this.selectedServiceIndex].serviceId);
  }

  private refreshConfigurations(body: any, headers: any) {
    this.infoMsg = headers.get('x-nrmsapp-params');
    this.loadConfigurations(this.services[this.selectedServiceIndex].serviceId);
  }

  private onError(message: any) {
    this.infoMsg = message;
  }

  loadServices(customerId: any) {
    this.pmService.findServices(customerId).subscribe(
      (res: HttpResponse<any>) => (this.services = res.body),
      (res: HttpErrorResponse) => this.onError(res.message),
    );
  }

  loadSources(serviceId: any) {
    this.pmService.findSources(serviceId).subscribe(
      (res: HttpResponse<any>) => (this.sources = res.body),
      (res: HttpErrorResponse) => this.onError(res.message),
    );
  }

  loadConfigurations(serviceId: any) {
    this.pmService.findConfigurations(serviceId).subscribe(
      (res: HttpResponse<any>) => (this.configurations = res.body),
      (res: HttpErrorResponse) => this.onError(res.message),
    );
  }

  displayTab(): void {
    this.tabObj = new Tab({
      height: 390,
      showCloseButton: false,
      selecting: this.tabSelected,
      items: [
        { header: { text: 'Customer' }, content: '#customer' },
        { header: { text: 'Service' }, content: '#service', disabled: true },
        { header: { text: 'Source' }, content: '#source', disabled: true },
        { header: { text: 'Configuration' }, content: '#configuration', disabled: true },
      ],
    });
    this.tabObj.appendTo('#tab_wizard');
  }

  tabSelected(e: SelectEventArgs): void {
    if (e.isSwiped) {
      e.cancel = true;
    }
  }

  btnNavigation(args: any): void {
    switch (args.target.id) {
      case 'cust_next':
        let selectedCustomer: PMCustomerModel = this.customerGrid.getSelectedRecords()[0];
        if (selectedCustomer == null) {
          this.infoMsg = 'Please select customer below to proceed Next.';
        } else {
          this.infoMsg = '';
          this.loadServices(this.customers[this.selectedCustomerIndex].id);
          this.tabObj.enableTab(0, false);
          this.tabObj.enableTab(1, true);
          this.tabObj.select(1);
        }
        break;
      case 'serv_back':
        this.tabObj.enableTab(1, false);
        this.tabObj.enableTab(0, true);
        this.tabObj.select(0);
        break;
      case 'serv_next':
        let selectedService: PMServiceModel = this.serviceGrid.getSelectedRecords()[0];
        if (selectedService == null) {
          this.infoMsg = 'Please select service below to proceed Next.';
        } else {
          this.infoMsg = '';
          this.loadSources(this.services[this.selectedServiceIndex].serviceId);
          this.tabObj.enableTab(2, true);
          this.tabObj.select(2);
          this.tabObj.enableTab(1, false);
        }
        break;
      case 'sour_back':
        this.tabObj.enableTab(1, true);
        this.tabObj.select(1);
        this.tabObj.enableTab(2, false);
        break;
      case 'sour_next':
        this.loadConfigurations(this.services[this.selectedServiceIndex].serviceId);
        this.tabObj.enableTab(2, false);
        this.tabObj.enableTab(3, true);
        this.tabObj.select(3);

        break;
      case 'conf_back':
        this.tabObj.enableTab(2, true);
        this.tabObj.select(2);
        this.tabObj.enableTab(3, false);
        break;
    }
  }

  toolbarClick(args: ClickEventArgs): void {
    if (args.item.id === 'customerGrid_update') {
      this.customerUpdate = 1;
    }
    if (args.item.id === 'serviceGrid_update') {
      this.serviceUpdate = 1;
    }
    if (args.item.id === 'sourceGrid_update') {
      this.sourceUpdate = 1;
    }
    if (args.item.id === 'configurationGrid_update') {
      this.configurationUpdate = 1;
    }
  }

  rowSelected(e: any): void {
    if (this.tabObj.selectedItem == 0) {
      for (let i = 0; i < this.customers.length; i++) {
        if (this.customers[i].name == e.data.name) {
          this.selectedCustomerIndex = i;
          break;
        }
      }
    } else if (this.tabObj.selectedItem == 1) {
      for (let i = 0; i < this.services.length; i++) {
        if (this.services[i].serviceId == e.data.serviceId) {
          this.selectedServiceIndex = i;
          break;
        }
      }
    } else if (this.tabObj.selectedItem == 2) {
      for (let i = 0; i < this.sources.length; i++) {
        if (
          this.sources[i].customerSid == e.data.customerSid &&
          this.sources[i].pmSource == e.data.pmSource &&
          this.sources[i].nodeId == e.data.nodeId
        ) {
          this.selectedSourceIndex = i;
          break;
        }
      }
    } else if (this.tabObj.selectedItem == 3) {
      for (let i = 0; i < this.configurations.length; i++) {
        if (
          this.configurations[i].serviceId == e.data.serviceId &&
          this.configurations[i].sourceName == e.data.sourceName &&
          this.configurations[i].nodeId == e.data.nodeId
        ) {
          this.selectedConfigurationIndex = i;
          break;
        }
      }
    }
  }

  editComplete(e: any) {
    if (this.tabObj.selectedItem == 0 && this.customerUpdate == 1) {
      this.selectedCustomer = e.data;
      this.editCustomer(this.selectedCustomer);
      this.customerUpdate = 0;
    } else if (this.tabObj.selectedItem == 1 && this.serviceUpdate == 1) {
      this.selectedService = e.data;
      this.editService(this.selectedService, this.customers[this.selectedCustomerIndex].id);
      this.serviceUpdate = 0;
    } else if (this.tabObj.selectedItem == 2 && this.sourceUpdate == 1) {
      this.selectedSource = e.data;
      this.editSource(
        this.selectedSource,
        this.customers[this.selectedCustomerIndex].name,
        this.services[this.selectedServiceIndex].serviceId,
      );
      this.sourceUpdate = 0;
    } else if (this.tabObj.selectedItem == 3 && this.configurationUpdate == 1) {
      this.selectedConfiguration = e.data;
      this.editConfiguration(this.selectedConfiguration, this.services[this.selectedServiceIndex].serviceId);
      this.configurationUpdate = 0;
    }
  }
}

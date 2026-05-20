import {Component, OnInit } from '@angular/core'
import {HttpResponse} from '@angular/common/http'
import { CommonModule } from '@angular/common';
import {FormsModule} from '@angular/forms'
import {ManagereservedcapService} from "./managereservedcap.service";
import { forkJoin } from 'rxjs';

interface ManagereservedcapStatus {
  total: string;
  id: number;
  segmentName: string;
  fiberPair: string;
  customerName: string;
  capacityName: string;
  dlsName: string;
  reserveCapId: number;
  segmentid: number;
  dlsid: number;
  fiberpairid: number;
  capacityid: number;
  capSegFpDlsId: number;
}
 interface ManageCustomerStatus {
  id: number;
  name: String;
}

 interface DlsCapacity {
  sfxTblId: number;
  dlsId: number;
  isReserved: number;
  segmentFpId:number;
  crossTableId:number;
  capacityId:number;
  dlsName: String;
  capacityName : String;
}

 interface ColHeaders {
  capacityName: String;
   segmentName:String;
  fibrepairname : String;
  dlsname: String;
}

@Component({
  selector: 'jhi-managereservedcap',
  templateUrl:'./managereservedcap.component.html',
  styleUrls: ['./managereservedcap.scss'],
  imports:[FormsModule,CommonModule]
})

export class ManagereservedcapComponent implements OnInit {

  errorMessage!: string;
  dlsNamesLoop: any;
  loading!: boolean;
  dlscapdata: any;
  capacityList: any;
  segmentList: any;
  segmentColspan = [1, 1, 2, 4];
  fiberPairColspan = [1, 1, 1, 1, 2, 1, 1, 1];
  fiberPairList: any;
  dlsList: any;
  customer: any;
  filteredCustomers: any;
  data: any;
  editMode: boolean[] = [];
  editcustomerData: any;
  successFlag: any;
  isModalVisible: boolean = false;
  isFormValid:any;
  formData: any = {
    dls16_1_1: '',
    dls11_4_1: '',
    dls14_5_1: '',
    dls54_6_1: '',
    dls27_7_1: '',
    dls57_8_1: '',
    dls67_9_1: '',
    dls_10_1:  '',
    dls16_1_2: '',
    dls11_4_2: '',
    dls14_5_2: '',
    dls54_6_2: '',
    dls27_7_2: '',
    dls57_8_2: '',
    dls67_9_2: '',
    dls_10_2: '',
    dls16_1_3: '',
    dls11_4_3: '',
    dls14_5_3: '',
    dls54_6_3: '',
    dls27_7_3: '',
    dls57_8_3: '',
    dls67_9_3: '',
    dls_10_3: ''
  };

  constructor(
    private managereservedcapService: ManagereservedcapService) {
  }


  ngOnInit(): void {
    this.loadData();
  }

  loadData() {
    this.loading = true;
    this.getDlsCapacity();
    forkJoin(this.managereservedcapService.getGridData(),
      this.managereservedcapService.getGridCustomer(),
      this.managereservedcapService.getDlsCapacity(),
      this.managereservedcapService.getColHeaders())
      .subscribe(
        ([gridData, customerData, dlsCapacityData, colHeadersData]: [HttpResponse<ManagereservedcapStatus[]>, HttpResponse<ManageCustomerStatus[]>,
          HttpResponse<DlsCapacity[]>, HttpResponse<ColHeaders[]>]) => {
          const gridDataBody = gridData.body || [];
          const customerDataBody = customerData.body || [];
          const dlsCapacityDataBody = dlsCapacityData.body || [];
          const colHeadersDataBody = colHeadersData.body || [];
          this.processData(gridDataBody, customerDataBody, dlsCapacityDataBody, colHeadersDataBody);
          this.loading = false;
        },
        (error) => {
          console.error('Error fetching data:', error);
          this.loading = false;
        }
      )

  }

  getDlsCapacity(): void {
    this.managereservedcapService.getDlsCapacity().subscribe(response => {
      const dlsNames: (string | null)[] = response.body
        .filter((item: any) => item.isreserved === 1)
        .map((item: any) => item.dlsname);
      this.dlsNamesLoop = Array.from(new Set(dlsNames));
    });
  }

  processData(gridData: ManagereservedcapStatus[], customerData: ManageCustomerStatus[], dlsCapacityData: DlsCapacity[], colHeadersData: ColHeaders[]): void {
    this.dlscapdata = dlsCapacityData.filter((item:any) => item != null).map(item => ({
      sfxTblId: item.sfxTblId,
      dlsId: item.dlsId,
      isReserved: item.isReserved,
      segmentFpId: item.segmentFpId,
      crossTableId: item.crossTableId,
      capacityId: item.capacityId,
      dlsName: item.dlsName,
      capacityName: item.capacityName
    }));

    this.capacityList = Array.from(new Set(colHeadersData.filter(item => item && item.capacityName).map(item => item.capacityName)));

    const segmentsFilter = Array.from(new Set(colHeadersData.filter(item => item && item.segmentName).map(item => item.segmentName)));
    this.segmentList = segmentsFilter.map((name, index) => ({
      segment: name,
      colspan: this.segmentColspan[index % this.segmentColspan.length] // Using modulo to cycle through segmentColspan array
    }));


    const fiberpairFilter = colHeadersData.filter(item => item && item.fibrepairname).map(item => item.fibrepairname);
    this.fiberPairList = fiberpairFilter.map((name, index) => ({
      fiberpair: name,
      colspan: this.fiberPairColspan[index % this.fiberPairColspan.length] // Using modulo to cycle through segmentColspan array
    }));
    let result = [];
    let skipCount = 0; // Track how many rows to skip

    for (let i = 0; i < this.fiberPairList.length; i++) {
      const item = this.fiberPairList[i];
      if (skipCount > 0) {
        skipCount--;
        this.fiberPairColspan
        continue;
      }
      if (item.colspan > 1) {
        skipCount = item.colspan - 1;
      }
      result.push({
        fiberpair: item.fiberpair,
        colspan: item.colspan
      });

    }
    this.fiberPairList = result;

    this.dlsList = colHeadersData.filter(item => item).map(item => item.dlsname);
    this.dlsNamesLoop = Array.from(new Set(this.dlsList.filter((item: any) => item !== undefined)));
    this.customer = customerData.filter(item => item != null).map(item => ({
      customerName: item.name, customerId: item.id
    }));
    this.filteredCustomers = (this.customer || []).filter((customer: any) =>
      !((gridData || []).some(gridItem =>
        gridItem && customer &&
        gridItem.customerName === customer.customerName
      ))
    );

    this.data = this.customer
      .filter((customer: any) => gridData.some(item => item.customerName === customer.customerName))
      .map((customer: any) => {
        const capacities = this.capacityList.map((capacityName: any) => {
          const dlsNamesWithCapacity = this.dlsNamesLoop.map((dlsName: any) => {
            const exampleItem = gridData.find(item =>
              item.customerName === customer.customerName &&
              item.capacityName === capacityName &&
              item.dlsName === dlsName
            );
            return {
              capSegFpDlsId: exampleItem ? exampleItem.capSegFpDlsId : '',
              dlsName: dlsName,
              total: exampleItem ? (Number(exampleItem.total) === 0 ? null : exampleItem.total) : '',
              reserveCapId: exampleItem ? exampleItem.id : ''

            };
          });
          return {
            capacityName: capacityName,
            dlsNames: dlsNamesWithCapacity
          };
        });
        return {
          customerName: customer.customerName,
          capacities: capacities
        };
      })

  }

  onSubmit() {
    if (!this.formData.customer_id) {
      alert('Please select a customer.');
      return;
    }


    const dlsValues = Object.keys(this.formData).filter(key => key.startsWith('dls') && this.formData[key]);
    if (dlsValues.length === 0) {
      alert('Please select at least one DLS value.');
      return;
    }
    this.isFormValid = true;
    const formData = this.formData;
    const customerId = formData['customer_id'];
    const dataToSend = [];

    for (const key in formData) {
      if (key !== 'customer_id' && formData.hasOwnProperty(key)) {
        const value = formData[key];
        const parts = key.split('_');
        const capSegFpDlsId = parts[1];
        if (value) {
          dataToSend.push({
            customer_id: customerId,
            cap_seg_fp_dls_id: capSegFpDlsId,
            total: value
          });
        }
      }
    }

    dataToSend.forEach((data:any) => {
      this.managereservedcapService.saveData(data).subscribe({
        next: (response) => {
          console.log('Data saved successfully:', response);
        },
        error: (error) => {
          console.error('Error saving data:', error);
        }
      });
    });
    alert('Data saved successfully');
    this.closeModal();
    this.fetchDataCol();
  }

  toggleEditMode(rowIndex: number, customerName: string) {
    this.editMode[rowIndex] = !this.editMode[rowIndex];
    this.editcustomerData = this.data.find((cust: any) => cust.customerName === customerName)
  }

  deleteCustomer(rowIndex: number) {
    const customerNameToFind = this.data[rowIndex].customerName;
    const matchingCustomer = this.customer.find((item:any) => item.customerName === customerNameToFind)
    if (confirm(`Are you sure you want to delete ${customerNameToFind}?`)) {
      this.managereservedcapService.deleteItem(matchingCustomer.customerId).subscribe(
        () => {
          alert('Data Deleted Successfully!');
          this.fetchDataCol();
        },
        (error) => {
          console.error('Error deleting data:', error);
        });
    }
  }

  fetchDataCol() {
    this.loading = true;
    this.loading = true;
    forkJoin(
      this.managereservedcapService.getGridData(),
      this.managereservedcapService.getGridCustomer(),
      this.managereservedcapService.getDlsCapacity(),
      this.managereservedcapService.getColHeaders()
    ).subscribe(
      ([gridData, customerData, dlsCapacityData, colHeadersData]: [HttpResponse<ManagereservedcapStatus[]>, HttpResponse<ManageCustomerStatus[]>,
        HttpResponse<DlsCapacity[]>, HttpResponse<ColHeaders[]>]) => {
        const gridDataBody = gridData.body || [];
        const customerDataBody = customerData.body || [];
        const dlsCapacityDataBody = dlsCapacityData.body || [];
        const colHeadersDataBody = colHeadersData.body || [];
        this.processData(gridDataBody, customerDataBody, dlsCapacityDataBody, colHeadersDataBody);
        this.loading = false;
      },
      (error) => {
        console.error('Error fetching data:', error);
        this.loading = false;
      }
    );
  }

  saveEdit(rowIndex: number) {
    this.editMode[rowIndex] = false;
    const dataToInsert: { customer_id: any; cap_seg_fp_dls_id: any; total: any; }[] = [];
    const dataToSave: { customer_id: any; cap_seg_fp_dls_id: any; total: any; id: any; }[] = [];
    const customerNameToFind = this.data[rowIndex].customerName;
    const matchingCustomer = this.customer.find((item: any) => item.customerName === customerNameToFind)

    this.data[rowIndex].capacities.forEach((capacity: any) => {
      capacity.dlsNames.forEach((dls: any) => {
        let matchingEntry = this.dlscapdata.find(
          (entry: any) => entry.dlsName === dls.dlsName && entry.capacityName === capacity.capacityName
        );
         if (matchingEntry) {
          dls.capSegFpDlsId = matchingEntry.crossTableId;
        }
       });
    });


     this.data[rowIndex].capacities.forEach((capacity: any) => {
       capacity.dlsNames.forEach((dls: any) => {
        if ((dls.reserveCapId === "0" || dls.reserveCapId == '') && dls.total != 0) {
          dataToInsert.push({
            customer_id: matchingCustomer.customerId,
            cap_seg_fp_dls_id: dls.capSegFpDlsId,
            total: dls.total
          });

        } else {
          dataToSave.push({
            customer_id: matchingCustomer.customerId,
            cap_seg_fp_dls_id: dls.capSegFpDlsId,
            total: dls.total,
            id: dls.reserveCapId
          });
        }
      });
     });

    dataToInsert.forEach((data: any) => {
      this.managereservedcapService.saveData(data).subscribe({
        next: (response) => {
          this.successFlag = 1;
        },
        error: (error) => {
          this.successFlag = 0;
        }
      });
    });

    const updateRequests = dataToSave.map(data => this.managereservedcapService.updateData(data));
    forkJoin(updateRequests).subscribe({
      next: (responses) => {
        this.successFlag = 1;
        alert('Data saved successfully');
        this.fetchDataCol();
      },
      error: (err) => {
        this.successFlag = 0;
      }
    });
    this.fetchDataCol();
  }

  cancelEdit(rowIndex: number) {
    this.editMode[rowIndex] = false;
  }

  updateDlsTotal(event: any, rowIndex: number, capacityIndex: number, dlsIndex: number) {
    this.data[rowIndex].capacities[capacityIndex].dlsNames[dlsIndex].total = +event.target.value;
    const inputElement = event.target as HTMLInputElement;
    const newValue = Number(inputElement.value);
    this.data[rowIndex].capacities[capacityIndex].dlsNames[dlsIndex].total = newValue;
    if (capacityIndex === 0 || capacityIndex === 1) {
      this.calculateThirdLoopValues(dlsIndex, rowIndex);
    }
  }
  calculateThirdLoopValues(dlsIndex: number, rowIndex: number): void {
    const total1 = this.data[rowIndex].capacities[0].dlsNames[dlsIndex].total;
    const total2 = this.data[rowIndex].capacities[1].dlsNames[dlsIndex].total;
    this.data[rowIndex].capacities[2].dlsNames[dlsIndex].total = total1 - total2;
  }
  calculateDifferenceNew(field1: string, field2: string, resultField: string): void {
    const value1 = parseFloat(this.formData[field1]);
    const value2 = parseFloat(this.formData[field2]);

    if (!isNaN(value1) && isNaN(value2)) {
      this.formData[resultField] = value1;
    } else if (isNaN(value1) && !isNaN(value2)) {
      this.formData[resultField] = value2;
    } else if (!isNaN(value1) && !isNaN(value2)) {
      this.formData[resultField] = value1 - value2;
    } else {
      this.formData[resultField] = null;
    }
  }

  openModal() {
    this.isModalVisible = true;
    this.isFormValid = false;
  }

  closeModal() {
    this.isModalVisible = false;
    this.formData = {};
  }

}

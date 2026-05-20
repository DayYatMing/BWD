import { Component, OnInit } from '@angular/core';
import { CustomerService } from './customer.service';
import { Customer } from './customer.model';
import { CommonModule } from '@angular/common';
import { RouterModule, Router, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'jhi-customer-delete',
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './customer-delete.component.html',
})
export class CustomerDeleteComponent implements OnInit {
  constructor(
    private customerService: CustomerService,
    private router: Router,
    private route: ActivatedRoute,
  ) {}

  infoMsg: string = '';
  public customer: Customer = new Customer();
  customerId: string = '';

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.customerId = params.get('id')!;
    });

    this.customerService.findId(this.customerId).subscribe({
      next: res => {
        console.log('Status:', res.status);
        console.log('Headers:', res.headers);
        if (res.body !== null) {
          this.customer = res.body;
        }
      },
      error: err => {
        this.infoMsg = err.message;
      },
    });
  }

  public delete() {
    this.customerService.delete(this.customer).subscribe({
      next: res => {
        console.log('Status:', res.status);
        console.log('Headers:', res.headers);
        this.router.navigate(['/services/customer']);
      },
      error: err => {
        this.infoMsg = err.message;
      },
    });
  }
}

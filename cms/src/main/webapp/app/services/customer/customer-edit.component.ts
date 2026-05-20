import { Component, OnInit } from '@angular/core';
import { CustomerService } from './customer.service';
import { Customer } from './customer.model';
import { CommonModule } from '@angular/common';
import { RouterModule, Router, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { EntityService } from '../entity/entity.service';
import { Entity } from '../entity/entity.model';

@Component({
  selector: 'jhi-customer-edit',
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './customer-edit.component.html',
})
export class CustomerEditComponent implements OnInit {
  constructor(
    private customerService: CustomerService,
    private entityService: EntityService,
    private router: Router,
    private route: ActivatedRoute,
  ) {}

  infoMsg: string = '';
  public customer: Customer = new Customer();
  customerId: string = '';
  entities: Entity[] = [];

  ngOnInit() {
    this.loadEntity();

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

  loadEntity() {
    this.entityService.find().subscribe({
      next: res => {
        console.log('Status:', res.status);
        console.log('Headers:', res.headers);
        if (res.body !== null) {
          this.entities = res.body;
        }
      },
      error: err => {
        this.infoMsg = err.message;
      },
    });
  }

  public edit() {
    this.customerService.edit(this.customer).subscribe({
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

  onEntitySelected(name: string) {
    const entity = this.entities.find(e => e.name === name);
    if (entity) {
      this.customer.shortName = entity.shortName;
      this.customer.entityId = entity.id;
    }
  }
}

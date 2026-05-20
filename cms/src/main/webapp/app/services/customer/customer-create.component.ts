import { Component, OnInit } from '@angular/core';
import { CustomerService } from './customer.service';
import { Customer } from './customer.model';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { EntityService } from '../entity/entity.service';
import { Entity } from '../entity/entity.model';
import { UserService } from '../../entities/user/service/user.service';

@Component({
  selector: 'jhi-customer-create',
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './customer-create.component.html',
})
export class CustomerCreateComponent implements OnInit {
  constructor(
    private customerService: CustomerService,
    private entityService: EntityService,
    private userService: UserService,
    private router: Router,
  ) {}

  infoMsg: string = '';
  customer: Customer = new Customer();
  entities: Entity[] = [];
  users: any;

  ngOnInit() {
    this.loadUser();
    this.loadEntity();
  }

  loadUser() {
    this.userService.queryAll().subscribe({
      next: res => {
        console.log('Status:', res.status);
        console.log('Headers:', res.headers);
        if (res.body !== null) {
          this.users = res.body.map(user => user.login).sort();
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

  public new() {
    this.customerService.create(this.customer).subscribe({
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

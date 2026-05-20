import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { EntityService } from './entity.service';
import { Entity } from './entity.model';

@Component({
  selector: 'jhi-entity-create',
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './entity-create.component.html',
})
export class EntityCreateComponent implements OnInit {
  constructor(
    private entityService: EntityService,
    private router: Router,
  ) {}

  infoMsg: string = '';
  entity: Entity = new Entity();

  ngOnInit() {}

  public new() {
    this.entityService.create(this.entity).subscribe({
      next: res => {
        console.log('Status:', res.status);
        console.log('Headers:', res.headers);
        this.router.navigate(['/services/entity']);
      },
      error: err => {
        this.infoMsg = err.message;
      },
    });
  }
}

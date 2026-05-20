import { Component, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import HasAnyAuthorityDirective from '../shared/auth/has-any-authority.directive';

@Component({
  selector: 'jhi-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['contact.css'],
  imports: [MatCardModule, HasAnyAuthorityDirective],
})
export default class ContactComponent implements OnInit {
  contacts = {
    NZHQ: '+64 800 002 600',
    NZHQ_OUTSIDE: '+64 9887 3243',
    USA: '+1 8888 313 339',
    AUSY: '+61 1800 319 388',
    SUPPORTMAIL: 'support@bw-digital.com',
    NZFREE: '+64 800 002 600',
    AUSFREE: '+61 1800 319 388',
    USFREE: '+1 8888 313 339',
    AUSB: '+61 449 516164',
    NZB: '+64 21 593 561',
    INDO: '+62 21 3009 6404',
    INDOFREE: '+62 800 150 3392',
    GRAHAM: '+61 407 910 034',
    GRAHAMMAIL: 'grabruc@bw-digital.com',
    DAVID: '+64 21 597 488',
    DAVIDMAIL: 'davisl@bw-digital.com',
    THASA: '+61 424 775 130',
    THASAMAIL: 'thbal@bw-digital.com',
    JUSTIN: '+64 21 597 375',
    JUSTINMAIL: 'juspak@bw-digital.com',
    DANIEL: '+61 409 906 418',
    DANIELMAIL: 'danimc@bw-digital.com',
    FLORENT: '+65 8136 1498',
    FLORENTMAIL: 'flblo@bw-digital.com',
  };

  ngOnInit() {}

  async copy(value: string) {
    try {
      await navigator.clipboard.writeText(value);
      console.log('Copied successfully!');
    } catch (err) {
      console.error('Failed to copy:', err);
    }
  }
}

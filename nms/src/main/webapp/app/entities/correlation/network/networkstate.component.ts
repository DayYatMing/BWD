import {Component, ElementRef, OnInit, ViewChild, inject, signal} from '@angular/core';
import {NetworkstateService} from "./networkstate.service";
import {Networkstate} from "./networkstate.model";
import {Account} from "../../../core/auth/account.model";
import {AccountService} from "../../../core/auth/account.service";
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { interval, take } from 'rxjs';
import { NgIf } from '@angular/common';

@Component({
  selector: 'jhi-networkstate',
  styleUrls: ['networkstate.css'],
  imports: [NgIf],
  providers: [],
  templateUrl: './networkstate.component.html'
})

export class NetworkstateComponent implements OnInit {
  account = signal<Account | null>(null);
  private readonly destroy$ = new Subject<void>();
  private readonly accountService = inject(AccountService);

  infoMsg: string = "";
  updatedBy: string | undefined;
  networkState: Networkstate = new Networkstate();
  selectedFile: File | null = null;
  countdown: number = 15;
  countdownActive = false;

  constructor(
    private networkstateService: NetworkstateService
  ) {
  }

  ngOnInit(): void {this.accountService
    .getAuthenticationState()
    .pipe(takeUntil(this.destroy$))
    .subscribe(account => this.account.set(account));

    this.updatedBy = this.account()?.login;
    this.loadNetworkstate();
  }

  loadNetworkstate(){
    this.networkstateService.find().subscribe({
      next: res => {
        console.log('Status:', res.status);
        console.log('Headers:', res.headers);
        if (res.body !== null) {
          this.networkState = res.body;
        }
      },
      error: err => {
        this.infoMsg = err.message;
      },
    });
  }

  @ViewChild('fileInput') fileInput!: ElementRef<HTMLInputElement>;

  triggerFileInput(event: Event) {
    event.preventDefault();
    this.fileInput.nativeElement.click();
  }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (!input.files?.length) {
      this.selectedFile = null;
      this.infoMsg = '';
      return;
    }

    this.selectedFile = input.files[0];
    const formData = new FormData();
    formData.append('updatedby', this.updatedBy ?? 'unknown');
    if (this.selectedFile) formData.append('networkimage', this.selectedFile);

    this.networkstateService.upload(formData).subscribe({
      next: res => {
        console.log('Status:', res.status);
        console.log('Headers:', res.headers);

        this.loadNetworkstate();
      },
      complete: () =>{
        this.infoMsg = "Uploaded successfully."
      },
      error: err => {
        this.infoMsg = err.message;
      },
    });
  }

  public downloadFile() {
    if (!this.networkState?.networkimage) return;
    const blob = new Blob(
      [this.networkState.networkimage],
      { type: "image/svg+xml" }
    );
    const url = URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = "network-state.svg";
    a.click();
    URL.revokeObjectURL(url);
  }

  public showNewTab() {
    if (!this.networkState?.networkimage) return;
    const blob = new Blob(
      [this.networkState.networkimage],
      { type: "image/svg+xml" }
    );
    const url = URL.createObjectURL(blob);
    window.open(url, "_blank", "noopener,noreferrer");
  }

  get isReady(): boolean {
    return !!this.networkState?.id && !this.countdownActive;
  }

  startCountdown(action: () => void, withDetails: boolean, event: Event) {
    event.preventDefault();

    if (!this.networkState.id) return;

    this.loadUpdatesNetworkstate(withDetails);

    let c = this.countdown;
    this.countdownActive = true;

    interval(1000)
      .pipe(take(c))
      .subscribe({
        next: () => {
          this.countdown--;
        },
        complete: () => {
          this.countdownActive = false;
          this.countdown = c;
          action();
        },
      });
  }

  loadUpdatesNetworkstate(withDetails: boolean){
    this.networkstateService.process(this.networkState.id, withDetails).subscribe({
      next: res => {
        console.log('Status:', res.status);
        console.log('Headers:', res.headers);
        if (res.body !== null) {
          this.networkState = res.body;
        }
      },
      error: err => {
        this.infoMsg = err.message;
      },
    });
  }

}

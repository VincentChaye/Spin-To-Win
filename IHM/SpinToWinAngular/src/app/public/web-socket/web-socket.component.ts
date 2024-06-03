import { Component, OnInit, OnDestroy, NgZone, PLATFORM_ID, Inject } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { Observable, Observer, Subscription } from 'rxjs';

@Component({
  selector: 'app-web-socket',
  templateUrl: './web-socket.component.html',
  styleUrls: ['./web-socket.component.css']
})
export class WebSocketComponent implements OnInit, OnDestroy {
  public randomNumber: string = '';
  public salonNumber: number | null = null;
  private socket: WebSocket | null = null;
  private observer: Observer<string> | null = null;
  private subscription: Subscription | null = null;

  constructor(
    private ngZone: NgZone,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  ngOnInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      this.connectWebSocket();
    }
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
    if (this.socket) {
      this.socket.close();
    }
  }

  private connectWebSocket() {
    this.socket = new WebSocket('ws://localhost:8888');
    this.socket.onmessage = (event) => this.onMessage(event);
  }

  private onMessage(event: MessageEvent) {
    this.ngZone.run(() => {
      const data = JSON.parse(event.data);
      this.randomNumber = data.randomNumber;
      this.salonNumber = data.salonNumber;
    });
  }
}

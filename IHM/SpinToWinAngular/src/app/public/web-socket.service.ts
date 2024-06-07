import { Injectable } from '@angular/core';
import { Observable, Observer, EMPTY } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private socket: WebSocket | null = null;
  private observer: Observer<any> | null = null;
  private url: string = 'ws://localhost:8888';
  private subscription: Observable<any> | null = null;

  constructor() {}

  connect(): Observable<any> {
    if (typeof WebSocket === 'undefined') {
      console.error('WebSocket is not supported in this environment.');
      return EMPTY; // Retourne un Observable vide
    }
    
    if (!this.subscription || !this.socket) {
      this.subscription = new Observable((observer: Observer<any>) => {
        this.observer = observer;
        this.socket = new WebSocket(this.url);
        this.socket.onmessage = (event) => this.onMessage(event);
        this.socket.onclose = () => this.onClose();
        return () => this.socket?.close();
      });
    }
    return this.subscription;
  }

  private onMessage(event: MessageEvent) {
    if (this.observer) {
      const data = JSON.parse(event.data);
      this.observer.next(data);
    }
  }

  private onClose() {
    if (this.observer) {
      this.observer.complete();
    }
  }
}

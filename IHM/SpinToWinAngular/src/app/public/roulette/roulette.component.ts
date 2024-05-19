import { Component, OnInit, ViewChild, ElementRef, Renderer2, AfterViewInit } from "@angular/core";
import { CommonModule } from "@angular/common";
import { RouterOutlet } from "@angular/router";
import { PlayoutComponent } from "../playout/playout.component";
import { HttpClient } from "@angular/common/http";

@Component({
  selector: "app-roulette",
  standalone: true,
  imports: [RouterOutlet, CommonModule],
  templateUrl: "./roulette.component.html",
  styleUrls: ["./roulette.component.css"],
})
export class RouletteComponent implements OnInit, AfterViewInit {
  paths: string[] = [];
  finalAngle: number = 0;
  tab = [0, 32, 15, 19, 4, 21, 2, 25, 17, 34, 6, 27, 13, 36, 11, 30, 8, 23, 10, 5, 24, 16, 33, 1, 20, 14, 31, 9, 22, 18, 29, 7, 28, 12, 35, 3, 26];
  ballFalling: number | undefined;

  @ViewChild("ball") ball!: ElementRef<SVGCircleElement>;
  @ViewChild("spinButton") spinButton!: ElementRef<HTMLButtonElement>;

  constructor(private httpClient: HttpClient, private renderer: Renderer2, public PLAYERINFO: PlayoutComponent) {
    this.PLAYERINFO.pageCharger = 0;
  }

  ngOnInit() {
    this.generatePaths();
    this.startAnimation();
  }

  ngAfterViewInit() {
    if (this.ball && this.ball.nativeElement && this.spinButton && this.spinButton.nativeElement) {
      this.renderer.listen(this.spinButton.nativeElement, 'click', () => {
        this.startAnimation();
      });
    } else {
      console.error('Error: ball or spinButton reference is undefined or their native elements are undefined.');
    }
  }

  getBall(): Promise<number> {
    const url = 'http://localhost:8000/game/ball';
    return this.httpClient.get<number>(url).toPromise().then(response => {
      if (typeof response === 'number') {
        return response;
      } else {
        throw new Error('Response is not a number');
      }
    }).catch(error => {
      console.error('Error fetching ball number:', error);
      return 0; // Return a default value in case of error
    });
  }

  generatePaths(): void {
    const numSlices = 37;
    const sliceDegree = 360 / numSlices;
    for (let i = 0; i < numSlices; i++) {
      const startAngle = i * sliceDegree - 85;
      const endAngle = startAngle + sliceDegree;
      const largeArc = endAngle - startAngle > 180 ? 1 : 0;

      const start = this.polarToCartesian(100, startAngle);
      const end = this.polarToCartesian(100, endAngle);

      const path = `M 0 0 L ${start.x} ${start.y} A 100 100 0 ${largeArc} 1 ${end.x} ${end.y} L 0 0`;
      this.paths.push(path);
    }
  }

  startAnimation() {
    this.getBall().then(randomSliceIndex => {
      const sliceDegree = 360 / 37;
      this.finalAngle = 1080 + randomSliceIndex * sliceDegree;
      console.log(this.tab[randomSliceIndex]);
      this.animateBall(this.finalAngle, this.tab[randomSliceIndex]);
      
    }).catch(error => {
      console.error('Error during animation:', error);
    });
  }
  
  animateBall(angle: number, ball: number) {
    if (this.ball && this.ball.nativeElement) {
      this.renderer.removeStyle(this.ball.nativeElement, 'transition');
      this.renderer.removeStyle(this.ball.nativeElement, 'transform');
      setTimeout(() => {
        this.renderer.setStyle(
          this.ball.nativeElement,
          'transition',
          'transform 4s ease-out',
        );
        this.renderer.setStyle(
          this.ball.nativeElement,
          'transform',
          `rotate(${angle}deg)`,
        );
      }, 100);
    
      // Écouter la fin de la transition
      this.renderer.listen(this.ball.nativeElement, 'transitionend', () => {
        // Mettre à jour ballFalling une fois que l'animation est terminée
        this.ballFalling = ball; 
      });
    } else {
      console.error('Error: ball reference is undefined or its native element is undefined.');
    }
  }
  

  polarToCartesian(
    radius: number,
    angleInDegrees: number,
  ): { x: number; y: number } {
    const angleInRadians = ((angleInDegrees + 90) * Math.PI) / 180.0;
    return {
      x: radius * Math.cos(angleInRadians),
      y: radius * Math.sin(angleInRadians),
    };
  }

  WichIndiceInTab(numero: number) {
    let i = 0;
    for (i; i <= 36; i++) {
      if (this.tab[i] === numero) {
        return i;
      }
    }
    return -1;
  }
}

import {
  Component,
  OnInit,
  ViewChild,
  ElementRef,
  Renderer2,
  AfterViewInit,
} from "@angular/core";
import { CommonModule } from "@angular/common";
import { RouterOutlet } from "@angular/router";

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

  @ViewChild("ball") ball!: ElementRef<SVGCircleElement>;
  @ViewChild("spinButton") spinButton!: ElementRef<HTMLButtonElement>;

  constructor(private renderer: Renderer2) {}

  ngOnInit() {
    this.generatePaths();
  }

  ngAfterViewInit() {
    this.renderer.listen(this.spinButton.nativeElement, 'click', () => {
      this.startAnimation();
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

  getRandomInt(max: number): number {
    const nbr = Math.floor(Math.random() * max);
    return this.WichIndiceInTab(nbr);
  }

  startAnimation() {
    const randomSliceIndex = this.getRandomInt(38);
    const sliceDegree = 360 / 37;
    this.finalAngle = 1080 + randomSliceIndex * sliceDegree;
    console.log(this.tab[randomSliceIndex]);
    this.animateBall(this.finalAngle);
  }

  animateBall(angle: number) {
    this.renderer.removeStyle(this.ball.nativeElement, "transition");
    this.renderer.removeStyle(this.ball.nativeElement, "transform");
    setTimeout(() => {
      this.renderer.setStyle(
        this.ball.nativeElement,
        "transition",
        "transform 4s ease-out",
      );
      this.renderer.setStyle(
        this.ball.nativeElement,
        "transform",
        `rotate(${angle}deg)`,
      );
    }, 100);
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

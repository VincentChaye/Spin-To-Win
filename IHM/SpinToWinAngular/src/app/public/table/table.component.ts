import { Component, ElementRef } from '@angular/core';
import { PlayoutComponent } from '../playout/playout.component';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css']
})
export class TableComponent {
  selectedToken: { value: number, color: string } | null = null;
  cellTokens: { [key: string]: { count: number, color: string, originalContent: string, originalColor: string } } = {};
  credit: number = 100; //INITIALISATION CREDIT
  actionHistory: { cellId: string, previousCount: number, previousColor: string, tokenValue: number }[] = [];
  previousSelectedTokenElement: HTMLElement | null = null;
  isCreditBlurred: boolean = false;

  constructor(public PLAYERINFO: PlayoutComponent, private elRef: ElementRef) {
    this.PLAYERINFO.pageCharger = 0;
  }

  creditOp(value: number) {
    this.credit += value;
  }

  onTokenClick(value: number, color: string) {
    if (value === 99) { // Check if the clicked token is MAX
      if (this.credit > 0) {
        value = this.credit; // Use all remaining credits
      } else {
        alert("Crédit insuffisant pour placer ce pari !");
        return;
      }
    }
    this.selectedToken = { value, color };
    console.log(`Selected token: ${value} of color ${color}`);

    if (this.previousSelectedTokenElement) {
      this.previousSelectedTokenElement.classList.remove('selected');
    }

    const tokenElements = this.elRef.nativeElement.querySelectorAll('.token');
    tokenElements.forEach((tokenElement: HTMLElement) => {
      if (parseInt(tokenElement.querySelector('text')?.textContent || '0', 10) === value || (value === this.credit && tokenElement.querySelector('text')?.textContent === 'MAX')) {
        tokenElement.classList.add('selected');
        this.previousSelectedTokenElement = tokenElement;
      }
    });
  }

  onCellClick(event: MouseEvent) {
    const target = event.target as HTMLElement;
    if (this.selectedToken && target.tagName === 'TD' && target.id) {
      const cellId = target.id;
  
      // Vérifiez si le crédit est suffisant pour placer le jeton
      if (this.credit < this.selectedToken.value) {
        alert("Crédit insuffisant pour placer ce pari !");
        return;
      }
  
      if (!this.cellTokens[cellId]) {
        this.cellTokens[cellId] = { 
          count: 0, 
          color: this.selectedToken.color, 
          originalContent: target.innerText,
          originalColor: target.style.backgroundColor || '' 
        };
      }
  
      this.actionHistory.push({ 
        cellId, 
        previousCount: this.cellTokens[cellId].count, 
        previousColor: this.cellTokens[cellId].color,
        tokenValue: this.selectedToken.value 
      });
  
      this.cellTokens[cellId].count += this.selectedToken.value;
      this.cellTokens[cellId].color = this.selectedToken.color;
      this.creditOp(-this.selectedToken.value);
  
      this.updateCellDisplay(cellId);
      this.logTokensData();
    }
  }
  

  updateCellDisplay(cellId: string) {
    const cell = document.getElementById(cellId);
    if (cell) {
      const cellData = this.cellTokens[cellId];
      cell.innerHTML = '';

      if (cellData.count > 0 && this.selectedToken) {
        const tokenColor = this.getTokenColor(cellData.count);
        const tokenSvg = this.createTokenSvg(cellData.count, tokenColor);
        tokenSvg.classList.add('token-svg');
        cell.appendChild(tokenSvg);
      } else {
        cell.innerText = cellData.originalContent;
        cell.style.backgroundColor = cellData.originalColor;
      }
    }
  }

  getTokenColor(count: number): string {
    if (count >= 1 && count <= 4) {
      return 'yellow';
    } else if (count >= 5 && count <= 9) {
      return 'orange';
    } else if (count >= 10 && count <= 19) {
      return 'red';
    } else if (count >= 20 && count <= 49) {
      return 'purple';
    } else {
      return 'lightblue';
    }
  }

  createTokenSvg(value: number, color: string): SVGSVGElement {
    const svgNamespace = 'http://www.w3.org/2000/svg';
    const svg = document.createElementNS(svgNamespace, 'svg') as SVGSVGElement;
    svg.setAttribute('viewBox', '0 0 100 100');
    svg.setAttribute('class', 'token');
    svg.setAttribute('width', '50');
    svg.setAttribute('height', '50');

    const circle = document.createElementNS(svgNamespace, 'circle') as SVGCircleElement;
    circle.setAttribute('cx', '50');
    circle.setAttribute('cy', '50');
    circle.setAttribute('r', '45');
    circle.setAttribute('class', 'outer-circle');
    circle.setAttribute('fill', 'black');

    const innerCircle = document.createElementNS(svgNamespace, 'circle') as SVGCircleElement;
    innerCircle.setAttribute('cx', '50');
    innerCircle.setAttribute('cy', '50');
    innerCircle.setAttribute('r', '30');
    innerCircle.setAttribute('fill', color);

    const text = document.createElementNS(svgNamespace, 'text') as SVGTextElement;
    text.setAttribute('x', '50');
    text.setAttribute('y', '60');
    text.setAttribute('font-size', '150%');
    text.setAttribute('text-anchor', 'middle');
    text.setAttribute('class', 'chip-number');
    text.setAttribute('fill', 'black');
    text.textContent = `${value}`;

    const segmentsGroup = document.createElementNS(svgNamespace, 'g') as SVGGElement;
    segmentsGroup.setAttribute('class', 'segments6');

    const createSegment = (rotation: number) => {
      const rect = document.createElementNS(svgNamespace, 'rect') as SVGRectElement;
      rect.setAttribute('x', '47');
      rect.setAttribute('y', '5');
      rect.setAttribute('width', '6');
      rect.setAttribute('height', '10');
      rect.setAttribute('transform', `rotate(${rotation} 50 50)`);
      rect.setAttribute('fill', color);
      return rect;
    };

    for (let i = 0; i < 360; i += 45) {
      const rect = createSegment(i);
      segmentsGroup.appendChild(rect);
    }

    svg.appendChild(circle);
    svg.appendChild(innerCircle);
    svg.appendChild(segmentsGroup);
    svg.appendChild(text);

    return svg;
  }

  getCellCount(cellId: string): number {
    return this.cellTokens[cellId]?.count;
  }

  onRemoveAllTokens() {
    Object.keys(this.cellTokens).forEach(cellId => {
      const cellData = this.cellTokens[cellId];
      this.creditOp(cellData.count);
      cellData.count = 0;
      const cell = document.getElementById(cellId);
      if (cell) {
        cell.style.backgroundColor = cellData.originalColor;
        cell.innerText = cellData.originalContent;
      }
    });
    this.cellTokens = {};
    this.actionHistory = [];
    this.logTokensData();
  }

  onUndoLastAction() {
    const lastAction = this.actionHistory.pop();
    if (lastAction) {
      const { cellId, previousCount, previousColor, tokenValue } = lastAction;
      const cellData = this.cellTokens[cellId];
      cellData.count = previousCount;
      cellData.color = previousColor;
      this.updateCellDisplay(cellId);
      this.creditOp(tokenValue);
      this.logTokensData();
    }
  }

  logTokensData() {
    console.table(this.cellTokens);
  }

  toggleCreditBlur() {
    this.isCreditBlurred = !this.isCreditBlurred;
  }
}

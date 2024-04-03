import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VegastudioComponent } from './vegastudio.component';

describe('VegastudioComponent', () => {
  let component: VegastudioComponent;
  let fixture: ComponentFixture<VegastudioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VegastudioComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(VegastudioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

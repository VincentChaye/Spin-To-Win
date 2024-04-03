import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpintowinComponent } from './spintowin.component';

describe('SpintowinComponent', () => {
  let component: SpintowinComponent;
  let fixture: ComponentFixture<SpintowinComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SpintowinComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SpintowinComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

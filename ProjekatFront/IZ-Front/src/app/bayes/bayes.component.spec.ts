import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BayesComponent } from './bayes.component';

describe('BayesComponent', () => {
  let component: BayesComponent;
  let fixture: ComponentFixture<BayesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BayesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BayesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

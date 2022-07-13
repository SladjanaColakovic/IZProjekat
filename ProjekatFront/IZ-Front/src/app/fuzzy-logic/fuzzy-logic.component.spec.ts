import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FuzzyLogicComponent } from './fuzzy-logic.component';

describe('FuzzyLogicComponent', () => {
  let component: FuzzyLogicComponent;
  let fixture: ComponentFixture<FuzzyLogicComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FuzzyLogicComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FuzzyLogicComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

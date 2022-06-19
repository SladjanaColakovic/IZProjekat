import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ComponentSuggestionComponent } from './component-suggestion.component';

describe('ComponentSuggestionComponent', () => {
  let component: ComponentSuggestionComponent;
  let fixture: ComponentFixture<ComponentSuggestionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ComponentSuggestionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ComponentSuggestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

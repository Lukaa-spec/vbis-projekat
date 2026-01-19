import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchStudentsComponent } from './search-students-component';

describe('SearchStudentsComponent', () => {
  let component: SearchStudentsComponent;
  let fixture: ComponentFixture<SearchStudentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SearchStudentsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SearchStudentsComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { TestBed } from '@angular/core/testing';

import { ServiceStudentService } from './service-student.service';

describe('ServiceStudentService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ServiceStudentService = TestBed.get(ServiceStudentService);
    expect(service).toBeTruthy();
  });
});

import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component } from '@angular/core';
import { JobService } from '../../services/JobService';

@Component({
  selector: 'app-search-students-component',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './search-students-component.html',
  styleUrl: './search-students-component.css',
})
export class SearchStudentsComponent {
  students: any[] = [];
  selectedStudent: any = null;
  loading: boolean = false;

  constructor(private jobService: JobService, private cdr: ChangeDetectorRef) {}

  fetchStudents() {
    this.loading = true;
    this.jobService.getStudentsLookingForJob().subscribe({
      next: (data) => {
        this.students = data;
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        alert("Greška: " + err.error);
        this.loading = false;
      }
    });
  }

  toggleDetails(student: any) {
    this.selectedStudent = this.selectedStudent === student ? null : student;
  }
}

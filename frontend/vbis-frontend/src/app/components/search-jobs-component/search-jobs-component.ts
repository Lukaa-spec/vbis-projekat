import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { JobAd } from '../../models/job';
import { JobService } from '../../services/JobService';

@Component({
  selector: 'app-search-jobs-component',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './search-jobs-component.html',
  styleUrl: './search-jobs-component.css',
})
export class SearchJobsComponent {
  searchQuery: string = '';
  jobs: JobAd[] = [];
  selectedJob: JobAd | null = null;
  error: string = '';
  loading: boolean = false;

  constructor(
    private jobService: JobService,
    private cdr: ChangeDetectorRef
  ) {}

  onSearch() {
    if (!this.searchQuery.trim()) return;
    
    this.loading = true;
    this.error = '';
    this.selectedJob = null;

    this.jobService.search(this.searchQuery).subscribe({
      next: (data) => {
        this.jobs = data;
        this.loading = false;
        if (this.jobs.length === 0) {
          this.error = 'Nema pronađenih oglasa za dati pojam.';
        }
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.error = err.error || 'Došlo je do greške prilikom pretrage.';
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }

  selectJob(job: JobAd) {
    this.selectedJob = job;
  }

}

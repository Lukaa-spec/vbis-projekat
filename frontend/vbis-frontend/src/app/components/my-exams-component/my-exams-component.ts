import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { JobService } from '../../services/JobService';
import { AuthService } from '../../services/AuthService';

@Component({
  selector: 'app-my-exams-component',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './my-exams-component.html',
  styleUrl: './my-exams-component.css',
})
export class MyExamsComponent implements OnInit {
exams: any[] = [];

  constructor(
    private jobService: JobService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.jobService.getMyExams().subscribe({
      next: (data: any[]) => {
        console.log("Stigli ispiti preko my-exams funkcije:", data);
        this.exams = data || [];
        this.cdr.detectChanges(); 
      },
      error: (err) => console.error("Greška pri učitavanju ispita:", err)
    });
  }
  }



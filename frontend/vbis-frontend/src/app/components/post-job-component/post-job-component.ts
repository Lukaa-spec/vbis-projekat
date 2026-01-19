import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { JobAd, JobRequirement } from '../../models/job';
import { LevelOfReadiness, Priority } from '../../models/enums';
import { JobService } from '../../services/JobService';
import { Router } from '@angular/router';

@Component({
  selector: 'app-post-job-component',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './post-job-component.html',
  styleUrl: './post-job-component.css',
})
export class PostJobComponent {
    //Glavni objekat koji ide na backend
  jobAd: JobAd = {
    id: '', 
    title: '',
    requirements: []
  };

  //Pomoćni model za formu unosa jednog zahteva
  newRequirement: JobRequirement = {
    skill: { name: '' },
    priority: Priority.MEDIUM,
    levelOfReadiness: LevelOfReadiness.LOW
  };

  //Liste za dropdown-ove 
  priorities = Object.values(Priority);
  levels = Object.values(LevelOfReadiness);

  constructor(private jobService: JobService, private router: Router, private cdr: ChangeDetectorRef) {}

  // Dodaje zahtev u lokalni niz pre slanja
  addRequirement() {
    if (!this.newRequirement.skill.name.trim()) {
      alert("Unesite ime veštine!");
      return;
    }

    //Duboka kopija 
    const reqToAdd: JobRequirement = {
      skill: { name: this.newRequirement.skill.name },
      priority: this.newRequirement.priority,
      levelOfReadiness: this.newRequirement.levelOfReadiness
    };

    this.jobAd.requirements.push(reqToAdd);

    
    this.newRequirement.skill.name = '';
  }

  //Uklanja zahtev iz niza 
  removeRequirement(index: number) {
    this.jobAd.requirements.splice(index, 1);
  }

  //Finalno slanje na backend
  submitForm() {
   if (!this.jobAd.title.trim() || this.jobAd.requirements.length === 0) {
    alert("Oglas mora imati naslov i bar jednu veštinu!");
    return;
  }

  this.jobService.postJobAd(this.jobAd).subscribe({
    next: (res) => {
      alert("Oglas uspešno objavljen!");
      
      //reset forme
      this.jobAd = {
        id: '',
        title: '',
        requirements: [] 
      };

      this.cdr.detectChanges();

    },
    error: (err) => {
      alert("Greška pri čuvanju: " + err.error);
      this.cdr.detectChanges();
    }
  });
  }
}

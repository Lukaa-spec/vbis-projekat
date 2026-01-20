import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { JobService } from '../../services/JobService';

@Component({
  selector: 'app-my-ads-component',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './my-ads-component.html',
  styleUrl: './my-ads-component.css',
})
export class MyAdsComponent implements OnInit {
  myAds: any[] = [];
  editingAd: any = null; 
  constructor(private jobService: JobService, private cdr: ChangeDetectorRef) {}

  ngOnInit() {
    this.loadAds();
  }

  loadAds() {
    this.jobService.getMyAds().subscribe({
      next: (data) => {
        this.myAds = data;
        this.cdr.detectChanges();
      },
      error: (err) => alert("Greška pri učitavanju: " + err.error)
    });
  }

  onDelete(adId: string) {
    if (confirm("Da li ste sigurni da želite da obrišete ovaj oglas?")) {
      this.jobService.deleteAd(adId).subscribe(() => {
        this.loadAds(); 
      });
    }
  }

  startEdit(ad: any) {
    // Pravimo duboku kopiju (deep copy) oglasa koji menjamo
    this.editingAd = JSON.parse(JSON.stringify(ad));
  }

  cancelEdit() {
    this.editingAd = null;
  }

  saveEdit() {
    const request = {
      adId: this.editingAd.id,
      title: this.editingAd.title,
      requirements: this.editingAd.requirements
    };

    this.jobService.updateAd(request).subscribe({
      next: () => {
        alert("Oglas uspešno izmenjen!");
        this.editingAd = null;
        this.loadAds();
      },
      error: (err) => alert("Greška: " + err.error)
    });
  }


}

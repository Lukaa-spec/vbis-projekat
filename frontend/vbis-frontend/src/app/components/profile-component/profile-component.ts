import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/AuthService';
import { UpdateProfileRequest } from '../../models/UpdateProfileRequest';

@Component({
  selector: 'app-profile-component',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './profile-component.html',
  styleUrl: './profile-component.css',
})
export class ProfileComponent implements OnInit {
  userData: any = {};
  userType: string | null = '';
  loading: boolean = false;

  constructor(
    private authService: AuthService,
    private cdr: ChangeDetectorRef
  ) {}

 ngOnInit() {
    this.userType = localStorage.getItem('userType');
    this.loadProfile();
  }

  loadProfile() {
    this.authService.getProfile().subscribe({
      next: (data) => {
        this.userData = data; 
        if(!this.userData.password) this.userData.password = '';
        this.cdr.detectChanges(); 
      },
      error: (err) =>  {console.error("Greška pri učitavanju", err)
        alert(`Greška na serveru: ${err.status} - ${err.message}`);
      }
      
    });
  }

  onSave() {
    this.loading = true;
    const request: UpdateProfileRequest = {
      email: this.userData.email,
      password: this.userData.password,
      agencyName: this.userData.agencyName,
      lookingForJob: this.userData.lookingForJob
    };

    this.authService.updateProfile(request).subscribe({
      next: (updatedUser) => {
        alert("Uspešno ste ažurirali profil!");
        this.userData = updatedUser;
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        alert("Greška: " + (err.error || "Došlo je do greške"));
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }

}

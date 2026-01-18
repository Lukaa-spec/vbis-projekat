import { ChangeDetectorRef, Component } from '@angular/core';
import { AuthService } from '../../services/AuthService';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register-component',
  imports: [FormsModule, CommonModule],
  templateUrl: './register-component.html',
  styleUrl: './register-component.css',
})
export class RegisterComponent {
  userData = {
    username: '',
    password: '',
    email: '',
    type: 'STUDENT', 
    agencyName: ''
  };
  message = '';
  error = '';

  constructor(private authService: AuthService, private router: Router, private cdr: ChangeDetectorRef) {}

  onRegister() {
    this.error = ''; 
    this.message = '';

    this.authService.register(this.userData).subscribe({
      next: (res) => {
        this.message = res.message;
        setTimeout(() => this.router.navigate(['/login']), 2000);
      },
     error: (err) => {
    console.log("Ceo error objekat:", err); 
    this.error = err.error?.error || "Došlo je do greške na serveru";
    this.cdr.detectChanges();
}
    });
  }
}

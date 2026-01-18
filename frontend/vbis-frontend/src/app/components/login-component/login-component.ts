import { ChangeDetectorRef, Component } from '@angular/core';
import { AuthService } from '../../services/AuthService';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login-component',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './login-component.html',
  styleUrl: './login-component.css',
})
export class LoginComponent {
  credentials = {
    username: '',
    password: ''
  };
  error = '';

  constructor(
    private authService: AuthService, 
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  onLogin() {
    this.error = '';


    this.authService.login(this.credentials).subscribe({
      next: (res) => {
        this.authService.saveToken(res.token); 
        console.log('Token sačuvan!');
        this.router.navigate(['/search-jobs']); 
      },
      error: (err) => {
        this.error = err.error?.error || 'Greška pri prijavi';
        this.cdr.detectChanges();
      }
    });
  }
}

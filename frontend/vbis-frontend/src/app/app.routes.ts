import { Routes } from '@angular/router';
import { LoginComponent } from './components/login-component/login-component';
import { RegisterComponent } from './components/register-component/register-component';
import { SearchJobsComponent } from './components/search-jobs-component/search-jobs-component';

export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'search-jobs', component: SearchJobsComponent },
    { path: '', redirectTo: '/login', pathMatch: 'full' }
];

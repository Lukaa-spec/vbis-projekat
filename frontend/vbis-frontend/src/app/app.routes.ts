import { Routes } from '@angular/router';
import { LoginComponent } from './components/login-component/login-component';
import { RegisterComponent } from './components/register-component/register-component';
import { SearchJobsComponent } from './components/search-jobs-component/search-jobs-component';
import { PostJobComponent } from './components/post-job-component/post-job-component';
import { ProfileComponent } from './components/profile-component/profile-component';
import { SearchStudentsComponent } from './components/search-students-component/search-students-component';

export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'search-jobs', component: SearchJobsComponent },
    { path: 'job-ads', component: PostJobComponent },
    { path: 'profile', component: ProfileComponent },
    { path: 'search-students', component: SearchStudentsComponent },
    { path: '', redirectTo: '/login', pathMatch: 'full' }
];

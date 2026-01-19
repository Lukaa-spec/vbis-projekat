import { JobAd } from "./job";

export interface User {
  username: string;
  email: string;
}

export interface Student extends User {
  examHistory: any[]; 
  skills: string[];
  lookingForJob: boolean;
}

export interface Agency extends User {
  agencyName: string;
  jobAds: JobAd[]; 
}
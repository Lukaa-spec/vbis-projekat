import { LevelOfReadiness, Priority } from "./enums";
import { Skill } from "./skill";

export interface JobRequirement {
  skill: Skill;
  priority: Priority;
  levelOfReadiness: LevelOfReadiness;
}

export interface JobAd {
  id: string;
  title: string;
  agencyName?: string; 
  requirements: JobRequirement[];
}
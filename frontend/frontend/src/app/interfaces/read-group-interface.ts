import {PersonInGroupInterface} from "./person-in-group-interface";

export interface ReadGroupInterface {
  id: string;
  creationTime: string;
  meetingTime: string;
  groupMembers: Array<PersonInGroupInterface>
}

import {PersonInGroupInterface} from "./person-in-group-interface";

export interface GroupInterface {
  id: string;
  creationTime: string;
  meetingTime: string;
  groupMembers: Array<PersonInGroupInterface>
}

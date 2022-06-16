import {GroupInPersonInterface} from "./group-in-person-interface";

export interface ReadPersonInterface {
  id: string;
  firstName: string;
  lastName: string;
  age: number;
  groups: Array<GroupInPersonInterface>;
}

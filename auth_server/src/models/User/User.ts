import { Person } from "../Person";

type passwordType = string;
type roleType = string;
type personType = Person;

export interface UserInterface {
  password: passwordType;
  role: roleType;
  person: personType;
}

export class User {
  password: passwordType;
  role: roleType;
  person: personType;

  constructor(role: roleType, password: passwordType, person: personType) {
    this.role = role;
    this.password = password;
    this.person = person;
  }
}

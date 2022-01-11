type emailType = string;
type usernameType = string;
type passwordType = string;

export interface UserInterface {
  email: emailType;
  username: usernameType;
  password: passwordType;
}

export class User {
  email: emailType;
  username: usernameType;
  password: passwordType;

  constructor(
    email: emailType,
    username: usernameType,
    password: passwordType
  ) {
    this.email = email;
    this.username = username;
    this.password = password;
  }
}

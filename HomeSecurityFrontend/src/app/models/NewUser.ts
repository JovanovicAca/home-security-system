export class NewUser {
    firstName: string;
    lastName: string;
    email: string;
    username: string;
    password: string;
    role: string;
  
    constructor(
      firstName: string,
      lastName: string,
      email: string,
      username: string,
      password: string,
      role: string
    ) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.username = username;
      this.password = password;
      this.role = role;
    }
  }
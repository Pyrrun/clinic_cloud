import { format } from "date-fns";
import { Address } from "./../Address";

type firstNameType = string;
type secondNameType = string;
type lastNameType = string;
type birthDateType = string;
type emailType = string;
type phoneType = string;
type addressType = Address;

export interface PersonInterface {
  firstName: firstNameType;
  secondName: secondNameType;
  lastName: lastNameType;
  birthDate: birthDateType;
  email: emailType;
  phone: phoneType;
  address: addressType;
}

export class Person {
  firstName: firstNameType;
  secondName: secondNameType;
  lastName: lastNameType;
  birthDate: birthDateType;
  email: emailType;
  phone: phoneType;
  address: addressType;

  constructor(
    firstName: firstNameType,
    secondName: secondNameType,
    lastName: lastNameType,
    birthDate: birthDateType,
    email: emailType,
    phone: phoneType,
    address: addressType
  ) {
    this.firstName = firstName;
    this.secondName = secondName;
    this.lastName = lastName;
    this.birthDate = format(new Date(birthDate), "yyyy-MM-dd");
    this.email = email;
    this.phone = phone;
    this.address = address;
  }
}

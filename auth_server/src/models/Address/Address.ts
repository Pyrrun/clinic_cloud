type countryType = string;
type cityType = string;
type streetType = string;
type flatNumberType = number;
type postcodeType = string;

export interface AddressInterface {
  country: countryType;
  city: cityType;
  street: streetType;
  flatNumber: flatNumberType;
  postcode: postcodeType;
}

export class Address {
  country: countryType;
  city: cityType;
  street: streetType;
  flatNumber: flatNumberType;
  postcode: postcodeType;

  constructor(
    country: countryType,
    city: cityType,
    street: streetType,
    flatNumber: flatNumberType,
    postcode: postcodeType
  ) {
    this.country = country;
    this.city = city;
    this.street = street;
    this.flatNumber = flatNumber;
    this.postcode = postcode;
  }
}

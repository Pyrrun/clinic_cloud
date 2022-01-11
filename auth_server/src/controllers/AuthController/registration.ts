import { app_user } from "@prisma/client";
// import { User as UserModel } from "@prisma/client";
import { Request, Response } from "express";
import { Address as AddressModel } from "../../models/Address";
import { Person as PersonModel } from "../../models/Person";
import { User as UserModel } from "../../models/User";
import { Specializations, User } from "../../repository";
import { hashPassword, response, responseException } from "../../helpers";
// import jwt from "jsonwebtoken";
// import { findUserByUsername, createNewUser } from "../../db/user";
// import { responseException, response, hashPassword } from "../../helpers";
// import { User } from "../../services/User";
// import { wrappedSendMail } from "../../utilities/emailService";

// firstName: "Imie"
// lastName: "Nazwisko"
// secondName: "drugieImie"
// password: "asdASD123!"
// email: "asd@asd.pl"
// phone: "123123123"
// birthdate: "2022-01-11"
// city: "wwa"
// country: "Polska"
// flatNumber: "123"
// postcode: "12-312"
// street: "asd"
// specializationIds: (3) [3, 2, 1]
// registrationType: "doctorType"

export default async function (req: Request, res: Response) {
  const {
    // address data
    country,
    city,
    street,
    flatNumber,
    postcode,
    registrationType,
    // person data
    firstName,
    secondName,
    lastName,
    birthdate,
    email,
    phone,
    // user data
    role,
    password,
  } = req.body;

  const isUserDoctor = registrationType === "doctorType";
  if (isUserDoctor && req.body.specializationIds.length === 0) {
    return responseException(res, 400, "Specialization has to be defined");
  }

  const userAddress: AddressModel = new AddressModel(
    country,
    city,
    street,
    flatNumber,
    postcode
  );

  const userPerson: PersonModel = new PersonModel(
    firstName,
    secondName,
    lastName,
    birthdate,
    email,
    phone,
    userAddress
  );

  const hashedPassword: string = await hashPassword(password);
  const appUser: UserModel = new UserModel(role, hashedPassword, userPerson);

  try {
    const user: app_user | null = await User.create(appUser);

    console.log(user);
    let specializationsData = [];
    if (isUserDoctor) {
      await Specializations.addToDoctor(req.body.specializationIds, user.id);
      specializationsData = req.body.specializationIds;
    }

    return response(res, 200, {
      ...user,
      specializations: specializationsData,
    });
  } catch (err) {
    return responseException(res, 400, `Internal server error ${err}`);
  }

  // const result: UserModel | null = await findUserByUsername(username);
  // if (result) {
  //   return responseException(res, 400, "User already exists");
  // }
  // const hashedPassword: string = await hashPassword(password);
  // const user: UserModel = await createNewUser(
  //   new User(email, username, hashedPassword)
  // );
  // const accessToken: string = jwt.sign({ userId: user.id }, "secret");
  // const emailResponse: unknown = await sendVerificationEmail(email, user.id);
  // if (!emailResponse) {
  //   return responseException(res, 400, `Send email error`);
  // }
  // return response(res, 200, { lolo: "lolo" });
}

// async function sendVerificationEmail(
//   email: string,
//   id: number
// ): Promise<unknown> {
//   return await wrappedSendMail({
//     from: "admin@admin.pl",
//     to: email,
//     subject: "Registration confirmation",
//     html: `<a href=\"localhost:3000/auth/verifyAccount?id=${id}\"><button>Verify account</button></a>`,
//   });
// }

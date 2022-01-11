import { app_user, person } from "@prisma/client";
import { Request, Response } from "express";
import jwt from "jsonwebtoken";
// import argon2 from "argon2";
import { findPersonByEmail } from "../../db/person";
import { finUserByPersonId } from "../../db/user";
import { responseException, response } from "../../helpers";

export default async function (req: Request, res: Response) {
  const { email, password } = req.body;
  const person: person | null = await findPersonByEmail(email);

  if (!person) {
    return responseException(res, 400, "User not found");
  }

  const user: app_user | null = await finUserByPersonId(person.id);

  console.log(user);
  if (!user?.enabled) {
    return responseException(
      res,
      400,
      "User account is not verified. Check your mailbox and verify your account"
    );
  }

  try {
    console.log("password", password);
    console.log("user.password"), user.password;
    const passwordMatch: boolean =
      "$2a$10$7lNH0TPp06OzQJdyw4ql4.WP2RegPsevAPqurbKId0csokW9FRDfO" ===
      password;
    // await argon2.verify(user.password, password);
    // console.log(passwordMatch);
    if (passwordMatch) {
      const accessToken: string = jwt.sign({ userId: user.id }, "secret");

      return response(res, 200, {
        ...user,
        accessToken,
      });
    }
    return responseException(res, 400, "Invalid password");
  } catch (err) {
    return responseException(res, 400, `Internal server error ${err}`);
  }
}

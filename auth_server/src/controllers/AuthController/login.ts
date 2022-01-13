import { app_user, person } from "@prisma/client";
import { Request, Response } from "express";
import jwt from "jsonwebtoken";
import argon2 from "argon2";
import { Person, User } from "../../repository";
import { responseException, response } from "../../helpers";

export default async function (req: Request, res: Response) {
  const { email, password } = req.body;
  const person: person | null = await Person.findByEmail(email);

  if (!person) {
    return responseException(res, 400, "User not found");
  }

  const user: app_user | null = await User.findByPersonId(person.id);

  if (!user?.enabled) {
    return responseException(
      res,
      400,
      "User account is not verified. Check your mailbox and verify your account"
    );
  }

  try {
    const passwordMatch = await argon2.verify(user.password, password);
    if (passwordMatch) {
      const accessToken: string = jwt.sign(
        { userEmail: email },
        process.env.JWT_SECRET || "secret"
      );

      return response(res, 200, {
        ...user,
        ...person,
        accessToken,
      });
    }
    return responseException(res, 400, "Invalid password");
  } catch (err) {
    return responseException(res, 400, `Internal server error ${err}`);
  }
}

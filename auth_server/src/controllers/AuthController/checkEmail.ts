import { person } from "@prisma/client";
import { Request, Response } from "express";
import { Person } from "../../repository";
import { responseException, response } from "../../helpers";

export default async function (req: Request, res: Response) {
  const { email } = req.body;
  const person: person | null = await Person.findByEmail(email);

  if (!person) {
    return response(res, 200, {});
  }

  return responseException(
    res,
    400,
    `User already exists with email: ${email}`
  );
}

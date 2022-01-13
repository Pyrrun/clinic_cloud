import { person } from "@prisma/client";
import { Request, Response } from "express";
import jwt from "jsonwebtoken";
import { responseException } from "../helpers";
import { Person } from "../repository";

export const checkJwt = async (req: Request, res: Response) => {
  if (!req.headers["authorization"]) {
    return responseException(res, 400, "Missing authorization");
  }

  const authHeader: string = req.headers["authorization"];
  const authHeaderValues: Array<string> = authHeader.split(" ");
  const authMethod: string = authHeaderValues[0];
  const accessToken: string = authHeaderValues[1];

  if (!authMethod || !accessToken) {
    return responseException(res, 400, "Invalid auth header");
  } else if (authMethod !== "Bearer") {
    return responseException(res, 400, "Invalid auth method");
  }

  let tokenBody: any;

  try {
    tokenBody = jwt.verify(accessToken, "secret");
  } catch (err) {
    return responseException(res, 400, "Invalid token");
  }

  if (!tokenBody?.userEmail) {
    return responseException(res, 400, "Invalid token");
  }

  const person: person | null = await Person.findByEmail(tokenBody?.userEmail);

  if (!person) {
    return responseException(res, 400, "Invalid token");
  }

  return tokenBody.userEmail;
};

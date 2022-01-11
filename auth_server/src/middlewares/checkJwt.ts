import { PrismaClient } from "@prisma/client";
import { Request, Response, NextFunction } from "express";
import jwt from "jsonwebtoken";
import { responseException } from "src/helpers";

const prisma = new PrismaClient();

export const checkJwt = async (
  req: Request,
  res: Response,
  next: NextFunction
) => {
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

  if (!tokenBody.userId) {
    return responseException(res, 400, "Invalid token");
  }

  const user = await prisma.user.findUnique({
    where: {
      id: tokenBody.userId,
    },
  });

  if (!user) {
    return responseException(res, 400, "User does not exist");
  }

  req.User = user;

  return next();
};

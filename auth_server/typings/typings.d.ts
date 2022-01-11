import { app_user, person } from "@prisma/client";
export {};

declare global {
  namespace Express {
    interface Request {
      app_user?: app_user;
      person?: person;
    }
  }
}

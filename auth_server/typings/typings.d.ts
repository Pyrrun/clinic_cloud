import { app_user, person, specialization } from "@prisma/client";
export {};

declare global {
  namespace Express {
    interface Request {
      specialization?: specialization;
      app_user?: app_user;
      person?: person;
    }
  }
}

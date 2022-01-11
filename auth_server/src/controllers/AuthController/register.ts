// import { User as UserModel } from "@prisma/client";
// import { Request, Response } from "express";
// import jwt from "jsonwebtoken";
// // import { findUserByUsername, createNewUser } from "../../db/user";
// import { responseException, response, hashPassword } from "../../helpers";
// import { User } from "../../services/User";
// import { wrappedSendMail } from "../../utilities/emailService";

// export default async function (req: Request, res: Response) {
//   const { email, username, password } = req.body;

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
// }

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

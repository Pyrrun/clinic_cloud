// import { User } from "@prisma/client";
// import { Request, Response } from "express";
// import { responseException, response } from "../../helpers";
// import { findUserById, updateUser } from "../../db/user";

// export default async function (req: Request, res: Response) {
//   const userId: number = Number(req.query.id);

//   if (!userId) {
//     return responseException(res, 400, "Invalid user id");
//   }

//   const user: User | null = await findUserById(userId);

//   if (!user) {
//     return responseException(res, 404, "User not found");
//   }

//   const updatedUser: User = await updateUser(userId, { verify: true });

//   return response(res, 200, updatedUser);
// }

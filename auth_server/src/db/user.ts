import { app_user, PrismaClient } from "@prisma/client";
// import { UserInterface } from "src/services/User";

const prisma = new PrismaClient();

// export async function findUserByUsername(
//   username: string
// ): Promise<app_user | null> {
//   return await prisma.app_user.findUnique({
//     where: {
//       username: username,
//     },
//   });
// }

export async function findUserById(userId: number): Promise<app_user | null> {
  return await prisma.app_user.findUnique({
    where: {
      id: userId,
    },
  });
}

export async function finUserByPersonId(
  personId: number
): Promise<app_user | null> {
  return await prisma.app_user.findUnique({
    where: {
      person_id: personId,
    },
  });
}

// export async function findUserByEmail(email: string): Promise<app_user | null> {
//   return await prisma.app_user.findUnique({
//     where: {
//       email: email,
//     },
//   });
// }

// export async function createNewUser(user: UserInterface): Promise<app_user> {
//   return await prisma.app_user.create({
//     data: user,
//   });
// }

export async function updateUser(
  userId: number,
  data: Object
): Promise<app_user> {
  return await prisma.app_user.update({
    where: {
      id: userId,
    },
    data: data,
  });
}

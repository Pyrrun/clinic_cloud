import { person, PrismaClient } from "@prisma/client";

const prisma = new PrismaClient();

export async function findPersonByEmail(email: string): Promise<person | null> {
  return await prisma.person.findFirst({
    where: {
      email: email,
    },
  });
}

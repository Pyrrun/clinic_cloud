import { person, PrismaClient } from "@prisma/client";

const prisma = new PrismaClient();

async function findByEmail(email: string): Promise<person | null> {
  return await prisma.person.findFirst({
    where: {
      email: email,
    },
  });
}

export { findByEmail };

import { specialization, PrismaClient } from "@prisma/client";

const prisma = new PrismaClient();

async function read(): Promise<specialization[] | null> {
  return await prisma.specialization.findMany();
}

async function addToDoctor(
  specializations: number[],
  userId: number
): Promise<any> {
  const data: { app_user_id: number; specialization_id: number }[] =
    specializations.map((specializationId: number) => ({
      app_user_id: userId,
      specialization_id: specializationId,
    }));

  return await prisma.doctor_specialization.createMany({ data: data });
}

export { read, addToDoctor };

import { app_user, PrismaClient } from "@prisma/client";
import { UserInterface } from "src/models/User";
import { getUserRoleKey } from "./../helpers/roles";

const prisma = new PrismaClient();

async function findById(userId: number): Promise<app_user | null> {
  return await prisma.app_user.findUnique({
    where: {
      id: userId,
    },
  });
}

async function findByPersonId(personId: number): Promise<app_user | null> {
  return await prisma.app_user.findUnique({
    where: {
      person_id: personId,
    },
  });
}

export async function create(user: UserInterface): Promise<app_user> {
  const { person } = user;
  const { address, ...personData } = person;

  const addressRes = await prisma.address.create({
    data: {
      country: address.country,
      city: address.city,
      street: address.street,
      flat_number: Number(address.flatNumber),
      postcode: address.postcode,
    },
  });

  const personRes = await prisma.person.create({
    data: {
      first_name: personData.firstName,
      second_name: personData.secondName,
      last_name: personData.lastName,
      birthdate: new Date(personData.birthDate),
      email: personData.email,
      phone: personData.phone,
      address_id: addressRes.id,
    },
  });

  return await prisma.app_user.create({
    data: {
      password: user.password,
      role: getUserRoleKey(user.role),
      enabled: true,
      person_id: personRes.id,
    },
  });
}

async function updateUser(userId: number, data: Object): Promise<app_user> {
  return await prisma.app_user.update({
    where: {
      id: userId,
    },
    data: data,
  });
}

export { findById, findByPersonId, updateUser };

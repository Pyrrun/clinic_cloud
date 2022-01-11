import { specialization } from "@prisma/client";
import { Request, Response } from "express";
import { Specializations } from "../../repository";
import { responseException, response } from "../../helpers";

export default async function (req: Request, res: Response) {
  const specializations: specialization[] | null = await Specializations.read();

  if (!specializations || specializations?.length === 0) {
    return responseException(res, 404, "Specializations resources not found");
  }

  return response(res, 200, { specializations: specializations });
}

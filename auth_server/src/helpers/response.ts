import { Response } from "express";

export function response(res: Response, status: number, data: Object) {
  return res.status(status).json({ success: true, data: data });
}

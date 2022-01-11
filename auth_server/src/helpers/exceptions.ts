import { Response } from "express";

export function responseException(
  res: Response,
  status: number,
  message: string
) {
  return res.status(status).json({ success: false, error: message });
}

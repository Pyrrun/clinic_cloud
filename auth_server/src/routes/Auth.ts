import { Router } from "express";
import { AuthController } from "../controllers";

export const router = Router();

router.post("/login", AuthController.login);
router.post("/email", AuthController.checkEmail);
// router.post("/register", AuthController.register);
// router.post("/verifyAccount", AuthController.verifyAccount);

export default router;

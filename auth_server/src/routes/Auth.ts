import { Router } from "express";
import { AuthController } from "../controllers";
import { body } from "express-validator";
export const router = Router();

router.post("/login", AuthController.login);

router.post(
  "/registration",
  body("firstName")
    .notEmpty()
    .isString()
    .isLength({ max: 20 })
    .not()
    .equals("chuj"),
  body("lastName").notEmpty().isString().isLength({ max: 30 }),
  body("secondName").notEmpty().isString().isLength({ max: 30 }),
  body("password").isStrongPassword().notEmpty(),
  body("email").isEmail().notEmpty(),
  body("phone").notEmpty(),
  body("birthdate").notEmpty(),
  body("city").notEmpty().isString(),
  body("country").notEmpty().isString(),
  body("flatNumber").notEmpty().isString(),
  body("postcode").notEmpty().isString(),
  body("street").notEmpty().isString(),
  body("registrationType").notEmpty().isIn(["doctorType, receptionistType"]),
  body("specializationIds"),
  AuthController.registration
);

router.post("/email", AuthController.checkEmail);

router.get("/specializations", AuthController.specializations);
// router.post("/verifyAccount", AuthController.verifyAccount);

export default router;

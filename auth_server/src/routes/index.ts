import { Router } from "express";
import auth from "./Auth";

const routes = Router();

routes.use("/auth", auth);

export default routes;

import loginFnc from "./login";
import checkEmailFnc from "./checkEmail";
import specializations from "./specializations";
import registrationFnc from "./registration";
// import verifyAccountFnc from "./verifyAccount";

const AuthController = {
  login: loginFnc,
  registration: registrationFnc,
  // verifyAccount: verifyAccountFnc,
  checkEmail: checkEmailFnc,
  specializations: specializations,
};

export default AuthController;

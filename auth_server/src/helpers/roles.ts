const USER_ROLES = {
  doctorType: "DOCTOR",
  receptionistType: "RECEPTION_WORKER",
};

export const getUserRoleKey = (key: string): string => {
  const role = Object.entries(USER_ROLES).find((elem) => elem[0] === key);
  if (role) {
    return role[1];
  }
  return USER_ROLES.doctorType;
};

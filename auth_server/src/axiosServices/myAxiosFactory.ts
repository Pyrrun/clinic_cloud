import axios from "axios";

const myAxiosFactory = () => (options: any) => {
  let headers = {
    "Access-Control-Allow-Origin": "*",
    "Content-Type": "application/json",
    Accept: "application/json",
  };

  return axios.create({
    baseURL: `${process.env.BACKEND_URL}`,
    headers,
    ...options,
  });
};

const myAxios = myAxiosFactory();

// Export `myAxios`
export default myAxios;

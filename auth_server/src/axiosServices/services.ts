import myAxios from "./myAxiosFactory";

const servicesFactory = (myAxios: any) => ({
  ["post"]: (url: any, payload = {}, options = {}) => {
    let axios = myAxios(options);

    return axios.post(url, payload);
  },
  ["put"]: (url: any, payload = {}, options = {}) => {
    let axios = myAxios(options);

    return axios.put(url, payload);
  },
  ["patch"]: (url: any, payload = {}, options = {}) => {
    let axios = myAxios(options);

    return axios.patch(url, payload);
  },
  ["delete"]: (url: any, payload = {}, options = {}) => {
    let axios = myAxios(options);

    return axios.delete(url, payload);
  },
  ["get"]: (url: any, payload = {}, options = {}) => {
    let axios = myAxios(options);

    return axios.get(url);
  },
});

const services = (method: string) => {
  const service = Object.entries(servicesFactory(myAxios)).find(
    (m) => m[0] === method
  );
  if (!service) {
    throw new Error("Invalid axios method!!!");
  }
  return service[1];
};

export default services;

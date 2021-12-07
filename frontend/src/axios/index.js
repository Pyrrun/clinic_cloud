import axios from 'axios'

export const tokenAxios = axios.create({
    baseURL: `${process.env.VUE_APP_BASE_URL}`,

})

tokenAxios.interceptors.request.use(
    function (config) {
        config.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem('user')
        return config
    })



export default axios.create({
    baseURL: `${process.env.VUE_APP_BASE_URL}`
})



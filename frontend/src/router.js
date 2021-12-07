import Vue from 'vue';
import Router from 'vue-router';
import Registration from "@/components/Registration/Registration";
import Confirmation from "@/components/Registration/Confirmation/Confirmation";
import Login from "@/components/Login/Login";
// import { tokenAxios } from "@/axios";
import PanelSelector from "@/components/Panels/PanelSelector";

Vue.use(Router);

const RouterVue = new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [
        {
            path: '/register',
            name: 'register',
            component: Registration
        },
        {
            path: '/confirm/:token',
            name: 'confirm',
            component: Confirmation,
            meta: {
                reload: false,
                transitionName: 'fade'
            }

        },
        {
            path: '',
            name: 'panelSelector',
            // meta: {
            //     requiresAuth: true
            // },
            component: PanelSelector
        },
        {
            path: '/login',
            name: 'login',
            component: Login,
            // beforeEnter: (to, from, next) => {
            //     tokenAxios.get('jwt').then(() => next({ name: 'panelSelector' }))
            //         .catch((error) => {
            //             console.log(error)
            //             next()
            //         })

            // },
            meta: {
                transitionName: 'slide'
            },

        }


    ],
    props: {
        default: true
    },


});

// RouterVue.beforeEach((to, from, next) => {
//     if (to.matched.some(record => record.meta.requiresAuth)) {
//         if (localStorage.getItem('user') === null || localStorage.getItem('role') === null) {
//             next({ name: 'login' })
//         } else {
//             tokenAxios.get('jwt').then(() => next())
//                 .catch((error) => {
//                     console.log(error)
//                     next({ name: 'login' })
//                 })
//         }
//     } else {
//         next()
//     }
// })

export default RouterVue;
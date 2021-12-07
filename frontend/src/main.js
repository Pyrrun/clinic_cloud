import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify';
import axios from 'axios';
import VueAxios from 'vue-axios';
import vueDebounce from 'vue-debounce'
import lodash from 'lodash'
import {store} from './store'
import router from './router'
import VueToast from 'vue-toast-notification';
import 'vue-toast-notification/dist/theme-sugar.css';
import VueMask from 'v-mask'


Vue.use(VueMask, {
  placeholders: {
    'X' : /[1-9]?/
  }
});
Vue.use(VueToast)
Vue.use(vueDebounce)
Vue.config.productionTip = false
Vue.use(VueAxios, axios)
Vue.use(lodash)



new Vue({
  vuetify,
  store,
  router,
  render: h => h(App)
}).$mount('#app')

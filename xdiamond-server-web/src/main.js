import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './assets/js/store'
import Ajax from './assets/js/tool/ajax'

Vue.config.productionTip = false
Vue.prototype.ajax = Ajax;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

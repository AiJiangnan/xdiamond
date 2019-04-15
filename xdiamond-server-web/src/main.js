import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './assets/js/store'
import Ajax from './assets/js/tool/ajax'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.config.productionTip = false
Vue.prototype.ajax = Ajax;
Vue.use(ElementUI);

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

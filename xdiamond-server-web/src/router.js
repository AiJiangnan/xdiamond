import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);

export default new Router({
  routes: [
    { path: '/', name: 'home', component: () => import('./views/Home.vue') },
    { path: '/config', name: 'config', component: () => import('./views/project/Config.vue') },
    { path: '/user', name: 'user', component: () => import('./views/system/User.vue') },
    { path: '/group', name: 'group', component: () => import('./views/system/Group.vue') },
    { path: '/role', name: 'role', component: () => import('./views/system/Role.vue') },
    { path: '/menu', name: 'menu', component: () => import('./views/system/Menu.vue') },
  ]
})

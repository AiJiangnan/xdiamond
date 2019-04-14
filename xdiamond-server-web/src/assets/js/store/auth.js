import { Storage } from '../tool';

/**
 * Auth 用户权限验证状态
 *
 * @type {{state: {token: boolean}, getters: {}, mutations: {login(*): void, logout(*): void}, actions: {login(*, *): void, logout(*, *): void}}}
 */
const AUTH_KEY = 'auth';

const auth = {
  state: {
    token: !!Storage.getSession(AUTH_KEY)
  },
  getters: {},
  mutations: {
    login(state) {
      Storage.setSession(AUTH_KEY, true);
      state.token = true;
    },
    logout(state) {
      Storage.removeSession(AUTH_KEY);
      state.token = false;
    }
  },
  actions: {
    login(context, data) {
      context.commit('login')
    },
    logout(context, data) {
      context.commit('logout')
    }
  }
};

export default auth
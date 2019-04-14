import Vue from 'vue'
import Vuex from 'vuex'
import method from './module'

Vue.use(Vuex);

/**
 * 加载Vuex模块
 *
 * @type {Store}
 */
const store = new Vuex.Store({
  modules: method
});
if (module.hot) {
  // 使 action 和 mutation 成为可热重载模块
  module.hot.accept(['./module'], () => {
    // 获取更新后的模块
    // 因为 babel 6 的模块编译格式问题，这里需要加上 `.default`

    const newModuleA = require('./module').default;
    // 加载新模块
    store.hotUpdate({
      modules: method
    })
  })
}

export default store
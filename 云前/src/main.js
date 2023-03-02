import Vue from 'vue'
import App from './App.vue'
//引入ElementUI组件库
import ElementUI from 'element-ui'
//引入ElementUI全部样式
import 'element-ui/lib/theme-chalk/index.css'
//引入VueRouter
import VueRouter from 'vue-router'
// 引入jquery
import jquery from "jquery";
import Video from 'video.js'
import 'video.js/dist/video-js.css'
Vue.prototype.$ = jquery;

//引入路由器
import router from './router'
Vue.config.productionTip = false
//应用插件
Vue.use(VueRouter)
// 应用ElemenUI
Vue.use(ElementUI);
new Vue({
  render: h => h(App),
  router
}).$mount('#app')

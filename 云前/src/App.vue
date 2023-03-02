<template>
<div>
  <router-view></router-view>
</div>
</template>

<script>
import Login from './components/Login'
import Home from './components/Home'
import './assets/fontawesome/css/all.css'
export default {
  name: 'App',
  components: {Login,Home}
}
</script>

<style>
*{
  padding: 0;
  margin: 0;
}
a{
  text-decoration:none;
  font-weight:550;
  color: black;
}
</style>

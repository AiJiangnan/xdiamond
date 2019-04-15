<template>
  <el-container style="height:100%;">
    <el-header class="header">
      <h2 style="margin:0 40px;line-height:60px;color:#fff;">XDiamond配置中心</h2>
    </el-header>
    <el-container>
      <el-aside width="260px" class="side">
        <Menu/>
      </el-aside>
      <el-container class="main-cont">
        <el-main>
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
    <el-footer height="30px" class="footer">2017-{{new Date().getFullYear()}} &copy; CodeArtist</el-footer>
  </el-container>
</template>

<script>
import Menu from "./components/home/Menu";
export default {
  components: { Menu },
  created() {}
};
</script>


<style>
html,
body {
  margin: 0;
  height: 100%;
}

#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

.main-cont {
  height: 100%;
  background-color: #EBEEF5;
}

.header {
  background-color: #409eff;
}

.side {
  background-color: #DCDFE6;
}

.footer {
  color: #fff;
  background: #606266;
  text-align: center;
  line-height: 30px;
}
</style>

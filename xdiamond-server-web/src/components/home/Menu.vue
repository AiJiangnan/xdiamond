<template>
  <el-menu :router="true">
    <el-submenu index="1">
      <template slot="title">
        <i class="el-icon-menu"></i>
        <span>项目管理</span>
      </template>
      <el-menu-item-group>
        <el-menu-item index="config">配置管理</el-menu-item>
      </el-menu-item-group>
    </el-submenu>
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-setting"></i>
        <span>系统管理</span>
      </template>
      <el-menu-item-group>
        <el-menu-item index="user">用户管理</el-menu-item>
        <el-menu-item index="group">组管理</el-menu-item>
        <el-menu-item index="role">角色管理</el-menu-item>
        <el-menu-item index="menu">菜单管理</el-menu-item>
      </el-menu-item-group>
    </el-submenu>
  </el-menu>
</template>

<script>
export default {
  methods: {}
};
</script>

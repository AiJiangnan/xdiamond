<template>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
      <span>码匠</span>
      <el-button style="float: right; padding: 3px 0" type="text" @click="about">关于</el-button>
    </div>
    <div>Code Artist</div>
  </el-card>
</template>

<script>
export default {
  methods: {
    about: () => {
      alert("About");
    }
  }
};
</script>

<style scoped>
.box-card {
  width: 480px;
}
</style>


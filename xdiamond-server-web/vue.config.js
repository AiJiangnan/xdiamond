module.exports = {
  devServer: {
    proxy: {
      '/': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        ws: false
      }
    },
    port: 8088
  },
  productionSourceMap: false,
  outputDir: '../xdiamond-server/src/main/resources/static'
};
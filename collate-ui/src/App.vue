<template>
  <div id="app">
    <router-view />
    <theme-picker />
  </div>
</template>

<script>
import ThemePicker from "@/components/ThemePicker";
import watermark from "watermark-dom";

export default {
  name: "App",
  components: { ThemePicker },
  metaInfo() {
    return {
      title: this.$store.state.settings.dynamicTitle && this.$store.state.settings.title,
      titleTemplate: title => {
        return title ? `${title} - ${process.env.VUE_APP_TITLE}` : process.env.VUE_APP_TITLE
      }
    }
  },
  mounted() {
    // 加载水印
    // const username = "暂未交付，禁止使用 Auth: coder-itl";
    // watermark.load({ watermark_txt: username + "水印" });
  },
};
</script>
<style scoped>
#app .theme-picker {
  display: none;
}
</style>

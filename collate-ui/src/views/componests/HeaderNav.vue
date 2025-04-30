<template>
<!--   顶部导航栏 -->
  <el-header class="app-header">
    <div class="header-content">
      <img
        class="app-logo"
        src="@/assets/logo/logo.png"
        alt="错题管理系统 Logo"
      />
      <el-menu default-active="2" mode="horizontal" background="#f5f7fa">
        <el-menu-item index="1">
          <i class="el-icon-document"></i>
          <span @click="goToQuestion">全部题目</span>
        </el-menu-item>
        <el-menu-item index="2">
          <i class="el-icon-warning"></i>
          <span>我的错题</span>
        </el-menu-item>
        <el-menu-item index="3">
          <i class="el-icon-notebook-2"></i>
          <span>错题笔记</span>
        </el-menu-item>
        <el-menu-item index="5">
          <i class="el-icon-data-line"></i>
          <span>错题统计</span>
        </el-menu-item>
      </el-menu>
      <el-button type="text" size="small" class="login-btn">登录</el-button>
    </div>
  </el-header>
</template>
<script lang="ts">
</script>

<style scoped lang="scss">
.app-container {
  padding: 0;
  transition: all 0.2s;
  min-height: 100vh;
  background-color: #f5f7fa;
}

.app-header {
  position: sticky;
  /* 吸顶定位 */
  top: 0;
  /* 吸顶位置为页面顶部 */
  height: 69px;
  /* 设置导航栏高度 */
  z-index: 1000;
  /* 保证导航栏层级较高，避免被其他元素遮挡 */
  background-color: #fff;
}



.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
  padding: 0 20px;
}

.app-logo {
  height: 40px;
}

.el-menu {
  margin: 0;
}

.el-menu-item {
  font-size: 14px;
}

.login-btn {
  margin-left: 20px;
}


.el-menu {
  background: none !important;
  padding: 20px 0 !important;
}

.el-menu--horizontal > .el-menu-item:not(.is-disabled):hover,
.el-menu--horizontal > .el-menu-item:not(.is-disabled):focus {
  background: none;
  color: rgb(0, 13, 255);
  font-weight: bold;
}

.el-menu.el-menu--horizontal{
  border-bottom: none !important;
}
</style>

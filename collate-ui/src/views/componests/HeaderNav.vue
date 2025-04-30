<template>
  <el-header class="app-header">
    <div class="header-content">
      <img
        class="app-logo"
        src="@/assets/logo/logo.png"
        alt="错题管理系统 Logo"
      />
      <!-- 中央对齐的导航菜单 -->
      <el-menu
        :default-active="activeMenu"
        mode="horizontal"
        background="#f5f7fa"
        class="menu"
        router
      >
        <el-menu-item index="/question">
          <i class="el-icon-document"></i>
          <span>全部题目</span>
        </el-menu-item>
        <el-menu-item index="/my-mistake">
          <i class="el-icon-warning"></i>
          <span>我的错题</span>
        </el-menu-item>
        <el-menu-item index="/notes">
          <i class="el-icon-notebook-2"></i>
          <span>错题笔记</span>
        </el-menu-item>
        <el-menu-item index="/stats">
          <i class="el-icon-data-line"></i>
          <span>错题统计</span>
        </el-menu-item>
        <el-menu-item index="/back/index">
          <i class="el-icon-setting"></i>
          <span>进入后台</span>
        </el-menu-item>
      </el-menu>
      <!-- 登录或用户信息显示 -->
      <div v-if="!isLoggedIn">
        <el-button
          type="text"
          size="small"
          class="login-btn"
          @click="redirectToLogin"
        >
          登录
        </el-button>
      </div>
      <div v-else>
        <el-button type="text" size="small" class="login-btn">
          {{ userName }}
        </el-button>
      </div>
    </div>
  </el-header>
</template>
<script>
export default {
  name: 'AppHeader',
  data() {
    return {
      activeMenu: this.$route.path, // 跟踪当前路由，初始化高亮菜单
      isLoggedIn: false,            // 用户登录状态
      userName: '张三'              // 用户名，假设已登录时有此数据
    };
  },
  methods: {
    // 跳转到登录页
    redirectToLogin() {
      this.$router.push("/back/index");
    }
  },
  watch: {
    $route(to) {
      this.activeMenu = to.path; // 路由变化时更新 activeMenu
    }
  }
}
</script>
<style scoped lang="scss">
.app-header {
  height: 69px;
  background-color: #fff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 32px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  color: #1f2d3d;
}

.header-content {
  display: flex;
  align-items: center;
  width: 100%;
  justify-content: space-between;
}
.app-logo {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 32px;
  padding: 2px;
  border: 2px solid white;

  /* 白色渐变荧光效果 */
  box-shadow: 0 0 10px 3px rgba(255, 255, 255, 0.7), 0 0 20px 5px rgba(255, 255, 255, 0.4);

  /* 增加动画效果 */
  animation: neonGlow 1.5s ease-in-out infinite alternate;
}

/* 白色渐变荧光动画 */
@keyframes neonGlow {
  0% {
    box-shadow: 0 0 10px 3px rgba(255, 255, 255, 0.7), 0 0 20px 5px rgba(255, 255, 255, 0.4);
    transform: scale(1);
  }
  50% {
    box-shadow: 0 0 20px 6px rgba(255, 255, 255, 1), 0 0 25px 8px rgba(73, 154, 236, 0.7);
    transform: scale(1.05);
  }
  100% {
    box-shadow: 0 0 10px 3px rgba(255, 255, 255, 0.7), 0 0 20px 5px rgba(90, 194, 241, 0.62);
    transform: scale(1);
  }
}


.el-menu {
  flex: 1;
  background: transparent !important;
  height: 100%;
  line-height: 69px;
  border-bottom: none;
  text-align: center;
  margin-left: 120px;
  box-sizing: border-box;
}

.el-menu-item {
  font-size: 16px;
  padding: 0 20px;
}

.login-btn {
  margin-left: 20px;
  font-size: 14px;
  color: #409EFF;
  padding: 6px 12px;
}

.el-menu--horizontal > .el-menu-item.is-active {
  color: #409EFF;
  font-weight: bold;
}

</style>

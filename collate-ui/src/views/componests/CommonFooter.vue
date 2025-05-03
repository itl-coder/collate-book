<script>
export default {
  name: "CommonFooter"
}
</script>

<template>
  <el-footer>
    <div class="app-wrapper">
      <!-- 底部版权信息 -->
      <footer class="footer">
        <div class="footer-container">
          <div class="copyright">
            ©2025 错题本系统 | 提升学习效率，从整理错题开始
          </div>
          <div class="footer-links">
            <ul>
              <li><a href="/about-us" target="_blank">关于我们</a></li>
              <li><a href="/privacy-policy" target="_blank">隐私政策</a></li>
              <li>
                <a href="/terms-of-service" target="_blank">使用条款</a>
              </li>
              <li><a href="/contact" target="_blank">联系我们</a></li>
            </ul>
          </div>
          <div class="social-media">
            <a
              href="https://www.weibo.com"
              target="_blank"
              class="social-icon weibo"
            >微博</a
            >
            <a
              href="https://www.qq.com"
              target="_blank"
              class="social-icon qq"
            >QQ</a
            >
            <a
              href="https://www.wechat.com"
              target="_blank"
              class="social-icon wechat"
            >微信</a
            >
          </div>
        </div>
      </footer>
    </div>
  </el-footer>
</template>

<style scoped lang="scss">

.footer {
  background-color: #12151a;
  padding: 20px;
  text-align: center;
  font-size: 14px;
  font-weight: bold;
  color: hsla(0, 0%, 100%, 0.8);
}

.el-footer {
  padding: 0 !important;
}

.footer .copyright {
  margin-bottom: 10px;
  font-size: 16px;
}

.footer-links ul {
  list-style: none;
  padding: 0;
  margin: 10px 0;
}

.footer-links ul li {
  display: inline-block;
  margin: 0 15px;
}

.footer-links a {
  color: hsla(0, 0%, 100%, 0.8);
  text-decoration: none;
}

.footer-links a:hover {
  text-decoration: underline;
}

</style>

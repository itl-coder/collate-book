<template>
  <div>
    <el-container class="app-container">
      <!-- 顶部导航栏 -->
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

      <div class="banner">
        <!-- 轮播图 -->
        <el-carousel class="app-carousel" height="580px">
          <el-carousel-item v-for="item in swipperList" :key="item.id">
            <image-preview :src="item.imageUrl"  :width="1200" :height="580" />
          </el-carousel-item>
        </el-carousel>
        <el-container class="banner">
          <!-- 主体内容区 -->
          <el-main class="app-main">
            <el-card shadow="never" class="main-card">
              <div slot="header" class="card-header">
                <span class="card-title">我的错题</span>
                <el-button
                  v-if="isMobile"
                  size="mini"
                  @click="toggleSidebar"
                  class="sidebar-toggle"
                >
                  <i
                    :class="isCollapse ? 'el-icon-s-unfold' : 'el-icon-s-fold'"
                  ></i>
                </el-button>
              </div>

              <!-- 搜索区域 -->
              <div class="search-area">
                <el-input
                  v-model="search"
                  placeholder="输入题目关键词"
                  clearable
                  class="search-input"
                  @keyup.enter.native="fetchData"
                >
                  <el-button
                    slot="append"
                    icon="el-icon-search"
                    @click="fetchData"
                  ></el-button>
                </el-input>
              </div>

              <!-- 错题卡片列表 -->
              <div class="card-list" >
                <el-row :gutter="20">
                  <el-col
                    :xs="24"
                    :sm="12"
                    v-for="collate in collateList" :key="collate.id"
                  >
                    <el-card
                      shadow="always"
                      class="question-card"
                      :class="{ 'has-image': collate.image }"
                    >
                      <!-- 题目图片展示 -->
                      <div class="question-image" v-if="collate.image">
                        <el-image
                          :src="item.image"
                          fit="cover"
                          :preview-src-list="[item.image]"
                          class="question-image-content"
                        ></el-image>
                      </div>

                      <div class="question-title">题目：</div>
                      <div class="question-content">
                        {{ collate.questionContent }}
                      </div>

                      <div class="answer-block">
                        <span class="label">我的答案：</span>
                        <!-- 错误答案 -->
                        <span class="wrong-answer" :title="collate.answer">{{
                            truncateText(collate.answer)
                          }}</span>
                      </div>

                      <div class="answer-block">
                        <span class="label">正确答案：</span>
                        <span
                          class="correct-answer"
                          :title="collate.correctAnswer"
                        >{{ truncateText(collate.correctAnswer) }}</span
                        >
                      </div>

                      <div class="card-footer">
                        <el-dropdown
                          trigger="click"
                          @command="handleImportanceChange(collate, $event)"
                        >
                          <el-tag
                            :type="tagType(collate.tagId)"
                            size="small"
                            class="importance-tag"
                            :class="'level-' + collate.tagId"
                          >
                            <i class="el-icon-warning"></i>
                            {{ getImportanceText(collate.tagId) }}
                            <i class="el-icon-arrow-down el-icon--right"></i>
                          </el-tag>
                          <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item
                              command="1"
                              :class="{
                                'is-selected': collate.tagId === 1,
                              }"
                            >
                              <i class="el-icon-info"></i> {{collate.tagName}}
                            </el-dropdown-item>
                            <el-dropdown-item
                              command="2"
                              :class="{
                                'is-selected': collate.tagId === 2,
                              }"
                            >
                              <i class="el-icon-warning"></i> 重要
                            </el-dropdown-item>
                            <el-dropdown-item
                              command="3"
                              :class="{
                                'is-selected': collate.tagId === 3,
                              }"
                            >
                              <i class="el-icon-error"></i> 非常重要
                            </el-dropdown-item>
                          </el-dropdown-menu>
                        </el-dropdown>

                        <div class="action-buttons">
                          <el-button
                            type="text"
                            size="small"
                            @click="showDetail(collate)"
                            class="detail-btn"
                          >
                            <i class="el-icon-view"></i> 详情
                          </el-button>
                          <el-button
                            type="text"
                            size="small"
                            @click="viewNote(collate)"
                            class="note-btn"
                          >
                            <i class="el-icon-document"></i> 笔记
                          </el-button>
                          <el-button
                            type="text"
                            size="small"
                            @click="editItem(collate)"
                            class="edit-btn"
                          >
                            <i class="el-icon-edit"></i> 编辑
                          </el-button>
                        </div>
                      </div>
                    </el-card>
                  </el-col>
                </el-row>

                <!-- 空状态提示 -->
                <el-empty
                  v-if="tableData.length === 0"
                  description="暂无错题数据"
                  class="empty-tip"
                ></el-empty>
              </div>

              <!-- 分页 -->
              <div class="pagination-area" v-if="tableData.length > 0">
                <el-pagination
                  background
                  layout="prev, pager, next, jumper"
                  :total="total"
                  :page-size="pageSize"
                  :current-page="currentPage"
                  @current-change="handlePageChange"
                  :pager-count="isMobile ? 3 : 5"
                  prev-text="上一页"
                  next-text="下一页"
                ></el-pagination>
              </div>
            </el-card>
          </el-main>
        </el-container>

        <!-- 详情弹窗 -->
        <el-dialog
          :visible.sync="detailVisible"
          :title="'错题详情 - ' + currentItem.subject"
          width="70%"
          top="5vh"
          class="detail-dialog"
        >
          <div class="dialog-content">
            <!-- 图片展示区 -->
            <div class="dialog-image-area" v-if="currentItem.image">
              <el-image
                :src="currentItem.image"
                fit="contain"
                class="dialog-image"
                :preview-src-list="[currentItem.image]"
              ></el-image>
            </div>

            <!-- 主要内容区 -->
            <div
              class="dialog-main"
              :class="{ 'has-image': currentItem.image }"
            >
              <div class="dialog-section">
                <h3 class="dialog-section-title">
                  <i class="el-icon-question"></i> 题目内容
                </h3>
                <div class="dialog-section-content">
                  {{ currentItem.question_content }}
                </div>
              </div>

              <div class="dialog-section">
                <h3 class="dialog-section-title">
                  <i class="el-icon-warning"></i> 我的答案
                </h3>
                <div class="dialog-section-content wrong-answer">
                  {{ currentItem.wrong_answer }}
                </div>
              </div>

              <div class="dialog-section">
                <h3 class="dialog-section-title">
                  <i class="el-icon-success"></i> 正确答案
                </h3>
                <div class="dialog-section-content correct-answer">
                  {{ currentItem.correct_answer }}
                </div>
              </div>

              <div class="dialog-meta">
                <div class="meta-item">
                  <span class="meta-label"
                  ><i class="el-icon-collection-tag"></i> 标签：</span
                  >
                  <el-tag
                    v-for="(tag, index) in currentItem.tags"
                    :key="index"
                    size="small"
                    type="info"
                    class="meta-tag"
                  >
                    {{ tag }}
                  </el-tag>
                </div>

                <div class="meta-item">
                  <span class="meta-label"
                  ><i class="el-icon-trophy"></i> 重要等级：</span
                  >
                  <el-dropdown
                    trigger="click"
                    @command="handleImportanceChange(currentItem, $event)"
                  >
                    <el-tag
                      :type="tagType(currentItem.importance_level)"
                      size="small"
                      :class="'level-' + currentItem.importance_level"
                    >
                      <i class="el-icon-warning"></i>
                      {{ getImportanceText(currentItem.importance_level) }}
                      <i class="el-icon-arrow-down el-icon--right"></i>
                    </el-tag>
                    <el-dropdown-menu slot="dropdown">
                      <el-dropdown-item
                        command="1"
                        :class="{
                          'is-selected': currentItem.importance_level === 1,
                        }"
                      >
                        <i class="el-icon-info"></i> 一般
                      </el-dropdown-item>
                      <el-dropdown-item
                        command="2"
                        :class="{
                          'is-selected': currentItem.importance_level === 2,
                        }"
                      >
                        <i class="el-icon-warning"></i> 重要
                      </el-dropdown-item>
                      <el-dropdown-item
                        command="3"
                        :class="{
                          'is-selected': currentItem.importance_level === 3,
                        }"
                      >
                        <i class="el-icon-error"></i> 非常重要
                      </el-dropdown-item>
                    </el-dropdown-menu>
                  </el-dropdown>
                </div>

                <div class="meta-item">
                  <span class="meta-label"
                  ><i class="el-icon-notebook-2"></i> 科目：</span
                  >
                  <span>{{ currentItem.subject }}</span>
                </div>
              </div>
            </div>
          </div>
        </el-dialog>
      </div>
    </el-container>

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
  </div>
</template>

<script>
import {getSwipperList} from "@/api/carousel/swipper";
import {frontListAnswer} from "@/api/errorbook/answer";

export default {
  data() {
    return {
      collateList: [],
      swipperList:[],
      search: "",
      tableData: [],
      currentPage: 1,
      pageSize: 4,
      isCollapse: false,
      isMobile: false,
      detailVisible: false,
      currentItem: {
        question_content: "",
        wrong_answer: "",
        correct_answer: "",
        importance_level: 0,
        subject: "",
        tags: [],
        image: "",
      },
    };
  },
  computed: {
    total() {
      return this.tableData.length;
    },
    paginatedData() {
      const start = (this.currentPage - 1) * this.pageSize;
      return this.tableData.slice(start, start + this.pageSize);
    },
  },
  methods: {
    async getCollateData(){
      let query = {
        userId:1,
        subjectName:''
      }
      const res=  await frontListAnswer(query)
      console.log("getCollateData res: ",res)
      this.collateList = res.data
    },
    goToQuestion(){
      this.$router.push("/question")
    },
    async fetchSwipperData(){
      const res = await getSwipperList()
      this.swipperList = res.data
      console.log("fetchSwipperData: ",res)
    },

    fetchData() {
      // 模拟数据
      this.tableData = [
        {
          id: 1,
          question_content:
            "牛顿第一定律内容是？物体保持静止或匀速直线运动状态的性质称为惯性。",
          wrong_answer: "静止状态下会继续静止",
          correct_answer:
            "物体保持静止或匀速直线运动状态，除非有外力迫使它改变这种状态",
          importance_level: 3,
          subject: "物理",
          tags: ["力学", "基础概念"],
        },
        {
          id: 2,
          question_content: "标准大气压下，水的沸点是多少摄氏度？",
          wrong_answer: "80度",
          correct_answer: "100度",
          importance_level: 2,
          subject: "化学",
          tags: ["物态变化"],
        },
        {
          id: 3,
          question_content: "光合作用的化学方程式是什么？",
          wrong_answer: "H₂O + O₂ → CO₂ + 葡萄糖",
          correct_answer: "6CO₂ + 6H₂O → C₆H₁₂O₆ + 6O₂",
          importance_level: 3,
          subject: "生物",
          tags: ["植物生理", "重要反应"],
        },
        {
          id: 4,
          question_content: "中国历史上第一个封建王朝是？",
          wrong_answer: "商朝",
          correct_answer: "夏朝",
          importance_level: 1,
          subject: "历史",
          tags: ["中国古代史"],
        },
        {
          id: 5,
          question_content: "水的化学式是什么？",
          wrong_answer: "HO",
          correct_answer: "H₂O",
          importance_level: 2,
          subject: "化学",
          tags: ["化学式"],
        },
      ];
    },
    handlePageChange(page) {
      this.currentPage = page;
      window.scrollTo({ top: 0, behavior: "smooth" });
    },
    tagType(level) {
      switch (level) {
        case 1:
          return "";
        case 2:
          return "warning";
        case 3:
          return "danger";
        default:
          return "info";
      }
    },
    getImportanceText(level) {
      switch (level) {
        case 1:
          return "一般";
        case 2:
          return "重要";
        case 3:
          return "非常重要";
        default:
          return "未分级";
      }
    },
    truncateText(text, length = 20) {
      if (!text) return "";
      return text.length > length ? text.substring(0, length) + "..." : text;
    },
    showDetail(item) {
      this.currentItem = JSON.parse(JSON.stringify(item));
      this.detailVisible = true;
    },
    viewNote(row) {
      this.$notify({
        title: "查看笔记",
        message: `正在查看「${this.truncateText(row.question_content)}」的笔记`,
        type: "info",
      });
    },
    editItem(row) {
      this.$notify({
        title: "编辑错题",
        message: `正在编辑「${this.truncateText(row.question_content)}」`,
        type: "success",
      });
    },
    toggleSidebar() {
      this.isCollapse = !this.isCollapse;
    },
    checkIsMobile() {
      this.isMobile = window.innerWidth < 768;
      if (this.isMobile) {
        this.isCollapse = true;
      }
    },
    handleImportanceChange(item, level) {
      item.importance_level = parseInt(level);
      this.$message.success(
        `已将「${this.truncateText(
          item.question_content
        )}」的重要等级修改为${this.getImportanceText(level)}`
      );
    },
  },
  mounted() {
    this.fetchSwipperData();

    this.fetchData();
    this.getCollateData();
    this.checkIsMobile();
    window.addEventListener("resize", this.checkIsMobile);
  },
  beforeDestroy() {
    window.removeEventListener("resize", this.checkIsMobile);
  },
};
</script>
<style lang="scss" scoped>
.app-container {
  min-height: 100vh;
  padding: 0;
  transition: all 0.2s;
}

.app-sidebar {
  border-right: 1px solid #e6e6e6;
  transition: all 0.3s ease;

  .el-menu {
    border-right: none;
  }

  .el-menu-item {
    font-size: 14px;
    transition: all 0.2s;

    &:hover {
      color: #ff4d00;
    }

    i {
      color: #0080ff;
    }
  }

  .el-menu-item.is-active {
    background-color: red;
    color: #000;
    font-weight: 500;
  }
}

.app-main {
  padding: 20px 0 !important;

  .main-card {
    min-height: calc(100vh - 90px);
  }

  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;

    .card-title {
      font-size: 18px;
      font-weight: 600;
      color: #333;
    }

    .sidebar-toggle {
      padding: 5px;
      font-size: 16px;
    }
  }
}

.search-area {
  margin-bottom: 20px;

  .search-input {
    max-width: 400px;

    ::v-deep .el-input-group__append {
      background-color: #409eff;
      color: white;
      border: none;
      cursor: pointer;

      &:hover {
        opacity: 0.9;
      }
    }
  }
}

.card-list {
  margin-top: 15px;

  .question-card {
    margin-bottom: 20px;
    border-radius: 8px;
    border: 1px solid #ebeef5;
    transition: all 0.3s ease;
    position: relative;
    overflow: hidden;
    height: 100%;
    display: flex;
    flex-direction: column;

    &:hover {
      transform: translateY(-3px);
      box-shadow: inset 0 -1px 0 var(--line_regular) !important;
    }

    .question-image {
      height: 160px;
      margin: -20px -20px 15px -20px;
      overflow: hidden;
      background-color: #f5f7fa;
      display: flex;
      align-items: center;
      justify-content: center;

      .question-image-content {
        width: 100%;
        height: 100%;
        object-fit: cover;
        transition: transform 0.3s;
        cursor: pointer;

        &:hover {
          transform: scale(1.05);
        }
      }
    }

    // 无图片时的布局
    &:not(.has-image) {
      .question-title {
        margin-top: 10px;
      }
    }

    .question-title {
      font-weight: 600;
      margin-bottom: 8px;
      font-size: 16px;
      color: #333;
    }

    .question-content {
      padding: 8px 0 12px;
      font-size: 15px;
      line-height: 1.6;
      color: #555;
      white-space: pre-wrap;
      border-bottom: 1px dashed #eee;
      flex-grow: 1;
    }

    .answer-block {
      margin-top: 12px;
      font-size: 14px;
      line-height: 1.5;
      display: flex;
      flex-wrap: wrap;

      .label {
        font-weight: 500;
        color: #409eff;
        margin-right: 5px;
        min-width: 70px;
      }

      .wrong-answer {
        color: #f56c6c;
        flex: 1;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
      }

      .correct-answer {
        color: #67c23a;
        flex: 1;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
      }
    }

    .card-footer {
      margin-top: 15px;
      padding-top: 10px;
      display: flex;
      justify-content: space-between;
      align-items: center;
      flex-wrap: wrap;
      border-top: 1px dashed #eee;

      .importance-tag {
        border-radius: 4px;
        padding: 0 8px;
        height: 26px;
        line-height: 26px;
        cursor: pointer;
        transition: all 0.2s;

        &:hover {
          opacity: 0.9;
          transform: translateY(-1px);
        }

        i {
          margin-right: 4px;
        }

        &.level-1 {
          background-color: #f4f4f5;
          color: #909399;
          border-color: #d3d4d6;
        }

        &.level-2 {
          background-color: #fdf6ec;
          color: #e6a23c;
          border-color: #f5dab1;
        }

        &.level-3 {
          background-color: #fef0f0;
          color: #f56c6c;
          border-color: #fbc4c4;
        }
      }

      .action-buttons {
        display: flex;

        .detail-btn {
          color: #909399;

          &:hover {
            color: #4facfe;
          }
        }

        .note-btn {
          color: #909399;

          &:hover {
            color: #409eff;
          }
        }

        .edit-btn {
          color: #909399;

          &:hover {
            color: #67c23a;
          }
        }
      }
    }
  }

  .empty-tip {
    padding: 40px 0;

    ::v-deep .el-empty__description p {
      color: #999;
    }
  }
}

.pagination-area {
  margin-top: 25px;
  display: flex;
  justify-content: center;

  ::v-deep .el-pagination {
    .btn-prev,
    .btn-next {
      padding: 0 10px;
      border-radius: 4px;
      border: 1px solid #dcdfe6;
      transition: all 0.2s;

      &:hover {
        color: #409eff;
        border-color: #c6e2ff;
        background-color: #ecf5ff;
      }
    }

    .el-pager {
      li {
        border-radius: 4px;
        margin: 0 5px;
        transition: all 0.2s;

        &:hover {
          color: #409eff;
        }

        &.active {
          background-color: #409eff;
          color: white;
        }
      }
    }

    .el-pagination__jump {
      margin-left: 10px;

      .el-input {
        width: 60px;

        .el-input__inner {
          text-align: center;
        }
      }
    }
  }
}

/* 详情弹窗样式 */
.detail-dialog {
  ::v-deep .el-dialog {
    border-radius: 12px;
    box-shadow: 0 5px 30px rgba(0, 0, 0, 0.15);

    .el-dialog__header {
      border-bottom: 1px solid #eee;
      padding: 15px 20px;

      .el-dialog__title {
        font-size: 18px;
        font-weight: 600;
        color: #333;
      }
    }

    .el-dialog__body {
      padding: 20px;
    }
  }

  .dialog-content {
    display: flex;
    max-height: 60vh;
    overflow: auto;

    .dialog-image-area {
      flex: 0 0 40%;
      padding-right: 20px;
      display: flex;
      align-items: center;

      .dialog-image {
        width: 100%;
        height: 300px;
        border-radius: 8px;
        object-fit: contain;
        background-color: #f5f7fa;
        cursor: pointer;
        transition: transform 0.3s;

        &:hover {
          transform: scale(1.02);
        }
      }
    }

    .dialog-main {
      flex: 1;

      &.has-image {
        padding-left: 20px;
        border-left: 1px dashed #eee;
      }
    }

    .dialog-section {
      margin-bottom: 20px;

      &-title {
        font-size: 16px;
        color: #409eff;
        margin: 0 0 10px 0;
        display: flex;
        align-items: center;

        i {
          margin-right: 8px;
        }
      }

      &-content {
        padding: 12px;
        background-color: #f9f9f9;
        border-radius: 6px;
        line-height: 1.6;

        &.wrong-answer {
          color: #f56c6c;
          background-color: rgba(245, 108, 108, 0.05);
        }

        &.correct-answer {
          color: #67c23a;
          background-color: rgba(103, 194, 58, 0.05);
        }
      }
    }

    .dialog-meta {
      margin-top: 30px;
      padding-top: 15px;
      border-top: 1px dashed #eee;

      .meta-item {
        margin-bottom: 12px;
        display: flex;
        align-items: center;

        .meta-label {
          font-weight: 500;
          color: #666;
          min-width: 80px;
          display: flex;
          align-items: center;

          i {
            margin-right: 6px;
            color: #909399;
          }
        }

        .meta-tag {
          margin-right: 8px;
          margin-bottom: 4px;
        }

        .meta-value {
          color: #333;
        }
      }
    }
  }
}

.app-container {
  min-height: 100vh;
  background-color: #f5f7fa;
}

.app-header {
  color: #000;
  padding: 0 20px;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
  height: 69px !important;
  display: flex;
  align-items: center;

  .header-content {
    display: flex;
    align-items: center;
    justify-content: space-between;
    /* 将内容分布到两端 */
    width: 100%;
  }

  .app-logo {
    height: 40px;
    /* 控制 Logo 的高度 */
    // width: auto;
    /* 宽度自动调整 */
    border-radius: 50%;
  }

  .el-menu {
    border: none;
  }

  .el-menu-item {
    font-size: 14px;
    transition: all 0.2s;
  }

  .el-menu-item.is-active {
    // background-color: #ecf5ff;
    color: #409eff;
    font-weight: 500;
  }
}

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

.social-media {
  margin-top: 20px;
}

.social-media .social-icon {
  margin: 0 10px;
  font-size: 14px;
  color: #555;
  text-decoration: none;
}

.social-media .social-icon:hover {
  color: #007bff;
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

.app-carousel {
  position: relative;
  margin-top: 15px;
  /* 确保容器有相对定位 */
  width: 100%;
  /* 容器宽度 */
  height: 580px;
  /* 容器高度，可以根据需要调整 */
  overflow: hidden;
  /* 隐藏超出的图片部分 */
}

.app-carousel img {
  width: 100%;
  /* 图片宽度自适应容器 */
  height: 100%;
  /* 图片高度自适应容器 */
  object-fit: cover;
  /* 保持比例并填充整个容器 */
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
  /* 左右内边距 */
}

.app-logo {
  height: 40px;
  /* 设置 logo 高度 */
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

.banner {
  width: 1200px;
  margin: 0 auto;
}
</style>

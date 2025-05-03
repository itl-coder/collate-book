<template>
  <div>
    <el-container class="app-container">
      <!-- 顶部导航栏 -->
      <el-header class="sticky-header">
        <header-nav/>
      </el-header>

      <!-- 轮播图 -->
      <div class="banner">
        <el-carousel class="app-carousel" height="580px">
          <el-carousel-item v-for="item in swipperList" :key="item.id">
            <image-preview :height="580" :src="item.imageUrl" :width="1200"/>
          </el-carousel-item>
        </el-carousel>
      </div>

      <!-- 主体内容区 -->
      <el-main class="app-main">
        <el-card class="main-card" shadow="never">
          <div slot="header" class="card-header">
            <span class="card-title">我的错题</span>
            <el-button
              v-if="isMobile"
              class="sidebar-toggle"
              size="mini"
              @click="toggleSidebar"
            >
              <i :class="isCollapse ? 'el-icon-s-unfold' : 'el-icon-s-fold'"/>
            </el-button>
          </div>

          <div class="filter-area">
            <!-- 科目下拉框 -->
            <el-select
              v-model="selectedSubject"
              clearable
              placeholder="选择科目"
              style="margin-right: 10px;"
              @change="handleSubjectChange"
            >
              <el-option
                v-for="subject in subjects"
                :key="subject.id"
                :label="subject.subjectName"
                :value="subject.id"
              />
            </el-select>

            <!-- 教辅下拉框 -->
            <el-select
              v-model="selectedBook"
              clearable
              placeholder="选择标签"
              style="margin-right: 10px;"
            >
              <el-option
                v-for="tag in tagCountList"
                :key="tag.id"
                :label="tag.tagName"
                :value="tag.id"
              />
            </el-select>

            <el-button type="primary" @click="getCollateData">搜索我的错题</el-button>
            <el-button type="warning" @click="clearHandle">重置</el-button>
          </div>

          <!-- 错题卡片列表 -->
          <div class="mistake-container">
            <!-- 错题列表 -->
            <div v-if="collateList.length > 0">
              <error-card-item
                v-for="(mistake, index) in collateList"
                :key="index"
                :item="mistake"
                :tag-list="tagCountList"
                @update-tag="handleUpdateTag"
                @view-details="handleViewDetails"
              />
            </div>

            <!-- 空状态 -->
            <el-empty v-else description="暂无错题数据"></el-empty>
          </div>

          <!-- 分页组件 -->
          <div v-if="collateList.length > 0" class="pagination-area">
            <el-pagination
              :current-page="currentPage"
              :page-size="pageSize"
              :pager-count="isMobile ? 3 : 5"
              :total="total"
              background
              layout="prev, pager, next, jumper"
              next-text="下一页"
              prev-text="上一页"
              @current-change="handlePageChange"
            />
          </div>
        </el-card>
      </el-main>

      <!-- 底部 -->
      <common-footer/>
    </el-container>
  </div>
</template>

<script>
import {getSwipperList} from "@/api/carousel/swipper";
import {frontListAnswer, updateAnswer} from "@/api/errorbook/answer";
import HeaderNav from "@/views/componests/HeaderNav.vue";
import CommonFooter from "@/views/componests/CommonFooter.vue";
import Cookies from "js-cookie";
import {countListTag} from "@/api/errorbook/tag";
import ErrorCardItem from "@/views/front/ErrorCardItem.vue";
import {countListSubject} from "@/api/errorbook/subject";
import {countListBook} from "@/api/errorbook/book";

export default {
  name: "MistakeCollection",
  components: {
    HeaderNav,
    CommonFooter,
    ErrorCardItem,
  },
  data() {
    return {
      selectedSubject: null, // 选中的科目
      selectedBook: null, // 选中的教辅
      subjects: [], // 科目数据
      books: [], // 教辅数据
      swipperList: [],
      currentPage: 1,
      pageSize: 4,
      isCollapse: false,
      isMobile: false,
      collateList: [],
      tagCountList: [],
      dialogVisible: false,
      selectedQuestion: null,
      total: 0, // 总条数
    };
  },
  methods: {
    // 获取教辅列表
    async getCountBook() {
      try {
        const res = await countListBook();
        this.books = res.data;
      } catch (error) {
        console.error("获取教辅列表失败:", error);
        this.$message.error("获取教辅列表失败");
      }
    },

    // 获取科目列表
    async getCountSubject() {
      try {
        const res = await countListSubject();
        this.subjects = res.data;
      } catch (error) {
        console.error("获取科目列表失败:", error);
        this.$message.error("获取科目列表失败");
      }
    },

    // 科目改变时重置教辅选择
    handleSubjectChange() {
      this.selectedBook = null;
    },

    // 更新错题标签
    async handleUpdateTag({mistakeId, tagId}) {
      try {
        console.log(`item: ${mistakeId}, tagId: ${tagId}`)
        let userAnswerParam = {
          id: mistakeId,
          tagId: tagId
        }
        const res = await updateAnswer(userAnswerParam)
        console.log("updateAnswer: ",res)
        if(res.code==200){
          this.getCollateData();
          this.$message.success("标签更新成功");
        }
      } catch (error) {
        console.error("更新标签失败:", error);
        this.$message.error("标签更新失败");
      }
    },

    // 查看详情
    handleViewDetails(item) {
      this.selectedQuestion = item;
      this.dialogVisible = true;
    },

    // 切换侧边栏
    toggleSidebar() {
      this.isCollapse = !this.isCollapse;
    },

    // 重置筛选条件
    clearHandle() {
      this.selectedSubject = null;
      this.selectedBook = null;
      this.currentPage = 1;
      this.getCollateData();
    },

    // 获取错题数据
    async getCollateData() {
      const loginState = Cookies.get("userId");
      if (!loginState) {
        this.$message.error("登录已失效,请重新登录!");
        return;
      }

      const query = {
        userId: loginState,
        subjectId: this.selectedSubject,
        tagId: this.selectedBook,
      };
      console.log("getCollate quesy: ", query)
      try {
        const res = await frontListAnswer(query);
        this.collateList = res.data;
        console.log("aa: ", res)
        this.total = res.data.total || res.data.length;
      } catch (error) {
        console.error("加载数据失败:", error);
        this.$message.error("加载数据失败");
      }
    },

    // 获取轮播图数据
    async fetchSwipperData() {
      try {
        const res = await getSwipperList();
        this.swipperList = res.data;
      } catch (error) {
        console.error("获取轮播图失败:", error);
      }
    },

    // 切换分页
    handlePageChange(page) {
      this.currentPage = page;
      this.getCollateData();
      window.scrollTo({top: 0, behavior: "smooth"});
    },

    // 获取标签列表
    async getCountTagList() {
      try {
        const res = await countListTag();
        this.tagCountList = res.data;
      } catch (error) {
        console.error("获取标签列表失败:", error);
      }
    },

    // 判断是否为移动端
    checkIsMobile() {
      this.isMobile = window.innerWidth <= 768;
    },
  },
  mounted() {
    this.getCountSubject();
    this.getCountBook();
    this.fetchSwipperData();
    this.getCountTagList();
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
  background-color: #f5f7fa;
  padding: 0;
  display: flex;
  flex-direction: column;
}

.app-main {
  padding: 20px 0 !important;
  flex: 1;
}

.main-card {
  margin: 0 auto;
  max-width: 1200px;
  border: none;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
}

.card-title {
  font-size: 18px;
  font-weight: bold;
}

.filter-area {
  padding: 0 20px 20px;
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 10px;
}

.pagination-area {
  margin-top: 25px;
  display: flex;
  justify-content: center;
  padding: 0 20px 20px;
}

.sticky-header {
  position: sticky;
  top: 0;
  z-index: 1000;
  background-color: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.app-carousel {
  position: relative;
  margin-top: 15px;
  height: 580px;
  overflow: hidden;
  border-radius: 8px;
}

.banner {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

.mistake-container {
  padding: 0 20px;
}

@media (max-width: 768px) {
  .filter-area {
    flex-direction: column;
    align-items: stretch;

    .el-select,
    .el-input {
      width: 100%;
      margin-right: 0;
      margin-bottom: 10px;
    }
  }

  .app-carousel {
    height: 300px;
  }

  .banner {
    padding: 0 10px;
  }
}
</style>

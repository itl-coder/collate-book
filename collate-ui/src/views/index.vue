<template>
  <div class="app-container home">
    <el-container style="padding: 20px; background-color: #f5f7fa;">
      <el-header>
        <el-row :gutter="20" type="flex" align="middle" style="margin-bottom: 10px;">
          <el-col :span="6">
            <el-select v-model="filter.subjectId" placeholder="选择科目" clearable @change="loadQuestions">
              <el-option v-for="item in subjectOptions" :key="item.id" :label="item.name" :value="item.id" />
            </el-select>
          </el-col>
          <el-col :span="6">
            <el-select v-model="filter.bookId" placeholder="选择教辅" clearable @change="loadQuestions">
              <el-option v-for="item in bookOptions" :key="item.id" :label="item.name" :value="item.id" />
            </el-select>
          </el-col>
          <el-col :span="6">
            <el-button type="primary" @click="loadQuestions">刷新错题</el-button>
          </el-col>
        </el-row>
      </el-header>

      <el-main>
        <el-row :gutter="20">
          <el-col :span="8" v-for="question in questionList" :key="question.id">
            <el-card shadow="hover" style="margin-bottom: 20px;">
              <div class="question-card">
                <div class="question-type">【{{ question.question_type }}】</div>
                <div class="question-content" v-html="formatContent(question.question_content)"></div>
                <div class="correct-answer"><b>正确答案：</b>{{ question.correct_answer }}</div>
                <div class="card-footer">
                  <el-button type="text" @click="showDetail(question)">查看详情</el-button>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>

        <div style="text-align: center; margin-top: 20px;">
          <el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize"
            @current-change="handlePageChange" />
        </div>
      </el-main>

      <!-- 查看详情弹窗 -->
      <el-dialog :visible.sync="detailVisible" title="题目详情" width="60%">
        <div v-if="currentQuestion">
          <div v-html="currentQuestion.question_content"></div>
          <p><b>题型：</b>{{ currentQuestion.question_type }}</p>
          <p><b>正确答案：</b>{{ currentQuestion.correct_answer }}</p>
        </div>
      </el-dialog>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "3.8.7",
      filter: {
        subjectId: null,
        bookId: null,
      },
      subjectOptions: [], // 假设通过接口获取
      bookOptions: [],
      questionList: [],
      total: 0,
      pageSize: 6,
      currentPage: 1,
      detailVisible: false,
      currentQuestion: null,
    };
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
    loadQuestions() {
      // 伪代码 - 替换为真实请求
      // axios.get('/api/wrong-questions', { params: this.filter })
      //   .then(res => { this.questionList = res.data; })
    },
    handlePageChange(page) {
      this.currentPage = page;
      this.loadQuestions();
    },
    formatContent(content) {
      return content.length > 100 ? content.substring(0, 100) + '...' : content;
    },
    showDetail(question) {
      this.currentQuestion = question;
      this.detailVisible = true;
    },
  },
  mounted() {
    this.loadQuestions();
  }
};
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }

  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }

  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans",
  "Helvetica Neue",
  Helvetica,
  Arial,
  sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}


.question-card {
  min-height: 180px;
}

.question-type {
  font-weight: bold;
  color: #409EFF;
  margin-bottom: 10px;
}

.question-content {
  font-size: 14px;
  margin-bottom: 8px;
  color: #333;
}

.correct-answer {
  color: #67C23A;
  font-size: 13px;
}

.card-footer {
  text-align: right;
  margin-top: 10px;
}
</style>

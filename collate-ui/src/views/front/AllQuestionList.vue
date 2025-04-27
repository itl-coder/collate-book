<template>
  <div class="question-container">
    <!-- 题目列表 -->
    <div class="question-list" v-for="question in questions" :key="question.id">
      <div class="question-item">
        <div class="question-header">
          <span class="question-index">题目 {{ question.id }}</span>
          <el-tag size="small" :type="getQuestionTypeTag(question.questionType)">
            {{ question.questionType }}
          </el-tag>
        </div>
        <!-- 题目内容 -->
        <div class="question-content">
          <div class="content-text" v-html="formatQuestionContent(question.questionContent)"></div>
        </div>
        <!-- 选项部分 -->
        <div class="question-options" v-if="question.questionType === '单选' || question.questionType === '多选'">
          <div v-for="(option, index) in question.options" :key="index" class="option">
            <!-- 多选题 -->
            <el-checkbox
              v-if="question.questionType === '多选'"
              v-model="selectedOptions"
              :label="option.optionLabel"
            >
              {{ option.optionLabel }}
            </el-checkbox>

            <!-- 单选题 绑定到当前题目的选中项  -->
            <el-radio
              v-else
              v-model="question.selectedOption"
            :label="option.optionLabel"
            >
            {{ option.optionLabel }}.{{ option.content}}
            </el-radio>
          </div>
        </div>

        <div class="answer-section" v-if="showAnswers[question.id]">
          <div class="answer-title">正确答案：</div>
          <div class="correct-answer" v-html="formatAnswerContent(question.correctAnswer)"></div>
        </div>

        <div class="question-footer">
          <div class="meta-info">
            <span class="meta-item">
              <i class="el-icon-collection"></i>
              科目名称: {{ question.subjectName }}
            </span>
            <span class="meta-item" v-if="question.bookId">
              <i class="el-icon-notebook-2"></i>
              教辅名称: {{ question.bookName }}
            </span>
            <!-- 查看/隐藏答案按钮，放置在教辅ID后面 -->
            <div class="action-buttons">
              <el-tooltip class="item" effect="dark" content="查看/隐藏答案" placement="top">
                <el-button
                  size="small"
                  @click="toggleAnswer(question.id)"
                  type="text"
                  icon="el-icon-view"
                >
                  {{ showAnswers[question.id] ? "隐藏答案" : "查看答案" }}
                </el-button>
              </el-tooltip>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 分页 -->
    <div class="pagination-container">
      <el-pagination
        background
        layout="prev, pager, next, jumper, sizes, total"
        :total="questions.length"
        :page-size="pageSize"
        :current-page="currentPage"
        :page-sizes="[5, 10, 20, 50]"
        @current-change="handleCurrentChange"
        @size-change="handleSizeChange"
      />
    </div>
  </div>
</template>
<style lang="scss" scoped>

.question-options {
  margin-bottom: 12px;
  padding: 10px;
  //background-color: #f5f7fa;
  border-radius: 4px;
}

.option {
  margin-bottom: 8px;
}


.question-container {
  max-width: 900px;
  margin: 0 auto;
  padding: 20px;
}

.question-list {
  display: flex;
  flex-direction: column;
  gap: 15px;  /* 减少题目间的间距 */
  min-height: 200px;
  margin-top: 10px;
}

.question-item {
  background-color: #fff;
  border-radius: 8px;
  padding: 15px; /* 减少 padding 值，减小上下间距 */
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  transition: all 0.3s;

  &:hover {
    box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
  }
}

.question-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;  /* 减小上下间距 */
  padding-bottom: 8px; /* 减少底部 padding */
  border-bottom: 1px dashed #ebeef5;

  .question-index {
    font-size: 16px;
    font-weight: bold;
    color: #303133;
  }
}

.question-content {
  margin-bottom: 10px; /* 减少下方的间距 */
  padding: 12px;  /* 减少 padding */
  background-color: #f5f7fa;
  border-radius: 4px;
  line-height: 1.6;

  .content-text {
    white-space: pre-wrap;
  }
}

.answer-section {
  padding: 12px;  /* 减少 padding */
  background-color: #f0f9eb;
  border-radius: 4px;
  margin-bottom: 12px; /* 减少 margin-bottom */
  animation: fadeIn 0.3s;

  .answer-title {
    font-weight: bold;
    margin-bottom: 6px;  /* 减少底部 margin */
    color: #67c23a;
  }

  .correct-answer {
    line-height: 1.6;
  }
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(-10px);
  }

  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.action-buttons {
  margin-bottom: 12px;  /* 减少 bottom margin */
  text-align: center;

  .meta-info {
    display: flex;
    align-items: center;
    gap: 15px;
  }
}

.question-footer {
  font-size: 12px;
  color: #909399;

  .meta-info {
    display: flex;
    align-items: center;
    gap: 15px;
    flex-wrap: wrap;
  }

  .meta-item {
    display: flex;
    align-items: center;
    gap: 5px;
  }

  .action-buttons {
    margin-left: auto;
  }
}

.pagination-container {
  margin-top: 30px;
  display: flex;
  justify-content: center;
}
</style>


<script>
import {frontListQuestion} from "@/api/errorbook/question";

export default {
  data() {
    return {
      // 多选
      selectedOptions:[],
      selectedOption:"",
      // 题目列表
      questions: [ ],
      showAnswers: {}, // 控制答案显示状态
      currentPage: 1, // 当前页码
      pageSize: 3, // 每页显示数量
    };
  },
  created() {
  this.getFrontList()
    },
  methods: {
   async getFrontList(){
      let query = {
        pageSize: this.pageSize,
        currentPage: this.currentPage
      }
     const res = await frontListQuestion(query)
     console.log("getFrontList: ",res)
     this.questions = res.data
    },
    getQuestionTypeTag(type) {
      const typeMap = {
        单选题: "primary",
        多选题: "success",
        判断题: "warning",
        简答题: "danger",
        填空题: "info",
      };
      return typeMap[type] || "";
    },
    formatQuestionContent(content) {
      return content.replace(/\n/g, "<br>");
    },
    formatAnswerContent(answer) {
      return `<span style="color: #67C23A; font-weight: 500">${answer}</span>`;
    },
    // 切换答案显示状态
    toggleAnswer(questionId) {
      this.$set(this.showAnswers, questionId, !this.showAnswers[questionId]);
    },
    // 分页相关方法
    handleCurrentChange(val) {
      this.currentPage = val;
      // 切换页面时隐藏所有答案
      this.showAnswers = {};
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.currentPage = 1; // 重置到第一页
      this.showAnswers = {};
    },
  },
};
</script>

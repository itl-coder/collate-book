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
              v-model="question.selectedOptions"
              :label="option.optionLabel"
            >
              {{ option.optionLabel }}
            </el-checkbox>

            <!-- 单选题 -->
            <el-radio
              v-else
              v-model="question.selectedOption"
              :label="option.optionLabel"
            >
              {{ option.optionLabel }}.{{ option.content }}
            </el-radio>
          </div>
        </div>

        <!-- 提交按钮 & 查看答案 -->
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

            <!-- 提交按钮 -->
            <el-button
              v-if="!question.isSubmitted"
              size="small"
              type="primary"
              @click="submitAnswer(question.id)"
              :disabled="!hasAnswer(question)"
            >
              提交答案
            </el-button>

            <!-- 查看答案按钮（提交后才显示） -->
            <el-button
              v-if="question.isSubmitted"
              size="small"
              type="text"
              @click="toggleAnswer(question.id)"
              icon="el-icon-view"
            >
              {{ showAnswers[question.id] ? "隐藏答案" : "查看答案" }}
            </el-button>
          </div>
        </div>

        <!-- 答案区域（提交后才显示） -->
        <div class="answer-section" v-if="question.isSubmitted && showAnswers[question.id]">
          <div class="answer-title">正确答案：</div>
          <div class="correct-answer" v-html="formatAnswerContent(question.correctAnswer)"></div>
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

<script>
import { frontListQuestion } from "@/api/errorbook/question";

export default {
  data() {
    return {
      questions: [],
      showAnswers: {}, // 控制答案显示状态
      currentPage: 1, // 当前页码
      pageSize: 3, // 每页显示数量
    };
  },
  created() {
    this.getFrontList();
  },
  methods: {
    async getFrontList() {
      let query = {
        pageSize: this.pageSize,
        currentPage: this.currentPage,
      };
      const res = await frontListQuestion(query);
      console.log("getFrontList: ", res);

      // 初始化题目数据，确保每个题目有独立的 selectedOption/selectedOptions 和 isSubmitted
      this.questions = res.data.map(question => ({
        ...question,
        selectedOption: "", // 单选题选中项
        selectedOptions: [], // 多选题选中项
        isSubmitted: false, // 是否已提交
      }));
    },

    // 检查是否有答案（用于禁用提交按钮）
    hasAnswer(question) {
      if (question.questionType === "单选") {
        return question.selectedOption !== "";
      } else if (question.questionType === "多选") {
        return question.selectedOptions.length > 0;
      }
      return false;
    },

    // 提交答案
    submitAnswer(questionId) {
      const question = this.questions.find(q => q.id === questionId);
      if (question) {
        question.isSubmitted = true;
        this.$message.success("答案已提交！");
      }
    },

    // 其他方法保持不变...
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
    toggleAnswer(questionId) {
      this.$set(this.showAnswers, questionId, !this.showAnswers[questionId]);
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.showAnswers = {};
      this.getFrontList();
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.currentPage = 1;
      this.showAnswers = {};
      this.getFrontList();
    },
  },
};
</script>

<style lang="scss" scoped>
.question-container {
  max-width: 900px;
  margin: 0 auto;
  padding: 20px;
}

.question-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
  margin-top: 10px;
}

.question-item {
  background-color: #fff;
  border-radius: 8px;
  padding: 15px;
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
  margin-bottom: 10px;
  padding-bottom: 8px;
  border-bottom: 1px dashed #ebeef5;

  .question-index {
    font-size: 16px;
    font-weight: bold;
    color: #303133;
  }
}

.question-content {
  margin-bottom: 10px;
  padding: 12px;
  background-color: #f5f7fa;
  border-radius: 4px;
  line-height: 1.6;

  .content-text {
    white-space: pre-wrap;
  }
}

/* 选项区域样式 */
.question-options {
  margin: 15px 0;
  padding: 12px;
  border-radius: 4px;
  background-color: #fafafa;

  .option {
    margin-bottom: 8px;
    display: flex;
    align-items: center;

    /* 单选/多选框样式 */
    ::v-deep .el-radio,
    ::v-deep .el-checkbox {
      margin-right: 8px;
    }

    /* 选项标签样式 */
    ::v-deep .el-radio__label,
    ::v-deep .el-checkbox__label {
      font-size: 14px;
      color: #606266;
    }
  }
}

/* 答案区域样式 */
.answer-section {
  margin-top: 12px;
  padding: 12px;
  background-color: #f0f9eb;
  border-radius: 4px;
  animation: fadeIn 0.3s;

  .answer-title {
    font-weight: bold;
    margin-bottom: 6px;
    color: #67c23a;
  }

  .correct-answer {
    line-height: 1.6;
    color: #333;
  }
}

/* 底部操作区域 */
.question-footer {
  margin-top: 15px;
  padding-top: 12px;
  border-top: 1px dashed #ebeef5;

  .meta-info {
    display: flex;
    align-items: center;
    flex-wrap: wrap;
    gap: 15px;
    font-size: 12px;
    color: #909399;

    .meta-item {
      display: flex;
      align-items: center;
      gap: 5px;
    }
  }

  /* 提交按钮样式 */
  .el-button {
    margin-left: auto;
    padding: 8px 15px;

    &[type="primary"] {
      background-color: #409eff;
      border-color: #409eff;

      &:hover {
        background-color: #66b1ff;
        border-color: #66b1ff;
      }

      &:disabled {
        background-color: #a0cfff;
        border-color: #a0cfff;
        cursor: not-allowed;
      }
    }

    &[type="text"] {
      color: #409eff;
      padding: 8px 5px;

      &:hover {
        color: #66b1ff;
      }
    }
  }
}

/* 分页样式 */
.pagination-container {
  margin-top: 30px;
  display: flex;
  justify-content: center;
}

/* 动画效果 */
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
</style>

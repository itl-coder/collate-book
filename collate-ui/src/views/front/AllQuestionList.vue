<template>
  <div class="question-container">
    <!-- 题目列表 -->
    <div class="question-list">
      <div
        class="question-item"
        v-for="(question, index) in paginatedQuestions"
        :key="question.id"
      >
        <div class="question-header">
          <span class="question-index"
          >题目 {{ (currentPage - 1) * pageSize + index + 1 }}</span
          >
          <el-tag
            size="small"
            :type="getQuestionTypeTag(question.question_type)"
          >
            {{ question.question_type }}
          </el-tag>
        </div>

        <div class="question-content">
          <div
            class="content-text"
            v-html="formatQuestionContent(question.question_content)"
          ></div>
        </div>

        <div class="answer-section" v-if="showAnswers[question.id]">
          <div class="answer-title">正确答案：</div>
          <div
            class="correct-answer"
            v-html="formatAnswerContent(question.correct_answer)"
          ></div>
        </div>

        <div class="question-footer">
          <div class="meta-info">
            <span class="meta-item">
              <i class="el-icon-collection"></i>
              科目ID: {{ question.subject_id }}
            </span>
            <span class="meta-item" v-if="question.book_id">
              <i class="el-icon-notebook-2"></i>
              教辅ID: {{ question.book_id }}
            </span>
            <!-- 查看/隐藏答案按钮，放置在教辅ID后面 -->
            <div class="action-buttons">
              <el-tooltip
                class="item"
                effect="dark"
                content="查看/隐藏答案"
                placement="top"
              >
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
.question-container {
  max-width: 900px;
  margin: 0 auto;
  padding: 20px;
}

.question-list {
  display: flex;
  flex-direction: column;
  gap: 25px;
  min-height: 500px;
  /* 保持高度避免页面跳动 */
}

.question-item {
  background-color: #fff;
  border-radius: 8px;
  padding: 20px;
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
  margin-bottom: 15px;
  padding-bottom: 10px;
  border-bottom: 1px dashed #ebeef5;

  .question-index {
    font-size: 16px;
    font-weight: bold;
    color: #303133;
  }
}

.question-content {
  margin-bottom: 15px;
  padding: 15px;
  background-color: #f5f7fa;
  border-radius: 4px;
  line-height: 1.6;

  .content-text {
    white-space: pre-wrap;
  }
}

.answer-section {
  padding: 15px;
  background-color: #f0f9eb;
  border-radius: 4px;
  margin-bottom: 15px;
  animation: fadeIn 0.3s;

  .answer-title {
    font-weight: bold;
    margin-bottom: 8px;
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
  margin-bottom: 15px;
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
    align-items: center; /* 保证所有元素垂直居中对齐 */
    gap: 15px; /* 间隔 */
    flex-wrap: wrap; /* 如果空间不足，允许换行 */
  }

  .meta-item {
    display: flex;
    align-items: center;
    gap: 5px;
  }

  .action-buttons {
    margin-left: auto; /* 确保按钮靠右对齐 */
  }
}

.pagination-container {
  margin-top: 30px;
  display: flex;
  justify-content: center;
}
</style>

<script>
export default {
  data() {
    return {
      questions: [
        // 题目数据...
        {
          id: 1,
          question_content:
            "1. 已知函数f(x) = x² + 2x + 1，求f(2)的值。\nA) 5\nB) 7\nC) 9\nD) 11",
          question_type: "单选题",
          correct_answer: "C) 9",
          subject_id: 101,
          book_id: 201,
          create_time: "2023-05-10 14:30:00",
          update_time: "2023-05-15 09:15:00",
        },
        {
          id: 2,
          question_content: "2. 简述牛顿第一定律的内容。",
          question_type: "简答题",
          correct_answer:
            "牛顿第一定律，又称惯性定律，指出：任何物体都要保持匀速直线运动或静止状态，直到外力迫使它改变运动状态为止。",
          subject_id: 102,
          book_id: null,
          create_time: "2023-05-12 10:20:00",
          update_time: "2023-05-12 10:20:00",
        },
        // 更多题目...
        {
          id: 3,
          question_content:
            "3. 下列哪个不是操作系统？\nA) Windows\nB) Linux\nC) Photoshop\nD) macOS",
          question_type: "单选题",
          correct_answer: "C) Photoshop",
          subject_id: 103,
          book_id: 203,
          create_time: "2023-05-13 11:30:00",
          update_time: "2023-05-13 11:30:00",
        },
        {
          id: 4,
          question_content: "4. 什么是面向对象编程的三大特性？",
          question_type: "简答题",
          correct_answer: "封装、继承和多态",
          subject_id: 104,
          book_id: 204,
          create_time: "2023-05-14 15:45:00",
          update_time: "2023-05-14 15:45:00",
        },
        {
          id: 5,
          question_content:
            "5. 下列哪些是JavaScript的基本数据类型？（多选）\nA) String\nB) Object\nC) Number\nD) Array\nE) Boolean",
          question_type: "多选题",
          correct_answer: "A) String\nC) Number\nE) Boolean",
          subject_id: 105,
          book_id: 205,
          create_time: "2023-05-15 09:00:00",
          update_time: "2023-05-15 09:00:00",
        },
      ],
      showAnswers: {}, // 控制答案显示状态
      currentPage: 1, // 当前页码
      pageSize: 3, // 每页显示数量
    };
  },
  computed: {
    // 分页后的题目数据
    paginatedQuestions() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.questions.slice(start, end);
    },
  },
  methods: {
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
    formatTime(time) {
      return new Date(time).toLocaleString();
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

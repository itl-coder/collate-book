<template>
  <div>
    <!-- 顶部导航 -->
    <header-nav/>
    <div class="question-container">

      <!-- 顶部导航 -->
      <el-menu :default-active="activeMenu" class="top-nav" mode="horizontal">
        <div class="menu-content">
          <!-- 左侧返回按钮 -->
          <el-menu-item index="1" @click="goBack">
            <i class="el-icon-arrow-left"></i> 返回
          </el-menu-item>

          <!-- 右侧科目选择 -->
          <el-menu-item class="subject-select" index="2">
            <el-input v-model="bookName" class="input-book-name" clearable placeholder="请输入教辅名称"
                      size="small" @clear="getFrontList"></el-input>
            <el-select v-model="selectedSubject" clearable placeholder="请选择科目" size="small"
                       @clear="getFrontList">
              <el-option
                v-for="subject in subjects"
                :key="subject.id"
                :label="subject.subjectName"
                :value="subject.subjectName"
              />
            </el-select>
            <el-button icon="el-icon-search" size="mini" type="primary" @click="getFrontList">搜索</el-button>
          </el-menu-item>
        </div>
      </el-menu>
      <!-- 题目列表 -->
      <div
        v-for="question in questions"
        v-if="!(isChoiceQuestion(question) && (!question.options || question.options.length === 0))"
        :key="question.id"
        class="question-list"
      >
        <div class="question-item">
          <div class="question-header">
            <span class="question-index">题目 {{ question.id }}</span>
            <div>
              <el-tag :type="getQuestionTypeTag(question.questionType)" size="small">
                {{ question.questionType }}
              </el-tag>
              <el-tag
                v-if="question.isSubmitted"
                class="add-bk"
                size="small"
                type="warning"
                @click="addCollateBook(question)"
              >
                加入错题本
              </el-tag>
            </div>
          </div>

          <!-- 题目内容 -->
          <div class="question-content">
            <div class="content-text" v-html="formatQuestionContent(question.questionContent)"></div>
          </div>

          <!-- 选项部分 -->
          <div v-if="question.questionType === '单选' || question.questionType === '多选'" class="question-options">
            <div v-for="(option, index) in question.options" :key="index" class="option">
              <!-- 多选题 -->
              <el-checkbox
                v-if="question.questionType === '多选'"
                v-model="question.selectedOptions"
                :class="getOptionClass(question, option)"
                :disabled="question.isSubmitted"
                :label="option.optionLabel"
              >
                {{ option.optionLabel }}.{{ option.content }}
              </el-checkbox>

              <!-- 单选题 -->
              <el-radio
                v-else
                v-model="question.selectedOption"
                :class="getOptionClass(question, option)"
                :disabled="question.isSubmitted"
                :label="option.optionLabel"
              >
                {{ option.optionLabel }}.{{ option.content }}
              </el-radio>
            </div>
          </div>

          <!-- 判断题部分：True/False 选项 -->
          <div v-if="question.questionType === '判断'" class="question-options">
            <el-radio-group v-model="question.selectedOption" :disabled="question.isSubmitted">
              <el-radio :label="'True'">正确</el-radio>
              <el-radio :label="'False'">错误</el-radio>
            </el-radio-group>
          </div>

          <!-- 简答题输入框 -->
          <div v-if="question.questionType === '简答'" class="question-answer">
            <el-input
              v-model="question.answerText"
              :disabled="question.isSubmitted"
              class="answer-input"
              clearable
              placeholder="请输入你的答案"
              rows="4"
              type="textarea"
            ></el-input>
          </div>

          <!-- 提交按钮 & 查看答案按钮 -->
          <div class="question-footer">
            <div class="meta-info">
            <span class="meta-item">
              <i class="el-icon-collection"></i>
              科目名称: {{ question.subjectName }}
            </span>
              <span v-if="question.bookId" class="meta-item">
              <i class="el-icon-notebook-2"></i>
              教辅名称: {{ question.bookName }}
            </span>

              <!-- 提交按钮 -->
              <el-button
                v-if="!question.isSubmitted"
                :disabled="!hasAnswer(question)"
                size="small"
                type="primary"
                @click="submitAnswer(question.id)"
              >
                提交答案
              </el-button>

              <!-- 查看答案按钮 -->
              <el-button
                v-if="question.isSubmitted"
                icon="el-icon-view"
                size="small"
                type="text"
                @click="toggleAnswer(question.id)"
              >
                {{ showAnswers[question.id] ? "隐藏答案" : "查看答案" }}
              </el-button>
            </div>
          </div>

          <!-- 答案展示区域 -->
          <div v-if="question.isSubmitted && showAnswers[question.id]" class="answer-section">
            <div class="answer-title">正确答案：</div>
            <div class="correct-answer" v-html="formatAnswerContent(question.correctAnswer)"></div>
          </div>
        </div>
      </div>

      <!-- 分页组件 -->
      <div class="pagination-container" v-if="questions.length > pageSize">
        <el-pagination
          :current-page="currentPage"
          :page-size="pageSize"
          :page-sizes="[5, 10, 20, 50]"
          :total="questions.length"
          background
          layout="prev, pager, next, jumper, sizes, total"
          @current-change="handleCurrentChange"
          @size-change="handleSizeChange"
        />
      </div>
    </div>
    <common-footer/>
  </div>
</template>

<script>
import {frontListQuestion} from "@/api/errorbook/question";
import {countListSubject} from "@/api/errorbook/subject";
import {addAnswer} from "@/api/errorbook/answer";
import Cookies from "js-cookie";
import HeaderNav from "@/views/componests/HeaderNav.vue";
import CommonFooter from "@/views/componests/CommonFooter.vue";

export default {
  components: {CommonFooter, HeaderNav},
  data() {
    return {
      activeMenu: "2", // 设置默认激活的菜单项
      subjects: [], // 科目列表
      selectedSubject: "", // 用户选择的科目
      bookName: "",
      questions: [],
      showAnswers: {}, // 控制每道题是否展开答案
      currentPage: 1,
      pageSize: 20,
    };
  },
  created() {
    this.getFrontList();
    this.getCountSubject();
  },
  methods: {
    // 加入错题本
    async addCollateBook(question) {
      console.log("addCollateBook: ", question)
      let loginUserId = Cookies.get("userId")
      console.log("loginUserId: ", loginUserId)
      let query = {
        userId: loginUserId, // 登录用户id
        questionId: question.id, // 题目id
        subjectId: question.subjectId, //
        answer: question.userAnswer[0], // 用户作答
        isCorrect: question.correctAnswer, // 提供的正确答案
        addCollate: 1, // 点击加入就改为1
        sortOrder: 1 // 默认权重
      }
      const loginState = Cookies.get("username") || Cookies.get("userId");
      if (loginState == undefined || loginState=="") {
        this.$message.info("您还未登录,请先登录")
        return;
      } else {
        const res = await addAnswer(query)
        console.log("addCollateBook res: ", res)
        if (res.code === 200) {
          this.$message.success(res.msg)
        }
      }
    },
    goBack() {
      this.$router.back()
    },
    async getCountSubject() {
      const res = await countListSubject()
      this.subjects = res.data
    },
    // 是否为单选或多选题
    isChoiceQuestion(question) {
      return question.questionType === '单选' || question.questionType === '多选';
    },
    async getFrontList() {
      const query = {
        pageSize: this.pageSize,
        currentPage: this.currentPage,
        subjectName: this.selectedSubject,
        bookName: this.bookName
      };
      console.log("getFrontList bookName: ", query)
      const res = await frontListQuestion(query);
      console.log("getFrontList: ", res);

      this.questions = res.data.list.map(question => ({
        ...question,
        selectedOption: "",
        selectedOptions: [],
        answerText: "",
        isSubmitted: false,
        userAnswer: [],
      }));
    },

    hasAnswer(question) {
      if (question.questionType === "单选") {
        return question.selectedOption !== "";
      } else if (question.questionType === "多选") {
        return question.selectedOptions.length > 0;
      } else if (question.questionType === "简答") {
        return question.answerText.trim() !== "";
      } else if (question.questionType === "判断") {
        return question.selectedOption !== "";
      }
      return false;
    },

    submitAnswer(questionId) {
      const question = this.questions.find(q => q.id === questionId);
      if (question) {
        question.isSubmitted = true;
        if (question.questionType === "单选") {
          question.userAnswer = [question.selectedOption];
        } else if (question.questionType === "多选") {
          question.userAnswer = question.selectedOptions;
        } else if (question.questionType === "简答") {
          question.userAnswer = [question.answerText];
        } else if (question.questionType === "判断") {
          question.userAnswer = [question.selectedOption];
        }

        this.$message.success("答案已提交！");
      }
    },

    // 选项正确性判断 - 只有在提交之后才去高亮
    getOptionClass(question, option) {
      if (!question.isSubmitted) return '';
      if (this.isOptionCorrect(question, option)) return 'correct';
      if (this.isOptionIncorrect(question, option)) return 'incorrect';
      return '';
    },

    // 判断选项是否正确（只要是正确答案就绿色）
    isOptionCorrect(question, option) {
      if (!question.isSubmitted) return false; // 没提交就不高亮
      return option.isCorrect === 1; // 只要正确答案都绿色
    },

    // 判断选项是否错误（用户提交且选中了错误选项）
    isOptionIncorrect(question, option) {
      if (!question.isSubmitted) return false; // 没提交就不高亮
      if (question.questionType === "单选") {
        return question.selectedOption === option.optionLabel && option.isCorrect !== 1;
      }
      if (question.questionType === "多选") {
        return question.selectedOptions.includes(option.optionLabel) && option.isCorrect !== 1;
      }
      return false;
    },

    getQuestionTypeTag(type) {
      const typeMap = {
        单选: "primary",
        多选: "success",
        判断: "warning",
        简答: "danger",
        填空: "info",
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
  margin-top: 15px;
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
}

.question-options {
  margin: 15px 0;
  padding: 12px;
  border-radius: 4px;
  background-color: #fafafa;

  .option {
    margin-bottom: 8px;
    display: flex;
    align-items: center;

    ::v-deep .el-radio,
    ::v-deep .el-checkbox {
      margin-right: 8px;
    }

    ::v-deep .el-radio__label,
    ::v-deep .el-checkbox__label {
      font-size: 14px;
      color: #606266;
    }
  }
}

/* 正确选项：字体绿色 */
.correct ::v-deep .el-radio__label,
.correct ::v-deep .el-checkbox__label {
  color: #67c23a !important;
}

/* 错误选项：字体红色 */
.incorrect ::v-deep .el-radio__label,
.incorrect ::v-deep .el-checkbox__label {
  color: #f56c6c !important;
}


.question-answer {
  margin-top: 15px;
}

.answer-input {
  background-color: #f9f9f9;
  border: 1px solid #e4e7ed;
  border-radius: 4px;
  padding: 10px;
  font-size: 14px;
}

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
  }

  .el-button {
    margin-left: auto;
    padding: 8px 15px;
  }
}

.pagination-container {
  margin-top: 30px;
  display: flex;
  justify-content: center;
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

.menu-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.subject-select {
  text-align: right;
}

.input-book-name {
  width: 50%;
  margin-right: 10px;
}

.el-button--mini {
  padding: 5px 10px;
  margin-left: 10px;
}

/* 重置 el-menu-item 在 hover 和 focus 状态下的样式 */
.el-menu-item:hover,
.el-menu-item:focus {
  background-color: transparent !important; /* 去除背景色 */
  color: inherit !important; /* 保持文本颜色不变 */
  border: none !important; /* 去除边框 */
  box-shadow: none !important; /* 去除阴影 */
}

.add-bk {
  margin-left: 10px;
  cursor: pointer;
}

</style>

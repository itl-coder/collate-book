<template>
  <el-card class="mistake-card" shadow="hover">
    <!-- 卡片头部 -->
    <div slot="header" class="card-header">
      <div class="header-left">
        <el-tag type="primary">{{ item.subjectName }}</el-tag>
        <span class="book-name">{{ item.bookName }}</span>
      </div>
      <div class="header-right">
        <el-tag :type="getTagType(item.tagName)" effect="plain">
          {{ item.tagName }}
        </el-tag>

        <!-- 操作下拉菜单 -->
        <el-dropdown @command="handleDropdownCommand">
          <el-button size="mini" icon="el-icon-more" type="primary">
            操作<i class="el-icon-arrow-down el-icon--right"></i>
          </el-button>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="editTag">
              <i class="el-icon-edit"></i> 修改标签
            </el-dropdown-item>
            <el-dropdown-item command="editWeight">
              <i class="el-icon-sort"></i> 修改权重
            </el-dropdown-item>
            <el-dropdown-item divided command="delete">
              <i class="el-icon-delete"></i> 删除
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>

    <!-- 题目内容 -->
    <div class="question-content">
      <div class="content-title">题目内容：</div>
      <div class="content-text" v-html="item.questionContent"></div>
    </div>

    <!-- 答案对比 -->
    <div class="answer-section">
      <div class="answer-item">
        <span class="label">你的答案：</span>
        <el-tag
          :type="item.answer === item.isCorrect ? 'success' : 'danger'"
          class="answer-tag"
        >
          {{ item.answer }}
        </el-tag>
      </div>
      <div class="answer-item">
        <span class="label">正确答案：</span>
        <el-tag type="info" class="answer-tag">
          {{ item.isCorrect }}
        </el-tag>
      </div>
    </div>

    <!-- 标签选择对话框 -->
    <el-dialog title="选择错题标签" :visible.sync="tagDialogVisible" width="400px">
      <el-radio-group v-model="selectedTagId">
        <el-radio
          v-for="tag in tagList"
          :key="tag.id"
          :label="tag.id"
          class="tag-radio"
        >
          {{ tag.tagName }}
        </el-radio>
      </el-radio-group>
      <span slot="footer" class="dialog-footer">
        <el-button @click="tagDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirmTagChange">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 权重修改对话框 -->
    <el-dialog title="修改权重" :visible.sync="weightDialogVisible" width="300px">
      <div class="weight-editor">
        <span>当前权重：</span>
        <el-tag type="warning">{{ weight }}</el-tag>
        <el-button
          size="mini"
          icon="el-icon-plus"
          @click="increaseWeight"
          :disabled="weight >= maxWeight"
        >
          增加
        </el-button>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="weightDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirmWeightChange">确 定</el-button>
      </span>
    </el-dialog>
  </el-card>
</template>

<script>
export default {
  name: 'ErrorCardItem',
  props: {
    item: {
      type: Object,
      required: false
    },
    tagList: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      tagDialogVisible: false,
      weightDialogVisible: false,
      selectedTagId: this.item.tagId,
      weight: this.item.weight || 1,
      maxWeight: 5 // 权重最大值
    }
  },
  methods: {
    /** 获取标签样式 */
    getTagType(tagName) {
      const tagTypes = ['', 'success', 'info', 'warning', 'danger']
      const index = this.tagList.findIndex(tag => tag.tagName === tagName)
      return tagTypes[index % tagTypes.length] || ''
    },

    /** 操作菜单选择处理 */
    handleDropdownCommand(command) {
      if (command === 'editTag') {
        this.showTagDialog()
      } else if (command === 'editWeight') {
        this.showWeightDialog()
      } else if (command === 'delete') {
        this.confirmDelete()
      }
    },

    /** 显示标签选择弹窗 */
    showTagDialog() {
      this.selectedTagId = this.item.tagId
      this.tagDialogVisible = true
    },

    /** 标签修改确认 */
    confirmTagChange() {
      this.tagDialogVisible = false
      this.$emit('update-tag', {
        mistakeId: this.item.id,
        tagId: this.selectedTagId
      })
    },

    /** 显示权重弹窗 */
    showWeightDialog() {
      this.weight = this.item.weight || 1
      this.weightDialogVisible = true
    },

    /** 增加权重 */
    increaseWeight() {
      if (this.weight < this.maxWeight) {
        this.weight += 1
      }
    },

    /** 权重修改确认 */
    confirmWeightChange() {
      this.weightDialogVisible = false
      this.$emit('update-weight', {
        mistakeId: this.item.id,
        weight: this.weight
      })
    },

    /** 删除确认 */
    confirmDelete() {
      this.$confirm('确认要删除该错题吗？', '提示', {
        confirmButtonText: '删除',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$emit('delete-mistake', this.item.id)
      }).catch(() => {
        // 用户取消，无需处理
      })
    }
  }
}
</script>

<style scoped>
.mistake-card {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header-left {
  display: flex;
  align-items: center;
}

.header-left .el-tag {
  margin-right: 10px;
}

.book-name {
  font-size: 14px;
  color: #666;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 8px;
}

.question-content {
  margin-bottom: 20px;
}

.content-title {
  font-size: 14px;
  color: #999;
  margin-bottom: 8px;
}

.content-text {
  line-height: 1.6;
}

.answer-section {
  display: flex;
  margin-top: 15px;
}

.answer-item {
  margin-right: 30px;
  display: flex;
  align-items: center;
}

.answer-item .label {
  margin-right: 8px;
  font-size: 14px;
  color: #666;
}

.answer-tag {
  font-size: 15px;
  padding: 0 12px;
  height: 28px;
  line-height: 26px;
}

.tag-radio {
  display: block;
  margin: 10px 0;
}

.weight-editor {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin: 20px 0;
}

.weight-editor .el-tag {
  font-size: 16px;
}
</style>

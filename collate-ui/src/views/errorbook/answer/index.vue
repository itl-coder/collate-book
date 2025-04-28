<template>
  <div class="app-container">
<!--    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">-->
<!--      <el-form-item label="用户ID，关联用户表" prop="userId">-->
<!--        <el-input-->
<!--          v-model="queryParams.userId"-->
<!--          placeholder="请输入用户ID，关联用户表"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="题目ID，关联题目表" prop="questionId">-->
<!--        <el-input-->
<!--          v-model="queryParams.questionId"-->
<!--          placeholder="请输入题目ID，关联题目表"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="关联科目表" prop="subjectId">-->
<!--        <el-input-->
<!--          v-model="queryParams.subjectId"-->
<!--          placeholder="请输入关联科目表"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="用户作答此题的得分" prop="score">-->
<!--        <el-input-->
<!--          v-model="queryParams.score"-->
<!--          placeholder="请输入用户作答此题的得分"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="标签名" prop="tagId">-->
<!--        <el-input-->
<!--          v-model="queryParams.tagId"-->
<!--          placeholder="请输入标签名"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="排序字段，用户作答的顺序" prop="sortOrder">-->
<!--        <el-input-->
<!--          v-model="queryParams.sortOrder"-->
<!--          placeholder="请输入排序字段，用户作答的顺序"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="加入错题本,0:否,1:是" prop="addCollate">-->
<!--        <el-input-->
<!--          v-model="queryParams.addCollate"-->
<!--          placeholder="请输入加入错题本,0:否,1:是"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item>-->
<!--        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>-->
<!--        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>-->
<!--      </el-form-item>-->
<!--    </el-form>-->

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['errorbook:answer:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['errorbook:answer:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['errorbook:answer:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['errorbook:answer:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="answerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="自增主键，唯一标识一条作答记录" align="center" prop="id" />
      <el-table-column label="用户ID，关联用户表" align="center" prop="userId" />
      <el-table-column label="题目ID，关联题目表" align="center" prop="questionId" />
      <el-table-column label="关联科目表" align="center" prop="subjectId" />
      <el-table-column label="用户的作答内容，单选/多选或填空的答案" align="center" prop="answer" />
      <el-table-column label="正确答案" align="center" prop="isCorrect" />
      <el-table-column label="用户作答此题的得分" align="center" prop="score" />
      <el-table-column label="标签名" align="center" prop="tagId" />
      <el-table-column label="排序字段，用户作答的顺序" align="center" prop="sortOrder" />
      <el-table-column label="加入错题本,0:否,1:是" align="center" prop="addCollate" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['errorbook:answer:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['errorbook:answer:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改用户作答对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户ID，关联用户表" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户ID，关联用户表" />
        </el-form-item>
        <el-form-item label="题目ID，关联题目表" prop="questionId">
          <el-input v-model="form.questionId" placeholder="请输入题目ID，关联题目表" />
        </el-form-item>
        <el-form-item label="关联科目表" prop="subjectId">
          <el-input v-model="form.subjectId" placeholder="请输入关联科目表" />
        </el-form-item>
        <el-form-item label="用户的作答内容，单选/多选或填空的答案" prop="answer">
          <el-input v-model="form.answer" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="正确答案" prop="isCorrect">
          <el-input v-model="form.isCorrect" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="用户作答此题的得分" prop="score">
          <el-input v-model="form.score" placeholder="请输入用户作答此题的得分" />
        </el-form-item>
        <el-form-item label="标签名" prop="tagId">
          <el-input v-model="form.tagId" placeholder="请输入标签名" />
        </el-form-item>
        <el-form-item label="排序字段，用户作答的顺序" prop="sortOrder">
          <el-input v-model="form.sortOrder" placeholder="请输入排序字段，用户作答的顺序" />
        </el-form-item>
        <el-form-item label="加入错题本,0:否,1:是" prop="addCollate">
          <el-input v-model="form.addCollate" placeholder="请输入加入错题本,0:否,1:是" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAnswer, getAnswer, delAnswer, addAnswer, updateAnswer } from "@/api/errorbook/answer";

export default {
  name: "Answer",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 用户作答表格数据
      answerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        questionId: null,
        subjectId: null,
        answer: null,
        isCorrect: null,
        score: null,
        tagId: null,
        sortOrder: null,
        addCollate: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [
          { required: true, message: "用户ID，关联用户表不能为空", trigger: "blur" }
        ],
        questionId: [
          { required: true, message: "题目ID，关联题目表不能为空", trigger: "blur" }
        ],
        answer: [
          { required: true, message: "用户的作答内容，单选/多选或填空的答案不能为空", trigger: "blur" }
        ],
        isCorrect: [
          { required: true, message: "正确答案不能为空", trigger: "blur" }
        ],
        addCollate: [
          { required: true, message: "加入错题本,0:否,1:是不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询用户作答列表 */
    getList() {
      this.loading = true;
      listAnswer(this.queryParams).then(response => {
        this.answerList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        userId: null,
        questionId: null,
        subjectId: null,
        answer: null,
        isCorrect: null,
        createTime: null,
        updateTime: null,
        score: null,
        tagId: null,
        sortOrder: null,
        addCollate: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加用户作答";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getAnswer(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改用户作答";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAnswer(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAnswer(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除用户作答编号为"' + ids + '"的数据项？').then(function() {
        return delAnswer(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('errorbook/answer/export', {
        ...this.queryParams
      }, `answer_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="100px"
    >
      <el-form-item label="所属科目ID" prop="subjectId">
        <el-input
          v-model="queryParams.subjectId"
          placeholder="请输入所属科目ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属教辅ID" prop="bookId">
        <el-input
          v-model="queryParams.bookId"
          placeholder="请输入所属教辅ID "
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['errorbook:question:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['errorbook:question:edit']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['errorbook:question:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['errorbook:question:export']"
          >导出</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="questionList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="题目主键" align="center" prop="id" />
      <!-- <el-table-column label="题目内容" align="center" prop="questionContent" /> -->
      <el-table-column label="题目内容" align="center">
        <template slot-scope="scope">
          <el-button type="text" @click="handleViewDetail(scope.row)"
            >查看题目详情</el-button
          >
        </template>
      </el-table-column>
      <el-table-column label="题目类型" align="center" prop="questionType" />
      <el-table-column label="正确答案" align="center" prop="correctAnswer" />
      <el-table-column label="所属科目" align="center" prop="subjectName" />
      <el-table-column label="所属教辅 " align="center" prop="bookName" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['errorbook:question:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['errorbook:question:remove']"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 查看题目详情对话框 -->
    <el-dialog
      title="题目详情"
      :visible.sync="detailVisible"
      width="800px"
      append-to-body
    >
      <editor v-model="detailContent" :read-only="true" :min-height="200" />
      <span slot="footer" class="dialog-footer">
        <el-button @click="detailVisible = false">关闭</el-button>
      </span>
    </el-dialog>

    <!-- 添加或修改题目管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="题目内容">
          <editor v-model="form.questionContent" :min-height="192" />
        </el-form-item>
        <el-form-item label="正确答案" prop="correctAnswer">
          <el-input
            v-model="form.correctAnswer"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="所属科目ID" prop="subjectId">
          <el-input v-model="form.subjectId" placeholder="请输入所属科目ID" />
        </el-form-item>
        <el-form-item label="所属教辅ID " prop="bookId">
          <el-input v-model="form.bookId" placeholder="请输入所属教辅ID " />
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
import {
  listQuestion,
  getQuestion,
  delQuestion,
  addQuestion,
  updateQuestion,
} from "@/api/errorbook/question";

export default {
  name: "Question",
  data() {
    return {
      // 查看详情对话框
      detailVisible: false,
      detailContent: "",
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
      // 题目管理表格数据
      questionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        questionContent: null,
        questionType: null,
        correctAnswer: null,
        subjectId: null,
        bookId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        questionContent: [
          { required: true, message: "题目内容不能为空", trigger: "blur" },
        ],
        questionType: [
          { required: true, message: "题目类型不能为空", trigger: "change" },
        ],
        correctAnswer: [
          { required: true, message: "正确答案不能为空", trigger: "blur" },
        ],
        subjectId: [
          { required: true, message: "所属科目ID不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查看详情操作 */
    handleViewDetail(row) {
      this.detailContent = row.questionContent;
      this.detailVisible = true;
    },
    /** 查询题目管理列表 */
    getList() {
      this.loading = true;
      listQuestion(this.queryParams).then((response) => {
        console.log("listQuestion: ", response);

        this.questionList = response.rows;
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
        questionContent: null,
        questionType: null,
        correctAnswer: null,
        subjectId: null,
        bookId: null,
        createTime: null,
        updateTime: null,
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
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加题目管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getQuestion(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改题目管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateQuestion(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addQuestion(this.form).then((response) => {
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
      this.$modal
        .confirm('是否确认删除题目管理编号为"' + ids + '"的数据项？')
        .then(function () {
          return delQuestion(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "errorbook/question/export",
        {
          ...this.queryParams,
        },
        `question_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>

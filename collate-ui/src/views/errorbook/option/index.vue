<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['errorbook:option:add']"
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
          v-hasPermi="['errorbook:option:edit']"
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
          v-hasPermi="['errorbook:option:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['errorbook:option:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="optionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="选项标识符，如：A、B、C、D" align="center" prop="optionLabel" />
      <el-table-column label="选项内容" align="center" prop="content" />
      <el-table-column label="是否为正确答案" align="center" prop="isCorrect" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['errorbook:option:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['errorbook:option:remove']"
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

    <!-- 添加或修改选项，用于存储每道题的选项及其是否为正确答案对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="180px">
        <el-form-item label="题目内容" prop="questionId">
          <el-select v-model="form.questionId" placeholder="请选择要添加的题目">
            <el-option
              v-for="question in questionList"
              :key="question.id"
              :label="question.questionContent"
              :value="question.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="选项标识符" prop="optionLabel">
          <el-input v-model="form.optionLabel" placeholder="请输入选项标识符，如：A、B、C、D" />
        </el-form-item>
        <el-form-item label="选项内容">
          <editor v-model="form.content" :min-height="192"/>
        </el-form-item>
        <el-form-item label="是否为正确答案" prop="isCorrect">
          <el-select v-model="form.isCorrect" placeholder="请选择是否为正确答案">
            <el-option label="是" :value="1" />
            <el-option label="否" :value="0" />
          </el-select>
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
import { listOption, getOption, delOption, addOption, updateOption } from "@/api/errorbook/option";
import {countFrontListQuestion} from "@/api/errorbook/question";

export default {
  name: "Option",
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
      // 选项，用于存储每道题的选项及其是否为正确答案表格数据
      optionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        questionId: null,
        optionLabel: null,
        content: null,
        isCorrect: null,
        createdAt: null,
        updatedAt: null
      },
      // 表单参数
      form: {},
      questionList:[],
      // 表单校验
      rules: {
        questionId: [
          { required: true, message: "外键，关联题目表 (questions)，用于标识这个选项属于哪道题不能为空", trigger: "blur" }
        ],
        optionLabel: [
          { required: true, message: "选项标识符，如：A、B、C、D不能为空", trigger: "blur" }
        ],
        content: [
          { required: true, message: "选项内容，表示题目中的具体选项内容不能为空", trigger: "blur" }
        ],
        isCorrect: [
          { required: true, message: "是否为正确答案，布尔值，表示该选项是否正确不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
   async getCountQuestionList(){
     const res = await countFrontListQuestion()
     console.log("getCountQuestionList: ",res)
     this.questionList = res.data

    },
    /** 查询选项，用于存储每道题的选项及其是否为正确答案列表 */
    getList() {
      this.loading = true;
      listOption(this.queryParams).then(response => {
        this.optionList = response.rows;
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
        questionId: null,
        optionLabel: null,
        content: null,
        isCorrect: null,
        createdAt: null,
        updatedAt: null
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
      this.getCountQuestionList()
      this.reset();
      this.open = true;
      this.title = "添加选项";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOption(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改选项";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateOption(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOption(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除选项，用于存储每道题的选项及其是否为正确答案编号为"' + ids + '"的数据项？').then(function() {
        return delOption(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('errorbook/option/export', {
        ...this.queryParams
      }, `option_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>


<style lang="scss" scoped>
.el-input--medium .el-input__inner{
  width: 600px !important;
}

</style>

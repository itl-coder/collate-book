<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="外键，关联题目表 (questions)，用于标识这个选项属于哪道题" prop="questionId">
        <el-input
          v-model="queryParams.questionId"
          placeholder="请输入外键，关联题目表 (questions)，用于标识这个选项属于哪道题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="选项标识符，如：A、B、C、D" prop="optionLabel">
        <el-input
          v-model="queryParams.optionLabel"
          placeholder="请输入选项标识符，如：A、B、C、D"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否为正确答案，布尔值，表示该选项是否正确" prop="isCorrect">
        <el-input
          v-model="queryParams.isCorrect"
          placeholder="请输入是否为正确答案，布尔值，表示该选项是否正确"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="记录创建时间，自动生成" prop="createdAt">
        <el-date-picker clearable
          v-model="queryParams.createdAt"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择记录创建时间，自动生成">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="记录更新时间，每次更新时自动生成" prop="updatedAt">
        <el-date-picker clearable
          v-model="queryParams.updatedAt"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择记录更新时间，每次更新时自动生成">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
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
      <el-table-column label="选项的唯一标识符，主键，用于唯一标识每个选项" align="center" prop="id" />
      <el-table-column label="外键，关联题目表 (questions)，用于标识这个选项属于哪道题" align="center" prop="questionId" />
      <el-table-column label="选项标识符，如：A、B、C、D" align="center" prop="optionLabel" />
      <el-table-column label="选项内容，表示题目中的具体选项内容" align="center" prop="content" />
      <el-table-column label="是否为正确答案，布尔值，表示该选项是否正确" align="center" prop="isCorrect" />
      <el-table-column label="记录创建时间，自动生成" align="center" prop="createdAt" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="记录更新时间，每次更新时自动生成" align="center" prop="updatedAt" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updatedAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
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
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="外键，关联题目表 (questions)，用于标识这个选项属于哪道题" prop="questionId">
          <el-input v-model="form.questionId" placeholder="请输入外键，关联题目表 (questions)，用于标识这个选项属于哪道题" />
        </el-form-item>
        <el-form-item label="选项标识符，如：A、B、C、D" prop="optionLabel">
          <el-input v-model="form.optionLabel" placeholder="请输入选项标识符，如：A、B、C、D" />
        </el-form-item>
        <el-form-item label="选项内容，表示题目中的具体选项内容">
          <editor v-model="form.content" :min-height="192"/>
        </el-form-item>
        <el-form-item label="是否为正确答案，布尔值，表示该选项是否正确" prop="isCorrect">
          <el-input v-model="form.isCorrect" placeholder="请输入是否为正确答案，布尔值，表示该选项是否正确" />
        </el-form-item>
        <el-form-item label="记录创建时间，自动生成" prop="createdAt">
          <el-date-picker clearable
            v-model="form.createdAt"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择记录创建时间，自动生成">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="记录更新时间，每次更新时自动生成" prop="updatedAt">
          <el-date-picker clearable
            v-model="form.updatedAt"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择记录更新时间，每次更新时自动生成">
          </el-date-picker>
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
      this.reset();
      this.open = true;
      this.title = "添加选项，用于存储每道题的选项及其是否为正确答案";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOption(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改选项，用于存储每道题的选项及其是否为正确答案";
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

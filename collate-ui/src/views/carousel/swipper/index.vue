<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="180px">
      <el-form-item label="轮播图标题" prop="title">
        <el-input v-model="queryParams.title" placeholder="请输入轮播图标题" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="展示开始时间" prop="startTime">
        <el-date-picker v-model="queryParams.startTime" type="date" value-format="yyyy-MM-dd" placeholder="请选择展示开始时间"
          :picker-options="startPickerOptions" @change="handleStartTimeChange" clearable />
      </el-form-item>

      <el-form-item label="展示结束时间" prop="endTime">
        <el-date-picker v-model="queryParams.endTime" type="date" value-format="yyyy-MM-dd" placeholder="请选择展示结束时间"
          :picker-options="endPickerOptions" :disabled="!queryParams.startTime" clearable />
      </el-form-item>
      <el-form-item label="是否启用" prop="isActive">
        <el-select v-model="queryParams.isActive" placeholder="请选择状态" clearable style="width: 100%">
          <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['carousel:swipper:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['carousel:swipper:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['carousel:swipper:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['carousel:swipper:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="swipperList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="展览图图标题" align="center" prop="title" show-overflow-tooltip width="150" />
      <el-table-column label="展览图" align="center" prop="imageUrl" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.imageUrl" :width="50" :height="50" />
        </template>
      </el-table-column>
      <el-table-column label="点击跳转链接" align="center" prop="linkUrl" show-overflow-tooltip />
      <el-table-column label="描述文字" align="center" prop="description" show-overflow-tooltip width="170" />
      <!-- <el-table-column label="排序权重(数字越大越靠前)" align="center" prop="sortOrder" /> -->
      <el-table-column label="排序权重" align="center">
        <template #default="{ row }">
          <div class="sort-display">
            <i :class="getSortIcon(row.sortOrder)" :style="{ color: getSortColor(row.sortOrder) }" />
            <span>{{ row.sortOrder }}</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="展示开始时间" align="center" prop="startTime" width="130">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="展示结束时间" align="center" prop="endTime" width="130">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="isActive">
        <template #default="{ row }">
          <el-tag :type="row.isActive ? 'success' : 'danger'">
            {{ row.isActive ? '启用' : '禁用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="150">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['carousel:swipper:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['carousel:swipper:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改轮播图对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="130px">
        <el-form-item label="轮播图标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入轮播图标题" />
        </el-form-item>
        <el-form-item label="图片URL" prop="imageUrl">
          <image-upload v-model="form.imageUrl" />
        </el-form-item>
        <el-form-item label="点击跳转链接" prop="linkUrl">
          <el-input v-model="form.linkUrl" placeholder="请输入点击跳转链接" />
        </el-form-item>
        <el-form-item label="描述文字" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="排序权重" prop="sortOrder">
          <el-input v-model="form.sortOrder" placeholder="请输入排序权重(数字越大越靠前)" />
        </el-form-item>
        <el-form-item label="展示开始时间" prop="startTime">
          <el-date-picker clearable v-model="form.startTime" type="date" value-format="yyyy-MM-dd"
            placeholder="请选择展示开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="展示结束时间" prop="endTime">
          <el-date-picker clearable v-model="form.endTime" type="date" value-format="yyyy-MM-dd"
            placeholder="请选择展示结束时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="是否启用" prop="isActive" :value-key="form.isActive">
          <el-select v-model="form.isActive" placeholder="请选择状态" clearable style="width: 100%">
            <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="创建时间" prop="createdAt">
          <el-date-picker clearable v-model="form.createdAt" type="date" value-format="yyyy-MM-dd"
            placeholder="请选择创建时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="更新时间" prop="updatedAt">
          <el-date-picker clearable v-model="form.updatedAt" type="date" value-format="yyyy-MM-dd"
            placeholder="请选择更新时间">
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
import { listSwipper, getSwipper, delSwipper, addSwipper, updateSwipper } from "@/api/carousel/swipper";

export default {
  name: "Swipper",
  data() {
    return {
      // 其他数据...
      statusOptions: [
        {
          value: 1,
          label: '启用'
        },
        {
          value: 0,
          label: '禁用'
        }
      ],
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
      // 轮播图表格数据
      swipperList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
        imageUrl: null,
        linkUrl: null,
        description: null,
        sortOrder: null,
        startTime: null,
        endTime: null,
        isActive: null,
        createdAt: null,
        updatedAt: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        title: [
          { required: true, message: "轮播图标题不能为空", trigger: "blur" }
        ],
        imageUrl: [
          { required: true, message: "图片URL不能为空", trigger: "blur" }
        ],
        createdAt: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
        updatedAt: [
          { required: true, message: "更新时间不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询轮播图列表 */
    getList() {
      this.loading = true;
      listSwipper(this.queryParams).then(response => {
        this.swipperList = response.rows;
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
        title: null,
        imageUrl: null,
        linkUrl: null,
        description: null,
        sortOrder: null,
        startTime: null,
        endTime: null,
        isActive: null,
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加轮播图";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSwipper(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改轮播图";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSwipper(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSwipper(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除轮播图编号为"' + ids + '"的数据项？').then(function () {
        return delSwipper(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('carousel/swipper/export', {
        ...this.queryParams
      }, `swipper_${new Date().getTime()}.xlsx`)
    },
    getSortIcon(val) {
      if (val > 7) return 'el-icon-top'
      if (val > 3) return 'el-icon-right'
      return 'el-icon-bottom'
    },
    getSortColor(val) {
      if (val > 7) return '#67C23A'
      if (val > 3) return '#E6A23C'
      return '#F56C6C'
    },
    handleStartTimeChange(val) {
      // 当开始时间变更时，如果结束时间早于新开始时间，则清空结束时间
      if (this.queryParams.endTime && new Date(this.queryParams.endTime) < new Date(val)) {
        this.queryParams.endTime = ''
      }
    }
  },
  computed: {
    startPickerOptions() {
      return {
        disabledDate: (time) => {
          // 如果已选择结束时间，开始时间不能晚于结束时间
          if (this.queryParams.endTime) {
            return time.getTime() > new Date(this.queryParams.endTime).getTime()
          }
          return false
        }
      }
    },
    endPickerOptions() {
      return {
        disabledDate: (time) => {
          // 结束时间不能早于开始时间
          if (this.queryParams.startTime) {
            return time.getTime() < new Date(this.queryParams.startTime).getTime()
          }
          return false
        }
      }
    }
  },
};
</script>


<style lang="scss" scoped>
.sort-display {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 5px;
}
</style>
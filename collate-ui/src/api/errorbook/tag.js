import request from '@/utils/request'

// 查询错题标签列表
export function listTag(query) {
  return request({
    url: '/errorbook/tag/list',
    method: 'get',
    params: query
  })
}

// 查询错题标签详细
export function getTag(id) {
  return request({
    url: '/errorbook/tag/' + id,
    method: 'get'
  })
}

// 新增错题标签
export function addTag(data) {
  return request({
    url: '/errorbook/tag',
    method: 'post',
    data: data
  })
}

// 修改错题标签
export function updateTag(data) {
  return request({
    url: '/errorbook/tag',
    method: 'put',
    data: data
  })
}

// 删除错题标签
export function delTag(id) {
  return request({
    url: '/errorbook/tag/' + id,
    method: 'delete'
  })
}

export function countListTag() {
  return request({
    url: '/errorbook/tag/count/list',
    method: 'get',
  })
}

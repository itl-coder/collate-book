import request from '@/utils/request'

// 查询科目分类列表
export function listSubject(query) {
  return request({
    url: '/errorbook/subject/list',
    method: 'get',
    params: query
  })
}

// 查询科目分类详细
export function getSubject(id) {
  return request({
    url: '/errorbook/subject/' + id,
    method: 'get'
  })
}

// 新增科目分类
export function addSubject(data) {
  return request({
    url: '/errorbook/subject',
    method: 'post',
    data: data
  })
}

// 修改科目分类
export function updateSubject(data) {
  return request({
    url: '/errorbook/subject',
    method: 'put',
    data: data
  })
}

// 删除科目分类
export function delSubject(id) {
  return request({
    url: '/errorbook/subject/' + id,
    method: 'delete'
  })
}

// 查询科目分类列表
export function countListSubject() {
  return request({
    url: '/errorbook/subject/count/list',
    method: 'get'
  })
}

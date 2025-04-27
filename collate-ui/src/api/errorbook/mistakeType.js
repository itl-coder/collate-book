import request from '@/utils/request'

// 查询错题类型列表
export function listMistakeType(query) {
  return request({
    url: '/errorbook/mistakeType/list',
    method: 'get',
    params: query
  })
}

// 查询错题类型详细
export function getMistakeType(id) {
  return request({
    url: '/errorbook/mistakeType/' + id,
    method: 'get'
  })
}

// 新增错题类型
export function addMistakeType(data) {
  return request({
    url: '/errorbook/mistakeType',
    method: 'post',
    data: data
  })
}

// 修改错题类型
export function updateMistakeType(data) {
  return request({
    url: '/errorbook/mistakeType',
    method: 'put',
    data: data
  })
}

// 删除错题类型
export function delMistakeType(id) {
  return request({
    url: '/errorbook/mistakeType/' + id,
    method: 'delete'
  })
}

import request from '@/utils/request'

// 查询用户题目状态列表
export function listStatus(query) {
  return request({
    url: '/errorbook/status/list',
    method: 'get',
    params: query
  })
}

// 查询用户题目状态详细
export function getStatus(userId) {
  return request({
    url: '/errorbook/status/' + userId,
    method: 'get'
  })
}

// 新增用户题目状态
export function addStatus(data) {
  return request({
    url: '/errorbook/status',
    method: 'post',
    data: data
  })
}

// 修改用户题目状态
export function updateStatus(data) {
  return request({
    url: '/errorbook/status',
    method: 'put',
    data: data
  })
}

// 删除用户题目状态
export function delStatus(userId) {
  return request({
    url: '/errorbook/status/' + userId,
    method: 'delete'
  })
}

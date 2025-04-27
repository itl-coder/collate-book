import request from '@/utils/request'

// 查询选项，用于存储每道题的选项及其是否为正确答案列表
export function listOption(query) {
  return request({
    url: '/errorbook/option/list',
    method: 'get',
    params: query
  })
}

// 查询选项，用于存储每道题的选项及其是否为正确答案详细
export function getOption(id) {
  return request({
    url: '/errorbook/option/' + id,
    method: 'get'
  })
}

// 新增选项，用于存储每道题的选项及其是否为正确答案
export function addOption(data) {
  return request({
    url: '/errorbook/option',
    method: 'post',
    data: data
  })
}

// 修改选项，用于存储每道题的选项及其是否为正确答案
export function updateOption(data) {
  return request({
    url: '/errorbook/option',
    method: 'put',
    data: data
  })
}

// 删除选项，用于存储每道题的选项及其是否为正确答案
export function delOption(id) {
  return request({
    url: '/errorbook/option/' + id,
    method: 'delete'
  })
}

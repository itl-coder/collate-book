import request from '@/utils/request'

// 查询错题笔记列表
export function listErrornote(query) {
  return request({
    url: '/errorbook/errornote/list',
    method: 'get',
    params: query
  })
}

// 查询错题笔记详细
export function getErrornote(id) {
  return request({
    url: '/errorbook/errornote/' + id,
    method: 'get'
  })
}

// 新增错题笔记
export function addErrornote(data) {
  return request({
    url: '/errorbook/errornote',
    method: 'post',
    data: data
  })
}

// 修改错题笔记
export function updateErrornote(data) {
  return request({
    url: '/errorbook/errornote',
    method: 'put',
    data: data
  })
}

// 删除错题笔记
export function delErrornote(id) {
  return request({
    url: '/errorbook/errornote/' + id,
    method: 'delete'
  })
}

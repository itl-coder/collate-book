import request from '@/utils/request'

// 查询错题列表
export function listErrorquestion(query) {
  return request({
    url: '/errorbook/errorquestion/list',
    method: 'get',
    params: query
  })
}

// 查询错题详细
export function getErrorquestion(id) {
  return request({
    url: '/errorbook/errorquestion/' + id,
    method: 'get'
  })
}

// 新增错题
export function addErrorquestion(data) {
  return request({
    url: '/errorbook/errorquestion',
    method: 'post',
    data: data
  })
}

// 修改错题
export function updateErrorquestion(data) {
  return request({
    url: '/errorbook/errorquestion',
    method: 'put',
    data: data
  })
}

// 删除错题
export function delErrorquestion(id) {
  return request({
    url: '/errorbook/errorquestion/' + id,
    method: 'delete'
  })
}

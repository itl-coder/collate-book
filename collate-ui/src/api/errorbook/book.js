import request from '@/utils/request'

// 查询教辅分类列表
export function listBook(query) {
  return request({
    url: '/errorbook/book/list',
    method: 'get',
    params: query
  })
}

// 查询教辅分类详细
export function getBook(id) {
  return request({
    url: '/errorbook/book/' + id,
    method: 'get'
  })
}

// 新增教辅分类
export function addBook(data) {
  return request({
    url: '/errorbook/book',
    method: 'post',
    data: data
  })
}

// 修改教辅分类
export function updateBook(data) {
  return request({
    url: '/errorbook/book',
    method: 'put',
    data: data
  })
}

// 删除教辅分类
export function delBook(id) {
  return request({
    url: '/errorbook/book/' + id,
    method: 'delete'
  })
}
// 查询教辅分类列表
export function countListBook() {
  return request({
    url: '/errorbook/book/count/list',
    method: 'get',
  })
}

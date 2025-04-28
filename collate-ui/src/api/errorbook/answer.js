import request from '@/utils/request'

// 查询用户作答列表
export function listAnswer(query) {
  return request({
    url: '/errorbook/answer/list',
    method: 'get',
    params: query
  })
}

// 查询用户作答详细
export function getAnswer(id) {
  return request({
    url: '/errorbook/answer/' + id,
    method: 'get'
  })
}

// 新增用户作答
export function addAnswer(data) {
  return request({
    url: '/errorbook/answer',
    method: 'post',
    data: data
  })
}

// 修改用户作答
export function updateAnswer(data) {
  return request({
    url: '/errorbook/answer',
    method: 'put',
    data: data
  })
}

// 删除用户作答
export function delAnswer(id) {
  return request({
    url: '/errorbook/answer/' + id,
    method: 'delete'
  })
}

// 加入错题本的数据
export function frontListAnswer(query) {
  return request({
    url: '/errorbook/answer/front/list',
    method: 'get',
    params: query
  })
}

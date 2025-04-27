import request from '@/utils/request'

// 查询轮播图列表
export function listSwipper(query) {
  return request({
    url: '/carousel/swipper/list',
    method: 'get',
    params: query
  })
}

// 查询轮播图详细
export function getSwipper(id) {
  return request({
    url: '/carousel/swipper/' + id,
    method: 'get'
  })
}

// 新增轮播图
export function addSwipper(data) {
  return request({
    url: '/carousel/swipper',
    method: 'post',
    data: data
  })
}

// 修改轮播图
export function updateSwipper(data) {
  return request({
    url: '/carousel/swipper',
    method: 'put',
    data: data
  })
}

// 删除轮播图
export function delSwipper(id) {
  return request({
    url: '/carousel/swipper/' + id,
    method: 'delete'
  })
}

/**
 * 轮播图前台动态展示
 */
export function getSwipperList() {
  return request({
    url: '/carousel/swipper/front/list',
    method: 'get'
  })
}
import request from '@/utils/request'

// 查询错题统计列表
export function listStatistics(query) {
  return request({
    url: '/errorbook/statistics/list',
    method: 'get',
    params: query
  })
}

// 查询错题统计详细
export function getStatistics(id) {
  return request({
    url: '/errorbook/statistics/' + id,
    method: 'get'
  })
}

// 新增错题统计
export function addStatistics(data) {
  return request({
    url: '/errorbook/statistics',
    method: 'post',
    data: data
  })
}

// 修改错题统计
export function updateStatistics(data) {
  return request({
    url: '/errorbook/statistics',
    method: 'put',
    data: data
  })
}

// 删除错题统计
export function delStatistics(id) {
  return request({
    url: '/errorbook/statistics/' + id,
    method: 'delete'
  })
}

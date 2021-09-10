/*
 * action 类型
 */

export const SET_STATUS = 'SET_STATUS';
export const TOGGLE_SIDER = 'TOGGLE_SIDER';
export const UPDATE_TIME = 'UPDATE_TIME';

/*
 * 其它的常量
 */
export const statusTypes = {
  SUPER_ADMINISTRATOR: "SUPER_ADMINISTRATOR",
  ADMINISTRATOR: "ADMINISTRATOR",
  USER: "USER",
  GUEST: "GUEST"
}
export const siderStates = {
  OPEN: "OPEN",
  CLOSE: "CLOSE",
}
/*
 * action 创建函数
 */

export function setStatus(status) {
  return { type: SET_STATUS, status }
}
export function toggleSider() {
  return { type: TOGGLE_SIDER }
}
export function updateTime() {
  return { type: UPDATE_TIME }
}






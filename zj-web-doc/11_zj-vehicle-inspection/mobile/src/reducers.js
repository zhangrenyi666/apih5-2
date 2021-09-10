import moment from 'moment'
import 'moment/locale/zh-cn';
import { SET_STATUS, statusTypes, TOGGLE_SIDER, siderStates, UPDATE_TIME } from './actions'
const { GUEST } = statusTypes
const { OPEN, CLOSE } = siderStates
function status(state = GUEST, action) {
  switch (action.type) {
    case SET_STATUS:
      return action.status
    default:
      return state
  }
}
function sider(state = OPEN, action) {
  switch (action.type) {
    case TOGGLE_SIDER:
      return state === OPEN ? CLOSE : OPEN
    default:
      return state
  }
}


function time(state = moment(), action) {
  switch (action.type) {
    case UPDATE_TIME:
      return moment()
    default:
      return state
  }
}

const reducers = {
  status,
  sider,
  time
}


export default reducers
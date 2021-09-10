import {  SET_LOGOUTANDLOGOUTINFO, SET_FETCHDATAS } from './actions'
import MyViews from '../../views'
const { reducers: viewReducers } = MyViews
function loginAndLogoutInfo(state = null, action) {
    switch (action.type) {
        case SET_LOGOUTANDLOGOUTINFO:
            return action.data
        default:
            return state
    }
}
function fetchDatas(state = JSON.stringify({}), action) {
    switch (action.type) {
        case SET_FETCHDATAS:
            return action.fetchDatas
        default:
            return state
    }
}
function actions(state = {}, action) {
    return state
}
function corpInfo(state = {}, action) {
    return state
}
const reducers = {
    ...viewReducers,
    corpInfo,//
    loginAndLogoutInfo,
    fetchDatas,
    actions,
}


export default reducers
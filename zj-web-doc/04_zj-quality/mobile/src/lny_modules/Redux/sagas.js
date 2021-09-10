import { take, select, put, call, all, fork, cancelled, cancel } from 'redux-saga/effects'
import {
    SET_LOGOUTANDLOGOUTINFO,
    SET_FETCHDATAS,
    LOGIN_REQUEST,
    LOGOUT,
    TAB_LOGINTYPE,
    SET_REDIRECTINFO,
    LOGIN_ERROR,
    FETCH,
} from './actions'
import MyUser from "../User"
import MyViews from '../../views'
import MyFetch from '../Fetch';
const { sagas: viewSagas } = MyViews
function* authorize(action) {
    try {
        let { loginInfo: _loginInfo, logoutInfo: _logoutInfo } = yield call(MyUser.openLoginLoading)
        yield put({ type: SET_LOGOUTANDLOGOUTINFO, data: { loginInfo: _loginInfo, redirectInfo: null, logoutInfo: _logoutInfo } })
        let { loginInfo, redirectInfo, logoutInfo } = yield call(MyUser.login, action)
        yield put({ type: SET_LOGOUTANDLOGOUTINFO, data: { loginInfo, redirectInfo, logoutInfo } })
        return loginInfo
    } catch (error) {
        yield put({ type: LOGIN_ERROR, error })
    } finally {
        if (yield cancelled()) {
            console.log("取消登录")
            // ... 在這裡放置特殊的取消操作程式碼
        }
    }
}


function* loginFlow() {
    while (true) {
        const action = yield take([LOGIN_REQUEST, LOGOUT, TAB_LOGINTYPE, SET_REDIRECTINFO])
        if (action.type === LOGOUT) {
            let logoutInfo = yield call(MyUser.logout, action.pathName)
            yield put({ type: SET_LOGOUTANDLOGOUTINFO, data: { loginInfo: null, redirectInfo: null, logoutInfo } })
        } else if (action.type === TAB_LOGINTYPE) {
            let { loginType } = action
            let logoutInfo = yield call(MyUser.tabLoginType, loginType)
            yield put({ type: SET_LOGOUTANDLOGOUTINFO, data: { loginInfo: null, redirectInfo: null, logoutInfo } })
        } else if (action.type === SET_REDIRECTINFO) {
            let { loginInfo, redirectInfo } = yield call(MyUser.setRedirectInfo, action.redirectInfo)
            yield put({ type: SET_LOGOUTANDLOGOUTINFO, data: { loginInfo, redirectInfo, logoutInfo: null } })
        } else {
            const task = yield fork(authorize, action)
            const _action = yield take([LOGIN_ERROR, LOGOUT, SET_REDIRECTINFO])
            if (_action.type === LOGOUT) {
                let logoutInfo = yield call(MyUser.logout, _action.pathName)
                yield cancel(task)
                yield put({ type: SET_LOGOUTANDLOGOUTINFO, data: { loginInfo: null, redirectInfo: null, logoutInfo } })
            } else if (_action.type === SET_REDIRECTINFO) {
                let { loginInfo, redirectInfo } = yield call(MyUser.setRedirectInfo, _action.redirectInfo)
                yield put({ type: SET_LOGOUTANDLOGOUTINFO, data: { loginInfo, redirectInfo, logoutInfo: null } })
            }
        }
    }
}
function* fetchFlow() {
    while (true) {
        let { apiName = "", body = {}, fetchName, pathName, dataType } = yield take(FETCH)
        fetchName = fetchName || apiName
        let fetchDatas = yield select((state) => state.fetchDatas)
        fetchDatas = JSON.parse(fetchDatas)
        if (fetchDatas[fetchName]) {
            fetchDatas[fetchName] = { ...fetchDatas[fetchName], loading: true }
        } else {
            fetchDatas[fetchName] = { loading: true }
        }
        yield put({ type: SET_FETCHDATAS, fetchDatas: JSON.stringify(fetchDatas) })
        const fetchData = yield call(MyFetch, apiName, body, dataType)
        if (!fetchData.success && (fetchData.code === "3003" || fetchData.code === "3004")) {
            fetchDatas[fetchName] = { ...fetchDatas[fetchName], loading: false }
            yield put({ type: SET_FETCHDATAS, fetchDatas })
            yield put({ type: LOGOUT, pathName })
        } else {
            fetchDatas[fetchName] = { ...fetchData, loading: false }
            yield put({ type: SET_FETCHDATAS, fetchDatas: JSON.stringify(fetchDatas) })
        }
    }
}

export default function* rootSaga() {
    yield all([
        ...viewSagas,
        loginFlow(),
        fetchFlow()
    ])
}
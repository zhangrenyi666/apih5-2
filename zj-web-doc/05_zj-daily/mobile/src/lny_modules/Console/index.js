export const createConsole = (moduleName) => {
    return {
        log: (...arg) => { console.log(`${moduleName}:`, ...arg) },
        error: (...arg) => { console.error(`${moduleName}:`, ...arg) },
        warn: (...arg) => { console.warn(`${moduleName}:`, ...arg) },
        info: (...arg) => { console.info(`${moduleName}:`, ...arg) },
    }
}
const MyConsole = createConsole("未知模块：")
export default MyConsole  
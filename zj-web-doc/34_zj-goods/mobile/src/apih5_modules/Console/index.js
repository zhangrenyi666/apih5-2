const { debugConsole } = window.configs;
export const createConsole = (moduleName) => {
    if (debugConsole) {
        return {
            log: (...arg) => { console.log(`${moduleName}:`, ...arg) },
            error: (...arg) => { console.error(`${moduleName}:`, ...arg) },
            warn: (...arg) => { console.warn(`${moduleName}:`, ...arg) },
            info: (...arg) => { console.info(`${moduleName}:`, ...arg) },
        }
    } else {
        return {
            log:()=>{},
            error:()=>{},
            warn:()=>{},
            info:()=>{},
        }
    }

}
const MyConsole = createConsole("未知模块：")
export default MyConsole  

import registerServiceWorker from './registerServiceWorker';
import apih5 from "./apih5_modules"

//热更新标识
window.hotRender = false;
//首次请求后一些数据会放入进这个变量中。下次热更新直接使用接口
window.hotRenderData = {};

apih5.start()
registerServiceWorker();

//启用热更新
if (module.hot) {
    // module.hot.accept()
    module.hot.accept("./apih5_modules", () => {
        window.hotRender = true;
        apih5.start()
    })
}

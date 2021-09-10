// import '@babel/polyfill'; 
import registerServiceWorker from './registerServiceWorker';
import apih5 from "./apih5_modules"
apih5.start()
registerServiceWorker();

//启用热更新
if (module.hot) {
    module.hot.accept()
}

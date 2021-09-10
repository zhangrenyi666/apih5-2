// import 'babel-polyfill'; 
import registerServiceWorker from './registerServiceWorker';
import lny from "./lny_modules"
lny.start()
registerServiceWorker();
import React from 'react';
import ReactDOM from 'react-dom';
import { setTimeout } from 'timers';
import './index.css';
import App from './App';
import registerServiceWorker from './registerServiceWorker';
setTimeout(() => { 
    ReactDOM.render(<App />, document.getElementById('root'));
}, 500)
registerServiceWorker();

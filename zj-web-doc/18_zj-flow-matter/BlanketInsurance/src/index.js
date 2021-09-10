
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

/**
 * 以下代码用于与开发平台 webview 进程通信
 * 全局监听点击事件,传送会主进程
 * 监听主进程信息
 */
 if (window.qnn && window.qnn.electron) {
	// 监听路由
	window.addEventListener("hashchange", function(e) {
		window.parent._navigateInPage({
			event: e,
			location: window.location,
		});
	});

	// 父级消息监听
	window.listenerParentMessage = (arg) => {
		const { type, content } = arg;
		switch (type) {
			case "message":
				console.log("收到父级信息：", content);
				break;
			default:
				break;
		}
		console.log(arg);
	};

	window.addEventListener("click", (e) => {
		window.parent._webviewClickCB({ event: e });
	});

	// 鼠标右键事件监听
	window.addEventListener("contextmenu", (e) => {
		window.parent._webviewContextmenuCB({ event: e });
	});
}

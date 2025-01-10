/* ===============聊天室============= */


function getCoustomerList() {

	const receiver = document.querySelector('.receiver').value;
	const chatList = document.getElementById("chat-list");

	if (!chatList) {
		console.error("Element with id 'chat-list' not found!");
	}


	// 准备发送的数据
	const requestBody = {
		receiver: receiver
	};

	// 使用 fetch 发送 POST 请求
	fetch("/C/getChatList", {
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(requestBody)
	})
		.then(response => {
			if (!response.ok) {
				throw new Error("Network response was not ok");
			}
			return response.text(); // 解析为 HTML 片段
		})
		.then(html => {
			const chatList = document.getElementById("chat-list");

			// 将聊天框内容加载到 #chat-box
			chatList.innerHTML = html;

			bindMinimizeButton();
			expandChatList();
			// 更新全局变量
			window.receiver = receiver;


		})
		.catch(error => console.error("加载错误:", error));
}


document.addEventListener("DOMContentLoaded", () => {
	// 页面加载完成后调用 getCoustomerList 方法
	getCoustomerList();
	
});


document.getElementById("chat-button").addEventListener("click", () => {
	const chatContainer = document.getElementById("chat-container");

	if (chatContainer.classList.contains("hidden")) {
		chatContainer.classList.remove("hidden");
		return;
	}

	// 显示聊天框
	chatContainer.style.display = "flex";
});

/* ===============聊天室============= */
/* ===============聊天室放大縮小按鈕============= */
function expandChatList() {

	const expandButton = document.getElementById("expand-button");
	const contentExpandButton = document.getElementById("content-expand-button");
	const minimizeButton = document.getElementById("minimize-button");
	const chatContent = document.getElementById("chat-content");
	const chatList = document.getElementById("chat-list");
	if (!expandButton) {
		console.error("未找到 expand-button 按钮");
		return;
	}

	if (!chatContent.classList.contains("hidden")) {
		expandButton.classList.add("button-hidden");
		minimizeButton.classList.add("button-hidden");
	}

	contentExpandButton.addEventListener("click", () => {
		// 后续点击切换 'hidden' 类
		chatContent.classList.add("hidden");
		chatContent.style.display = "none";
		expandButton.classList.remove("button-hidden");
		minimizeButton.classList.remove("button-hidden");

		// 将左侧列表移到父容器最右边
		chatList.style.right = "0px"; // 移到最右侧
	});

	expandButton.addEventListener("click", () => {
		// 后续点击切换 'hidden' 类
		chatContent.classList.remove("hidden");
		chatContent.style.display = "block";
		expandButton.classList.add("button-hidden");
		minimizeButton.classList.add("button-hidden");
		// 将左侧列表移动到父容器的原始位置
		chatList.style.right = "calc(50% + 50px)"; // 回到原位
	});
}

// 插入 Fragment 后绑定事件监听器
function bindMinimizeButton() {
	const chatList = document.getElementById("chat-list");
	const minimizeButton = document.getElementById("minimize-button");
	const contentMinimizeButton = document.getElementById("content-minimize-button");
	const chatContainer = document.getElementById("chat-container");

	if (!minimizeButton) {
		console.error("未找到 minimize-button 按钮");
		return;
	}

	contentMinimizeButton.addEventListener("click", () => {
		if (!chatList.classList.contains("hidden")) {
			chatContainer.classList.add("hidden");
		}
	});

	minimizeButton.addEventListener("click", () => {
		if (!chatList.classList.contains("hidden")) {
			chatContainer.classList.add("hidden");
		}
	});
}
/* ===============聊天室放大縮小按鈕============= */

/* ===============聊天室客戶列表訂閱============= */
// 创建一个新的 WebSocket 连接
const username = document.querySelector('.receiver').value;
const socket = new SockJS(`/ws?username=${encodeURIComponent(username)}`); // 将用户名拼接到 URL 中
const stompClient = Stomp.over(socket);
// 建立连接
stompClient.connect({}, () => {
	console.log('Connected to WebSocket');
	// 订阅用户的消息队列
	stompClient.subscribe('/user/queue/messages', (message) => {
		console.log("訂閱成功，收到推播：", message.body);
		const msg = JSON.parse(message.body);
		console.log("Received message:", JSON.parse(message.body));
	
		// 获取 #chat-header-list 容器
		const chatHeaderList = document.getElementById("chat-header-list");
		const counterCname = document.querySelector(".receiver").value;
		const coustomer = document.querySelector(".coustomerName") 
		    ? document.querySelector(".coustomerName").value 
		    : null;
		const chatWindow = document.getElementById('chat-window');
		
		console.log(coustomer);

		if (msg.sender === coustomer || msg.receiver === coustomer) {
			const msgDiv = document.createElement('div');
			msgDiv.className = msg.sender === counterCname ? 'mes right' : 'mes left';
			msgDiv.textContent = `${msg.content}`;
			chatWindow.appendChild(msgDiv);

			// 自动滚动到最新消息
			chatWindow.scrollTop = chatWindow.scrollHeight;
		}
		
		if(msg.sender === counterCname){
			return;
		}
		// 初始化一个数组来存储所有的 coustomer 值
		const receiverValues = [];
		const receivers = document.querySelectorAll(".coustomerReceiver");
		receivers.forEach(input => {
			receiverValues.push(input.value);
		});

		if (receiverValues.includes(msg.sender)) {
			document.querySelector(`[data-coustomer="${msg.sender}"]`).remove();
			// 如果 sender 已存在，可以执行相应逻辑
		}

		// 创建新的 div 元素
		const newDiv = document.createElement("div");
		newDiv.setAttribute("data-coustomer", msg.sender); // 设置自定义属性
		newDiv.className = "coustomer-item"; // 设置 class
		
		// 添加点击事件
		newDiv.addEventListener("click", function() {
		    getChatContext(this);
		});

		// 创建 input 元素
		const input = document.createElement("input");
		input.type = "hidden";
		input.className = "coustomerReceiver";
		input.value = msg.sender; // 设置 input 的值
		newDiv.appendChild(input);//??

		// 创建 span 元素
		const span = document.createElement("span");
		span.className = "customer-name";
		span.textContent = msg.sender; // 设置显示的名字
		newDiv.appendChild(span);//??

		// 在 chat-header-list 后面插入新元素
		chatHeaderList.insertAdjacentElement("afterend", newDiv);
		
		

	});
}, (error) => {
	console.error('WebSocket connection error:', error);
});


/* ===============聊天室客戶列表訂閱============= */

/* ===============聊天室內容============= */
function getChatContext(element) {
	const expandButton = document.getElementById("expand-button");
	const chatContent = document.getElementById("chat-content");
	const sender = element.getAttribute("data-coustomer");
	const receiver = document.querySelector('.receiver').value;

	if (!sender) {
		alert("請先登入");
		return;
	}

	if (chatContent.classList.contains("hidden")) {
		expandButton.click();
	}
	
	// 准备发送的数据
	const requestBody = {
		sender: sender,
		receiver: receiver
	};

	// 使用 fetch 发送 POST 请求
	fetch("/C/startChat", {
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(requestBody)
	})
		.then(response => {
			if (!response.ok) {
				throw new Error("Network response was not ok");
			}
			return response.text(); // 解析为 HTML 片段
		})
		.then(html => {
			const chatBox = document.getElementById("chat-box");
			
			
			// 清空聊天框內容
			chatBox.innerHTML = "";

			// 将聊天框内容加载到 #chat-box
			chatBox.innerHTML = html;

			const chatWindow = document.getElementById('chat-window');
			if (!chatWindow) {
				console.log("chat-window 未加载成功");
				return;
			}

			// 显示聊天框
			chatBox.style.display = "block";

			// 更新全局变量
			window.sender = sender;
			window.receiver = receiver;

			// 动态绑定事件监听器
			bindSendButton(sender, receiver);
			
			// 更新聊天室名稱
			updateHeaderWithSender();
			
			// 自动滚动到最新消息
			chatWindow.scrollTop = chatWindow.scrollHeight;
		})
		.catch(error => console.error("加载错误:", error));
}


/* ===============聊天室內容============= */
/* ===============聊天室送出按鈕============= */
function handleKeyPress(event) {
	if (event.key === 'Enter') {
		const messageInput = document.getElementById('message-input');

		if (!messageInput) {
			console.error("聊天框中的元素未加载，无法绑定事件监听器。");
			return;
		}

		const content = messageInput.value;
		if (!content.trim()) return; // 如果消息为空，不发送

		const message = {
			sender: receiver,
			receiver: sender, //倒過來(跟接收的時候相反)
			content: content,
			timestamp: new Date().toISOString()
		};

		stompClient.send("/app/sendMessage", {}, JSON.stringify(message));
		messageInput.value = ''; // 清空输入框
	}
}
function bindSendButton(sender, receiver) {
	const sendButton = document.getElementById('send-icon');
	const messageInput = document.getElementById('message-input');

	if (!sendButton || !messageInput) {
		console.error("聊天框中的元素未加载，无法绑定事件监听器。");
		return;
	}

	sendButton.addEventListener('click', () => {
		const content = messageInput.value;
		if (!content.trim()) return; // 如果消息为空，不发送

		const message = {
			sender: receiver,
			receiver: sender, //倒過來(跟接收的時候相反)
			content: content,
			timestamp: new Date().toISOString()
		};

		stompClient.send("/app/sendMessage", {}, JSON.stringify(message));
		messageInput.value = ''; // 清空输入框
	});
}

/* ===============聊天室送出按鈕============= */

/* ===============聊天室title============= */
// 插入 HTML 後執行的代碼
function updateHeaderWithSender() {
    // 找到插入的 `input` 元素
    const coustomerNameInput = document.querySelector(".coustomerName");
	const chatHeader = document.getElementById("chat-header-content");
	
    if (coustomerNameInput) {
        // 獲取 `value` 值
        const senderValue = coustomerNameInput.value;
		
		const existingHeaderItem = chatHeader.querySelector(".new-header-item");
		if (existingHeaderItem) {
		    chatHeader.removeChild(existingHeaderItem);
		}
		// 創建新的 div 元素
		const newDiv = document.createElement("div");
		newDiv.className = "new-header-item"; // 添加 class（可選）
		
		// 创建 span 元素
		const span = document.createElement("span");
		span.className = "customer-chatroom";
		span.textContent = `與 ${senderValue} 的聊天室`;
		newDiv.appendChild(span);//??
		// 插入到 chatHeader 的內層最前面
		chatHeader.insertBefore(newDiv, chatHeader.firstChild);

    } else {
        console.error("無法找到 .coustomerName 元素！");
    }
}

/* ===============聊天室title============= */
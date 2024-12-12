document.addEventListener("DOMContentLoaded", function() {
	// 获取输入字段
	const memAccount = document.getElementById("memAccount");
	const memPassword = document.getElementById("memPassword");
	const confirmPassword = document.getElementById("confirmPassword");

	// 添加事件监听器，直接阻止非法字符输入
	function restrictInput(event) {
		// 允许的键：英文字符（大小写）和数字
		const allowedKeys = /^[a-zA-Z0-9]$/;

		// 获取当前按下的键
		const key = event.key;

		// 如果按键不匹配正则，则阻止默认行为
		if (!allowedKeys.test(key)) {
			event.preventDefault();
		}
	}

	// 绑定事件到输入框
	memAccount.addEventListener("keypress", restrictInput);
	memPassword.addEventListener("keypress", restrictInput);
	confirmPassword.addEventListener("keypress", restrictInput);
});


//發送驗證碼
function sendVerificationCode() {
	const email = document.getElementById('memEmail').value.trim();
	const firstButton = document.getElementById('firstButton');

	console.log(email);
	if (!email) {
		alert('請輸入信箱');
		return;
	}

	fetch('/email/sent', {
		method: 'POST',
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify({ email })
	})
		.then(response => response.json())
		.then(data => {
			if (data.success === "true") {
				alert(data.message); // 顯示返回的成功消息

				// 顯示驗證碼輸入框
				document.getElementById('verification-section').style.display = 'block';
				disableButtonWithCountdown(firstButton, 2 * 5);
			} else if (data.success === "false") {
				alert(data.message);
			} else {
				alert('發送失敗，請稍後再試！');
			}

		})
		.catch(error => {
			console.error('錯誤:', error);
			alert('發送失敗，請稍後再試！');
		});

}


// 禁用按鈕並倒數
function disableButtonWithCountdown(button, seconds) {
	button.disabled = true; // 禁用按鈕
	let remainingTime = seconds;

	// 設置按鈕文字為剩餘時間
	const intervalId = setInterval(() => {
		remainingTime--;
		button.textContent = `請稍等 ${remainingTime} 秒`;

		if (remainingTime <= 0) {
			clearInterval(intervalId); // 清除計時器
			button.disabled = false; // 啟用按鈕
			button.textContent = '發送驗證碼'; // 恢復原文字
		}
	}, 1000); // 每秒更新一次
}


// 驗證驗證碼
function verifyCode() {
	const verificationCode = document.getElementById('memEmailConfirm').value.trim();
	const email = document.getElementById('memEmail').value.trim();

	if (!verificationCode) {
		alert('請輸入驗證碼');
		return;
	}

	// 模擬驗證邏輯
	fetch('/email/verify', {
		method: 'POST',
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify({
			code: verificationCode,
			email: email
		})
	})
		.then(response => response.json())
		.then(data => {
			if (data.success === "true") {
				alert(data.message);
				// 顯示驗證碼輸入框
				document.getElementById('verification-section').style.display = 'none';
				//輸入文字框readonly
				document.getElementById('memEmail').setAttribute('readonly', true);
				//鎖發送驗證碼按紐
				document.getElementById('firstButton').setAttribute('disabled', true);
			} else if (data.success === "false") {
				alert(data.message);
			}
		})
		.catch(error => {
			console.error('錯誤:', error);
			alert('驗證失敗，請稍後再試！');
		});
}

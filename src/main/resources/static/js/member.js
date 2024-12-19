let items = document.querySelectorAll('.goods .item');
let itemsToShow = 4; // 初始顯示的商品數量

// 顯示指定數量的商品
function showItems() {
	for (let i = 0; i < items.length; i++) {
		if (i < itemsToShow) {
			items[i].classList.remove('hide'); // 顯示商品
		}
	}
}

// 檢查滾動位置，觸發加載更多商品
function handleScroll() {
	// 檢查是否已經滾動到頁面底部
	let scrollPosition = window.scrollY + window.innerHeight;
	let pageHeight = document.documentElement.scrollHeight;

	if (scrollPosition >= pageHeight - 10 && itemsToShow < items.length) {
		// 滾動到頁面底部並且還有商品未顯示
		itemsToShow += 4; // 顯示更多商品
		showItems(); // 顯示新商品
	}
}


// 初始加載 4 個商品
showItems();

// 當滾動時，檢查是否需要顯示更多商品
window.addEventListener('scroll', handleScroll);

$(".mempro").on("click", function() {
	$(".two").removeClass("aaa");
	$(".twogoods span#a1").removeClass("aaa");
	$(".buy").removeClass("aaa");
	$("div#b1").removeClass("aaa");
	$(".prothings").slideDown(1000);
	$(".twothings").slideUp(1000);
	$(".proac").addClass("aaa");
	$(".pro span#1").addClass("aaa");

	const itemId = $("span").data("id"); // 取得 data-id 的值


	// 發送 AJAX 請求到伺服器
	$.ajax({
		url: "content/profile", // 後端的 API 路徑
		method: "GET", // 或 "POST"，根據需求
		success: function(response) {
			// 成功後更新 use_1 區塊
			$(".use_1").html(response);
		},
		error: function(xhr, status, error) {
			console.error("發生錯誤:", error);
			alert("無法更新內容，請稍後再試。");
		},
	});

});
$(".proac").on("click", function() {
	$(".two").removeClass("aaa");
	$(".twogoods span#a1").removeClass("aaa");
	$(".pro span#2").removeClass("aaa");
	$(".pro span#3").removeClass("aaa");
	$(".pro span#4").removeClass("aaa");
	$(".pro span#5").removeClass("aaa");
	$(".buy").removeClass("aaa");
	$("div#b1").removeClass("aaa");
	$(".prothings").slideDown(1000);
	$(".twothings").slideUp(1000);
	$(".pro span#1").addClass("aaa");

	const itemId = $("span").data("id"); // 取得 data-id 的值
	// 發送 AJAX 請求到伺服器
	$.ajax({
		url: "content/profile", // 後端的 API 路徑
		method: "GET", // 或 "POST"，根據需求
		data: { id: itemId }, // 傳遞參數給後端
		success: function(response) {
			// 成功後更新 use_1 區塊
			$(".use_1").html(response);
		},
		error: function(xhr, status, error) {
			console.error("發生錯誤:", error);
			alert("請先登入");
		},
	});


	if ($(".proac").hasClass("aaa")) {
		$(".proac").removeClass("aaa");
		$(".pro span#1").removeClass("aaa");
		$(".prothings").slideUp(600);
	} else {
		$(".proac").addClass("aaa");
	}
});

$(".pro span#1").on("click", function() {
	$(".proac").addClass("aaa");
	$(".pro span#1").addClass("aaa");
	$(".pro span#2").removeClass("aaa");
	$(".pro span#3").removeClass("aaa");
	$(".pro span#4").removeClass("aaa");
	$(".pro span#5").removeClass("aaa");
	const itemId = $("span").data("id"); // 取得 data-id 的值
	// 發送 AJAX 請求到伺服器
	$.ajax({
		url: "content/profile", // 後端的 API 路徑
		method: "GET", // 或 "POST"，根據需求
		data: { id: itemId }, // 傳遞參數給後端
		success: function(response) {
			// 成功後更新 use_1 區塊
			$(".use_1").html(response);

		},
		error: function(xhr, status, error) {
			console.error("發生錯誤:", error);
			alert("無法更新內容，請稍後再試。");
		},
	});
});

$(".buy").on("click", function() {
	window.location.href = "member";
})




$(".two").on("click", function() {
	$(".proac").removeClass("aaa");
	$(".pro span#1").removeClass("aaa");
	$(".buy").removeClass("aaa");
	$("div#b1").removeClass("aaa");
	$(".twothings").slideDown(800);
	$(".prothings").slideUp(500);

	$(".twogoods span#a1").addClass("aaa");
	if ($(".two").hasClass("aaa")) {
		$(".two").removeClass("aaa");
		$(".twogoods span#a1").removeClass("aaa");
		$(".twothings").slideUp(600);
	} else {
		$(".two").addClass("aaa");
	}
});

$("span#4").on("click", function() {
	$(".proac").removeClass("aaa");
	$(".pro span#1").removeClass("aaa");
	$("span#4").addClass("aaa");
	$(".pro span#2").removeClass("aaa");
	$(".pro span#3").removeClass("aaa");
	$(".pro span#5").removeClass("aaa");
	$.ajax({
		url: "content/credit", // 後端的 API 路徑
		method: "GET", // 或 "POST"，根據需求
		// 傳遞參數給後端
		success: function(response) {
			// 成功後更新 use_1 區塊
			$(".use_1").html(response);

		},
		error: function(xhr, status, error) {
			console.error("發生錯誤:", error);
			alert("無法更新內容，請稍後再試。");
		},
	});
});

$("span#2").on("click", function() {
	$(".proac").removeClass("aaa");
	$(".pro span#1").removeClass("aaa");
	$("span#2").addClass("aaa");
	$(".pro span#4").removeClass("aaa");
	$(".pro span#3").removeClass("aaa");
	$(".pro span#5").removeClass("aaa");
	$.ajax({
		url: "content/changeps", // 後端的 API 路徑
		method: "GET", // 或 "POST"，根據需求
		// 傳遞參數給後端
		success: function(response) {
			// 成功後更新 use_1 區塊
			$(".use_1").html(response);

		},
		error: function(xhr, status, error) {
			console.error("發生錯誤:", error);
			alert("無法更新內容，請稍後再試。");
		},
	});

});
$("span#5").on("click", function() {
	$(".proac").removeClass("aaa");
	$(".pro span#1").removeClass("aaa");
	$("span#5").addClass("aaa");
	$(".pro span#4").removeClass("aaa");
	$(".pro span#3").removeClass("aaa");
	$(".pro span#2").removeClass("aaa");
	$.ajax({
		url: "content/delete", // 後端的 API 路徑
		method: "GET", // 或 "POST"，根據需求
		// 傳遞參數給後端
		success: function(response) {
			// 成功後更新 use_1 區塊
			$(".use_1").html(response);

		},
		error: function(xhr, status, error) {
			console.error("發生錯誤:", error);
			alert("無法更新內容，請稍後再試。");
		},
	});
});

$("span#3").on("click", function() {
	$(".proac").removeClass("aaa");
	$(".pro span#1").removeClass("aaa");
	$("span#3").addClass("aaa");
	$(".pro span#2").removeClass("aaa");
	$(".pro span#4").removeClass("aaa");
	$(".pro span#5").removeClass("aaa");
	$.ajax({
		url: "content/add", // 後端的 API 路徑
		method: "GET", // 或 "POST"，根據需求
		// 傳遞參數給後端
		success: function(response) {
			// 成功後更新 use_1 區塊
			$(".use_1").html(response);

		},
		error: function(xhr, status, error) {
			console.error("發生錯誤:", error);
			alert("無法更新內容，請稍後再試。");
		},
	});
});

// 重新綁定密碼變更的表單驗證





//=====================二手商品管理事件====================================

$(document).ready(function() {
	// 当 span id 为 a1 的元素被点击时，触发 AJAX 事件
	$('#a1').click(function() {
		// 使用AJAX从后端以POST方式获取数据
		$.ajax({
			url: '/used/getSellerUsedListFragment', // 服务器端 API，返回 Thymeleaf 片段
			type: 'POST',
			success: function(response) {
				console.log("Fragment HTML:", response); // 打印返回的 HTML，便于调试

				// 使用 fragment 替换 <div class="use_1">
				$('.use_1').html(response);

				// 检查并销毁已有 DataTables 实例
				if ($.fn.DataTable.isDataTable('#UsedList')) {
					$('#UsedList').DataTable().destroy();
				}

				// 重新初始化 DataTables
				$('#UsedList').DataTable({
					"lengthMenu": [3, 5, 10, 20, 50, 100],
					"searching": true,
					"paging": true,
					"ordering": true,
					"language": {
						"processing": "處理中...",
						"loadingRecords": "載入中...",
						"lengthMenu": "顯示 _MENU_ 筆結果",
						"zeroRecords": "沒有符合的結果",
						"info": "顯示第 _START_ 至 _END_ 筆結果，共<font color=red> _TOTAL_ </font>筆",
						"infoEmpty": "顯示第 0 至 0 筆結果，共 0 筆",
						"infoFiltered": "(從 _MAX_ 筆結果中過濾)",
						"paginate": {
							"first": "第一頁",
							"previous": "上一頁",
							"next": "下一頁",
							"last": "最後一頁"
						}
					}
				});

				// 初始化 Slick 特效
				function initializeSlick() {
					$('.photo-slider').not('.slick-initialized').slick({
						dots: false,
						infinite: true,
						speed: 300,
						slidesToShow: 1,
						adaptiveHeight: true
					});
				}

				// 初次载入时初始化 Slick
				initializeSlick();

				// 监听 DataTables 分页切换事件，重新初始化 Slick
				$('#UsedList').on('draw.dt', function() {
					initializeSlick();
				});
			},
			error: function(xhr, status, error) {
				console.error('Error fetching used list data:', error);
			}
		});
	});
});

//====================二手商品刪除事件======================

document.addEventListener("DOMContentLoaded", function() {
	// 初始化外部 DataTable
	let dataTable;

	// 动态绑定嵌入 DataTable 的删除按钮事件
	document.body.addEventListener('click', function(event) {
		if (event.target.classList.contains('deleteUsed')) {
			const button = event.target;

			// 获取绑定的商品编号 (usedNo)
			const usedNo = button.getAttribute('data-usedNo');

			// 确认删除操作
			if (!confirm(`確定要刪除商品編號 ${usedNo} 嗎？`)) return;

			// 发送删除请求
			fetch(`/used/deleteUsed`, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify({ usedNo: usedNo }) // 将 usedNo 发送到后端
			})
				.then(response => response.json()) // 将响应解析为 JSON
				.then(data => {
					if (data.success) {
						alert(data.message); // 显示成功消息


						$('#a1').click();// 重新觸發一次click事件取得 fragment
					} else {
						alert(`刪除失敗：${data.message}`);
					}
				})
				.catch(error => {
					console.error('刪除過程中發生錯誤:', error);
					alert('刪除過程中發生錯誤');
				});
		}
	});

	// 当页面动态嵌入 DataTable 后，重新初始化 DataTable
	function initializeDataTable() {
		if (dataTable) {
			dataTable.destroy(); // 如果 DataTable 已初始化，先销毁
		}

		// 重新初始化 DataTable
		dataTable = $('#UsedList').DataTable({
			paging: true,
			searching: true,
			ordering: true
		});
	}

	// 假设触发嵌入 DataTable 的点击事件
	//    document.getElementById('loadDataTableButton').addEventListener('click', function () {
	//        // 模拟嵌入新表格（根据你的需求可能是 AJAX 或其他加载方式）
	//        // 这里假设 DataTable 已嵌入 DOM 中
	//
	//        // 初始化新的 DataTable
	//        initializeDataTable();
	//    });
});

////=========================二手訂單事件(我是買家)==============================================

$(document).ready(function() {
	$('#a2').click(function() {
		// 使用ajax從後端從POST方法獲取數據
		$.ajax({
			url: '/usedorder/getBuyerUsedOrderListFragment',
			type: 'POST',
			success: function(response) {
				console.log("Fragment HTML:", response); // 打印返回的 HTML，便于调试

				// 使用 fragment 替換 <div class="use_1">
				$('.use_1').html(response);

				// 檢查並銷毀已有 DataTables 實例
				if ($.fn.DataTable.isDataTable('#example')) {
					$('#example').DataTable().destroy();
				}

				// 重新初始化 DataTables
				$('#example').DataTable({
					"lengthMenu": [3, 5, 10, 20, 50, 100],
					"searching": true,
					"paging": true,
					"ordering": true,
					"language": {
						"processing": "處理中...",
						"loadingRecords": "載入中...",
						"lengthMenu": "顯示 _MENU_ 筆結果",
						"zeroRecords": "沒有符合的結果",
						"info": "顯示第 _START_ 至 _END_ 筆結果，共 _TOTAL_ 筆",
						"infoEmpty": "顯示第 0 至 0 筆結果，共 0 筆",
						"infoFiltered": "(從 _MAX_ 筆結果中過濾)",
						"paginate": {
							"first": "第一頁",
							"previous": "上一頁",
							"next": "下一頁",
							"last": "最後一頁"
						}
					}
				});
			},
			error: function(xhr, status, error) {
				console.error('Error fetching used list data:', error);
			}
		});
	});

	// 彈出模態框時加載滿意度值
	$(document).on('show.bs.modal', '#orderDetailModal', function() {
		var rating = $('#sellerSatisfication').val();
		$('#sellerSatisfication').val(rating);
	});
});

function saveComment() {
	var formData = new FormData();
	formData.append('usedOrderNo', $('#orderNo').text());
	formData.append('sellerCommentContent', $('#sellerCommentContent').val());
	formData.append('sellerSatisfication', $('#sellerSatisfication').val());

	console.log("orderNo = " + $('#orderNo').val());
	console.log("sellerCommentContent = " + $('#sellerCommentContent').val());
	console.log("sellerSatisfication = " + $('#sellerSatisfication').val());

	$.ajax({
		url: '/usedorder/updateFragComment',
		type: 'POST',
		data: formData,
		processData: false,
		contentType: false,
		success: function(response) {
			if (response.success) {
				alert('評論更新成功');
				var imageUrl = response.imageUrl; // 假設後端返回了更新後的圖片 URL
				$('#imgPreview').attr('src', imageUrl);
				$('#imgPreview').show();
				$('#orderDetailModal').modal('hide');
				location.reload();  // 刷新页面，更新顯示
			} else {
				alert('評論更新失敗: ' + response.error);
			}
		},
		error: function(xhr, status, error) {
			alert('評論更新过程中出現錯誤: ' + error);
		}
	});
}

function sendComplaintEmail() {
	var orderNo = $('#orderNo').text();
	var subject = $('#complaintSubject').val();
	var content = $('#complaintContent').val();

	var formData = new FormData();
	formData.append('orderNo', orderNo);
	formData.append('subject', subject);
	formData.append('content', content);

	$.ajax({
		url: '/usedorder/sendComplaintEmail',
		type: 'POST',
		data: formData,
		processData: false,
		contentType: false,
		success: function(response) {
			if (response.success) {
				alert('郵件發送成功');
			} else {
				alert('郵件發送失敗: ' + response.error);
			}
		},
		error: function(xhr, status, error) {
			alert('郵件發送過程中出現錯誤: ' + error);
		}
	});
}




//=========================二手訂單事件(我是賣家)==============================================


//當span id為a1的標籤被點擊時，觸發ajax事件
$('#a3').click(function() {
	// 使用ajax從後端POST方式獲取數據
	$.ajax({
		url: '/usedorder/getSellerUsedOrderListFragment', // 服务器端 API，返回 Thymeleaf 片段
		type: 'POST',
		success: function(response) {
			console.log("Fragment HTML:", response); // 打印返回的 HTML，便于调试

			// 使用 fragment 替換<div class="use_1">
			$('.use_1').html(response);

			// 檢查並銷毀 DataTables 實例
			if ($.fn.DataTable.isDataTable('#exampleSeller')) {
				$('#exampleSeller').DataTable().destroy();
			}

			// 重新初始化 DataTables
			$('#exampleSeller').DataTable({
				"lengthMenu": [3, 5, 10, 20, 50, 100],
				"searching": true,
				"paging": true,
				"ordering": true,
				"language": {
					"processing": "處理中...",
					"loadingRecords": "載入中...",
					"lengthMenu": "顯示 _MENU_ 筆結果",
					"zeroRecords": "沒有符合的結果",
					"info": "顯示第 _START_ 至 _END_ 筆結果，共<font color=red> _TOTAL_ </font>筆",
					"infoEmpty": "顯示第 0 至 0 筆結果，共 0 筆",
					"infoFiltered": "(從 _MAX_ 筆結果中過濾)",
					"paginate": {
						"first": "第一頁",
						"previous": "上一頁",
						"next": "下一頁",
						"last": "最後一頁"
					}
				}
			});

			// 監聽 DataTables 分頁切換事件，重新初始化 Slick
			$('#exampleSeller').on('draw.dt', function() {

			});
		},
		error: function(xhr, status, error) {
			console.error('Error fetching used list data:', error);
		}
	});
});



function updateDeliveryStatus(usedOrderNo) {
	var deliveryStatus = $("#deliveryStatus-" + usedOrderNo).val();

	$.ajax({
		url: "/usedorder/updateDeliveryStatus",
		type: "POST",
		data: {
			usedOrderNo: usedOrderNo,
			deliveryStatus: deliveryStatus
		},
		success: function(response) {
			if (response.success) {
				alert('更新成功');
				// 向 counterInform 表新增數據
				addCounterInform(usedOrderNo, "訂單狀態更新");
			} else {
				alert('更新失敗: ' + response.error);
			}
		},
		error: function(xhr, status, error) {
			alert('更新過程中出現失誤: ' + error);
		}
	});
}

function addNotice(usedOrderNo, message) {
	$.ajax({
		url: "/usedorder/addNotice",
		type: "POST",
		data: {
			usedOrderNo: usedOrderNo,
			message: message,
			buyerNo: buyerNo
		},
		success: function(response) {
			if (response.success) {
				console.log('通知新增成功');
			} else {
				console.log('通知新增失败: ' + response.error);
			}
		},
		error: function(xhr, status, error) {
			console.log('通知新增過程中出現錯誤: ' + error);
		}
	});
}

function showSellerOrderDetails(element) {
	var buyerNo = element.getAttribute('data-buyerNo');
	var orderNo = element.getAttribute('data-orderNo');
	var usedNo = element.getAttribute('data-usedNo');
	var usedPrice = element.getAttribute('data-usedPrice');
	var usedCount = element.getAttribute('data-usedCount');
	var usedTotalPrice = element.getAttribute('data-usedTotalPrice');
	var sellerSatisfication = element.getAttribute('data-sellerSatisfication');
	var sellerCommentContent = element.getAttribute('data-sellerCommentContent');
	var sellerCommentDate = element.getAttribute('data-sellerCommentDate');

	$('#orderDetailModal').find('#buyerNo').text(buyerNo);
	$('#orderDetailModal').find('#orderNo').text(orderNo);
	$('#orderDetailModal').find('#usedNo').text(usedNo);
	$('#orderDetailModal').find('#usedPrice').text(usedPrice);
	$('#orderDetailModal').find('#usedCount').text(usedCount);
	$('#orderDetailModal').find('#usedTotalPrice').text(usedTotalPrice);
	$('#orderDetailModal').find('#sellerCommentDate').text(sellerCommentDate);

	// 更新星星顯示
	$('#starRating i').each(function() {
		const value = $(this).data('value');
		if (value <= sellerSatisfication) {
			$(this).addClass('text-warning'); // 高亮
		} else {
			$(this).removeClass('text-warning'); // 灰色
		}
	});

	// 顯示評論文字
	$('#orderDetailModal').find('#sellerCommentContent').text(sellerCommentContent);

	// 顯示模態框
	$('#orderDetailModal').modal('show');
}



//==========================訂單蒐尋器==================
document.addEventListener('DOMContentLoaded', () => {
	const searchInput = document.getElementById('orderSearchInput'); // 搜索輸入框
	const orders = document.querySelectorAll('.order'); // 所有訂單的外層 div

	searchInput.addEventListener('input', function() {
		const filter = searchInput.value.toLowerCase(); // 將輸入的值轉為小寫
		orders.forEach(order => {
			// 獲取每個訂單內的文字內容
			const text = order.textContent.toLowerCase();

			// 判斷文字是否包含關鍵字，控制是否顯示
			if (text.includes(filter)) {
				order.style.display = ''; // 顯示匹配的訂單
			} else {
				order.style.display = 'none'; // 隱藏不匹配的訂單
			}
		});
	});
});
//==============================星形評分互動============================
document.addEventListener("DOMContentLoaded", () => {
	const maxStars = 5; // 最大星星數

	// 星形評分互動
	$(document).on('mouseenter', '#starRating i', function() {
		const hoverValue = $(this).data('value');
		$('#starRating i').each(function() {
			if ($(this).data('value') <= hoverValue) {
				$(this).addClass('hover');
			} else {
				$(this).removeClass('hover');
			}
		});
	});

	$(document).on('mouseleave', '#starRating i', function() {
		$('#starRating i').removeClass('hover');
	});

	$(document).on('click', '#starRating i', function() {
		const selectedValue = $(this).data('value');
		$('#sellerSatisfication').val(selectedValue); // 更新隱藏輸入框的值
		$('#starRating i').each(function() {
			if ($(this).data('value') <= selectedValue) {
				$(this).addClass('selected');
			} else {
				$(this).removeClass('selected');
			}
		});
	});


	// 顯示訂單詳細資訊
	window.showReceiverOrderDetails = function(element) {
		const orderNo = element.getAttribute('data-orderNo');
		const usedNo = element.getAttribute('data-usedNo');
		const usedName = element.getAttribute('data-usedName');
		const usedPrice = element.getAttribute('data-usedPrice');
		const usedCount = element.getAttribute('data-usedCount');
		const usedTotalPrice = element.getAttribute('data-usedTotalPrice');
		const receiverName = element.getAttribute('data-receiverName');
		const receiverPhone = element.getAttribute('data-receiverPhone');
		const receiverAdr = element.getAttribute('data-receiverAdr');
		const sellerSatisfication = element.getAttribute('data-sellerSatisfication');
		const sellerCommentContent = element.getAttribute('data-sellerCommentContent');
		const sellerCommentDate = element.getAttribute('data-sellerCommentDate');

		$('#orderDetailModal').find('#orderNo').text(orderNo);
		$('#orderDetailModal').find('#usedNo').text(usedNo);
		$('#orderDetailModal').find('#usedPrice').text(usedPrice);
		$('#orderDetailModal').find('#usedCount').text(usedCount);
		$('#orderDetailModal').find('#usedTotalPrice').text(usedTotalPrice);
		$('#orderDetailModal').find('#receiverName').text(receiverName);
		$('#orderDetailModal').find('#receiverPhone').text(receiverPhone);
		$('#orderDetailModal').find('#receiverAdr').text(receiverAdr);
		$('#orderDetailModal').find('#sellerSatisfication').val(sellerSatisfication);
		$('#orderDetailModal').find('#sellerCommentContent').text(sellerCommentContent);
		$('#orderDetailModal').find('#sellerCommentDate').text(sellerCommentDate);

		$('#orderDetailModal').find('.modal-footer').html(
			'<button type="button" class="btn btn-primary" onclick="saveComment(' + orderNo + ')">保存</button>' +
			'<button type="button" class="btn btn-secondary" data-dismiss="modal">關閉</button>'
		);

		// 設置星形評分
		$('#starRating i').removeClass('selected');
		$('#starRating i').each(function() {
			if ($(this).data('value') <= sellerSatisfication) {
				$(this).addClass('selected');
			}
		});

		$('#sellerSatisfication').val(sellerSatisfication);
		$('#orderDetailModal').modal('show');
	};

	// 保存評論
	function saveComment() {
		var formData = new FormData();
		formData.append('usedOrderNo', $('#orderNo').text());
		formData.append('sellerCommentContent', $('#sellerCommentContent').val());
		formData.append('sellerSatisfication', $('#sellerSatisfication').val());

		console.log("orderNo = " + $('#orderNo').val());
		console.log("sellerCommentContent = " + $('#sellerCommentContent').val());
		console.log("sellerSatisfication = " + $('#sellerSatisfication').val());

		$.ajax({
			url: '/usedorder/updateFragComment',
			type: 'POST',
			data: formData,
			processData: false,
			contentType: false,
			success: function(response) {
				if (response.success) {
					alert('評論更新成功');
					var imageUrl = response.imageUrl; // 假設後端返回了更新後的圖片 URL
					$('#imgPreview').attr('src', imageUrl);
					$('#imgPreview').show();
					$('#orderDetailModal').modal('hide');
					location.reload();  // 刷新页面，更新顯示
				} else {
					alert('評論更新失敗: ' + response.error);
				}
			},
			error: function(xhr, status, error) {
				alert('評論更新过程中出現錯誤: ' + error);
			}
		});
	}

	// 發送投訴郵件
	window.sendComplaintEmail = function() {
		const orderNo = $('#orderNo').text();
		const subject = $('#complaintSubject').val();
		const content = $('#complaintContent').val();

		const formData = new FormData();
		formData.append('orderNo', orderNo);
		formData.append('subject', subject);
		formData.append('content', content);

		$.ajax({
			url: '/usedorder/sendComplaintEmail',
			type: 'POST',
			data: formData,
			processData: false,
			contentType: false,
			success: function(response) {
				if (response.success) {
					alert('郵件發送成功');
				} else {
					alert('郵件發送失敗: ' + response.error);
				}
			},
			error: function(xhr, status, error) {
				alert('郵件發送過程中出現錯誤: ' + error);
			}
		});
	};

	// 渲染星形評分
	function renderStars(element, rating) {
		let starsHtml = '';
		for (let i = 1; i <= maxStars; i++) {
			if (i <= rating) {
				starsHtml += '<i class="fas fa-star" style="color: gold;"></i>'; // 實心星星
			} else {
				starsHtml += '<i class="far fa-star" style="color: gold;"></i>'; // 空心星星
			}
		}
		element.innerHTML = starsHtml; // 清空並重新設置 HTML
	}

	$(document).on('mouseenter', '.star-display', function() {
		const rating = $(this).data('rating');
		renderStars(this, rating);
	});

	// 動態生成的內容重新初始化
	$(document).ajaxComplete(function() {
		$('.star-display').each(function() {
			const rating = $(this).data('rating');
			renderStars(this, rating);
		});
	});
});
//=======================追蹤商品===================
// 当 class 为 goodsfollow 的 div 被点击时，触发 AJAX 请求
$('.goodsfollow').click(function() {
	// 使用 AJAX 从后端以 POST 方式获取数据
	$.ajax({
		url: '/goodsTrack/getFavoritesfragment', // 服务器端 API，返回 Thymeleaf 片段
		type: 'GET',
		success: function(response) {
			// 使用 fragment 替换 <div class="use_1">
			$('.use_1').html(response);

		},
		error: function(xhr, status, error) {
			console.error('Error fetching favorites fragment:', error);
		}
	});
});


/* ===============商品收藏移除============= */


function removeGoods(element) {
	// 获取商品编号
	const goodsNo = element.getAttribute('data-goodsNo');

	if (!goodsNo) {
		alert('無法移除，商品不存在！');
		return;
	}

	fetch('/goodsTrack/remove', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify({ goodsNo })
	})
		.then(response => response.json())
		.then(data => {
			if (data.success === "true") {
				alert(data.message);
				element.closest('div').remove();
			} else {
				alert(data.message);
			}
		})
		.catch(error => {
			console.error('移除失敗:', error);
			alert('移除失敗，請稍後再試！');
		});
}

/* ===============商品收藏移除============= */

//=======================我的優惠券===================
$(".coupon").on("click", function() {
	$.ajax({
		url: "/memcoupon/listFragment",
		method: "GET",
		success: function(response) {
			// 插入返回的 HTML 內容
			$(".use_1").html(response);

			// 初始化分頁功能
			initPagination();
		},
		error: function(xhr, status, error) {
			console.error("Error loading coupons:", xhr.responseText);
			alert("無法載入優惠券，錯誤: " + error);
		},
	});
});

// 分頁功能初始化函數
function initPagination() {
	const itemsPerPage = 5;
	const items = document.querySelectorAll('.coupon_49_item');
	const totalPages = Math.ceil(items.length / itemsPerPage);
	const pagination = document.getElementById('coupon_49_pagination');
	let currentPage = 1;

	// 重置分頁容器
	if (pagination) {
		pagination.innerHTML = '';
	}

	function generatePagination() {
		const prevButton = document.createElement('button');
		prevButton.innerHTML = '&laquo;';
		prevButton.onclick = () => showPage(currentPage - 1);
		pagination.appendChild(prevButton);

		for (let i = 1; i <= totalPages; i++) {
			const button = document.createElement('button');
			button.textContent = i;
			button.onclick = () => showPage(i);
			pagination.appendChild(button);
		}

		const nextButton = document.createElement('button');
		nextButton.innerHTML = '&raquo;';
		nextButton.onclick = () => showPage(currentPage + 1);
		pagination.appendChild(nextButton);
	}

	function showPage(pageNum) {
		if (pageNum < 1 || pageNum > totalPages) return;

		currentPage = pageNum;
		const start = (pageNum - 1) * itemsPerPage;
		const end = start + itemsPerPage;

		items.forEach(item => item.style.display = 'none');

		for (let i = start; i < end && i < items.length; i++) {
			items[i].style.display = 'block';
		}

		updatePaginationButtons();
	}

	function updatePaginationButtons() {
		const buttons = pagination.querySelectorAll('button');
		buttons.forEach((button, index) => {
			if (index === 0) {
				button.disabled = currentPage === 1;
			} else if (index === buttons.length - 1) {
				button.disabled = currentPage === totalPages;
			} else {
				button.classList.toggle('coupon_49_active', index === currentPage);
			}
		});
	}

	// 如果有項目才初始化分頁
	if (items.length > 0) {
		generatePagination();
		showPage(1);
	}
}

// 頁面載入時初始化（如果已經有優惠券內容的話）
$(document).ready(function() {
	const items = document.querySelectorAll('.coupon_49_item');
	if (items.length > 0) {
		initPagination();
	}
});

//=========================通知總覽===========================
$(document).ready(function() {
	// 點擊通知標題加載通知列表
	$('.notice.title').click(function() {
		$.ajax({
			url: '/notice/listAllNoticeFragment', // 服务器端 API，返回 Thymeleaf 片段
			type: 'POST',
			success: function(response) {
				console.log("Fragment HTML:", response); // 打印返回的 HTML，便于调试

				// 使用 fragment 替换 <div class="use_1">
				$('.use_1').html(response);

				// 檢查並銷毀已有 DataTables 實例
				if ($.fn.DataTable.isDataTable('#notice')) {
					$('#notice').DataTable().destroy();
				}

				// 初始化 DataTables
				var table = $('#notice').DataTable({
					"lengthMenu": [10, 20, 50, 100],
					"searching": true,
					"paging": true,
					"ordering": true,
					"language": {
						"processing": "處理中...",
						"loadingRecords": "載入中...",
						"lengthMenu": "顯示 _MENU_ 筆結果",
						"zeroRecords": "沒有符合的結果",
						"info": "顯示第 _START_ 至 _END_ 筆結果，共<font color=red> _TOTAL_ </font>筆",
						"infoEmpty": "顯示第 0 至 0 筆結果，共 0 筆",
						"infoFiltered": "(從 _MAX_ 筆結果中過濾)",
						"search": "搜尋:",
						"paginate": {
							"first": "第一頁",
							"previous": "上一頁",
							"next": "下一頁",
							"last": "最後一頁"
						},
						"aria": {
							"sortAscending": ": 升冪排列",
							"sortDescending": ": 降冪排列"
						}
					},
					"drawCallback": function(settings) {
						$('#notice tbody tr').each(function() {
							var noticeRead = $(this).find('td').eq(2).text(); // 確認 noticeRead 是在第幾個 td
							if (noticeRead == '1') {
								$(this).css('background-color', '#dff1f7');
							}
						});
					}
				});

				// 在lengthMenu旁新增清空通知和標記所有為已讀按鈕
				$('#notice_length').append('<button id="clearAllBtn" class="btn btn-danger ml-2">清空通知</button>');
				$('#notice_length').append('<button id="markAllReadBtn" class="btn btn-primary ml-2">標記所有為已讀</button>');

				// 清空通知按鈕事件
				$('#clearAllBtn').on('click', function() {
					if (confirm('確定要清空所有通知嗎？')) {
						$.post('/notice/clearAll', function(response) {
							// 清空通知列表
							$('#notice tbody').empty();
						});
					}
				});

				// 標記所有為已讀按鈕事件
				$('#markAllReadBtn').on('click', function() {
					$.post('/notice/markAllRead', function(response) {
						// 即時變更所有通知行的背景顏色為淺藍色
						$('#notice tbody tr').each(function() {
							$(this).css('background-color', '#dff1f7');
						});
					});
				});


			},
			error: function(jqXHR, textStatus, errorThrown) {
				console.log("AJAX request failed: " + textStatus + ', ' + errorThrown);
			}
		});
	});
});

// ================================== 實現圖片前進和後退小圖片的效果 ==================================
let prev = document.querySelector('.prev');
let next = document.querySelector('.next');
let ul   = document.querySelector('.spec-items ul');
let lis  = document.querySelectorAll('.spec-items ul li');

// 設定每次移動的距離（每次顯示一張圖片）
let imgWidth = lis[0].offsetWidth + 12.2; // 每個小圖片的寬度加上邊距

// 顯示當前圖片的索引
let currentIndex = 0;

// 左右箭頭點擊事件
prev.onclick = function() {
  if (currentIndex > 0) {
    currentIndex--; // 往回退一步
    let newLeft = -(currentIndex * imgWidth); // 計算新的左邊距
    ul.style.left = `${newLeft}px`; // 移動圖片
  }
};

next.onclick = function() {
  if (currentIndex < lis.length - 1) {
    currentIndex++; // 往前進一步
    let newLeft = -(currentIndex * imgWidth); // 計算新的左邊距
    ul.style.left = `${newLeft}px`; // 移動圖片
  }
};

// ================================== 實現顯示中圖的效果 ==================================
let img = document.querySelector('.main-img-img');
let imgs = document.querySelectorAll('.spec-items img');
let zoomDivImg = document.querySelector('.zoom-div img');  // 放大鏡大圖

// 左邊下方圖片區域-圖片區 圖片選取邊框設定
for (let i = 0; i < lis.length; i++) {
  lis[i].onmouseover = function () {
    // 先清除所有圖片選擇的邊框
    for (let j = 0; j < lis.length; j++) {
      lis[j].className = '';
    }
    // 加上當前圖片的選擇樣式
    lis[i].className = 'img-hover';

    // 更新主圖像
    img.src = imgs[i].src;

    // 更新放大鏡圖像
    zoomDivImg.src = imgs[i].src;  // 將放大鏡顯示的大圖更新為當前圖片
  }
}
// ================================== 實現放大鏡效果 ==================================
let mainImg = document.querySelector('.main-img');
let zoomPup = document.querySelector('.zoom-pup');
let zoomDiv = document.querySelector('.zoom-div');
let bigImg = document.querySelector('.zoom-div img');
mainImg.onmouseover = function(){
  zoomPup.style.display = 'block';
  zoomDiv.style.display = 'block';
}
mainImg.onmouseout = function(){
  zoomPup.style.display = 'none';
  zoomDiv.style.display = 'none';
}

mainImg.onmousemove = function(e){
  // 獲取滑鼠距離文檔上方的距離
  let pageY = e.pageY;
  // 獲取滑鼠距離文檔左側的距離
  let pageX = e.pageX;

  // 獲取圖片容器距離文檔頂部的距離
  let offsetTop = mainImg.offsetTop;
  // 獲取圖片容器距離文檔左側的距離
  let offsetLeft = mainImg.offsetLeft;

  // 獲取放大鏡區塊的高度
  let h = zoomPup.clientHeight / 1;
  // 獲取放大鏡區塊的寬度
  let w = zoomPup.clientHeight / 2;

  // 計算放大鏡的位置，讓它根據滑鼠在圖片中的位置移動
  let top = pageY - offsetTop - h;
  let left = pageX - offsetLeft - w;
  if(top <= 0){ 
      top = 0;
  }else if(top >= mainImg.clientHeight - zoomPup.clientHeight){
      top = mainImg.clientHeight - zoomPup.clientHeight;
  }
  if(left <= 0){
      left = 0;
  }else if(left >= mainImg.clientWidth - zoomPup.clientWidth){
      left = mainImg.clientWidth - zoomPup.clientWidth;
  }

  // 設定放大鏡的位置
  zoomPup.style.top = top + 'px';
  zoomPup.style.left = left + 'px';

  let y = top / (mainImg.clientHeight - zoomPup.clientHeight);

  let yy = y * (1200 - 540);

  bigImg.style.top = -yy +'px';

  let x = left / (mainImg.clientWidth - zoomPup.clientWidth);

  let xx = x * (1200 - 540);

  bigImg.style.left = -xx +'px';
}

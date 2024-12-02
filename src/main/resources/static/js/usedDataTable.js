$(document).ready(function () {
    // 初始化 DataTables
    var table = $('#UsedList').DataTable({
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

    // 初始化 Slick
    function initializeSlick() {
        $('.photo-slider').not('.slick-initialized').slick({
            dots: false,
            infinite: true,
            speed: 300,
            slidesToShow: 1,
            adaptiveHeight: true
        });
    }

    // 初次載入時初始化 Slick
    initializeSlick();

    // 監聽 DataTables 分頁切換事件
    $('#UsedList').on('draw.dt', function () {
        initializeSlick(); // 在每次重新繪製表格時初始化 Slick
    });
});

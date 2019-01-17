$(document).ready(function () {

    $('.btn-delete').on('click', function () {
        var $parentTr = $(this).closest('tr');
        var productId = $parentTr.attr('product-id');
        $.ajax({
            url: '/product/' + productId,
            type: 'DELETE',
            success: function (result) {
                location.reload();
            },
            error: function () {
                alert('cos poszlo nie tak');
            }
        })
    });

    $('.btn-usun').on('click', function () {
        var $parentTr = $(this).closest('tr');
        var orderId = $parentTr.attr('order-id')
        $.ajax({
            url: window.location.href + "/" + orderId,
            type: 'DELETE',
            success: function (result) {
                location.reload();
            },
            error: function () {
                alert('cos nie pykło')
            }
        })

    });

});
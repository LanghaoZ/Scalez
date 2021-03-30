$(function() {
   getData();
});

function getQueryParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }

    return null;
}

function getData() {
    var productId = getQueryParam("productId");
    $.ajax({
        url: "/product/detail/data/" + productId,
        type: "GET",
        success: function(data) {
            if (data.code == 1200) {
                renderPage(data.data);
            } else {
                console.log(data);
                layer.msg(data.message);
            }
        },
        error: function() {
            layer.msg("Request to backend failed");
        }
    })
}

function renderPage(product) {
    if (product == null) {
        layer.msg("Specified product does not exist!")
    }
    $("#productName").text(product.name);
    $("#productPrice").text(product.price);
    $("#productCount").text(product.count);
    $("#productId").val(product.id);
}

function performBuyAction() {
    $.ajax({
        url: "/sell/v2",
        type: "POST",
        data: {
            productId: $("#productId").val()
        },
        success: function(data) {
            if (data.code == 1200) {
                window.location.href = "/webpage/orderDetail.htm?orderId=" + data.data.id;
            } else {
                console.log(data);
                layer.msg(data.message);
            }
        },
        error: function() {
            layer.msg("Request to backend failed");
        }
    })
}
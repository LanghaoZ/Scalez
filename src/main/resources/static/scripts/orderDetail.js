
$(function() {
    getData();
})

function getData() {
    var orderId = getQueryParam("orderId");
    $.ajax({
        url: "/order/detail",
        type: "GET",
        data: {
            orderId: orderId
        },
        success: function(data) {
            if (data.code == 1200) {
                console.log(data);
                renderOrderPage(data.data);
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

function getQueryParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }

    return null;
}

function renderOrderPage(data) {
    var product = data.product;
    var order = data.orderDetail;
    $("#productName").text(product.name);
    $("#productPrice").text(product.price);
    $("#createdTime").text(order.createTime);

    var status = "";
    if (order.status == 0) {
        status = "pending payment";
    } else if (order.status == 1) {
        status = "pending shipment";
    } else if (order.status == 2) {
        status = "pending delivery";
    } else {
        status = "completed";
    }

    $("#orderStatus").text(status);

}
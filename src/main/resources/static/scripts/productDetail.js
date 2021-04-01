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
        url: "/sell/v3",
        type: "POST",
        data: {
            productId: $("#productId").val()
        },
        success: function(data) {
            if (data.code == 1200) {
                retrieveResult(data.data);
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

function retrieveResult(uuid) {
    displayLoadingPage();
    $.ajax({
        url: "/sell/result",
        type: "GET",
        data: {
            id: uuid
        },
        success: function(data) {
            if (data.code == 1200) {
                layer.msg("Congrats! Your order was successful");
                window.location.href="/webpage/orderDetail.htm?orderId=" + data.data.id;
            } else if (data.code == 1450) {
                setTimeout(function(){
                    retrieveResult(uuid);
                }, 50);
            } else if (data.code == 1460) {
                layer.msg("Sorry. Your order was unsuccessful.");
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

function displayLoadingPage(){
    var idx = layer.msg('Processing...', {icon: 16,shade: [0.5, '#f5f5f5'],scrollbar: false,offset: '0px', time:100000}) ;
    return idx;
}
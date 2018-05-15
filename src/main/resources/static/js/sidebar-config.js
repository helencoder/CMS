/**
 * Created by zhenghailun on 2018/5/4.
 */

$(function () {
    var userId = $("#txt_userId").val();
    $('#menu').sidebarMenu({
        data: [{
            id: '1',
            text: '内容管理',
            icon: 'icon-home',
            url: '/contentList'
        }, {
            id: '2',
            text: '用户管理',
            icon: 'icon-user',
            url: '/userList?param=' + userId
        }, {
            id: '3',
            text: '摘要管理',
            icon: 'icon-cog',
            url: '/abstractList'
        }, {
            id: '4',
            text: '系统管理',
            icon: 'icon-star',
            url: 'http://127.0.0.1:8080/'
        }]
    });

    $("#menu-toggler").click(function () {
        var children = $("#sidebar-collapse").children("i");
        if ($(children).hasClass("icon-double-angle-left")) {
            $(children).removeClass("icon-double-angle-left").addClass("icon-double-angle-right");
            $("#sidebar").attr("class", "sidebar menu-min display");
        }
        else {
            $(children).removeClass("icon-double-angle-right").addClass("icon-double-angle-left");
            $("#sidebar").attr("class", "sidebar display");
        }
    });
});
/**
 * Created by zhenghailun on 2018/5/15.
 */
/**
 * Created by zhenghailun on 2018/5/4.
 */
$(function () {
    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();

    // 关键字高亮显示
    $('#tb_abstract').on('load-success.bs.table',function(data){
        console.log("load success");
        highlight();
    });

});


var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#tb_abstract').bootstrapTable({
            url: '/getAbstractList',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: false,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: false,
            showColumns: false,                  //是否显示所有的列
            showRefresh: false,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
            showToggle: false,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            columns: [{
                checkbox: true
            }, {
                field: 'id',
                title: 'ID'
            }, {
                field: 'content',
                title: '正文'
            }, {
                field: 'abs',
                title: '摘要'
            }, {
                field: 'result',
                title: '结果'
            }, {
                field: 'similarity',
                title: '相似度'
            }, {
                field: 'flag',
                title: '标志位'
            }, {
                field: 'createTime',
                title: '创建时间',
                // format: 'yyyy-MM-dd HH:mm:ss'
            }, {
                field: 'updateTime',
                title: '更新时间',
                // formatter: function (value) {
                //     return changeDateFormat(value)
                // }
            }]
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,   //页面大小
            offset: params.offset,  //页码
            content: $("#txt_search_content").val(),  // 姓名
            flag: $("#txt_search_flag").val()    // 手机号
        };
        return temp;
    };
    return oTableInit;
};
toastr.options =
    {
        "closeButton": false,//显示关闭按钮
        "debug": false,//启用debug
        "positionClass": "toast-top-right",//弹出的位置
        "showDuration": "300",//显示的时间
        "hideDuration": "1000",//消失的时间
        "timeOut": "3000",//停留的时间
        "extendedTimeOut": "1000",//控制时间
        "showEasing": "swing",//显示时的动画缓冲方式
        "hideEasing": "linear",//消失时的动画缓冲方式
        "showMethod": "fadeIn",//显示时的动画方式
        "hideMethod": "fadeOut"//消失时的动画方式
    };

var ButtonInit = function () {
    var oInit = new Object();
    var postdata = {};

    oInit.Init = function () {
        $("#btn_add").click(function () {
            $("#myModalLabel").text("新增");
            $("#myModal").find(".form-control").val("");
            $('#myModal').modal();
            postdata.id = "";
        });

        $("#btn_edit").click(function () {
            var arrselections = $("#tb_abstract").bootstrapTable('getSelections');
            if (arrselections.length > 1) {
                toastr.warning('只能选择一行进行编辑');
                return;
            }
            if (arrselections.length <= 0) {
                toastr.warning('请选择有效数据');
                return;
            }
            $("#myModalLabel").text("编辑");
            $("#txt_content").val(arrselections[0].content);
            $("#txt_abs").val(arrselections[0].abs);
            $("#txt_result").val(arrselections[0].result);
            $("#txt_similarity").val(arrselections[0].similarity);
            $("#txt_flag").val(arrselections[0].flag);

            postdata.id = arrselections[0].id;
            $('#myModal').modal();
        });

        $("#btn_delete").click(function () {
            var arrselections = $("#tb_abstract").bootstrapTable('getSelections');
            if (arrselections.length <= 0) {
                toastr.warning('请选择有效数据');
                return;
            }
            Ewin.confirm({message: "确认要删除选择的数据吗？"}).on(function (e) {
                if (!e) {
                    return;
                }
                $.ajax({
                    type: "post",
                    url: "/deleteAbstract",
                    data: {"param":JSON.stringify(arrselections)},
                    dataType: "text",
                    success: function (data, status) {
                        if (status == "success" && data == "success") {
                            toastr.success('提交数据成功');
                            $("#tb_abstract").bootstrapTable('refresh');
                        }
                    },
                    error: function () {
                        toastr.error('Error');
                    },
                    complete: function () {

                    }

                });
            });
        });

        $("#btn_submit").click(function () {
            postdata.result = $("#txt_result").val();
            postdata.similarity = $("#txt_similarity").val();
            postdata.flag = $("#txt_flag").val();
            $.ajax({
                type: "post",
                url: "/updateAbstract",
                data: {"param": JSON.stringify(postdata)},
                dataType: "text",
                success: function (data, status) {
                    if (status == "success" && data == "success") {
                        toastr.success('提交数据成功');
                        $("#tb_abstract").bootstrapTable('refresh');
                    }
                },
                error: function () {
                    toastr.error('Error');
                },
                complete: function () {

                }

            });
        });

        $("#btn_query").click(function () {
            $("#tb_abstract").bootstrapTable('refresh', {
                onLoadSuccess: highlight()
            });
        });
    };

    return oInit;
};


function highlight() {
    var keyword = $("#txt_search_content").val();
    var reg = new RegExp(keyword, 'ig');
    var tableId = document.getElementById("tb_abstract");
    if (keyword !== null && keyword !== undefined && keyword.length !== 0) {
        for (var i = 1; i < tableId.rows.length; i++) {
            var bb = tableId.rows[i].cells[2].innerHTML;
            if (bb.indexOf(keyword) >= 0) {
                var displayColor = '<span style="background-color:#FFFF33">' + keyword + '</span>';
                var cc = bb.replace(reg, displayColor);
                tableId.rows[i].cells[2].innerHTML = cc;
            }
        }
    }
}


//转换日期格式(时间戳转换为datetime格式)
function changeDateFormat(cellval) {
    var dateVal = cellval + "";
    alert("原始数据为: " + dateVal);
    if (cellval != null) {
        var date = new Date(parseInt(dateVal.replace("/Date(", "").replace(")/", ""), 10));
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();

        var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();

        return date.getFullYear() + "-" + month + "-" + currentDate + " " + hours + ":" + minutes + ":" + seconds;
    }
}
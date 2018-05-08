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

});

var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#tb_content').bootstrapTable({
            url: '/getContentList',         //请求后台的URL（*）
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
                field: 'data',
                title: '内容'
            }, {
                field: 'flag',
                title: '标志位'
            }, {
                field: 'label',
                title: '标签'
            }, {
                field: 'remark',
                title: '备注'
            }, {
                field: 'createTime',
                title: '创建时间'
            }, {
                field: 'updateTime',
                title: '更新时间'
            }]
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,   //页面大小
            offset: params.offset,  //页码
            data: $("#txt_search_data").val(),
            flag: $("#txt_search_flag").val(),
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
            var arrselections = $("#tb_content").bootstrapTable('getSelections');
            if (arrselections.length > 1) {
                toastr.warning('只能选择一行进行编辑');
                return;
            }
            if (arrselections.length <= 0) {
                toastr.warning('请选择有效数据');
                return;
            }
            $("#myModalLabel").text("编辑");
            $("#txt_ystid").val(arrselections[0].ystid);
            $("#txt_name").val(arrselections[0].name);
            $("#txt_department").val(arrselections[0].department);
            $("#txt_tablename").val(arrselections[0].tablename);
            $("#txt_remark").val(arrselections[0].remark);

            postdata.id = arrselections[0].id;
            $('#myModal').modal();
        });

        $("#btn_delete").click(function () {
            var arrselections = $("#tb_content").bootstrapTable('getSelections');
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
                    url: "/deleteContent",
                    data: {"param":JSON.stringify(arrselections)},
                    dataType: "text",
                    success: function (data, status) {
                        if (status == "success" && data == "success") {
                            toastr.success('提交数据成功');
                            $("#tb_content").bootstrapTable('refresh');
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
            postdata.ystid = $("#txt_ystid").val();
            postdata.name = $("#txt_name").val();
            postdata.tablename = $("#txt_tablename").val();
            postdata.department = $("#txt_department").val();
            postdata.remark = $("#txt_remark").val();
            $.ajax({
                type: "post",
                url: "/updateContent",
                data: {"param": JSON.stringify(postdata)},
                dataType: "text",
                success: function (data, status) {
                    if (status == "success" && data == "success") {
                        toastr.success('提交数据成功');
                        $("#tb_content").bootstrapTable('refresh');
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
            $("#tb_content").bootstrapTable('refresh');
        });
    };

    return oInit;
};
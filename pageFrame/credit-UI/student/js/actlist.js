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
	        
	    	var location = (window.location+'').split('/');
	    	var basePath = "http://" + location[2] + "/" + location[3];
	    	
	    	$('#table').bootstrapTable({
	            url: basePath + '/ids/ActList',    //请求后台的URL（*）
	            method: 'get',                      //请求方式（*）
	         
	            striped: true,                      //是否显示行间隔色
	            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
	            pagination: true,                   //是否显示分页（*）
	            sortable: false,                     //是否启用排序
	            sortOrder: "asc",                   //排序方式
	            queryParams: oTableInit.queryParams,//传递参数（*） 
	            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
	            pageNumber:1,                       //初始化加载第一页，默认第一页
	            pageSize: 3,                       //每页的记录行数（*）
	            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
	            search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
	            strictSearch: false,
	            showColumns: false,                  //是否显示所有的列
	            showRefresh: false,                  //是否显示刷新按钮
	            minimumCountColumns: 2,             //最少允许的列数
	            clickToSelect: true,                //是否启用点击选中行  
	            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
	            showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
	            cardView: false,                    //是否显示详细视图
	            detailView: false,                   //是否显示父子表
	            showHeader:false,					//是否显示标题
	            columns: [{
	                field: 'actName',
	                title: '活动名称',
	            },{
	    	        field: 'operation',
	    	        title: '',
	    	        formatter:function(value,row,index){
	    	            var s = '<a href="javascript:;" class="weui-btn weui-btn_primary detail">查看详情</a';
	    	            return s;
	    	        },
	    	        events: 'operateEvents'
	    	    }]
	        });
	    };
	
	    window.operateEvents = {
	            'click .detail': function(e, value, row, index) {      
	            	var location = (window.location+'').split('/');
	    	    	var basePath = "http://" + location[2] + "/" + location[3];
	            	$.ajax({
	    	            type: "post",
	    	            data: JSON.stringify(row),
	    	            dataType:'json',
	    	            contentType:"application/json",
	    	            url: basePath + '/user/delete',
	    	            success: function (result) {
	    	            	layer.alert(result.message, {icon: 2});
	    	                $('#UserTable').bootstrapTable('remove', {
	    	                    field: 'id',
	    	                    values: [row.id]
	    	                });
	    	            }
	    	        }); 
	             }
	     }
	    
	    //得到查询的参数
	    oTableInit.queryParams = function (params) {
	        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
	            limit: params.limit,   //页面大小
	            offset: params.offset 	//页码
	        };
	        return temp;
	    };
	    return oTableInit;
	};


	var ButtonInit = function () {
	    var oInit = new Object();
	    var postdata = {};

	    oInit.Init = function () {
	        //初始化页面上面的按钮事件
	    };

	    return oInit;
	};
	
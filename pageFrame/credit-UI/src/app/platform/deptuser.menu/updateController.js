'use strict';

//修改用户权限
angular.module('app')
    .controller('deptUserMenuUpdateController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster',
                                         function($scope, $rootScope, $state, $timeout, toaster) {
    var title = "编辑用户权限";
    var id = $state.params.id;
    activate(id);
    $scope.title = $rootScope.title = title;
    $scope.loading = true;
    $('form').submit(function() {
        var m = $scope.record;
        $scope.isDisabled = true;//提交disabled
        var permissionInfo = "";
        var menuCheckbox_num = $(".menuCheckbox:checked").length;
        $(".menuCheckbox:checked").each(function(menuIndex,dom){
        	var thisMenuId = $(dom).val();
        	var thisTrDocument = $(dom).parent().parent();
        	permissionInfo = permissionInfo+thisMenuId+":";
        	var permissionCheckbox_num = $(thisTrDocument).find(".permissionCheckbox:checked").length;
        	$(thisTrDocument).find(".permissionCheckbox:checked").each(function(index,obj){
    			if(index==permissionCheckbox_num-1){
    				permissionInfo = permissionInfo+$(obj).val()+"|";
    			}else{
    				permissionInfo = permissionInfo+$(obj).val()+",";
    			}
        	});
        	if(menuIndex==menuCheckbox_num-1){
				permissionInfo = permissionInfo.substring(0, permissionInfo.length-1);
			}
        });
        $.ajax({
			url : '/user/menu/update',
			data: {'id':id,'permissionInfo': permissionInfo}
		}).then(callback);
        function callback(result) {
            if(result.httpCode ==200){//成功
                toaster.clear('*');
                toaster.pop('success', '', "保存成功");
                $timeout(function(){
                    $state.go('main.platform.deptuser.list');
                },2000);
            }else{
                toaster.clear('*');
                toaster.pop('error', '', result.msg);
                $scope.isDisabled = false;
            }
        }
    });

    // 初始化页面
    function activate(id) {
        $scope.loading = true;
    	$.ajax({
			url : '/user/menu/read/detail',
			data: {'id': id}
		}).then(function(result) {
	        $scope.loading = false;
			if (result.httpCode == 200) {
				$scope.record = result.data;
			} else {
				$scope.msg = result.msg;
			}
			$scope.$apply();
			//加载函数
			init_menuCheckboxChange();
		});
    }
    
    function init_menuCheckboxChange(){
    	$(".menuCheckbox").change(function() {
    		//菜单ID
    		var sysMenuId = $(this).val();
    		if($(this).is(':checked')){
    			$(this).parent().parent().find(".permissionCheckbox").each(function(){
    				if($(this).attr("disabled")!="disabled"){
    					$(this).prop("checked",true);
    				}
    			});
    		}else{
    			$(this).parent().parent().find(".permissionCheckbox").each(function(){
    				if($(this).attr("disabled")!="disabled"){
    					$(this).prop("checked",false);
    				}
    			});
    		}
    	});
    	
    	
    	$(".permissionCheckbox").change(function() {
    		if($(this).is(':checked')){
    			//只要选中一个，菜单checkbox勾上
    			$(this).parent().parent().find(".menuCheckbox").prop("checked",true);
    		}else{
    			//判断是否这一行的都没选中了
    			var permissionCheckboxArray = $(this).parent().parent().find(".permissionCheckbox");
    			var allUnChecked = true;
    			$(this).parent().parent().find(".permissionCheckbox").each(function(){
    				if($(this).is(':checked')){
    					allUnChecked = false;
    				}
    			});
    			if(allUnChecked){
    				$(this).parent().parent().find(".menuCheckbox").prop("checked",false);
    			}
    		}
    	});
    }
}]);
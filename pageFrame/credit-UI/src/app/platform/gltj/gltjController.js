'use strict';

angular.module('app')
	.controller('gltjController', [ '$rootScope', '$scope', '$http', '$state', '$timeout', 'toaster',
	                                function($rootScope, $scope, $http, $state, $timeout, toaster) {
		$scope.title = '管理统计';
        $scope.param = { };
        $scope.loading = false;
        
		$scope.search = function () {
	        $scope.loading = true;
			$.ajax({
				url : '/ARound/list',
				data: $scope.param
			}).then(function(result) {
		        $scope.loading = false;
				if (result.httpCode == 200) {
					$scope.pageInfo = result.data.info1;
					$scope.pageInfo2 = result.data.info2;
				} else {
					$scope.msg = result.msg;
				}
				$scope.$apply();
			});
		};
		
		$scope.exportStuExcel = function(){
    		$http({
    			url: '/ARound/excelStu',
    			method: "POST",
    			params: { pageNum: $scope.pageInfo.pageNum ,dept : $scope.param.dept},
    			headers: {
       				'Content-type': 'application/json'
    			},
    			responseType: 'arraybuffer'
			}).success(function (result) {
    			 var blob = new Blob([result], {
        			type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
    			});
    			saveAs(blob, '学生信息' + '.xlsx');
			}).error(function (result) {
    			//upload failed
			})
		};
		
		$scope.exportDeptExcel = function(){
    		$http({
    			url: '/ARound/excelDept',
    			method: "POST",
    			params: { pageNum: $scope.pageInfo2.pageNum ,dept2 : $scope.param.dept2},
    			headers: {
       				'Content-type': 'application/json'
    			},
    			responseType: 'arraybuffer'
			}).success(function (result) {
    			 var blob = new Blob([result], {
        			type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
    			});
    			saveAs(blob, '学院信息' + '.xlsx');
			}).error(function (result) {
    			//upload failed
			})
		};
		
		$scope.search();
		
		$scope.clearSearch = function() {
			$scope.param.keyword= null;
			$scope.search();
		};
		
		/**
		 * 数据状态
		 */
		$scope.disableItem = function(record,enable) {
			$.ajax({
				url : enable ? '/user/update' : '/user/delete',
				data: record
			}).then(function(result) {
		        if(result.httpCode ==200){//成功
		        	toaster.clear('*');
                    toaster.pop('success', '', "保存成功");
        			$.ajax({
        				url : '/user/read/list',
        				data: $scope.param
        			}).then(function(result) {
        		        $scope.loading = false;
        				if (result.httpCode == 200) {
        					$scope.pageInfo = result.data;
        				} else {
        					$scope.msg = result.msg;
        				}
        				$scope.$apply();
        			});
                }else{
                    toaster.clear('*');
                    toaster.pop('error', '', result.msg);
                }
			});
		};
		
		// 翻页
        $scope.pagination = function (page) {
            $scope.param.pageNum=page;
            $scope.search();
        };
        
        // 翻页
        $scope.pagination2 = function (page) {
            $scope.param.pageNum2=page;
            $scope.search();
        };
        
} ]);
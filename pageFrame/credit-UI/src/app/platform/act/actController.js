'use strict';

angular.module('app')
	.controller('actController', ['$rootScope', '$scope', '$http', '$state', '$timeout', 'toaster',
		function($rootScope, $scope, $http, $state, $timeout, toaster) {
			$scope.title = '用户管理';
            $scope.show = 1;

			$scope.param = {};
            $scope.param_detail = { };
			$scope.loading = false;
			$scope.search = function() { 
                $scope.show = 1;
				$scope.loading = true;
				$.ajax({
					url: '/pact/read/list',
					data: $scope.param
				}).then(function(result) {
					$scope.loading = false;
					if(result.httpCode == 200) {
						for(var i = 0; i < result.data.list.length; i++) {
							var item = result.data.list[i];
							if(item.actEnrolStartTime != null) {
								var aEST = new Date(item.actEnrolStartTime);
								item.actEnrolStartTime = aEST.toLocaleString();
							} 
							if(item.actEnrolEndTime != null) {
								var aEET = new Date(item.actEnrolEndTime);
								item.actEnrolEndTime = aEET.toLocaleString();
							}
							if(item.actCancleTime != null){
								var aCT = new Date(item.actCancleTime);
								item.actCancleTime = aCT.toLocaleString();
							}
							var aST = new Date(item.actStartTime);
							var aET = new Date(item.actEndTime);
							item.actStartTime = aST.toLocaleString();
							item.actEndTime = aET.toLocaleString();
							
						}
						$scope.pageInfo = result.data;
					} else {
						$scope.msg = result.msg;
					}
					$scope.$apply();
				});
			};

			$scope.search();

			$scope.clearSearch = function() {
				$scope.param.keyword = null;
				$scope.search();
			};

			Date.prototype.toLocaleString = function() {
				var month = this.getMonth() + 1;
				var day = this.getDate();
				var hour = this.getHours();
				var minute = this.getMinutes();
				if(month < 10)
					month = "0" + month;
				if(day < 10)
					day = "0" + day;
				if(hour < 10)
					hour = "0" + hour;
				if(minute < 10)
					minute = "0" + minute;
				return this.getFullYear() + "-" + month + "-" + day + " " + hour + ":" + minute;
			};

            $scope.detail = function (id) {
                $scope.show = 2;
                $scope.param_detail.actId = id;
                $scope.loading = true;
                $.ajax({
                    url : '/xytj/actEnrol/list',
                    data:$scope.param_detail
                }).then(function(result) {
                    $scope.loading = false;
                    if (result.httpCode == 200) {
                        $scope.pageInfo = result.data;
                    } else {
                        $scope.msg = result.msg;
                    }
                    $scope.$apply();
                });
            }
            
            //活动报名人员报表导出
		$scope.enrollistReport = function(actId){
			$http({
				url:'/xytj/enrollist/report',
				method:'POST',
				params:{actId:actId},
				responseType:'arraybuffer'
			}).success(function (result) {
				var blob = new Blob([result],{
					type:'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
				});
				saveAs(blob,'报名列表'+'.xlsx');
            }).error(function(data){
            	alert(result.message);
			});
		}

			// 翻页
			$scope.pagination = function(page) {
				$scope.param.pageNum = page;
				$scope.search();
			};
            $scope.paginationDetail = function (page){
                $scope.param_detail.pageNum=page;
                $scope.detail();
            }

			$scope.delete = function(actId) {
				$scope.loading = true;
				$.ajax({
					url: "/pact/delete",
					data: {
						'id': actId
					}
				}).then(function(result) {
					$scope.loading = false;
					if(result.httpCode == 200) {
						$scope.search();
					} else {
						$scope.msg = result.msg;
					}
					$scope.$apply();
				});
			}
		}
	]);
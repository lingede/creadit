'use strict';

angular.module('app')
	.controller('actroundController2', ['$rootScope', '$scope', '$http', '$state', '$timeout', 'toaster', 'FileUploader',
		function($rootScope, $scope, $http, $state, $timeout, toaster, FileUploader) {
			$scope.title = '审核处理';
			$scope.param = {};
			$scope.param.deptId = $rootScope.userInfo.deptId;
			$scope.selected = [];
			$scope.isAffirmTime = 0;
			$scope.loading = false;

			var uploader = $scope.uploader = new FileUploader({
				url: '/actround2/reviewExcel',
				formData: [{
					deptId: $rootScope.userInfo.deptId,
					reviewName: $rootScope.userInfo.userName
				}],
				onCompleteItem: function(item, response, status, headers) {
					if(response == 'Successs') {
						toaster.clear('*');
						toaster.pop('success', '', "审核完成");
					}
				}
			});

			$scope.search = function() {
				$scope.loading = true;
				$.ajax({
					url: '/actround2/read/list',
					data: $scope.param
				}).then(function(result) {
					$scope.loading = false;
					if(result.httpCode == 200) {
						$scope.pageInfo = result.data;
					} else {
						$scope.msg = result.msg;
					}
					$scope.$apply();
				});
			};

			$scope.AffirmTime = function() {
				$.ajax({
					url: '/actround/affirmTime',
					data: {}
				}).then(function(result) {
					if(result.httpCode == 200) {
						$scope.isAffirmTime = result.data.isAffirmTime;
					} else {
						$scope.msg = result.msg;
					}
				});
			}

			$scope.search();
			$scope.AffirmTime();

			$scope.clearSearch = function() {
				$scope.param.keyword = null;
				$scope.search();
			};

			//选择操作
			var updateSelected = function(action, id) {
				if(action == 'add' && $scope.selected.indexOf(id) == -1) $scope.selected.push(id);
				if(action == 'remove' && $scope.selected.indexOf(id) != -1) $scope.selected.splice($scope.selected.indexOf(id), 1);
			};
			$scope.selectAll = function($event) {
				var checkbox = $event.target;
				var action = (checkbox.checked ? 'add' : 'remove');
				for(var i = 0; i < $scope.pageInfo.list.length; i++) {
					var contact = $scope.pageInfo.list[i];
					if($scope.pageInfo.list[i].reviewStatus2 == 0 && $scope.pageInfo.list[i].isaffirm2 == 1) {
						updateSelected(action, contact.id);
					}
				}
			};
			$scope.isSelected = function(id) {
				return $scope.selected.indexOf(id) >= 0;
			};
			$scope.isSelectedAll = function() {
				return $scope.selected.length == $scope.pageInfo.list.length;
			};
			//更新选择的操作
			$scope.updateSelection = function($event, id) {
				var checkbox = $event.target;
				var action = (checkbox.checked ? 'add' : 'remove');
				updateSelected(action, id);
			};
			//单个审核
			$scope.review = function(id) {
				layui.use(['layer'], function() {
					var layer = layui.layer;
					layer.open({
						type: 1,
						area: ['300px', '200px'],
						content: $('#reviewBox'),
						btn: ['确定', '取消'],
						yes: function(index, layero) {
							$.ajax({
								url: '/actround2/review',
								data: {
									isPass: $scope.isPass,
									deptId: $rootScope.userInfo.deptId,
									reviewReason: $scope.reviewReason,
									id: id,
									reviewName: $rootScope.userInfo.userName
								}
							}).then(callback);

							function callback(result) {
								if(result.httpCode == 200) { //成功
									toaster.clear('*');
									toaster.pop('success', '', "审核完成");
									$scope.param.keyword = null;
									$scope.search();
								} else {
									toaster.clear('*');
									toaster.pop('error', '', result.msg);
								}
							}
							layer.close(index);
						},
						btn2: function(index, layero) {
							$state.go('main.platform.actround2.list');
							layer.close(index);
						},
						cancel: function() {
							$state.go('main.platform.actround2.list');

							//return false 开启该代码可禁止点击该按钮关闭
						}

					});
				});
			};
			//批量审核
			$scope.batchReview = function() {
				if($scope.selected.length == 0) {
					layui.use(['layer'], function() {
						var layer = layui.layer;
						layer.open({
							type: 1,
							area: ['300px', '200px'],
							content: $('#reviewBox_noSelected'),
							btn: ['确定'],
							yes: function(index, layero) {
								layer.close(index);
							},
							cancel: function() {
								//return false 开启该代码可禁止点击该按钮关闭
							}

						});
					});
				} else {
					layui.use(['layer'], function() {
						var layer = layui.layer;
						layer.open({
							type: 1,
							area: ['300px', '200px'],
							content: $('#reviewBox'),
							btn: ['确定', '取消'],
							yes: function(index, layero) {
								$.ajax({
									url: '/actround2/batchReview',
									data: {
										isPass: $scope.isPass,
										deptId: $rootScope.userInfo.deptId,
										reviewReason: $scope.reviewReason,
										selected: $scope.selected,
										reviewName: $rootScope.userInfo.userName
									}
								}).then(callback);

								function callback(result) {
									if(result.httpCode == 200) { //成功
										toaster.clear('*');
										toaster.pop('success', '', "审核完成");
										$scope.param.keyword = null;
										$scope.search();
									} else {
										toaster.clear('*');
										toaster.pop('error', '', result.msg);
									}
								}

								layer.close(index);
							},
							btn2: function(index, layero) {
								$state.go('main.platform.actround2.list');
								layer.close(index);
							},
							cancel: function() {
								$state.go('main.platform.actround2.list');

								//return false 开启该代码可禁止点击该按钮关闭
							}

						});
					});
				}
			};

			$scope.allReview = function() {
				layui.use(['layer'], function() {
					var layer = layui.layer;
					layer.open({
						type: 1,
						area: ['300px', '200px'],
						content: $('#reviewBox'),
						btn: ['确定', '取消'],
						yes: function(index, layero) {
							$.ajax({
								url: '/actround2/reviewAll',
								data: {
									isPass: $scope.isPass,
									deptId: $rootScope.userInfo.deptId,
									reviewReason: $scope.reviewReason,
									reviewName: $rootScope.userInfo.userName
								}
							}).then(callback);

							function callback(result) {
								if(result.httpCode == 200) { //成功
									toaster.clear('*');
									toaster.pop('success', '', "审核完成");
									$scope.param.keyword = null;
									$scope.search();
								} else {
									toaster.clear('*');
									toaster.pop('error', '', result.msg);
								}
							}
							layer.close(index);
						},
						btn2: function(index, layero) {
							$state.go('main.platform.actround2.list');
							layer.close(index);
						},
						cancel: function() {
							$state.go('main.platform.actround2.list');

							//return false 开启该代码可禁止点击该按钮关闭
						}

					});
				});
			};

			$scope.nonReviewReport = function() {
				$http({
					url: '/actround2/nonReviewReport',
					method: 'POST',
					params: {
						deptId: $rootScope.userInfo.deptId
					},
					responseType: 'arraybuffer'
				}).success(function(result) {
					var blob = new Blob([result], {
						type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
					});
					saveAs(blob, '未审核名单报表(演出)' + '.xlsx');
				}).error(function(data) {
					alert(result.message);
				});
			}

			// 翻页
			$scope.pagination = function(page) {
				$scope.param.pageNum = page;
				$scope.search();
			};
		}
	]);
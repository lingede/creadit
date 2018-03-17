'use strict';

angular.module('app')
	.controller('actUpdateController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster',
		function($scope, $rootScope, $state, $timeout, toaster) {
			var title = "";
			var defaultAva = $rootScope.defaultAvatar;
			if($state.includes('**.act.update')) {
				title = "编辑活动";
				var id = $state.params.id;
				activate(id);
				validate(id);
			} else if($state.includes('**.act.create')) {
				title = "添加活动";
				activate(0);
				validate(null);
			}
			$scope.title = $rootScope.title = title;
			$scope.loading = true;
			$scope.submit = function() {
				var m = $scope.record;
				if(m) {
					$scope.isDisabled = true; //提交disabled
					$.ajax({
						url: $scope.record.id ? '/pact/update' : 'pact/add',
						data: $scope.record
					}).then(callback);
				}

				function callback(result) {
					if(result.httpCode == 200) { //成功
						toaster.clear('*');
						toaster.pop('success', '', "保存成功");
						$timeout(function() {
							$state.go('main.platform.act.list');
						}, 2000);
					} else {
						toaster.clear('*');
						toaster.pop('error', '', result.msg);
						$scope.isDisabled = false;
					}
				}

			};

			// 初始化页面
			function activate(id) {
				$scope.loading = true;
				$.ajax({
					url: '/pact/read/detail',
					data: {
						'id': id
					}
				}).then(function(result) {
					$scope.loading = false;
					if(result.httpCode == 200) {
						$scope.record = result.data.record;
						if(result.data.pBasicDeptBeans != undefined && result.data.pBasicDeptBeans.length > 0) {
							for(var i = 0; i < result.data.pBasicDeptBeans.length; i++) {
								if(result.data.pBasicDeptBeans[i].id == null || result.data.pBasicDeptBeans[i].id == "null") {
									//添加页面默认值为空
									result.data.pBasicDeptBeans[i].id = undefined;
								}
							}
						}
						$scope.pBasicDeptBeans = result.data.pBasicDeptBeans;
						if(id == 0) {
							getDateRet($scope.record);
						} else {
							getDataFormat($scope.record);
						}
					} else {
						$scope.msg = result.msg;
					}
					$scope.$apply();
				});
			}

			function getDataFormat(item) {
				if(item.actEnrolStartTime != null) {
					item.actEnrolStartTime = new Date(item.actEnrolStartTime);
				} else {
					item.actEnrolStartTime = getDateInit(7);
				}
				if(item.actEnrolEndTime != null) {
					item.actEnrolEndTime = new Date(item.actEnrolEndTime);
				} else {
					item.actEnrolEndTime = getDateInit(23);
				}
				if(item.actCancleTime != null) {
					item.actCancleTime = new Date(item.actCancleTime);
				} else {
					item.actCancleTime = getDateInit(7);
				}
				item.actStartTime = new Date(item.actStartTime);
				item.actEndTime = new Date(item.actEndTime);
			}

			function getDateInit(hours) {
				var date = new Date();
				date.setMilliseconds("");
				date.setSeconds("");
				date.setMinutes(0);
				date.setHours(hours);
				return date;
			}

			function getDateRet(item) {
				var date = new Date();
				date.setMilliseconds("");
				item.actEnrolStartTime = date;
				item.actEnrolEndTime = date;
				item.actStartTime = date;
				item.actEndTime = date;
				item.actCancleTime = date;
			}

			$scope.updateSNumber = function updateStudentNumber() {
				$scope.record.actStudentNumber = $scope.record.actPeopleNumber - $scope.record.actTeacherNumber;
			}

			$scope.updateTNumber = function updateTeacherNumber() {
				$scope.record.actTeacherNumber = $scope.record.actPeopleNumber - $scope.record.actStudentNumber;
			}

			//表单验证
			function validate(userId) {
				//notEqual 规则
				$.validator.addMethod('notEqual', function(value, ele) {
					return value != this.settings.rules[ele.name].notEqual;
				});
				jQuery('form').validate({
					onkeyup: function(element) {
						if($(element).attr("name") != "account") {
							$(element).valid();
						}
					},
					rules: {
						actName: {
							required: true,
							stringCheck: [],
							maxlength: 10,
							//							isExist:["/user/checkName",userId]
						},
						deptId: {
							required: true
						},
						actNo: {
							required: true,
							isNumber: []
						},
						actEnrolStartTime: {
							required: true,
						},
						actEnrolEndTime: {
							required: true
						},
						actStartTime: {
							required: true
						},
						actEndTime: {
							required: true
						},
						actContent: {
							required: true
						},
						actPlace: {
							required: true
						},
						actCancleTime: {
							required: true
						},
						actPeopleNumber: {
							required: true,
							isNumber: [],
							isIntGteZero: true
						},
						actTeacherNumber: {
							required: true,
							isNumber: [],
							isIntGteZero: true
						},
						actStudentNumber: {
							required: true,
							isNumber: [],
							isIntGteZero: true
						},
					},
					messages: {
						actName: {
							required: '请填写活动名',
							maxlength: "活动名不得超过{0}个字符"
						},
						deptId: {
							required: '请输入学院编号'
						},
						actNo: {
							required: '请填写活动编号',
							isNumber: "活动编号只能是数字"
						},
						actEnrolStartTime: {
							required: '请填写报名开始时间'
						},
						actEnrolEndTime: {
							required: '请填写报名结束时间'
						},
						actStartTime: {
							required: '请填写活动开始时间'
						},
						actEndTime: {
							required: '请填写活动结束时间'
						},
						actContent: {
							required: '请填写活动内容'
						},
						actPlace: {
							required: '请填写活动场地'
						},
						actCancleTime: {
							required: '请填写活动取消时间'
						},
						actPeopleNumber: {
							required: '请填写活动人数',
							isNumber: "活动人数只能是数字",
							isIntGtZero: "活动人数大于零"
						},
						actTeacherNumber: {
							required: '请填写教师人数',
							isNumber: "教师人数只能是数字",
							isIntGtZero: "教师人数大于零"
						},
						actStudentNumber: {
							required: '请填写学生人数',
							isNumber: "学生人数只能是数字",
							isIntGtZero: "学生人数大于零"
						},
					},
					submitHandler: function() {
						$scope.submit();
					}
				});
			}
		}
	]);
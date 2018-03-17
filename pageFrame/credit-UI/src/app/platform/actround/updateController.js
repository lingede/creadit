'use strict';

    angular.module('app')
        .controller('actroundUpdateController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster', 
                                             function($scope, $rootScope, $state, $timeout ,toaster) {
        var title = "";
        var defaultAva = $rootScope.defaultAvatar;
        $scope.myImage='';
        $scope.myCroppedImage = '';
        if($state.includes('**.user.update')){
            title="编辑用户";
            var id = $state.params.id;
            activate(id);
            validate(id);
        }else if($state.includes('**.user.create')){
            title="添加用户";
            activate(0);
            validate(null);
            setTimeout(function(){
                $scope.myCroppedImage = defaultAva;
                !$rootScope.$$phase && $scope.$apply();
            },300);

        }
        $scope.title = $rootScope.title = title;
        $scope.loading = true;
        $scope.submit= function(){
        	$scope.loading = true;
        	$.ajax({
				url : '/upload/imageData',
				data: {fileData:$scope.myCroppedImage,folderName:"userHead"}
			}).then(function(result){
                    if(result && result.httpCode ==200){//成功
                        $scope.record['avatar'] =result.imgName[0];
                        saveData();
                    }else if(result && result.httpCode ==400){
                        saveData();
                    }
                });
        };

        function saveData(){
            var m = $scope.record;
            if(m){
                $scope.isDisabled = true;//提交disabled
                $.ajax({
    				url : $scope.record.id ? '/user/update' : 'user/add',
    				data: $scope.record
    			}).then(callback);
            }
            function callback(result){
                if(result.httpCode ==200){//成功
                    toaster.clear('*');
                    toaster.pop('success', '', "保存成功");
                    $timeout(function(){
                        $state.go('main.sys.user.list');
                    },2000);
                }else{
                    toaster.clear('*');
                    toaster.pop('error', '', result.msg);
                    $scope.isDisabled = false;
                }
            }
        }

        var handleFileSelect=function(evt) {
            var file=evt.currentTarget.files[0];
            if(!/image\/\w+/.test(file.type)){
                return false;
            }
            var reader = new FileReader();
            reader.onload = function (evt) {
                $scope.$apply(function($scope){
                    $scope.myImage=evt.target.result;
                });
            };
            reader.readAsDataURL(file);
        };
        angular.element(document.querySelector('#fileInput')).on('change',handleFileSelect);


        // 初始化页面
        function activate(id) {
	        $scope.loading = true;
        	$.ajax({
				url : '/user/read/detail',
				data: {'id': id}
			}).then(function(result) {
		        $scope.loading = false;
				if (result.httpCode == 200) {
					$scope.record = result.data.record;
					if(result.data.sysDeptBeans!=undefined&&result.data.sysDeptBeans.length>0){
						for(var i=0;i<result.data.sysDeptBeans.length; i++){
							if(result.data.sysDeptBeans[i].id==null||result.data.sysDeptBeans[i].id=="null"){
								//添加页面默认值为空
								result.data.sysDeptBeans[i].id = undefined;
							}
		                }
					}
					$scope.sysDeptBeans = result.data.sysDeptBeans;
				} else {
					$scope.msg = result.msg;
				}
				$scope.$apply();
	            $timeout(function(){
	                $scope.myCroppedImage=$scope.record.avatar || defaultAva;//初始化 预览图
	                $scope.record.avatar = null;
	            },300);
			});
        }

      //表单验证
        function validate(userId){
            //notEqual 规则
            $.validator.addMethod('notEqual', function(value, ele){
                return value != this.settings.rules[ele.name].notEqual;
            });
            jQuery('form').validate({
            	onkeyup : function(element){
            		if($(element).attr("name")!="account"){
            			$(element).valid();
            		}
            	},
                rules: {
                    account: {
                        required: true,
                        stringCheck:[],
                        maxlength:10,
                        isExist:["/user/checkName",userId]
                    },
                    userName: {
                        required: true
                    },
                    phone: {
                        required: true,
                        isPhone:[]
                    },
                    password:{
                    	maxlength: 16
                    },
                    confirmPassword:{
                    	maxlength: 16,
                        equalTo: "#password"
                    },
                    userLogoPic:{
                    	
                    },
                    sex:{
                    	selected:[]
                    },
                    userType:{
                    	selected:[]
                    },
                    deptId:{
                    	selected:[]
                    },
                    locked:{
                    	selected:[]
                    }
                },
                messages: {
                    account: {
                        required: '请填写帐号',
                        maxlength:"帐号不得超过{0}个字符",
                        isExist:"该帐号已存在"
                    },
                    userName: {
                        required: '请填写用户名'
                    },
                    phone: {
                        required: '请填写联系方式'
                    },
                    password:{
                        required: '请填写密码',
                        maxlength: '密码长度不可大于16位'
                    },
                    confirmPassword:{
                        required: '请填写确认密码',
                        maxlength: '密码长度不可大于16位',
                        equalTo: '两次输入的密码不相符'
                    },
                    userLogoPic:{
                    	
                    },
                    sex:{
                    	selected:"请选择性别"
                    },
                    userType:{
                    	selected:"请选择用户类型"
                    },
                    deptId:{
                    	selected:"请选择部门"
                    },
                    locked:{
                    	selected:"请选择激活状态"
                    }
                },
                submitHandler: function() {
                    $scope.submit();
                }
            });
        }

    }]);
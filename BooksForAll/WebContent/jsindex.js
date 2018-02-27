var app = angular.module('myApp', []);
app.controller('ebooksCtrl', function($scope, $http, $window,$location) {
	

	//,$location,$window,$state
	$scope.submitLogin = function (){
	    var loginData = {};
	    $("#loginForm").find("input[name]").each(function (index, node) {
	    	loginData[node.name] = node.value;
	    });
	    loginData=JSON.stringify(loginData);
	    console.log(loginData);
		
		$http.post('Login',loginData).success(function(data,status,headers,config){
			//alert("success");
			var allcookies = document.cookie;

    		cookiearray = allcookies.split(';');   
   			username = cookiearray[0].split('=')[1];
   			type = cookiearray[1].split('=')[1];
   			console.log(type);
   			if(type=="admin"){
   				window.location.href = 'homeAdmin.html';
   				console.log($location)
   				//alert("admin");
   			}
   			if(type=="user"){
   				window.location.href = 'homeUser.html';
   				//alert("user");
   			}
		})
		
		.error(function(data,status,headers,config){
			//alert("failed");
			     
            document.getElementById("failure").innerHTML="invalid username or password";
			//console.log("failed");
			//$window.location.href = '/index.html';
		});
	};
	
	
	$scope.submitRegistration = function (){
	    var loginData = {};
	    var regData = {};
	     $("#registrationForm").find("input[name]").each(function (index, node) {
	         regData[node.name] = node.value;
	     });
	     console.log(regData);
	     regData=JSON.stringify(regData);
		
		$http.post('SignUp',regData).success(function(data,status,headers,config){
			$scope.x=headers('message');
			//console.log("l"+$scope.x+"");
			if ($scope.x == "true") {
				//alert("yesy");
				document.getElementById('msg').innerHTML = "signed up successfully, welcome";
				location.reload();
			} else {
				document.getElementById('msg').innerHTML = $scope.x;
			}
		}) 
		.error(function(data,status,headers,config){
			
			console.log("failed");
		});
	};
});



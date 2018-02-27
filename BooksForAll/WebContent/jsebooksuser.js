	var app = angular.module('myApp', []);
	app
			.controller(
					'ebooksCtrl',
					function($scope, $http, $window) {

						var timerhandler;

						$scope.CheckNow = function() {
							$
									.ajax({
										type : 'GET',
										url : 'GetAllLikesNumOrdered',
										success : function(data) {
											//       	alert('success');
											var allcookies = document.cookie;
											cookiearray = allcookies.split(';');
											// username = cookiearray[0].split('=')[1]; 
											type = cookiearray[1].split('=')[1];
											console.log(type)
											if (type == "admin") {
												window.location.href = 'homeAdmin.html';
											} else {
												$http.get('GetAllEbooks').success(function(data, status, headers,config) {
														$scope.allEbooks = data;
												})
												.error(function(data, status, headers,config){
													console.log(data);
												});
												$scope.myVar = true;
												// alert('suuu')
											}
										}
									});

						}
						console.log("ajaaax")
						timerhandler = setInterval($scope.CheckNow, 50000);

						$http.get('GetAllEbooks').success(function(data, status, headers,config) {
							$scope.myData = data;
							$scope.statuscode = status;
						})
						.error(function(data, status, headers,config){
							console.log(data);
						});
						$scope.myFunction = function(bookTitle) {
							console.log(bookTitle);
							var pars = {
								params : {
									title : bookTitle
								}
							};
							$http.get('GetAllNicknamesByEbookLikes', pars).success(function(data, status, headers,config) {
									$scope.booklikes = data;
									$scope.likeStatusCode = status;
							})
							.error(function(data, status, headers,config){
								console.log(data);
							});
						}

						$scope.submitPayment = function() {
							//var formData = $("#payment-form").serializeArray();
							//formData=JSON.stringify(formData);
							var formData = {};
							var title = document.getElementById("selectedBook").innerHTML;
							formData["title"] = title;
							var allcookies = document.cookie;
							cookiearray = allcookies.split(';');
							name = cookiearray[0].split('=')[0];
							value = cookiearray[0].split('=')[1];
							formData[name] = value;

							$("#payment-form").find("input[name]").each(
									function(index, node) {
										formData[node.name] = node.value;
									});
							$("#payment-form").find("select[name]").each(
									function(index, node) {
										formData[node.name] = node.value;
									});
							//if(!(formData["id"]!=null&&formData["username"]!=null&&formData["fullName"]!=null&&formData["expiry"]!=null&&formData["cvv"]!=null&&formData["creditCardNumber"]!=null&&formData["creditCardComapany"]!=null)){
							//return false;
							//	}

							//d=JSON.stringify(formData["creditCardNumber"]);
							//console.log(d.replace(/\s/g, ''));  // ##A#B##C###D#EF#
							//e=JSON.stringify(formData["expiry"]);
							console.log(formData);
							formData = JSON.stringify(formData);
							$http
									.post('AddNewEbookPurchase', formData)
									.success(function(data, status, headers,config) {
												$scope.x=headers('message');
												console.log("l"+$scope.x+"");
												if ($scope.x == "true") {
													//alert("sssss");
													$('#payModal').modal('toggle');
													document.getElementById("selectedBook").innerHTML = "";
												} else {
													document.getElementById('msg').innerHTML = $scope.x;
													//alert(data);
												}
												//$('#payModal').dialog("close")
												//$.modal.close();
											}).error(
											function(data, status, headers,
													config) {
								 
												console.log("failed");
											});
						}

						$scope.logout = function(){
							$http.get("Logout").success(function(data, status, headers,config){
				               window.location.href='index.html';

							})
							.error(function(data, status, headers,config){
								console.log(data);
							});
						}

						$scope.BuyEbook = function(title) {
							document.getElementById("BuyEbookName").innerHTML = "paying for: "
									+ title;
							document.getElementById("selectedBook").innerHTML = title;
							document.getElementById("selectedBook").style.visibility = " none";
						}
						$scope.getReviews = function(id, title) {
							document.getElementById('ebookName').innerHTML = title;
							var temp = {
								title : title
							};
							var jsonId = JSON.stringify(temp);
							var pars = {
								params : {
									title : title
								}
							};
							console.log(jsonId)
							
							
							$http.get('GetAllReviewsByTitle', pars).success(function(data, status, headers,config){
										$scope.reviews = data;
										$scope.statusCode = status;
							})
							.error(function(data, status, headers,config){
								console.log(data);
							});
						}

						$scope.openImg = function(imgId, source, alter) {
							var modal = document.getElementById('ghadaModal');

							// Get the image and insert it inside the modal - use its "alt" text as a caption
							var img = document.getElementById(imgId);
							var modalImg = document.getElementById("img01");
							var captionText = document
									.getElementById("caption");
							modal.style.display = "block";
							modalImg.src = source;
							captionText.innerHTML = alter;
							// Get the <span> element that closes the modal
							var span = document.getElementsByClassName("close")[0];

							// When the user clicks on <span> (x), close the modal
							span.onclick = function() {
								modal.style.display = "none";
							}
						}

						$(document).ready(function() {
							$('[data-toggle="tooltip"]').tooltip();
						});
					});
var x;
	var test=0;
	var app = angular.module('myApp', []);
	app.directive('errSrc', function() {
		  return {
		    link: function(scope, element, attrs) {
		      element.bind('error', function() {
		        if (attrs.src != attrs.errSrc) {
		          attrs.$set('src', attrs.errSrc);
		        }
		      });
		    }
		  }
		});
	app.controller('ebooksCtrl', function($scope, $http,$window) {		
			var isReading=0;
			var timerhandler;
			
		    $scope.CheckNow = function()
		    {
//	        	alert('success');
				$scope.openBook=false;
		        $.ajax(
		        {
		            type: 'GET',
		            url: 'GetAllLikesNumOrdered',
		            success: function(data) 
		            {
		     //       	alert('success');
		                var allcookies = document.cookie;
		                cookiearray = allcookies.split(';');
		                  // username = cookiearray[0].split('=')[1]; 
		                   type = cookiearray[1].split('=')[1];  
		                   console.log(type)
		                   if(type=="admin"){
		                       window.location.href='homeAdmin.html';
		                   }
		                   else{
		                	   var allcookies = document.cookie;
		                       cookiearray = allcookies.split(';');
		                       var username = cookiearray[0].split('=')[1]; 
		                		var pars = {
		                			params : {username : username}
		               			};
		                		console.log(username);
		                		$http.get('GetEbooksByUsername', pars).success(function(data, status, headers,config) {
		                			$scope.myEbooks = data;
		               				$scope.statusCode = status;
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
	
		
		    timerhandler = setInterval($scope.CheckNow,50000);
					
        var allcookies = document.cookie;
        cookiearray = allcookies.split(';');
           username = cookiearray[0].split('=')[1]; 
           type = cookiearray[1].split('=')[1];  
           console.log(username);
   //		if(type=="admin")
	//			window.location.href = 'homeAdmin.html';


       var parameters = { params: {
			username : username
          }
       }
       

       
       $scope.logout = function(){
			$http.get("Logout").success(function(data, status, headers,config){
              window.location.href='index.html';

			})
			.error(function(data, status, headers,config){
				console.log(data);
			});
		}
		
		$scope.submitAddingReview = function() {
			var formData = {};
			var title = document.getElementById("selectedBook").innerHTML;
			formData["title"] = title;
			var allcookies = document.cookie;
			cookiearray = allcookies.split(';');
			name = cookiearray[0].split('=')[0];
			value = cookiearray[0].split('=')[1];
			formData["username"] = value;
			//formData["nickname"] = "Ghada";
			//formData["approved"] = 0;
			$("#addReviewForm").find("textarea[name]").each(function(index, node) {
				formData[node.name] = node.value;
			});
			$("#addReviewForm").find("input[name]").each(function(index, node) {
				formData[node.name] = node.value;
			});
			
			formData = JSON.stringify(formData);
			console.log(formData);
			$http.post('AddEbookReview', formData).success(function(data,status,headers,config) {
				//alert(data);
				document.getElementById("review").value = null;
				$('#addReviewModal').modal('toggle');
				document.getElementById("selectedBook").innerHTML = "";

				})
				.error(function(data,status,headers,config){
					console.log("failed");
				});
		}
		
		
		
		$scope.AddReview = function(title) {
			document.getElementById('selectedBook').innerHTML = title;
			document.getElementById('selectedBook').style.visibility = 'hidden';
		}
		$scope.AddNewLike =function (title){
// 			var pars = {
// 				params:{title: title}
// 			};
			var allcookies = document.cookie;
	        cookiearray = allcookies.split(';');
			var username = cookiearray[0].split('=')[1]; 
			var temp = {
					title: title,
					username:username
			};
			var jsonTitle = JSON.stringify(temp);
			console.log(jsonTitle);
			$http.post('AddNewEbookLike',jsonTitle).success(function(data,status,headers,config) {
				//alert('like added successfully');
				location.reload();
			})
			.error(function(data,status,headers,config){
				//alert("failed");
				console.log("failed");
			});
		}
		
		$scope.myFunction = function(bookTitle) {
			console.log(bookTitle);
			var pars = {
					params:{title: bookTitle}
				};
				$http.get('GetAllNicknamesByEbookLikes',pars).success(function(data, status, headers,config) {
					$scope.booklikes = data;
					$scope.likeStatusCode = status;
				})
				.error(function(data, status, headers,config){
					console.log(data);
				});
		}
		$scope.removeLike =function (title){
// 			var pars = {
// 				params:{title: title}
// 			};
			var allcookies = document.cookie;
	        cookiearray = allcookies.split(';');
			var username = cookiearray[0].split('=')[1]; 
			var temp = {
					title: title,
					username:username
			};
			var jsonTitle = JSON.stringify(temp);
			console.log(jsonTitle);
			$http.post('UnlikeEbook',jsonTitle).success(function(data, status, headers,config){
				//alert('unlike done');
				location.reload();
			})
			.error(function(data, status, headers,config){
				console.log(data);
			});
		}
		
       $scope.getReviews =function (id,title){
			document.getElementById('ebookName').innerHTML =  title;
			var temp = {
				title: title
			};
			var jsonId = JSON.stringify(temp);
			var pars = {
				params:{title: title}
			};
			console.log(jsonId)
			$http.get('GetAllReviewsByTitle',pars).success(function(data, status, headers,config) {
				$scope.reviews = data;
				$scope.statusCode = status;
			})
			.error(function(data, status, headers,config){
				console.log(data);
			});
		}
		

	
		$scope.openImg = function(imgId,source,alter){
			console.log("heeey")
		    	var modal = document.getElementById('ghadaModal');

		    	// Get the image and insert it inside the modal - use its "alt" text as a caption
		    	var img = document.getElementById(imgId);
		    	var modalImg = document.getElementById("img01");
		    	var captionText = document.getElementById("caption");

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
		
		$scope.openBookFunc = function(ebook,title){
		 //   document.getElementById('selectedTarget').style.visibility='hidden';
		    document.getElementById('back_btn').style.visibility='visible';

		//	var tempScrollTop = $(window).scrollTop();
		//var tempScrollTop =   document.getElementById('theBook');
		//var t=tempScrollTop.scrolltop;
			var allcookies = document.cookie;
			cookiearray = allcookies.split(';');
			name = cookiearray[0].split('=')[0];
			value = cookiearray[0].split('=')[1];
			var pars = {
					params : {
						title : title,
						username : value
					}
				};
				$http.get('GetScrollPosition', pars).success(function(data, status, headers,config) {
							$scope.lastPostition = data;
							console.log($scope.lastPostition);	
							if($scope.lastPostition > 0){
								
								var result = confirm(username+", "+ "want to go to where you last stopped reading?" );
								if(result){
								//	console.log("to scroll")
								
									function updatePos(){
									
										$(window).scrollTop($scope.lastPostition);
									}
									setTimeout(updatePos,200);
									
								//	$(document).scrollTop($scope.lastPosition);
	
								}

						}
							
						})
						.error(function(data, status, headers,config){
							console.log(data);
						});
				
		//	$('#selectedTarget').load(ebook);
			
		//	$("img").each(function() {
			//    console.log($(this).prop("src"));
		//	});
			
		//    var x = document.getElementById("selectedTarget");

		    $scope.openBook=true;
		    test=1;
			$scope.ebookContent  =ebook;
			$scope.theBookTitle = title;
			x=title;
			console.log($scope.openBook)
			console.log(ebook)
// 			$('#selectedTarget').load(ebook,function(data){
// 			    $(data).find('img').each(function(){
// 			         $(this).attr('src','...')
// 			    });
// 			});
			//var a=document.getElementById("allmyebooks");
			//a.setAttribute('data-ng-include',ebook);
			//isReading=1;
			//var a=document.getElementById("allmyebooks");
			//var b=document.getElementById("readbook");
			//a.setAttribute('data-ng-include',ebook);
			//window.open(ebook);

		}
		

		
		$scope.finishReading = function(id){
			if($scope.openBook){
				//alert("here");
				//document.getElementById('selectedTarget').style.visibility='visible';
			    document.getElementById('back_btn').style.visibility='hidden';
				//var tempScrollTop = $(window).scrollTop();
				//var tempScrollTop =   document.getElementById('theBook');
				//var t=tempScrollTop.scrolltop;
				var tempScrollTop = $(window).scrollTop();
				var allcookies = document.cookie;
				cookiearray = allcookies.split(';');
				name = cookiearray[0].split('=')[0];
				value = cookiearray[0].split('=')[1];
				var temp = {
						scrollPos: tempScrollTop,
						username:value,
						title : $scope.theBookTitle
				};
				var position = JSON.stringify(temp);
				$http.post('UpdateScrollPosition', position).success(function(data,status,headers,config) {
				//	alert(data);
	     //           window.location.href='myEbooks.html';
					$scope.openBook=false;
					test=0;
					})
					.error(function(data,status,headers,config){
						//alert("failed");
						console.log("failed");
					});
				if(id=="home"){
	                window.location.href='homeUser.html';
				}
				if(id=="ebooks"){
	                window.location.href='ebooksUser.html';
				}
				if(id=="myEbooks"){
	                window.location.href='myEbooks.html';
	                location.reload();
				}
				if(id=="booksForAll"){
	                window.location.href='homeUser.html';
				}

			 //   document.getElementById(id).href="xyz.php"; 
		//	    document.getElementById("ebooks").href="ebooksUser"; 
		//	    document.getElementById("myEbooks").href="myEbooks.html"; 
		//	    document.getElementById("booksForAll").href="xyz.php"; 


			}
			test=0;
			$scope.openBook=false;
		}
		
		
	});


function savePosition(){
	if(test){
		console.log("save");
		var tempScrollTop = $(window).scrollTop();
		var allcookies = document.cookie;
		cookiearray = allcookies.split(';');
		name = cookiearray[0].split('=')[0];
		value = cookiearray[0].split('=')[1];
		var temp = {
				scrollPos: tempScrollTop,
				username:value,
				title : x
		};
		var position = JSON.stringify(temp);
		$.post('UpdateScrollPosition', position).done(function(response) {
			
		});
	}
}
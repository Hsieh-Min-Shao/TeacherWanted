


$(document).ready(function(){
    $("#collapse").on("click",function(){
        $("#sidebar").toggleClass("active")
        
    })

    const file = document.querySelector("#p_file");
    const img = document.querySelector("#eximg");
    file.addEventListener("change", () => {
        img.src = URL.createObjectURL(file.files[0]);
    });
    
      
        // ===============銀行代碼
        fetch('.././json/bankCodes.json')
        .then(response => response.json())
        .then(data => {
          const bankCodes = data.bankCodes;
          const bankCodeInput = document.getElementById('bankCode');
          const bankName = document.getElementById('bankName');

          // 綁定輸入框的oninput事件
          bankCodeInput.oninput = () => {
            const code = bankCodeInput.value;
            const bank = bankCodes.find(bank => bank.code === code);
            if (bank) {
              bankName.innerText = bank.name;
              bankName.classList.remove('error');
              $("#bankCode").removeClass('is-invalid');
            } else {
              bankName.innerText = '找不到該銀行';
              bankName.classList.add('error');
              // $("#bankCode").addClass('is-invalid');

            }
          };
              // 觸發oninput事件，以取得初始數值
            bankCodeInput.oninput();
        });
        var admin =JSON.parse(sessionStorage.getItem('adminStorage'))





        $.ajax({
          type: 'GET',
          url: '/teachers/pro/'+admin.adminId,
          contentType: 'application/json',
          success: function(data) {
            $('#teaLocation').val(data.teaLocation);
            if(data.teaProfile!=null){

              $('#teaProfile').val(data.teaProfile.replace(/<br>/g, '\n'));
            }
            $('#teachingSubject1').val(data.teachingSubject1);
            $('#bankCode').val(data.bankCode);
            $('#teaLocation').val(data.teaLocation);
            $('#bankAccount').val(data.bankAccount);
            $('#teaName').val(data.teaName);
            var teaPhoto = data.teaPhoto;
            if (teaPhoto !== null) {
              $("#eximg").attr("src", "data:image/png;base64," + teaPhoto);
          }else{
            $("#eximg").attr("src","../img/logobgg.png")
          }
        
          },
          error: function(jqXHR, textStatus, errorThrown) {
            console.log("error")
          }
        });
        
        console.log(file.files[0])

        $('#btnsubmit').click(function(event) {
          // console.log("1111");
          event.preventDefault();
          console.log(file.files[0])        
    

          

               // 檢查每個 input 元素的值
               var allInputsFilled = true;
               $(".needvalue").each(function() {
                 if ($(this).val() === "") {
                  console.log(this)
                   allInputsFilled = false;
                   console.log("有空值")
                   return false;  // 結束迴圈
                 }
               });
     
               // 如果所有 input 元素都有值，執行送出資料的動作
               if (allInputsFilled) {
               } else {
                 // 如果有任何一個 input 元素沒有值，提示使用者填寫完整
                 alert("請填寫完整資料！");
                 return;
               }
     
          // =====隨機產生密碼===============
            var randomCode = '';
            var characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
            for (var i = 0; i < 8; i++) {
              randomCode += characters.charAt(Math.floor(Math.random() * characters.length));
            }
                    // 建立 Date 物件
                    var date = new Date();
    
                    // 取得年、月、日、時、分、秒
                    var year = date.getFullYear();
                    var month = ('0' + (date.getMonth() + 1)).slice(-2);
                    var day = ('0' + date.getDate()).slice(-2);
                    var hours = ('0' + date.getHours()).slice(-2);
                    var minutes = ('0' + date.getMinutes()).slice(-2);
                    var seconds = ('0' + date.getSeconds()).slice(-2);
    
                    // 格式化日期時間字串
                    var nowdate = year + '/' + month + '/' + day + ' ' + hours + ':' + minutes + ':' + seconds;
    
            // ===============修改老師=====//


            // ========上傳圖片處理================ //
            // if(沒有上傳圖片){}，做不同的ajex
            if(file.files[0]===undefined){
              var formDataEdit = {
                adminId: admin.adminId,
                teaProfile: $('#teaProfile').val(),
                // teaPhoto: btoa(e.target.result),
                teaLocation: $('#teaLocation').val(),
                teachingSubject1: $('#teachingSubject1').val(),
                bankCode: $('#bankCode').val(),
                bankAccount: $('#bankAccount').val(),
                teaName: $('#teaName').val(),
            };
                            // 發送 POST 請求到 "/test/t1"
                            fetch("/teachers/pro/"+admin.adminId, {
                              method: "PUT",
                              headers: {
                                  "Content-Type": "application/json"
                              },
                              body: JSON.stringify(formDataEdit)
                          })
                          .then(response => {
                              if (response.ok) {
                                  alert("成功");
                                  location.reload()
                              } else {
                                  alert("失敗");
                              }
                          })
                          .catch(error => {
                              alert("请求失败: " + error);
                          });
                          
            }else{
              const fr = new FileReader();
              // 當 FileReader 讀取完成時觸發事件
                fr.addEventListener("load", e => {
                  var formDataEdit = {
                    adminId: admin.adminId,
                    teaProfile: $('#teaProfile').val(),
                    teaPhoto: btoa(e.target.result),
                    teaLocation: $('#teaLocation').val(),
                    teachingSubject1: $('#teachingSubject1').val(),
                    bankCode: $('#bankCode').val(),
                    bankAccount: $('#bankAccount').val(),
                    teaName: $('#teaName').val(),
                };
                console.log(formDataEdit)
                            // 發送 POST 請求到 "/test/t1"
                            fetch("/teachers/pro/"+admin.adminId, {
                              method: "PUT",
                              headers: {
                                  "Content-Type": "application/json"
                              },
                              body: JSON.stringify(formDataEdit)
                          })
                          .then(response => {
                              if (response.ok) {
                                alert("成功");
                                location.reload() 
                              } else {
                                  alert("失敗");
                              }
                          })
                          .catch(error => {
                              alert("请求失败: " + error);
                          });
              });
              // 以二進位字串讀取選擇的檔案
              fr.readAsBinaryString(file.files[0]);
            }

            
    
    })



})

// =====================使textarea裡的文字可以換行


var admin =JSON.parse(sessionStorage.getItem('adminStorage'))
function goTofrontTea(){
  location.href=`http://localhost:8080/courseteacher/TeacherInfoVue.html?teaId=${admin.teaId}`
}
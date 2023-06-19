// ============去驗證是否登入+把登入者資料放入前端session變數裡
// ============使用範例：var test=JSON.parse(sessionStorage.getItem('memberStorage'));
// ============可印出來：console.log(test)
// ============可印會員ID：console.log(test.teaId)
$(function () {
  console.log("hi");
  loginCheck();

  // ===========登出的方法===============
  // function logOut() {
  //   $.ajax({
  //     url: "/logout", // 修正URL
  //     type: "GET",
  //     success: function(Data) {
  //       sessionStorage.removeItem('memberStorage');
  //       window.location.href = "/index";
  //     },
  //     error: function(xhr, status, error) {
  //       console.log("登出失敗");
  //     }
  //   });
  // }
});

function loginCheck() {
  $.ajax({
    type: "GET",
    url: "/user/session",
    contentType: "application/json",
    success: function (Data) {
      sessionStorage.setItem("memberStorage", JSON.stringify(Data));
    },
    error: function () {
      alert("請先登入");
      window.location.href = "/login";
      return; // 錯誤發生時立即結束函式，不執行其他的JavaScript代碼
    },
  });
}

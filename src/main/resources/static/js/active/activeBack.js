// document.write("../check.js");

var uniqueParam = Date.now(); // 使用时间戳作为唯一参数
var url = "/activeBack?param=" + uniqueParam;
var teacherInfo = JSON.parse(sessionStorage.getItem("adminStorage"));
console.log("------------->", teacherInfo.teaId);
$(function () {
  let data = { teaId: teacherInfo.teaId };
  activeAjax(data);
});

$(function () {
  // 監聽選項變更事件
  $("#activityType").change(function () {
    // 觸發搜尋按鈕的點擊事件
    $(".searchBtn").click();
  });
  // 監聽輸入框的按鍵事件
  $("#searchKey").keydown(function (event) {
    // 檢查是否按下的是 Enter 鍵 (keyCode 13)
    if (event.keyCode === 13) {
      // 觸發搜尋按鈕的點擊事件
      $(".searchBtn").click();
    }
  });
  // 搜尋按鈕 標籤選取 開始
  $(".searchBtn").on("click", function () {
    $("#activeTbody").empty();
    let searchKeyword = $("#searchKey").val();
    let activityType = $("#activityType").val();
    let dataSearch = {
      searchKeyword: searchKeyword,
      activityType: activityType,
      teaId: teacherInfo.teaId,
    };
    activeAjax(dataSearch);
  });
  // 搜尋按鈕 結束
  // 重製按鈕 開始
  $(".remakeBtn").on("click", function () {
    $("#activeTbody").empty();
    $("#searchKey").val("");
    $("#activityType").val("");
    let data = { teaId: teacherInfo.teaId };
    activeAjax(data);
  });
  // 重製按鈕 結束
});

// function區域 開始
function activeAjax(data) {
  $.ajax({
    url: url, // 資料請求的網址
    type: "GET", // GET | POST | PUT | DELETE | PATCH
    data: data, // 將物件資料(不用雙引號) 傳送到指定的 url
    dataType: "json", // 預期會接收到回傳資料的格式： json | xml | html
    success: function (data) {
      // request 成功取得回應後執行
      $("#activeTbody").empty();
      for (let i = 0; i < data.length; i++) {
        let status;
        let dropBtnText;
        if (data[i].activityStatus == 1) {
          status = "上架中";
          dropBtnText = "下架";
        } else if (data[i].activityStatus == 0) {
          status = "已下架";
          dropBtnText = "上架";
        } else if (data[i].activityStatus == 2) {
          status = "已截止";
          dropBtnText = "限制";
        }

        tableData(data, i, status, dropBtnText);
      }
      tableAction(data);
    },
    error: function (xhr) {
      // request 發生錯誤的話執行
      console.log(xhr);
      alert("無登入狀態，或登入異常，請重新登入。\n如有問題請聯繫管理員");
      window.location.href = "/active/easyLogin.html";
    },
    // success回傳部分 結束
  });
}

//表格操作按鈕 開始
function tableAction(data) {
  // 詳情按鈕  燈箱部分開始
  // 監聽按鈕點擊事件
  $(".introduceBtn").on("click", function () {
    console.log("詳情");
    $(".lightbox").fadeIn();
  });

  // 監聽關閉按鈕點擊事件
  $(".lightbox").on("click", ".lightbox-close", function () {
    $(".lightbox").fadeOut();
  });

  // 監聽燈箱以外的地方點擊事件
  $(".lightbox").on("click", function (event) {
    var lightboxContent = $(".lightbox-content");
    if (
      !lightboxContent.is(event.target) &&
      lightboxContent.has(event.target).length === 0
    ) {
      $(".lightbox").fadeOut();
    }
  });

  // 監聽按下 "Esc" 鍵事件
  $(document).on("keydown", function (event) {
    if (event.keyCode === 27) {
      $(".lightbox").fadeOut();
    }
  });

  $(".tbodyBtn").each(function (i) {
    var tbodyBtnThis = this;
    i--; //去掉表頭欄位
    $(this).on("click", ".introduceBtn", function () {
      console.log($(tbodyBtnThis).siblings(".activityId").text());
      $(".lightbox").empty().append(`
      <div class="lightbox-content">
      <span class="lightbox-close">&times;</span>
      <div
      class="borderShadow"
      style="width: 700px; "
    >
      <!-- 活動詳情 開始 -->
      <section>
        <div class="activeBackOneImgBlock flexAllCenter" style="height:300px" >
          <img
            src= "data:image/${
              data[i].activityPhotoType
            };base64,${data[i].activityPhoto}" 
            style="width: 80%; height: 80%"
            alt=""
          />
        </div>
        <div class="flexAllCenter" >
          <div style="width: 80%; height: 80%">
            <p>活動ID:${data[i].activityId}</p>
            <p>活動名稱:${data[i].activityName}</p>
            <p>類型:${data[i].activityType}</p>
            <p>報名截止日期:${convertToFormattedDate(
              data[i].activityDueTime
            )}</p>
            <p>活動開始日期:${convertToFormattedDate(
              data[i].activityStartTime
            )}</p>
            <p>活動結束日期:${convertToFormattedDate(
              data[i].activityEndTime
            )}</p>
            <p>報名人數:${data[i].currentNumber}</p>
            <p>活動介紹:${data[i].activityDetail}</p>
            <a href="/active/activeChatRoom.html?activityId=${
              data[i].activityId
            }">活動聊天室連結</a>
          </div>
        </div>
      </section>
      <!-- 活動詳情 結束 -->
      <!-- 報名人的資料 開始 -->
      <table class="activeBackTable" style="max-height: 100%">
        <thead>
          <tr>
            <th class="activityOrderName">名稱</th>
            <th class="activityOrderPhone">電話</th>
            <th class="activityOrderEmail">信箱</th>
            <th class="activityOrderRegisterTime">報名時間</th>
          </tr>
        </thead>
        <tbody id="activeOrderTbody"></tbody>
      </table>
      <!-- 報名人的資料 結束 -->
    </div>
      </div>`);
      // 報名成員列表 開始
      axiosOrderList(data[i].activityId).then((data) => {
        console.log(data);
        $("#activeOrderTbody").empty();
        for (let i = 0; i < data.length; i++) {
          $("#activeOrderTbody").append(`
          <tr>
            <th>${data[i].memName}</th>
            <th>${data[i].memPhone}</th>
            <th>${data[i].memEmail}</th>
            <th>${convertToFormattedDate(data[i].registerTime)}</th>
          </tr>
          
          
          `);
        }
      });
      // 報名成員列表 結束
    });
  });
  // 詳情按鈕  燈箱部分結束
  // 修改按鈕部分開始
  $(".tbodyBtn").each(function (i) {
    var tbodyBtnThis = this;
    $(this).on("click", ".editBtn", function () {
      window.location.href =
        "/active/activeBackEdit.html?activityId=" +
        $(tbodyBtnThis).siblings(".activityId").text();
    });
  });
  // 修改按鈕部分結束
  // 上/下架按鈕 開始
  $(".tbodyBtn").each(function (i) {
    var tbodyBtnThis = this;
    let dropBtnTextColor = $(this).find(".dropBtn").text();
    console.log("上/下架按鈕文字:" + $(this).find(".dropBtn").text());
    // 根據上下架狀態改變顏色 開始
    if (dropBtnTextColor == "上架") {
      $(this).find(".dropBtn").addClass("dropUpBtn");
    } else if (dropBtnTextColor == "下架") {
      $(this).find(".dropBtn").addClass("dropDownBtn");
    } else if (dropBtnTextColor == "限制") {
      $(this).find(".dropBtn").addClass("dropNoneBtn");
      $(this).attr("disabled", true);
    }
    // 根據上下架狀態改變顏色 結束
    // 上/下架按鈕 點擊事件 開始
    $(this).on("click", ".dropBtn", function () {
      id = $(tbodyBtnThis).siblings(".activityId").text();
      console.log(id);

      let statusValue;
      if ($(tbodyBtnThis).siblings(".status").text() == "已下架") {
        statusValue = 1;
      } else if ($(tbodyBtnThis).siblings(".status").text() == "上架中") {
        statusValue = 0;
      } else {
        statusValue = 2;
      }
      console.log(statusValue);
      let data = JSON.stringify({
        activityId: id,
        activityStatus: statusValue,
      });
      updateStatus(data);
    });
    // 上/下架按鈕 點擊事件 結束
  });
  // 上/下架按鈕 結束
  // 刪除按鈕部分開始
  $(".tbodyBtn").each(function (i) {
    var tbodyBtnThis = this;
    $(this).on("click", ".deleteBtn", function () {
      console.log(
        "這個活動id" + $(tbodyBtnThis).siblings(".activityId").text()
      );
      if (confirm("請問要刪除嗎?")) {
        deleteById($(tbodyBtnThis).siblings(".activityId").text());
      }
    });
  });
  // 刪除按鈕部分結束

  // 分頁開始
  /*----產生data-th-----*/
  let $table = $(".activeBackTable");
  let $thRows = $table.find("thead th");
  $thRows.each(function (key, thRow) {
    $table
      .find("tbody tr td:nth-child(" + (key + 1) + ")")
      .attr("data-th", $(thRow).text());
  });
  /*-----------*/
  goPage(1, 5); // 一開始先秀第一頁,以及每一頁最多兩筆資料
  // 分頁結束
}
//表格操作按鈕 結束

// 表格帶入 開始

function tableData(data, i, status, dropBtnText) {
  $("#activeTbody").append(
    `<tr>
<td class="activityId">${data[i].activityId}</td>
<td class="activityType">${data[i].activityType}</td>
<td class="activityName">${data[i].activityName}</td>
<td class="teaId">${data[i].teaId}</td>
<!-- <td>${data[i].activityStartTime}</td> -->
<!-- <td>${data[i].activityEndTime}</td> -->
<td class="currentNumber">${data[i].currentNumber}</td>
<td class="status">${status}</td>
<td class="tbodyBtn">
<div class="btnList">
<a class="introduceBtn">詳情</a>
<a class="editBtn">修改</a>
<a class="dropBtn">${dropBtnText}</a>
<a class="deleteBtn">刪除</a>
</div>
</td>
</tr>
`
  );
}

// 表格帶入 結束
// 更新請求 開始
function updateStatus(data) {
  $.ajax({
    url: "/activeBackStatusEdit", // 資料請求的網址
    type: "PUT", // GET | POST | PUT | DELETE | PATCH
    data: data, // 將物件資料(不用雙引號) 傳送到指定的 url
    dataType: "text", // 預期會接收到回傳資料的格式： json | xml | html
    contentType: "application/json; charset=utf-8",
    success: function (response) {
      // 请求成功后执行的代码
      console.log(response); // 在控制台打印返回的数据
      if (response == "上架成功") {
        alert("上架成功");
        window.location.href = "/active/activeBack.html";
      } else if (response == "下架成功") {
        alert("下架成功");
        window.location.href = "/active/activeBack.html";
      } else {
        alert("無法操作，報名時間已截止");
        // window.location.href = "/active/activeBack.html";
      }
    },
  });
}
// 更新請求 結束

// 刪除請求 開始
function deleteById(id) {
  $.ajax({
    url: "/activeBackDelete", // 資料請求的網址
    type: "DELETE", // GET | POST | PUT | DELETE | PATCH
    data: { activityId: id }, // 將物件資料(不用雙引號) 傳送到指定的 url
    dataType: "text", // 預期會接收到回傳資料的格式： json | xml | html
    success: function (data) {
      // request 成功取得回應後執行
      console.log(data);
      if (data == "刪除成功") {
        alert("刪除成功");
        window.location.href = "/active/activeBack.html";
      } else {
        alert("刪除失敗，如遇問題請聯繫管理員");
      }
    },
  });
}
// 刪除請求 結束

// 分頁功能 開始

function goPage(currentPage, pageSize) {
  var tr = $(".activeBackTable tbody tr");
  var num = $(".activeBackTable tbody tr").length; //表格所有行數(所有記錄數)
  var totalPage = Math.ceil(num / pageSize); // 表格所有行數/每頁顯示行數 = 總頁數

  $("#numberPage").attr("max", totalPage); // 寫入跳至第幾頁input

  $("#numberPage")
    .off("change")
    .on("change", function () {
      // 跳至第幾頁
      let numberPage = $("#numberPage").val();
      if (numberPage > totalPage) {
        console.log("頁數超過");
        return;
      }
      goPage(numberPage, 2);
    });

  var startRow = (currentPage - 1) * pageSize + 1; //開始顯示的行
  var endRow = currentPage * pageSize; //結束顯示的行
  endRow = endRow > num ? num : endRow;

  //遍歷顯示資料實現分頁
  for (var i = 1; i < num + 1; i++) {
    var trRow = tr[i - 1];
    if (i >= startRow && i <= endRow) {
      trRow.style.display = "";
    } else {
      trRow.style.display = "none";
    }
  }

  var tempStr = "";
  if (currentPage > 1) {
    tempStr += `<a href="javascript:;" class="pageBtnQuick" onClick="goPage(1,${pageSize})">首頁</a>`;
    tempStr += `<a href="javascript:;" class="pageBtn" onClick="goPage(${
      currentPage - 1
    },${pageSize})">上一頁</a>`;
  } else {
    tempStr += `<a href="javascript:;" class="disabled pageBtnQuick">首頁</a>`;
    tempStr += `<a href="javascript:;" class="disabled pageBtn">上一頁</a>`;
  }

  tempStr += `<div><span>第${currentPage}頁</span>/<span>共${totalPage}頁</span></div>`;

  if (currentPage < totalPage) {
    tempStr += `<a href="javascript:;" class="pageBtn" onClick="goPage(${
      currentPage + 1
    },${pageSize})">下一頁</a>`;
    tempStr += `<a href="javascript:;"class="pageBtnQuick" onClick="goPage(${totalPage},${pageSize})">尾頁</a>`;
  } else {
    tempStr += `<a href="javascript:;" class="disabled pageBtn">下一頁</a>`;
    tempStr += `<a href="javascript:;" class="disabled pageBtnQuick">尾頁</a>`;
  }

  $("#pageModule").html(tempStr);
}
// 分頁功能 結束
// 請求報名成員訂單明細 開始
function axiosOrderList(activityId) {
  return axios
    .get("/activeOrderList", {
      params: {
        activityId: activityId,
      },
    })
    .then((res) => {
      return res.data;
    });
}
// 請求訂單明細 結束
// 時間轉換 開始
function convertToFormattedDate(dateString) {
  // 移除 "T" 字元並分割時間字串
  var dateTimeParts = dateString.split("T");
  var datePart = dateTimeParts[0];
  var timePart = dateTimeParts[1];

  // 取得年、月、日
  var year = datePart.substr(0, 4);
  var month = datePart.substr(5, 2);
  var day = datePart.substr(8, 2);

  // 取得時、分
  var hour = timePart.substr(0, 2);
  var minute = timePart.substr(3, 2);

  // 組合成指定格式的時間字串
  var formattedDate =
    year + "-" + month + "-" + day + " " + hour + ":" + minute;

  return formattedDate;
}
// 時間轉換 結束
// function區域 結束

$(document).ready(function () {
  // 初始化日曆
  $("#calendar").fullCalendar({
    height: 600,
    width: 800,
    editable: true,
    // events: [
    //   {
    //     id: "eventGroup1",
    //     title: "昨天的活動",
    //     start: moment().subtract(2, "days").format("YYYY-MM-DD HH:mm:ss"),
    //   },
    //   {
    //     title: "持續一周的活動",
    //     start: moment().add(7, "days").format("YYYY-MM-DD HH:mm:ss"),
    //     end: moment().add(14, "days").format("YYYY-MM-DD HH:mm:ss"),
    //     color: "#3c6a36",
    //   },
    // ],
    eventDrop: function (event, delta, revertFunc) {
      console.log("拖曳事件：", event);
      var newStartDate = event.start.format("YYYY-MM-DD HH:mm:ss");
      console.log("新的開始日期：", newStartDate);
      if (event.end) {
        var newEndDate = event.end.format("YYYY-MM-DD HH:mm:ss");
        console.log("新的結束日期：", newEndDate);
      }
    },
    eventClick: function (event) {
      //  燈箱部分開始

      $(".lightbox").fadeIn();

      $(".lightbox").empty().append(`
      <div class="lightbox-content">
      <span class="lightbox-close">&times;</span>
      <div
      style="width: 700px; "
    >
   

          <label for="favtitleBox">Select your Title:</label>
          <input type="text" id="favtitleBox" name="favtitleBox" value=${
            event.title
          } />
          <br />
          <label for="favStartBox">Select your StartTime:</label>
          <input type="datetime-local" id="favStartBox" name="favStartBox" value=${inputFormattedDate(
            event.start
          )} />
          <br />
          <label for="favEndBox">Select your EndTime:</label>
          <input type="datetime-local" id="favEndBox" name="favEndBox" value= ${inputFormattedDate(
            event.end
          )} />
          <br />
          <label for="favtextBox">Select your Text:</label>
          <textarea name="favtextBox" id="favtextBox" cols="30" rows="10">${
            event.description
          }</textarea>
          <br />
          <label for="favcolorBox">Select your Color:</label>
          <input type="color" id="favcolorBox" name="favcolorBox" value=${
            event.color
          } />
          <br />

          <div class="flexWrap">
          
          <div id="nothingBtn" class="flexAllCenter">取消</div>
          <div id="editBtn" class="flexAllCenter">修改</div>
          <div id="deleteBtn" class="flexAllCenter">刪除</div>
          
          </div>


    </div>
      </div>`);

      // 取消 開始
      $("#nothingBtn").on("click", function () {
        $(".lightbox").fadeOut();
      });
      // 取消 結束
      // 修改活動 開始
      $("#editBtn").on("click", function () {
        let dataEdit = {
          calendarId: event.id,
          calendarName: $("#favtitleBox").val(),
          calendarStartTime: $("#favStartBox").val(),
          calendarEndTime: $("#favEndBox").val(),
          calendarContent: $("#favtextBox").val(),
          calendarColor: $("#favcolorBox").val(),
        };
        axios
          .put("/calendarPut", dataEdit)
          .then((res) => {
            console.log(res.data);
            if (res.data == "修改成功") {
              alert("修改成功");
              window.location.href = "/member/MySubscribe.html";
            }
          })
          .catch((err) => {
            console.log(err.response.data);
          });
      });
      // 修改活動 結束
      // 刪除活動 開始
      $("#deleteBtn").on("click", function () {
        axios
          .delete("/calendarDelete", { params: { calendarId: event.id } })
          .then((res) => {
            console.log(res.data);
            if (res.data == "刪除成功") {
              alert("刪除成功");
              window.location.href = "/member/MySubscribe.html";
            }
          })
          .catch((err) => {
            console.log(err.response.data);
          });
      });
      // 刪除活動 結束
    },
    eventRender: function (event, element) {
      // 移除 fc-time 類別
      element.find(".fc-time").remove();
    },
  });

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

  //  燈箱部分結束

  //   創建陣列活動物件

  //   var events = [];

  //   for (var i = 0; i < 3; i++) {
  //     // 創建活動物件
  //     var event = {
  //       title: "活動標題" + i,
  //       start: moment("2023-06-01").format("YYYY-MM-DD HH:mm:ss"),
  //       end: moment("2023-06-05").format("YYYY-MM-DD HH:mm:ss"),
  //       description: i + "I" + i + "I" + i + "I" + i + "I",
  //     };

  //     // 將活動物件加入陣列中
  //     events.push(event);
  //   }
  //   $("#calendar").fullCalendar("renderEvents", events);

  // 創建單個活動物件，並寫到資料庫
  $("#submitCalender").on("click", function () {
    var data = {
      calendarName: $("#favtitle").val(),
      calendarStartTime: $("#favStart").val(),
      calendarEndTime: $("#favEnd").val(),
      calendarContent: $("#favtext").val(),
      calendarColor: $("#favcolor").val(),
    };

    axios
      .post("/calendarPost", data)
      .then((res) => {
        console.log(res.data);
      })
      .catch((err) => {
        console.log(err.response.data);
      });
  });

  // 從資料庫撈資料 開始
  axios.get("/calendarGet").then((res) => {
    for (let i = 0; i < res.data.length; i++) {
      // console.log(res.data[i]);
      var event = {
        id: res.data[i].calendarId,
        title: res.data[i].calendarName,
        start: moment(res.data[i].calendarStartTime).format(
          "YYYY-MM-DD HH:mm:ss"
        ),
        end: moment(res.data[i].calendarEndTime).format("YYYY-MM-DD HH:mm:ss"),
        description: res.data[i].calendarContent,
        color: res.data[i].calendarColor,
      };
      $("#calendar").fullCalendar("renderEvent", event);
    }
  });

  // 從資料庫撈資料 結束
});

// 轉換時間格式 開始
// 接收
function inputFormattedDate(dateString) {
  // 將時間字串轉換成 Date 物件
  var date = new Date(dateString);

  // 取得年、月、日、時、分
  var year = date.getFullYear();
  var month = ("0" + (date.getMonth() + 1)).slice(-2);
  var day = ("0" + date.getDate()).slice(-2);
  var hours = ("0" + date.getHours()).slice(-2);
  var minutes = ("0" + date.getMinutes()).slice(-2);

  // 組合成指定格式的時間字串
  var formattedDate =
    year + "-" + month + "-" + day + "T" + hours + ":" + minutes;

  return formattedDate;
}
// 轉換時間格式 結束

//===========點擊活動新增之後===========//
$(document).ready(function() {
  $("#submitCalender").click(function() {
        // 顯示活動新增成功
    alert("活動新增成功！");

    // 刷新頁面
    location.reload();
  });
});
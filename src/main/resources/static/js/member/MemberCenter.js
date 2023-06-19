//==========行事曆============//
//初始化
$(document).ready(function () {
  $("#calendar").fullCalendar();
});

//===========option 切換頁面===========//
// $(document).ready(function() {
//   console.log('test : ', 12345789)
//   $("#navSearch1").change(function() {
//     let switchValue = $('#navSearch1').find(":selected").val();;
//     console.log('switchValue:',switchValue)
//     switch (switchValue) {
//       case "MemberCenter":
//         location.href="/member/MemberCenter.html";
//         break;
//       case "MemberDetail":
//         location.href="/member/MemberDetail.html";
//         break;
//       case "MySubscribe":
//         location.href="/member/MySubscribe.html";
//         break;
//       case "orderList":
//         location.href="/member/orderList.html";
//         break;
//       case "inboxmail":
//         location.href="/member/inboxmail.html";
//         break;
//       default:
//         return;
//     }
//   });
// });

const app = Vue.createApp({
  data() {
    return {
      url: "/memberDetail",
      memberList: [],
    };
  },
  mounted() {
    axiosMemberLession().then((data) => {
      this.memberList = data;
      console.log("memberList", this.memberList);

      // 在成员列表数据获取后，为每个成员获取课程图片URL
      this.memberList.forEach((member) => {
        this.axiosGetCourseImg(member.courseId).then((url) => {
          console.log(url);
          member.courseImgUrl = url;
        });
      });
    });
  },
  methods: {
    axiosGetCourseImg(id) {
      return axios
        .get("/getCourseImg", { params: { courseId: id } })
        .then((res) => {
          return "data:image/null;base64," + res.data.coursePhoto1;
        });
    },
    axiosMemberLession() {
      return axios.get("/memberLession").then((res) => {
        return res.data;
      });
    },
    // 其他方法...
    hrefCourseId(id) {
      window.location.href = "/courseteacher/CourseVideo.html?courseId=" + id;
    },
  },
});

window.addEventListener("load", () => {
  app.mount("#app");
});

$(function () {
  var test = JSON.parse(sessionStorage.getItem("memberStorage"));
  $("span#memNameHTML").html("會員暱稱:" + test.memNickname);
  var imageSrc;
  if (test.memPhoto == null) {
    imageSrc = "/teacherWanted/baseVer2/img/logo1.png";
  } else {
    imageSrc = "data:image/png;base64," + test.memPhoto;
  }
  $("img#eximg").attr("src", imageSrc);
});

function axiosMemberLession() {
  return axios.get("/memberLesson").then((res) => {
    console.log("我的課程ㄚㄚㄚㄚㄚㄚ");
    console.log(res.data);
    return res.data;
  });
}

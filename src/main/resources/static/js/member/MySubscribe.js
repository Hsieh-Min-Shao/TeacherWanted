// //==========行事曆============//
// //初始化
// // $(document).ready(function() {
// //   $('#calendar').fullCalendar();

// // });

//==========option 切換頁面===========//
$(document).ready(function () {
  // console.log("test : ", 12345789);
  $("#navSearch1").change(function () {
    let switchValue = $("#navSearch1").find(":selected").val();
    // console.log("switchValue:", switchValue)
    switch (switchValue) {
      case "MemberCenter":
        window.location.href = "/member/MemberCenter.html";
        break;
      case "MemberDetail":
        window.location.href = "/member/MemberDetail.html";
        break;
      case "MySubscribe":
        window.location.href = "/member/MySubscribe.html";
        break;
      case "orderList":
        window.location.href = "/member/orderList.html";
        break;
      case "inboxmail":
        window.location.href = "/member/inboxmail.html";
        break;
      default:
        return;
    }
  });
});

// //===========Vue 訂閱部分 =============//
// // const app_subscirbe = Vue.createApp({
// //   data() {
// //     return {
// //       courseId:"",
// //       courseUrl:"/course/"+ courseId,
// //       courseTableData: [],
// //       activityUrl:"/activeOrderMemberInfo",
// //       acitvityTableData: [],
// //       shopUrl:"/",
// //       shopTableData: [],

// //     };
// //   },
// //   mounted() {
// //     // 呼叫預先執行的函式
// //     // const url= "http://localhost:8080/memberInfo";
// //     // const urlParams = new URLSearchParams(window.location.search);
// //     // const memId = urlParams.get("memId");
// //     // console.log(this.memId);
// //     this.getMemberSubscribe();
// //     this.getMemberActiviySubscribe();
// //   },
// //   methods: {
// //     //拿到會員課程訂閱相關資料
// //     getMemberCourseSubscribe() {
// //       axios.get(this.courseUrl,{                            //promise 等後端回應

// //         courseId: this.courseId,
// //         courseName: this.courseName,
// //         coursePhoto1: this.coursePhoto1,
// //     })
// //       .then((response) => {
// //         console.log(response.data)                         // 後端回傳的資訊
// //         // response = {memId: 10}
// //         this.courseTableData = response.data,
// //         console.log(this.courseTableData),
// //         this.courseId = response.data.courseId,
// //         this.courseName = response.data.courseName,
// //         this.coursePhoto1 = response.data.coursePhoto1

// //       })
// //       .catch((error) => console.log(error))
// //     },
// //     //拿到會員活動訂閱相關資料
// //     getMemberActivitySubscribe() {
// //       axios.get(this.activityUrl,{                            //promise 等後端回應

// //         activityId: this.activityId,
// //         activiteName: this.activiteName,
// //         activityPhoto: this.activityPhoto,
// //     })
// //       .then((response) => {
// //         console.log(response.data)                         // 後端回傳的資訊
// //         // response = {memId: 10}
// //         this.activityTableData = response.data,
// //         console.log(this.courseTableData),
// //         this.activityId = response.data.activityId,
// //         this.activiteName = response.data.activiteName,
// //         this.activityPhoto = response.data.activityPhoto

// //       })
// //       .catch((error) => console.log(error))
// //     },

// //   },
// // });

// // window.addEventListener("load", () => {
// // app.mount("#app_subscribe");
// // });

// //===========Vue 收藏部分 =============//
// const app_favorite = Vue.createApp({
//   data() {
//     return {
//       url:"/",
//       memId :"",
//       memAccount: "",
//       memPassword: "",
//       memName: "",
//       memPhone: "" ,
//       memNickname: "",
//       memBirthday: "",
//       memGender: "",
//       memEmail: "",
//       mailVerify: "",
//       memLocation: "",
//       memPhoto: "",
//       interest1: "",
//       interest2: "",
//       interest3: "",
//       createTime: "",
//       updateTime: "",
//       memStatus: "",
//       member:[],
//       isDisabled: true

//     };
//   },
//   mounted() {
//     // 呼叫預先執行的函式
//     // const url= "http://localhost:8080/memberInfo";
//     // const urlParams = new URLSearchParams(window.location.search);
//     // const memId = urlParams.get("memId");
//     // console.log(this.memId);
//     this.getMemberDetail();                            //
//   },
//   methods: {
//     getMemberDetail() {
//       axios.post(this.url,{                            //promise 等後端回應

//         memAccount: this.memAccount,
//         memPassword: this.memPassword,
//         memName: this.memName,
//         memPhone: this.memPhone,
//         memNickname: this.memNickname,
//         memBirthday: this.memBirthday,
//         memGender: this.memGender,
//         memEmail: this.memEmail,
//         mailVerify: this.mailVerify,
//         memLocation: this.memLocation,
//         memPhoto: this.memPhoto,
//         interest1: this.interest1,
//         interest2: this.interest2,
//         interest3: this.interest3,
//         createTime: this.createTime,
//         updateTime: this.updateTime,
//         memStatus: this.memStatus

//     })
//       .then((response) => {
//         console.log(response.data)                         // 後端回傳的資訊
//         // response = {memId: 10}
//         this.member = response.data,
//         console.log(this.member),
//         this.memId = response.data.memId,
//         this.memAccount = response.data.memAccount,
//         this.memPassword = response.data.memPassword,
//         this.memName = response.data.memName,
//         this.memPhone = response.data.memPhone,
//         this.memNickname = response.data.memNickname,
//         this.memBirthday = response.data.memBirthday,
//         this.memGender = response.data.memGender,
//         this.memEmail= response.data.memEmail,
//         this.mailVerify = response.data.mailVerify,
//         this.memLocation = response.data.memLocation,
//         this.memPhoto = response.data.memPhoto,
//         this.interest1 = response.data.interest1,
//         this.interest2 = response.data.interest2,
//         this.interest3 = response.data.interest3,
//         this.createTime = response.data.createTime,
//         this.updateTime = response.data.updateTime,
//         this.memStatus = response.data.memStatus
//       })
//       .catch((error) => console.log(error))
//     },

//     edit(){
//       this.isDisabled = false;
//       console.log(this.memId);

//     },
//     //取消時
//     cancel() {
//       this.isDisabled = true;
//       this.getMemberDetail();
//     },

//     submit(){
//       //按送出之後要作業送出的資料
//       axios.put(this.url, {
//         //   headers: {
//         //     'Content-Type': 'application/x-www-form-urlencoded'
//         // },
//           member: this.member,
//           memId :this.memId,
//           memAccount: this.memAccount,
//           memPassword: this.memPassword,
//           memName: this.memName,
//           memPhone: this.memPhone,
//           memNickname: this.memNickname,
//           memBirthday: this.memBirthday,
//           memGender: this.memGender,
//           memEmail: this.memEmail,
//           mailVerify: this.mailVerify,
//           memLocation: this.memLocation,
//           memPhoto: this.memPhoto,
//           interest1: this.interest1,
//           interest2: this.interest2,
//           interest3: this.interest3,
//           createTime: this.createTime,
//           updateTime: this.updateTime,
//           memStatus: this.memStatus

//         })
//     }

//     // }
//   },
// });

// window.addEventListener("load", () => {
// app.mount("#app_favorite");
// });

// //===========Vue 許願部分 =============//
// const app_wish = Vue.createApp({
//   data() {
//     return {
//       url:"/memberDetail",
//       memId :"",
//       memAccount: "",
//       memPassword: "",
//       memName: "",
//       memPhone: "" ,
//       memNickname: "",
//       memBirthday: "",
//       memGender: "",
//       memEmail: "",
//       mailVerify: "",
//       memLocation: "",
//       memPhoto: "",
//       interest1: "",
//       interest2: "",
//       interest3: "",
//       createTime: "",
//       updateTime: "",
//       memStatus: "",
//       member:[],
//       isDisabled: true

//     };
//   },
//   mounted() {
//     // 呼叫預先執行的函式
//     // const url= "http://localhost:8080/memberInfo";
//     // const urlParams = new URLSearchParams(window.location.search);
//     // const memId = urlParams.get("memId");
//     // console.log(this.memId);
//     this.getMemberDetail();                            //
//   },
//   methods: {
//     getMemberDetail() {
//       axios.post(this.url,{                            //promise 等後端回應

//         memAccount: this.memAccount,
//         memPassword: this.memPassword,
//         memName: this.memName,
//         memPhone: this.memPhone,
//         memNickname: this.memNickname,
//         memBirthday: this.memBirthday,
//         memGender: this.memGender,
//         memEmail: this.memEmail,
//         mailVerify: this.mailVerify,
//         memLocation: this.memLocation,
//         memPhoto: this.memPhoto,
//         interest1: this.interest1,
//         interest2: this.interest2,
//         interest3: this.interest3,
//         createTime: this.createTime,
//         updateTime: this.updateTime,
//         memStatus: this.memStatus

//     })
//       .then((response) => {
//         console.log(response.data)                         // 後端回傳的資訊
//         // response = {memId: 10}
//         this.member = response.data,
//         console.log(this.member),
//         this.memId = response.data.memId,
//         this.memAccount = response.data.memAccount,
//         this.memPassword = response.data.memPassword,
//         this.memName = response.data.memName,
//         this.memPhone = response.data.memPhone,
//         this.memNickname = response.data.memNickname,
//         this.memBirthday = response.data.memBirthday,
//         this.memGender = response.data.memGender,
//         this.memEmail= response.data.memEmail,
//         this.mailVerify = response.data.mailVerify,
//         this.memLocation = response.data.memLocation,
//         this.memPhoto = response.data.memPhoto,
//         this.interest1 = response.data.interest1,
//         this.interest2 = response.data.interest2,
//         this.interest3 = response.data.interest3,
//         this.createTime = response.data.createTime,
//         this.updateTime = response.data.updateTime,
//         this.memStatus = response.data.memStatus
//       })
//       .catch((error) => console.log(error))
//     },

//     edit(){
//       this.isDisabled = false;
//       console.log(this.memId);

//     },
//     //取消時
//     cancel() {
//       this.isDisabled = true;
//       this.getMemberDetail();
//     },

//     submit(){
//       //按送出之後要作業送出的資料
//       axios.put(this.url, {
//         //   headers: {
//         //     'Content-Type': 'application/x-www-form-urlencoded'
//         // },
//           member: this.member,
//           memId :this.memId,
//           memAccount: this.memAccount,
//           memPassword: this.memPassword,
//           memName: this.memName,
//           memPhone: this.memPhone,
//           memNickname: this.memNickname,
//           memBirthday: this.memBirthday,
//           memGender: this.memGender,
//           memEmail: this.memEmail,
//           mailVerify: this.mailVerify,
//           memLocation: this.memLocation,
//           memPhoto: this.memPhoto,
//           interest1: this.interest1,
//           interest2: this.interest2,
//           interest3: this.interest3,
//           createTime: this.createTime,
//           updateTime: this.updateTime,
//           memStatus: this.memStatus

//         })
//     }

//     // }
//   },
// });

// window.addEventListener("load", () => {
// app.mount("#app_wish");
// });

// //===========Vue 左側部分 =============//
// // onfile(event){
// // this.file=event.target.files[0]
// // let filereader=new FileReader();
// // filereader.readAsDataURL(this.file)
// // filereader.addEventListener("load",()=>{
// // this.memPhoto=filereader.result;
// // console.warn(this.memPhoto)

// // })

// // }
// //===========Vue 左側部分 =============//
const app = Vue.createApp({
  data() {
    return {
      url:"/memberDetail",
      memName: "",
      memPhoto: "",
      member:[],

    };
  },
  mounted() {
    // 呼叫預先執行的函式
    // const url= "http://localhost:8080/memberInfo";
    // const urlParams = new URLSearchParams(window.location.search);
    // const memId = urlParams.get("memId");
    // console.log(this.memId);
    this.getMemberDetail();                            //
  },
  methods: {
    getMemberDetail() {
      console.log("getMemberDetail")
      axios.post(this.url,{                            //promise 等後端回應

        memName: this.memName,
        memPhoto: this.memPhoto,

    })
      .then((response) => {
        console.log(response.data)                         // 後端回傳的資訊
        // response = {memId: 10}
        this.member = response.data,
        console.log(this.member),

        this.memName = response.data.memName,

        this.memPhoto = response.data.memPhoto

      })
      .catch((error) => console.log(error))
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
    imageSrc = "/img/member/aside/logobgg.png";
  } else {
    imageSrc = "data:image/png;base64," + test.memPhoto;
  }
  $("img#eximg").attr("src", imageSrc);
});



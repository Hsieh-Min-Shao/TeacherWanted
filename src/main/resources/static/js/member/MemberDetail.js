//=========上傳圖片 的編碼============//
function extractBase64String(dataURL) {
  var prefix = "data:image/";
  var index = dataURL.indexOf(";base64,");

  if (index !== -1) {
    var mimeType = dataURL.substring(prefix.length, index);
    var base64String = dataURL.substring(index + ";base64,".length);
    return {
      mimeType: mimeType,
      base64String: base64String,
    };
  }

  return null;
}

//==========行事曆============//
//初始化
$(document).ready(function () {
  $("#calendar").fullCalendar();
});
// $('#calendar').fullCalendar();
//===========頭貼上傳===========//
const btn = document.querySelector("#detail_submit");
const file = document.querySelector("#p_file");

//===========option按鈕切換頁面==========//
// $(document).ready(function () {
//   console.log("test : ", 12345789);
//   $("#navSearch1").change(function () {
//     let switchValue = $("#navSearch1").find(":selected").val();
//     console.log("switchValue:", switchValue);
//     switch (switchValue) {
//       case "MemberCenter":
//         location.href = "/member/MemberCenter.html";
//         break;
//       case "MemberDetail":
//         location.href = "/member/MemberDetail.html";
//         break;
//       case "MySubscribe":
//         location.href = "/member/MySubscribe.html";
//         break;
//       case "orderList":
//         location.href = "/member/orderList.html";
//         break;
//       case "inboxmail":
//         location.href = "/member/inboxmail.html";
//         break;
//       default:
//         return;
//     }
//   });
// });
// file.addEventListener('change', () => {
//   const service_img = document.querySelector('#service_img');
//   service_img.src = URL.createObjectURL(file.files[0]);
// });

//==========資料送回前端============//
//========================//
// var memberInfo ={
//     memId :memId,
//     memAccount: memAccount,
//     memPassword: memPassword,
//     memName: memName,
//     memPhone: memPhone ,
//     memNickname: memNickname,
//     memBirthday: memBirthday,
//     memGender: memGender,
//     memEmail: memEmail,
//     mailVerify: mailVerify,
//     memLocation: memLocation,
//     memPhoto: memPhoto,
//     interest1: interest1,
//     interest2: interest2,
//     interest3: interest3,
//     createTime: createTime,
//     updateTime: updateTime,
//     memStatus: memStatus
// }

// function axiosGetMember(memId) {
//   return axios
//     .get("http://localhost:8080/memberDetail", {
//       params: {
//         memId: memId
//       }
//     })
//     .then((res) => {
//       console.log("------------>", res.data);
//       return res.data;
//     })
//     .catch((err) => {
//       console.log("沒有資料喔!");
//       // console.error(err.response.data);
//       // alert(err.response.data);
//       // window.location.href = "/member/MemberDetail.html";
//     });
// }

//===========使用Vue  =============//
const app = Vue.createApp({
  data() {
    return {
      url: "/memberDetail",
      memId: "",
      memAccount: "",
      memPassword: "",
      memName: "",
      memPhone: "",
      memNickname: "",
      memBirthday: "",
      memGender: "",
      memEmail: "",
      mailVerify: "",
      memLocation: "",
      memPhoto: "",
      interest1: "",
      interest2: "",
      interest3: "",
      createTime: "",
      updateTime: "",
      memStatus: "",
      member: [],
      isDisabled: true,
      imageSrc: "",
    };
  },
  mounted() {
    // 呼叫預先執行的函式
    // const url= "http://localhost:8080/memberInfo";
    // const urlParams = new URLSearchParams(window.location.search);
    // const memId = urlParams.get("memId");
    // console.log(this.memId);

    this.getMemberDetail(); //
  },
  methods: {
    getMemberDetail() {
      axios
        .post(
          this.url
          // ,{                            //promise 等後端回應

          // memAccount: this.memAccount,
          // memPassword: this.memPassword,
          // memName: this.memName,
          // memPhone: this.memPhone,
          // memNickname: this.memNickname,
          // memBirthday: this.memBirthday,
          // memGender: this.memGender,
          // memEmail: this.memEmail,
          // mailVerify: this.mailVerify,
          // memLocation: this.memLocation,
          // memPhoto: this.memPhoto,
          // interest1: this.interest1,
          // interest2: this.interest2,
          // interest3: this.interest3,
          // createTime: this.createTime,
          // updateTime: this.updateTime,
          // memStatus: this.memStatus

          // }
        )
        .then((response) => {
          console.log(response.data); // 後端回傳的資訊
          // response = {memId: 10}
          this.member = response.data;
          console.log(this.member);
          this.memId = response.data.memId;
          console.log("memId:" + this.memId);
          this.memAccount = response.data.memAccount;
          this.memPassword = response.data.memPassword;
          this.memName = response.data.memName;
          this.memPhone = response.data.memPhone;
          this.memNickname = response.data.memNickname;
          this.memBirthday = response.data.memBirthday;
          this.memGender = response.data.memGender;
          this.memEmail = response.data.memEmail;
          this.mailVerify = response.data.mailVerify;
          this.memLocation = response.data.memLocation;
          this.memPhoto = response.data.memPhoto;
          this.interest1 = response.data.interest1;
          this.interest2 = response.data.interest2;
          this.interest3 = response.data.interest3;
          this.createTime = response.data.createTime;
          this.updateTime = response.data.updateTime;
          this.memStatus = response.data.memStatus;
          if (response.data.memPhoto == null) {
            this.imageSrc = "/img/member/aside/logobgg.png";
          } else {
            this.imageSrc = "data:image/png;base64," + response.data.memPhoto;
          }
        })
        .catch((error) => console.log(error));
    },

    edit() {
      this.isDisabled = false;
      console.log(this.memId);
    },
    //取消時
    cancel() {
      this.isDisabled = true;
      this.getMemberDetail();
    },

    submit() {
      console.log("我在submit裡面");
      console.log("memId:", this.member.memId);
      //按送出之後要作業送出的資料
      axios
        .put(this.url, {
          memId: this.member.memId,
          memAccount: this.member.memAccount,
          memPassword: this.member.memPassword,
          memName: this.member.memName,
          memPhone: this.member.memPhone,
          memNickname: this.member.memNickname,
          memBirthday: this.member.memBirthday,
          memGender: this.member.memGender,
          memEmail: this.member.memEmail,
          mailVerify: this.member.mailVerify,
          memLocation: this.member.memLocation,
          memPhoto: this.member.memPhoto,
          interest1: this.member.interest1,
          interest2: this.member.interest2,
          interest3: this.member.interest3,
          createTime: this.member.createTime,
          updateTime: this.member.updateTime,
          memStatus: this.member.memStatus,
          memPhoto: extractBase64String(this.imageSrc).base64String,
        })
        .then((response) => {
          // 成功回調函數
          console.log("資料更新成功");
          location.reload(); // 重新載入頁面
        })
        .catch((error) => console.log(error));
    },

    // }
    //=========上傳圖片============//

    handleFileUpload(event) {
      const file = event.target.files[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.imageSrc = e.target.result;
          // console.log(this.imageSrc);
        };
        reader.readAsDataURL(file);
      }
    },
    //=========分頁切換============//
    selectChange(event) {
      // 獲取選擇的值
      const selectedOption = event.target.value;
      // 執行你的操作，例如更新其他數據屬性
      console.log("選擇的值:", selectedOption);
      window.location.href = `/member/${selectedOption}.html`;
    },
  },
});

window.addEventListener("load", () => {
  app.mount("#app");
});

// axiosGetMember().then((data) => {
//       thimemId = data.memId;
//       this.memAccount = data.memAccount;
//       this.memPassword = data.memPassword;
//       this.memName = data.memName;
//       this.memPhone = data.memPhone;
//       this.memNickname = data.memNickname;
//       this.memBirthday = data.memBirthday;
//       this,memGender = data.memGender;
//       this.memEmail = data.memEmail;
//       this.mailVerify = data.mailVerify;
//       this.memLocation = data.memLocation;
//       this.memPhoto = data.memPhoto;
//       this.interest1 = data.interest1;
//       this.interest2 = data.interest2;
//       this.interest3 = data.interest3;
//       this.createTime = data.createTime;
//       this.updateTime = data.updateTime;
//       this.memStatus = data.memStatus;
//     });

// const url= "http://localhost:8080/memberInfo";

// axios.post(url,{                            //promise 等後端回應

//     // memId :memId,
//     // memAccount: memAccount,
//     // memPassword: memPassword,
//     // memName: memName,
//     // memPhone: memPhone ,
//     // memNickname: memNickname,
//     // memBirthday: memBirthday,
//     // memGender: memGender,
//     // memEmail: memEmail,
//     // mailVerify: mailVerify,
//     // memLocation: memLocation,
//     // memPhoto: memPhoto,
//     // interest1: interest1,
//     // interest2: interest2,
//     // interest3: interest3,
//     // createTime: createTime,
//     // updateTime: updateTime,
//     // memStatus: memStatus

// })
//   .then((resonse) =>console.log(resonse))// 後端回傳的資訊
//   .catch((error) => console.log(error))

// const app = Vue.createApp({
//   data() {
//     return {
//       activityLocation: "",
//       activityStartTime: "",
//       activityEndTime: "",
//       activityPrice: "",
//       imgSrc: "",
//       memName: "",
//       memPhone: "",
//       memEmail: "",
//     };
//   },
//   mounted() {
//     // 呼叫預先執行的函式
//     axiosConfirmOrder();
//     axiosGetActive().then((data) => {
//       this.activityLocation = data.activityLocation;
//       this.activityStartTime = convertToFormattedDate(data.activityStartTime);
//       this.activityEndTime = convertToFormattedDate(data.activityEndTime);
//       this.activityPrice = data.activityPrice;
//       this.imgSrc =
//         "data:image/" +
//         data.activityPhotoType +
//         ";base64," +
//         data.activityPhoto;
//     });
//     axiosGetMember().then((data) => {
//       this.memName = data.memName;
//       this.memPhone = data.memPhone;
//       this.memEmail = data.memEmail;
//     });
//   },
//   methods: {},
// });

// window.addEventListener("load", () => {
//   app.mount("#app");
// });

//==========分頁切換============//

// $(function () {
//   $("#formal").on("change", function () {
//     alert("chanfe！！！", $("#formal").val());
//     window.location.href = `/member/${$("#formal").val()}.html`;
//   });
// });

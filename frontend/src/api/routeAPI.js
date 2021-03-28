import axios from "axios";

export const getRoute = (areaID) => {
  const accessToken =
    "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyIiwiYXVkIjoidGVzdEB0ZXN0LmNvbSIsImV4cCI6MTYxNjkxODM1N30.4f4rX_qWSiSd9-BDt4Z3jmCMDzlt9_qEMNXxKmDz3Dp3mnN3nysw_IcgyiBwxFuIs2IkWc3Xns4kzBBwZQrRGA";
  axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  axios
    .get(`/route/`)
    .then((res) => {
      //   const { accessToken, refreshToken, expiredTime } = res.data;
      //   localStorage.setItem("refreshToken", refreshToken);
      //   localStorage.setItem("expiredTime", expiredTime);
      console.log(res);
    })
    .catch((error) => {
      console.error(error);
    });
};

export const postRoute = (areaID) => {
  const accessToken =
    "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyIiwiYXVkIjoidGVzdEB0ZXN0LmNvbSIsImV4cCI6MTYxNjkxODM1N30.4f4rX_qWSiSd9-BDt4Z3jmCMDzlt9_qEMNXxKmDz3Dp3mnN3nysw_IcgyiBwxFuIs2IkWc3Xns4kzBBwZQrRGA";

  axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  axios
    .post(`/route/empty`, {
      name: "홍대 데이트",
    })
    .then((res) => {
      //   const { accessToken, refreshToken, expiredTime } = res.data;
      //   localStorage.setItem("refreshToken", refreshToken);
      //   localStorage.setItem("expiredTime", expiredTime);
      console.log(res);
    })
    .catch((error) => {
      console.error(error);
    });
};

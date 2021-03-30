import axios from "axios";

export const getAuthorized = () => {
  const accessToken =
    "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0IiwiYXVkIjoibnRoOTcwOEBuYXZlci5jb20iLCJleHAiOjE2MTcwODI5MTB9.IOb7yZPnrYdaFdSF9IzgiE6_bxdzJIIG7hTe1jmQqX8CJ1V_1-Q8re98GRma9bxCc7iC7yu6_s35e8h7theHAA";
  axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  return accessToken;
};

export const getRoute = (areaID) => {
  axios
    .get(`/route/123`)
    .then((res) => {
      const { accessToken, refreshToken, expiredTime } = res.data;
      localStorage.setItem("refreshToken", refreshToken);
      localStorage.setItem("expiredTime", expiredTime);
      console.log(res);
    })
    .catch((error) => {
      console.error(error);
    });
};

export const postEmpty = () => {
  axios
    .post(`/route/empty`, {
      name: "Test1234",
    })
    .then((res) => {
      const { accessToken, refreshToken, expiredTime } = res.data;
      localStorage.setItem("refreshToken", refreshToken);
      localStorage.setItem("expiredTime", expiredTime);

      console.log(res);
      console.log(accessToken, refreshToken, expiredTime);
    })
    .catch((error) => {
      console.error(error);
    });
};

export const postRoute = (areaID) => {
  const accessToken =
    "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0IiwiYXVkIjoibnRoOTcwOEBuYXZlci5jb20iLCJleHAiOjE2MTcwMDA5ODJ9.XhRlxDabhAcGD8_2bFfqRCB-JoNGKgwOLx4dVRGo1Kgspg_Si6s9DOhAVsHEOvzjkQvSlno1_dMYnmL_dcqGvA";
  axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
  axios
    .post(`/route`, {
      name: "TEST1",
      places: [
        {
          id: 123,
          image:
            "https://api.apjung.xyz/tour/images/tour/main_new/mvisual_img07.jpg",
          name: "강릉바닷가",
          url: "https://api.apjung.xyz/tour/index.do",
          x: 37.748125,
          y: 128.878996,
          order: 1,
        },
        {
          id: 124,
          image:
            "https://api.apjung.xyz/xdata/images/hotel/270x200/129750773.jpg?k=d338049190ff48b19261ee5f516ee563aaeb8aeb97c4774c1e171e402cf25891&o=",
          name: "강릉 어린이집",
          url:
            "https://api.apjung.xyz/go/south-korea/kr-best-gangneung-things-to-do",
          x: 37.74813,
          y: 128.8789333,
          order: 2,
        },
      ],
    })
    .then((res) => {
      const { accessToken, refreshToken, expiredTime } = res.data;
      localStorage.setItem("refreshToken", refreshToken);
      localStorage.setItem("expiredTime", expiredTime);
      console.log(res);
    })
    .catch((error) => {
      console.error(error);
    });
};

import axios from "axios";

export const onLogin = (email, password) => {
  const data = {
    email,
    password,
  };
  console.log(data);
  axios
    .post("/auth/login", data)
    .then((res) => {
      const { accessToken, refreshToken, expiredTime } = res.data;
      axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
      localStorage.setItem("refreshToken", refreshToken);
      localStorage.setItem("expiredTime", expiredTime);
    })
    .catch((error) => {
      console.error(error);
      throw new Error(`Unexpected Login Error ${error}`);
    });
};

export const onRegister = (nickname, email, password) => {
  const data = {
    nickname,
    email,
    password,
  };
  axios
    .post("/auth/register", data)
    .then((res) => {
      return res.data.result;
    })
    .catch((error) => {
      console.error(error);
      throw new Error(`Unexpected Register Error ${error}`);
    });
};
